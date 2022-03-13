package com.sammaru5.sammaru.controller.article;

import com.sammaru5.sammaru.apiresult.ApiResult;
import com.sammaru5.sammaru.request.ArticleRequest;
import com.sammaru5.sammaru.service.article.ArticleRegisterService;
import com.sammaru5.sammaru.service.article.ArticleRemoveService;
import com.sammaru5.sammaru.service.article.ArticleSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController @RequiredArgsConstructor
public class ArticleController {

    private final ArticleRegisterService articleRegisterService;
    private final ArticleSearchService articleSearchService;
    private final ArticleRemoveService articleRemoveService;

    /**
     * 게시글 생성
     * @param authentication
     * @param boardId
     * @param articleRequest
     * @return
     */
    @PostMapping("/api/boards/{boardId}/articles")
    public ApiResult<?> articleAdd(Authentication authentication, @PathVariable Long boardId, ArticleRequest articleRequest) {
        try {
            return ApiResult.OK(articleRegisterService.addArticle(authentication, boardId, articleRequest));
        } catch (Exception e) {
            return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 게시글 상세
     */
    @GetMapping("/api/boards/{boardId}/articles/{articleId}")
    public ApiResult<?> articleDetails(Authentication authentication, @PathVariable Long boardId, @PathVariable Long articleId) {
        try {
            return ApiResult.OK(articleSearchService.findOneArticle(authentication, boardId, articleId));
        } catch (Exception e) {
            return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 작성자가 게시글 삭제
     */
    @DeleteMapping("/api/boards/{boardId}/articles/{articleId}")
    public ApiResult<?> articleRemove(Authentication authentication, @PathVariable Long boardId, @PathVariable Long articleId) {
        try {
            return ApiResult.OK(articleRemoveService.removeArticleByAuthor(authentication, boardId, articleId));
        } catch (Exception e) {
            return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
        }
    }
}
