package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.domain.Article;
import com.sammaru5.sammaru.domain.File;
import com.sammaru5.sammaru.domain.User;
import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.web.request.ArticleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@Service
@RequiredArgsConstructor
public class ArticleModifyService {
    private final ArticleRepository articleRepository;

    @Value("${app.fileDir}")
    private String fileDir;

    @CacheEvict(keyGenerator = "articleCacheKeyGenerator", value = "article", cacheManager = "cacheManager")
    public Long modifyArticle(Long articleId, User findUser, Long boardId, ArticleRequest articleRequest, MultipartFile[] multipartFiles) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND, articleId.toString()));

        if (article.getUser() != findUser) { //작성자가 아닌 사람이 접근하려고 할때
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER_ACCESS, findUser.getId().toString());
        }

        article.modifyArticle(articleRequest);

        if (multipartFiles != null) {
            article.removeFile();
            for(MultipartFile multipartFile : multipartFiles) {
                article.addFile(File.createFile(multipartFile, fileDir, boardId));
            }
        }
        return articleId;
    }

}
