package com.myaws.myapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.myaws.myapp.domain.MemberVo;
import com.myaws.myapp.service.MemberService;


@Controller // 어노테이션으로 스프링에게 객체 생성 요청
@RequestMapping(value="/member/")
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
//	@Autowired
//	private Test tt;
	
	@Autowired
	MemberService memberService;
	
	@RequestMapping(value="memberJoin.aws",method =RequestMethod.GET) // get방식으로 request
	public String memberJoin() {
		logger.info("memberJoin들어옴"); 
		//logger.info("tt값은"+tt.test()); 
		
		return "WEB-INF/member/memberJoin";
	}
	
	@RequestMapping(value="memberLogin.aws",method=RequestMethod.GET)
	public String memberLogin() {
		
		return "WEB-INF/member/memberLogin";
	}
	
	@RequestMapping(value="memberJoinAction.aws",method=RequestMethod.POST)
	public String memberJoinAction(MemberVo mv) {
		logger.info("memberJoinAction들어옴"); 
		
		int value = memberService.memberInsert(mv);
		logger.info("JAvalue : "+value);
		
		String path="";
		if(value == 1) {
			path = "redirect:/";
		} else if(value == 0) {
			path = "redirect:/member/memberJoin.aws";
		}
		
		
		
		return path;
	
	}
}
