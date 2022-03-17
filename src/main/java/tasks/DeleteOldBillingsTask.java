package tasks;

import service.BillingService;

import java.util.TimerTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteOldBillingsTask extends TimerTask {

    private final static BillingService billingService = BillingService.getInstance();

    private final static Logger logger = LogManager.getLogger();

    @Override
    public void run() {
        logger.info("Started deleting old billings task");
        int affectedRows = billingService.deleteOldBillings();
        logger.info("Finished deleting old billings number of affected rows {}", affectedRows);
    }
}
