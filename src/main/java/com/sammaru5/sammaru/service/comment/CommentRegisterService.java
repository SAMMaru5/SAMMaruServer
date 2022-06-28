package com.sammaru5.sammaru.service.comment;

import com.sammaru5.sammaru.domain.Article;
import com.sammaru5.sammaru.domain.Comment;
import com.sammaru5.sammaru.domain.User;
import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.repository.CommentRepository;
import com.sammaru5.sammaru.service.user.UserStatusService;
import com.sammaru5.sammaru.web.dto.CommentDTO;
import com.sammaru5.sammaru.web.request.CommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class CommentRegisterService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserStatusService userStatusService;

    public CommentDTO addComment(Authentication authentication, CommentRequest commentRequest, Long articleId) throws CustomException {
        User user = userStatusService.getUser(authentication);
        Optional<Article> findArticle = articleRepository.findById(articleId);
        if(findArticle.isEmpty())
            throw new CustomException(ErrorCode.ARTICLE_NOT_FOUND, articleId.toString());
        else {
            Comment comment = new Comment(commentRequest, user, findArticle.get());
            commentRepository.save(comment);
            return new CommentDTO(comment);
        }
    }
}
