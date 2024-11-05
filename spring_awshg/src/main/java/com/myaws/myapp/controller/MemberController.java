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

@Controller // ������̼����� ���������� ��ü ���� ��û
@RequestMapping(value = "/member/")
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private MemberService memberService;

	@Autowired(required = false)
	private BCryptPasswordEncoder bCryptPasswordEncoder; // ~security.crypto.bcrypt.BCryptPasswordEncoder

	@RequestMapping(value = "memberJoin.aws", method = RequestMethod.GET) // get������� request
	public String memberJoin() {
		logger.info("memberJoin����");

		return "WEB-INF/member/memberJoin";
	}

	@RequestMapping(value = "memberJoinAction.aws", method = RequestMethod.POST)
	public String memberJoinAction(MemberVo mv) {
		logger.info("memberJoinAction����");
		logger.info("bCryptPasswordEncoder==> ");

//	      // �Ѿ�� ��й�ȣ�� ��й�ȣ ��ȣȭ ��Ű��
		String memberpw_enc = bCryptPasswordEncoder.encode(mv.getMemberpwd());
//	      // ��ȣȭ ��Ų ��й�ȣ�� �ٽ� �ѱ��
		mv.setMemberpwd(memberpw_enc);

		// ��й�ȣ ��ȣȭ ó�� ��� �߰�(memberLoginAction ~ )

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
		logger.info("memberJoinAction����");

		MemberVo mv = memberService.memberLoginCheck(memberid);
		// ����� ��й�ȣ�� �����´�.

		String path = "";
		if (mv != null) { // ��ü���� null�̸�
			String reservedPwd = mv.getMemberpwd();
			if (bCryptPasswordEncoder.matches(memberpwd, reservedPwd)) {
				System.out.println("��й�ȣ ��ġ");

				path = "redirect:/";
			} else {

				path = "redirect:/member/memberLogin.aws";
			}
		} else {

			path = "redirect:/member/memberLogin.aws";
		}
		// ȸ�������� Session�� ��´�.
		// �α��ο� �����ϸ� �α����������� �̵�, �α����� �Ǹ� ������������ �̵�

		return path;
	}

	@ResponseBody // ����� ��ü�� ������
	@RequestMapping(value = "memberIdCheck.aws", method = RequestMethod.POST)
	public JSONObject memberIdCheck(@RequestParam("memberId") String memberId) {

		// POJO���
		int cnt = memberService.memberIdCheck(memberId);
		JSONObject obj = new JSONObject();
		obj.put("cnt", cnt);

		return obj;

	}
}
