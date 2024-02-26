package seongmin.minilife.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seongmin.minilife.domain.comment.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    boolean existsByIdAndUserId(Long commentId, Long userId);

    List<Comment> findCommentsByContentId(Long contentId);
}
