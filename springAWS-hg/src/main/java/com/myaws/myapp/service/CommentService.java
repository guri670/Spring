package com.myaws.myapp.service;

import java.util.ArrayList;

import com.myaws.myapp.domain.CommentVo;


public interface CommentService {

	public ArrayList<CommentVo> commentSelectAll(int bidx);
}
