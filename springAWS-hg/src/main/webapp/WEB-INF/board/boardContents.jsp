<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.myaws.myapp.domain.*" %>

<%
BoardVo bv = (BoardVo)request.getAttribute("bv");
CommentVo cv = (CommentVo)request.getAttribute("cv");
//강제 형변환 양쪽형을 맞춰준다.
 String memberName = "";
if(session.getAttribute("memberName") != null){
	memberName = (String)session.getAttribute("memberName");
} 
int midx=0;
if(session.getAttribute("midx") != null){
	midx = Integer.parseInt(session.getAttribute("midx").toString());
}

%> 


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글내용</title>
<script src="https://code.jquery.com/jquery-latest.min.js"></script> 
<!-- mvc jquery-CDN주소 추가  -->
<link href="<%=request.getContextPath() %>/resources/css/style2.css" rel="stylesheet">
<script> 

function checkImageType(fileName) {
	var parttern = /jpg$|gif$|png$|jpeg$/i; //자바스크립트의 정규표현식
	return fileName.match(pattern);
}
<%-- getOriginalFileName("<%=bv.getFilename()%>"); --%>

function getOriginalFileName(fileName) { //원본 파일이름 추출
	var idx = fileName.lastIndexOf("_")+1;
	//alert("fileName.substr : "+fileName.substr(idx));
	return fileName.substr(idx);
}

function getImageLink(fileName) {
	var front = fileName.substr(0,12); // 0 ~ 12번까지
	var end = fileName.substr(14); // 14번 ~ 끝까지 
	return front+end ; // 즉 0~12번 / s / 14번 ~ 값이 없는 파일을 리턴한다.
}

function download() {
	// 주소 사이에 -s 는빼고
	var downloadImage = getImageLink("<%=bv.getFilename()%>"); 
	var downLink="<%=request.getContextPath() %>/board/displayFile.aws?fileName="+downloadImage+"&down=1";
	
	return downLink;	
}

function commentDel(cidx){	
	let ans= confirm("삭제하시겠습니까?");	
	if (ans== true){
		
		$.ajax({
			type :  "get",    //전송방식
			url : "<%=request.getContextPath()%>/comment/"+cidx+"/commentDeleteAction.aws",
			dataType : "json",       // json타입은 문서에서  {"키값" : "value값","키값2":"value값2"}
			success : function(result){   //결과가 넘어와서 성공했을 받는 영역
			alert("전송성공 테스트");	
			//alert(result.value);
			$.boardCommentList();			
							
			},
			error : function(){  //결과가 실패했을때 받는 영역	
			//alert(result.value);
			alert("전송실패");
			}			
		});			
	}	
	return;
}




 //jquery로 만드는 함수  ready밖에 생성
$.boardCommentList = function(){
	//alert("ddddddd");
  	//alert("test");
	let block = $("#block").val();
  	alert("block:"+block);
  	
	$.ajax({
		type :  "get",    //전송방식
		url : "<%=request.getContextPath()%>/comment/<%=bv.getBidx()%>/"+block+"/commentList.aws",
		dataType : "json",       // json타입은 문서에서  {"키값" : "value값","키값2":"value값2"}
		success : function(result){   //결과가 넘어와서 성공했을 받는 영역
		alert("전송성공 테스트");			
		
		
		var strTr = "";				
		$(result.clist).each(function(){	
			
			var btnn="";			
			 //현재로그인 사람과 댓글쓴 사람의 번호가 같을때만 나타내준다
			if (this.midx == "<%=midx%>") {
				if (this.delyn=="N"){
					btnn= "<button type='button' onclick='commentDel("+this.cidx+");'>삭제</button>";
				}			
			}
			strTr = strTr + "<tr>"
			+"<td>"+this.cidx+"</td>"
			+"<td>"+this.cwriter+"</td>"
			+"<td class='content'>"+this.ccontents+"</td>"
			+"<td>"+this.writeday+"</td>"
			+"<td>"+btnn+"</td>"
			+"</tr>";					
		});		       
		
		var str  = "<table class='replyTable'>"
			+"<tr>"
			+"<th>번호</th>"
			+"<th>작성자</th>"
			+"<th>내용</th>"
			+"<th>날짜</th>"
			+"<th>DEL</th>"
			+"</tr>"+strTr+"</table>";		
		
		$("#commentListView").html(str);	
		
		if(result.moreView =="N") {
			$("#morebtn").css("display","none"); //감춘다
		} else {
			$("#morebtn").css("display","block"); //보여준다
		}
		
		let nextBlock = result.nextBlock;
		$("#block").val(nextBlock);
		
					
		},
		error : function(){  //결과가 실패했을때 받는 영역						
			alert("전송실패");
		}			
	});	
} 

$(document).ready(function(){	

	$("#dUrl").html(getOriginalFileName("<%=bv.getFilename()%>"));
	
	$("#dUrl").click(function(){
		$("#dUrl").attr("href",download());	
		
		return;
	});	
	
	$.boardCommentList();	
	
	$("#btn").click(function(){
		alert("추천버튼 클릭");		
	
		$.ajax({
			type :  "get",    //전송방식
			url : "<%=request.getContextPath()%>/board/boardRecom.aws?bidx=<%=bv.getBidx()%>",
			dataType : "json",       // json타입은 문서에서  {"키값" : "value값","키값2":"value값2"}
			success : function(result){   //결과가 넘어와서 성공했을 받는 영역
			//alert("전송성공 테스트");	
		
				var str ="추천("+result.recom+")";			
				$("#btn").val(str);			
			},
			error : function(){  //결과가 실패했을때 받는 영역						
				// alert("전송실패");
			}			
		});			
	});	
	
 	$("#cmtBtn").click(function(){
		//alert("ddd");
		let loginCheck = "<%=midx%>";
		//alert(loginCheck);
		if (loginCheck == "" || loginCheck == "null" || loginCheck == null || loginCheck == 0){
			alert("로그인을 해주세요");
			return;
		}  				
		let cwriter = $("#cwriter").val();
		let ccontents = $("#ccontents").val();
		
		if (cwriter == ""){
			alert("작성자를 입력해주세요");
			$("#cwriter").focus();
			return;		
		}else if (ccontents ==""){
			alert("내용을 입력해주세요");
			$("#ccontents").focus();
			return;
		}
		
		$.ajax({
			type :  "post",    //전송방식
			url : "<%=request.getContextPath()%>/comment/commentWriteAction.aws",
			data : {"cwriter" : cwriter, 
					   "ccontents" : ccontents, 
					   "bidx" : "<%=bv.getBidx()%>",
					   "midx" : "<%=midx%>"
					   },
			dataType : "json",       // json타입은 문서에서  {"키값" : "value값","키값2":"value값2"}
			success : function(result){   //결과가 넘어와서 성공했을 받는 영역
				//alert("전송성공 테스트");			
				//var str ="("+result.value+")";			
				//alert(str);		
				if(result.value ==1){
					$("#ccontents").val("");
				}				
				$.boardCommentList();
			},
			error : function(){  //결과가 실패했을때 받는 영역						
				alert("전송실패");
			}			
		});			
	});	 	
 	
 	$("#more").click(function(){
 		$.boardCommentList();
 	});
});




</script> 

</head>
<body>
<header>
	<h2 class="mainTitle">글내용</h2>
</header>

 <article class="detailContents">
	<h2 class="contentTitle"><%=bv.getSubject() %> (조회수:<%=bv.getViewcnt() %>)</h2>
	<input type = "button" id = "btn" value = "추천 : <%=bv.getRecom()%>">
	<p class="write"><%=bv.getWriter() %> (<%=bv.getWriteday() %>)</p>
	<hr>
	<div class="content">
		<%=bv.getContents() %> 
	</div>
	<% if (bv.getFilename() == null || bv.getFilename().equals("") ) {}else{ %>	
	<img src="<%=request.getContextPath() %>/board/displayFile.aws?fileName=<%=bv.getFilename()%>">	
	<p>
	<a id="dUrl"  href="#"  class="fileDown">	
	첨부파일 다운로드</a>
	</p>		
	<%} %>
	
	
</article>
	
<div class="btnBox">
	<a class="btn aBtn" href="<%=request.getContextPath() %>/board/boardModify.aws?bidx=<%=bv.getBidx()%>">수정</a>
	<a class="btn aBtn" href="<%=request.getContextPath() %>/board/boardDelete.aws?bidx=<%=bv.getBidx() %>">삭제</a>
	<a class="btn aBtn" href="<%=request.getContextPath() %>/board/boardReply.aws?bidx=<%=bv.getBidx()%>">답변</a>
	<a class="btn aBtn" href="<%=request.getContextPath() %>/board/boardList.aws">목록</a>
</div>

<article class="commentContents">
	<form name="frm">
		<p class="commentWriter">
		<input type="text" id="cwriter" name="cwriter" value="<%=memberName %>"readonly="readonly" style="width:100px;">
		<!-- readonly 속성으로 읽기만 가능하게(수정불가) 만든다. --></p>	
		<input type="text" id="ccontents" name="ccontents"> <!-- id값 부여 name sql 데이터와 일치 -->
		<button type="button" id="cmtBtn" class="replyBtn">댓글쓰기</button> <!-- id값 부여 및 유효성 검사 함수 제거 -->
	</form> 
	
   <div id="commentListView"></div>
   <div id="morebtn" style= "text-align: center,line-height:50px;">
   <button type="button" id="more">더보기</button>
   <input type="text" id="block" value="1">
   </div>
   
</article>

</body>
</html>