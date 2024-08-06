package I5.webserver.domain.Battery.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class BatteryDailyResponseDto {

    private LocalDate localDate;

    private String normalRatio;

    private String defectRatio;
}
