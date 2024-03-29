package seongmin.minilife.domain.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetTagsCountRes {
    private List<CountTagDto> tags;

    public static GetTagsCountRes from(List<CountTagDto> getTagResList) {
        return GetTagsCountRes.builder()
                .tags(getTagResList)
                .build();
    }
}
