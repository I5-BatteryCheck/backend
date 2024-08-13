package I5.webserver.domain.Battery.Entity;

import I5.webserver.domain.Pulse.entity.Pulse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
public class Battery {

    @Id
    private Long id;

    @Column(nullable = false)
    private Result result;

    @Column(nullable = false)
    private LocalDateTime testDate;

    @Column(nullable = false)
    private Double temperature;

    @Column(nullable = false)
    private Double humidity;

    @Column(nullable = false)
    private Double illuminance;

    @Column(nullable = false)
    private Double gas;

    @Column(nullable = false)
    private Double damagedLevel;

    @Column(nullable = false)
    private Double pollutionLevel;

    @OneToOne
    private Pulse pulse;
}
