package I5.webserver.global.common.file.dto.request;

import I5.webserver.domain.Battery.Entity.Result;
import I5.webserver.domain.Defect.Dto.request.CameraDefectsDto;
import I5.webserver.domain.Defect.Dto.request.DefectRequestDto;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FileRequestDto {

    private Result result;

    private LocalDateTime testDate;

    private Double temperature;

    private Double humidity;

    private Double illuminance;

    private Double gas;

    private Double damagedLevel;

    private Double pollutionLevel;

    private List<CameraDefectsDto> cameraDefects;

}
