package com.myaws.myapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller // 어노테이션으로 스프링에게 객체 생성 요청
@RequestMapping(value="/member/")
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@RequestMapping(value="memberJoin.aws",method =RequestMethod.GET) // get방식으로 request
	public String memberJoin() {
		logger.info("memberJoin들어옴"); //p
		
		
		return "WEB-INF/member/memberJoin";
	}
	
	@RequestMapping(value="memberLogin.aws",method=RequestMethod.GET)
	public String memberLogin() {
		
		return "WEB-INF/member/memberLogin";
	}
	
}
