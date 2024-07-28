package I5.webserver.global.common.file.repository;

import I5.webserver.domain.Picture.Entity.Picture;
import I5.webserver.global.common.file.entity.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<UploadedFile, Long> {

    UploadedFile findByPicture(Picture picture);
}
