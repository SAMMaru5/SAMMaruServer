package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.domain.Article;
import com.sammaru5.sammaru.domain.Board;
import com.sammaru5.sammaru.domain.File;
import com.sammaru5.sammaru.domain.User;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.service.board.BoardStatusService;
import com.sammaru5.sammaru.service.file.FileRegisterService;
import com.sammaru5.sammaru.web.dto.ArticleDTO;
import com.sammaru5.sammaru.web.request.ArticleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@Service @RequiredArgsConstructor
public class ArticleRegisterService {
    private final ArticleRepository articleRepository;
    private final BoardStatusService boardStatusService;
    private final FileRegisterService fileRegisterService;

    @Value("${app.fileDir}")
    private String fileDir;

    public ArticleDTO addArticle(User findUser, Long boardId, ArticleRequest articleRequest, MultipartFile[] multipartFiles) {

        Board board = boardStatusService.findBoard(boardId);
        Article article = Article.createArticle(articleRequest, board, findUser);

        if(multipartFiles != null){
            fileRegisterService.addFiles(multipartFiles, article.getId());
        }

        articleRepository.save(article);
        return ArticleDTO.toDto(article);
    }
}
