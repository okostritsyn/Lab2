package nc.apps.errors;

import lombok.extern.log4j.Log4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Log4j
class ErrorController {
    /*
    @ExceptionHandler
    public ResponseEntity<AppError> catchResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }
    */

    @ExceptionHandler(value = Exception.class)
    public ModelAndView
    defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException example
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
        ResponseStatus annotation = AnnotationUtils.findAnnotation
                (e.getClass(), ResponseStatus.class);

        // (AnnotationUtils.findAnnotation
        //        (e.getClass(), ResponseStatus.class) != null)
        //    throw e;

        // Otherwise setup and send the user to a default error-view.
        ModelAndView model = new ModelAndView();
        model.addObject("code", annotation != null?annotation.code():"500 SERVER ERROR");
        model.addObject("reason", annotation != null?annotation.reason():"");
        model.addObject("message", e.getMessage());
        model.addObject("cause", e.getCause());
        model.addObject("exception", e);
        model.addObject("url", req.getRequestURL());
        model.setViewName("error_page");
        return model;
    }
}