package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.domain.Article;
import com.sammaru5.sammaru.domain.User;
import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.service.file.FileRegisterService;
import com.sammaru5.sammaru.service.file.FileRemoveService;
import com.sammaru5.sammaru.web.dto.ArticleDTO;
import com.sammaru5.sammaru.web.request.ArticleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@Service
@RequiredArgsConstructor
public class ArticleModifyService {
    private final ArticleRepository articleRepository;
    private final FileRemoveService fileRemoveService;
    private final FileRegisterService fileRegisterService;

    @CacheEvict(keyGenerator = "articleCacheKeyGenerator", value = "article", cacheManager = "cacheManager")
    public ArticleDTO modifyArticle(Long articleId, User findUser, Long boardId, ArticleRequest articleRequest, MultipartFile[] multipartFiles) throws CustomException {
        Optional<Article> findArticle = articleRepository.findById(articleId);
        if (findArticle.isPresent()) {
            Article article = findArticle.get();

            if(article.getUser() != findUser){ //작성자가 아닌 사람이 접근하려고 할때
                throw new CustomException(ErrorCode.UNAUTHORIZED_USER_ACCESS, findUser.getId().toString());
           }
            article.modifyArticle(articleRequest);

            if (multipartFiles != null) {
                fileRemoveService.removeFilesByArticle(article);
                fileRegisterService.addFiles(multipartFiles, article.getId());
                return ArticleDTO.toDto(article);
            }
        } else {
            // 존재하지 않는 게시글에 접근했을때
            throw new CustomException(ErrorCode.ARTICLE_NOT_FOUND, articleId.toString());
        }

        return null;
    }

}
