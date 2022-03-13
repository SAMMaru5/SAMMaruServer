package com.sammaru5.sammaru.service.board;

import com.sammaru5.sammaru.exception.NoPermissionException;
import com.sammaru5.sammaru.request.BoardRequest;
import com.sammaru5.sammaru.domain.BoardEntity;
import com.sammaru5.sammaru.exception.AlreadyExistBoardnameException;
import com.sammaru5.sammaru.repository.BoardRepository;
import com.sammaru5.sammaru.service.user.UserSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardRegisterService {

    private final BoardRepository boardRepository;
    private final UserSearchService userSearchService;

    public BoardEntity addBoard(Authentication authentication, BoardRequest boardRequest) throws Exception {
        String boardname = boardRequest.getBoardname();
        BoardEntity findBoard = boardRepository.findByBoardname(boardname);
        if(userSearchService.verifyAdmin(authentication)) {
            if(findBoard == null) {
                return boardRepository.save(BoardEntity.builder()
                        .boardname(boardRequest.getBoardname())
                        .description(boardRequest.getDescription())
                        .build());
            } else {
                throw new AlreadyExistBoardnameException();
            }
        } else {
            throw new NoPermissionException();
        }
    }
}
