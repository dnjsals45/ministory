package seongmin.minilife.api.tag.service;

import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seongmin.minilife.common.response.code.TagErrorCode;
import seongmin.minilife.common.response.exception.TagErrorException;
import seongmin.minilife.domain.tag.dto.CountTagDto;
import seongmin.minilife.domain.tag.dto.GetTagRes;
import seongmin.minilife.domain.tag.entity.Tag;
import seongmin.minilife.domain.tag.repository.TagRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagUtilService {
    private final TagRepository tagRepository;

    public Tag findByTagName(String tagName) {
        return tagRepository.findByTagName(tagName)
                .orElseThrow(() -> new TagErrorException(TagErrorCode.INVALID_TAG));
    }

    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }

    public void saveTag(String tagName) {
        Tag tag = this.findByTagName(tagName);
        if (tag == null) {
            Tag newTag = Tag.builder()
                    .tagName(tagName)
                    .build();

            tagRepository.save(newTag);
        }
    }

    public List<CountTagDto> countTags() {
        return tagRepository.countTagsInContents();
    }
}
