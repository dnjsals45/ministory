package seongmin.minilife.api.content.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import seongmin.minilife.api.content.service.ContentService;
import seongmin.minilife.common.auth.dto.CustomUserDetails;
import seongmin.minilife.common.response.SuccessResponse;
import seongmin.minilife.domain.content.dto.ModifyContentReq;

@Tag(name = "게시글 API", description = "게시글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contents")
public class ContentController {
    private final ContentService contentService;

    @Operation(summary = "게시글 1개 조회", description = "content_id가 일치하는 게시글 1개 조회")
    @GetMapping("/{content_id}")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<?> getContent(@Parameter(name = "content_id", description = "게시글 번호")
                                        @PathVariable(name = "content_id") Long contentId) {
        return ResponseEntity.ok().body(SuccessResponse.from(contentService.getContent(contentId)));
    }

    @Operation(summary = "게시글 1개 생성", description = "기본적인 내용만 있는 게시글 생성")
    @PostMapping("")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createContent(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok().body(SuccessResponse.from(contentService.createContent(userDetails)));
    }

    @Operation(summary = "게시글 1개 수정", description = "complete가 true면 완성, 아니면 임시저장")
    @PatchMapping("/{content_id}")
    @PreAuthorize("isAuthenticated() && @authManager.isContentAuthor(#contentId, authentication.getPrincipal())")
    public ResponseEntity<?> modifyContent(@Parameter(name = "content_id", description = "게시글 번호")
                                           @PathVariable(name = "content_id") Long contentId,
                                           @Valid @RequestBody ModifyContentReq req,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok().body(SuccessResponse.from(contentService.modifyContent(contentId, req)));
    }

    @Operation(summary = "게시글 1개 삭제", description = "soft delete로 복구할 수 있도록 설정")
    @DeleteMapping("/{content_id}")
    @PreAuthorize("isAuthenticated() && @authManager.isContentAuthor(#contentId, authentication.getPrincipal())")
    public ResponseEntity<?> deleteContent(@Parameter(name = "content_id", description = "게시글 번호")
                                           @PathVariable(name = "content_id") Long contentId) {
        contentService.deleteContent(contentId);
        return ResponseEntity.ok().body(SuccessResponse.noContent());
    }
}
