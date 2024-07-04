package seongmin.ministory.api.content.service;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import seongmin.ministory.api.tag.service.ContentTagService;
import seongmin.ministory.api.tag.service.ContentTagUtilService;
import seongmin.ministory.api.tag.service.TagUtilService;
import seongmin.ministory.api.user.service.UserUtilService;
import seongmin.ministory.common.auth.dto.CustomUserDetails;
import seongmin.ministory.common.response.code.ContentErrorCode;
import seongmin.ministory.common.response.exception.ContentErrorException;
import seongmin.ministory.common.viewerTrack.service.ViewerTrackService;
import seongmin.ministory.domain.content.dto.*;
import seongmin.ministory.domain.content.entity.Content;
import seongmin.ministory.domain.tag.entity.ContentTag;
import seongmin.ministory.domain.tag.entity.Tag;
import seongmin.ministory.domain.user.entity.User;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContentService {
    private final ContentUtilService contentUtilService;
    private final UserUtilService userUtilService;
    private final TagUtilService tagUtilService;
    private final ViewerTrackService viewerTrackService;
    private final ContentTagService contentTagService;
    private final S3Template s3Template;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public GetContentRes getContent(String uuid, String viewerId) {
        Content content = contentUtilService.findByUUID(uuid);

        if (!viewerTrackService.isExist(viewerId, content.getId())) {
            content.plusViewCount();
            viewerTrackService.save(viewerId, content.getId());
        }

        return GetContentRes.from(content);
    }

    @Transactional
    public CreateContentRes createContent(CustomUserDetails userDetails, PostContentReq req) {
        User user = userUtilService.findById(userDetails.getUserId());

        Content newContent = createNewContent(user, req);

        contentUtilService.save(newContent);

        contentTagService.addContentTag(newContent, req.getTags());

        return CreateContentRes.builder()
                .contentId(newContent.getId())
                .uuid(newContent.getUuid().toString())
                .build();
    }

//    @Caching(evict = {
//            @CacheEvict(value = "tempContents", allEntries = true),
//            @CacheEvict(value = "recentContents", condition = "#req.getComplete == true", allEntries = true)
//    })
    public PostContentRes modifyContent(String uuid, PostContentReq req) {

        Content content = contentUtilService.findByUUID(uuid);

        contentUtilService.save(content.update(req.getTitle(), req.getBody(), req.getComplete()));

        return PostContentRes.of(content.getId(), content.getUuid().toString(), content.getUpdatedAt());
    }

    public void deleteContent(String uuid) {
        Content content = contentUtilService.findByUUID(uuid);

        content.softDelete();
        contentUtilService.save(content);
    }

    @Transactional(readOnly = true)
    public AllContentsRes getContentsPage(Long pageNum, String tag, String keyword) {
        Page<Content> contentsPage;

        contentsPage = contentUtilService.searchContent(PageRequest.of(pageNum.intValue() - 1, 5), tag, keyword);

        List<Content> contents = contentsPage.getContent();

        return AllContentsRes.from(contents, contentsPage.getTotalPages());
    }

    @Transactional(readOnly = true)
//    @Cacheable(value = "recentContents")
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

    @Transactional(readOnly = true)
//    @Cacheable(value = "tempContents")
    public FindTempContentsRes getTempContents() {
        return FindTempContentsRes.from(contentUtilService.findTempContents());
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

    private Content createNewContent(User user, PostContentReq req) {
        return Content.builder()
                .user(user)
                .uuid(UUID.randomUUID())
                .title(req.getTitle())
                .body(req.getBody())
                .complete(req.getComplete())
                .views(0L)
                .build();
    }
}
