package com.myaws.myapp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 가상경로에 해당하는 메소드 접근 전에 가로채기
		
		HttpSession session = request.getSession();
		
		if (session.getAttribute("midx") == null) { 
			// 로그인정보가 없으면 
			// 이동하려고 하는 주소를 보관하고 
			// 로그인 페이지로 보낸다.
			
			saveUrl(request); //이동할 경로를 저장한다
//			System.out.println("리스트로 들어왔니?");
			
			response.sendRedirect(request.getContextPath()+"/member/memberLogin.aws");
			return false;
			
		} else { // 회원정보가 저장되어있으면 이동함 
			return true;
		}
	}

	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		
	}

	public void saveUrl(HttpServletRequest request) {
		
		String uri = request.getRequestURI(); // 전체경로주소
		String param = request.getQueryString(); // 파라미터를 가져온다
		
		if(param == null || param.equals("null") || param.equals("")) {
			param="";
		} else {
			param = "?" + param;		
		}
		
		String locationUrl = uri + param;
		
		HttpSession session = request.getSession();
		if (request.getMethod().equals("GET")) { //대문자 (GET방식으로 넘어왔을때)
			session.setAttribute("saveUrl", locationUrl); // 값을 저장해놓는다.
		}
	
	}

}
