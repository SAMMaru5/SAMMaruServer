package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.domain.BoardEntity;
import com.sammaru5.sammaru.domain.UserEntity;
import com.sammaru5.sammaru.exception.EmptyArticleException;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.request.ArticleRequest;
import com.sammaru5.sammaru.service.board.BoardSearchService;
import com.sammaru5.sammaru.service.storage.FileRegisterService;
import com.sammaru5.sammaru.service.user.UserSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor @Service
public class ArticleRegisterService {

    private final ArticleRepository articleRepository;
    private final BoardSearchService boardSearchService;
    private final UserSearchService userSearchService;
    private final FileRegisterService fileRegisterService;

    public ArticleEntity addArticle(Authentication authentication, Long boardId, ArticleRequest articleRequest, MultipartFile[] multipartFiles) throws Exception {

        BoardEntity findBoard = boardSearchService.findBoardById(boardId);
        UserEntity findUser = userSearchService.getUserFromToken(authentication);
        ArticleEntity articleEntity = articleRepository.save(new ArticleEntity(articleRequest, findBoard, findUser));

        if(multipartFiles.length != 0)
            fileRegisterService.addFile(multipartFiles, articleEntity.getId());

        return articleEntity;
    }
}
