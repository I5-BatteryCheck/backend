package I5.webserver.domain.Defect.Service;

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

    @Transactional
    public Long save(Defect defect) {
        return defectRepository.save(defect).getId();
    }

    public Map<String, Long> countDefectTypeByDate(LocalDateTime startDate, LocalDateTime endDate) {
        List<Defect> allDefects = defectRepository.findByBatteryTestDateBetween(startDate, endDate);

        // Group defects by battery and collect defect types
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
}
