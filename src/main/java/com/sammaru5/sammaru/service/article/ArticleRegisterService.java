package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.domain.BoardEntity;
import com.sammaru5.sammaru.domain.UserEntity;
import com.sammaru5.sammaru.dto.ArticleDTO;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.request.ArticleRequest;
import com.sammaru5.sammaru.service.board.BoardStatusService;
import com.sammaru5.sammaru.service.file.FileRegisterService;
import com.sammaru5.sammaru.service.user.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service @RequiredArgsConstructor
public class ArticleRegisterService {
    private final ArticleRepository articleRepository;
    private final BoardStatusService boardStatusService;
    private final UserStatusService userStatusService;
    private final FileRegisterService fileRegisterService;

    public ArticleDTO addArticle(Authentication authentication, Long boardId, ArticleRequest articleRequest, MultipartFile[] multipartFiles) throws Exception {

        BoardEntity findBoard = boardStatusService.findBoard(boardId);
        UserEntity findUser = userStatusService.getUser(authentication);
        ArticleEntity articleEntity = articleRepository.save(new ArticleEntity(articleRequest, findBoard, findUser));

        if(multipartFiles.length != 0)
            fileRegisterService.addFiles(multipartFiles, articleEntity.getId());

        return new ArticleDTO(articleEntity);
    }
}
