package seongmin.minilife.api.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seongmin.minilife.api.content.service.ContentUtilService;
import seongmin.minilife.domain.content.entity.Content;
import seongmin.minilife.domain.tag.dto.SetContentTagReq;
import seongmin.minilife.domain.tag.entity.ContentTag;

@Service
@RequiredArgsConstructor
public class ContentTagService {
    private final ContentUtilService contentUtilService;
    private final ContentTagUtilService contentTagUtilService;
    private final TagUtilService tagUtilService;

    public void addTag(Long contentId, SetContentTagReq req) {
        Content content = contentUtilService.findById(contentId);
        ContentTag newContentTag = ContentTag.builder()
                .content(content)
                .tag(req.getTagName())
                .build();

        contentTagUtilService.save(newContentTag);
        tagUtilService.saveContentTag(req.getTagName());
    }
}
