package com.sammaru5.sammaru.service.article;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.domain.BoardEntity;
import com.sammaru5.sammaru.domain.UserEntity;
import com.sammaru5.sammaru.repository.ArticleRepository;
import com.sammaru5.sammaru.service.board.BoardStatusService;
import com.sammaru5.sammaru.service.file.FileRegisterService;
import com.sammaru5.sammaru.web.dto.ArticleDTO;
import com.sammaru5.sammaru.web.request.ArticleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@Service @RequiredArgsConstructor
public class ArticleRegisterService {
    private final ArticleRepository articleRepository;
    private final BoardStatusService boardStatusService;
    private final FileRegisterService fileRegisterService;

    public ArticleDTO addArticle(UserEntity findUser, Long boardId, ArticleRequest articleRequest, MultipartFile[] multipartFiles) throws NullPointerException {

        BoardEntity findBoard = boardStatusService.findBoard(boardId);
        ArticleEntity articleEntity = articleRepository.save(new ArticleEntity(articleRequest, findBoard, findUser));

        if(multipartFiles != null){
            fileRegisterService.addFiles(multipartFiles, articleEntity.getId());
        }
        
        return ArticleDTO.toDto(articleEntity);
    }
}
