package com.myaws.myapp.domain;

import org.springframework.stereotype.Component;

@Component // 컴포넌트 클래스 등록 bean처럼 사용

public class PageMaker {
	
	private int displayPageNum = 10;
	private int startPage; 
	private int endPage; 
	private int totalCnt; 
	
	private boolean prev; 
	private boolean next; 


	private SearchCriteria scri;

	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(int totalCnt) { 
		this.totalCnt = totalCnt;
		calcData(); 
		
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}
	
	public SearchCriteria getScri() {
		return scri;
	}

	public void setScri(SearchCriteria scri) {
		this.scri = scri;
	}
	
	public void calcData() {
		endPage = (int)(Math.ceil(scri.getPage()/(double)displayPageNum)*displayPageNum);
		
		startPage = (endPage-displayPageNum)+1;
		
		int tempEndPage = (int)(Math.ceil(totalCnt/(double)scri.getPerPageNum()));
		
		if(endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		
		prev = (startPage ==1 ? false : true); 
		next = (endPage*scri.getPerPageNum() >= totalCnt ? false : true );
		
	}
}
