package tasks;

import service.BillingService;

import java.util.TimerTask;

public class DeleteOldBillingsTask extends TimerTask {

    private final static BillingService billingService = BillingService.getInstance();

    @Override
    public void run() {
        billingService.deleteOldBillings();
    }
}
