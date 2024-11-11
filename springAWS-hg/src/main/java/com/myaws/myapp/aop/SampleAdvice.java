package com.myaws.myapp.aop;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component //빈으로 등록하기위한 부모클래스
@Aspect
public class SampleAdvice {

	public void startLog() {
		
	}
	
	
}
