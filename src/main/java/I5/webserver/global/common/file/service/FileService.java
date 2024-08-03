package I5.webserver.global.common.file.service;

import I5.webserver.domain.Battery.Entity.Battery;
import I5.webserver.domain.Battery.Service.BatteryService;
import I5.webserver.domain.Defect.Dto.request.CameraDefectsDto;
import I5.webserver.domain.Defect.Dto.request.DefectRequestDto;
import I5.webserver.domain.Defect.Entity.Defect;
import I5.webserver.domain.Defect.Service.DefectService;
import I5.webserver.domain.Picture.Entity.Picture;
import I5.webserver.domain.Picture.Service.PictureService;
import I5.webserver.global.Util.FileUtils;
import I5.webserver.global.common.file.dto.request.FileDto;
import I5.webserver.global.common.file.dto.request.FileRequestDto;
import I5.webserver.global.common.file.entity.FileName;
import I5.webserver.global.common.file.entity.UploadedFile;
import I5.webserver.global.common.file.repository.FileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FileService {

    private final FileRepository fileRepository;
    private final PictureService pictureService;
    private final BatteryService batteryService;
    private final DefectService defectService;

    @Transactional(rollbackFor = {EntityNotFoundException.class})
    public List<Long> save(FileDto fileDto, FileRequestDto requestDto, Long batteryId) throws EntityNotFoundException{
        List<Long> result = new ArrayList<>();
        Battery battery = batteryService.findById(batteryId);
        if (battery == null) {
            battery = Battery.builder()
                    .id(batteryId)
                    .result(requestDto.getResult())
                    .testDate(requestDto.getTestDate())
                    .humidity(requestDto.getHumidity())
                    .illuminance(requestDto.getIlluminance())
                    .temperature(requestDto.getTemperature())
                    .defectLevel(requestDto.getDefectLevel())
                    .build();
            batteryService.save(battery);
        }
        List<CameraDefectsDto> cameraDefectsDtos = requestDto.getCameraDefects();
        int sequence = 0;
        for (CameraDefectsDto cameraDefectsDto : cameraDefectsDtos) {
            Picture picture = pictureService.findByBatteryIdAndCameraNumber(batteryId, cameraDefectsDto.getCameraNumber());
            if (picture == null) {
                picture = Picture.builder()
                        .pictureName(fileDto.getUploadedFiles().get(sequence).getName())
                        .battery(battery)
                        .cameraNumber(cameraDefectsDto.getCameraNumber())
                        .build();
                pictureService.save(picture);
            }
            List<Defect> tempDefects = new ArrayList<>();
            for (DefectRequestDto defectRequestDto : cameraDefectsDto.getDefects()) {
                Defect defect = Defect.builder()
                        .type(defectRequestDto.getType())
                        .xMin(defectRequestDto.getXMin())
                        .xMax(defectRequestDto.getXMax())
                        .yMin(defectRequestDto.getYMin())
                        .yMax(defectRequestDto.getYMax())
                        .battery(battery)
                        .picture(picture)
                        .build();
                defectService.save(defect);
                tempDefects.add(defect);
            }
            picture.setDefects(tempDefects);

            MultipartFile origin = fileDto.getFiles().get(sequence);
            File copy = fileDto.getUploadedFiles().get(sequence);

            if (origin == null || copy == null) {
                continue;
            }
            Picture savedPicture = pictureService.findByBatteryIdAndCameraNumber(batteryId, cameraDefectsDto.getCameraNumber());
            UploadedFile uploadedFile = fileRepository.findByPicture(savedPicture);
            if(uploadedFile == null) {
                uploadedFile = UploadedFile.builder()
                        .size(origin.getSize())
                        .fileName(FileName.builder()
                                .originalName(origin.getOriginalFilename())
                                .savedPath(FileUtils.getPathWithoutFileName(copy.getPath()))
                                .savedName(copy.getName())
                                .extensionName(FileUtils.getExtensionName(origin.getOriginalFilename()))
                                .build())
                        .mimeType(origin.getContentType())
                        .picture(savedPicture)
                        .build();
                result.add(fileRepository.save(uploadedFile).getId());
            }
            sequence++;
        }
        return result;
    }
}
