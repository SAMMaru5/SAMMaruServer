package com.sammaru5.sammaru.service.comment;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.domain.CommentEntity;
import com.sammaru5.sammaru.domain.UserEntity;
import com.sammaru5.sammaru.dto.CommentDTO;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.repository.CommentRepository;
import com.sammaru5.sammaru.request.CommentRequest;
import com.sammaru5.sammaru.service.article.ArticleSearchService;
import com.sammaru5.sammaru.service.user.UserSearchService;
import com.sammaru5.sammaru.service.user.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class CommentRegisterService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserStatusService userStatusService;

    public CommentDTO addComment(Authentication authentication, CommentRequest commentRequest, Long articleId) throws NoSuchElementException{
        UserEntity user = userStatusService.getUser(authentication);
        Optional<ArticleEntity> findArticle = articleRepository.findById(articleId);
        if(findArticle.isEmpty()) throw new NoSuchElementException("해당하는 게시글이 없습니다.");
        else {
            CommentEntity comment = new CommentEntity(commentRequest, user, findArticle.get());
            commentRepository.save(comment);
            return new CommentDTO(comment);
        }
    }
}
