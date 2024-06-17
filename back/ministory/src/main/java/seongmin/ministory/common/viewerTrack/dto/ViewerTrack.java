package seongmin.ministory.common.viewerTrack.dto;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Builder
@Getter
@RedisHash(value = "viewerTrack")
public class ViewerTrack {
    @Id
    private final Long id;
    @Indexed
    private final String viewerId;
    @Indexed
    private final Long contentId;
    @TimeToLive
    private final Long ttl;

    @Builder
    public ViewerTrack(Long id, String viewerId, Long contentId, Long ttl) {
        this.id = id;
        this.viewerId = viewerId;
        this.contentId = contentId;
        this.ttl = ttl;
    }
}
