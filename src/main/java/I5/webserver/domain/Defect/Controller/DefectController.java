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
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/defect")
@Tag(name = "Defect", description = "불량 관련 조회")
public class DefectController {

    private final DefectService defectService;

    @GetMapping("/date")
    @Operation(summary = "최근 5일간 불량 유형 개수 조회", description = "최근 5일간 불량 유형의 개수를 조회합니다.")
    public ApiResponse<List<BatteryDefectTypeResponseDto>> getDefectTypeCountByDate() {
        Map<Integer, Map<String, Long>> defectTypeCountMap = defectService.countDefectTypeRecent5days();
        List<BatteryDefectTypeResponseDto> dtos = new ArrayList<>();
        LocalDate now = LocalDate.now();
        for (int i = 1; i <= 5; i++) {
            Map<String, Long> defectCounts = defectTypeCountMap.get(i);
            BatteryDefectTypeResponseDto dto = new BatteryDefectTypeResponseDto(
                    now.minusDays(i),
                    defectCounts.getOrDefault("pollution", 0L),
                    defectCounts.getOrDefault("damaged", 0L),
                    defectCounts.getOrDefault("both", 0L)
            );
            dtos.add(dto);
        }
        return ApiResponse.success(dtos);
    }

    @GetMapping("/rate")
    @Operation(summary = "불량 비율 조회", description = "파손, 오염, 복합으로 나뉜 전체 불량 비율을 조회합니다.")
    public ApiResponse<DefectRateResponseDto> getDefectRate() {
        DefectRateResponseDto defectRate = defectService.getDefectRate();
        return ApiResponse.success(defectRate);
    }
}
