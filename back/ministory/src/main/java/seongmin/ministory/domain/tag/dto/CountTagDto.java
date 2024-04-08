package seongmin.ministory.domain.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import seongmin.ministory.domain.tag.entity.Tag;

@Getter
@Builder
@AllArgsConstructor
public class CountTagDto {
    private String tagName;
    private Long count;

    public static CountTagDto from(Tag tag, Long count) {
        return CountTagDto.builder()
                .tagName(tag.getTagName())
                .count(count)
                .build();
    }
}
