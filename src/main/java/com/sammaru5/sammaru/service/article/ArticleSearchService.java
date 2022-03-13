package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.controller.article.ArticleDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class ArticleSearchService {

    private final BoardSearchService boardSearchService;
    private final ArticleRepository articleRepository;

    /**
     * boardId에 해당하는 게시판의 게시글들을 paging
     */
    public List<ArticleDTO> findArticlesByBoardId(Long boardId, Integer pageNum) throws Exception {
        BoardEntity findBoard = boardSearchService.findBoardById(boardId);
        Pageable pageable = PageRequest.of(pageNum, 15, Sort.by("createTime").descending());
        List<ArticleEntity> findArticlesByPaging = articleRepository.findByBoard(findBoard, pageable);
        if(findArticlesByPaging.isEmpty()) {
            throw new NonExistentAritcleException();
        }
        return findArticlesByPaging.stream().map(ArticleDTO::new).collect(Collectors.toList());
    }

    /**
     * boardId에 해당하는 게시판에 달린 모든 게시글들 조회
     * @param boardId
     * @return
     * @throws Exception
     */
    public List<ArticleEntity> findAllByBoardId(Long boardId) throws Exception {
        BoardEntity findBoard = boardSearchService.findBoardById(boardId);
        List<ArticleEntity> articles = articleRepository.findByBoard(findBoard);
        if(!articles.isEmpty()) return articles;
        else throw new NonExistentAritcleException();
    }

    /**
     * 메인페이지에 보여지는 7개의 공지사항을 가져오는 메서드
     * @param boardname
     * @return
     * @throws Exception
     */
    public List<ArticleEntity> getAnnoucementsInHome(String boardname) throws Exception {
        BoardEntity findBoard = boardSearchService.findBoardByBoardname(boardname);
        if(findBoard == null) {
            throw new NonExistentBoardnameException();
        }
        Pageable pageable = PageRequest.of(0, 7, Sort.by("createTime").descending());
        List<ArticleEntity> findArticles = articleRepository.findByBoard(findBoard, pageable);
        if(findArticles.isEmpty()) {
            throw new NonExistentAritcleException();
        }
        return findArticles;
    }
}
