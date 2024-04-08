package seongmin.ministory.domain.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import seongmin.ministory.domain.tag.entity.ContentTag;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetAllContentTagsRes {
    List<ContentTagDto> tags;

    public static GetAllContentTagsRes from(List<ContentTag> tagList) {
        GetAllContentTagsRes response = GetAllContentTagsRes
                .builder()
                .tags(new ArrayList<>())
                .build();

        for (ContentTag tag : tagList) {
            response.tags.add(ContentTagDto.from(tag));
        }

        return response;
    }
}
