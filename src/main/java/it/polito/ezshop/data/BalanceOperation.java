package it.polito.ezshop.data;

import java.io.Serializable;
import java.time.LocalDate;

public interface BalanceOperation extends Serializable {

    int getBalanceId();

    void setBalanceId(int balanceId);

    LocalDate getDate();

    void setDate(LocalDate date);

    double getMoney();

    void setMoney(double money);

    String getType();

    void setType(String type);
}
