package I5.webserver.domain.Picture.Service;

import I5.webserver.domain.Picture.Entity.Picture;
import I5.webserver.domain.Picture.Repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PictureService {

    private final PictureRepository pictureRepository;

    @Transactional
    public Long save(Picture picture) {
        return pictureRepository.save(picture).getId();
    }

    public Picture findByBatteryIdAndCameraNumber(Long id, Integer cameraNumber) {
        return pictureRepository.findByBatteryIdAndCameraNumber(id, cameraNumber);
    }
}
