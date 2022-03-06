package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.domain.BoardEntity;
import com.sammaru5.sammaru.domain.UserEntity;
import com.sammaru5.sammaru.exception.EmptyArticleException;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.request.ArticleRequest;
import com.sammaru5.sammaru.service.board.BoardSearchService;
import com.sammaru5.sammaru.service.user.UserSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor @Service
public class ArticleRegisterService {

    private final ArticleRepository articleRepository;
    private final BoardSearchService boardSearchService;
    private final UserSearchService userSearchService;

    public ArticleEntity addArticle(Authentication authentication, Long boardId, ArticleRequest articleRequest) throws Exception {
        if(articleRequest.getTitle() == null || articleRequest.getContent() == null) {
            throw new EmptyArticleException();
        }
        BoardEntity findBoard = boardSearchService.findBoardById(boardId);
        UserEntity findUser = userSearchService.getUserFromToken(authentication);
        return articleRepository.save(new ArticleEntity(articleRequest, findBoard, findUser));
    }
}
