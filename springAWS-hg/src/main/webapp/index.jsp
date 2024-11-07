<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>스프링 학습하기</title>
</head>
<body>

<% if (session.getAttribute("midx") != null){
	out.println(session.getAttribute("memberName")+"<a href='"+request.getContextPath()+"/member/memberLogout.aws'>로그아웃</a>");	
} %>

<br>
<a href="<%=request.getContextPath() %>/member/memberJoin.aws">회원 가입 페이지</a>
<br>
<a href="<%=request.getContextPath() %>/member/memberLogin.aws">회원 로그인 페이지</a>
<br>
<a href="<%=request.getContextPath() %>/member/memberList.aws">회원 리스트 페이지</a>
<br>
<br>
<a href="<%=request.getContextPath() %>/board/boardList.aws">게시판 목록 페이지</a>

</body>
</html> 