package seongmin.ministory.domain.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import seongmin.ministory.domain.tag.entity.Tag;

@Getter
@Builder
@AllArgsConstructor
public class GetTagRes {
    private String tagName;

    public static GetTagRes from(Tag tag) {
        return GetTagRes.builder()
                .tagName(tag.getTagName())
                .build();
    }
}
