package com.sammaru5.sammaru.service.board;

import com.sammaru5.sammaru.domain.IndelibleBoardName;
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

        if(IndelibleBoardName.contain(boardname)) {
            throw new CustomException(ErrorCode.INDELIBLE_BOARD, boardId.toString());
        }

        articleRemoveService.removeArticleByAdmin(boardId);
        boardRepository.deleteById(boardId);
        return true;
    }
}
