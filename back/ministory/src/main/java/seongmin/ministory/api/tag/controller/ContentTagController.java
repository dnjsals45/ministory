package seongmin.ministory.api.tag.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import seongmin.ministory.api.tag.service.ContentTagService;
import seongmin.ministory.common.response.SuccessResponse;
import seongmin.ministory.domain.tag.dto.DeleteContentTagReq;
import seongmin.ministory.domain.tag.dto.SetContentTagReq;

@Tag(name = "게시글 태그 API", description = "게시글에 태그 설정 CRUD")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contents/{content_id}/tags")
public class ContentTagController {
    private final ContentTagService contentTagService;

    @Operation(summary = "게시글 태그 조회", description = "게시글 1개에 등록된 태그 모두 반환")
    @GetMapping("")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getContentTags(@PathVariable(name = "content_id") Long contentId) {
        return ResponseEntity.ok().body(SuccessResponse.from(contentTagService.getContentTags(contentId)));
    }

    @Operation(summary = "게시글 태그 등록", description = "게시글 1개에 태그 등록")
    @PostMapping("")
    @PreAuthorize("isAuthenticated() && @authManager.isContentAuthor(#contentId, authentication.getPrincipal())")
    public ResponseEntity<?> setContentTag(@PathVariable(name = "content_id") Long contentId,
                                           @RequestBody SetContentTagReq req) {
        contentTagService.addContentTag(contentId, req);
        return ResponseEntity.ok().body(SuccessResponse.noContent());
    }

    @Operation(summary = "게시글 태그 해제", description = "게시글에 등록되어 있는 태그 해제")
    @PostMapping("/delete")
    @PreAuthorize("isAuthenticated() && @authManager.isContentAuthor(#contentId, authentication.getPrincipal())")
    public ResponseEntity<?> deleteContentTag(@PathVariable(name = "content_id") Long contentId,
                                              @RequestBody DeleteContentTagReq req) {
        contentTagService.deleteContentTag(contentId, req);
        return ResponseEntity.ok().body(SuccessResponse.noContent());
    }
}
