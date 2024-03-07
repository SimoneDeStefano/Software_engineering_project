package it.polito.ezshop.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.polito.ezshop.exceptions.InvalidCreditCardException;
import it.polito.ezshop.exceptions.InvalidCustomerCardException;
import it.polito.ezshop.exceptions.InvalidCustomerIdException;
import it.polito.ezshop.exceptions.InvalidCustomerNameException;
import it.polito.ezshop.exceptions.InvalidDiscountRateException;
import it.polito.ezshop.exceptions.InvalidLocationException;
import it.polito.ezshop.exceptions.InvalidOrderIdException;
import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidPaymentException;
import it.polito.ezshop.exceptions.InvalidPricePerUnitException;
import it.polito.ezshop.exceptions.InvalidProductCodeException;
import it.polito.ezshop.exceptions.InvalidProductDescriptionException;
import it.polito.ezshop.exceptions.InvalidProductIdException;
import it.polito.ezshop.exceptions.InvalidQuantityException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidTicketNumberException;
import it.polito.ezshop.exceptions.InvalidTransactionIdException;
import it.polito.ezshop.exceptions.InvalidUserIdException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;
import it.polito.ezshop.manager.*;

public class EZShop implements EZShopInterface {

    @Override
    public void reset() {
        EZShopManager.getInstance().reset();
    }

    @Override
    public Integer createUser(String username, String password, String role)
            throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
        return UserManager.getInstance().createUser(username, password, role);
    }

    @Override
    public boolean deleteUser(Integer id) throws InvalidUserIdException, UnauthorizedException {
        return UserManager.getInstance().deleteUser(id);
    }

    @Override
    public List<User> getAllUsers() throws UnauthorizedException {
        return UserManager.getInstance().getAllUsers();
    }

    @Override
    public User getUser(Integer id) throws InvalidUserIdException, UnauthorizedException {
        return null;
    }

    @Override
    public boolean updateUserRights(Integer id, String role)
            throws InvalidUserIdException, InvalidRoleException, UnauthorizedException {
        return UserManager.getInstance().updateUserRights(id, role);
    }

    @Override
    public User login(String username, String password) throws InvalidUsernameException, InvalidPasswordException {

        return AuthenticationManager.getInstance().login(username, password);
    }

    @Override
    public boolean logout() {
        return AuthenticationManager.getInstance().logout();
    }

    @Override
    public Integer createProductType(String description, String productCode, double pricePerUnit, String note)
            throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException,
            UnauthorizedException {
        return ProductTypeManager.getInstance().createProductType(description, productCode, pricePerUnit, note);
    }

    @Override
    public boolean updateProduct(Integer id, String newDescription, String newCode, double newPrice, String newNote)
            throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
            InvalidPricePerUnitException, UnauthorizedException {
        return ProductTypeManager.getInstance().updateProduct(id, newDescription, newCode, newPrice, newNote);
    }

    @Override
    public boolean deleteProductType(Integer id) throws InvalidProductIdException, UnauthorizedException {
        return ProductTypeManager.getInstance().deleteProductType(id);
    }

    @Override
    public List<ProductType> getAllProductTypes() throws UnauthorizedException {
        return ProductTypeManager.getInstance().getAllProductTypes();
    }

    @Override
    public ProductType getProductTypeByBarCode(String barCode)
            throws InvalidProductCodeException, UnauthorizedException {
        return ProductTypeManager.getInstance().getProductTypeByBarCode(barCode);
    }

    @Override
    public List<ProductType> getProductTypesByDescription(String description) throws UnauthorizedException {
        return ProductTypeManager.getInstance().getProductTypesByDescription(description);
    }

    @Override
    public boolean updateQuantity(Integer productId, int toBeAdded)
            throws InvalidProductIdException, UnauthorizedException {
        return ProductTypeManager.getInstance().updateQuantity(productId, toBeAdded);
    }

    @Override
    public boolean updatePosition(Integer productId, String newPos)
            throws InvalidProductIdException, InvalidLocationException, UnauthorizedException {
        return ProductTypeManager.getInstance().updatePosition(productId, newPos);
    }

    @Override
    public Integer issueOrder(String productCode, int quantity, double pricePerUnit) throws InvalidProductCodeException,
            InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException {
        return OrderManager.getInstance().issueOrder(productCode, quantity, pricePerUnit);
    }

    @Override
    public Integer payOrderFor(String productCode, int quantity, double pricePerUnit)
            throws InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException,
            UnauthorizedException {
        return OrderManager.getInstance().payOrderFor(productCode, quantity, pricePerUnit);
    }

    @Override
    public boolean payOrder(Integer orderId) throws InvalidOrderIdException, UnauthorizedException {
        return OrderManager.getInstance().payOrder(orderId);
    }

    @Override
    public boolean recordOrderArrival(Integer orderId)
            throws InvalidOrderIdException, UnauthorizedException, InvalidLocationException {
        return OrderManager.getInstance().recordOrderArrival(orderId);
    }

    @Override
    public List<Order> getAllOrders() throws UnauthorizedException {
        return OrderManager.getInstance().getAllOrders();
    }

    @Override
    public Integer defineCustomer(String customerName) throws InvalidCustomerNameException, UnauthorizedException {
        return CustomerManager.getInstance().defineCustomer(customerName);
    }

    @Override
    public boolean modifyCustomer(Integer id, String newCustomerName, String newCustomerCard)
            throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException,
            UnauthorizedException {
        return CustomerManager.getInstance().modifyCustomer(id, newCustomerName, newCustomerCard);
    }

    @Override
    public boolean deleteCustomer(Integer id) throws InvalidCustomerIdException, UnauthorizedException {
        return CustomerManager.getInstance().deleteCustomer(id);
    }

    @Override
    public Customer getCustomer(Integer id) throws InvalidCustomerIdException, UnauthorizedException {
        return CustomerManager.getInstance().getCustomer(id);
    }

    @Override
    public List<Customer> getAllCustomers() throws UnauthorizedException {
        return CustomerManager.getInstance().getAllCustomers();
    }

    @Override
    public String createCard() throws UnauthorizedException {
        return CustomerCardManager.getInstance().createCard();
    }

    @Override
    public boolean attachCardToCustomer(String customerCard, Integer customerId)
            throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {
        return CustomerManager.getInstance().attachCardToCustomer(customerCard, customerId);
    }

    @Override
    public boolean modifyPointsOnCard(String customerCard, int pointsToBeAdded)
            throws InvalidCustomerCardException, UnauthorizedException {
        return CustomerManager.getInstance().modifyPointsOnCard(customerCard, pointsToBeAdded);
    }

    @Override
    public Integer startSaleTransaction() throws UnauthorizedException {
        return SaleTransactionManager.getInstance().startSaleTransaction();
    }

    @Override
    public boolean addProductToSale(Integer transactionId, String productCode, int amount)
            throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException,
            UnauthorizedException {
        return SaleTransactionManager.getInstance().addProductToSale(transactionId, productCode, amount);
    }

    @Override
    public boolean deleteProductFromSale(Integer transactionId, String productCode, int amount)
            throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException,
            UnauthorizedException {
        return SaleTransactionManager.getInstance().deleteProductFromSale(transactionId, productCode, amount);
    }

    @Override
    public boolean applyDiscountRateToProduct(Integer transactionId, String productCode, double discountRate)
            throws InvalidTransactionIdException, InvalidProductCodeException, InvalidDiscountRateException,
            UnauthorizedException {
        return SaleTransactionManager.getInstance().applyDiscountRateToProduct(transactionId, productCode,
                discountRate);
    }

    @Override
    public boolean applyDiscountRateToSale(Integer transactionId, double discountRate)
            throws InvalidTransactionIdException, InvalidDiscountRateException, UnauthorizedException {
        return SaleTransactionManager.getInstance().applyDiscountRateToSale(transactionId, discountRate);
    }

    @Override
    public int computePointsForSale(Integer transactionId) throws InvalidTransactionIdException, UnauthorizedException {
        return SaleTransactionManager.getInstance().computePointsForSale(transactionId);
    }

    @Override
    public boolean endSaleTransaction(Integer transactionId)
            throws InvalidTransactionIdException, UnauthorizedException {
        return SaleTransactionManager.getInstance().endSaleTransaction(transactionId);
    }

    @Override
    public boolean deleteSaleTransaction(Integer saleNumber)
            throws InvalidTransactionIdException, UnauthorizedException {
        return SaleTransactionManager.getInstance().deleteSaleTransaction(saleNumber);
    }

    @Override
    public SaleTransaction getSaleTransaction(Integer transactionId)
            throws InvalidTransactionIdException, UnauthorizedException {
        return SaleTransactionManager.getInstance().getSaleTransaction(transactionId);
    }

    @Override
    public Integer startReturnTransaction(Integer saleNumber)
            throws InvalidTicketNumberException, UnauthorizedException {
        return ReturnTransactionManager.getInstance().startReturnTransaction(saleNumber);
    }

    @Override
    public boolean returnProduct(Integer returnId, String productCode, int amount) throws InvalidTransactionIdException,
            InvalidProductCodeException, InvalidQuantityException, UnauthorizedException {
        return ReturnTransactionManager.getInstance().returnProduct(returnId, productCode, amount);
    }

    @Override
    public boolean endReturnTransaction(Integer returnId, boolean commit)
            throws InvalidTransactionIdException, UnauthorizedException {
        return ReturnTransactionManager.getInstance().endReturnTransaction(returnId, commit);
    }

    @Override
    public boolean deleteReturnTransaction(Integer returnId)
            throws InvalidTransactionIdException, UnauthorizedException {
        return ReturnTransactionManager.getInstance().deleteReturnTransaction(returnId);
    }

    @Override
    public double receiveCashPayment(Integer ticketNumber, double cash)
            throws InvalidTransactionIdException, InvalidPaymentException, UnauthorizedException {
        return SaleTransactionManager.getInstance().receiveCashPayment(ticketNumber, cash);
    }

    @Override
    public boolean receiveCreditCardPayment(Integer ticketNumber, String creditCard)
            throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException {
        return SaleTransactionManager.getInstance().receiveCreditCardPayment(ticketNumber, creditCard);
    }

    @Override
    public double returnCashPayment(Integer returnId) throws InvalidTransactionIdException, UnauthorizedException {
        return ReturnTransactionManager.getInstance().returnCashPayment(returnId);
    }

    @Override
    public double returnCreditCardPayment(Integer returnId, String creditCard)
            throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException {
        return ReturnTransactionManager.getInstance().returnCreditCardPayment(returnId, creditCard);
    }

    @Override
    public boolean recordBalanceUpdate(double toBeAdded) throws UnauthorizedException {
        return BalanceOperationManager.getInstance().recordBalanceUpdate(toBeAdded);
    }

    @Override
    public List<BalanceOperation> getCreditsAndDebits(LocalDate from, LocalDate to) throws UnauthorizedException {
        return BalanceOperationManager.getInstance().getCreditsAndDebits(from, to);
    }

    @Override
    public double computeBalance() throws UnauthorizedException {
        return 0;
    }
}
