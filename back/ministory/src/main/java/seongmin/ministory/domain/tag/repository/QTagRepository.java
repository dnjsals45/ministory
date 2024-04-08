package seongmin.ministory.domain.tag.repository;

import seongmin.ministory.domain.tag.dto.CountTagDto;

import java.util.List;

public interface QTagRepository {

    List<CountTagDto> countTagsInContents();
}
