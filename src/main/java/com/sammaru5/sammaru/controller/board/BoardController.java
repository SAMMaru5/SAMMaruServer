package com.sammaru5.sammaru.controller.board;

import com.sammaru5.sammaru.apiresult.ApiResult;
import com.sammaru5.sammaru.domain.BoardEntity;
import com.sammaru5.sammaru.dto.ArticleDTO;
import com.sammaru5.sammaru.dto.BoardDTO;
import com.sammaru5.sammaru.request.BoardRequest;
import com.sammaru5.sammaru.service.article.ArticleSearchService;
import com.sammaru5.sammaru.service.board.BoardRegisterService;
import com.sammaru5.sammaru.service.board.BoardRemoveService;
import com.sammaru5.sammaru.service.board.BoardStatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController @RequiredArgsConstructor
@Api(tags = {"게시판 API"})
public class BoardController {

    private final BoardRegisterService boardRegisterService;
    private final BoardStatusService boardStatusService;
    private final BoardRemoveService boardRemoveService;
    private final ArticleSearchService articleSearchService;

    @PostMapping("/api/boards")
    @ApiOperation(value = "게시판 생성", notes = "게시판을 생성", response = BoardDTO.class)
    public ApiResult<?> boardAdd(@RequestBody BoardRequest boardRequest) {
        try {
            return ApiResult.OK(boardRegisterService.addBoard(boardRequest));
        } catch (Exception e) {
            return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/api/boards/{boardId}")
    @ApiOperation(value = "게시판 삭제", notes = "관리자가 게시판과 게시판에 속한 모든 게시글 삭제", response = Boolean.class)
    public ApiResult<?> boardRemove(@PathVariable Long boardId) {
        try {
            return ApiResult.OK(boardRemoveService.removeBoard(boardId));
        } catch (Exception e) {
            return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/no-permit/api/boards")
    @ApiOperation(value = "게시판 목록", notes = "게시판 목록을 조회", responseContainer = "List",response = BoardDTO.class)
    public ApiResult<?> boardList() {
        try {
            List<BoardEntity> boards = boardStatusService.findBoards();
            List<BoardDTO> collect = boards.stream().map(BoardDTO::new).collect(Collectors.toList());
            return ApiResult.OK(collect);
        } catch (Exception e) {
            return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/boards/{boardId}/pages/{pageNum}")
    @ApiOperation(value = "게시판 세부 정보 조회", notes = "해당 게시판의 게시글들을 조회 ex.자유게시판", responseContainer = "List",response = ArticleDTO.class)
    public ApiResult<?> boardDetails(@PathVariable Long boardId, @PathVariable Integer pageNum) {
        try {
            return ApiResult.OK(articleSearchService.findArticlesByBoardIdAndPaging(boardId, pageNum));
        } catch (Exception e) {
            return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
        }
    }
}
