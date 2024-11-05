package com.myaws.myapp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	// 로그인 후에 회원정보를 세션에 담는다
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		//가로채기 하기 전에 처리하는 메소드
		
		HttpSession session = request.getSession();
		 System.out.println("세션정보" +session);
		
		if (session.getAttribute("midx") != null) {
			System.out.println("midx" + session.getAttribute("midx") );
			session.removeAttribute("midx");
			session.removeAttribute("memberId");
			session.removeAttribute("memberName");
			session.invalidate(); //초기화 시킴
		}
		return true;
	}
	
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		
		// rttr로 정의한 값을 갖는다.
		// RedirectAttribute나 Model객체에 담은 값을 꺼낸다.
		String midx = modelAndView.getModel().get("midx").toString();
		String memberId =  modelAndView.getModel().get("memberId").toString();
		String memberName = modelAndView.getModel().get("memberName").toString();
		
		modelAndView.getModel().clear(); // 넘어오는 파라미터 model값을 지운다.
		
		HttpSession session = request.getSession();
		if(midx != null) {
			session.setAttribute("midx", midx);
			session.setAttribute("memberId", memberId);
			session.setAttribute("memberName", memberName);
		
		}
		
	}
	

}
