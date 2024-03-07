package it.polito.ezshop.dao;

import java.time.LocalDate;
import java.util.List;

import it.polito.ezshop.enums.BalanceOperationTypeEnum;
import it.polito.ezshop.model.BalanceOperation;
import it.polito.ezshop.util.HibernateConfiguration;

public class BalanceOperationDao extends BaseAbstractDao<BalanceOperation, Integer> {

	private static BalanceOperationDao instance = null;

	private BalanceOperationDao() {

	}

	public static BalanceOperationDao getInstance() {
		if (instance == null) {
			instance = new BalanceOperationDao();
		}
		return instance;
	}

	public List<BalanceOperation> findFromTo(LocalDate from, LocalDate to) {

		if (from == null || to == null) {
			return null;
		}

		session = HibernateConfiguration.getSessionFactory().openSession();

		try {

			List<BalanceOperation> list = session.createQuery(
					"from BalanceOperation where created_date >= :from and created_date <= :to order by created_date desc,balance_id desc")
					.setString("from", from.toString()).setString("to", to.toString()).list();

			session.close();
			return list;
		} catch (NullPointerException e) {
			return null;
		} catch (Exception e) {
			session.close();
			return null;
		}

	}

	public BalanceOperation getLastBalance() {

		BalanceOperation lastBalance = null;
		try {

			session = HibernateConfiguration.getSessionFactory().openSession();

			lastBalance = (BalanceOperation) session.createQuery(
					"from BalanceOperation where (type = :credit or type = :debit) order by created_date desc,balance_id desc")
					.setString("credit", BalanceOperationTypeEnum.CREDIT.name())
					.setString("debit", BalanceOperationTypeEnum.DEBIT.name()).list().get(0);

			session.close();
			return lastBalance;
		} catch (NullPointerException e) {
			return null;
		} catch (Exception e) {
			session.close();
			return null;
		}

	}

}
