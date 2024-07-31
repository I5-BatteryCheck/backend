package I5.webserver.domain.Battery.Dto.response;

import I5.webserver.domain.Battery.Entity.Battery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatteryConditionResponseDto {

    private Double temperature;

    private Double humidity;

    private Double illuminance;

    public static BatteryConditionResponseDto toDto(Battery battery) {
        return BatteryConditionResponseDto.builder()
                .temperature(battery.getTemperature())
                .humidity(battery.getHumidity())
                .illuminance(battery.getIlluminance())
                .build();
    }
}
