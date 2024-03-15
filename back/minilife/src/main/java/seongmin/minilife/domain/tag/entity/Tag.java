package seongmin.minilife.domain.tag.entity;

import jakarta.persistence.*;
import lombok.*;
import seongmin.minilife.common.Auditing;

@Entity
@Table(name = "TAG")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag extends Auditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tag_name", nullable = false)
    private String tagName;
}
