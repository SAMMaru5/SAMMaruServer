package com.sammaru5.sammaru.controller.home;

import com.sammaru5.sammaru.apiresult.ApiResult;
import com.sammaru5.sammaru.dto.AnnouncementDTO;
import com.sammaru5.sammaru.service.article.ArticleSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequiredArgsConstructor
@Api(tags = {"공지사항 API"})
public class HomeController {

    private final ArticleSearchService articleSearchService;

    @GetMapping("/no-permit/api/home/announcements")
    @ApiOperation(value = "공지사항 가져오기", notes = "메인화면 공지사항 가져오기", responseContainer = "List", response = AnnouncementDTO.class)
    public ApiResult<?> announcementList() {
        try {
            return ApiResult.OK(articleSearchService.findArticlesByBoardName("공지사항"));
        } catch (Exception e) {
            return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
        }
    }
}
