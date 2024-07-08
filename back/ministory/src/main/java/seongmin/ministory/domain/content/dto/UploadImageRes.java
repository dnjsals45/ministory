package seongmin.ministory.domain.content.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadImageRes {
    private String imageUrl;
}
