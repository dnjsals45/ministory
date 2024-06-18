package seongmin.ministory.domain.tag.dto;

import lombok.*;
import seongmin.ministory.domain.tag.entity.Tag;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AllTagsRes {
    private List<GetTagRes> tags;

    public static AllTagsRes from(List<Tag> tags) {
        AllTagsRes response =  AllTagsRes.builder()
                .tags(new ArrayList<>())
                .build();

        for (Tag tag : tags) {
            response.tags.add(GetTagRes.from(tag));
        }

        return response;
    }
}
