package com.hospitalreview2.conroller;

import com.hospitalreview2.domain.dto.ReviewCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    @PostMapping
    public String write(@RequestBody ReviewCreateRequest dto) {
        return "리뷰 등록에 성공했습니다.";

    }


}
