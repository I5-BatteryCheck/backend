package I5.webserver.domain.Picture.Dto.response;

import I5.webserver.domain.Picture.Entity.Picture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PictureResponseDto {

    private Long id;
    private Long batteryId;
    private String pictureName;
    private Integer cameraNumber;

    public static PictureResponseDto toDto(Picture picture) {
        return PictureResponseDto.builder()
                .id(picture.getId())
                .batteryId(picture.getBattery().getId())
                .pictureName(picture.getPictureName())
                .cameraNumber(picture.getCameraNumber())
                .build();
    }


}
