package seongmin.ministory.api.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seongmin.ministory.domain.tag.entity.ContentTag;
import seongmin.ministory.domain.tag.repository.ContentTagRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContentTagUtilService {
    private final ContentTagRepository contentTagRepository;

    public ContentTag save(ContentTag newContentTag) {
        contentTagRepository.save(newContentTag);

        return newContentTag;
    }
    public void deleteByContentIdAndTagId(Long contentId, Long tagId) {
        contentTagRepository.deleteByContentIdAndTagId(contentId, tagId);
    }

    public List<ContentTag> findAllByContentId(Long contentId) {
        return contentTagRepository.findAllByContentId(contentId);
    }

    public boolean existByContentIdAndTagId(Long contentId, Long tagId) {
        return contentTagRepository.existsByContentIdAndTagId(contentId, tagId);
    }

    public void deleteAllByContentId(Long id) {
        contentTagRepository.deleteAllByContentId(id);
    }
}
