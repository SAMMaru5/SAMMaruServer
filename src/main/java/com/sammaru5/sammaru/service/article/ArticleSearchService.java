package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.domain.BoardEntity;
import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.domain.FileEntity;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.repository.FileRepository;
import com.sammaru5.sammaru.service.board.BoardStatusService;
import com.sammaru5.sammaru.web.dto.ArticleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ArticleSearchService {
    private final BoardStatusService boardStatusService;
    private final FileRepository fileRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    @Cacheable(keyGenerator = "articleCacheKeyGenerator", value = "article", cacheManager = "cacheManager")
    public ArticleDTO findArticle(Long articleId) {
        ArticleEntity article = articleRepository.findById(articleId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND, articleId.toString()));

        article.plusViewCnt(); //조회수 증가
        List<FileEntity> files = fileRepository.findByArticle(article);
        if (files.isEmpty()) return ArticleDTO.toDto(article);
        return ArticleDTO.toDtoWithFile(article, files);
    }

    //boardId에 해당하는 게시판의 게시글들을 paging
    public List<ArticleDTO> findArticlesByBoardIdAndPaging(Long boardId, Integer pageNum) throws CustomException {

        BoardEntity findBoard = boardStatusService.findBoard(boardId);
        Pageable pageable = PageRequest.of(pageNum, 15, Sort.by("createTime").descending());
        List<ArticleEntity> findArticlesByPaging = articleRepository.findByBoard(findBoard, pageable);
        if (findArticlesByPaging.isEmpty()) {
            throw new CustomException(ErrorCode.BOARD_IS_EMPTY, boardId.toString());
        }

        return findArticlesByPaging.stream().map(ArticleDTO::toDto).collect(Collectors.toList());
    }

    //boardId에 해당하는 게시판에 달린 모든 게시글들 조회
    public List<ArticleDTO> findArticlesByBoardId(Long boardId) throws CustomException {

        BoardEntity findBoard = boardStatusService.findBoard(boardId);
        List<ArticleEntity> findArticles = articleRepository.findByBoard(findBoard);
        if (findArticles.isEmpty()) {
            throw new CustomException(ErrorCode.BOARD_IS_EMPTY, boardId.toString());
        }

        return findArticles.stream().map(ArticleDTO::toDto).collect(Collectors.toList());
    }

    // 메인페이지에 보여지는 7개의 공지사항을 가져오는 메서드 findArticlesByBoardName
    public List<ArticleDTO> findArticlesByBoardName(String boardName) throws CustomException {

        BoardEntity findBoard = boardStatusService.findByBoardName(boardName);
        Pageable pageable = PageRequest.of(0, 7, Sort.by("createTime").descending());
        List<ArticleEntity> findArticles = articleRepository.findByBoard(findBoard, pageable);
        if (findArticles.isEmpty()) {
            throw new CustomException(ErrorCode.BOARD_IS_EMPTY, findBoard.getId().toString());
        }

        return findArticles.stream().map(ArticleDTO::toDto).collect(Collectors.toList());
    }
}
