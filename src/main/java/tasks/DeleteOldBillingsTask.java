package tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.BillingService;

import java.util.TimerTask;

public class DeleteOldBillingsTask extends TimerTask {

    private static final BillingService billingService = BillingService.getInstance();

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void run() {
        logger.info("Started deleting old billings task");
        int affectedRows = billingService.deleteOldBillings();
        logger.info("Finished deleting old billings number of affected rows {}", affectedRows);
    }
}
