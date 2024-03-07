package it.polito.ezshop.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.polito.ezshop.dao.CustomerCardDao;
import it.polito.ezshop.enums.RoleTypeEnum;
import it.polito.ezshop.exceptions.InvalidCustomerCardException;
import it.polito.ezshop.exceptions.InvalidCustomerIdException;
import it.polito.ezshop.exceptions.UnauthorizedException;
import it.polito.ezshop.model.CustomerCard;

public class CustomerCardManager {

    private static CustomerCardManager instance;

    private CustomerCardManager() {

    }

    public static CustomerCardManager getInstance() {
        if (instance == null) {
            instance = new CustomerCardManager();
        }
        return instance;
    }

    public CustomerCard findByCode(String customerCard) {
        List<CustomerCard> foundCards = CustomerCardDao.getInstance().findByCriteria(new HashMap<String, Object>() {
            {
                put("cardCode", customerCard);
            }
        });

        if (foundCards != null && !foundCards.isEmpty()) {
            return foundCards.get(0);
        }
        return null;
    }

    public String createCard() throws UnauthorizedException {

        if (controlLogin()) {
            CustomerCard card = new CustomerCard();
            card = CustomerCardDao.getInstance().save(card);
            String cardCode = ((Integer) (card.getId() + 1000000000)).toString();
            card.setCardCode(cardCode);
            card.setCardPoints(0);
            CustomerCardDao.getInstance().save(card);
            return card.getCardCode();
        } else {
            throw new UnauthorizedException();
        }
    }

    public void deleteCard(String cardCode) throws InvalidCustomerCardException, UnauthorizedException {
        if (controlLogin()) {
            if(cardCode == null || cardCode.length() != 10)
                throw new InvalidCustomerCardException();
            CustomerCard deletedCard = findByCode(cardCode);
            if (deletedCard != null) {
                CustomerCardDao.getInstance().delete(deletedCard);
            } else {
                throw new InvalidCustomerCardException();
            }
        } else {
            throw new UnauthorizedException();
        }
    }

    public void attachCard(String customerCard, Integer customerId)
            throws InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException {
        if(!controlLogin())
            throw new UnauthorizedException();
        if(customerCard != null && customerCard.length() ==10) {
            if(customerId != null) {
                CustomerCard card = findByCode(customerCard);
                if(card == null)
                    throw new InvalidCustomerCardException();
                card.setCustomerId(customerId);
                CustomerCardDao.getInstance().save(card);
            }
            else
                throw new InvalidCustomerIdException();
        }
        else
            throw new InvalidCustomerCardException();
    }

    private boolean controlLogin() {
        return (AuthenticationManager.getInstance().getAuthUser() != null && (AuthenticationManager.getInstance()
                .getAuthUser().getRole().equals(RoleTypeEnum.Administrator.name())
                || AuthenticationManager.getInstance().getAuthUser().getRole().equals(RoleTypeEnum.ShopManager.name())
                || AuthenticationManager.getInstance().getAuthUser().getRole().equals(RoleTypeEnum.Cashier.name())));
    }

    public List<CustomerCard> getAllCard() {
        List<CustomerCard> cards = CustomerCardDao.getInstance().findAll();
        return new ArrayList<>(cards);
    }
}
