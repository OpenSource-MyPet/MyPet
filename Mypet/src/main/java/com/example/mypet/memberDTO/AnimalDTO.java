package com.example.mypet.memberDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

//동물한마리에 대한 정보
@Data
@JsonIgnoreProperties(ignoreUnknown = true) //json에 우리가 안 쓰는 필드 있어도 무시(모르는애무시해)
public class AnimalDTO {

    //유기동물 고유 id (상세 페이지 url용)
    private String desertionNo;

    //큰 이미지 (상세/카드 상단 이미지)
    private String popfile1;		//대표
    private String popfile2;
    private String popfile3;
    private String popfile4; 		//예비

    //품종 관련
    //기본 kindFullNm사용하고 필요하면 구분해서 다른키 사용
    private String kindFullNm;   	//[강아지] 믹스견
    private String kindNm;        	//믹스견 / 진돗개 등 (품종)근데 '개'<처럼 축종처럼 뜨기도 함...
    private String upKindNm;      	//개 / 고양(축종)

    //기본 정보
    private String sexCd;         	//성별 (M/F/Q) 수컷암컷중성
    private String age;           	//나이 (2021(년생))
    private String colorCd;       	//색상
    private String weight;        	//체중 ex)1.3(Kg)
    private String neuterYn;		//중성화여부 (Y/N/U) 중성화완료미완료미상

    //상태 정보 (보호중/공고중)
    private String processState;

    //공고 정보
    private String noticeNo;      	//공고번호 (ex.경북-문경-2025-00041)
    private String noticeSdt;     	//공고시작일 (ex.20251113)
    private String noticeEdt;     	//공고종료일 (ex.20251124)

    //발견 정보
    private String happenDt;      	//발견일 (ex.20251113)
    private String happenPlace;   	//발견장소


    //보호소 관련 정보(상세페이지에서 띄울거)
    private String careNm;        	//보호소명
    private String careTel;       	//연락처
    private String careAddr;      	//보호소 주소(도로명주소)
    private String specialMark;   	//특이사항
    //쓸지 모르겠음
    private String careRegNo;     	//보호소 아이디
    private String careOwnerNm;   	//담당자


    //변경일/업데이트 시간
    private String updTm;         	//업데이트 시간


    //나이 가공
    //현재년도 - 출생년도 = 나이
    public String getAgeDisplay() {
        if (age == null || age.isBlank()) {
            return "나이 정보 없음";
        }

        try {
            //ex. 2024(년생)-> 첫 4자리만 잘라내서 출생년도숫자로
            String yearStr = age.substring(0, 4);
            int birthYear = Integer.parseInt(yearStr);

            int currentYear = java.time.LocalDate.now().getYear(); //시스템 현재날짜에서 현재년도 가져와
            int diff = currentYear - birthYear; //실제나이

            if (diff <= 0) {
                return "1살 미만";
            } else {
                return diff + "살 추정";
            }
        } catch (Exception e) {
            return age; //형식 안 맞으면 원본 그대로 ex. 3개월 이런식이면 substring(0,4)안됨 -> 원본 반환
        }
    }

    //성별 가공
    public String getSexDisplay() {
        if (sexCd == null) return "미상";

        return switch (sexCd) {
            case "M" -> "수컷";
            case "F" -> "암컷";
            default -> "미상"; 			//Q포함
        };
    }

    //중성화여부
    public String getNeuterDisplay() {
        if (neuterYn == null) return "미상";

        return switch (neuterYn) {
            case "Y" -> "중성화 완료";
            case "N" -> "중성화 미완료";
            default -> "미상";			//U포함
        };
    }



    //지역
    /* 	주소 전체: "경상남도 합천군 합천읍 어쩌구 ..." 이런식이면
    	지역 요약: "경상남도 합천군" 이렇게 띄우기 */
    public String getRegionDisplay() {
        if (careAddr == null || careAddr.isBlank()) {
            return "지역 정보 없음";
        }

        String[] parts = careAddr.split(" "); //주소를 공백 기준으로 분리
        if (parts.length >= 2) {
            return parts[0] + " " + parts[1];  //도 + 시/군 앞에 두개까지만 띄우게
        }
        return careAddr; //예외의 경우 그냥 원본 그대로 반환
    }



    //공고기간
    //"20251113" ~ "20251124" → "2025.11.13 ~ 2025.11.24"
    public String getNoticePeriodDisplay() {
        if (noticeSdt == null || noticeEdt == null) {
            return "공고 기간 정보 없음";
        }

        try {
            String start = noticeSdt.substring(0,4) + "." + noticeSdt.substring(4,6) + "." + noticeSdt.substring(6,8);
            String end   = noticeEdt.substring(0,4) + "." + noticeEdt.substring(4,6) + "." + noticeEdt.substring(6,8);
            return start + " ~ " + end;
        } catch (Exception e) {
            return noticeSdt + " ~ " + noticeEdt;   //포맷팅 실패하면 원본 그대로
        }
    }



}