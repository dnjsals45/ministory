package seongmin.ministory.domain.content.entity;

import jakarta.persistence.*;
import lombok.*;
import seongmin.ministory.common.Auditing;
import seongmin.ministory.domain.tag.entity.ContentTag;
import seongmin.ministory.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CONTENT")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content extends Auditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "content")
    private List<ContentTag> contentTags = new ArrayList<>();

    @Column(name = "title", nullable = false)
    private String title;

    @Lob // varchar(2)가 아닌 MEDIUMTEXT 형식으로 저장
    @Column(name = "body")
    private String body;

    @Column(name = "complete", nullable = false)
    private Boolean complete;

    @Column(name = "views", nullable = false)
    private Long views;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Content update(String title, String body, Boolean complete) {
        this.title = title;
        this.body = body;
        this.complete = complete;

        return this;
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void plusViewCount() {
        this.views += 1;
    }
}
