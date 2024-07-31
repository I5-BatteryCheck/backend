package I5.webserver.domain.Picture.Controller;

import I5.webserver.domain.Picture.Dto.response.PictureWebResponseDto;
import I5.webserver.domain.Picture.Entity.Picture;
import I5.webserver.domain.Picture.Repository.PictureRepository;
import I5.webserver.global.common.ApiResponse;
import I5.webserver.global.common.file.entity.UploadedFile;
import I5.webserver.global.common.file.repository.FileRepository;
import I5.webserver.global.common.file.service.FileDownloadService;
import I5.webserver.global.common.file.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/picture")
@Tag(name = "Picture", description = "배터리 사진 조회")
public class PictureController {

    private final FileRepository fileRepository;
    private final FileDownloadService fileDownloadService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "배터리 사진 조회", description = "객체 탐지된 배터리 사진")
    @ResponseBody
    public ApiResponse<PictureWebResponseDto> getImage() {
        List<UploadedFile> images = fileRepository.findRecentThree();
        List<String> encodedImages = new ArrayList<>();
        for (UploadedFile image : images) {
            Path imagePath = Paths.get(image.getFileName().getSavedPath(), image.getFileName().getSavedName());
            Path absolutePath = imagePath.toAbsolutePath();
            byte[] imageByte = fileDownloadService.getImageFile(absolutePath);
            String encodedString = Base64.getEncoder().encodeToString(imageByte);
            encodedImages.add(encodedString);
        }
        Picture picture = images.get(0).getPicture();
        Long batteryId = picture.getBattery().getId();
        return ApiResponse.success(new PictureWebResponseDto(batteryId, encodedImages));
    }
}
