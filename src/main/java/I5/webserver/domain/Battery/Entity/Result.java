package I5.webserver.domain.Battery.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Result {

    NORMAL("정상", "normal"),
    DEFECT("불량", "defect");

    private final String key;
    private final String description;
}
