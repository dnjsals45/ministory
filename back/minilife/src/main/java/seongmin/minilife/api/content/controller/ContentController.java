package seongmin.minilife.api.content.controller;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import seongmin.minilife.api.content.service.ContentService;
import seongmin.minilife.common.auth.dto.CustomUserDetails;
import seongmin.minilife.common.response.SuccessResponse;
import seongmin.minilife.common.response.code.ContentErrorCode;
import seongmin.minilife.common.response.exception.ContentErrorException;
import seongmin.minilife.domain.content.dto.ModifyContentReq;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Tag(name = "게시글 API", description = "게시글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contents")
public class ContentController {
    private final ContentService contentService;


    @Operation(summary = "최근 게시물 9개 조회", description = "9개까지만 보여주기")
    @GetMapping("/recent")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getRecentContent() {
        return ResponseEntity.ok().body(SuccessResponse.from(contentService.getRecentContents()));
    }

    @Operation(summary = "게시글 페이지 조회(페이징 기능)", description = "페이지 번호에 맞게 가져오기")
    @GetMapping("")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getContentsPage(@RequestParam(name = "page") Long pageNum) {
        pageNum = pageNum == null ? 1 : pageNum;
        return ResponseEntity.ok().body(SuccessResponse.from(contentService.getContentsPage(pageNum)));
    }

    @Operation(summary = "게시글 1개 조회", description = "content_id가 일치하는 게시글 1개 조회")
    @GetMapping("/{content_id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getContent(@Parameter(name = "content_id", description = "게시글 번호")
                                        @PathVariable(name = "content_id") Long contentId) {
        return ResponseEntity.ok().body(SuccessResponse.from(contentService.getContent(contentId)));
    }

    @Operation(summary = "게시글 1개 생성", description = "기본적인 내용만 있는 게시글 생성")
    @PostMapping("")
    @PreAuthorize("isAuthenticated() && #userDetails.getRole() == 'ROLE_ADMIN'")
    public ResponseEntity<?> createContent(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok().body(SuccessResponse.from(contentService.createContent(userDetails)));
    }

    @Operation(summary = "게시글 1개 수정", description = "complete가 true면 완성, 아니면 임시저장")
    @PatchMapping("/{content_id}")
    @PreAuthorize("isAuthenticated() && #userDetails.getRole() == 'ROLE_ADMIN'")
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

    @Operation(summary = "태그가 달려있는 게시물만 반환", description = "spring일 경우 spring태그가 있는 게시물만 반환")
    @GetMapping("/tags/{tag_name}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getTagContents(@PathVariable(name = "tag_name") String tagName,
                                             @RequestParam(name = "page") Long pageNum) {
        pageNum = pageNum == null ? 1 : pageNum;
        return ResponseEntity.ok().body(SuccessResponse.from(contentService.getTagContents(tagName, pageNum)));
    }

    @Operation(summary = "게시글 이미지 업로드", description = "게시글에 추가되는 이미지를 업로드")
    @PostMapping("/image-upload")
//    @PreAuthorize("isAuthenticated() && #userDetails.getRole() == 'ROLE_ADMIN'")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> uploadImage(@RequestParam MultipartFile image) throws IOException {
        return ResponseEntity.ok().body(SuccessResponse.from(contentService.uploadImage(image)));
    }

    @Operation(summary = "게시글 이미지 렌더링", description = "프론트 callback 함수에서 들어와 렌더링된 이미지 데이터를 가져가게 됨")
    @GetMapping(value = "/image-print", produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public byte[] printEditorImage(@RequestParam String filepath) throws IOException {
        File uploadedFile = new File(filepath);

        return Files.readAllBytes(uploadedFile.toPath());
    }
}
