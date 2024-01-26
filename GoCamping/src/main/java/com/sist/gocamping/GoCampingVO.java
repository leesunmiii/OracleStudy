package com.sist.gocamping;

public class GoCampingVO {
	private String title;
	private String subtitle;
	private String poster;
	private String loc;
	private String num;
	private String envir;
	private String category;
	private String season;
	private String open;
	private String homepage;
	private String reservation;
	private String facility;
	private String pic1;
	private String pic2;
	private String pic3;
	private String explain;
	private String customHeader;
	
	public String getReservation() {
		return reservation;
	}
	public void setReservation(String reservation) {
		this.reservation = reservation;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getEnvir() {
		return envir;
	}
	public void setEnvir(String envir) {
		this.envir = envir;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	public String getFacility() {
		return facility;
	}
	public void setFacility(String facility) {
		this.facility = facility;
	}
	public String getPic1() {
		return pic1;
	}
	public void setPic1(String pic1) {
		this.pic1 = pic1;
	}
	public String getPic2() {
		return pic2;
	}
	public void setPic2(String pic2) {
		this.pic2 = pic2;
	}
	public String getPic3() {
		return pic3;
	}
	public void setPic3(String pic3) {
		this.pic3 = pic3;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	public void setValueForHeader(String header, String value) {
        switch (header) {
            case "주소":
                this.setLoc(value);
                break;
            case "문의처":
                this.setNum(value);
                break;
            case "캠핑장 환경":
                this.setEnvir(value);
                break;
            case "캠핑장 유형":
                this.setCategory(value);
                break;
            case "운영기간":
                this.setSeason(value);
                break;
            case "운영일":
                this.setOpen(value);
                break;
            case "주변이용가능시설":
                this.setFacility(value);
                break;
           
        }
    }

    // 사용자 정의 헤더 값을 가져오는 메소드 (예시)
    public String getCustomHeader() {
        return customHeader;
    }

    // 사용자 정의 헤더 값을 설정하는 메소드 (예시)
    public void setCustomHeader(String customHeader) {
        this.customHeader = customHeader;
    }
	
	
}