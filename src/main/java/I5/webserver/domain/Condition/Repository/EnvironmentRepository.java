package I5.webserver.domain.Condition.Repository;

import I5.webserver.domain.Condition.Entity.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvironmentRepository extends JpaRepository<Environment, Long> {
}
