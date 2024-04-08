package seongmin.ministory.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seongmin.ministory.domain.comment.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    boolean existsByIdAndUserId(Long commentId, Long userId);

    List<Comment> findCommentsByContentId(Long contentId);
}
