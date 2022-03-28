package uz.pdp.cinemaroomb6.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse implements Serializable {
    private String message;
    private boolean isSuccess;
    private Object data;

    public ApiResponse(String message, boolean isSuccess) {
        this.message = message;
        this.isSuccess = isSuccess;
    }
}
