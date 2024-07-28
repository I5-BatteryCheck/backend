package I5.webserver.domain.Battery.Service;

import I5.webserver.domain.Battery.Entity.Battery;
import I5.webserver.domain.Battery.Repository.BatteryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
