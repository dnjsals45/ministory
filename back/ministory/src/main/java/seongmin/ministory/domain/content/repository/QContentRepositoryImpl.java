package seongmin.ministory.domain.content.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import seongmin.ministory.domain.content.entity.Content;
import seongmin.ministory.domain.content.entity.QContent;
import seongmin.ministory.domain.tag.entity.QContentTag;
import seongmin.ministory.domain.tag.entity.QTag;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class QContentRepositoryImpl implements QContentRepository {

    private final JPAQueryFactory jpaQueryFactory;

    QContent content = QContent.content;
    QContentTag contentTag = QContentTag.contentTag;
    QTag tag = QTag.tag;

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

//        메모리에서 처리하게 되는 코드(fetchJoin 과 limit 함께 사용) 나중에 많은 건을 조회했을 때 성능차이 비교해보기
//        return jpaQueryFactory
//                .selectFrom(content)
//                .leftJoin(content.contentTags, contentTag).fetchJoin()
//                .where(content.deletedAt.isNull().and(content.complete.isTrue()))
//                .orderBy(content.createdAt.desc())
//                .limit(5)
//                .fetch();
    }

    @Override
    public Page<Content> findTagContents(String tagName, Pageable pageable) {

        List<Long> contentIds = jpaQueryFactory
                .select(content.id)
                .from(content)
                .join(content.contentTags, contentTag)
                .join(contentTag.tag, tag)
                .where(tag.tagName.eq(tagName)
                        .and(content.complete.isTrue())
                        .and(content.deletedAt.isNull()))
                .orderBy(content.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        if (contentIds.isEmpty()) {
            return Page.empty(pageable);
        }

        List<Content> contents = jpaQueryFactory
                .selectFrom(content)
                .leftJoin(content.contentTags, contentTag).fetchJoin()
                .where(content.id.in(contentIds))
                .orderBy(content.createdAt.desc())
                .fetch();

        Long totalCount = jpaQueryFactory
                .select(content.count())
                .from(content)
                .join(content.contentTags, contentTag)
                .join(contentTag.tag, tag)
                .where(tag.tagName.eq(tagName)
                        .and(content.complete.isTrue())
                        .and(content.deletedAt.isNull()))
                .fetchOne();

        if (totalCount == null) {
            totalCount = 0L;
        }

        return new PageImpl<>(contents, pageable, totalCount);
    }

    @Override
    public Page<Content> searchContent(String keyword, Pageable pageable) {
        List<Long> contentIds = new ArrayList<>();

        List<Long> titleSearch = jpaQueryFactory
                .select(content.id)
                .from(content)
                .where(content.title.contains(keyword)
                        .and(content.complete.isTrue())
                        .and(content.deletedAt.isNull()))
                .orderBy(content.createdAt.desc())
                .fetch();

        List<Long> contentSearch = jpaQueryFactory
                .select(content.id)
                .from(content)
                .where(content.body.contains(keyword)
                        .and(content.complete.isTrue())
                        .and(content.deletedAt.isNull()))
                .orderBy(content.createdAt.desc())
                .fetch();

        for (Long id : titleSearch) {
            if (!contentIds.contains(id)) {
                contentIds.add(id);
            }
        }

        for (Long id : contentSearch) {
            if (!contentIds.contains(id)) {
                contentIds.add(id);
            }
        }

        if (contentIds.isEmpty()) {
            return Page.empty(pageable);
        }

        List<Content> contents = jpaQueryFactory
                .selectFrom(content)
                .leftJoin(content.contentTags, contentTag).fetchJoin()
                .where(content.id.in(contentIds))
                .orderBy(content.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = (long) contentIds.size();

        if (totalCount == null) {
            totalCount = 0L;
        }

        return new PageImpl<>(contents, pageable, totalCount);
    }
}
