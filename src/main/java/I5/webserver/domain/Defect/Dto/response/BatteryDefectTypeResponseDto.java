package I5.webserver.domain.Defect.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class BatteryDefectTypeResponseDto {

    private LocalDate localDate;
    private Long pollution;
    private Long damaged;
    private Long both;
}
