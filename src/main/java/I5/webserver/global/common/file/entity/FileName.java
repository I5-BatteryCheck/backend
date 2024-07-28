package I5.webserver.global.common.file.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class FileName {

    @NotBlank(message = "원본 파일을 입력해주세요.")
    @Column(nullable = false)
    private String originalName;

    @NotBlank(message = "업로드된 파일명을 입력해주세요.")
    @Column(nullable = false)
    private String savedName;

    @NotBlank(message = "업로드된 파일의 경로를 입력해주세요.")
    @Column(nullable = false)
    private String savedPath;

    @NotBlank(message = "파일의 확장자명을 입력해주세요.")
    @Column(columnDefinition = "CHAR(8)", nullable = false)
    private String extensionName;

}
