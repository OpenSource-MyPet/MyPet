package com.example.mypet.service;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class ChatbotService {
    public String getprompt(String message) throws JSONException {
        URI uri = UriComponentsBuilder
                .fromUriString("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent")
                .encode()
                .build()
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        String key = "AIzaSyDFMoUp5UWJgIturOVdOFfiPBg8hPqDuxI";
        String previous_key = "AIzaSyDTwiL9aooGEpwyNI0qLO1z2WrowXaBSkg";
        headers.set("x-goog-api-key", key);
        headers.setContentType(MediaType.APPLICATION_JSON);
//        String prePrompt = " 모든 응답은 100자 이내로 요약해서 말해줘";
        String content = message ;
        System.out.println( "contents in Service : " + content);

        JSONObject sysInstruction = new JSONObject();
        sysInstruction.put("role", "system");
        JSONArray sysArray = new JSONArray();

        JSONObject systext = new JSONObject();
        systext.put("text", "모든 답변은 100자 이내로 간결하고 부드럽게 해줘");
        sysArray.put(systext);

        sysInstruction.put("parts", sysArray);


        JSONObject text = new JSONObject(); // { } 생성
        text.put("text", content);  // 생성한 {}에 값 추가, {text : ...}
        JSONArray parts = new JSONArray(); // [] 생성
        parts.put(text); // [{text : ...} ]
        JSONObject wrapper = new JSONObject();
        wrapper.put("parts", parts); // parts : { [{text : ...} ] }
        JSONArray contents = new JSONArray(); // []
        contents.put(wrapper); // [parts : { [{text : ...}] }]
        JSONObject prompt = new JSONObject(); // {}

        prompt.put("system_instruction", sysInstruction);
        prompt.put("contents", contents); // contents : { [ parts : { [{text : ...} ] }] }

        HttpEntity<String> entity = new HttpEntity<>(prompt.toString(), headers);
        RestTemplate rest = new RestTemplate() ;
        int attempt = 3;
        int delay = 2000;
        for(int i = 0 ; i < attempt ; i++){
            try{
                ResponseEntity<String> response = rest.exchange(uri, HttpMethod.POST, entity, String.class);
                JSONObject parser = new JSONObject(response.getBody());
                String texts = parser.getJSONArray("candidates").getJSONObject(0).getJSONObject("content").getJSONArray("parts").getJSONObject(0).getString("text");

                System.out.println(texts);
                return texts;
            }catch(HttpServerErrorException e){
                if(e.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE){
                    System.out.println("현재 Gemini 과부하 상태, 시도 횟수 :" + i);
                    if(i == attempt-1){
                        return "AI 서버가 현재 과부화 상태입니다 다음에 다시 시도해주세요";
                    }
                    try{
                        Thread.sleep(delay);
                    }catch(InterruptedException interruptedError){}
                }
                else{
                    return "AI 서버 오류입니다 다음에 다시 시도해주세요";
                }

            }
        }
        return "AI 오류입니다 다음에 다시 시도해주세요";

    }
}
