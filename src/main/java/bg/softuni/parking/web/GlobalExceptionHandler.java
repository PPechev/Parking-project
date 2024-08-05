package bg.softuni.parking.web;

import bg.softuni.parking.exception.*;



import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.servlet.ModelAndView;



@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ReservationNotFoundException.class)
    public ModelAndView handleReservationNotFoundException(ReservationNotFoundException ex, Model model) {
        ModelAndView modelAndView = new ModelAndView("error");
        model.addAttribute("message", ex.getMessage());
        modelAndView.addObject("exception", ex);
        return modelAndView;
    }


    @ExceptionHandler(ParkingSpotNotFoundException.class)
    public ModelAndView handleParkingSpotNotFoundException(ParkingSpotNotFoundException ex, Model model) {
        ModelAndView modelAndView = new ModelAndView("error");
        model.addAttribute("message", ex.getMessage());
        modelAndView.addObject("exception", ex);
        return modelAndView;
    }


    @ExceptionHandler(BankCardNotFoundException.class)
    public ModelAndView handleBankCardNotFoundException(BankCardNotFoundException ex, Model model) {
        ModelAndView modelAndView = new ModelAndView("error");
        model.addAttribute("message", ex.getMessage());
        modelAndView.addObject("exception", ex);
        return modelAndView;
    }


    @ExceptionHandler(InvalidUserIdException.class)
    public ModelAndView handleInvalidUserIdException(InvalidUserIdException ex, Model model) {
        ModelAndView modelAndView = new ModelAndView("error");
        model.addAttribute("message", ex.getMessage());
        modelAndView.addObject("exception", ex);
        return modelAndView;
    }

    @ExceptionHandler(InvalidUserTypeException.class)
    public ModelAndView handleInvalidUserTypeException(InvalidUserTypeException ex, Model model) {
        ModelAndView modelAndView = new ModelAndView("error");
        model.addAttribute("message", ex.getMessage());
        modelAndView.addObject("exception", ex);
        return modelAndView;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ModelAndView handleUsernameNotFoundException(UsernameNotFoundException ex, Model model) {
        ModelAndView modelAndView = new ModelAndView("error");
        model.addAttribute("message", ex.getMessage());
        modelAndView.addObject("exception", ex);
        return modelAndView;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView handleUserNotFoundException(UserNotFoundException ex, Model model) {
        ModelAndView modelAndView = new ModelAndView("error");
        model.addAttribute("message", ex.getMessage());
        modelAndView.addObject("exception", ex);
        return modelAndView;
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ModelAndView handleRoleNotFoundException(RoleNotFoundException ex, Model model) {
        ModelAndView modelAndView = new ModelAndView("error");
        model.addAttribute("message", ex.getMessage());
        modelAndView.addObject("exception", ex);
        return modelAndView;
    }


}