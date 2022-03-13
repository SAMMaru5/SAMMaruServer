package com.sammaru5.sammaru.controller.board;

import com.sammaru5.sammaru.apiresult.ApiResult;
import com.sammaru5.sammaru.domain.BoardEntity;
import com.sammaru5.sammaru.dto.BoardDTO;
import com.sammaru5.sammaru.request.BoardRequest;
import com.sammaru5.sammaru.service.article.ArticleSearchService;
import com.sammaru5.sammaru.service.board.BoardRegisterService;
import com.sammaru5.sammaru.service.board.BoardRemoveService;
import com.sammaru5.sammaru.service.board.BoardSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardRegisterService boardRegisterService;
    private final BoardSearchService boardSearchService;
    private final BoardRemoveService boardRemoveService;
    private final ArticleSearchService articleSearchService;

    /**
     * 게시판을 생성하는 메서드입니다.
     * @param boardRequest
     * @return
     */
    @PostMapping("/api/boards")
    public ApiResult<?> boardAdd(Authentication authentication, @RequestBody BoardRequest boardRequest) {
        try {
            return ApiResult.OK(new BoardDTO(boardRegisterService.addBoard(authentication, boardRequest)));
        } catch (Exception e) {
            return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 게시판과 해당 게시판에 달린 게시글들을 모두 삭제하는 메서드입니다. (관리자 권한만 가능)
     * @param authentication
     * @param boardId
     * @return
     */
    @DeleteMapping("/api/boards/{boardId}")
    public ApiResult<?> boardRemove(Authentication authentication, @PathVariable Long boardId) {
        try {
            return ApiResult.OK(boardRemoveService.removeBoard(authentication, boardId));
        } catch (Exception e) {
            return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 게시판 목록을 조회하는 메서드입니다.
     */
    @GetMapping("/api/boards")
    public ApiResult<?> boardList() {
        try {
            List<BoardEntity> boards = boardSearchService.findBoardList();
            List<BoardDTO> collect = boards.stream().map(BoardDTO::new).collect(Collectors.toList());
            return ApiResult.OK(collect);
        } catch (Exception e) {
            return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 게시판 세부 정보 조회 (해당 게시판의 게시글들을 조회 ex.자유게시판)
     */
    @GetMapping("/api/boards/{boardId}/pages/{pageNum}")
    public ApiResult<?> boardDetails(@PathVariable Long boardId, @PathVariable Integer pageNum) {
        try {
            return ApiResult.OK(articleSearchService.findArticlesByBoardId(boardId, pageNum));
        } catch (Exception e) {
            return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
        }
    }
}
