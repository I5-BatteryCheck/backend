package I5.webserver.domain.Picture.Repository;

import I5.webserver.domain.Picture.Entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {

    Picture findByBatteryIdAndCameraNumber(Long id, Integer cameraNumber);

}
