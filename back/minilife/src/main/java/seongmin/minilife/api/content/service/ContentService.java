package seongmin.minilife.api.content.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seongmin.minilife.api.user.service.UserUtilService;
import seongmin.minilife.common.auth.dto.CustomUserDetails;
import seongmin.minilife.domain.content.dto.GetContentRes;
import seongmin.minilife.domain.content.dto.ModifyContentReq;
import seongmin.minilife.domain.content.dto.ModifyContentRes;
import seongmin.minilife.domain.content.entity.Content;
import seongmin.minilife.domain.user.entity.User;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentUtilService contentUtilService;
    private final UserUtilService userUtilService;

    @Transactional
    public GetContentRes getContent(Long contentId) {
        Content content = contentUtilService.findById(contentId);

        return GetContentRes.from(content);
    }

    public Long createContent(CustomUserDetails userDetails) {
        User user = userUtilService.findById(userDetails.getUserId());

        Content newContent = Content.builder()
                .user(user)
                .title("")
                .body("")
                .complete(false)
                .views(0L)
                .build();

        contentUtilService.save(newContent);

        return newContent.getId();
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
}
