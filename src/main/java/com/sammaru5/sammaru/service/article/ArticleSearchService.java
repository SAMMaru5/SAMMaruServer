package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.domain.Article;
import com.sammaru5.sammaru.domain.Board;
import com.sammaru5.sammaru.domain.SearchSubject;
import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.repository.BoardRepository;
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
    private final BoardRepository boardRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    @Cacheable(keyGenerator = "articleCacheKeyGenerator", value = "article", cacheManager = "cacheManager")
    public ArticleDTO findArticle(Long articleId) {
        Article article = articleRepository.findOneWithFilesAndUserById(articleId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND, articleId.toString()));

        article.plusViewCnt(); //조회수 증가

        return ArticleDTO.toDto(article);
    }

    //boardId에 해당하는 게시판의 게시글들을 paging
    public List<ArticleDTO> findArticlesByBoardIdAndPaging(Long boardId, Integer pageNum) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
        Pageable pageable = PageRequest.of(pageNum, 15, Sort.by("createTime").descending());
        List<Article> articles = articleRepository.findArticlesWithFilesAndUserByBoard(board, pageable);

        return articles.stream().map(ArticleDTO::toDto).collect(Collectors.toList());
    }

    //boardId에 해당하는 게시판에 달린 모든 게시글들 조회
    public List<ArticleDTO> findArticlesByBoardId(Long boardId) {
        List<Article> findArticles = articleRepository.findArticlesByBoardId(boardId);
        if (findArticles.isEmpty()) {
            throw new CustomException(ErrorCode.BOARD_IS_EMPTY, boardId.toString());
        }

        return findArticles.stream().map(ArticleDTO::toDto).collect(Collectors.toList());
    }

    // 메인페이지에 보여지는 7개의 공지사항을 가져오는 메서드 findArticlesByBoardName
    public List<ArticleDTO> findArticlesByBoardName(String boardName) {
        Board board = boardRepository.findByBoardNameNotice(boardName)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
        Pageable pageable = PageRequest.of(0, 7, Sort.by("createTime").descending());
        List<Article> findArticles = articleRepository.findByBoard(board, pageable);
        if (findArticles.isEmpty()) {
            throw new CustomException(ErrorCode.BOARD_IS_EMPTY, board.getId().toString());
        }

        return findArticles.stream().map(ArticleDTO::toDto).collect(Collectors.toList());
    }

    public List<ArticleDTO> findArticlesByBoardIdAndKeywordAndPaging(Long boardId, Integer pageNum, SearchSubject searchSubject, String keyword) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
        Pageable pageable = PageRequest.of(pageNum, 15, Sort.by("createTime").descending());

        List<Article> articles;
        switch (searchSubject) {
            case WRITER_STUDENT_ID:
                articles = articleRepository.searchArticlesByBoardAndStudentId(board, pageable, keyword);
                break;
            case WRITER_NAME:
                articles = articleRepository.searchArticlesByBoardAndUsername(board, pageable, keyword);
                break;
            case ARTICLE_TITLE:
                articles = articleRepository.searchArticlesByBoardAndTitle(board, pageable, keyword);
                break;
            case ARTICLE_CONTENT:
                articles = articleRepository.searchArticlesByBoardAndContent(board, pageable, keyword);
                break;
            case ARTICLE_TITLE_AND_CONTENT:
                articles = articleRepository.searchArticlesByBoardAndTitleAndContent(board, pageable, keyword);
                break;
            default:
                throw new CustomException(ErrorCode.WRONG_SEARCH_SUBJECT, searchSubject.name());
        }
        return articles.stream().map(ArticleDTO::toDto).collect(Collectors.toList());
    }

    public List<ArticleDTO> findArticlesByKeywordAndPaging(Integer pageNum, SearchSubject searchSubject, String keyword) {
        Pageable pageable = PageRequest.of(pageNum, 15, Sort.by("createTime").descending());

        List<Article> articles;
        switch (searchSubject) {
            case WRITER_STUDENT_ID:
                articles = articleRepository.searchArticlesByStudentId(pageable, keyword);
                break;
            case WRITER_NAME:
                articles = articleRepository.searchArticlesByUsername(pageable, keyword);
                break;
            case ARTICLE_TITLE:
                articles = articleRepository.searchArticlesByTitle(pageable, keyword);
                break;
            case ARTICLE_CONTENT:
                articles = articleRepository.searchArticlesByContent(pageable, keyword);
                break;
            case ARTICLE_TITLE_AND_CONTENT:
                articles = articleRepository.searchArticlesByTitleAndContent(pageable, keyword);
                break;
            default:
                throw new CustomException(ErrorCode.WRONG_SEARCH_SUBJECT, searchSubject.name());
        }
        return articles.stream().map(ArticleDTO::toDto).collect(Collectors.toList());
    }
}
