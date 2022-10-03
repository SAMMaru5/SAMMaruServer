package com.sammaru5.sammaru.service.comment;

import com.sammaru5.sammaru.domain.Article;
import com.sammaru5.sammaru.domain.Comment;
import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.repository.CommentRepository;
import com.sammaru5.sammaru.web.dto.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentSearchService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public List<CommentDTO> findCommentsByArticleId(Long articleId) throws CustomException {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND, articleId.toString()));
        List<Comment> comments = commentRepository.findByArticle(article);

        return comments.stream().map(CommentDTO::new).collect(Collectors.toList());
    }
}
