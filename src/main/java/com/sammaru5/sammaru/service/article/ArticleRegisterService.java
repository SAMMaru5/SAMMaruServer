package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.domain.*;
import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.repository.BoardRepository;
import com.sammaru5.sammaru.repository.UserRepository;
import com.sammaru5.sammaru.web.dto.ArticleDTO;
import com.sammaru5.sammaru.web.request.ArticleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ArticleRegisterService {
    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Value("${sammaru.fileDir}")
    private String fileDir;

    @Transactional
    public ArticleDTO addArticle(Long userId, Long boardId, ArticleRequest articleRequest, MultipartFile[] multipartFiles) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, userId.toString()));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND, boardId.toString()));

        Article article = Article.createArticle(articleRequest, board, findUser);

        if (multipartFiles != null) {
            for (MultipartFile multipartFile : multipartFiles) {
                article.addFile(File.createFile(multipartFile, fileDir, boardId));
            }
        }

        return ArticleDTO.from(articleRepository.save(article));
    }
}
