package I5.webserver.global.common.file.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@PropertySource("file.properties")
@Service
public class FileUploadService {

    @Value("${upload.location}")
    private String SAVED_PATH;

    public List<File> uploadFiles(List<MultipartFile> files) {
        List<File> result = new ArrayList<>();
        files.forEach(file -> result.add(uploadFile(file)));
        return result;
    }

    public File uploadFile(MultipartFile file) {
        try {
            File tempFile = getTempFile(file);
            file.transferTo(Path.of(tempFile.getAbsolutePath()));
            return tempFile;
        }
        catch (IOException e) {
            log.error("파일을 업로드하는 도중 오류가 발생했습니다. 파일명 : {}\n{}", file.getOriginalFilename(), e.getMessage());
            return null;
        }
    }

    private File getTempFile(MultipartFile file) throws IOException {
        File folder = new File(getDailySavedPath());
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }
        File tempFile = new File(
                new StringBuilder(getDailySavedPath())
                        .append('/')
                        .append(UUID.randomUUID())
                        .append('.')
                        .append("png")
                        .toString()
        );
        while (tempFile.exists()) {
            tempFile = new File(
                    new StringBuilder(getDailySavedPath())
                            .append('/')
                            .append(UUID.randomUUID())
                            .append('.')
                            .append("png")
                            .toString()
            );
        }

        tempFile.createNewFile();
        return tempFile;
    }

    // 날짜별로 파일 업로드 위치 분리
    private String getDailySavedPath() {
        LocalDate ld = LocalDate.now();

        return new StringBuilder(SAVED_PATH)
                .append('/')
                .append(ld.getYear())
                .append('/')
                .append(ld.getMonthValue())
                .append('/')
                .append(ld.getDayOfMonth())
                .toString();
    }
}
