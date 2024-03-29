package seongmin.minilife.domain.tag.repository;

import seongmin.minilife.domain.tag.dto.CountTagDto;
import seongmin.minilife.domain.tag.dto.GetTagRes;

import java.util.List;

public interface QTagRepository {

    List<CountTagDto> countTagsInContents();
}
