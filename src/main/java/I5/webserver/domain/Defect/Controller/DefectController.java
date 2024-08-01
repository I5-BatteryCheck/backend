package I5.webserver.domain.Defect.Controller;

import I5.webserver.domain.Defect.Dto.response.BatteryDefectTypeResponseDto;
import I5.webserver.domain.Defect.Dto.response.DefectRateResponseDto;
import I5.webserver.domain.Defect.Service.DefectService;
import I5.webserver.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/defect")
@Tag(name = "Defect", description = "불량 관련 조회")
public class DefectController {

    private final DefectService defectService;

    @GetMapping("/date")
    @Operation(summary = "기간별 불량 유형 개수 조회", description = "기간별 불량 유형의 개수를 조회합니다. /날짜 예시 : 2024-07-28T00:00:00")
    public ApiResponse<BatteryDefectTypeResponseDto> getDefectTypeCountByDate(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        Map<String, Long> defectTypeCountMap = defectService.countDefectTypeByDate(startDate, endDate);
        BatteryDefectTypeResponseDto dto = new BatteryDefectTypeResponseDto(defectTypeCountMap.get("pollution"), defectTypeCountMap.get("damaged"), defectTypeCountMap.get("both"));
        return ApiResponse.success(dto);
    }

    @GetMapping("/rate")
    @Operation(summary = "불량 비율 조회", description = "파손, 오염, 복합으로 나뉜 전체 불량 비율을 조회합니다.")
    public ApiResponse<DefectRateResponseDto> getDefectRate() {
        DefectRateResponseDto defectRate = defectService.getDefectRate();
        return ApiResponse.success(defectRate);
    }
}
