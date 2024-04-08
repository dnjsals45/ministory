package seongmin.ministory.domain.comment.entity;

import jakarta.persistence.*;
import lombok.*;
import seongmin.ministory.common.Auditing;
import seongmin.ministory.domain.content.entity.Content;
import seongmin.ministory.domain.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "COMMENT")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends Auditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Comment update(String comment) {
        this.comment = comment;

        return this;
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }
}
