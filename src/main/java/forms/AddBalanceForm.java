package forms;

import forms.base.Form;
import forms.base.InputType;
import forms.base.annotations.HtmlInput;
import validators.annotations.Min;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AddBalanceForm extends Form {

    @HtmlInput(name = "amount", type = InputType.NUMBER, literal = "step=\"0.01\" class =\"form-control my-2\"")
    @Min(min = 0f, localizedError = "errors.IncorrectMoneyAmount")
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        try {
            this.amount = new BigDecimal(amount).setScale(2, RoundingMode.FLOOR);
        } catch (NumberFormatException nfe) {
            this.amount = BigDecimal.ZERO;
        }
    }
}
