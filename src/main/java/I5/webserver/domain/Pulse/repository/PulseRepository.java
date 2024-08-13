package I5.webserver.domain.Pulse.repository;

import I5.webserver.domain.Pulse.entity.Pulse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PulseRepository extends JpaRepository<Pulse, Long> {
}
