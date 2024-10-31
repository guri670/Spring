package com.myaws.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller // 어노테이션으로 스프링에게 객체 생성 요청
public class MemberController {
	
	@RequestMapping(value="/member/memberJoin.aws",method =RequestMethod.GET) // get방식으로 request
	public String memberJoin() {
		
		
		return "WEB-INF/member/memberJoin";
	}
	
	@RequestMapping(value="/member/memberLogin.aws",method=RequestMethod.GET)
	public String memberLogin() {
		
		return "WEB-INF/member/memberLogin";
	}
	
}