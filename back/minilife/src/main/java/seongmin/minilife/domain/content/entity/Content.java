package seongmin.minilife.domain.content.entity;

import jakarta.persistence.*;
import lombok.*;
import seongmin.minilife.common.Auditing;
import seongmin.minilife.domain.user.entity.User;

import java.time.LocalDateTime;

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

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "body", length = 2000)
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
}
