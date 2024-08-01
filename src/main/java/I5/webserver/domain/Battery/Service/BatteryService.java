package I5.webserver.domain.Battery.Service;

import I5.webserver.domain.Battery.Entity.Battery;
import I5.webserver.domain.Battery.Entity.Result;
import I5.webserver.domain.Battery.Repository.BatteryRepository;
import I5.webserver.domain.Battery.Dto.response.BatteryConditionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BatteryService {

    private final BatteryRepository batteryRepository;

    @Transactional
    public Long save(Battery battery) {
        return batteryRepository.save(battery).getId();
    }

    public Battery findById(Long id) {
        return batteryRepository.findById(id).orElse(null);
    }

    public Battery findLatestBattery() {
        return batteryRepository.findFirstByOrderByIdDesc();
    }

    public BatteryConditionResponseDto findBatteryCondition(Long id) {
        Battery battery = batteryRepository.findById(id).orElse(null);
        return (battery != null) ? BatteryConditionResponseDto.toDto(battery) : null;
    }

    public Double calculateAllNormalRatio() {
        Long total = batteryRepository.count();
        if(total == 0) {
            return 0.0;
        }
        Long normalCount = batteryRepository.countByResult(Result.NORMAL);
        return ((double) normalCount / total) * 100;
    }

    public Double calculateNormalRatioByDate(LocalDateTime startDate, LocalDateTime endDate) {
        Long total = batteryRepository.countByTestDateBetween(startDate, endDate);
        if (total == 0) {
            return 0.0;
        }
        Long normalCount = batteryRepository.countByResultAndTestDateBetween(Result.NORMAL, startDate, endDate);
        return ((double) normalCount / total) * 100;
    }

    public Long findProductCountByDate(LocalDateTime startDate, LocalDateTime endDate) {
        return batteryRepository.countByTestDateBetween(startDate, endDate);
    }

    public Long countAll() {
        return batteryRepository.count();
    }

    public BatteryConditionResponseDto findBatteryConditionAverage(LocalDateTime startDate, LocalDateTime endDate) {
        return batteryRepository.findBatteryConditionAverage(startDate, endDate);
    }
}
