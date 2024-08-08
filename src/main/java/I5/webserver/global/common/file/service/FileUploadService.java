package I5.webserver.global.common.file.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileUploadService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public Map<String, File> uploadFiles(List<MultipartFile> files) {
        return files.stream()
                .map(this::uploadFile)
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (existing, replacement) -> existing));
    }

    public Map<String, File> uploadFile(MultipartFile file) {
        try {
            File tempFile = convert(file)
                    .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));
            String savedName = upload(tempFile, "picture");
            return Map.of(savedName, tempFile);
        }
        catch (IOException e) {
            log.error("파일을 업로드하는 도중 오류가 발생했습니다. 파일명 : {}\n{}", file.getOriginalFilename(), e.getMessage());
            return null;
        }
    }
    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return fileName;
    }
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(
                CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }
    private Optional<File> convert(MultipartFile file) throws IOException {
        String directoryPath = "/src/resources/picture";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean isDirCreated = directory.mkdirs();
            if (!isDirCreated) {
                throw new IOException("디렉토리를 생성할 수 없습니다: " + directoryPath);
            }
        }
        File convertFile = new File(directoryPath + "/" + file.getOriginalFilename());
        if (convertFile.exists()) {
            log.info("파일이 이미 존재합니다. 기존 파일을 삭제합니다: {}", convertFile.getPath());
            boolean isDeleted = convertFile.delete();
            if (!isDeleted) {
                log.error("기존 파일을 삭제할 수 없습니다: {}", convertFile.getPath());
                throw new IOException("기존 파일을 삭제할 수 없습니다: " + convertFile.getPath());
            }
        }

        // 파일 생성 시도
        boolean isFileCreated = convertFile.createNewFile();
        if (isFileCreated) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        } else {
            log.error("파일을 생성할 수 없습니다: {}", convertFile.getPath());
        }

        return Optional.empty();
    }
}
