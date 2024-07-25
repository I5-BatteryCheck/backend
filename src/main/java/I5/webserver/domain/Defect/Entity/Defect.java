package I5.webserver.domain.Defect.Entity;

import I5.webserver.domain.PictureDefect.Entity.PictureDefect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @JsonIgnore
    @OneToMany(mappedBy = "defect")
    private List<PictureDefect> pictures;

}
