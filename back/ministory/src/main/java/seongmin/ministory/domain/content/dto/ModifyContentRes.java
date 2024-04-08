package seongmin.ministory.domain.content.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ModifyContentRes {
    private Long contentId;
    private LocalDateTime updatedAt;

    public static ModifyContentRes of(Long contentId, LocalDateTime updatedAt) {
        return new ModifyContentRes(contentId, updatedAt);
    }
}
