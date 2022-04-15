package com.sammaru5.sammaru.service.comment;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.domain.CommentEntity;
import com.sammaru5.sammaru.dto.CommentDTO;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.repository.CommentRepository;
import com.sammaru5.sammaru.service.article.ArticleSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentSearchService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public List<CommentDTO> findCommentsByArticleId(Long articleId) throws NoSuchElementException {
        Optional<ArticleEntity> article = articleRepository.findById(articleId);
        if(article.isEmpty()) throw new NoSuchElementException("해당하는 게시글이 존재하지 않습니다.");
        else {
            List<CommentEntity> comments = commentRepository.findByArticle(article.get());
            return comments.stream().map(CommentDTO::new).collect(Collectors.toList());
        }
    }
}
