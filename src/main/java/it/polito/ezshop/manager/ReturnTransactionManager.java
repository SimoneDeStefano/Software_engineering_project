package it.polito.ezshop.manager;

import java.time.LocalDate;
import java.util.*;

import it.polito.ezshop.dao.*;
import it.polito.ezshop.enums.BalanceOperationTypeEnum;
import it.polito.ezshop.enums.ReturnTransactionEnum;
import it.polito.ezshop.enums.RoleTypeEnum;
import it.polito.ezshop.exceptions.*;
import it.polito.ezshop.model.*;

public class ReturnTransactionManager {

    private static ReturnTransactionManager instance;

    private ReturnTransactionManager() {

    }

    public static ReturnTransactionManager getInstance() {
        if (instance == null) {
            instance = new ReturnTransactionManager();
        }
        return instance;
    }

    public ReturnTransaction findById(Integer id) {
        List<ReturnTransaction> results = ReturnTransactionDao.getInstance()
                .findByCriteria(new HashMap<String, Object>() {
                    {
                        put("id", id);
                    }
                });

        if (results != null && !results.isEmpty()) {
            return results.get(0);
        }
        return null;
    }

    public Integer startReturnTransaction(Integer ticketNumber)
            throws InvalidTicketNumberException, UnauthorizedException {
        if (controlLogin()) {
            if (ticketNumber == null)
                throw new InvalidTicketNumberException();
            SaleTransaction transaction = SaleTransactionDao.getInstance().findById(ticketNumber);
            if (transaction != null) {
                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(ticketNumber);
                returnTransaction.setStatus(ReturnTransactionEnum.OPEN.name());
                returnTransaction.setPrice(0);
                returnTransaction = ReturnTransactionDao.getInstance().save(returnTransaction);

                return returnTransaction.getId();
            } else {
                throw new InvalidTicketNumberException();
            }
        } else {
            throw new UnauthorizedException();
        }
    }

    public boolean returnProduct(Integer returnId, String productCode, int returnAmount)
            throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException,
            UnauthorizedException {

        if (controlLogin()) {
            ReturnTransaction returnTransaction = findById(returnId);
            if (returnId == null)
                throw new InvalidTransactionIdException();
            if (returnTransaction != null && ReturnTransactionEnum.OPEN.name().equals(returnTransaction.getStatus())) {
                SaleTransaction saleTransaction = SaleTransactionDao.getInstance()
                        .findById(returnTransaction.getTicketNumber());
                List<TicketEntry> entries = saleTransaction.getTicketEntries();

                if (productCode == null)
                    throw new InvalidProductCodeException();
                TicketEntry entry = isInList(productCode, entries);
                ProductType product;
                if (entry != null) {
                    if (returnAmount > 0 && entry.getAmount() >= returnAmount) {

                        ReturnedProduct returnedProduct = new ReturnedProduct();
                        returnedProduct.setAmount(returnAmount);
                        returnedProduct.setPrice(entry.getPricePerUnit());
                        returnedProduct.setBarCode(entry.getBarCode());
                        returnedProduct.setReturnTransaction(returnTransaction);
                        ReturnedProductDao.getInstance().save(returnedProduct);

                        returnTransaction.addProduct(returnedProduct);

                        entry.setAmount(entry.getAmount() - returnAmount);
                        product = ProductTypeDao.getInstance().findByBarCode(entry.getBarCode());
                        product.setQuantity(product.getQuantity() + returnAmount);

                        double salePrice = saleTransaction.getPrice();

                        if(saleTransaction.getDiscountRate() != 0.00){
                            saleTransaction.setPrice(salePrice -
                                    (entry.getPricePerUnit()-entry.getPricePerUnit()*entry.getDiscountRate())
                                            *saleTransaction.getDiscountRate()
                                            *returnAmount);
                        }
                        else{
                            saleTransaction.setPrice(salePrice -
                                    (entry.getPricePerUnit()-entry.getPricePerUnit()*entry.getDiscountRate())
                                            *returnAmount);
                        }

                        returnTransaction.setPrice(returnTransaction.getPrice() +
                                salePrice - saleTransaction.getPrice());

                        ProductTypeDao.getInstance().save(product);
                        TicketEntryDao.getInstance().save(entry);
                        SaleTransactionDao.getInstance().save(saleTransaction);
                        ReturnTransactionDao.getInstance().save(returnTransaction);
                        return true;
                    } else {
                        throw new InvalidQuantityException();
                    }
                } else {
                    throw new InvalidProductCodeException();
                }
            } else {
                throw new InvalidTransactionIdException();
            }
        } else {
            throw new UnauthorizedException();
        }
    }

    public boolean endReturnTransaction(Integer returnId, boolean commit)
            throws InvalidTransactionIdException, UnauthorizedException {

        if (controlLogin()) {
            if (returnId == null)
                throw new InvalidTransactionIdException();
            ReturnTransaction returnTransaction = findById(returnId);
            if (returnTransaction != null && ReturnTransactionEnum.OPEN.name().equals(returnTransaction.getStatus())
                    && commit) {
                returnTransaction.setStatus(ReturnTransactionEnum.CLOSED.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);
                return true;
            } else if (returnTransaction != null
                    && ReturnTransactionEnum.OPEN.name().equals(returnTransaction.getStatus()) && !commit) {
                returnTransaction.setStatus(ReturnTransactionEnum.CLOSED.name());
                ReturnTransactionDao.getInstance().save(returnTransaction);
                deleteReturnTransaction(returnId);
                return false;
            } else {
                throw new InvalidTransactionIdException();
            }
        } else {
            throw new UnauthorizedException();
        }
    }

    public boolean deleteReturnTransaction(Integer returnId)
            throws InvalidTransactionIdException, UnauthorizedException {
        if(!controlLogin())
            throw new UnauthorizedException();
        if (returnId == null)
            throw new InvalidTransactionIdException();
        ReturnTransaction transaction = findById(returnId);
        if (transaction == null)
            throw new InvalidTransactionIdException();
        if (ReturnTransactionEnum.CLOSED.name().equals(transaction.getStatus())) {
            updateTicket(transaction, transaction.getProducts());
            updateInventory(transaction.getProducts());
            List<ReturnedProduct> products = ReturnedProductDao.getInstance().findAll();
            for (ReturnedProduct product : products)
                ReturnedProductDao.getInstance().delete(product);
            ReturnTransactionDao.getInstance().delete(transaction);
            return true;
        }
        else{
            throw new InvalidTransactionIdException();
        }
    }

    public double returnCashPayment(Integer returnId) throws InvalidTransactionIdException, UnauthorizedException {

        if (controlLogin()) {
            ReturnTransaction transaction = findById(returnId);
            if (transaction != null && ReturnTransactionEnum.CLOSED.name().equals(transaction.getStatus())) {
                BalanceOperation returnOperation = new BalanceOperation();
                returnOperation.setDate(LocalDate.now());
                returnOperation.setType(BalanceOperationTypeEnum.RETURN.name());
                returnOperation.setMoney(transaction.getPrice());

                BalanceOperationDao.getInstance().save(returnOperation);
                return transaction.getPrice();
            } else {
                throw new InvalidTransactionIdException();
            }
        } else {
            throw new UnauthorizedException();
        }
    }

    public double returnCreditCardPayment(Integer returnId, String creditCard)
            throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException {

        if (controlLogin()) {
            if(returnId == null)
                throw new InvalidTransactionIdException();
            if(creditCard==null || creditCard.isEmpty())
                throw new InvalidCreditCardException();
            ReturnTransaction transaction = findById(returnId);
            if (transaction != null && ReturnTransactionEnum.CLOSED.name().equals(transaction.getStatus())) {

                if (checkCreditCard(creditCard)) {
                    BalanceOperationManager.getInstance().recordBalanceUpdate(transaction.getPrice());
                    return transaction.getPrice();
                } else {
                    throw new InvalidCreditCardException();
                }
            } else {
                throw new InvalidTransactionIdException();
            }
        } else {
            throw new UnauthorizedException();
        }
    }

    private boolean checkCreditCard(String creditCard) {
        int nDigits = creditCard.length();

        int nSum = 0;
        boolean isSecond = false;
        for (int i = nDigits - 1; i >= 0; i--) {

            int d = creditCard.charAt(i) - '0';
            if (isSecond)
                d = d * 2;
            nSum += d / 10;
            nSum += d % 10;

            isSecond = !isSecond;
        }
        return (nSum % 10 == 0);
    }

    private void updateTicket(ReturnTransaction transaction, List<ReturnedProduct> returnedProducts) {
        int operation=1;

        TicketEntry saleEntry;
        SaleTransaction saleTransaction = SaleTransactionDao.getInstance().findById(transaction.getTicketNumber());

        List<TicketEntry> saleEntries = saleTransaction.getTicketEntries();

        for (ReturnedProduct returnedProduct : returnedProducts) {
            saleEntry = isInList(returnedProduct.getBarCode(), saleEntries);
            assert saleEntry != null;
            saleEntry.setAmount(saleEntry.getAmount() + (operation * (returnedProduct.getAmount())));
            TicketEntryDao.getInstance().save(saleEntry);
        }

        saleTransaction.setPrice(saleTransaction.getPrice()+transaction.getPrice());
        SaleTransactionDao.getInstance().save(saleTransaction);
    }

    private void updateInventory(List<ReturnedProduct> returnedProducts) {
        int operation=1;

        for (ReturnedProduct returnedProduct : returnedProducts) {
            ProductType product = ProductTypeDao.getInstance().findByBarCode(returnedProduct.getBarCode());
            product.setQuantity(product.getQuantity() - ((operation) * (returnedProduct.getAmount())));
            ProductTypeDao.getInstance().save(product);
        }
    }


    private TicketEntry isInList(String productCode, List<TicketEntry> entries) {
        for (TicketEntry entry : entries) {
            if (entry.getBarCode().equals(productCode)) {
                return entry;
            }
        }
        return null;
    }

    private boolean controlLogin() {
        return (AuthenticationManager.getInstance().getAuthUser() != null && (AuthenticationManager.getInstance()
                .getAuthUser().getRole().equals(RoleTypeEnum.Administrator.name())
                || AuthenticationManager.getInstance().getAuthUser().getRole().equals(RoleTypeEnum.ShopManager.name())
                || AuthenticationManager.getInstance().getAuthUser().getRole().equals(RoleTypeEnum.Cashier.name())));
    }

}
