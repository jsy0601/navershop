package com.sparta.navershop.utils;

import com.sparta.navershop.models.ItemDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class NaverShopSearch {
    public String search(String query) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", "9dJjpuKBFhaZNYrqu3PP");
        headers.add("X-Naver-Client-Secret", "mndLlzJ9Cp");

        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
                sb.append("  \"username\": \"test\",\n");
                        sb.append("  \"contents\": \"i\"\n");
                                sb.append("}");
        String body = sb.toString();

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("https://openapi.naver.com/v1/search/shop.json?query=" + query, HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
        System.out.println("Response status: " + status);
        System.out.println(response);

        return response;
    }

    public List<ItemDto> fromJSONtoItems(String result) {
        JSONObject rjson = new JSONObject(result);
        JSONArray items = rjson.getJSONArray("items");

        List<ItemDto> itemDtoList = new ArrayList<>();

        for (int i=0; i<items.length(); i++) {
            JSONObject itemJson = (JSONObject) items.get(i);
            ItemDto itemDto = new ItemDto(itemJson);
            itemDtoList.add(itemDto);
        }
        return itemDtoList;
    }
}