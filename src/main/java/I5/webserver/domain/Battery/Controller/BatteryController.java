package I5.webserver.domain.Battery.Controller;

import I5.webserver.domain.Defect.Dto.response.BatteryDefectTypeResponseDto;
import I5.webserver.domain.Battery.Dto.response.BatteryResponseDto;
import I5.webserver.domain.Battery.Service.BatteryService;
import I5.webserver.domain.Battery.Dto.response.BatteryConditionResponseDto;
import I5.webserver.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/battery")
@Tag(name = "Battery", description = "배터리 관련 조회")
public class BatteryController {

    private final BatteryService batteryService;

    @GetMapping("/defectRatio")
    @Operation(summary = "전체 불량 비율 조회", description = "전체 검사된 배터리의 불량 비율을 조회합니다.")
    private ApiResponse<BatteryResponseDto> getAllDefectRatio() {
        String normalRatio = String.format("%.1f", batteryService.calculateAllNormalRatio());
        String defectRatio = String.format("%.1f", 100 - Double.parseDouble(normalRatio));
        return ApiResponse.success(new BatteryResponseDto(normalRatio, defectRatio));
    }

    @GetMapping("/defectRatio/date")
    @Operation(summary = "기간별 불량 비율 조회", description = "기간별 배터리의 불량 비율을 조회합니다. /날짜 예시 : 2024-07-28T00:00:00")
    private ApiResponse<BatteryResponseDto> searchDefectRatioByDate(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        String normalRatio = String.format("%.1f", batteryService.calculateNormalRatioByDate(startDate, endDate));
        String defectRatio = String.format("%.1f", (100 - Double.parseDouble(normalRatio)));
        return ApiResponse.success(new BatteryResponseDto(normalRatio, defectRatio));
    }

    @GetMapping("/condition")
    @Operation(summary = "배터리 온도, 습도, 조도 조회", description = "배터리 촬영 당시의 온도, 습도, 조도 조회")
    public ApiResponse<BatteryConditionResponseDto> getBatteryCondition(@RequestParam(name = "batteryId") Long id) {
        BatteryConditionResponseDto batteryCondition = batteryService.findBatteryCondition(id);
        if(batteryCondition == null) return ApiResponse.failure();
        return ApiResponse.success(batteryCondition);
    }

    @GetMapping("/productCount/date")
    @Operation(summary = "기간별 생산량 조회", description = "기간별 생산량을 조회합니다. /날짜 예시 : 2024-07-28T00:00:00")
    public ApiResponse<Long> getProductCountByDate(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        return ApiResponse.success(batteryService.findProductCountByDate(startDate, endDate));
    }

//    @GetMapping("/defectType/date")
//    @Operation(summary = "기간별 불량 유형 개수 조회", description = "기간별 불량 유형의 개수를 조회합니다. /날짜 예시 : 2024-07-28T00:00:00")
//    public ApiResponse<BatteryDefectTypeResponseDto> getDefectTypeCountByDate(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
//
//    }
}
