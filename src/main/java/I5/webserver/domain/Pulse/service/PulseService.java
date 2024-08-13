package I5.webserver.domain.Pulse.service;

import I5.webserver.domain.Battery.Entity.Battery;
import I5.webserver.domain.Battery.Service.BatteryService;
import I5.webserver.domain.Pulse.dto.response.PulseResponse;
import I5.webserver.domain.Pulse.entity.Pulse;
import I5.webserver.domain.Pulse.repository.PulseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PulseService {

    private final BatteryService batteryService;
    private final PulseRepository pulseRepository;

    @Transactional
    public void save(Pulse pulse) {
        pulseRepository.save(pulse);
    }

    public PulseResponse getPulseAverageFromYesterday() {
        List<Battery> batteries = batteryService.findYesterDayBatteries();
        List<Double> frequencies = batteries.get(0).getPulse().getFrequency();
        return new PulseResponse(calculateMagnitudesAverage(batteries), frequencies);
    }

    public List<Double> calculateMagnitudesAverage(List<Battery> batteries) {
        int size = batteries.get(0).getPulse().getMagnitude().size();
        List<Double> averages = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            int index = i;
            double average = batteries.stream()
                    .mapToDouble(battery -> battery.getPulse().getMagnitude().get(index))
                    .average()
                    .orElse(0.0);
            averages.add(average);
        }
        return averages;
    }
}
