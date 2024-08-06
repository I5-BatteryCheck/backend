package I5.webserver.domain.Battery.Entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Result {

    NORMAL("정상", "normal"),
    DAMAGED("손상", "damaged"),
    POLLUTION("오염", "pollution"),
    BOTH("혼합", "both");

    private final String key;
    private final String description;
}
