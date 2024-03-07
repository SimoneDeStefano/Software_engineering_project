package it.polito.ezshop.manager;

import it.polito.ezshop.dao.*;
import it.polito.ezshop.enums.BalanceOperationTypeEnum;
import it.polito.ezshop.enums.RoleTypeEnum;
import it.polito.ezshop.enums.SaleTransactionEnum;
import it.polito.ezshop.exceptions.*;
import it.polito.ezshop.model.*;
import it.polito.ezshop.util.HibernateConfiguration;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class SaleTransactionManager {

    private static SaleTransaction openTransaction;
    private TicketEntry ticket;
    private static SaleTransactionManager instance;

    private SaleTransactionManager() {

    }

    public static SaleTransactionManager getInstance() {
        if (instance == null) {
            instance = new SaleTransactionManager();
        }

        if (openTransaction != null) {
            openTransaction = SaleTransactionDao.getInstance().findById(openTransaction.getTicketNumber());
        }

        return instance;
    }

    // ------------------------------------------------------------//
    public boolean checkAuthenticationManager() {
        return (AuthenticationManager.getInstance().getAuthUser() != null && (AuthenticationManager.getInstance()
                .getAuthUser().getRole().equals(RoleTypeEnum.Administrator.name())
                || AuthenticationManager.getInstance().getAuthUser().getRole().equals(RoleTypeEnum.ShopManager.name())
                || AuthenticationManager.getInstance().getAuthUser().getRole().equals(RoleTypeEnum.Cashier.name())));
    }

    // ----------------------------------------------------------------------------------------------------------------------------------//

    public Integer startSaleTransaction() throws UnauthorizedException {

        if (!checkAuthenticationManager()) {
            throw new UnauthorizedException();
        }

        openTransaction = new SaleTransaction();
        openTransaction.setStatus(SaleTransactionEnum.OPEN.name());

        openTransaction.setPrice(0);
        openTransaction = SaleTransactionDao.getInstance().save(openTransaction);

        return openTransaction.getTicketNumber();

    }

    public boolean addProductToSale(Integer transactionId, String productCode, int amount)
            throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException,
            UnauthorizedException {
        if (!checkAuthenticationManager()) {
            throw new UnauthorizedException();
        }
        if (transactionId == null || transactionId <= 0) {

            throw new InvalidTransactionIdException();
        }

        if (!(ProductTypeManager.getInstance().checkProductCode(productCode))) {

            throw new InvalidProductCodeException();
        }

        ProductType currentProduct = ProductTypeManager.getInstance().getProductTypeByBarCode(productCode);

        if (currentProduct == null) {
            return false;
        }
        if (amount < 0) {
            throw new InvalidQuantityException();
        } else if (amount > currentProduct.getQuantity() || amount == 0) {
            return false;
        }

        if (openTransaction != null && SaleTransactionEnum.OPEN.name().equals(openTransaction.getStatus())) {
            currentProduct.setQuantity(currentProduct.getQuantity() - amount);

            Optional<TicketEntry> existingTe = openTransaction.getTicketEntries().stream()
                    .filter(entry -> entry.getBarCode().equals(currentProduct.getBarCode())).map(foundEntry -> {
                        foundEntry.setAmount(foundEntry.getAmount() + amount);

                        return foundEntry;
                    }).findFirst();

            if (existingTe.isPresent()) {
                ticket = existingTe.get();
            } else {

                ticket = new TicketEntry();
                ticket.setBarCode(currentProduct.getBarCode());
                ticket.setAmount(amount);
                ticket.setTicket_number(openTransaction.getTicketNumber());
                ticket.setPricePerUnit(currentProduct.getPricePerUnit());
                ticket.setProductDescription(currentProduct.getProductDescription());
            }

            TicketEntryDao.getInstance().save(ticket);
            ProductTypeDao.getInstance().save(currentProduct);

            openTransaction = SaleTransactionDao.getInstance().save(openTransaction);
            return true;
        } else {
            return false;
        }

    }

    public boolean deleteProductFromSale(Integer transactionId, String productCode, int amount)
            throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException,
            UnauthorizedException {

        if (!checkAuthenticationManager()) {
            throw new UnauthorizedException();
        }
        if (transactionId == null || transactionId <= 0) {
            throw new InvalidTransactionIdException();
        }

        if (!(ProductTypeManager.getInstance().checkProductCode(productCode))) {
            throw new InvalidProductCodeException();
        }

        ProductType currentProduct = ProductTypeManager.getInstance().getProductTypeByBarCode(productCode);

        if (currentProduct == null) {
            return false;
        }
        if (amount < 0) {
            throw new InvalidQuantityException();
        } else if (amount == 0) {
            return false;
        }
        if (openTransaction != null && SaleTransactionEnum.OPEN.name().equals(openTransaction.getStatus())) {
            currentProduct.setQuantity(currentProduct.getQuantity() + amount);
            TicketEntryDao.getInstance().delete(ticket);
            TicketEntryDao.getInstance().save(ticket);
            // ProductTypeDao.getInstance().save(currentProduct);
            ProductTypeDao.getInstance().save(currentProduct);
            return true;
        } else {
            return false;
        }
    }

    public boolean applyDiscountRateToProduct(Integer transactionId, String productCode, double discountRate)
            throws InvalidTransactionIdException, InvalidProductCodeException, InvalidDiscountRateException,
            UnauthorizedException {
        if (!checkAuthenticationManager()) {
            throw new UnauthorizedException();
        }
        if (transactionId == null || transactionId <= 0) {
            throw new InvalidTransactionIdException();
        }

        if (!(ProductTypeManager.getInstance().checkProductCode(productCode))) {
            throw new InvalidProductCodeException();
        }
        if (discountRate < 0 || discountRate >= 1) {
            throw new InvalidDiscountRateException();
        }
        ProductType currentProduct = ProductTypeManager.getInstance().getProductTypeByBarCode(productCode);
        if (currentProduct == null) {
            return false;
        }
        /*
         * if (openTransaction != null &&
         * SaleTransactionEnum.OPEN.name().equals(openTransaction.getStatus())) {
         * 
         * Optional<TicketEntry> existingTe =
         * openTransaction.getTicketEntries().stream() .filter(entry ->
         * entry.getBarCode().equals(productCode)).map(newEntry -> {
         * newEntry.setDiscountRate(discountRate); newEntry.setPricePerUnit(
         * newEntry.getPricePerUnit() - newEntry.getPricePerUnit() * discountRate);
         * return newEntry; }).findFirst();
         * 
         * if (existingTe.isPresent()) { ticket = existingTe.get();
         * 
         * TicketEntryDao.getInstance().save(ticket); return true; } else { return
         * false; }
         * 
         */
        if (openTransaction != null && SaleTransactionEnum.OPEN.name().equals(openTransaction.getStatus())) {
            ticket = new TicketEntry();
            ticket.setDiscountRate(discountRate);
            TicketEntryDao.getInstance().save(ticket);
            return true;
        } else {
            return false;
        }

    }

    public boolean applyDiscountRateToSale(Integer transactionId, double discountRate)
            throws InvalidTransactionIdException, InvalidDiscountRateException, UnauthorizedException {
        if (!checkAuthenticationManager()) {
            throw new UnauthorizedException();
        }
        if (transactionId == null || transactionId <= 0) {
            throw new InvalidTransactionIdException();
        }
        if (discountRate < 0 || discountRate >= 1) {
            throw new InvalidDiscountRateException();
        }
        if (openTransaction != null && (SaleTransactionEnum.OPEN.name().equals(openTransaction.getStatus())
                || SaleTransactionEnum.CLOSED.name().equals(openTransaction.getStatus()))) {

            openTransaction.setDiscountRate(discountRate);
            openTransaction = SaleTransactionDao.getInstance().save(openTransaction);
            return true;
        } else {
            return false;
        }
    }

    public int computePointsForSale(Integer transactionId) throws InvalidTransactionIdException, UnauthorizedException {

        if (!checkAuthenticationManager()) {
            throw new UnauthorizedException();
        }
        if (transactionId <= 0 || transactionId == null) {
            throw new InvalidTransactionIdException();
        }
        int points = 0;
        if (openTransaction == null) {
            return -1;
        } else if (SaleTransactionEnum.OPEN.name().equals(openTransaction.getStatus())
                || SaleTransactionEnum.CLOSED.name().equals(openTransaction.getStatus())
                || SaleTransactionEnum.PAYED.name().equals(openTransaction.getStatus())) {
            points = (int) (openTransaction.getPrice() / 10);
        }
        return points;
    }

    public boolean endSaleTransaction(Integer transactionId)
            throws InvalidTransactionIdException, UnauthorizedException {
        if (!checkAuthenticationManager()) {
            throw new UnauthorizedException();
        }
        if (transactionId <= 0 || transactionId == null) {
            throw new InvalidTransactionIdException();
        }
        if (openTransaction == null && !(SaleTransactionEnum.OPEN.name().equals(openTransaction.getStatus()))) {
            return false;
        }

        for (TicketEntry ticket : openTransaction.getTicketEntries()) {

            openTransaction.setPrice(openTransaction.getPrice() + (ticket.getAmount()
                    * (ticket.getPricePerUnit() - ticket.getPricePerUnit() * ticket.getDiscountRate())));
        }

        openTransaction.setPrice(
                openTransaction.getPrice() - (openTransaction.getPrice() * openTransaction.getDiscountRate()));
        openTransaction.setStatus(SaleTransactionEnum.CLOSED.name());
        SaleTransactionDao.getInstance().save(openTransaction);
        return true;

    }

    public boolean deleteSaleTransaction(Integer transactionId)
            throws InvalidTransactionIdException, UnauthorizedException {
        if (!checkAuthenticationManager()) {
            throw new UnauthorizedException();
        }
        if (transactionId <= 0 || transactionId == null) {
            throw new InvalidTransactionIdException();
        }
        if (openTransaction == null || SaleTransactionEnum.PAYED.name().equals(openTransaction.getStatus())) {
            return false;
        }
        SaleTransactionDao.getInstance().delete(openTransaction);

        for (TicketEntry te : openTransaction.getTicketEntries()) {

            ProductType pt = ProductTypeDao.getInstance().findByBarCode(te.getBarCode());

            try {
                ProductTypeManager.getInstance().updateQuantity(pt.getId(), te.getAmount());
            } catch (InvalidProductIdException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        openTransaction = null;
        return true;
    }

    public SaleTransaction getSaleTransaction(Integer transactionId)
            throws InvalidTransactionIdException, UnauthorizedException {
        if (!checkAuthenticationManager()) {
            throw new UnauthorizedException();
        }
        if (transactionId <= 0 || transactionId == null) {
            throw new InvalidTransactionIdException();
        }
        return SaleTransactionDao.getInstance().findById(transactionId);

    }

    /**
     * This method record the payment of a sale transaction with cash and returns
     * the change (if present). This method affects the balance of the system. It
     * can be invoked only after a user with role "Administrator", "ShopManager" or
     * "Cashier" is logged in.
     *
     * @param transactionId the number of the transaction that the customer wants to
     *                      pay
     * @param cash          the cash received by the cashier
     *
     * @return the change (cash - sale price) -1 if the sale does not exists, if the
     *         cash is not enough, if there is some problemi with the db
     *
     * @throws InvalidTransactionIdException if the number is less than or equal to
     *                                       0 or if it is null
     * @throws UnauthorizedException         if there is no logged user or if it has
     *                                       not the rights to perform the operation
     * @throws InvalidPaymentException       if the cash is less than or equal to 0
     */
    public double receiveCashPayment(Integer transactionId, double cash)
            throws InvalidTransactionIdException, InvalidPaymentException, UnauthorizedException {
        if (!checkAuthenticationManager()) {
            throw new UnauthorizedException();
        }
        if (transactionId <= 0 || transactionId == null) {
            throw new InvalidTransactionIdException();
        }
        if (cash <= 0) {
            throw new InvalidPaymentException();
        }

        if (!(openTransaction != null && SaleTransactionEnum.CLOSED.name().equals(openTransaction.getStatus()))
                || cash < openTransaction.getPrice()) {
            return -1;
        }
        BalanceOperation balance = new BalanceOperation();
        balance.setCreated_date(LocalDate.now().toString());
        balance.setMoney(openTransaction.getPrice());
        balance.setType(BalanceOperationTypeEnum.SALE.name());
        Session session = HibernateConfiguration.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            BalanceOperationDao.getInstance().appendSave(balance, session);
            openTransaction.setStatus(SaleTransactionEnum.PAYED.name());
            SaleTransactionDao.getInstance().appendSave(openTransaction, session);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
            }
            return -1;
        }

        return (cash - openTransaction.getPrice());

    }

    public boolean checkValidCard(String creditCard) {

        if (creditCard == null || creditCard.isEmpty() || !(creditCard.length() == 16)) {
            return false;
        }

        try {
            Long.parseLong(creditCard);
        } catch (NumberFormatException e) {
            return false;
        }

        int nDigits = creditCard.length();

        int nSum = 0;
        boolean isSecond = false;
        for (int i = nDigits - 1; i >= 0; i--) {

            int d = creditCard.charAt(i) - '0';

            if (isSecond == true)
                d = d * 2;

            nSum += d / 10;
            nSum += d % 10;

            isSecond = !isSecond;
        }
        return (nSum % 10 == 0);

    }

    /**
     * This method record the payment of a sale with credit card. If the card has
     * not enough money the payment should be refused. The credit card number
     * validity should be checked. It should follow the luhn algorithm. The credit
     * card should be registered in the system. This method affects the balance of
     * the system. It can be invoked only after a user with role "Administrator",
     * "ShopManager" or "Cashier" is logged in.
     *
     * @param transactionId the number of the sale that the customer wants to pay
     * @param creditCard    the credit card number of the customer
     *
     * @return true if the operation is successful false if the sale does not
     *         exists, if the card has not enough money, if the card is not
     *         registered, if there is some problem with the db connection
     *
     * @throws InvalidTransactionIdException if the sale number is less than or
     *                                       equal to 0 or if it is null
     * @throws InvalidCreditCardException    if the credit card number is empty,
     *                                       null or if luhn algorithm does not
     *                                       validate the credit card
     * @throws UnauthorizedException         if there is no logged user or if it has
     *                                       not the rights to perform the operation
     */
    public boolean receiveCreditCardPayment(Integer transactionId, String creditCard)
            throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException {
        if (!checkAuthenticationManager()) {
            throw new UnauthorizedException();
        }
        if (transactionId <= 0 || transactionId == null) {
            throw new InvalidTransactionIdException();
        }

        if (!(checkValidCard(creditCard))) {
            throw new InvalidCreditCardException();
        }

        if (!(openTransaction != null && SaleTransactionEnum.CLOSED.name().equals(openTransaction.getStatus()))) {
            return false;
        }
        BalanceOperation balance = new BalanceOperation();
        balance.setCreated_date(LocalDate.now().toString());
        balance.setMoney(openTransaction.getPrice());
        balance.setType(BalanceOperationTypeEnum.SALE.name());
        Session session = HibernateConfiguration.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            BalanceOperationDao.getInstance().appendSave(balance, session);
            openTransaction.setStatus(SaleTransactionEnum.PAYED.name());
            SaleTransactionDao.getInstance().appendSave(openTransaction, session);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
            }
            return false;

        }
        return true;
    }

}
