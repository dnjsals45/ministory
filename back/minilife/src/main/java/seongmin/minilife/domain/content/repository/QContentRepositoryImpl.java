package seongmin.minilife.domain.content.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import seongmin.minilife.domain.content.entity.Content;
import seongmin.minilife.domain.content.entity.QContent;
import seongmin.minilife.domain.tag.entity.QContentTag;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QContentRepositoryImpl implements QContentRepository {

    private final JPAQueryFactory jpaQueryFactory;

    QContent content = QContent.content;
    QContentTag contentTag = QContentTag.contentTag;

    @Override
    public Page<Content> findContentsPage(Pageable pageable) {
        List<Long> contentIds = jpaQueryFactory
                .select(content.id)
                .from(content)
                .where(content.deletedAt.isNull().and(content.complete.isTrue()))
                .orderBy(content.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Content> contents = jpaQueryFactory
                .selectFrom(content)
                .leftJoin(content.contentTags, contentTag).fetchJoin()
                .where(content.id.in(contentIds))
                .orderBy(content.createdAt.desc())
                .fetch();

        Long totalCount = jpaQueryFactory
                .select(content.count())
                .from(content)
                .where(content.deletedAt.isNull().and(content.complete.isTrue()))
                .fetchOne();

        if (totalCount == null) {
            throw new NullPointerException();
        }

        return new PageImpl<>(contents, pageable, totalCount);
    }

    @Override
    public List<Content> findRecentContentsWithTags() {
        List<Long> contentIds = jpaQueryFactory
                .select(content.id)
                .from(content)
                .where(content.deletedAt.isNull().and(content.complete.isTrue()))
                .orderBy(content.createdAt.desc())
                .limit(9)
                .fetch();

        if (!contentIds.isEmpty()) {
            return jpaQueryFactory
                    .selectFrom(content)
                    .leftJoin(content.contentTags, contentTag).fetchJoin()
                    .where(content.id.in(contentIds))
                    .orderBy(content.createdAt.desc())
                    .fetch();

        } else {
            return null;
        }

//        메모리에서 처리하게 되는 코드(fetchJoin 과 limit 함께 사용) 나중에 많은 건을 조회했을 때 성능차이 비교해보자
//        return jpaQueryFactory
//                .selectFrom(content)
//                .leftJoin(content.contentTags, contentTag).fetchJoin()
//                .where(content.deletedAt.isNull().and(content.complete.isTrue()))
//                .orderBy(content.createdAt.desc())
//                .limit(5)
//                .fetch();
    }
}
