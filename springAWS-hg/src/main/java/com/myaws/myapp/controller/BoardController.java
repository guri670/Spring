package com.myaws.myapp.controller;

import java.net.InetAddress;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myaws.myapp.domain.BoardVo;
import com.myaws.myapp.domain.PageMaker;
import com.myaws.myapp.domain.SearchCriteria;
import com.myaws.myapp.service.BoardService;
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

	@RequestMapping(value = "boardWriteAction.aws")
	public String boardWriteAction(
			BoardVo bv, 
			@RequestParam("filename") MultipartFile filename,
			RedirectAttributes rttr,
			HttpServletRequest request
			) throws Exception {
		
		    MultipartFile file = filename;    
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
						
			String path="";
			int value = boardService.boardInsert(bv);
			
			if (value == 2) {
				path = "redirect:/board/boardList.aws";
			} else {
				rttr.addFlashAttribute("msg","입력이잘못되었습니다");
				path = "redirect:/board/boardWrite.aws";
			}
	
		return path;

	}
	@RequestMapping(value = "boardContents.aws")
	public String boardContents(@RequestParam("bidx") int bidx,  Model model) {
		
		BoardVo bv = boardService.boardSelectOne(bidx);
		model.addAttribute("bv", bv);

		String path = "WEB-INF/board/boardContents";
		return path;
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
