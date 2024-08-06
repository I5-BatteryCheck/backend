package I5.webserver.domain.Defect.Service;

import I5.webserver.domain.Battery.Service.BatteryService;
import I5.webserver.domain.Defect.Dto.response.DefectRateResponseDto;
import I5.webserver.domain.Defect.Entity.Defect;
import I5.webserver.domain.Defect.Entity.Type;
import I5.webserver.domain.Defect.Repository.DefectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class DefectService {

    private final DefectRepository defectRepository;
    private final BatteryService batteryService;

    @Transactional
    public Long save(Defect defect) {
        return defectRepository.save(defect).getId();
    }

    public Map<Integer, Map<String, Long>> countDefectTypeRecent5days() {
        Map<Integer, Map<String, Long>> resultMap = new HashMap<>();
        LocalDateTime endDate = LocalDate.now().atStartOfDay();
        IntStream.rangeClosed(1, 5).forEach(day -> {
            LocalDateTime startDate = endDate.minusDays(day);
            LocalDateTime currentEndDate = endDate.minusDays(day - 1);
            List<Defect> defects = defectRepository.findByBatteryTestDateBetween(startDate, currentEndDate);
            Map<String, Long> defectsCount = getDefectsCount(defects);
            resultMap.put(day, defectsCount);
        });
        return resultMap;
    }

    private static Map<String, Long> getDefectsCount(List<Defect> allDefects) {
        Map<Long, Set<Type>> batteryDefectsMap = allDefects.stream()
                .collect(Collectors.groupingBy(
                        defect -> defect.getBattery().getId(),
                        Collectors.mapping(Defect::getType, Collectors.toSet())
                ));
        long onlyPollutionCount = batteryDefectsMap.values().stream()
                .filter(types -> types.size() == 1 && types.contains(Type.POLLUTION))
                .count();
        long onlyDamagedCount = batteryDefectsMap.values().stream()
                .filter(types -> types.size() == 1 && types.contains(Type.DAMAGED))
                .count();
        long bothTypesCount = batteryDefectsMap.values().stream()
                .filter(types -> types.size() > 1 && types.contains(Type.POLLUTION) && types.contains(Type.DAMAGED))
                .count();
        return Map.of(
                "pollution", onlyPollutionCount,
                "damaged", onlyDamagedCount,
                "both", bothTypesCount
        );
    }

    public DefectRateResponseDto getDefectRate() {
        List<Defect> allDefects = defectRepository.findAll();
        Map<String, Long> defectsCount = getDefectsCount(allDefects);
        Long total = batteryService.countAll();
        String pollutionRate = String.format("%.1f", (double) defectsCount.get("pollution") / total);
        String damagedRate = String.format("%.1f", (double) defectsCount.get("damaged") / total);
        String bothRate = String.format("%.1f", (double)defectsCount.get("both")/total);
        return new DefectRateResponseDto(pollutionRate, damagedRate, bothRate);
    }
}
