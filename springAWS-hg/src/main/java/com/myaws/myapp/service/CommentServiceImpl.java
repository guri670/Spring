package com.myaws.myapp.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myaws.myapp.domain.CommentVo;
import com.myaws.myapp.persistance.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService{
	private CommentMapper cm;
	 
	@Autowired // 이렇게도 사용이 가능하다.
	public CommentServiceImpl(SqlSession sqlSession) {
		this.cm = sqlSession.getMapper(CommentMapper.class); 
	}

	@Override
	public ArrayList<CommentVo> commentSelectAll(int bidx) {
		

		ArrayList<CommentVo> clist = cm.commentSelectAll(bidx);
		
		
		return clist;
	}
}
//	@Override
//	public int boardTotalCount(SearchCriteria scri) {
//		
//
//		int cnt = bm.boardTotalCount(scri);
//
//		return cnt;
//	}
//
//	@Override
//	@Transactional
//	public int boardInsert(BoardVo bv) {
//		
//		int value = bm.boardInsert(bv);
//		int maxBidx = bv.getBidx();
//		int value2 = bm.boardOriginbidxUpdate(maxBidx);
//		
//		return value + value2;
//	}
//
//	@Override
//	public BoardVo boardSelectOne(int bidx) {
//		BoardVo bv = bm.boardSelectOne(bidx);
//		
//		return bv;
//	}
//
//	@Override
//	public int boardViewCntUpdate(int bidx) {
//		int cnt = bm.boardViewCntUpdate(bidx);
//		
//		return cnt;
//	}
//
//	@Override
//	public int boardRecomUpdate(int bidx) {
//	
//		BoardVo bv = new BoardVo();
//		bv.setBidx(bidx);
//		int cnt = bm.boardRecomUpdate(bv);
//		int recom = bv.getRecom();
//		
//		return recom;
//	}
//
//	@Override
//	public int boardDelete(int bidx, int midx, String password) {
//		
//		HashMap<String,Object> hm = new HashMap<String,Object>();
//		hm.put("bidx", bidx);
//		hm.put("midx", midx);
//		hm.put("password", password);
//		// bidx, midx password 매개변수를 hashmap에 담는다
//		
//		int cnt = bm.boardDelete(hm);
//		
//		return cnt;
//	}
//
//	@Override
//	public int boardUpdate(BoardVo bv) {
//
//		int value = bm.boardUpdate(bv);
//		
//		return value;
//	}
//	
//	@Transactional
//	@Override
//	public int boardReply(BoardVo bv) {
//		
//		int value = bm.boardReplyUpdate(bv);
//		System.out.println("value"+value);
//		
//		int value2 = bm.boardReplyInsert(bv);
//		
//		int maxBidx = bv.getBidx();
//		return maxBidx;
//	}
//
