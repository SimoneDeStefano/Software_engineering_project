package it.polito.ezshop.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.polito.ezshop.dao.CustomerCardDao;
import it.polito.ezshop.dao.CustomerDao;
import it.polito.ezshop.enums.RoleTypeEnum;
import it.polito.ezshop.exceptions.InvalidCustomerCardException;
import it.polito.ezshop.exceptions.InvalidCustomerIdException;
import it.polito.ezshop.exceptions.InvalidCustomerNameException;
import it.polito.ezshop.exceptions.UnauthorizedException;
import it.polito.ezshop.model.Customer;
import it.polito.ezshop.model.CustomerCard;

public class CustomerManager {

    private static CustomerManager instance;

    private CustomerManager() {

    }

    public static CustomerManager getInstance() {
        if (instance == null) {
            instance = new CustomerManager();
        }
        return instance;
    }

    private Customer findById(Integer id) {
        List<Customer> foundCustomers = CustomerDao.getInstance().findByCriteria(new HashMap<String, Object>() {
            {
                put("id", id);
            }
        });
        if (foundCustomers != null && !foundCustomers.isEmpty()) {
            return foundCustomers.get(0);
        }
        return null;
    }

    public Integer defineCustomer(String customerName) throws UnauthorizedException, InvalidCustomerNameException {

        if (controlLogin()) {
            if (customerName != null && !customerName.isEmpty()) {
                if (!isPresent(customerName)) {
                    Customer newCustomer = new Customer();

                    newCustomer.setCustomerName(customerName);
                    newCustomer.setCustomerCard(null);
                    newCustomer.setPoints(0);

                    newCustomer = CustomerDao.getInstance().save(newCustomer);

                    return newCustomer.getId();
                } else {
                    throw new InvalidCustomerNameException();
                }
            } else {
                throw new InvalidCustomerNameException();
            }
        } else {
            throw new UnauthorizedException();
        }
    }

    private boolean isPresent(String customerName) throws UnauthorizedException {
        List<it.polito.ezshop.data.Customer> customers = getAllCustomers();
        for (it.polito.ezshop.data.Customer c : customers) {
            if (c.getCustomerName().equals(customerName)) {
                return true;
            }
        }
        return false;
    }

    public boolean deleteCustomer(Integer id) throws InvalidCustomerIdException, UnauthorizedException {

        if (controlLogin()) {
            if(id == null)
                throw new InvalidCustomerIdException();
            Customer deletedCustomer = findById(id);
            if (deletedCustomer != null) {
                CustomerDao.getInstance().delete(deletedCustomer);

                if (deletedCustomer.getCustomerCard() != null) {
                    try {
                        CustomerCardManager.getInstance().deleteCard(deletedCustomer.getCustomerCard());
                    } catch (InvalidCustomerCardException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } else {
                throw new InvalidCustomerIdException();
            }
        } else {
            throw new UnauthorizedException();
        }
        return true;
    }

    public boolean modifyCustomer(Integer id, String newCustomerName, String newCustomerCard)
            throws InvalidCustomerNameException, InvalidCustomerIdException, InvalidCustomerCardException,
            UnauthorizedException {

        if (controlLogin()) {
            Customer customer = findById(id);
            if (customer != null) {
                if(newCustomerName == null)
                    throw new InvalidCustomerNameException();
                if(customer.getCustomerName().equals(newCustomerName))
                    newCustomerName="";
                if (!newCustomerName.isEmpty() && !isPresent(newCustomerName)) {
                    customer.setCustomerName(newCustomerName);
                    CustomerDao.getInstance().save(customer);
                } else if(newCustomerName.isEmpty()) {
                    customer.setCustomerName(customer.getCustomerName());
                    CustomerDao.getInstance().save(customer);
                }else {
                    throw new InvalidCustomerNameException();
                }

                if (newCustomerCard != null) {
                    if (!newCustomerCard.isEmpty()) {
                        int l = newCustomerCard.length();
                        if (newCustomerCard.length() == 10) {
                            CustomerCard card = CustomerCardManager.getInstance().findByCode(newCustomerCard);
                            if (card != null) {
                                return attachCardToCustomer(card.getCardCode(), id);
                            } else {
                                throw new InvalidCustomerCardException();
                            }
                        } else
                            throw new InvalidCustomerCardException();
                    } else if (customer.getCustomerCard() != null) {
                        CustomerCardManager.getInstance().deleteCard(customer.getCustomerCard());
                        customer.setCustomerCard(null);
                        customer.setPoints(0);
                        CustomerDao.getInstance().save(customer);
                    }
                }
                return true;
            } else {
                throw new InvalidCustomerIdException();
            }
        } else {
            throw new UnauthorizedException();
        }
    }

    public boolean attachCardToCustomer(String cardCode, Integer id)
            throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {
        if (controlLogin()) {
            if (id == null)
                throw new InvalidCustomerIdException();
            CustomerCard card = CustomerCardManager.getInstance().findByCode(cardCode);
            if (cardCode != null && cardCode.length() == 10 && card != null) {
                Customer customer = findById(id);
                if (customer != null) {
                    if (customer.getCustomerCard() != null) {
                        CustomerCard oldCard = CustomerCardManager.getInstance().findByCode(customer.getCustomerCard());
                        CustomerCardDao.getInstance().delete(oldCard);
                    }
                    CustomerCardManager.getInstance().attachCard(cardCode, id);
                    customer.setCustomerCard(cardCode);
                    CustomerDao.getInstance().save(customer);
                    return true;
                } else {
                    throw new InvalidCustomerIdException();
                }
            } else {
                throw new InvalidCustomerCardException();
            }
        } else {
            throw new UnauthorizedException();
        }
    }

    public Customer getCustomer(Integer id) throws InvalidCustomerIdException, UnauthorizedException {
        if (controlLogin()) {
            Customer customer = findById(id);
            if (customer != null) {
                return customer;
            } else {
                throw new InvalidCustomerIdException();
            }
        } else {
            throw new UnauthorizedException();
        }
    }

    public List<it.polito.ezshop.data.Customer> getAllCustomers() throws UnauthorizedException {
        if (controlLogin()) {
            List<Customer> customers = CustomerDao.getInstance().findAll();
            return new ArrayList<>(customers);
        } else {
            throw new UnauthorizedException();
        }
    }

    public boolean modifyPointsOnCard(String customerCard, int pointsToBeAdded)
            throws InvalidCustomerCardException, UnauthorizedException {
        if (controlLogin()) {
            if (customerCard == null || customerCard.length() != 10) {
                throw new InvalidCustomerCardException();
            }
            CustomerCard card = CustomerCardManager.getInstance().findByCode(customerCard);
            if (card != null && (card.getCardPoints() + pointsToBeAdded) >= 0) {
                card.setCardPoints(card.getCardPoints() + pointsToBeAdded);
                CustomerCardDao.getInstance().save(card);
                Customer customer = findById(card.getCustomerId());
                assert customer != null;
                customer.setPoints(card.getCardPoints());
                CustomerDao.getInstance().save(customer);
                return true;
            } else {
                throw new InvalidCustomerCardException();
            }
        } else {
            throw new UnauthorizedException();
        }
    }

    private boolean controlLogin() {
        return (AuthenticationManager.getInstance().getAuthUser() != null && (AuthenticationManager.getInstance()
                .getAuthUser().getRole().equals(RoleTypeEnum.Administrator.name())
                || AuthenticationManager.getInstance().getAuthUser().getRole().equals(RoleTypeEnum.ShopManager.name())
                || AuthenticationManager.getInstance().getAuthUser().getRole().equals(RoleTypeEnum.Cashier.name())));
    }
}
