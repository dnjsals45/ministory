package seongmin.ministory.domain.content.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostContentRes {
    private Long contentId;
    private String uuid;
    private LocalDateTime updatedAt;

    public static PostContentRes of(Long contentId, String uuid, LocalDateTime updatedAt) {
        return new PostContentRes(contentId, uuid, updatedAt);
    }
}
