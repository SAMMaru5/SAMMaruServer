package com.sammaru5.sammaru.controller.comment;

import com.sammaru5.sammaru.apiresult.ApiResult;
import com.sammaru5.sammaru.dto.CommentDTO;
import com.sammaru5.sammaru.request.CommentRequest;
import com.sammaru5.sammaru.service.comment.CommentRegisterService;
import com.sammaru5.sammaru.service.comment.CommentRemoveService;
import com.sammaru5.sammaru.service.comment.CommentSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@Api(tags = {"댓글 API"})
public class CommentController {

    private final CommentSearchService commentSearchService;
    private final CommentRegisterService commentRegisterService;
    private final CommentRemoveService commentRemoveService;

    @GetMapping("/api/boards/{boardId}/articles/{articleId}/comments")
    @ApiOperation(value = "댓글 목록 조회", notes = "해당 게시글에 달린 댓글 목록 불러오기", response = CommentDTO.class)
    public ApiResult<?> commentList(@PathVariable Long boardId, @PathVariable Long articleId) {
        return ApiResult.OK(commentSearchService.findCommentsByArticleId(articleId));
    }

    @PostMapping("/api/boards/{boardId}/articles/{articleId}/comments")
    @ApiOperation(value = "댓글 작성", notes = "해당 게시글에 댓글 작성", response = CommentDTO.class)
    public ApiResult<?> commentAdd(@PathVariable Long boardId, @PathVariable Long articleId, Authentication authentication, @Valid @RequestBody CommentRequest commentRequest) {
        return ApiResult.OK(commentRegisterService.addComment(authentication, commentRequest, articleId));
    }

    @DeleteMapping("/api/boards/{boardId}/articles/{articleId}/comments/{commentId}")
    @ApiOperation(value = "댓글 삭제", notes = "해당 댓글 삭제", response = boolean.class)
    public ApiResult<?> commentRemove(@PathVariable Long boardId, @PathVariable Long articleId, Authentication authentication, @PathVariable Long commentId) {
        return ApiResult.OK(commentRemoveService.removeComment(authentication, commentId));
    }
}
