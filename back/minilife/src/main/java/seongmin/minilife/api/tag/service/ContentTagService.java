package seongmin.minilife.api.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seongmin.minilife.api.content.service.ContentUtilService;
import seongmin.minilife.domain.content.entity.Content;
import seongmin.minilife.domain.tag.dto.DeleteContentTagReq;
import seongmin.minilife.domain.tag.dto.GetAllContentTagsRes;
import seongmin.minilife.domain.tag.dto.SetContentTagReq;
import seongmin.minilife.domain.tag.entity.ContentTag;
import seongmin.minilife.domain.tag.entity.Tag;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentTagService {
    private final ContentUtilService contentUtilService;
    private final ContentTagUtilService contentTagUtilService;
    private final TagUtilService tagUtilService;

    public GetAllContentTagsRes getContentTags(Long contentId) {
        contentUtilService.findById(contentId);
        List<ContentTag> contentTags = contentTagUtilService.findAllByContentId(contentId);

        return GetAllContentTagsRes.from(contentTags);
    }

    public void addContentTag(Long contentId, SetContentTagReq req) {
        Content content = contentUtilService.findById(contentId);
        Tag tag = tagUtilService.findByTagName(req.getTagName());
        ContentTag newContentTag = ContentTag.builder()
                .content(content)
                .tag(tag)
                .build();

        contentTagUtilService.save(newContentTag);
    }

    @Transactional
    public void deleteContentTag(Long contentId, DeleteContentTagReq req) {
        contentUtilService.findById(contentId);
        Tag tag = tagUtilService.findByTagName(req.getTagName());
        contentTagUtilService.deleteByContentIdAndTagId(contentId, tag.getId());
    }
}
