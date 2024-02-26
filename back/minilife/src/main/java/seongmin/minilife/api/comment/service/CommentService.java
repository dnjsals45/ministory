package seongmin.minilife.api.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seongmin.minilife.api.content.service.ContentUtilService;
import seongmin.minilife.api.user.service.UserUtilService;
import seongmin.minilife.common.auth.dto.CustomUserDetails;
import seongmin.minilife.domain.comment.dto.*;
import seongmin.minilife.domain.comment.entity.Comment;
import seongmin.minilife.domain.content.entity.Content;
import seongmin.minilife.domain.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserUtilService userUtilService;
    private final ContentUtilService contentUtilService;
    private final CommentUtilService commentUtilService;

    public List<GetCommentRes> getComments(Long contentId) {
        contentUtilService.findById(contentId);

        List<Comment> comments = commentUtilService.findCommentsByContentId(contentId);
        List<GetCommentRes> response = new ArrayList<>();

        for (Comment comment : comments) {
            GetCommentRes dto = GetCommentRes.fromEntity(comment);
            response.add(dto);
        }

        return response;
    }

    public CreateCommentRes createComment(Long contentId, CustomUserDetails userDetails, CreateCommentReq req) {
        User user = userUtilService.findById(userDetails.getUserId());
        Content content = contentUtilService.findById(contentId);

        Comment newComment = Comment.builder()
                .user(user)
                .content(content)
                .comment(req.getComment())
                .deletedAt(null)
                .build();

        commentUtilService.save(newComment);

        return CreateCommentRes.from(newComment);
    }

    public ModifyCommentRes modifyComment(Long contentId, Long commentId, ModifyCommentReq req) {
        contentUtilService.findById(contentId);

        Comment comment = commentUtilService.findById(commentId);

        comment.update(req.getComment());

        return ModifyCommentRes.of(comment.getId(), comment.getUpdatedAt());
    }

    public void deleteComment(Long contentId, Long commentId) {
        contentUtilService.findById(contentId);

        Comment comment = commentUtilService.findById(commentId);

        comment.softDelete();
    }
}
