package com.sammaru5.sammaru.service.comment;

import com.sammaru5.sammaru.domain.Comment;
import com.sammaru5.sammaru.domain.User;
import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.repository.CommentRepository;
import com.sammaru5.sammaru.repository.UserRepository;
import com.sammaru5.sammaru.web.dto.CommentDTO;
import com.sammaru5.sammaru.web.request.CommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class CommentModifyService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentDTO modifyComment(Long userId, Long commentId, CommentRequest commentRequest) throws CustomException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, userId.toString()));

        Optional<Comment> findComment = commentRepository.findById(commentId);

        if(findComment.isEmpty()) {
            throw new CustomException(ErrorCode.COMMENT_NOT_FOUND, commentId.toString());
        }
        if (findComment.get().getUser() != user) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER_ACCESS, user.getId().toString());
        }

        findComment.get().modifyContent(commentRequest);

        return new CommentDTO(commentRepository.save(findComment.get()));
    }
}
