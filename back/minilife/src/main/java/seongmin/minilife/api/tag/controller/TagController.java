package seongmin.minilife.api.tag.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seongmin.minilife.api.tag.service.TagService;
import seongmin.minilife.common.response.SuccessResponse;

@Tag(name = "태그 API", description = "태그 등록 등..")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tags")
public class TagController {
    private final TagService tagService;

    @Operation(summary = "태그 조회", description = "존재하는 모든 태그 목록 반환")
    @GetMapping("")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getAllTags() {
        return ResponseEntity.ok().body(SuccessResponse.from(tagService.getAllTags()));
    }
}
