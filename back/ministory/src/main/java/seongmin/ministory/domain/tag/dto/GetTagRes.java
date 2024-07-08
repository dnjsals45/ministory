package seongmin.ministory.domain.tag.dto;

import lombok.*;
import seongmin.ministory.domain.tag.entity.Tag;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetTagRes {
    private String tagName;

    public static GetTagRes from(Tag tag) {
        return GetTagRes.builder()
                .tagName(tag.getTagName())
                .build();
    }
}
