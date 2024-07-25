package I5.webserver.domain.Condition.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Environment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime conditionDateTime;

    @Column(nullable = false)
    private Double insideTemperature;

    @Column(nullable = false)
    private Double insideHumidity;

    @Column(nullable = false)
    private Double insideIlluminance;

    @Column(nullable = false)
    private Double outsideTemperature;

    @Column(nullable = false)
    private Double outsideHumidity;

    @Column(nullable = false)
    private Double outsideIlluminance;

}
