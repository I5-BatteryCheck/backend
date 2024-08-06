package I5.webserver.global.common.file.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BatteryOutlineRequestDto {

    private String xMin;

    private String xMax;

    private String yMin;

    private String yMax;
}
