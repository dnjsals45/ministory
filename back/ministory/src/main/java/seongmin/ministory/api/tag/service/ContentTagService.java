package seongmin.ministory.api.tag.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ContentTagService {
    private final ContentUtilService contentUtilService;
    private final ContentTagUtilService contentTagUtilService;
    private final TagUtilService tagUtilService;

    public GetAllContentTagsRes getContentTags(String uuid) {
        Content content = contentUtilService.findByUUID(uuid);
        List<ContentTag> contentTags = contentTagUtilService.findAllByContentId(content.getId());

        return GetAllContentTagsRes.from(contentTags);
    }

    public void modifyContentTag(Content content, List<String> tags) {
        List<ContentTag> contentTags = contentTagUtilService.findAllByContentId(content.getId());

        for (ContentTag contentTag : contentTags) {
            Tag tag = contentTag.getTag();
            if (!tags.contains(tag.getTagName())) {
                contentTagUtilService.deleteByContentIdAndTagId(content.getId(), tag.getId());
            } else {
                tags.remove(tag.getTagName());
            }
        }

        if (tags == null || tags.isEmpty()) {
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

    public void deleteAllContentTagWithContentId(Long id) {
        contentTagUtilService.deleteAllByContentId(id);
    }
}
