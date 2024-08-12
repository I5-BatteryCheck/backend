package I5.webserver.domain.Picture.Controller;

import I5.webserver.domain.Battery.Entity.Result;
import I5.webserver.domain.Defect.Entity.Defect;
import I5.webserver.domain.Defect.Entity.Type;
import I5.webserver.domain.Picture.Dto.response.PictureFilterResponseDto;
import I5.webserver.domain.Picture.Dto.response.PictureWebResponseDto;
import I5.webserver.domain.Picture.Entity.Picture;
import I5.webserver.domain.Picture.Service.PictureService;
import I5.webserver.global.common.ApiResponse;
import I5.webserver.global.common.file.entity.UploadedFile;
import I5.webserver.global.common.file.repository.FileRepository;
import I5.webserver.global.common.file.service.FileDownloadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/picture")
@Tag(name = "Picture", description = "배터리 사진 조회")
public class PictureController {

    private final FileRepository fileRepository;
    private final FileDownloadService fileDownloadService;
    private final PictureService pictureService;

    @GetMapping(value = "/web", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "배터리 사진 조회", description = "객체 탐지된 배터리 사진")
    @ResponseBody
    public ApiResponse<PictureWebResponseDto> getImage() {
        List<UploadedFile> images = fileRepository.findRecentThree();
        List<String> encodedImages = new ArrayList<>();
        for (UploadedFile image : images) {
            String s3Path = image.getFileName().getSavedName();
            byte[] imageByte = fileDownloadService.getImageFile(s3Path);
            String encodedString = Base64.getEncoder().encodeToString(imageByte);
            encodedImages.add(encodedString);
        }
        Picture picture = images.get(0).getPicture();
        Long batteryId = picture.getBattery().getId();
        LocalDateTime localDateTime = picture.getBattery().getTestDate();
        List<Type> defectTypes = picture.getDefects().stream().map(Defect::getType).toList();
        Result result = picture.getBattery().getResult();
        return ApiResponse.success(new PictureWebResponseDto(batteryId, encodedImages, defectTypes, localDateTime, result));
    }

    @GetMapping("/statistics")
    @Operation(summary = "세부 통계 조회", description = "필터링된 통계 조회 /날짜 예시 : 2024-07-28T00:00:00")
    public ApiResponse<List<PictureFilterResponseDto>> getStatistics(
            @RequestParam(name = "startDate", required = false) LocalDateTime startDate,
            @RequestParam(name = "endDate", required = false) LocalDateTime endDate,
            @RequestParam(name = "results", required = false) List<Result> results,
            @RequestParam(name = "types", required = false) List<Type> types,
            @RequestParam(name = "cameraNumber", required = false) List<Integer> cameraNumbers
    ) {
        List<PictureFilterResponseDto> statistics = pictureService.getStatistics(startDate, endDate, results, types, cameraNumbers);
        return ApiResponse.success(statistics);
    }
}
