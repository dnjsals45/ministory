package seongmin.minilife.domain.tag.entity;

import jakarta.persistence.*;
import lombok.*;
import seongmin.minilife.common.Auditing;
import seongmin.minilife.domain.content.entity.Content;

@Entity
@Table(name = "CONTENT_TAG")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentTag extends Auditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_category_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
