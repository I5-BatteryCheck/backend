package I5.webserver.global.Util;

import I5.webserver.global.common.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResponseUtil {

    public static void sendErrorResponse(HttpServletResponse response, int status) throws IOException {
        response.getWriter().write(ApiResponse.failure().toJson());
        response.setContentType("application/json");
        response.setStatus(status);
    }

}