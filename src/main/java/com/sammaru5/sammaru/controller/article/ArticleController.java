package com.sammaru5.sammaru.controller.article;

import com.sammaru5.sammaru.apiresult.ApiResult;
import com.sammaru5.sammaru.dto.ArticleDTO;
import com.sammaru5.sammaru.request.ArticleRequest;
import com.sammaru5.sammaru.service.article.ArticleModifyService;
import com.sammaru5.sammaru.service.article.ArticleRegisterService;
import com.sammaru5.sammaru.service.article.ArticleRemoveService;
import com.sammaru5.sammaru.service.article.ArticleSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController @RequiredArgsConstructor
@Api(tags = {"게시글 API"})
public class ArticleController {

    private final ArticleRegisterService articleRegisterService;
    private final ArticleSearchService articleSearchService;
    private final ArticleRemoveService articleRemoveService;
    private final ArticleModifyService articleModifyService;

    @PostMapping(value="/api/boards/{boardId}/articles", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "게시글 생성", notes = "게시판에 게시글 추가", response = ArticleDTO.class)
    public ApiResult<?> articleAdd(Authentication authentication, @PathVariable Long boardId, @RequestPart(value="article") @Valid ArticleRequest articleRequest, @RequestPart(value="file", required = false) MultipartFile[] multipartFiles) {
        try {
            return ApiResult.OK(articleRegisterService.addArticle(authentication, boardId, articleRequest, multipartFiles));
        } catch (Exception e) {
            return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/boards/{boardId}/articles/{articleId}")
    @ApiOperation(value = "게시글 상세", notes = "게시글 상세 정보 가져오기", response = ArticleDTO.class)
    public ApiResult<?> articleDetails(@PathVariable Long boardId, @PathVariable Long articleId) {
        try {
            return ApiResult.OK(articleSearchService.findArticle(articleId));
        } catch (Exception e) {
            return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/api/boards/{boardId}/articles/{articleId}")
    @ApiOperation(value = "게시글 삭제", notes = "게시글 삭제", response = Boolean.class)
    public ApiResult<?> articleRemove(Authentication authentication, @PathVariable Long boardId, @PathVariable Long articleId) {
        return ApiResult.OK(articleRemoveService.removeArticle(authentication, boardId, articleId));
    }

    @PutMapping(value = "/api/boards/{boardId}/articles/{articleId}", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "게시글 수정", notes = "작성자가 게시글 수정", response = ArticleDTO.class)
    public ApiResult<?> articleModify(Authentication authentication, @PathVariable Long boardId, @PathVariable Long articleId, @RequestPart(value="article", required = false) @Valid ArticleRequest articleRequest, @RequestPart(value="file", required = false) MultipartFile[] multipartFiles) {
        return ApiResult.OK(articleModifyService.modifyArticle(authentication, boardId, articleId, articleRequest, multipartFiles));
    }
}
