package com.mysite.sbb.dto;

import java.util.List;

public class HospitalListResponse {
    private List<HospitalDto> items;

    public HospitalListResponse() {}
    public HospitalListResponse(List<HospitalDto> items) { this.items = items; }

    public List<HospitalDto> getItems() { return items; }
    public void setItems(List<HospitalDto> items) { this.items = items; }
}
