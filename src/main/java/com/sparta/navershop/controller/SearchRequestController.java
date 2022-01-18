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

    private final UserTimeRepository userTimeRepository;

    @Autowired
    public SearchRequestController(NaverShopSearch naverShopSearch, UserTimeRepository userTimeRepository) {
        this.naverShopSearch = naverShopSearch;
        this.userTimeRepository = userTimeRepository;
    }

    @GetMapping("/api/search")
    public List<ItemDto> getItems(@RequestParam String query, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 측정 시작 시간
        long startTime = System.currentTimeMillis();

        try {
            String resultString = naverShopSearch.search(query);
            return naverShopSearch.fromJSONtoItems(resultString);
        } finally {
            // 측정 종료 시간
            long endTime = System.currentTimeMillis();
            // 수행시간 = 종료 시간 - 시작 시간
            long runTime = endTime - startTime;
            // 로그인 회원 정보
            User loginUser = userDetails.getUser();

            // 수행시간 및 DB 에 기록
            UserTime userTime = userTimeRepository.findByUser(loginUser);
            if (userTime != null) {
                // 로그인 회원의 기록이 있으면
                long totalTime = userTime.getTotalTime();
                totalTime = totalTime + runTime;
                userTime.updateTotalTime(totalTime);
            } else {
                // 로그인 회원의 기록이 없으면
                userTime = new UserTime(loginUser, runTime);
            }

            System.out.println("[User Time] User: " + userTime.getUser().getUsername() + ", Total Time: " + userTime.getTotalTime() + " ms");
            userTimeRepository.save(userTime);
        }
    }
}
