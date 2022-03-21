package forms;

import forms.base.Form;
import forms.base.InputType;
import forms.base.annotations.HtmlInput;
import models.base.SqlColumn;
import models.base.SqlType;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AddBalanceForm extends Form {

    @SqlColumn(columnName = "", type = SqlType.DECIMAL)
    @HtmlInput(name = "amount", type = InputType.NUMBER, literal = "step=\"0.01\" class =\"form-control my-2\"")
    private BigDecimal amount;

    @Override
    public boolean validate() {
        if(amount != null){
            if(amount.compareTo(BigDecimal.ZERO) <= 0){
                addLocalizedError("errors.IncorrectMoneyAmount");
            }
        }
        return errors.size() == 0;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        try {
            BigDecimal decimalAmount = new BigDecimal(amount).setScale(2, RoundingMode.FLOOR);
            this.amount = decimalAmount;
        } catch (NumberFormatException nfe) {
            this.amount = new BigDecimal(0);
            addLocalizedError("errors.IncorrectMoneyAmount");
        }
    }
}
