package I5.webserver.global.common.file.controller;

import I5.webserver.domain.Battery.Entity.Battery;
import I5.webserver.domain.Battery.Service.BatteryService;
import I5.webserver.global.common.ApiResponse;
import I5.webserver.global.common.file.dto.request.FileDto;
import I5.webserver.global.common.file.dto.request.FileRequestDto;
import I5.webserver.global.common.file.dto.response.FileResponseDto;
import I5.webserver.global.common.file.service.FileService;
import I5.webserver.global.common.file.service.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
@Tag(name = "FileUpload", description = "파일 업로드")
public class FileController {

    private final FileUploadService fileUploadService;
    private final FileService fileService;
    private final BatteryService batteryService;

    private static Long batteryId = 1L;

    @Operation(summary = "배터리 사진 업로드", description = "객체 탐지된 배터리 사진")
    @PostMapping(value = "/battery", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<FileResponseDto> uploadPicture(
            @RequestPart(name = "multipartFile") List<MultipartFile> multipartFiles,
            @RequestPart(name = "content") FileRequestDto fileRequestDto
    ) {
        Map<String, File> uploadedFiles = fileUploadService.uploadFiles(multipartFiles);
        List<String> savedNames = uploadedFiles.keySet().stream()
                .toList();
        List<File> files = uploadedFiles.values().stream()
                .toList();
        for(File file : files) {
            System.out.println(file.getName());
        }
        try {
            FileDto fileDto = new FileDto(multipartFiles.size(), multipartFiles, savedNames, files);
            Battery battery = batteryService.findLatestBattery();
            List<Long> savedFileIds;
            if(battery != null) {
                batteryId = battery.getId();
                savedFileIds = fileService.save(fileDto, fileRequestDto, batteryId + 1);
            }else{
                savedFileIds = fileService.save(fileDto, fileRequestDto, batteryId);
            }
            FileResponseDto responseDto = new FileResponseDto(multipartFiles.size(), (int)files.stream().filter(Objects::nonNull).count() , savedFileIds);
            return ApiResponse.success(responseDto);
        }
        catch (EntityNotFoundException e) {
            for (File uploadedFile : files) {
                if (uploadedFile != null && uploadedFile.exists()) {
                    uploadedFile.delete();
                }
            }
            log.error(e.getMessage());
            return ApiResponse.failure();
        }
    }
}
