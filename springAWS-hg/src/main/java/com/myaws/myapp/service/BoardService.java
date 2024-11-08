package com.myaws.myapp.service;

import java.util.ArrayList;

import com.myaws.myapp.domain.BoardVo;
import com.myaws.myapp.domain.SearchCriteria;

public interface BoardService {
	
	public ArrayList<BoardVo> boardSelectAll(SearchCriteria scri);
	public int boardTotalCount(SearchCriteria scri);
	public int boardInsert(BoardVo bv);
	
	public BoardVo boardSelectOne(int bidx);
	
	public int boardViewCntUpdate(int bidx);
	public int boardRecomUpdate(int bidx);
	public int boardDelete(int bidx, int midx, String password); // update -> 되면 1 안돼면 0으로 반환하기때문에 int형
}
