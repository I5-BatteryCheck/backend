package I5.webserver.domain.Battery.Repository;

import I5.webserver.domain.Battery.Entity.Battery;
import I5.webserver.domain.Battery.Entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface BatteryRepository extends JpaRepository<Battery, Long> {

    Battery findFirstByOrderByIdDesc();

    Long countByResult(Result result);

    @Query("SELECT COUNT(b) FROM Battery b WHERE b.testDate BETWEEN :startDate AND :endDate")
    Long countByTestDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(b) FROM Battery b WHERE b.result = :result AND b.testDate BETWEEN :startDate AND :endDate")
    Long countByResultAndTestDateBetween(@Param("result") Result result, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}
