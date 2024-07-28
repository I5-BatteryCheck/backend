package I5.webserver.global.common;

import lombok.Builder;
import lombok.Getter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Getter
@Builder
public class ApiResponse<T> {

    @Builder.Default
    private Boolean success = true;
    private T data;

    public static <T> ApiResponse<T> success() {
        return ApiResponse.<T> builder().build();
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T> builder()
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> failure() {
        return ApiResponse.<T> builder()
                .success(false)
                .build();
    }

    public String toJson() {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(this);
    }
}
