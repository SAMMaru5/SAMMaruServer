package com.sammaru5.sammaru.controller.comment;

import com.sammaru5.sammaru.apiresult.ApiResult;
import com.sammaru5.sammaru.dto.CommentDTO;
import com.sammaru5.sammaru.service.comment.CommentSearchService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentSearchService commentSearchService;

    @GetMapping("/api/boards/{boardId}/articles/{articleId}/comments")
    @ApiOperation(value = "댓글 목록 조회", notes = "해당 게시글에 달린 댓글 목록 불러오기", response = CommentDTO.class)
    public ApiResult<?> commentList(@PathVariable Long boardId, @PathVariable Long articleId) {
        return ApiResult.OK(commentSearchService.findCommentsByArticleId(articleId));
    }
}
