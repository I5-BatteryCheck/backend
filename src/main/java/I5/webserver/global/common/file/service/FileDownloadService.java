package I5.webserver.global.common.file.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
@Service
@RequiredArgsConstructor
public class FileDownloadService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public byte[] getImageFile(String s3Path) {
        try (S3Object s3Object = amazonS3Client.getObject(bucket, s3Path);
             S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent()) {
            return s3ObjectInputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
