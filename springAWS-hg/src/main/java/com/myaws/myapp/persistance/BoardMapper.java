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
	public int boardViewCntUpdate(int bidx);
	public int boardRecomUpdate(BoardVo bv); // service 는 다르다
	public int boardDelete(HashMap hm); // 매개변수가 3개이기때문에 -> impl에서 hashmap 설정

	public int boardUpdate(BoardVo bv);
	public int boardReplyInsert(BoardVo bv);
	public int boardReplyUpdate(BoardVo bv);
}
