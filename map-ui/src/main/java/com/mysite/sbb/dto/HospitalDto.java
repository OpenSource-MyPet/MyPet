package com.mysite.sbb.dto;

public class HospitalDto {
    private String name;
    private String address;
    private String phone;
    private Double lat; // 위도
    private Double lng; // 경도

    public HospitalDto() {}
    public HospitalDto(String name, String address, String phone, Double lat, Double lng) {
        this.name = name; this.address = address; this.phone = phone; this.lat = lat; this.lng = lng;
    }

    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public Double getLat() { return lat; }
    public Double getLng() { return lng; }

    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setLat(Double lat) { this.lat = lat; }
    public void setLng(Double lng) { this.lng = lng; }
}
