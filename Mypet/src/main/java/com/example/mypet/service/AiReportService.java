package com.example.mypet.service;

import org.apache.logging.log4j.util.Base64Util;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import org.springframework.http.*;


@Service
public class AiReportService {
    public String getBase64(MultipartFile image) {
        try{
            byte[] imageByte = image.getBytes();
            return Base64.getEncoder().encodeToString(imageByte);
        }catch(Exception e){
            e.printStackTrace();
            return "none";
        }

    }

    public Map<String, String> getPrompt(String base64, String symptom, String type, String contentType) {

        String prompt = "너는 AI 수의사야 질문에 응답 하지말고 해당 이미지와 다음의 증상에 대한 설명을 보고 진단서 형식에 맞춰서 수치와 퍼센트를 포함하는 예상 증상 진단서를 만들어 제목으로는 [MyPet 맞춤형 AI 예상 증상 진단서]으로 하고 진단서에 날짜는 포함하지말고 Markdown 형식으로 작성하지 말고 실제 진단서 양식으로 로마 숫자와 일반 숫자를 활용해서 구분 나눠가며 자세하게 작성해줘, 키우는 동물: "+ type + "증상: " + symptom +
                "그리고 JSON object 형태로 리턴할건데 앞의 요구에 따라 만들어진 예상 진단서는 prompt 필드로, 예상 진단서의 요약본은 summarize 필드로 구성하고 요약본은 한눈에 보기 쉽도록 180자 내에서 최소 150자 이상으로 자세하게 각 항목들을 숫자로 열거하며 구성해";

        Map<String, Object> inline = new HashMap<>();
        inline.put("mime_type", contentType);
        inline.put("data", base64);

        List<Map<String, Object>> part = new ArrayList<>();

        Map<String, Object> imgpart = new HashMap<>();
        imgpart.put("inline_data", inline);
        part.add(imgpart);

        Map<String, Object> text = new HashMap<>();
        text.put("text", prompt);
        part.add(text);

        Map<String, Object> content = new HashMap<>();
        content.put("parts", part);

        List<Map<String, Object>> contents = new ArrayList<>();
        contents.add(content);

        Map<String, Object> request = new HashMap<>();
        request.put("contents", contents);

        String key = "AIzaSyBdPuAaiiKfkApQog_uAZLi_TE26Y7zrEk";

        URI uri = UriComponentsBuilder
                .fromUriString("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent")
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-goog-api-key", key);
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate rest = new RestTemplate();

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        int attempt = 3 ;
        int delay = 2000;

        for(int i = 0 ; i < attempt ; i++){
            try{
                ResponseEntity<String> response = rest.exchange(uri, HttpMethod.POST, entity, String.class);
                System.out.println(response.getBody());
                JSONObject parser = new JSONObject(response.getBody());
                String texts = parser.getJSONArray("candidates").getJSONObject(0).getJSONObject("content").getJSONArray("parts").getJSONObject(0).getString("text");
//            System.out.println("prompt's text is " + texts);
                texts = texts.replace("```json", "").replace("```", "").trim();
                JSONObject innerparser = new JSONObject(texts);
                String diagnosis = innerparser.getString("prompt");
                String summarize = innerparser.getString("summarize");
                Map<String, String> res = new HashMap<>();
                res.put("prompt", diagnosis);
                res.put("summarize", summarize);
                return res;
            }catch(HttpServerErrorException e){
                if(e.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE){
                    if(i == attempt-1){
                        Map<String, String> res = new HashMap<>();
                        res.put("prompt", "AI 서버가 현재 과부화 상태입니다 다음에 다시 시도해주세요");
                        res.put("summarize", "AI 서버가 현재 과부화 상태입니다 다음에 다시 시도해주세요");
                        return res;
                    }
                    try{
                        Thread.sleep(delay);
                    }catch(InterruptedException interruptedError){}
                }
                else{
                    Map<String, String> res = new HashMap<>();
                    res.put("prompt", "AI 서버 오류입니다 다음에 다시 시도해주세요");
                    res.put("summarize", "AI 서버 오류입니다 다음에 다시 시도해주세요");
                    return res;
                }

            }catch(JSONException e){
                e.printStackTrace();
            }
        }
        Map<String, String> res = new HashMap<>();
        res.put("prompt", "AI 오류입니다 다음에 다시 시도해주세요");
        res.put("summarize", "AI 오류입니다 다음에 다시 시도해주세요");
        return res;



//        try{
//            ResponseEntity<String> response = rest.exchange(uri, HttpMethod.POST, entity, String.class);
//            System.out.println(response.getBody());
//            JSONObject parser = new JSONObject(response.getBody());
//            String texts = parser.getJSONArray("candidates").getJSONObject(0).getJSONObject("content").getJSONArray("parts").getJSONObject(0).getString("text");
////            System.out.println("prompt's text is " + texts);
//            texts = texts.replace("```json", "").replace("```", "").trim();
//            JSONObject innerparser = new JSONObject(texts);
//            String diagnosis = innerparser.getString("prompt");
//            String summarize = innerparser.getString("summarize");
//            Map<String, String> res = new HashMap<>();
//            res.put("prompt", diagnosis);
//            res.put("summarize", summarize);
//            return res;
//        } catch (Exception e) {
//            e.printStackTrace();
//            Map<String, String> res = new HashMap<>();
//            String error = "AI가 현재 과부화 상태입니다 나중에 다시 시도해주십시오";
//            res.put("prompt", error);
//            res.put("summarize", error);
//            return res;
//        }



        
    }
}
