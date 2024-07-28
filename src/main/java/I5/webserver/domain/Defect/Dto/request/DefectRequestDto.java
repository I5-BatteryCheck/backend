package I5.webserver.domain.Defect.Dto.request;

import I5.webserver.domain.Defect.Entity.Type;
import I5.webserver.domain.Picture.Entity.Picture;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DefectRequestDto {

    private Type type;

    private String xMin;

    private String xMax;

    private String yMin;

    private String yMax;

}
