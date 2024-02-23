package seongmin.minilife.domain.content.entity;

import jakarta.persistence.*;
import lombok.*;
import seongmin.minilife.common.Auditing;
import seongmin.minilife.domain.user.entity.User;

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

    @Column(name = "body")
    private String body;

    @Column(name = "complete", nullable = false)
    private Boolean complete;

    @Column(name = "views", nullable = false)
    private Long views;
}
