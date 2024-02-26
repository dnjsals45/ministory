package seongmin.minilife.common.auth.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import seongmin.minilife.api.comment.service.CommentUtilService;
import seongmin.minilife.api.content.service.ContentUtilService;
import seongmin.minilife.common.auth.dto.CustomUserDetails;

@Component
@RequiredArgsConstructor
public class AuthManager {
    private final ContentUtilService contentUtilService;
    private final CommentUtilService commentUtilService;

    public boolean isContentAuthor(Long contentId, CustomUserDetails userDetails) {
        return contentUtilService.existsByIdAndUserId(contentId, userDetails.getUserId());
    }

    public boolean isCommentAuthor(Long commentId, CustomUserDetails userDetails) {
        return commentUtilService.existsByIdAndUserId(commentId, userDetails.getUserId());
    }
}
