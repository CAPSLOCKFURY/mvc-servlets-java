package validators.base;

import java.util.LinkedList;
import java.util.List;

public class ValidationResult {

    private final List<String> localizedErrors;

    public ValidationResult(){
        localizedErrors = new LinkedList<>();
    }

    public void addLocalizedError(String error){
        localizedErrors.add(error);
    }

    public List<String> getLocalizedErrors(){
        return localizedErrors;
    }

    public boolean isValid(){
        return localizedErrors.isEmpty();
    }

}
