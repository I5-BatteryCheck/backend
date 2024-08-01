package I5.webserver.domain.Defect.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Type {

    POLLUTION("오염", "pollution"),
    DAMAGED("손상", "damaged");

    private final String key;
    private final String description;
}
