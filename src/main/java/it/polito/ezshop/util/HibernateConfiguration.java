package it.polito.ezshop.util;

import java.util.Properties;

import it.polito.ezshop.model.ReturnedProduct;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import it.polito.ezshop.dao.ProductTypeDao;
import it.polito.ezshop.dao.UserDao;
import it.polito.ezshop.enums.RoleTypeEnum;
import it.polito.ezshop.model.ProductType;
import it.polito.ezshop.model.SaleTransaction;
import it.polito.ezshop.model.TicketEntry;
import it.polito.ezshop.model.User;

public class HibernateConfiguration {
        private static SessionFactory sessionFactory;

        public static SessionFactory getSessionFactory() {
                if (sessionFactory == null) {
                        try {
                                Configuration configuration = new Configuration();

                                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                                Properties settings = new Properties();
                                settings.put(Environment.DRIVER, "org.sqlite.JDBC");
                                settings.put(Environment.URL,
                                                "jdbc:sqlite:src/main/java/it/polito/ezshop/util/ezshop.db");
                                settings.put(Environment.USER, "");
                                settings.put(Environment.PASS, "");
                                settings.put(Environment.DIALECT, "com.enigmabridge.hibernate.dialect.SQLiteDialect");

                                settings.put(Environment.SHOW_SQL, "true");
                                settings.put(Environment.AUTOCOMMIT, "true");

                                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                                String HBM2DDL_AUTO = "create-drop";
                                // String HBM2DDL_AUTO = "update";
                                // settings.put(Environment.HBM2DDL_AUTO, "update");
                                settings.put(Environment.HBM2DDL_AUTO, HBM2DDL_AUTO);

                                configuration.setProperties(settings);

                                // add here all class mapped

                                configuration.addAnnotatedClass(it.polito.ezshop.model.BalanceOperation.class);
                                configuration.addAnnotatedClass(it.polito.ezshop.model.User.class);
                                configuration.addAnnotatedClass(it.polito.ezshop.model.TicketEntry.class)
                                                .addAnnotatedClass(SaleTransaction.class);
                                configuration.addAnnotatedClass(it.polito.ezshop.model.SaleTransaction.class)
                                                .addAnnotatedClass(TicketEntry.class);

                                configuration.addAnnotatedClass(it.polito.ezshop.model.ProductType.class);
                                configuration.addAnnotatedClass(it.polito.ezshop.model.Order.class)
                                                .addAnnotatedClass(ProductType.class);
                                configuration.addAnnotatedClass(it.polito.ezshop.model.Customer.class);
                                configuration.addAnnotatedClass(it.polito.ezshop.model.CustomerCard.class);
                                configuration.addAnnotatedClass(it.polito.ezshop.model.ReturnedProduct.class)
                                                .addAnnotatedClass(it.polito.ezshop.model.ReturnTransaction.class);
                                configuration.addAnnotatedClass(it.polito.ezshop.model.ReturnTransaction.class)
                                                .addAnnotatedClass(ReturnedProduct.class);

                                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                                                .applySettings(configuration.getProperties()).build();

                                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

                                if ("create-drop".equals(HBM2DDL_AUTO)) {

                                        User admin = new User();
                                        admin.setUsername("admin");
                                        admin.setRole(RoleTypeEnum.Administrator.name());
                                        admin.setPassword("admin");

                                        ProductType pt = new ProductType();
                                        pt.setBarCode("123123123125");
                                        pt.setPricePerUnit(1d);
                                        pt.setNote("test product note");
                                        pt.setProductDescription("test product note");
                                        pt.setLocation("1-a-1");
                                        pt.setQuantity(100);

                                        ProductTypeDao.getInstance().save(pt);
                                        UserDao.getInstance().save(admin);
                                }

                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                }
                return sessionFactory;
        }
}
