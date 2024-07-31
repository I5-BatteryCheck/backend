package I5.webserver.domain.Picture.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PictureWebResponseDto {

    private Long batteryId;

    private List<String> pictures;

}
