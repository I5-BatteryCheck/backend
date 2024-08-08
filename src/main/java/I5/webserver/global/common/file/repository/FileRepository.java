package I5.webserver.global.common.file.repository;

import I5.webserver.domain.Picture.Entity.Picture;
import I5.webserver.global.common.file.entity.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileRepository extends JpaRepository<UploadedFile, Long> {

    UploadedFile findByPicture(Picture picture);

    @Query(value = "SELECT * FROM uploaded_file ORDER BY id DESC LIMIT 3", nativeQuery = true)
    List<UploadedFile> findRecentThree();

    @Query("SELECT uf FROM UploadedFile uf JOIN uf.picture p WHERE p.battery.id = :batteryId AND p.cameraNumber = :cameraNumber")
    UploadedFile findByBatteryIdAndCameraNumber(@Param("batteryId") Long batteryId, @Param("cameraNumber") Integer cameraNumber);
}
