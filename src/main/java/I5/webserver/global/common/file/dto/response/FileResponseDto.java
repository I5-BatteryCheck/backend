package I5.webserver.global.common.file.dto.response;

import I5.webserver.domain.Picture.Entity.Picture;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FileResponseDto {

    private int requestedFileCount;

    private int successfulFileCount;

    private List<Long> successfulFileIds;

}
