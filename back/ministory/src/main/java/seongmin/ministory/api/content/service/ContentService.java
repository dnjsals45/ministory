package seongmin.ministory.api.content.service;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import seongmin.ministory.api.tag.service.TagUtilService;
import seongmin.ministory.api.user.service.UserUtilService;
import seongmin.ministory.common.auth.dto.CustomUserDetails;
import seongmin.ministory.common.response.code.ContentErrorCode;
import seongmin.ministory.common.response.exception.ContentErrorException;
import seongmin.ministory.domain.content.dto.*;
import seongmin.ministory.domain.content.entity.Content;
import seongmin.ministory.domain.user.entity.User;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentUtilService contentUtilService;
    private final UserUtilService userUtilService;
    private final TagUtilService tagUtilService;
    private final S3Template s3Template;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;


    @Transactional
    public GetContentRes getContent(Long contentId) {
        Content content = contentUtilService.findById(contentId);

        content.plusViewCount();

        return GetContentRes.from(content);
    }

    public CreateContentRes createContent(CustomUserDetails userDetails) {
        User user = userUtilService.findById(userDetails.getUserId());

        Content newContent = Content.builder()
                .user(user)
                .title("")
                .body("")
                .complete(false)
                .views(0L)
                .build();

        contentUtilService.save(newContent);

        return CreateContentRes.builder()
                .contentId(newContent.getId())
                .build();
    }

    public ModifyContentRes modifyContent(Long contentId, ModifyContentReq req) {
        Content content = contentUtilService.findById(contentId);

        contentUtilService.save(content.update(req.getTitle(), req.getBody(), req.getComplete()));

        return ModifyContentRes.of(content.getId(), content.getUpdatedAt());
    }

    public void deleteContent(Long contentId) {
        Content content = contentUtilService.findById(contentId);

        content.softDelete();
        contentUtilService.save(content);
    }

    @Transactional(readOnly = true)
    public AllContentsRes getContentsPage(Long pageNum) {
        Page<Content> contentsPage = contentUtilService.findContentPages(PageRequest.of(pageNum.intValue() - 1,  5));
        List<Content> contents = contentsPage.getContent();

        return AllContentsRes.from(contents, contentsPage.getTotalPages());
    }

    @Transactional(readOnly = true)
    public RecentContentsRes getRecentContents() {
        List<Content> contents = contentUtilService.findRecentContentsWithTags();

        return RecentContentsRes.from(contents);
    }

    @Transactional(readOnly = true)
    public AllContentsRes getTagContents(String tagName, Long pageNum) {
        tagUtilService.findByTagName(tagName);

        Page<Content> contentsPage = contentUtilService.findTagContents(tagName, PageRequest.of(pageNum.intValue() - 1, 5));
        List<Content> contents = contentsPage.getContent();

        return AllContentsRes.from(contents, contentsPage.getTotalPages());
    }

    public UploadImageRes uploadImage(MultipartFile image) throws IOException {
        if (image.isEmpty()) {
            throw new ContentErrorException(ContentErrorCode.EMPTY_IMAGE);
        }

        String originalFilename = image.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        String saveFilename = uuid + "." + extension;

        S3Resource s3Resource = s3Template.upload(bucket, saveFilename, image.getInputStream(), ObjectMetadata.builder().contentType(extension).build());

        return UploadImageRes.builder()
                .imageUrl(s3Resource.getURL().toString())
                .build();
    }
}
