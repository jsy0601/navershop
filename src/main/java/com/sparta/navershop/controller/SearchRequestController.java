package com.sparta.navershop.controller;

import com.sparta.navershop.dto.ItemDto;
import com.sparta.navershop.models.User;
import com.sparta.navershop.models.UserTime;
import com.sparta.navershop.repository.UserTimeRepository;
import com.sparta.navershop.security.UserDetailsImpl;
import com.sparta.navershop.utils.NaverShopSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // JSON으로 응답함을 선언합니다.
public class SearchRequestController {

    private final NaverShopSearch naverShopSearch;

    @Autowired
    public SearchRequestController(NaverShopSearch naverShopSearch) {
        this.naverShopSearch = naverShopSearch;
    }

    @GetMapping("/api/search")
    public List<ItemDto> getItems(@RequestParam String query) {
        String resultString = naverShopSearch.search(query);
        return naverShopSearch.fromJSONtoItems(resultString);
    }
}
