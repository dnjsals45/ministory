package seongmin.ministory.domain.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetTagsCountRes {
    private List<CountTagDto> tags;
    private Long total;

    public static GetTagsCountRes from(List<CountTagDto> getTagResList, Long total) {
        return GetTagsCountRes.builder()
                .tags(getTagResList)
                .total(total)
                .build();
    }
}
