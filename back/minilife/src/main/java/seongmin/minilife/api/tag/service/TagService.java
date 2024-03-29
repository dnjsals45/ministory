package seongmin.minilife.api.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seongmin.minilife.domain.tag.dto.*;
import seongmin.minilife.domain.tag.entity.Tag;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagUtilService tagUtilService;

    public AllTagsRes getAllTags() {
        List<Tag> tags = tagUtilService.findAllTags();

        return AllTagsRes.from(tags);
    }

    public void registerTag(RegisterTagReq req) {
        tagUtilService.saveTag(req.getTagName());
    }

    public GetTagsCountRes countTags() {
        return GetTagsCountRes.from(tagUtilService.countTags());
    }

}
