package I5.webserver.domain.Defect.Service;

import I5.webserver.domain.Battery.Service.BatteryService;
import I5.webserver.domain.Defect.Dto.response.DefectRateResponseDto;
import I5.webserver.domain.Defect.Entity.Defect;
import I5.webserver.domain.Defect.Entity.Type;
import I5.webserver.domain.Defect.Repository.DefectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefectService {

    private final DefectRepository defectRepository;
    private final BatteryService batteryService;

    @Transactional
    public Long save(Defect defect) {
        return defectRepository.save(defect).getId();
    }

    public Map<String, Long> countDefectTypeByDate(LocalDateTime startDate, LocalDateTime endDate) {
        List<Defect> allDefects = defectRepository.findByBatteryTestDateBetween(startDate, endDate);
        return getDefectsCount(allDefects);
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
