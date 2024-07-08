package seongmin.ministory.api.content.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import seongmin.ministory.api.content.service.ContentService;
import seongmin.ministory.common.auth.dto.CustomUserDetails;
import seongmin.ministory.common.response.SuccessResponse;
import seongmin.ministory.domain.content.dto.PostContentReq;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Tag(name = "게시글 API", description = "게시글 관련 API")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/contents")
public class ContentController {
    private final ContentService contentService;

    // GET
    @Operation(summary = "최근 게시물 9개 조회", description = "9개까지만 보여주기")
    @GetMapping("/recent")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getRecentContent() {
        return ResponseEntity.ok().body(SuccessResponse.from(contentService.getRecentContents()));
    }

    @Operation(summary = "게시글 페이지 조회(페이징 기능)", description = "페이지 번호에 맞게 가져오기")
    @GetMapping("")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getContentsPage(@RequestParam(name = "page") Long pageNum,
                                             @RequestParam(name = "tag", required = false) String tag,
                                             @RequestParam(name = "keyword", required = false) String keyword) {
        pageNum = pageNum == null ? 1 : pageNum;
        return ResponseEntity.ok().body(SuccessResponse.from(contentService.getContentsPage(pageNum, tag, keyword)));
    }

    @Operation(summary = "게시글 1개 조회", description = "content_id가 일치하는 게시글 1개 조회")
    @GetMapping("/{content_uuid}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getContent(@Parameter(name = "content_uuid", description = "게시글 uuid")
                                        @PathVariable(name = "content_uuid") String uuid,
                                        HttpServletRequest request) {
        return ResponseEntity.ok().body(SuccessResponse.from(contentService.getContent(uuid, (String) request.getAttribute("viewerId"))));
    }

    @Operation(summary = "임시저장된 게시글 조회", description = "임시저장된 게시글들을 List형태로 반환")
    @GetMapping("/temp")
    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getTempContents() {
        return ResponseEntity.ok().body(SuccessResponse.from(contentService.getTempContents()));
    }

    @Operation(summary = "태그가 달려있는 게시물만 반환", description = "spring일 경우 spring태그가 있는 게시물만 반환")
    @GetMapping("/tags/{tag_name}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getTagContents(@PathVariable(name = "tag_name") String tagName,
                                            @RequestParam(name = "page") Long pageNum) {
        pageNum = pageNum == null ? 1 : pageNum;
        return ResponseEntity.ok().body(SuccessResponse.from(contentService.getTagContents(tagName, pageNum)));
    }

    @Operation(summary = "게시글 이미지 렌더링", description = "프론트 callback 함수에서 들어와 렌더링된 이미지 데이터를 가져가게 됨")
    @GetMapping(value = "/image-print", produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public byte[] printEditorImage(@RequestParam String filepath) throws IOException {
        File uploadedFile = new File(filepath);

        return Files.readAllBytes(uploadedFile.toPath());
    }

    // POST
    @Operation(summary = "게시글 1개 생성", description = "프론트에서 날아오는 정보를 바탕으로 게시글 생성")
    @PostMapping("")
    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createContent(@AuthenticationPrincipal CustomUserDetails userDetails,
                                           @Valid @RequestBody PostContentReq req) {
        return ResponseEntity.ok().body(SuccessResponse.from(contentService.createContent(userDetails, req)));
    }

    @Operation(summary = "게시글 이미지 업로드", description = "게시글에 추가되는 이미지를 업로드")
    @PostMapping("/image-upload")
    @PreAuthorize("isAuthenticated() && #userDetails.getRole() == 'ROLE_ADMIN'")
    public ResponseEntity<?> uploadImage(@RequestParam MultipartFile image) throws IOException {
        return ResponseEntity.ok().body(SuccessResponse.from(contentService.uploadImage(image)));
    }


    // PATCH
    @Operation(summary = "게시글 1개 수정", description = "complete가 true면 완성, 아니면 임시저장")
    @PatchMapping("/{content_uuid}")
    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> modifyContent(@Parameter(name = "content_uuid", description = "게시글 uuid")
                                           @PathVariable(name = "content_uuid") String uuid,
                                           @Valid @RequestBody PostContentReq req,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok().body(SuccessResponse.from(contentService.modifyContent(uuid, req)));
    }


    // DELETE
    @Operation(summary = "게시글 1개 삭제", description = "soft delete로 복구할 수 있도록 설정")
    @DeleteMapping("/{content_uuid}")
    @PreAuthorize("isAuthenticated() && @authManager.isContentAuthor(#uuid, authentication.getPrincipal())")
    public ResponseEntity<?> deleteContent(@Parameter(name = "content_uuid", description = "게시글 uuid")
                                           @PathVariable(name = "content_uuid") String uuid) {
        contentService.deleteContent(uuid);
        return ResponseEntity.ok().body(SuccessResponse.noContent());
    }
}
