package seongmin.ministory.api.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seongmin.ministory.api.content.service.ContentUtilService;
import seongmin.ministory.domain.content.entity.Content;
import seongmin.ministory.domain.tag.dto.DeleteContentTagReq;
import seongmin.ministory.domain.tag.dto.GetAllContentTagsRes;
import seongmin.ministory.domain.tag.dto.SetContentTagReq;
import seongmin.ministory.domain.tag.entity.ContentTag;
import seongmin.ministory.domain.tag.entity.Tag;

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

        List<String> tags = req.getTags();
        if (tags.isEmpty()) {
            return;
        }
        for (String tagName : tags) {
            Tag tag = tagUtilService.findByTagName(tagName);
            if (tag == null) {
                throw new IllegalArgumentException("일치하는 태그가 없습니다");
            }
            ContentTag newContentTag = ContentTag.builder()
                    .content(content)
                    .tag(tag)
                    .build();

            contentTagUtilService.save(newContentTag);
        }
    }

    @Transactional
    public void deleteContentTag(Long contentId, DeleteContentTagReq req) {
        contentUtilService.findById(contentId);
        Tag tag = tagUtilService.findByTagName(req.getTagName());
        contentTagUtilService.deleteByContentIdAndTagId(contentId, tag.getId());
    }
}
