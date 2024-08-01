package I5.webserver.domain.Defect.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DefectRateResponseDto {

    private String pollution;

    private String damaged;

    private String both;
}
