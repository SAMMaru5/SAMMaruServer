package com.sammaru5.sammaru.web.controller.home;

import com.sammaru5.sammaru.web.apiresult.ApiResult;
import com.sammaru5.sammaru.web.dto.ArticleDTO;
import com.sammaru5.sammaru.service.article.ArticleSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController @RequiredArgsConstructor
@Api(tags = {"공지사항 API"})
public class HomeController {

    private final ArticleSearchService articleSearchService;

    @GetMapping("/no-permit/api/home/announcements")
    @ApiOperation(value = "공지사항 가져오기", notes = "메인화면 공지사항 가져오기")
    public ApiResult<List<ArticleDTO>> announcementList() {
        return ApiResult.OK(articleSearchService.findArticlesByBoardName("공지사항"));
    }
}
