package I5.webserver.domain.Defect.Dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CameraDefectsDto {

    private Integer cameraNumber;

    private List<DefectRequestDto> defects;

}
