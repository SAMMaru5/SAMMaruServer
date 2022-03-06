package com.sammaru5.sammaru.controller.article;

import com.sammaru5.sammaru.apiresult.ApiResult;
import com.sammaru5.sammaru.request.ArticleRequest;
import com.sammaru5.sammaru.service.article.ArticleRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequiredArgsConstructor
public class ArticleController {

    private final ArticleRegisterService articleRegisterService;

    @PostMapping("/api/boards/{boardId}/articles")
    public ApiResult<?> articleAdd(Authentication authentication, @PathVariable Long boardId, ArticleRequest articleRequest) {
        try {
            return ApiResult.OK(articleRegisterService.addArticle(authentication, boardId, articleRequest));
        } catch (Exception e) {
            return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
        }
    }
}
