package seongmin.ministory.api.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seongmin.ministory.api.content.service.ContentUtilService;
import seongmin.ministory.domain.tag.dto.*;
import seongmin.ministory.domain.tag.entity.Tag;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagUtilService tagUtilService;
    private final ContentUtilService contentUtilService;

    public AllTagsRes getAllTags() {
        List<Tag> tags = tagUtilService.findAllTags();

        return AllTagsRes.from(tags);
    }

    @Transactional
    public void registerTag(RegisterTagReq req) {
        tagUtilService.isExist(req.getTagName());
        tagUtilService.saveTag(req.getTagName());
    }

    public GetTagsCountRes countTags() {
        Long total = contentUtilService.countAllContents();

        return GetTagsCountRes.from(tagUtilService.countTags(), total);
    }
}
