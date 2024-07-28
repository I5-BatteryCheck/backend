package I5.webserver.domain.Defect.Service;

import I5.webserver.domain.Defect.Entity.Defect;
import I5.webserver.domain.Defect.Repository.DefectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefectService {

    private final DefectRepository defectRepository;

    @Transactional
    public Long save(Defect defect) {
        return defectRepository.save(defect).getId();
    }
}
