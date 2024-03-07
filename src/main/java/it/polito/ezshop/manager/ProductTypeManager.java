package it.polito.ezshop.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import it.polito.ezshop.dao.ProductTypeDao;
import it.polito.ezshop.enums.RoleTypeEnum;
import it.polito.ezshop.exceptions.InvalidLocationException;
import it.polito.ezshop.exceptions.InvalidPricePerUnitException;
import it.polito.ezshop.exceptions.InvalidProductCodeException;
import it.polito.ezshop.exceptions.InvalidProductDescriptionException;
import it.polito.ezshop.exceptions.InvalidProductIdException;
import it.polito.ezshop.exceptions.UnauthorizedException;
import it.polito.ezshop.model.ProductType;
import org.hibernate.Session;

public class ProductTypeManager {

	private final static List<Integer> GTIN12 = Arrays.asList(3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3);
	private final static List<Integer> GTIN13 = Arrays.asList(1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3);
	private final static List<Integer> GTIN14 = Arrays.asList(3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3);

	private static ProductTypeManager instance;

	private ProductTypeManager() {

	}

	public static ProductTypeManager getInstance() {
		if (instance == null) {
			instance = new ProductTypeManager();
		}
		return instance;
	}

	public Integer createProductType(String description, String productCode, double pricePerUnit, String note)
			throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException,
			UnauthorizedException {

		ProductType productType = new ProductType();

		if (AuthenticationManager.getInstance().getAuthUser() != null && (AuthenticationManager.getInstance()
				.getAuthUser().getRole().equals(RoleTypeEnum.Administrator.name())
				|| AuthenticationManager.getInstance().getAuthUser().getRole()
						.equals(RoleTypeEnum.ShopManager.name()))) {

			productType.setNote(note != null ? note : "");

			if (description != null && !description.isEmpty()) {
				productType.setProductDescription(description);
			} else {
				throw new InvalidProductDescriptionException();
			}
			if (pricePerUnit > 0 && pricePerUnit < Double.MAX_VALUE && pricePerUnit > Double.MIN_VALUE) {
				productType.setPricePerUnit(pricePerUnit);
			} else {
				throw new InvalidPricePerUnitException();
			}

			if (checkProductCode(productCode)) {

				if (ProductTypeDao.getInstance().findByBarCode(productCode) != null) {
					throw new InvalidProductCodeException();
				}
				productType.setBarCode(productCode);
			} else {
				throw new InvalidProductCodeException();
			}
			productType.setQuantity(0);
			productType = ProductTypeDao.getInstance().save(productType);

		} else {
			throw new UnauthorizedException();
		}
		return productType.getId();
	}

	public boolean updateProduct(Integer id, String newDescription, String newCode, double newPrice, String newNote)
			throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
			InvalidPricePerUnitException, UnauthorizedException {

		ProductType product = null;

		if (id == null || id <= 0 || id == Integer.MAX_VALUE) {
			throw new InvalidProductIdException();
		}
		if (newDescription == null || newDescription.isEmpty()) {
			throw new InvalidProductDescriptionException();
		}
		if (newCode == null || !checkProductCode(newCode)) {
			throw new InvalidProductCodeException();
		}
		if (newPrice <= 0d || newPrice == Double.MIN_VALUE || newPrice == Double.MAX_VALUE) {
			throw new InvalidPricePerUnitException();
		}
		if (!(AuthenticationManager.getInstance().getAuthUser() != null && (AuthenticationManager.getInstance()
				.getAuthUser().getRole().equals(RoleTypeEnum.Administrator.name())
				|| AuthenticationManager.getInstance().getAuthUser().getRole()
						.equals(RoleTypeEnum.ShopManager.name())))) {
			throw new UnauthorizedException();
		}

		ProductType currentProduct = ProductTypeDao.getInstance().findById(id);

		ProductType existingProduct = ProductTypeDao.getInstance().findByBarCode(newCode);

		if (currentProduct == null
				|| (existingProduct != null && !existingProduct.getBarCode().equals(currentProduct.getBarCode()))) {
			return false;
		}

		product.setProductDescription(newDescription);
		product.setBarCode(newCode);
		product.setPricePerUnit(newPrice);
		product.setNote(newNote);

		ProductTypeDao.getInstance().save(product);

		return true;
	}

	public boolean checkProductCode(String barcode) {

		if (barcode == null || barcode.isEmpty() || !(barcode.length() >= 12 && barcode.length() <= 14)) {
			return false;
		}

		try {
			Long.parseLong(barcode);
		} catch (NumberFormatException e) {
			return false;
		}

		Integer sum = 0;
		char[] digits = barcode.toCharArray();

		List<Integer> checkArray = null;

		switch (barcode.length()) {
			case 12:
				checkArray = GTIN12;
				break;
			case 13:
				checkArray = GTIN13;
				break;
			case 14:
				checkArray = GTIN14;
				break;
			default:
				return false;
		}

		for (int i = 0; i < checkArray.size(); i++) {
			sum += new Integer(new Character(digits[i]).toString()) * checkArray.get(i);
		}

		Integer checkDigit = (10 - sum % 10);

		Integer lastDigit = new Integer(new Character(digits[digits.length - 1]).toString());

		return (checkDigit - lastDigit) == 0;
	}

	public boolean deleteProductType(Integer id) throws InvalidProductIdException, UnauthorizedException {

		if (!(AuthenticationManager.getInstance().getAuthUser() != null && (AuthenticationManager.getInstance()
				.getAuthUser().getRole().equals(RoleTypeEnum.Administrator.name())
				|| AuthenticationManager.getInstance().getAuthUser().getRole()
						.equals(RoleTypeEnum.ShopManager.name())))) {

			throw new UnauthorizedException();
		}
		if (id == null || id <= 0) {
			throw new InvalidProductIdException();
		}
		ProductType deletingProduct = ProductTypeDao.getInstance().findById(id);

		if (deletingProduct == null) {
			return false;
		}

		return ProductTypeDao.getInstance().delete(deletingProduct);

	}

	public List<it.polito.ezshop.data.ProductType> getAllProductTypes() throws UnauthorizedException {

		List<it.polito.ezshop.data.ProductType> list = new ArrayList<it.polito.ezshop.data.ProductType>();

		List<ProductType> result = ProductTypeDao.getInstance().findAll();

		for (ProductType pt : result) {
			list.add(pt);
		}

		return list;
	}

	public ProductType getProductTypeByBarCode(String barCode)
			throws InvalidProductCodeException, UnauthorizedException {

		if (!checkProductCode(barCode)) {

			throw new InvalidProductCodeException();
		}
		if (!(AuthenticationManager.getInstance().getAuthUser() != null && (AuthenticationManager.getInstance()
				.getAuthUser().getRole().equals(RoleTypeEnum.Administrator.name())
				|| AuthenticationManager.getInstance().getAuthUser().getRole()
						.equals(RoleTypeEnum.ShopManager.name())))) {

			throw new UnauthorizedException();
		}

		return ProductTypeDao.getInstance().findByBarCode(barCode);
	}

	public ProductType getProductTypeByBarCode(String barCode, Session session)
			throws InvalidProductCodeException, UnauthorizedException {

		if (!checkProductCode(barCode)) {

			throw new InvalidProductCodeException();
		}
		if (!(AuthenticationManager.getInstance().getAuthUser() != null && (AuthenticationManager.getInstance()
				.getAuthUser().getRole().equals(RoleTypeEnum.Administrator.name())
				|| AuthenticationManager.getInstance().getAuthUser().getRole()
						.equals(RoleTypeEnum.ShopManager.name())))) {

			throw new UnauthorizedException();
		}

		return ProductTypeDao.getInstance().findByBarCode(barCode);
	}

	public List<it.polito.ezshop.data.ProductType> getProductTypesByDescription(String description)
			throws UnauthorizedException {

		List<it.polito.ezshop.data.ProductType> list = new ArrayList<it.polito.ezshop.data.ProductType>();
		List<ProductType> result = ProductTypeDao.getInstance().findByDescription(description);

		for (ProductType pt : result) {
			list.add(pt);
		}

		return list;

	}

	public boolean updateQuantity(Integer productId, int toBeAdded)
			throws InvalidProductIdException, UnauthorizedException {

		if (!(AuthenticationManager.getInstance().getAuthUser() != null && (AuthenticationManager.getInstance()
				.getAuthUser().getRole().equals(RoleTypeEnum.Administrator.name())
				|| AuthenticationManager.getInstance().getAuthUser().getRole()
						.equals(RoleTypeEnum.ShopManager.name())))) {

			throw new UnauthorizedException();
		}

		ProductType product = null;

		if (productId == null || productId <= 0
				|| (product = ProductTypeDao.getInstance().findById(productId)) == null) {
			throw new InvalidProductIdException();
		}

		if (product.getQuantity() + toBeAdded < 0 || product.getLocation() == null || product.getLocation().isEmpty()) {
			return false;
		}

		product.setQuantity(product.getQuantity() + toBeAdded);
		ProductTypeDao.getInstance().save(product);

		return true;
	}

	public boolean updatePosition(Integer productId, String newPos)
			throws InvalidProductIdException, InvalidLocationException, UnauthorizedException {
		if (!(AuthenticationManager.getInstance().getAuthUser() != null && (AuthenticationManager.getInstance()
				.getAuthUser().getRole().equals(RoleTypeEnum.Administrator.name())
				|| AuthenticationManager.getInstance().getAuthUser().getRole()
						.equals(RoleTypeEnum.ShopManager.name())))) {

			throw new UnauthorizedException();
		}

		ProductType product = null;

		if (productId == null || productId <= 0) {
			throw new InvalidProductIdException();
		}

		if (newPos == null || newPos.isEmpty() || newPos.split("-").length < 3) {
			throw new InvalidLocationException();
		}

		if ((product = ProductTypeDao.getInstance().findById(productId)) == null) {
			return false;
		}

		List<ProductType> productByPos = ProductTypeDao.getInstance().findByCriteria(new HashMap<String, Object>() {
			{
				put("location", newPos);
			}
		});

		if (productByPos != null && !productByPos.isEmpty()) {
			return false;
		}

		product.setLocation(newPos);
		ProductTypeDao.getInstance().save(product);

		return true;

	}

}
