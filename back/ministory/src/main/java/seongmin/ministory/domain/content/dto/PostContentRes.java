package seongmin.ministory.domain.content.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostContentRes {
    private Long contentId;
    private LocalDateTime updatedAt;

    public static PostContentRes of(Long contentId, LocalDateTime updatedAt) {
        return new PostContentRes(contentId, updatedAt);
    }
}
