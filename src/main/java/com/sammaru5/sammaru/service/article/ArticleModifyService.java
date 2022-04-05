package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.domain.UserEntity;
import com.sammaru5.sammaru.dto.ArticleDTO;
import com.sammaru5.sammaru.exception.InvalidUserException;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.request.ArticleRequest;
import com.sammaru5.sammaru.service.file.FileRegisterService;
import com.sammaru5.sammaru.service.file.FileRemoveService;
import com.sammaru5.sammaru.service.user.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service @RequiredArgsConstructor
public class ArticleModifyService {
    private final ArticleRepository articleRepository;
    private final UserStatusService userStatusService;
    private final FileRemoveService fileRemoveService;
    private final FileRegisterService fileRegisterService;

    public ArticleDTO modifyArticle(Authentication authentication, Long boardId, Long articleId, ArticleRequest articleRequest, MultipartFile[] multipartFiles) throws Exception {
        UserEntity findUser = userStatusService.getUser(authentication);
        if (findUser != null) {
            Optional<ArticleEntity> findArticle = articleRepository.findById(articleId);
            if (findArticle.isPresent()) {
                ArticleEntity article = findArticle.get();
                article.modifyArticle(articleRequest);

                if (multipartFiles != null) {
                    fileRemoveService.removeFilesByArticle(article);
                    fileRegisterService.addFiles(multipartFiles, article.getId());
                    return new ArticleDTO(article);
                }
            } else {
                // 존재하지 않는 게시글에 접근했을때
                throw new NullPointerException("존재하지 않는 게시물에 접근했습니다");
            }
        } else {
            // 정상적인 사용자가 아닐때
            throw new InvalidUserException();
        }

        return null;
    }

}
