package I5.webserver.global.common.file.repository;

import I5.webserver.domain.Picture.Entity.Picture;
import I5.webserver.global.common.file.entity.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileRepository extends JpaRepository<UploadedFile, Long> {

    UploadedFile findByPicture(Picture picture);

    @Query(value = "SELECT * FROM uploaded_file ORDER BY id DESC LIMIT 3", nativeQuery = true)
    List<UploadedFile> findRecentThree();
}
