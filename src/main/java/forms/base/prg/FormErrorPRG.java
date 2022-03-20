package forms.base.prg;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Base interface for error prg transport
 */
public interface FormErrorPRG {
    void processErrors(HttpServletRequest request, HttpServletResponse response);
}
