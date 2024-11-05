package com.myaws.myapp.controller;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myaws.myapp.domain.MemberVo;
import com.myaws.myapp.service.MemberService;

@Controller // 어노테이션으로 스프링에게 객체 생성 요청
@RequestMapping(value = "/member/")
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private MemberService memberService;

	@Autowired(required = false)
	private BCryptPasswordEncoder bCryptPasswordEncoder; // ~security.crypto.bcrypt.BCryptPasswordEncoder

	@RequestMapping(value = "memberJoin.aws", method = RequestMethod.GET) // get방식으로 request
	public String memberJoin() {
		logger.info("memberJoin들어옴");

		return "WEB-INF/member/memberJoin";
	}

	@RequestMapping(value = "memberJoinAction.aws", method = RequestMethod.POST)
	public String memberJoinAction(MemberVo mv) {
		logger.info("memberJoinAction들어옴");
		logger.info("bCryptPasswordEncoder==> ");

//	      // 넘어온 비밀번호를 비밀번호 암호화 시키기
		String memberpw_enc = bCryptPasswordEncoder.encode(mv.getMemberpwd());
//	      // 암호화 시킨 비밀번호를 다시 넘기기
		mv.setMemberpwd(memberpw_enc);

		// 비밀번호 암호화 처리 기능 추가(memberLoginAction ~ )

		int value = memberService.memberInsert(mv);
		logger.info("JAvalue : " + value);

		String path = "";
		if (value == 1) {
			path = "redirect:/";
		} else if (value == 0) {
			path = "redirect:/member/memberJoin.aws";
		}

		return path;

	}

	@RequestMapping(value = "memberLogin.aws", method = RequestMethod.GET)
	public String memberLogin() {

		return "WEB-INF/member/memberLogin";
	}

	@RequestMapping(value = "memberLoginAction.aws", method = RequestMethod.GET)
	public String memberLoginAction(@RequestParam("memberid") String memberid,
			@RequestParam("memberPwd") String memberpwd) {
		logger.info("memberJoinAction들어옴");

		MemberVo mv = memberService.memberLoginCheck(memberid);
		// 저장된 비밀번호를 가져온다.

		String path = "";
		if (mv != null) { // 객체값이 null이면
			String reservedPwd = mv.getMemberpwd();
			if (bCryptPasswordEncoder.matches(memberpwd, reservedPwd)) {
				System.out.println("비밀번호 일치");

				path = "redirect:/";
			} else {

				path = "redirect:/member/memberLogin.aws";
			}
		} else {

			path = "redirect:/member/memberLogin.aws";
		}
		// 회원정보를 Session에 담는다.
		// 로그인에 실패하면 로그인페이지로 이동, 로그인이 되면 메인페이지로 이동

		return path;
	}

	@ResponseBody // 결과값 객체로 보낸다
	@RequestMapping(value = "memberIdCheck.aws", method = RequestMethod.POST)
	public JSONObject memberIdCheck(@RequestParam("memberId") String memberId) {

		// POJO방식
		int cnt = memberService.memberIdCheck(memberId);
		JSONObject obj = new JSONObject();
		obj.put("cnt", cnt);

		return obj;

	}
}
