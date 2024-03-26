package seongmin.minilife.api.content.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import seongmin.minilife.common.response.code.ContentErrorCode;
import seongmin.minilife.common.response.exception.ContentErrorException;
import seongmin.minilife.domain.content.dto.RecentContentsRes;
import seongmin.minilife.domain.content.entity.Content;
import seongmin.minilife.domain.content.repository.ContentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentUtilService {
    private final ContentRepository contentRepository;

    public Content findById(Long contentId) {
        return contentRepository.findById(contentId)
                .orElseThrow(() -> new ContentErrorException(ContentErrorCode.INVALID_CONTENT_ID));
    }

    public Content save(Content content) {
        contentRepository.save(content);

        return content;
    }

    public boolean existsByIdAndUserId(Long contentId, Long userId) {
        return contentRepository.existsByIdAndUserId(contentId, userId);
    }

    public Page<Content> findContentPages(Pageable pageable) {
        return contentRepository.findContentsPage(pageable);
    }

    public List<Content> findRecentContentsWithTags() {
        return contentRepository.findRecentContentsWithTags();
    }
}
