package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.domain.UserEntity;
import com.sammaru5.sammaru.dto.ArticleDTO;
import com.sammaru5.sammaru.exception.InvalidUserException;
import com.sammaru5.sammaru.exception.NonExistentAritcleException;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.service.user.UserSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleRemoveService {

    private final ArticleRepository articleRepository;
    private final ArticleSearchService articleSearchService;
    private final UserSearchService userSearchService;

    /**
     * 작성자가 게시글 삭제
     */
    public boolean removeArticleByAuthor(Authentication authentication, Long boardId, Long articleId) throws Exception {
        UserEntity findUser = userSearchService.getUserFromToken(authentication);
        if(findUser == null) {
            throw new InvalidUserException();
        }
        Optional<ArticleEntity> findArticle = articleRepository.findById(articleId);
        if(findArticle.isPresent()) {
            if(findArticle.get().getUser().getUsername().equals(findUser.getUsername())) {
                articleRepository.deleteById(articleId);
                return true;
            } else {
                throw new InvalidUserException();
            }
        } else {
            throw new NonExistentAritcleException();
        }

    }

    /**
     * Admin이 게시글 삭제
     * @param boardId
     * @return
     * @throws Exception
     */
    public boolean removeArticleByAdmin(Long boardId) throws Exception {
        List<ArticleDTO> articles = articleSearchService.findAllByBoardId(boardId);
        if (articles.isEmpty()) {
            throw new NonExistentAritcleException();
        }
        List<Long> ids = new ArrayList<>();
        for (ArticleDTO a : articles) {
            ids.add(a.getId());
        }
        articleRepository.deleteAllByIdInQuery(ids);
        return true;
    }

}

