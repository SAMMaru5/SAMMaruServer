package com.sammaru5.sammaru.service.board;

import com.sammaru5.sammaru.repository.BoardRepository;
import com.sammaru5.sammaru.service.article.ArticleRemoveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service @RequiredArgsConstructor
public class BoardRemoveService {

    private final ArticleRemoveService articleRemoveService;
    private final BoardRepository boardRepository;

    @Transactional
    public boolean removeBoard(Long boardId) throws Exception {
        try {
            articleRemoveService.removeArticleByAdmin(boardId);
            boardRepository.deleteById(boardId);
            return true;
        } catch (Exception e){
            throw e;
        }

    }
}
