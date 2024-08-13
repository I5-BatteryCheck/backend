package I5.webserver.domain.Pulse.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PulseResponse {

    private List<Double> magnitudes;

    private List<Double> frequencies;
}
