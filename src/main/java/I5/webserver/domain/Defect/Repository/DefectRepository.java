package I5.webserver.domain.Defect.Repository;

import I5.webserver.domain.Defect.Entity.Defect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DefectRepository extends JpaRepository<Defect, Long> {

    @Query("SELECT d FROM Defect d WHERE d.battery.testDate BETWEEN :startDate AND :endDate")
    List<Defect> findByBatteryTestDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
