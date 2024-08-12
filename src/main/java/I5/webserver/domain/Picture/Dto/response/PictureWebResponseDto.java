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
public class PictureWebResponseDto {

    private Long batteryId;

    private List<String> pictures;

    private List<Type> type;

    private LocalDateTime localDateTime;

    private Result result;

}
