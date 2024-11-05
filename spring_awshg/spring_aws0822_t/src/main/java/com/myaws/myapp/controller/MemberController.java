package com.myaws.myapp.controller;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myaws.myapp.domain.MemberVo;
import com.myaws.myapp.service.MemberService;

import netscape.javascript.JSObject;

@Controller
@RequestMapping(value="/member/")
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);	
		
	@Autowired
	private MemberService memberService;
	
	@Autowired(required=false)
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@RequestMapping(value="memberJoin.aws",method=RequestMethod.GET)
	public String memberJoin() {		
		logger.info("memberJoin����");		
		logger.info("bCryptPasswordEncoder==>"+bCryptPasswordEncoder);
	//	logger.info("tt����"+tt.test());
		
		return "WEB-INF/member/memberJoin";
	}
	
	@RequestMapping(value="memberJoinAction.aws",method=RequestMethod.POST)
	public String memberJoinAction(MemberVo mv) {		
		logger.info("memberJoinAction����");		
		logger.info("bCryptPasswordEncoder==>"+bCryptPasswordEncoder);
		
		String memberpwd_enc = bCryptPasswordEncoder.encode(mv.getMemberpwd());
		mv.setMemberpwd(memberpwd_enc);
		
		int value = memberService.memberInsert(mv);
		logger.info("value:"+value);
		
		String path="";
		if (value==1) {
			path = "redirect:/";
		}else if (value==0) {
			path = "redirect:/member/memberJoin.aws";
		}		
		return path;
	}
	
	@RequestMapping(value="memberLogin.aws",method=RequestMethod.GET)
	public String memberLogin() {		
		
		return "WEB-INF/member/memberLogin";
	}
	
	@RequestMapping(value="memberLoginAction.aws",method=RequestMethod.POST)
	public String memberLoginAction(
			@RequestParam("memberid") String memberid, 
			@RequestParam("memberpwd") String memberpwd,
			RedirectAttributes rttr
			) {		
		
		MemberVo mv = memberService.memberLoginCheck(memberid);
		//����� ��й�ȣ�� �����´�
		
		String path = "";
		if (mv != null) {  //��ü���� ������
			String reservedPwd = mv.getMemberpwd(); 
		
			if(bCryptPasswordEncoder.matches(memberpwd, reservedPwd)) {
			//	System.out.println("��й�ȣ ��ġ");
				 rttr.addAttribute("midx",mv.getMidx());
				 rttr.addAttribute("memberId", mv.getMemberid());
				 rttr.addAttribute("memberName", mv.getMembername());			
				
				path ="redirect:/";
			}else {				
//				 rttr.addAttribute("midx","");
//				 rttr.addAttribute("memberId", "");
//				 rttr.addAttribute("memberName", "");	
				 rttr.addFlashAttribute("msg", "���̵�/��й�ȣ�� Ȯ�����ּ���");
				path = "redirect:/member/memberLogin.aws";
			}		
		}else {
//			 rttr.addAttribute("midx","");
//			 rttr.addAttribute("memberId", "");
//			 rttr.addAttribute("memberName", "");	
			 rttr.addFlashAttribute("msg", "�ش��ϴ� ���̵� �����ϴ�.");
			path = "redirect:/member/memberLogin.aws";
		}		
		//ȸ��������  ���ǿ� ��´�
				
		return path;
	}
	
	
	@ResponseBody
	@RequestMapping(value="memberIdCheck.aws",method=RequestMethod.POST)
	public JSONObject memberIdCheck(@RequestParam("memberId") String memberId) {		
			
		int cnt = memberService.memberIdCheck(memberId);					
	
		JSONObject obj = new JSONObject();
		obj.put("cnt", cnt);		
		
		return obj;
	}
	
	
	
	
	
	
	
	
	
	
}
