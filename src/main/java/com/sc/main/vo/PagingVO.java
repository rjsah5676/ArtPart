package com.sc.main.vo;

public class PagingVO {
	private int nowPage = 1; //����������
	private int onePageRecord = 8; //���������� ǥ���� ���ڵ� ��
	
	private int totalRecord; //�ѷ��ڵ� ��
	private int totalPage; //�������� ��
	private int offset;	//���÷��ڵ��� ������ġ
	
	//��������ȣ
	private int onePageCount = 5;
	private int startPageNum = 1;
	
	//�˻���
	private String searchKey;
	private String searchWord;
	private int searchConcept;
	
	//���ı���
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
		
		//offset�� 0���� ����
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
		
		//�������� ��
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
