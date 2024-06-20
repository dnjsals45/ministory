package seongmin.ministory.domain.content.dto;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateContentRes {
    private Long contentId;
    private String uuid;
}
