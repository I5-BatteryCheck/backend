package I5.webserver.domain.Defect.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BatteryDefectTypeResponseDto {

    private Long pollution;
    private Long damaged;
    private Long both;
}
