package I5.webserver.domain.Picture.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/picture")
@Tag(name = "Picture", description = "배터리 사진 조회")
public class PictureController {

}
