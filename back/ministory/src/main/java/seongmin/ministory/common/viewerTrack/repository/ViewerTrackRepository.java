package seongmin.ministory.common.viewerTrack.repository;

import org.springframework.data.repository.CrudRepository;
import seongmin.ministory.common.viewerTrack.dto.ViewerTrack;

public interface ViewerTrackRepository extends CrudRepository<ViewerTrack, Long> {
    boolean existsByViewerIdAndContentId(String viewerId, Long contentId);
}
