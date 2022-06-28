package com.sammaru5.sammaru.service.board;

import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.repository.BoardRepository;
import com.sammaru5.sammaru.service.article.ArticleRemoveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service @RequiredArgsConstructor
public class BoardRemoveService {

    private final ArticleRemoveService articleRemoveService;
    private final BoardRepository boardRepository;

    @Transactional
    public boolean removeBoard(Long boardId)  {

        String boardname = boardRepository.findById(boardId).get().getBoardName();
        if(boardname.equals("족보") || boardname.equals("사진첩") || boardname.equals("공지사항")) {
            new CustomException(ErrorCode.BOARD_NOT_REMOVE, boardId.toString());
        }

        articleRemoveService.removeArticleByAdmin(boardId);
        boardRepository.deleteById(boardId);
        return true;
    }
}
