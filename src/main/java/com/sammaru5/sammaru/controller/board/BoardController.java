package com.sammaru5.sammaru.controller.board;

import com.sammaru5.sammaru.apiresult.ApiResult;
import com.sammaru5.sammaru.service.board.BoardRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardRegisterService boardRegisterService;

    @PostMapping("/api/board")
    public ApiResult<?> boardAdd(@RequestBody BoardRequest boardRequest) {
        try {
            return ApiResult.OK(new BoardDTO(boardRegisterService.addBoard(boardRequest)));
        } catch (Exception e) {
            return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
        }
    }
}
