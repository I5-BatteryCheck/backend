package I5.webserver.domain.Battery.Repository;

import I5.webserver.domain.Battery.Entity.Battery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatteryRepository extends JpaRepository<Battery, Long> {

    Battery findFirstByOrderByIdDesc();
}
