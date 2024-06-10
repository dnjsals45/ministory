package seongmin.ministory.api.content.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import seongmin.ministory.common.response.code.ContentErrorCode;
import seongmin.ministory.common.response.exception.ContentErrorException;
import seongmin.ministory.domain.content.entity.Content;
import seongmin.ministory.domain.content.repository.ContentRepository;

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

    public Page<Content> findTagContents(String tagName, Pageable pageable) {
        return contentRepository.findTagContents(tagName, pageable);
    }

    public Page<Content> searchContent(Pageable pageable, String tagName, String keyword) {
        return contentRepository.searchContent(pageable, tagName, keyword);
    }

    public Long countAllContents() {
        return contentRepository.countAllByCompleteIsTrue();
    }
}
