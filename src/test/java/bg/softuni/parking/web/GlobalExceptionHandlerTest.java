

package bg.softuni.parking.web;

import bg.softuni.parking.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class GloalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        model = mock(Model.class);
    }

    @Test
    void handleReservationNotFoundException() {
        ReservationNotFoundException exception = new ReservationNotFoundException("Reservation not found");
        ModelAndView modelAndView = exceptionHandler.handleReservationNotFoundException(exception, model);
        assertEquals("error", modelAndView.getViewName());
        assertEquals(exception, modelAndView.getModel().get("exception"));
    }

    @Test
    void handleParkingSpotNotFoundException() {
        ParkingSpotNotFoundException exception = new ParkingSpotNotFoundException("Parking spot not found");
        ModelAndView modelAndView = exceptionHandler.handleParkingSpotNotFoundException(exception, model);
        assertEquals("error", modelAndView.getViewName());
        assertEquals(exception, modelAndView.getModel().get("exception"));
    }

    @Test
    void handleBankCardNotFoundException() {
        BankCardNotFoundException exception = new BankCardNotFoundException("Bank card not found");
        ModelAndView modelAndView = exceptionHandler.handleBankCardNotFoundException(exception, model);
        assertEquals("error", modelAndView.getViewName());
        assertEquals(exception, modelAndView.getModel().get("exception"));
    }

    @Test
    void handleInvalidUserIdException() {
        InvalidUserIdException exception = new InvalidUserIdException("Invalid user ID");
        ModelAndView modelAndView = exceptionHandler.handleInvalidUserIdException(exception, model);
        assertEquals("error", modelAndView.getViewName());
        assertEquals(exception, modelAndView.getModel().get("exception"));
    }

    @Test
    void handleInvalidUserTypeException() {
        InvalidUserTypeException exception = new InvalidUserTypeException("Invalid user type");
        ModelAndView modelAndView = exceptionHandler.handleInvalidUserTypeException(exception, model);
        assertEquals("error", modelAndView.getViewName());
        assertEquals(exception, modelAndView.getModel().get("exception"));
    }


    @Test
    void handleUserNotFoundException() {
        UserNotFoundException exception = new UserNotFoundException("User not found");
        ModelAndView modelAndView = exceptionHandler.handleUserNotFoundException(exception, model);
        assertEquals("error", modelAndView.getViewName());
        assertEquals(exception, modelAndView.getModel().get("exception"));
    }

    @Test
    void handleRoleNotFoundException() {
        RoleNotFoundException exception = new RoleNotFoundException("Role not found");
        ModelAndView modelAndView = exceptionHandler.handleRoleNotFoundException(exception, model);
        assertEquals("error", modelAndView.getViewName());
        assertEquals(exception, modelAndView.getModel().get("exception"));
    }
}
