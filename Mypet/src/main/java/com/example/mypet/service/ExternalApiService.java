package com.example.mypet.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class ExternalApiService {
    private final ObjectMapper objectMapper;

    public ExternalApiService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ResponseEntity<String> test1()  {
        URI uri = UriComponentsBuilder
                .fromUriString("https://open.assembly.go.kr/portal/openapi/nzmimeepazxkubdpn")
                .queryParam("Type","json")
                .queryParam("AGE", 22)
                .encode()
                .build()
                .toUri();

        System.out.println(uri);
        ObjectMapper mapper = new ObjectMapper();


        RestTemplate rest = new RestTemplate();

        ResponseEntity<String> response = rest.getForEntity(uri, String.class);
//        System.out.println(response.getBody().get("BILL_NAME"));

        try{
            JsonNode data = mapper.readTree(response.getBody());
            String Bill_no = data.get("nzmimeepazxkubdpn")
                    .get(1)
                    .get("row").get(0)
                    .get("BILL_NO")
                    .asText();
            System.out.println("BILL_NO : " + Bill_no);
            return new ResponseEntity<String>(data.toString(), HttpStatus.ACCEPTED);
        }catch(Exception e){
            e.printStackTrace();
        }

        return response;
    }
}
