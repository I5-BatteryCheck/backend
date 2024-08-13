package I5.webserver.domain.Pulse.entity;

import I5.webserver.domain.Battery.Entity.Battery;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pulse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "battery_id")
    private Battery battery;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "pulse_frequency", joinColumns = @JoinColumn(name = "pulse_id"))
    @OrderColumn(name = "frequency_order")
    @Column(name = "frequency")
    private List<Double> frequency;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "pulse_magnitude", joinColumns = @JoinColumn(name = "pulse_id"))
    @OrderColumn(name = "magnitude_order")
    @Column(name = "magnitude")
    private List<Double> magnitude;
}
