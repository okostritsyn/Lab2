package nc.apps.controllers.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such group")  // 404
public class GroupNotFoundException extends RuntimeException  {
    public GroupNotFoundException(String idPath) {
    }
}
