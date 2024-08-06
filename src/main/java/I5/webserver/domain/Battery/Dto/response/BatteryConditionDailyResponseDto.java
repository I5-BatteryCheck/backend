package I5.webserver.domain.Battery.Dto.response;

import I5.webserver.domain.Battery.Entity.Battery;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class BatteryConditionDailyResponseDto {

    private LocalDate localDate;

    private Double temperature;

    private Double humidity;

    private Double illuminance;

    public BatteryConditionDailyResponseDto(LocalDate localDate, Double temperature, Double humidity, Double illuminance) {
        this.localDate = localDate;
        this.temperature = temperature != null ? temperature : 0.0;
        this.humidity = humidity != null ? humidity : 0.0;
        this.illuminance = illuminance != null ? illuminance : 0.0;
    }

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
