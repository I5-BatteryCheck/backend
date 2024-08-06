package I5.webserver.domain.Battery.Controller;

import I5.webserver.domain.Battery.Dto.response.BatteryConditionDailyResponseDto;
import I5.webserver.domain.Battery.Dto.response.BatteryDailyCountResponseDto;
import I5.webserver.domain.Battery.Dto.response.BatteryDailyResponseDto;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @Operation(summary = "최근 5일간 불량 비율 조회", description = "최근 5일의 배터리의 불량 비율을 조회합니다.")
    private ApiResponse<List<BatteryDailyResponseDto>> searchDefectRatioByDate() {
        List<BatteryDailyResponseDto> result = new ArrayList<>();
        for(int i = 1; i <= 5; i++) {
            LocalDate date = batteryService.findRecent5Dates().get(i - 1);
            String normalRatio = String.format("%.1f", batteryService.calculateNormalRatioRecent5days().get(i));
            String defectRatio = String.format("%.1f", (100 - Double.parseDouble(normalRatio)));
            result.add(new BatteryDailyResponseDto(date, normalRatio, defectRatio));
        }
        return ApiResponse.success(result);
    }

    @GetMapping("/condition")
    @Operation(summary = "배터리 온도, 습도, 조도 조회", description = "배터리 촬영 당시의 온도, 습도, 조도 조회")
    public ApiResponse<BatteryConditionResponseDto> getBatteryCondition(@RequestParam(name = "batteryId") Long id) {
        BatteryConditionResponseDto batteryCondition = batteryService.findBatteryCondition(id);
        if(batteryCondition == null) return ApiResponse.failure();
        return ApiResponse.success(batteryCondition);
    }

    @GetMapping("/condition/date")
    @Operation(summary = "최근 5일간 배터리 온도, 습도, 조도 조회", description = "배터리 촬영 당시의 온도, 습도, 조도 조회(평균)")
    public ApiResponse<List<BatteryConditionDailyResponseDto>> getBatteryConditionAverage() {
        Map<Integer, BatteryConditionDailyResponseDto> batteryConditionAverageRecent5days = batteryService.findBatteryConditionAverageRecent5days();
        List<BatteryConditionDailyResponseDto> result = new ArrayList<>();
        for(int i = 1; i <= 5; i++) {
            result.add(batteryConditionAverageRecent5days.get(i));
        }
        return ApiResponse.success(result);
    }

    @GetMapping("/productCount/date")
    @Operation(summary = "최근 5일간 생산량 조회", description = "최근 5일간 생산량을 조회합니다.")
    public ApiResponse<List<BatteryDailyCountResponseDto>> getProductCountRecent5days() {
        Map<Integer, Long> productCountRecent5days = batteryService.findProductCountRecent5days();
        List<BatteryDailyCountResponseDto> result = new ArrayList<>();
        for(int i = 1; i <= 5; i++) {
            LocalDate date = batteryService.findRecent5Dates().get(i - 1);
            BatteryDailyCountResponseDto dto = new BatteryDailyCountResponseDto(date, productCountRecent5days.get(i));
            result.add(dto);
        }
        return ApiResponse.success(result);
    }
}
