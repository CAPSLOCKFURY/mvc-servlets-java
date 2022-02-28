package forms.base;

import java.util.LinkedList;
import java.util.List;

public abstract class Form {
    protected List<String> errors = new LinkedList<>();

    public List<String> getErrors() {
        return errors;
    }

    public abstract boolean validate();

}
