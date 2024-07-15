package seongmin.ministory.domain.tag.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import seongmin.ministory.domain.tag.dto.CountTagDto;
import seongmin.ministory.domain.tag.entity.QContentTag;
import seongmin.ministory.domain.tag.entity.QTag;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QTagRepositoryImpl implements QTagRepository {

    private final JPAQueryFactory jpaQueryFactory;

    QContentTag contentTag = QContentTag.contentTag;
    QTag tag = QTag.tag;

    @Override
    public List<CountTagDto> countTagsInContents() {
        return jpaQueryFactory
                .select(Projections.constructor(CountTagDto.class,
                        tag.tagName,
                        contentTag.content.id.countDistinct()))
                .from(contentTag)
                .join(contentTag.tag, tag)
                .where(contentTag.content.complete.isTrue())
                .groupBy(tag.tagName)
                .fetch();
    }
}
