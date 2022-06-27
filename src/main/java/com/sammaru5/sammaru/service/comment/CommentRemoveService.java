package com.sammaru5.sammaru.service.comment;

import com.sammaru5.sammaru.domain.CommentEntity;
import com.sammaru5.sammaru.domain.UserEntity;
import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.repository.CommentRepository;
import com.sammaru5.sammaru.service.user.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class CommentRemoveService {

    private final CommentRepository commentRepository;
    private final UserStatusService userStatusService;

    public boolean removeComment(Authentication authentication, Long commentId) throws CustomException {
        UserEntity user = userStatusService.getUser(authentication);
        Optional<CommentEntity> findComment = commentRepository.findById(commentId);
        if(findComment.isPresent()) {
            if (findComment.get().getUser() == user) {
                commentRepository.deleteById(commentId);
                return true;
            } else {
                throw new CustomException(ErrorCode.UNAUTHORIZED_USER_ACCESS, user.getId().toString());
            }
        } else {
            throw new CustomException(ErrorCode.COMMENT_NOT_FOUND, commentId.toString());
        }
    }
}
