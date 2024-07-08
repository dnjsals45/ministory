package seongmin.ministory.domain.tag.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetContentTagReq {
    private List<String> tags;
}
