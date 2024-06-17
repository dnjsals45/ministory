package seongmin.ministory.common.viewerTrack.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seongmin.ministory.common.viewerTrack.dto.ViewerTrack;
import seongmin.ministory.common.viewerTrack.repository.ViewerTrackRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class ViewerTrackService {
    private final ViewerTrackRepository viewerTrackRepository;

    public void save(String viewerId, Long contentId) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime tomorrow = now.plusDays(1).truncatedTo(ChronoUnit.DAYS);
        long ttl = ChronoUnit.SECONDS.between(now, tomorrow);

        ViewerTrack newTrack = ViewerTrack
                .builder()
                .viewerId(viewerId)
                .contentId(contentId)
                .ttl(ttl)
                .build();

        viewerTrackRepository.save(newTrack);
    }

    public boolean isExist(String viewerId, Long contentId) {
        return viewerTrackRepository.existsByViewerIdAndContentId(viewerId, contentId);
    }
}
