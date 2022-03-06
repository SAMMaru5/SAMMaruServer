package com.sammaru5.sammaru.controller.home;

import com.sammaru5.sammaru.apiresult.ApiResult;
import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.service.article.ArticleSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController @RequiredArgsConstructor
public class HomeController {

    private final ArticleSearchService articleSearchService;

    @GetMapping("/api/home/announcements")
    public ApiResult<?> announcementList() {
        try {
            List<ArticleEntity> findAnnoucements = articleSearchService.findArticlesByBoardname("공지사항");
            List<AnnouncementDTO> collect = findAnnoucements.stream().map(AnnouncementDTO::new).collect(Collectors.toList());
            return ApiResult.OK(collect);
        } catch (Exception e) {
            return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
        }
    }
}
