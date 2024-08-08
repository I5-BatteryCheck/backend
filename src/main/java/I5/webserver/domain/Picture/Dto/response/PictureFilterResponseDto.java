package I5.webserver.domain.Picture.Dto.response;

import I5.webserver.domain.Battery.Entity.Result;
import I5.webserver.domain.Defect.Entity.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PictureFilterResponseDto {

    private Long pictureId;
    private Long batteryId;
    private LocalDateTime testDate;
    private Result result;
    private List<Type> type;
    private Integer cameraNumber;
    private String encodedImage;
    private Double damagedLevel;
    private Double pollutionLevel;
}
