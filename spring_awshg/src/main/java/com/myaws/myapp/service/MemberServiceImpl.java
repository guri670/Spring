package com.myaws.myapp.service;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myaws.myapp.domain.MemberVo;
import com.myaws.myapp.persistance.MemberMapper;

import netscape.javascript.JSObject;

@Service
public class MemberServiceImpl implements MemberService{

	private MemberMapper mm;
	
	@Autowired
	public MemberServiceImpl(SqlSession sqlSession) {
		this.mm = sqlSession.getMapper(MemberMapper.class);
	}
	
	@Override
	public int memberInsert(MemberVo mv) {
		// mybatis¿Í ¿¬µ¿
		int value = mm.memberInsert(mv);
		
		return value;
	}

	@Override
	public int memberIdCheck(String memberId) {
			int value = mm.memberIdCheck(memberId);		
		return value;
	}

	@Override
	public MemberVo memberLoginCheck(String memberId) {
		// TODO Auto-generated method stub
		return null;
	}
}

