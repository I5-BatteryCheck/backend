package I5.webserver.domain.Defect.Entity;

import I5.webserver.domain.Battery.Entity.Battery;
import I5.webserver.domain.Picture.Entity.Picture;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Defect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Type type;

    @Column(nullable = false)
    private String xMin;

    @Column(nullable = false)
    private String xMax;

    @Column(nullable = false)
    private String yMin;

    @Column(nullable = false)
    private String yMax;

    @ManyToOne(fetch = FetchType.LAZY)
    private Battery battery;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "picture_id")
    private Picture picture;

}
