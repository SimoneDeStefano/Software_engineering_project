package it.polito.ezshop.manager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import it.polito.ezshop.dao.BalanceOperationDao;
import it.polito.ezshop.dao.OrderDao;
import it.polito.ezshop.dao.ProductTypeDao;
import it.polito.ezshop.enums.BalanceOperationTypeEnum;
import it.polito.ezshop.enums.OrderStatusEnum;
import it.polito.ezshop.enums.RoleTypeEnum;
import it.polito.ezshop.exceptions.InvalidLocationException;
import it.polito.ezshop.exceptions.InvalidOrderIdException;
import it.polito.ezshop.exceptions.InvalidPricePerUnitException;
import it.polito.ezshop.exceptions.InvalidProductCodeException;
import it.polito.ezshop.exceptions.InvalidQuantityException;
import it.polito.ezshop.exceptions.UnauthorizedException;
import it.polito.ezshop.model.BalanceOperation;
import it.polito.ezshop.model.Order;
import it.polito.ezshop.model.ProductType;
import it.polito.ezshop.util.HibernateConfiguration;

public class OrderManager {

	private static OrderManager instance;

	private OrderManager() {

	}

	public static OrderManager getInstance() {
		if (instance == null) {
			instance = new OrderManager();
		}
		return instance;
	}

	public Integer issueOrder(String productCode, int quantity, double pricePerUnit) throws InvalidProductCodeException,
			InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException {

		if (!(AuthenticationManager.getInstance().getAuthUser() != null && (AuthenticationManager.getInstance()
				.getAuthUser().getRole().equals(RoleTypeEnum.Administrator.name())
				|| AuthenticationManager.getInstance().getAuthUser().getRole()
						.equals(RoleTypeEnum.ShopManager.name())))) {

			throw new UnauthorizedException();
		}

		if (productCode == null || productCode.isEmpty()
				|| !ProductTypeManager.getInstance().checkProductCode(productCode)) {
			throw new InvalidProductCodeException();
		}

		if (quantity <= 0) {
			throw new InvalidQuantityException();
		}

		if (pricePerUnit <= 0d) {
			throw new InvalidPricePerUnitException();
		}

		Order order = new Order();
		order.setPricePerUnit(pricePerUnit);
		order.setProductCode(productCode);
		order.setQuantity(quantity);
		order.setStatus(OrderStatusEnum.ISSUED.name());

		try {

			order = OrderDao.getInstance().save(order);
		} catch (Exception e) {
			return -1;
		}

		return order.getOrderId();

	}

	public Integer payOrderFor(String productCode, int quantity, double pricePerUnit)
			throws InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException,
			UnauthorizedException {

		if (!(AuthenticationManager.getInstance().getAuthUser() != null && (AuthenticationManager.getInstance()
				.getAuthUser().getRole().equals(RoleTypeEnum.Administrator.name())
				|| AuthenticationManager.getInstance().getAuthUser().getRole()
						.equals(RoleTypeEnum.ShopManager.name())))) {

			throw new UnauthorizedException();
		}

		if (productCode == null || productCode.isEmpty()
				|| !ProductTypeManager.getInstance().checkProductCode(productCode)) {
			throw new InvalidProductCodeException();
		}

		if (quantity <= 0) {
			throw new InvalidQuantityException();
		}

		if (pricePerUnit <= 0d) {
			throw new InvalidPricePerUnitException();
		}

		Session session = null;
		Order order = null;
		try {

			session = HibernateConfiguration.getSessionFactory().openSession();

			session.beginTransaction();

			BalanceOperation operation = new BalanceOperation();
			operation.setDate(LocalDate.now());
			operation.setMoney(pricePerUnit * quantity);
			operation.setType(BalanceOperationTypeEnum.ORDER.name());

			BalanceOperationDao.getInstance().appendSave(operation, session);

			order = new Order();
			order.setPricePerUnit(pricePerUnit);
			order.setProductCode(productCode);
			order.setQuantity(quantity);
			order.setStatus(OrderStatusEnum.PAYED.name());

			order = OrderDao.getInstance().appendSave(order, session);

			session.getTransaction().commit();
		} catch (Exception e) {
			if (session != null) {

				session.getTransaction().rollback();
			}
			return -1;
		}

		return order.getOrderId();
	}

	public boolean payOrder(Integer orderId) throws InvalidOrderIdException, UnauthorizedException {

		if (!(AuthenticationManager.getInstance().getAuthUser() != null && (AuthenticationManager.getInstance()
				.getAuthUser().getRole().equals(RoleTypeEnum.Administrator.name())
				|| AuthenticationManager.getInstance().getAuthUser().getRole()
						.equals(RoleTypeEnum.ShopManager.name())))) {

			throw new UnauthorizedException();
		}

		if (orderId == null || orderId <= 0) {
			throw new InvalidOrderIdException();
		}

		Order order = null;
		Session session = null;

		try {

			order = OrderDao.getInstance().findById(orderId);
		} catch (Exception e) {
			return false;
		}

		try {

			session = HibernateConfiguration.getSessionFactory().openSession();

			if (order == null || (!(OrderStatusEnum.PAYED.name().equals(order.getStatus()))
					&& !(OrderStatusEnum.ISSUED.name().equals(order.getStatus()))
					&& !(OrderStatusEnum.ORDERED.name().equals(order.getStatus()))))

			{
				return false;
			}

			BalanceOperation operation = new BalanceOperation();
			operation.setDate(LocalDate.now());
			operation.setMoney(order.getPricePerUnit() * order.getQuantity());
			operation.setType(BalanceOperationTypeEnum.ORDER.name());

			session.beginTransaction();

			order.setStatus(OrderStatusEnum.PAYED.name());

			OrderDao.getInstance().appendSave(order, session);
			BalanceOperationDao.getInstance().appendSave(operation, session);

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			session.getTransaction().rollback();
			session.close();
			return false;
		}

		return true;
	}

	public boolean recordOrderArrival(Integer orderId)
			throws InvalidOrderIdException, UnauthorizedException, InvalidLocationException {

		if (!(AuthenticationManager.getInstance().getAuthUser() != null && (AuthenticationManager.getInstance()
				.getAuthUser().getRole().equals(RoleTypeEnum.Administrator.name())
				|| AuthenticationManager.getInstance().getAuthUser().getRole()
						.equals(RoleTypeEnum.ShopManager.name())))) {

			throw new UnauthorizedException();
		}

		if (orderId == null || orderId <= 0) {
			throw new InvalidOrderIdException();
		}

		Order order = OrderDao.getInstance().findById(orderId);

		try {

			Session session = HibernateConfiguration.getSessionFactory().openSession();

			session.beginTransaction();

			if (order == null || !(OrderStatusEnum.COMPLETED.name().equals(order.getStatus())
					|| OrderStatusEnum.PAYED.name().equals(order.getStatus())
					|| OrderStatusEnum.ORDERED.name().equals(order.getStatus()))) {
				return false;
			}

			if (order.getProductType() != null && order.getProductType().getLocation() != null) {

				order.setStatus(OrderStatusEnum.COMPLETED.name());
			} else {
				return false;
			}

			ProductType product = order.getProductType();

			product.setQuantity(product.getQuantity() + order.getQuantity());
			OrderDao.getInstance().appendSave(order, session);
			ProductTypeDao.getInstance().appendSave(product, session);

			session.getTransaction().commit();

			session.close();
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	/**
	 * This method return the list of all orders ISSUED, ORDERED and COMLPETED. It
	 * can be invoked only after a user with role "Administrator" or "ShopManager"
	 * is logged in.
	 *
	 * @return a list containing all orders
	 *
	 * @throws UnauthorizedException if there is no logged user or if it has not the
	 *                               rights to perform the operation
	 */
	public List<it.polito.ezshop.data.Order> getAllOrders() throws UnauthorizedException {
		if (!(AuthenticationManager.getInstance().getAuthUser() != null && (AuthenticationManager.getInstance()
				.getAuthUser().getRole().equals(RoleTypeEnum.Administrator.name())
				|| AuthenticationManager.getInstance().getAuthUser().getRole()
						.equals(RoleTypeEnum.ShopManager.name())))) {

			throw new UnauthorizedException();
		}

		List<Order> orders = OrderDao.getInstance().findAll();
		List<it.polito.ezshop.data.Order> result = new ArrayList<it.polito.ezshop.data.Order>();

		for (Order o : orders) {
			result.add(o);
		}

		return result;
	}

}
