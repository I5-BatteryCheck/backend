package I5.webserver.domain.Picture.Repository;

import I5.webserver.domain.Battery.Entity.Result;
import I5.webserver.domain.Defect.Entity.Type;
import I5.webserver.domain.Picture.Entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {

    Picture findByBatteryIdAndCameraNumber(Long id, Integer cameraNumber);

    @Query("SELECT p FROM Picture p " +
            "JOIN p.battery b " +
            "LEFT JOIN p.Defects d " +
            "WHERE (:startDate IS NULL OR b.testDate >= :startDate) " +
            "AND (:endDate IS NULL OR b.testDate <= :endDate) " +
            "AND (:types IS NULL OR d.type IN :types) " +
            "AND (:results IS NULL OR b.result IN :results) " +
            "AND (:cameraNumbers IS NULL OR p.cameraNumber IN :cameraNumbers)")
    List<Picture> findAllByFilters(@Param("startDate") LocalDateTime startDate,
                                   @Param("endDate") LocalDateTime endDate,
                                   @Param("results") List<Result> results,
                                   @Param("types") List<Type> types,
                                   @Param("cameraNumbers") List<Integer> cameraNumbers);

}
