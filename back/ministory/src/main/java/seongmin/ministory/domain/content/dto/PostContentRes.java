package seongmin.ministory.domain.content.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostContentRes {
    private Long contentId;
    private LocalDateTime updatedAt;

    public static PostContentRes of(Long contentId, LocalDateTime updatedAt) {
        return new PostContentRes(contentId, updatedAt);
    }
}
