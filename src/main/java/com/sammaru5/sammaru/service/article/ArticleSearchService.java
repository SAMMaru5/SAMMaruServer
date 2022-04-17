package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.domain.BoardEntity;
import com.sammaru5.sammaru.domain.FileEntity;
import com.sammaru5.sammaru.dto.ArticleDTO;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.service.board.BoardStatusService;
import com.sammaru5.sammaru.service.file.FileStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class ArticleSearchService {
    private final BoardStatusService boardStatusService;
    private final ArticleRepository articleRepository;
    private final FileStatusService fileStatusService;

    public ArticleDTO findArticle(Long articleId) throws NullPointerException {

        Optional<ArticleEntity> findArticle = articleRepository.findById(articleId);
        if (!findArticle.isPresent()) {
            throw new NullPointerException("해당 articleId 게시글이 존재하지 않습니다!");
        }
        List<FileEntity> findFiles = fileStatusService.findFilesByArticle(findArticle.get());
        findArticle.get().plusViewCnt(); //조회수 증가
        articleRepository.save(findArticle.get());
        return new ArticleDTO(findArticle.get(), findFiles);
    }

     //boardId에 해당하는 게시판의 게시글들을 paging
    public List<ArticleDTO> findArticlesByBoardIdAndPaging(Long boardId, Integer pageNum) throws NullPointerException {
        BoardEntity findBoard = boardStatusService.findBoard(boardId);
        Pageable pageable = PageRequest.of(pageNum, 15, Sort.by("createTime").descending());
        List<ArticleEntity> findArticlesByPaging = articleRepository.findByBoard(findBoard, pageable);
        if(findArticlesByPaging.isEmpty()) {
            throw new NullPointerException("해당 게시판의 게시글이 아무것도 존재하지 않습니다!");
        }
        return findArticlesByPaging.stream().map(ArticleDTO::new).collect(Collectors.toList());
    }

    //boardId에 해당하는 게시판에 달린 모든 게시글들 조회
    public List<ArticleDTO> findArticlesByBoardId(Long boardId) throws NullPointerException {
        BoardEntity findBoard = boardStatusService.findBoard(boardId);
        List<ArticleEntity> findArticles = articleRepository.findByBoard(findBoard);
        if(findArticles.isEmpty()) {
            throw new NullPointerException("해당 게시판의 게시글이 아무것도 존재하지 않습니다!");
        }
        return findArticles.stream().map(ArticleDTO::new).collect(Collectors.toList());
    }

    // 메인페이지에 보여지는 7개의 공지사항을 가져오는 메서드 findArticlesByBoardName
    public List<ArticleDTO> findArticlesByBoardName(String boardName) throws NullPointerException {
        BoardEntity findBoard = boardStatusService.findBoardByName(boardName);
        Pageable pageable = PageRequest.of(0, 7, Sort.by("createTime").descending());
        List<ArticleEntity> findArticles = articleRepository.findByBoard(findBoard, pageable);
        if(findArticles.isEmpty()){
            throw new NullPointerException("해당 게시판의 게시글이 아무것도 존재하지 않습니다!");
        }
        return findArticles.stream().map(ArticleDTO::new).collect(Collectors.toList());
    }
}
