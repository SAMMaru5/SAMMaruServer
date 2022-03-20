package com.sammaru5.sammaru.controller.article;

import com.sammaru5.sammaru.apiresult.ApiResult;
import com.sammaru5.sammaru.request.ArticleRequest;
import com.sammaru5.sammaru.service.article.ArticleRegisterService;
import com.sammaru5.sammaru.service.article.ArticleRemoveService;
import com.sammaru5.sammaru.service.article.ArticleSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PostMapping(value="/auth/api/boards/{boardId}/articles", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResult<?> articleAdd(Authentication authentication, @PathVariable Long boardId, @RequestPart(value="article", required = false) ArticleRequest articleRequest, @RequestPart(value="file", required = false) MultipartFile[] multipartFiles) {
        try {
            return ApiResult.OK(articleRegisterService.addArticle(authentication, boardId, articleRequest, multipartFiles));
        } catch (Exception e) {
            return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 게시글 상세
     */
    @GetMapping("/auth/api/boards/{boardId}/articles/{articleId}")
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
    @DeleteMapping("/auth/api/boards/{boardId}/articles/{articleId}")
    public ApiResult<?> articleRemove(Authentication authentication, @PathVariable Long boardId, @PathVariable Long articleId) {
        try {
            return ApiResult.OK(articleRemoveService.removeArticleByAuthor(authentication, boardId, articleId));
        } catch (Exception e) {
            return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
        }
    }
}
