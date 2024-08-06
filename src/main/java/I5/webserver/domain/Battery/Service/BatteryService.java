package I5.webserver.domain.Battery.Service;

import I5.webserver.domain.Battery.Dto.response.BatteryConditionDailyResponseDto;
import I5.webserver.domain.Battery.Entity.Battery;
import I5.webserver.domain.Battery.Entity.Result;
import I5.webserver.domain.Battery.Repository.BatteryRepository;
import I5.webserver.domain.Battery.Dto.response.BatteryConditionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

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

    public Map<Integer, Double> calculateNormalRatioRecent5days() {
        Map<Integer, Double> resultMap = new HashMap<>();
        LocalDateTime endDate = LocalDateTime.now();
        IntStream.rangeClosed(1, 5).forEach(day -> {
            LocalDateTime startDate = endDate.minusDays(day);
            LocalDateTime currentEndDate = endDate.minusDays(day - 1);
            Long total = batteryRepository.countByTestDateBetween(startDate, currentEndDate);
            if (total == 0) {
                resultMap.put(day, 0.0);
            }
            Long normalCount = batteryRepository.countByResultAndTestDateBetween(Result.NORMAL, startDate, currentEndDate);
            resultMap.put(day, ((double) normalCount / total) * 100);
        });
        return resultMap;
    }

    public Map<Integer, Long> findProductCountRecent5days() {
        Map<Integer, Long> resultMap = new HashMap<>();
        LocalDateTime endDate = LocalDateTime.now();
        IntStream.rangeClosed(1, 5).forEach(day -> {
            LocalDateTime startDate = endDate.minusDays(day);
            LocalDateTime currentEndDate = endDate.minusDays(day - 1);
            resultMap.put(day, batteryRepository.countByTestDateBetween(startDate, currentEndDate));
        });
        return resultMap;
    }

    public Long countAll() {
        return batteryRepository.count();
    }

    public Map<Integer, BatteryConditionDailyResponseDto> findBatteryConditionAverageRecent5days() {
        Map<Integer, BatteryConditionDailyResponseDto> resultMap = new HashMap<>();
        LocalDateTime endDate = LocalDateTime.now();
        LocalDate localDate = endDate.toLocalDate();
        IntStream.rangeClosed(1, 5).forEach(day -> {
            LocalDateTime startDate = endDate.minusDays(day);
            LocalDateTime currentEndDate = endDate.minusDays(day - 1);
            BatteryConditionResponseDto batteryConditionAverage = batteryRepository.findBatteryConditionAverage(startDate, currentEndDate);
            BatteryConditionDailyResponseDto batteryConditionDailyResponseDto = new BatteryConditionDailyResponseDto(localDate.minusDays(day), batteryConditionAverage.getTemperature(), batteryConditionAverage.getHumidity(), batteryConditionAverage.getIlluminance());
            resultMap.put(day, batteryConditionDailyResponseDto);
        });
        return resultMap;
    }

    public List<LocalDate> findRecent5Dates() {
        List<LocalDate> recentDates = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = 1; i <= 5; i++) {
            recentDates.add(today.minusDays(i));
        }
        return recentDates;
    }
}
