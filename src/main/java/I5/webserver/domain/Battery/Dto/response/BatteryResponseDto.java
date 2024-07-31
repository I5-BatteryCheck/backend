package I5.webserver.domain.Battery.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BatteryResponseDto {

    private String normalRatio;
    private String defectRatio;
}
