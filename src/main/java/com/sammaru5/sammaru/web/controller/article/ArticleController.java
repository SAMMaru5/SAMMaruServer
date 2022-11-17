package com.sammaru5.sammaru.web.controller.article;

import com.sammaru5.sammaru.config.security.SecurityUtil;
import com.sammaru5.sammaru.domain.SearchSubject;
import com.sammaru5.sammaru.service.article.*;
import com.sammaru5.sammaru.service.file.FileStatusService;
import com.sammaru5.sammaru.util.OverMemberRole;
import com.sammaru5.sammaru.web.apiresult.ApiResult;
import com.sammaru5.sammaru.web.dto.ArticleDTO;
import com.sammaru5.sammaru.web.request.ArticleRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Api(tags = {"게시글 API"})
public class ArticleController {

    private final ArticleRegisterService articleRegisterService;
    private final ArticleSearchService articleSearchService;
    private final ArticleRemoveService articleRemoveService;
    private final ArticleModifyService articleModifyService;
    private final FileStatusService fileStatusService;
    private final ArticleLikeService articleLikeService;

    @PostMapping(value = "/api/boards/{boardId}/articles", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "게시글 생성", notes = "게시판에 게시글 추가")
    @OverMemberRole
    public ApiResult<ArticleDTO> articleAdd(@PathVariable Long boardId, @RequestPart(value = "article") @Valid ArticleRequest articleRequest, @RequestPart(value = "file", required = false) MultipartFile[] multipartFiles) {
        return ApiResult.OK(articleRegisterService.addArticle(SecurityUtil.getCurrentUserId(), boardId, articleRequest, multipartFiles));
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
    public ApiResult<Boolean> articleRemove(@PathVariable Long boardId, @PathVariable Long articleId) {
        return ApiResult.OK(articleRemoveService.removeArticle(articleId, SecurityUtil.getCurrentUserId(), boardId));
    }

    @PatchMapping(value = "/api/boards/{boardId}/articles/{articleId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "게시글 수정", notes = "작성자가 게시글 수정")
    @OverMemberRole
    public ApiResult<ArticleDTO> articleModify(@PathVariable Long boardId, @PathVariable Long articleId, @RequestPart(value = "article", required = false) @Valid ArticleRequest articleRequest, @RequestPart(value = "file", required = false) MultipartFile[] multipartFiles) {
        return ApiResult.OK(articleModifyService.modifyArticle(articleId, SecurityUtil.getCurrentUserId(), boardId, articleRequest, multipartFiles));
    }

    //파일 다운로드
    @GetMapping("/no-permit/api/boards/{boardId}/articles/{articleId}/files/{fileName:.+}")
    @ApiOperation(value = "파일 다운로드", notes = "게시판 id와 파일 경로를 통해 파일 다운로드")
    public ResponseEntity<InputStreamResource> fileDownload(@PathVariable Long boardId, @PathVariable Long articleId, @PathVariable String fileName) throws IOException {
        return fileStatusService.downloadFile(boardId, fileName);
    }

    @PostMapping("/api/boards/{boardId}/articles/{articleId}/like")
    @ApiOperation(value = "게시글에 좋아요 추가", notes = "로그인된 유저가 해당 게시글에 좋아요를 누른다")
    @OverMemberRole
    public ApiResult<Long> articleLikeAdd(@PathVariable Long boardId, @PathVariable Long articleId) {
        return ApiResult.OK(articleLikeService.giveArticleLike(articleId, SecurityUtil.getCurrentUserId()));
    }

    @DeleteMapping("/api/boards/{boardId}/articles/{articleId}/like")
    @ApiOperation(value = "게시글에 눌렀던 좋아요 취소", notes = "로그인된 유저가 해당 게시글에 눌렀던 좋아요를 취소한다")
    @OverMemberRole
    public ApiResult<Boolean> articleLikeRemove(@PathVariable Long boardId, @PathVariable Long articleId) {
        return ApiResult.OK(articleLikeService.cancelArticleLike(articleId, SecurityUtil.getCurrentUserId()));
    }

    @GetMapping("/no-permit/api/boards/{boardId}/searchResult/pages/{pageNum}")
    @ApiOperation(value = "특정 게시판을 대상으로 게시글 검색", notes = "해당 게시판을 대상으로 게시글들을 검색 ex.자유게시판", responseContainer = "List", response = ArticleDTO.class)
    public ApiResult<Page<ArticleDTO>> boardSearch(@PathVariable Long boardId, @PathVariable Integer pageNum, @RequestParam Integer pageSize, @RequestParam SearchSubject searchSubject, @RequestParam String keyword) {
        return ApiResult.OK(articleSearchService.findArticlesByBoardIdAndKeywordAndPaging(boardId, pageNum - 1, pageSize, searchSubject, keyword));
    }

    @GetMapping("/no-permit/api/boards/searchResult/pages/{pageNum}")
    @ApiOperation(value = "모든 게시판을 대상으로 게시글 검색", notes = "모든 게시판을 대상으로 게시글들을 검색", responseContainer = "List", response = ArticleDTO.class)
    public ApiResult<Page<ArticleDTO>> search(@PathVariable Integer pageNum, @RequestParam Integer pageSize, @RequestParam SearchSubject searchSubject, @RequestParam String keyword) {
        return ApiResult.OK(articleSearchService.findArticlesByKeywordAndPaging(pageNum - 1, pageSize, searchSubject, keyword));
    }
}
