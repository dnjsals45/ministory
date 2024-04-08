package seongmin.ministory.domain.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import seongmin.ministory.domain.tag.entity.ContentTag;

@Getter
@Builder
@AllArgsConstructor
public class ContentTagDto {
    private String tagName;

    public static ContentTagDto from(ContentTag contentTag) {
        return ContentTagDto.builder()
                .tagName(contentTag.getTag().getTagName())
                .build();
    }
}
