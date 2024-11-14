<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page import ="java.util.*" %>
 <%@page import ="com.myaws.myapp.domain.*" %>  
 <%@ taglib prefix = "c" uri= "http://java.sun.com/jsp/jstl/core" %> 
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글목록</title>
<link href="${pageContext.request.contextPath}/resources/css/style2.css" rel="stylesheet">
</head>
<body>
<header>
	<h2 class="mainTitle">글목록</h2>
	<form class="search" name="frm" action="${pageContext.request.contextPath}/board/boardList.aws" method="get">
		<select name="searchType">
			<option value="subject">제목</option>
			<option value="writer">작성자</option>
		</select>
		<input type="text" name="keyword">
		<button  type="submit" class="btn">검색</button>
	</form>
</header>

<section>	
	<table class="listTable">
		<tr>
			<th>No</th>
			<th>제목</th>
			<th>작성자</th>
			<th>조회</th>
			<th>추천</th>
			<th>날짜</th>
		</tr>

		
		<c:forEach items="${blist}" var ="bv" varStatus="status"> <!--  ${stauts.index} -->
		<tr>
			<td>${pm.totalCnt-((status.index)+(pm.scri.page-1)*pm.scri.perPageNum)}</td>
			<td class="title">
			<c:forEach var="i" begin="1" end="${bv.level_}" step="1">
			  &nbsp;&nbsp;
			<c:if test="${i ==bv.level_ }">
			ㄴ
			</c:if>	
			</c:forEach>
			
			<a href="${pageContext.request.contextPath}/board/boardContents.aws?bidx=${bv.bidx}">${bv.subject}
			</a></td>
			<td>${bv.writer}</td>
			<td>${bv.viewcnt}</td>
			<td>${bv.recom}</td>
			<td>${bv.writeday}</td>
		</tr>
		</c:forEach>
		
	</table>
	
	<div class="btnBox">
		<a class="btn aBtn" href="${pageContext.request.contextPath}/board/boardWrite.aws">글쓰기</a>
	</div>
	 
	
	<c:set var="queryParam" value="keyword=${pm.scri.keyword}&searchType=${pm.scri.keyword}"></c:set>
	<div class="page">
		
		<ul>
		<c:if test= "${pm.prev == true}">
		<li><a href="${pageContext.request.contextPath}/board/boardList.aws?page=${pm.startPage-1}&${queryParam}"></a>◀</li>
		</c:if>


		<c:forEach var="i" begin="${pm.startPage}" end="${pm.endPage}" step="1"> 
		<li <c:if test="${i==pm.scri.page}"> class='on' </c:if>	>		
		<a href="${pageContext.request.contextPath}/board/boardList.aws?page=${i}&${queryParam}">
		<span style="font-size:20px;">${i}</span></a>
		</li>
		</c:forEach> 
		
		<c:if test="${pm.next == true}">
		<li><a href="${pageContext.request.contextPath}/board/boardList.aws?page=${pm.endPage+1}&${queryParam}">▶</a></li>
		</c:if>
		
		</ul>
	</div> 
</section>

</body>
</html>
