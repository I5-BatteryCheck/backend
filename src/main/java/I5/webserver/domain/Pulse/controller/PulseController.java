package I5.webserver.domain.Pulse.controller;

import I5.webserver.domain.Pulse.dto.response.PulseResponse;
import I5.webserver.domain.Pulse.service.PulseService;
import I5.webserver.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pulse")
@Tag(name = "Pulse", description = "진동 관련 조회")
public class PulseController {

    private final PulseService pulseService;

    @GetMapping("/retrieve")
    @Operation(summary = "전일 진동 평균 조회", description = "전일의 진동 평균을 조회합니다.")
    public ApiResponse<PulseResponse> getPulseAverageFromYesterday() {
        PulseResponse pulseAverageFromYesterday = pulseService.getPulseAverageFromYesterday();
        return ApiResponse.success(pulseAverageFromYesterday);
    }

}
