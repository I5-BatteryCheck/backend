package I5.webserver.domain.Defect.Dto.request;

import I5.webserver.global.common.file.dto.request.BatteryOutlineRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CameraDefectsDto {

    private Integer cameraNumber;

    private BatteryOutlineRequestDto batteryOutline;

    private List<DefectRequestDto> defects;

}
