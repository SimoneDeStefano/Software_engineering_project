package it.polito.ezshop.manager;

import it.polito.ezshop.dao.*;

public class EZShopManager {

    private static EZShopManager instance;

    private EZShopManager() {
    }

    public static EZShopManager getInstance() {
        if (instance == null) {
            instance = new EZShopManager();
        }

        return instance;
    }

    public void reset() {

        CustomerDao.getInstance().deleteAll();
        ReturnedProductDao.getInstance().deleteAll();
        SaleTransactionDao.getInstance().deleteAll();
        ReturnTransactionDao.getInstance().deleteAll();
        TicketEntryDao.getInstance().deleteAll();
        UserDao.getInstance().deleteAll();
        CustomerCardDao.getInstance().deleteAll();
        OrderDao.getInstance().deleteAll();
        BalanceOperationDao.getInstance().deleteAll();
        ProductTypeDao.getInstance().deleteAll();

    }
}
