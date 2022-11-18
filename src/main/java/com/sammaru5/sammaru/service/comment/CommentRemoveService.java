package com.sammaru5.sammaru.service.comment;

import com.sammaru5.sammaru.domain.Comment;
import com.sammaru5.sammaru.domain.User;
import com.sammaru5.sammaru.domain.UserAuthority;
import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.repository.CommentRepository;
import com.sammaru5.sammaru.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentRemoveService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public boolean removeComment(Long userId, Long commentId) throws CustomException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, userId.toString()));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND, commentId.toString()));

        if (!comment.isWrittenBy(user) && !user.isAdmin()) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER_ACCESS, user.getId().toString());
        }

        commentRepository.deleteById(commentId);
        return true;
    }
}