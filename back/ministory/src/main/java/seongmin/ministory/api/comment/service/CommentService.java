package seongmin.ministory.api.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seongmin.ministory.api.content.service.ContentUtilService;
import seongmin.ministory.api.user.service.UserUtilService;
import seongmin.ministory.common.auth.dto.CustomUserDetails;
import seongmin.ministory.domain.comment.dto.*;
import seongmin.ministory.domain.comment.entity.Comment;
import seongmin.ministory.domain.content.entity.Content;
import seongmin.ministory.domain.user.entity.User;

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
