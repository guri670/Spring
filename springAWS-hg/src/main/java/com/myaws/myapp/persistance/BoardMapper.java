package com.myaws.myapp.persistance;

import java.util.ArrayList;
import java.util.HashMap;

import com.myaws.myapp.domain.BoardVo;
import com.myaws.myapp.domain.MemberVo;
import com.myaws.myapp.domain.SearchCriteria;

public interface BoardMapper {

	public ArrayList<BoardVo> boardSelectAll(HashMap<String,Object> hm);
	
	public int boardTotalCount(SearchCriteria scri);
	// hash맵을 넘기는 mybatis
	
	public int boardInsert(BoardVo bv);
	
	public int boardOriginbidxUpdate(int bidx);
	// bidx값을 업데이트 치는 메서드
	
	public BoardVo boardSelectOne(int bidx);
//	public BoardVo boardViewCnt(int bidx);
}
