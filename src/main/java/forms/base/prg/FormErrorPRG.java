package forms.base.prg;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface FormErrorPRG {
    void processErrors(HttpServletRequest request, HttpServletResponse response);
}
