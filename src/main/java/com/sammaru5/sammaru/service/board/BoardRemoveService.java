package com.sammaru5.sammaru.service.board;

import com.sammaru5.sammaru.domain.BoardEntity;
import com.sammaru5.sammaru.repository.BoardRepository;
import com.sammaru5.sammaru.service.article.ArticleRemoveService;
import com.sammaru5.sammaru.service.user.UserSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class BoardRemoveService {

    private final BoardSearchService boardSearchService;
    private final ArticleRemoveService articleRemoveService;
    private final UserSearchService userSearchService;
    private final BoardRepository boardRepository;

    /**
     * 게시판과 그에 달린 게시글들을 삭제하는 메서드
     * 관리자인지 권한을 확인해야함
     * @param authentication
     * @param boardId
     * @return
     * @throws Exception
     */
    public boolean removeBoard(Authentication authentication, Long boardId) throws Exception {
        boolean isAdmin = userSearchService.verifyAdmin(authentication);
        if(isAdmin) { // 관리자 권한이 있으면
            articleRemoveService.removeArticleByAdmin(boardId);
            boardRepository.deleteById(boardId);
            return true;
        } else {
            return false;
        }

    }
}
