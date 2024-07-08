package seongmin.ministory.domain.tag.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteContentTagReq {
    private String tagName;
}
