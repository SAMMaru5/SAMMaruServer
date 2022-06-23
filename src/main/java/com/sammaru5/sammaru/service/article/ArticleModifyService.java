package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.domain.UserEntity;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.service.file.FileRegisterService;
import com.sammaru5.sammaru.service.file.FileRemoveService;
import com.sammaru5.sammaru.web.dto.ArticleDTO;
import com.sammaru5.sammaru.web.request.ArticleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.NoSuchElementException;

@Transactional
@Service
@RequiredArgsConstructor
public class ArticleModifyService {
    private final ArticleRepository articleRepository;
    private final FileRemoveService fileRemoveService;
    private final FileRegisterService fileRegisterService;

    @CacheEvict(keyGenerator = "articleCacheKeyGenerator", value = "article", cacheManager = "cacheManager")
    public ArticleDTO modifyArticle(Long articleId, UserEntity user, Long boardId, ArticleRequest articleRequest, MultipartFile[] multipartFiles) throws AccessDeniedException, NullPointerException {
        ArticleEntity article = articleRepository.findById(articleId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 게시글입니다."));

        if (article.getUser() != user) { //작성자가 아닌 사람이 접근하려고 할때때
            throw new AccessDeniedException("해당 게시물에 권한이 없는 사용자 입니다");
        }

        article.modifyArticle(articleRequest);

        if (multipartFiles != null) {
            fileRemoveService.removeFilesByArticle(article);
            fileRegisterService.addFiles(multipartFiles, article.getId());
            return new ArticleDTO(article);
        }

        return null;
    }

}
