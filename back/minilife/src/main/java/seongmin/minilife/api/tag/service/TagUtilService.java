package seongmin.minilife.api.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seongmin.minilife.domain.tag.entity.Tag;
import seongmin.minilife.domain.tag.repository.TagRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagUtilService {
    private final TagRepository tagRepository;

    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }

    public void saveContentTag(String tagName) {
        Tag tag = tagRepository.findByTagName(tagName);
        if (tag == null) {
            Tag newTag = Tag.builder()
                    .tagName(tagName)
                    .build();

            tagRepository.save(newTag);
        }
    }
}
