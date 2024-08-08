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
public class BatteryConditionResponseDto {

    private Double temperature;

    private Double humidity;

    private Double illuminance;

    private Double gas;

    public BatteryConditionResponseDto(Double temperature, Double humidity, Double illuminance, Double gas) {
        this.temperature = temperature != null ? temperature : 0.0;
        this.humidity = humidity != null ? humidity : 0.0;
        this.illuminance = illuminance != null ? illuminance : 0.0;
        this.gas = gas != null ? gas : 0.0;
    }

    public static BatteryConditionResponseDto toDto(Battery battery) {
        return BatteryConditionResponseDto.builder()
                .temperature(battery.getTemperature())
                .humidity(battery.getHumidity())
                .illuminance(battery.getIlluminance())
                .gas(battery.getGas())
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
