package seongmin.ministory.domain.content.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UploadImageRes {
    private String imageUrl;
}
