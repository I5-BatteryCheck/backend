package I5.webserver.global.common.file.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FileDto {

    private Integer size;

    private List<MultipartFile> files;

    private List<String> savedName;

    private List<File> UploadedFiles;

}
