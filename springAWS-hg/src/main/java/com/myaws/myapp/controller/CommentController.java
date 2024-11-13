package com.myaws.myapp.controller;

import java.net.InetAddress;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myaws.myapp.domain.CommentVo;
import com.myaws.myapp.service.CommentService;
import com.myaws.myapp.util.UserIp;

@RestController // ResponseBody가 있는걸로 간주한다.
@RequestMapping(value="/comment")
public class CommentController {
	
	@Autowired
	CommentService commentService;
	
	@Autowired(required = false) // null도 포함
	private UserIp userIp;
	
	@RequestMapping(value="/{bidx}/commentList.aws")
	public JSONObject commentList(@PathVariable("bidx") int bidx) {
		
		JSONObject js = new JSONObject();
		
		ArrayList<CommentVo> clist = commentService.commentSelectAll(bidx);
		js.put("clist", clist);
		
		
		return js;
	}
	
	@RequestMapping(value="/commentWriteAction.aws", method=RequestMethod.POST)
	// 입력된 방식을 넘긴다 POST방식
	public JSONObject commentWriteAction(
			CommentVo cv, 
			HttpServletRequest request
			) throws Exception {
		
		
		cv.setCip(userIp.getUserIp(request));
		JSONObject js = new JSONObject();
		int value = commentService.commentInsert(cv);
		js.put("value", value);
		
		return js;
	}
	
	@RequestMapping(value="/{cidx}/commentDeleteAction.aws") //GET 방식
	public JSONObject commentDeleteAction(
			@PathVariable("cidx") int cidx,
			HttpServletRequest request,
			CommentVo cv
			) throws Exception {
		
		int midx = Integer.parseInt(request.getSession().getAttribute("midx").toString()); // 누가 쓴것인지 확인
		cv.setMidx(midx);
		cv.setCidx(cidx);
		cv.setCip(userIp.getUserIp(request));
		
		int value = commentService.commentDelete(cv);
		JSONObject js = new JSONObject();
		js.put("value",value);
		
		return js;
	}
	
	
}
