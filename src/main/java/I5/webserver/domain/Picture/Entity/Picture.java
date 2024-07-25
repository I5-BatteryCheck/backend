package I5.webserver.domain.Picture.Entity;

import I5.webserver.domain.Battery.Entity.Battery;
import I5.webserver.domain.Defect.Entity.Defect;
import I5.webserver.domain.PictureDefect.Entity.PictureDefect;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "battery_id")
    private Battery battery;

    @Column(nullable = false)
    private String pictureName;

    @Column(nullable = false)
    private Integer cameraNumber;

    @OneToMany(mappedBy = "picture")
    private List<PictureDefect> pictureDefects;

}
