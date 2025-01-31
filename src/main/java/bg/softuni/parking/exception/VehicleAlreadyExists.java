package bg.softuni.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class VehicleAlreadyExists extends RuntimeException {
    public VehicleAlreadyExists(String message) {
        super(message);
    }
}
