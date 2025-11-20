package com.mysite.sbb.controller;

import com.mysite.sbb.dto.HospitalListResponse;
import com.mysite.sbb.service.HospitalSearchService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HospitalController {

    private final HospitalSearchService service;

    public HospitalController(HospitalSearchService service) {
        this.service = service;
    }

   
    @GetMapping("/hospitals")
    public HospitalListResponse hospitals(
            @RequestParam(name = "lat") double lat,
            @RequestParam(name = "lng") double lng,
            @RequestParam(name = "radius", required = false) Integer radius
    ) {
        return service.searchNearby(lat, lng, radius);
    }

    @GetMapping("/hospitals/mock")
    public HospitalListResponse mock() {
        return new HospitalListResponse(java.util.List.of(
                new com.mysite.sbb.dto.HospitalDto("충북대 동물병원", "청주시 서원구 충대로 1", "043-000-0000", 36.6329, 127.4590),
                new com.mysite.sbb.dto.HospitalDto("행복동물병원", "청주시 서원구 00로 12", "043-111-2222", 36.6285, 127.4511),
                new com.mysite.sbb.dto.HospitalDto("러브펫의원", "청주시 서원구 00길 7", "043-333-4444", 36.6371, 127.4475)
        ));
    }
}
