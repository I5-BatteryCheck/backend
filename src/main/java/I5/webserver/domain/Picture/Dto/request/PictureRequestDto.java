package I5.webserver.domain.Picture.Dto.request;

import I5.webserver.domain.Picture.Entity.Picture;
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
public class PictureRequestDto {

    private Long batteryId;

    private String pictureName;

    private Integer cameraNumber;

    public static PictureRequestDto toDto(Picture picture) {
        return PictureRequestDto.builder()
                .batteryId(picture.getBattery().getId())
                .pictureName(picture.getPictureName())
                .cameraNumber(picture.getCameraNumber())
                .build();
    }

}
