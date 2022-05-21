package com.sammaru5.sammaru.controller.article;

import com.sammaru5.sammaru.apiresult.ApiResult;
import com.sammaru5.sammaru.domain.UserEntity;
import com.sammaru5.sammaru.dto.ArticleDTO;
import com.sammaru5.sammaru.dto.UserDTO;
import com.sammaru5.sammaru.request.ArticleRequest;
import com.sammaru5.sammaru.security.AuthUser;
import com.sammaru5.sammaru.service.article.ArticleModifyService;
import com.sammaru5.sammaru.service.article.ArticleRegisterService;
import com.sammaru5.sammaru.service.article.ArticleRemoveService;
import com.sammaru5.sammaru.service.article.ArticleSearchService;
import com.sammaru5.sammaru.service.file.FileStatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController @RequiredArgsConstructor
@Api(tags = {"게시글 API"})
public class ArticleController {

    private final ArticleRegisterService articleRegisterService;
    private final ArticleSearchService articleSearchService;
    private final ArticleRemoveService articleRemoveService;
    private final ArticleModifyService articleModifyService;
    private final FileStatusService fileStatusService;

    @PostMapping(value="/api/boards/{boardId}/articles", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "게시글 생성", notes = "게시판에 게시글 추가")
    public ApiResult<ArticleDTO> articleAdd(@AuthUser UserEntity user, @PathVariable Long boardId, @RequestPart(value="article") @Valid ArticleRequest articleRequest, @RequestPart(value="file", required = false) MultipartFile[] multipartFiles) {
        return ApiResult.OK(articleRegisterService.addArticle(user, boardId, articleRequest, multipartFiles));
    }

    @GetMapping("/api/boards/{boardId}/articles/{articleId}")
    @ApiOperation(value = "게시글 상세", notes = "게시글 상세 정보 가져오기")
    public ApiResult<ArticleDTO> articleDetails(@PathVariable Long boardId, @PathVariable Long articleId) {
        return ApiResult.OK(articleSearchService.findArticle(articleId));
    }

    @DeleteMapping("/api/boards/{boardId}/articles/{articleId}")
    @ApiOperation(value = "게시글 삭제", notes = "게시글 삭제")
    public ApiResult<Boolean> articleRemove(@AuthUser UserEntity user, @PathVariable Long boardId, @PathVariable Long articleId) {
        return ApiResult.OK(articleRemoveService.removeArticle(articleId, user, boardId));
    }

    @PatchMapping(value = "/no-permit/api/boards/{boardId}/articles/{articleId}", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "게시글 수정", notes = "작성자가 게시글 수정")
    public ApiResult<ArticleDTO> articleModify(@AuthUser UserEntity user, @PathVariable Long boardId, @PathVariable Long articleId, @RequestPart(value="article", required = false) @Valid ArticleRequest articleRequest, @RequestPart(value="file", required = false) MultipartFile[] multipartFiles) {
        return ApiResult.OK(articleModifyService.modifyArticle(articleId, user, boardId, articleRequest, multipartFiles));
    }

    //파일 다운로드
    @GetMapping("/api/boards/{boardId}/articles/{articleId}/files/{fileName:.+}")
    @ApiOperation(value = "파일 다운로드", notes = "게시판 id와 파일 경로를 통해 파일 다운로드")
    public ResponseEntity<InputStreamResource> fileDownload(@PathVariable Long boardId, @PathVariable Long articleId, @PathVariable String fileName) throws IOException {
        return fileStatusService.downloadFile(boardId, fileName);
    }
}
