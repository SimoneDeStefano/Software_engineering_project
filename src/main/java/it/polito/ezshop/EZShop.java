package it.polito.ezshop;

import it.polito.ezshop.data.EZShopInterface;
import it.polito.ezshop.util.HibernateConfiguration;
import it.polito.ezshop.view.EZShopGUI;

public class EZShop {
    public static void main(String[] args) {

        HibernateConfiguration.getSessionFactory();

        EZShopInterface ezShop = new it.polito.ezshop.data.EZShop();
        EZShopGUI gui = new EZShopGUI(ezShop);
    }
}
