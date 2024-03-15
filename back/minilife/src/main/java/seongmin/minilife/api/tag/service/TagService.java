package seongmin.minilife.api.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seongmin.minilife.domain.tag.dto.AllTagsRes;
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
}
