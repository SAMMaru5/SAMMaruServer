package com.sammaru5.sammaru.web.controller.article;

import com.sammaru5.sammaru.util.OverMemberRole;
import com.sammaru5.sammaru.web.apiresult.ApiResult;
import com.sammaru5.sammaru.domain.User;
import com.sammaru5.sammaru.web.dto.ArticleDTO;
import com.sammaru5.sammaru.web.request.ArticleRequest;
import com.sammaru5.sammaru.util.AuthUser;
import com.sammaru5.sammaru.service.article.ArticleModifyService;
import com.sammaru5.sammaru.service.article.ArticleRegisterService;
import com.sammaru5.sammaru.service.article.ArticleRemoveService;
import com.sammaru5.sammaru.service.article.ArticleSearchService;
import com.sammaru5.sammaru.service.file.FileStatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    @OverMemberRole
    public ApiResult<Long> articleAdd(@AuthUser User user, @PathVariable Long boardId, @RequestPart(value="article") @Valid ArticleRequest articleRequest, @RequestPart(value="file", required = false) MultipartFile[] multipartFiles) {
        return ApiResult.OK(articleRegisterService.addArticle(user.getStudentId(), boardId, articleRequest, multipartFiles));
    }

    @GetMapping("/api/boards/{boardId}/articles/{articleId}")
    @ApiOperation(value = "게시글 상세", notes = "게시글 상세 정보 가져오기")
    @OverMemberRole
    public ApiResult<ArticleDTO> articleDetails(@PathVariable Long boardId, @PathVariable Long articleId) {
        return ApiResult.OK(articleSearchService.findArticle(articleId));
    }

    @DeleteMapping("/api/boards/{boardId}/articles/{articleId}")
    @ApiOperation(value = "게시글 삭제", notes = "게시글 삭제")
    @OverMemberRole
    public ApiResult<Boolean> articleRemove(@AuthUser User user, @PathVariable Long boardId, @PathVariable Long articleId) {
        return ApiResult.OK(articleRemoveService.removeArticle(articleId, user.getStudentId(), boardId));
    }

    @PatchMapping(value = "/api/boards/{boardId}/articles/{articleId}", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "게시글 수정", notes = "작성자가 게시글 수정")
    @OverMemberRole
    public ApiResult<Long> articleModify(@AuthUser User user, @PathVariable Long boardId, @PathVariable Long articleId, @RequestPart(value="article", required = false) @Valid ArticleRequest articleRequest, @RequestPart(value="file", required = false) MultipartFile[] multipartFiles) {
        return ApiResult.OK(articleModifyService.modifyArticle(articleId, user.getStudentId(), boardId, articleRequest, multipartFiles));
    }

    //파일 다운로드
    @GetMapping("/no-permit/api/boards/{boardId}/articles/{articleId}/files/{fileName:.+}")
    @ApiOperation(value = "파일 다운로드", notes = "게시판 id와 파일 경로를 통해 파일 다운로드")
    public ResponseEntity<InputStreamResource> fileDownload(@PathVariable Long boardId, @PathVariable Long articleId, @PathVariable String fileName) throws IOException {
        return fileStatusService.downloadFile(boardId, fileName);
    }
}
