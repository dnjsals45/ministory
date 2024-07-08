package seongmin.ministory.api.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seongmin.ministory.common.response.code.TagErrorCode;
import seongmin.ministory.common.response.exception.TagErrorException;
import seongmin.ministory.domain.tag.dto.CountTagDto;
import seongmin.ministory.domain.tag.entity.Tag;
import seongmin.ministory.domain.tag.repository.TagRepository;

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
        Tag newTag = Tag.builder()
                .tagName(tagName)
                .build();

        tagRepository.save(newTag);
    }

    public List<CountTagDto> countTags() {
        return tagRepository.countTagsInContents();
    }

    public void isExist(String tagName) {
        boolean isExist = tagRepository.existsByTagName(tagName);
        if (isExist) {
            throw new TagErrorException(TagErrorCode.EXIST_TAG);
        }
    }
}
