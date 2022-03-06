package com.sammaru5.sammaru.controller.board;

import com.sammaru5.sammaru.apiresult.ApiResult;
import com.sammaru5.sammaru.domain.BoardEntity;
import com.sammaru5.sammaru.request.BoardRequest;
import com.sammaru5.sammaru.service.board.BoardRegisterService;
import com.sammaru5.sammaru.service.board.BoardSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardRegisterService boardRegisterService;
    private final BoardSearchService boardSearchService;

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
}
