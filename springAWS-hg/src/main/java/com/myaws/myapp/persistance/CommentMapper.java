package com.myaws.myapp.persistance;

import java.util.ArrayList;

import com.myaws.myapp.domain.CommentVo;

public interface CommentMapper {

	public ArrayList<CommentVo> commentSelectAll(int bidx);
	
//	public int boardTotalCount(SearchCriteria scri);
//	// hash���� �ѱ�� mybatis
//	
//	public int boardInsert(BoardVo bv);
//	
//	public int boardOriginbidxUpdate(int bidx);
//	// bidx���� ������Ʈ ġ�� �޼���
//	
//	public BoardVo boardSelectOne(int bidx);
//	public int boardViewCntUpdate(int bidx);
//	public int boardRecomUpdate(BoardVo bv); // service �� �ٸ���
//	public int boardDelete(HashMap hm); // �Ű������� 3���̱⶧���� -> impl���� hashmap ����
//
//	public int boardUpdate(BoardVo bv);
//	public int boardReplyInsert(BoardVo bv);
//	public int boardReplyUpdate(BoardVo bv);
}
