package seongmin.ministory.common.auth.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import seongmin.ministory.api.comment.service.CommentUtilService;
import seongmin.ministory.api.content.service.ContentUtilService;
import seongmin.ministory.common.auth.dto.CustomUserDetails;

@Component
@RequiredArgsConstructor
public class AuthManager {
    private final ContentUtilService contentUtilService;
    private final CommentUtilService commentUtilService;

    public boolean isContentAuthor(String uuid, CustomUserDetails userDetails) {
        return contentUtilService.existsByUuidAndUserId(uuid, userDetails.getUserId());
    }

    public boolean isCommentAuthor(Long commentId, CustomUserDetails userDetails) {
        return commentUtilService.existsByIdAndUserId(commentId, userDetails.getUserId());
    }
}
