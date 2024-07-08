package seongmin.ministory.domain.tag.dto;

import lombok.*;
import seongmin.ministory.domain.tag.entity.ContentTag;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentTagDto {
    private String tagName;

    public static ContentTagDto from(ContentTag contentTag) {
        return ContentTagDto.builder()
                .tagName(contentTag.getTag().getTagName())
                .build();
    }
}
