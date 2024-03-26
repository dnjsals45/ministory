package seongmin.minilife.api.content.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seongmin.minilife.api.tag.service.ContentTagUtilService;
import seongmin.minilife.api.user.service.UserUtilService;
import seongmin.minilife.common.auth.dto.CustomUserDetails;
import seongmin.minilife.domain.content.dto.*;
import seongmin.minilife.domain.content.entity.Content;
import seongmin.minilife.domain.tag.entity.ContentTag;
import seongmin.minilife.domain.user.entity.User;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentUtilService contentUtilService;
    private final UserUtilService userUtilService;

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
}
