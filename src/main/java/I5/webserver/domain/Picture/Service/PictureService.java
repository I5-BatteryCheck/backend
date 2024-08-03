package I5.webserver.domain.Picture.Service;

import I5.webserver.domain.Battery.Entity.Result;
import I5.webserver.domain.Defect.Entity.Defect;
import I5.webserver.domain.Defect.Entity.Type;
import I5.webserver.domain.Picture.Dto.response.PictureFilterResponseDto;
import I5.webserver.domain.Picture.Entity.Picture;
import I5.webserver.domain.Picture.Repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public List<PictureFilterResponseDto> getStatistics(LocalDateTime startDate, LocalDateTime endDate, List<Result> results, List<Type> types, Integer cameraNumber) {
        System.out.println(results);
        if (results != null && results.isEmpty()) {
            results = null;
        }
        if (types != null && types.isEmpty()) {
            types = null;
        }
        List<Picture> pictures = pictureRepository.findAllByFilters(startDate, endDate, results, types, cameraNumber);
        List<PictureFilterResponseDto> responseDtos = new ArrayList<>();
        for (Picture picture : pictures) {
            PictureFilterResponseDto dto = new PictureFilterResponseDto(
                    picture.getId(),
                    picture.getBattery().getId(),
                    picture.getBattery().getTestDate(),
                    picture.getBattery().getResult(),
                    picture.getDefects().stream().map(Defect::getType).distinct().collect(Collectors.toList()),
                    picture.getCameraNumber(),
                    picture.getBattery().getDefectLevel()
            );
            responseDtos.add(dto);
        }
        return responseDtos;
    }
}
