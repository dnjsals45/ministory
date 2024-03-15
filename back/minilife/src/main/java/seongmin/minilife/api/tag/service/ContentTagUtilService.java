package seongmin.minilife.api.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seongmin.minilife.domain.tag.entity.ContentTag;
import seongmin.minilife.domain.tag.repository.ContentTagRepository;

@Service
@RequiredArgsConstructor
public class ContentTagUtilService {
    private final ContentTagRepository contentTagRepository;

    public ContentTag save(ContentTag newContentTag) {
        contentTagRepository.save(newContentTag);

        return newContentTag;
    }
}
