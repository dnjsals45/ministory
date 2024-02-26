package seongmin.minilife.api.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import seongmin.minilife.api.comment.service.CommentService;
import seongmin.minilife.common.auth.dto.CustomUserDetails;
import seongmin.minilife.common.response.SuccessResponse;
import seongmin.minilife.domain.comment.dto.CreateCommentReq;
import seongmin.minilife.domain.comment.dto.ModifyCommentReq;

@Tag(name = "댓글 API", description = "댓글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contents/{content_id}/comments")
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "댓글 1개 조회", description = "content_id 게시글의 모든 댓글 조회")
    @GetMapping("")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<?> getComments(@Parameter(name = "content_id", description = "게시글 번호")
                                         @PathVariable(name = "content_id") Long contentId) {
        return ResponseEntity.ok().body(SuccessResponse.from(commentService.getComments(contentId)));
    }

    @Operation(summary = "댓글 생성", description = "content_id 게시글에 댓글 1개 생성")
    @PostMapping("")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createComment(@Parameter(name = "content_id", description = "게시글 번호")
                                           @PathVariable(name = "content_id") Long contentId,
                                           @AuthenticationPrincipal CustomUserDetails userDetails,
                                           @RequestBody CreateCommentReq req) {
        return ResponseEntity.ok().body(SuccessResponse.from(commentService.createComment(contentId, userDetails, req)));
    }

    @Operation(summary = "댓글 수정", description = "comment_id와 일치하는 댓글 내용 수정")
    @PatchMapping("/{comment_id}")
    @PreAuthorize("isAuthenticated() && @authManager.isCommentAuthor(#commentId, authentication.getPrincipal())")
    public ResponseEntity<?> modifyComment(@Parameter(name = "content_id", description = "게시글 번호")
                                           @PathVariable(name = "content_id") Long contentId,
                                           @Parameter(name = "comment_id", description = "댓글 번호")
                                           @PathVariable(name = "comment_id") Long commentId,
                                           @AuthenticationPrincipal CustomUserDetails userDetails,
                                           ModifyCommentReq req) {
        return ResponseEntity.ok().body(SuccessResponse.from(commentService.modifyComment(contentId, commentId, req)));
    }

    @Operation(summary = "댓글 삭제", description = "데이터 보존을 위해 soft delete")
    @DeleteMapping("/{comment_id}")
    @PreAuthorize("isAuthenticated() && @authManager.isCommentAuthor(#commentId, authentication.getPrincipal())")
    public ResponseEntity<?> deleteComment(@Parameter(name = "content_id", description = "게시글 번호")
                                           @PathVariable(name = "content_id") Long contentId,
                                           @Parameter(name = "comment_id", description = "댓글 번호")
                                           @PathVariable(name = "comment_id") Long commentId,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        commentService.deleteComment(contentId, commentId);

        return ResponseEntity.ok().body(SuccessResponse.noContent());
    }
}
