package com.myaws.myapp.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myaws.myapp.domain.BoardVo;
import com.myaws.myapp.domain.PageMaker;
import com.myaws.myapp.domain.SearchCriteria;
import com.myaws.myapp.service.BoardService;
import com.myaws.myapp.util.MediaUtils;
import com.myaws.myapp.util.UploadFileUtiles;

@Controller
@RequestMapping(value = "/board/")
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Autowired(required = false)
	private BoardService boardService;

	@Autowired(required = false)
	private PageMaker pm;

	@Resource(name = "uploadPath")
	private String uploadPath;

	@RequestMapping(value = "boardList.aws")
	public String boardList(SearchCriteria scri, Model model) {
		// logger.info("boardList에 들어옴");

		int cnt = boardService.boardTotalCount(scri);
		pm.setScri(scri);
		pm.setTotalCnt(cnt);

		ArrayList<BoardVo> blist = boardService.boardSelectAll(scri);

		model.addAttribute("blist", blist);
		model.addAttribute("pm", pm);

		String path = "WEB-INF/board/boardList";
		return path;
	}

	@RequestMapping(value = "boardWrite.aws")
	public String boardWrite() {
//		logger.info("boardWrite에 들어옴"); 

		String path = "WEB-INF/board/boardWrite";
		return path;
	}
	
	@RequestMapping(value = "boardContents.aws")
	public String boardContents(@RequestParam("bidx") int bidx, Model model) {
		
		boardService.boardViewCntUpdate(bidx);
		BoardVo bv = boardService.boardSelectOne(bidx);
		model.addAttribute("bv", bv);

		String path = "WEB-INF/board/boardContents";
		return path;
	}

	@RequestMapping(value = "boardWriteAction.aws")
	public String boardWriteAction(BoardVo bv, 	
			@RequestParam("attachfile") MultipartFile attachfile,		
			HttpServletRequest request,
			RedirectAttributes rttr
			) throws Exception {	

		MultipartFile file = attachfile;
		String uploadedFileName = "";

		if (!file.getOriginalFilename().equals("")) {
			uploadedFileName = UploadFileUtiles.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
		}
		String midx = request.getSession().getAttribute("midx").toString();
		int midx_int = Integer.parseInt(midx);

		String ip = getUserIp(request);

		bv.setUploadedFilename(uploadedFileName);
		bv.setMidx(midx_int);
		bv.setIp(ip);

		String path = "";
		int value = boardService.boardInsert(bv);

		if (value == 2) {
			path = "redirect:/board/boardList.aws";
		} else {
			rttr.addFlashAttribute("msg", "입력이잘못되었습니다");
			path = "redirect:/board/boardWrite.aws";
		}

		return path;

	}
	@ResponseBody // (JSON 객체를 받아온다 body로)
	@RequestMapping(value="boardRecom.aws", method = RequestMethod.GET)
	public JSONObject boardRecom(@RequestParam("bidx") int bidx){  //json형식으로 넘겨야한다 모를때 타입을 ? 를 넣어도된다.

		int value = boardService.boardRecomUpdate(bidx);
		
		JSONObject js = new JSONObject();
		js.put("recom", value);
		
		return js ; // json 객체가 나와야한다
	}
	
	@RequestMapping(value="boardDeleteAction.aws", method = RequestMethod.POST)
	public String boardDeleteAction(
			@RequestParam("bidx") int bidx, 
			@RequestParam("password") String password,
			HttpSession session){  //json형식으로 넘겨야한다 모를때 타입을 ? 를 넣어도된다.

		int midx = Integer.parseInt(session.getAttribute("midx").toString());
		boardService.boardDelete(bidx,midx,password);
		
		String path ="redirect:/board/boardList.aws";
		
		return path ; 
	}
	
	@RequestMapping(value="boardDelete.aws")
	public String boardDelete(@RequestParam("bidx") int bidx, Model model ){  
		
		model.addAttribute("bidx",bidx);
		String path ="WEB-INF/board/boardDelete";
		
		return path ; 
	}
	
	@RequestMapping(value = "displayFile.aws", method = RequestMethod.GET)
	public ResponseEntity<byte[]> displayFile(@RequestParam("fileName") String fileName,
			@RequestParam(value = "down", defaultValue = "0") int down) {

		ResponseEntity<byte[]> entity = null;
		InputStream in = null;

		try {
			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
			MediaType mType = MediaUtils.getMediaType(formatName);

			HttpHeaders headers = new HttpHeaders();

			in = new FileInputStream(uploadPath + fileName);

			if (mType != null) {
				if (down == 1) {
					fileName = fileName.substring(fileName.indexOf("_") + 1);
					headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					headers.add("Content-Disposition",
							"attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");
				} else {
					headers.setContentType(mType);
				}
			} else {
				fileName = fileName.substring(fileName.indexOf("_") + 1);
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.add("Content-Disposition",
						"attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");
			}
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);

		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return entity;
	}
	
	
	
	

	public String getUserIp(HttpServletRequest request) throws Exception {
		String ip = null;
		ip = request.getHeader("X-Forwarded-For");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-RealIP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("REMOTE_ADDR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		if (ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127.0.0.1")) {
			InetAddress address = InetAddress.getLocalHost();
			ip = address.getHostAddress();
		}

		return ip;
	}

}
