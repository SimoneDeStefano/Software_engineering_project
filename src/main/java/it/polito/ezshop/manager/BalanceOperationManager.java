package it.polito.ezshop.manager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.polito.ezshop.dao.BalanceOperationDao;
import it.polito.ezshop.data.BalanceOperation;
import it.polito.ezshop.enums.BalanceOperationTypeEnum;
import it.polito.ezshop.enums.RoleTypeEnum;
import it.polito.ezshop.exceptions.UnauthorizedException;
import it.polito.ezshop.model.ReturnTransaction;

public class BalanceOperationManager {

	private static BalanceOperationManager instance;

	private BalanceOperationManager() {
	}

    public static BalanceOperationManager getInstance() {
		if (instance == null) {
			instance = new BalanceOperationManager();
		}
		return instance;
	}

	public List<BalanceOperation> getCreditsAndDebits(LocalDate from, LocalDate to) {

		List<it.polito.ezshop.model.BalanceOperation> list = it.polito.ezshop.dao.BalanceOperationDao.getInstance()
				.findFromTo(from, to);

		List<BalanceOperation> result = new ArrayList<BalanceOperation>();

		if (list != null) {

			for (it.polito.ezshop.model.BalanceOperation bo : list) {
				result.add(bo);
			}
		}

		return result;
	}

	/**
	 * This method record a balance update. <toBeAdded> can be both positive and
	 * nevative. If positive the balance entry should be recorded as CREDIT, if
	 * negative as DEBIT. The final balance after this operation should always be
	 * positive. It can be invoked only after a user with role "Administrator",
	 * "ShopManager" is logged in.
	 *
	 * @param toBeAdded the amount of money (positive or negative) to be added to
	 *                  the current balance. If this value is >= 0 than it should be
	 *                  considered as a CREDIT, if it is < 0 as a DEBIT
	 *
	 * @return true if the balance has been successfully updated false if toBeAdded
	 *         + currentBalance < 0.
	 * @throws UnauthorizedException if there is no logged user or if it has not the
	 *                               rights to perform the operation
	 */
	public boolean recordBalanceUpdate(double toBeAdded) throws UnauthorizedException {

		if (!(AuthenticationManager.getInstance().getAuthUser() != null && (AuthenticationManager.getInstance()
				.getAuthUser().getRole().equals(RoleTypeEnum.Administrator.name())
				|| AuthenticationManager.getInstance().getAuthUser().getRole()
						.equals(RoleTypeEnum.ShopManager.name())))) {

			throw new UnauthorizedException();
		}

		BalanceOperationTypeEnum type = toBeAdded > 0 ? BalanceOperationTypeEnum.CREDIT
				: BalanceOperationTypeEnum.DEBIT;

		BalanceOperation lastBalance = BalanceOperationDao.getInstance().getLastBalance();

		if ((lastBalance != null && lastBalance.getMoney() + toBeAdded > 0)
				|| (lastBalance == null && toBeAdded >= 0)) {

			it.polito.ezshop.model.BalanceOperation newBalance = new it.polito.ezshop.model.BalanceOperation();

			Double newMoney = (lastBalance != null ? (lastBalance.getMoney()) : 0d) + toBeAdded;
			newBalance.setDate(LocalDate.now());
			newBalance.setMoney(newMoney);
			newBalance.setType(type.name());

			BalanceOperationDao.getInstance().save(newBalance);

		} else {
			return false;
		}

		return true;
	}

}
