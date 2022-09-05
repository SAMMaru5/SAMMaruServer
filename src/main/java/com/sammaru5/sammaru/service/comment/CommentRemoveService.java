package com.sammaru5.sammaru.service.comment;

import com.sammaru5.sammaru.domain.Comment;
import com.sammaru5.sammaru.domain.User;
import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.repository.CommentRepository;
import com.sammaru5.sammaru.repository.UserRepository;
import com.sammaru5.sammaru.service.user.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentRemoveService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public boolean removeComment(String studentId, Long commentId) throws CustomException {
        User user = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, studentId));
        Optional<Comment> findComment = commentRepository.findById(commentId);
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
