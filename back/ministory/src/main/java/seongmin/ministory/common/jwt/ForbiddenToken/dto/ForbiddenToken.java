package seongmin.ministory.common.jwt.ForbiddenToken.dto;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Builder
@Getter
@RedisHash(value = "forbiddenToken")
public class ForbiddenToken {
    @Id
    private final Long id;
    @Indexed
    private final String token;
    @TimeToLive
    private final Long ttl;

    @Builder
    public ForbiddenToken(Long id, String token, Long ttl) {
        this.id = id;
        this.token = token;
        this.ttl = ttl;
    }
}
