package seongmin.ministory.domain.tag.dto;

import lombok.*;
import seongmin.ministory.domain.tag.entity.Tag;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
