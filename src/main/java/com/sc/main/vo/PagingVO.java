package com.sc.main.vo;

public class PagingVO {
	private int nowPage = 1; //현재페이지
	private int onePageRecord = 8; //한페이지에 표시할 레코드 수
	
	private int totalRecord; //총레코드 수
	private int totalPage; //총페이지 수
	private int offset;	//선택레코드의 시작위치
	
	//페이지번호
	private int onePageCount = 5;
	private int startPageNum = 1;
	
	//검색어
	private String searchKey;
	private String searchWord;
	private int searchConcept;
	
	//정렬기준
	//private String hit;
	//private String rating;
	private String searchHR;
	
	public String getSearchHR() {
		return searchHR;
	}
	public void setSearchHR(String searchHR) {
		this.searchHR = searchHR;
	}
	
	private String userid;
	
	@Override
	public String toString() {
		return "PagingVO [nowPage=" + nowPage + ", onePageRecord=" + onePageRecord + ", totalRecord=" + totalRecord
				+ ", totalPage=" + totalPage + ", offset=" + offset + ", onePageCount=" + onePageCount
				+ ", startPageNum=" + startPageNum + ", searchKey=" + searchKey + ", searchWord=" + searchWord
				+ ", searchConcept=" + searchConcept + ", searchHR=" + searchHR + "]";
	}
	public int getNowPage() {
		return nowPage;
	}
	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
		
		//offset은 0부터 시작
		offset = (nowPage-1) * onePageRecord;
		startPageNum = (nowPage-1)/onePageCount*onePageCount+1;
		
	}
	public int getOnePageRecord() {
		return onePageRecord;
	}
	public void setOnePageRecord(int onePageRecord) {
		this.onePageRecord = onePageRecord;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		
		//총페이지 수
		totalPage = (totalRecord%onePageRecord == 0)? totalRecord/onePageRecord:totalRecord/onePageRecord+1;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getOnePageCount() {
		return onePageCount;
	}
	public void setOnePageCount(int onePageCount) {
		this.onePageCount = onePageCount;
	}
	public int getStartPageNum() {
		return startPageNum;
	}
	public void setStartPageNum(int startPageNum) {
		this.startPageNum = startPageNum;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public String getSearchWord() {
		return searchWord;
	}
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	public int getSearchConcept() {
		return searchConcept;
	}
	public void setSearchConcept(int searchConcept) {
		this.searchConcept = searchConcept;
	}
	public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
		
//	public String getHit() {
//		return hit;
//	}
//	public void setHit(String hit) {
//		this.hit = hit;
//	}
//	public String getRating() {
//		return rating;
//	}
//	public void setRating(String rating) {
//		this.rating = rating;
//	}
			
}
