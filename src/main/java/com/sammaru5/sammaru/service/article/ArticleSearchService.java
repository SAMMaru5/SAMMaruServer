package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.domain.BoardEntity;
import com.sammaru5.sammaru.exception.NonExistentAritcleException;
import com.sammaru5.sammaru.exception.NonExistentBoardnameException;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.service.board.BoardSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
public class ArticleSearchService {

    private final BoardSearchService boardSearchService;
    private final ArticleRepository articleRepository;

    public List<ArticleEntity> findArticlesByBoardname(String boardname) throws Exception {
        BoardEntity findBoard = boardSearchService.findBoardByBoardname(boardname);
        if(findBoard == null) {
            throw new NonExistentBoardnameException();
        }
        Pageable pageable = PageRequest.of(0, 7, Sort.by("createTime").descending());
        List<ArticleEntity> findArticles = articleRepository.findByBoard(findBoard, pageable);
        if(findArticles == null) {
            throw new NonExistentAritcleException();
        }
        return findArticles;
    }
}
