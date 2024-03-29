package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.config.security.SecurityUtil;
import com.sammaru5.sammaru.domain.Article;
import com.sammaru5.sammaru.domain.Board;
import com.sammaru5.sammaru.domain.SearchSubject;
import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.repository.ArticleLikeRepository;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.repository.BoardRepository;
import com.sammaru5.sammaru.web.dto.ArticleDTO;
import com.sammaru5.sammaru.web.dto.ArticleDetailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
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
    private final ArticleLikeRepository articleLikeRepository;

    @Transactional
    @Cacheable(keyGenerator = "articleCacheKeyGenerator", value = "article", cacheManager = "cacheManager")
    public ArticleDetailDTO findArticle(Long articleId) {
        Article article = articleRepository.findOneWithFilesAndUserById(articleId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND, articleId.toString()));

        // 게시글의 이전글, 다음글
        Long prevArticleId = articleRepository.findPrevArticleId(articleId, article.getBoard().getId()).orElse(0L);
        Long nextArticleId = articleRepository.findNextArticleId(articleId, article.getBoard().getId()).orElse(0L);

        article.plusViewCnt(); //조회수 증가

        article.setLikeCnt(articleLikeRepository.countAllByArticleId(articleId));

        try {
            Long currentUserId = SecurityUtil.getCurrentUserId();
            article.setIsLiked(articleLikeRepository.existsByArticleIdAndUserId(articleId, currentUserId));
        } catch (CustomException e) { }

        return ArticleDetailDTO.from(article, prevArticleId, nextArticleId);
    }

    //boardId에 해당하는 게시판의 게시글들을 paging
    public Page<ArticleDTO> findArticlesByBoardIdAndPaging(Long boardId, Integer pageNum, Integer pageSize) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("createTime").descending());
        Page<Article> articles = articleRepository.findArticlesWithFilesAndUserByBoard(board, pageable);
        return articles.map(ArticleDTO::from);
    }

    //boardId에 해당하는 게시판에 달린 모든 게시글들 조회
    public List<ArticleDTO> findArticlesByBoardId(Long boardId) {
        List<Article> findArticles = articleRepository.findArticlesByBoardId(boardId);
        return findArticles.stream().map(ArticleDTO::from).collect(Collectors.toList());
    }

    // 메인페이지에 보여지는 7개의 공지사항을 가져오는 메서드 findArticlesByBoardName
    public List<ArticleDTO> findArticlesByBoardName(String boardName) {
        Board board = boardRepository.findByBoardName(boardName)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
        Pageable pageable = PageRequest.of(0, 7, Sort.by("createTime").descending());
        List<Article> findArticles = articleRepository.findByBoard(board, pageable);
        return findArticles.stream().map(ArticleDTO::from).collect(Collectors.toList());
    }

    public Page<ArticleDTO> findArticlesByBoardIdAndKeywordAndPaging(Long boardId, Integer pageNum, Integer pageSize, SearchSubject searchSubject, String keyword) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("createTime").descending());

        Page<Article> articles;
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
        return articles.map(ArticleDTO::from);
    }

    public Page<ArticleDTO> findArticlesByKeywordAndPaging(Integer pageNum, Integer pageSize, SearchSubject searchSubject, String keyword) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("createTime").descending());

        Page<Article> articles;
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
        return articles.map(ArticleDTO::from);
    }
}
