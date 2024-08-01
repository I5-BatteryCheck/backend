package I5.webserver.domain.Battery.Dto.response;

import I5.webserver.domain.Battery.Entity.Battery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;

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

    public Double getTemperature() {
        return formatDecimal(temperature);
    }

    public Double getHumidity() {
        return formatDecimal(humidity);
    }

    public Double getIlluminance() {
        return formatDecimal(illuminance);
    }

    private Double formatDecimal(Double value) {
        DecimalFormat df = new DecimalFormat("#.#");
        return Double.valueOf(df.format(value));
    }
}
