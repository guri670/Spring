package com.myaws.myapp.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myaws.myapp.domain.BoardVo;
import com.myaws.myapp.domain.SearchCriteria;
import com.myaws.myapp.persistance.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService{
	private BoardMapper bm;
	 
	@Autowired // �̷��Ե� ����� �����ϴ�.
	public BoardServiceImpl(SqlSession sqlSession) {
		this.bm = sqlSession.getMapper(BoardMapper.class); 
	}

	@Override
	public ArrayList<BoardVo> boardSelectAll(SearchCriteria scri) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		hm.put("startPageNum",(scri.getPage()-1)*scri.getPerPageNum());
		hm.put("perPageNum", scri.getPerPageNum());
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());	
		
		ArrayList<BoardVo> blist = bm.boardSelectAll(hm);
		
		
		return blist;
	}

	@Override
	public int boardTotalCount(SearchCriteria scri) {
		

		int cnt = bm.boardTotalCount(scri);

		return cnt;
	}

	@Override
	@Transactional
	public int boardInsert(BoardVo bv) {
		
		int value = bm.boardInsert(bv);
		int maxBidx = bv.getBidx();
		int value2 = bm.boardOriginbidxUpdate(maxBidx);
		
		return value + value2;
	}

	@Override
	public BoardVo boardSelectOne(int bidx) {
		BoardVo bv = bm.boardSelectOne(bidx);
		
		return bv;
	}

//	@Override
//	public BoardVo boardViewCnt(int bidx) {
//		BoardVo bv = bm.boardViewCnt(bidx);
//		
//		return bv;
//	}

}
