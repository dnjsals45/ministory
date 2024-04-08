package seongmin.ministory.api.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seongmin.ministory.domain.comment.entity.Comment;
import seongmin.ministory.domain.comment.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentUtilService {
    private final CommentRepository commentRepository;

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException("댓글이 존재하지 않습니다."));
    }

    public boolean existsByIdAndUserId(Long commentId, Long userId) {
        return commentRepository.existsByIdAndUserId(commentId, userId);
    }

    public List<Comment> findCommentsByContentId(Long contentId) {
        return commentRepository.findCommentsByContentId(contentId);
    }

    public Comment save(Comment comment) {
        commentRepository.save(comment);

        return comment;
    }

}
