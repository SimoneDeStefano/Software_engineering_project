package it.polito.ezshop.acceptanceTests;

import it.polito.ezshop.dao.*;
import it.polito.ezshop.enums.*;
import it.polito.ezshop.exceptions.*;
import it.polito.ezshop.manager.*;
import it.polito.ezshop.model.ProductType;
import it.polito.ezshop.model.SaleTransaction;
import it.polito.ezshop.model.User;
import org.junit.*;

import org.junit.rules.ExpectedException;
import it.polito.ezshop.dao.ProductTypeDao;
import it.polito.ezshop.dao.UserDao;
import it.polito.ezshop.enums.RoleTypeEnum;
import it.polito.ezshop.manager.AuthenticationManager;
import it.polito.ezshop.manager.ProductTypeManager;
import it.polito.ezshop.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;

public class TestEZShop {

    public static class TestProductTypeManager {

        private List<String> VALID_BARCODE_13;
        private List<String> NOT_VALID_BARCODE_13;
        private List<String> VALID_BARCODE_12;
        private List<String> NOT_VALID_BARCODE_12;
        private List<String> VALID_BARCODE_14;
        private List<String> NOT_VALID_BARCODE_14;

        @Before
        public void createUser() {
            User user = new User();
            user.setUsername("admin");
            user.setPassword("admin");
            user.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(user);
        }

        public TestProductTypeManager() {

            VALID_BARCODE_13 = Arrays.asList("1111111111116", "1111111111123");
            NOT_VALID_BARCODE_13 = Arrays.asList("1111111111115", "11111111111a5");
            VALID_BARCODE_12 = Arrays.asList("111111111117");
            NOT_VALID_BARCODE_12 = Arrays.asList("111111111116", "11111111111a");
            VALID_BARCODE_14 = Arrays.asList("11111111111113");
            NOT_VALID_BARCODE_14 = Arrays.asList("11111111111112", "1111111111111a");

        }

        /**
         * invalid value for description, expected InvalidProductDescriptionException
         *
         * @throws InvalidProductDescriptionException
         * @throws InvalidProductCodeException
         * @throws InvalidPricePerUnitException
         * @throws UnauthorizedException
         * @throws InvalidUsernameException
         * @throws InvalidPasswordException
         */
        @Test(expected = InvalidProductDescriptionException.class)
        public void test_8_createProductType()
                throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException,
                UnauthorizedException, InvalidUsernameException, InvalidPasswordException {

            AuthenticationManager.getInstance().login("admin", "admin");

            String description = "";
            String productCode = VALID_BARCODE_13.get(0);
            double pricePerUnit = 1.0d;
            String note = "asdasd";

            ProductTypeManager.getInstance().createProductType(description, productCode, pricePerUnit, note);

        }

        @Test(expected = InvalidProductDescriptionException.class)
        public void test_7_createProductType()
                throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException,
                UnauthorizedException, InvalidUsernameException, InvalidPasswordException {

            AuthenticationManager.getInstance().login("admin", "admin");

            String description = null;
            String productCode = VALID_BARCODE_13.get(0);
            double pricePerUnit = 1.0d;
            String note = "asdasd";

            ProductTypeManager.getInstance().createProductType(description, productCode, pricePerUnit, note);

        }

        /**
         * invalid value for product code ( empty string), expected
         * InvalidProductCodeException
         * 
         * @throws InvalidProductDescriptionException
         * @throws InvalidProductCodeException
         * @throws InvalidPricePerUnitException
         * @throws UnauthorizedException
         * @throws InvalidUsernameException
         * @throws InvalidPasswordException
         */
        @Test(expected = InvalidProductCodeException.class)
        public void test_2_createProductType()
                throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException,
                UnauthorizedException, InvalidUsernameException, InvalidPasswordException {

            AuthenticationManager.getInstance().login("admin", "admin");

            String description = "asd";
            String productCode = "";
            double pricePerUnit = 1.0d;
            String note = "asdasd";

            ProductTypeManager.getInstance().createProductType(description, productCode, pricePerUnit, note);

        }

        /**
         * invalid value for product code (bad bar code), expected
         * InvalidProductCodeException
         * 
         * @throws InvalidProductDescriptionException
         * @throws InvalidProductCodeException
         * @throws InvalidPricePerUnitException
         * @throws UnauthorizedException
         * @throws InvalidUsernameException
         * @throws InvalidPasswordException
         */
        @Test(expected = InvalidProductCodeException.class)
        public void test_5_createProductType()
                throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException,
                UnauthorizedException, InvalidUsernameException, InvalidPasswordException {

            AuthenticationManager.getInstance().login("admin", "admin");

            String description = "asd";
            String productCode = NOT_VALID_BARCODE_13.get(0);
            double pricePerUnit = 1.0d;
            String note = "asdasd";

            ProductTypeManager.getInstance().createProductType(description, productCode, pricePerUnit, note);

        }

        /**
         * invalid value for product code (null), expected InvalidProductCodeException
         * 
         * @throws InvalidProductDescriptionException
         * @throws InvalidProductCodeException
         * @throws InvalidPricePerUnitException
         * @throws UnauthorizedException
         * @throws InvalidUsernameException
         * @throws InvalidPasswordException
         */
        @Test(expected = InvalidProductCodeException.class)
        public void test_1_createProductType()
                throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException,
                UnauthorizedException, InvalidUsernameException, InvalidPasswordException {

            AuthenticationManager.getInstance().login("admin", "admin");

            String description = "asd";
            String productCode = null;
            double pricePerUnit = 1.0d;
            String note = "asdasd";

            ProductTypeManager.getInstance().createProductType(description, productCode, pricePerUnit, note);

        }

        /**
         * invalid value for product code (< 12), expected InvalidProductCodeException
         * 
         * @throws InvalidProductDescriptionException
         * @throws InvalidProductCodeException
         * @throws InvalidPricePerUnitException
         * @throws UnauthorizedException
         * @throws InvalidUsernameException
         * @throws InvalidPasswordException
         */
        @Test(expected = InvalidProductCodeException.class)
        public void test_3_createProductType()
                throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException,
                UnauthorizedException, InvalidUsernameException, InvalidPasswordException {

            AuthenticationManager.getInstance().login("admin", "admin");

            String description = "asd";
            String productCode = VALID_BARCODE_12.get(0).substring(0, VALID_BARCODE_12.get(0).length() - 1);
            double pricePerUnit = 1.0d;
            String note = "asdasd";

            ProductTypeManager.getInstance().createProductType(description, productCode, pricePerUnit, note);

        }

        /**
         * invalid value for product code ( > 14) , expected InvalidProductCodeException
         * 
         * @throws InvalidProductDescriptionException
         * @throws InvalidProductCodeException
         * @throws InvalidPricePerUnitException
         * @throws UnauthorizedException
         * @throws InvalidUsernameException
         * @throws InvalidPasswordException
         */
        @Test(expected = InvalidProductCodeException.class)
        public void test_4_createProductType()
                throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException,
                UnauthorizedException, InvalidUsernameException, InvalidPasswordException {

            AuthenticationManager.getInstance().login("admin", "admin");

            String description = "asd";
            String productCode = VALID_BARCODE_14.get(0) + "1";
            double pricePerUnit = 1.0d;
            String note = "asdasd";

            ProductTypeManager.getInstance().createProductType(description, productCode, pricePerUnit, note);

        }

        @Test(expected = InvalidProductCodeException.class)
        public void test_6_createProductType()
                throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException,
                UnauthorizedException, InvalidUsernameException, InvalidPasswordException {

            AuthenticationManager.getInstance().login("admin", "admin");

            String description = "asd";
            String productCode = VALID_BARCODE_14.get(0) + "1";
            double pricePerUnit = 1.0d;
            String note = "asdasd";

            ProductTypeManager.getInstance().createProductType(description, "1231231231231a", pricePerUnit, note);

        }

        /**
         * invalid value for price per unit(0), expected InvalidPricePerUnitException
         * 
         * @throws InvalidProductDescriptionException
         * @throws InvalidProductCodeException
         * @throws InvalidPricePerUnitException
         * @throws UnauthorizedException
         * @throws InvalidUsernameException
         * @throws InvalidPasswordException
         */
        @Test(expected = InvalidPricePerUnitException.class)
        public void test_9_createProductType()
                throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException,
                UnauthorizedException, InvalidUsernameException, InvalidPasswordException {

            AuthenticationManager.getInstance().login("admin", "admin");

            String description = "asd";
            String productCode = VALID_BARCODE_13.get(0);
            double pricePerUnit = 0d;
            String note = "asdasd";

            ProductTypeManager.getInstance().createProductType(description, productCode, pricePerUnit, note);

        }

        /**
         * valid value, expect product type with input value saved correctly
         * 
         * @throws InvalidProductDescriptionException
         * @throws InvalidProductCodeException
         * @throws InvalidPricePerUnitException
         * @throws UnauthorizedException
         * @throws InvalidUsernameException
         * @throws InvalidPasswordException
         */
        @Test
        public void test_10_createProductType()
                throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException,
                UnauthorizedException, InvalidUsernameException, InvalidPasswordException {

            AuthenticationManager.getInstance().login("admin", "admin");

            String description = "asd";
            String productCode = VALID_BARCODE_13.get(0);
            Double pricePerUnit = 12.0d;
            String note = "asdasd";

            Integer id = ProductTypeManager.getInstance().createProductType(description, productCode, pricePerUnit,
                    note);

            ProductType test = ProductTypeDao.getInstance().findById(id);

            ProductTypeDao.getInstance().delete(test);

            Assert.assertNotNull(test);
            Assert.assertEquals(description, test.getProductDescription());
            Assert.assertEquals(productCode, test.getBarCode());
            Assert.assertEquals(pricePerUnit, test.getPricePerUnit());
            Assert.assertEquals(note, test.getNote());

        }

        /**
         * invalid value for product id ( < 0), expected InvalidProductIdException
         * 
         * @throws InvalidPasswordException
         * @throws InvalidUsernameException
         * @throws UnauthorizedException
         * @throws InvalidProductDescriptionException
         * @throws InvalidPricePerUnitException
         * @throws InvalidProductIdException
         * @throws InvalidProductCodeException
         */
        @Test(expected = InvalidProductIdException.class)
        public void test_1_updateProduct() throws InvalidPasswordException, InvalidUsernameException,
                UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException,
                InvalidProductIdException, InvalidProductCodeException {

            AuthenticationManager.getInstance().login("admin", "admin");

            Integer productId = -1;
            String description = "asd";
            String productCode = VALID_BARCODE_13.get(0);
            Double pricePerUnit = 12.0d;
            String note = "asd";

            ProductTypeManager.getInstance().updateProduct(productId, description, productCode, pricePerUnit, note);

        }

        /**
         * invalid value for product id ( 0), expected InvalidProductIdException
         * 
         * @throws InvalidPasswordException
         * @throws InvalidUsernameException
         * @throws UnauthorizedException
         * @throws InvalidProductDescriptionException
         * @throws InvalidPricePerUnitException
         * @throws InvalidProductIdException
         * @throws InvalidProductCodeException
         */
        @Test(expected = InvalidProductIdException.class)
        public void test_2_updateProduct() throws InvalidPasswordException, InvalidUsernameException,
                UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException,
                InvalidProductIdException, InvalidProductCodeException {

            AuthenticationManager.getInstance().login("admin", "admin");

            Integer productId = 0;
            String description = "asd";
            String productCode = VALID_BARCODE_13.get(0);
            Double pricePerUnit = 12.0d;
            String note = "asd";

            ProductTypeManager.getInstance().updateProduct(productId, description, productCode, pricePerUnit, note);

        }

        /**
         * invalid value for product id ( null ), expected InvalidProductIdException
         * 
         * @throws InvalidPasswordException
         * @throws InvalidUsernameException
         * @throws UnauthorizedException
         * @throws InvalidProductDescriptionException
         * @throws InvalidPricePerUnitException
         * @throws InvalidProductIdException
         * @throws InvalidProductCodeException
         */
        @Test(expected = InvalidProductIdException.class)
        public void test_3_updateProduct() throws InvalidPasswordException, InvalidUsernameException,
                UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException,
                InvalidProductIdException, InvalidProductCodeException {

            AuthenticationManager.getInstance().login("admin", "admin");

            Integer productId = null;
            String description = "asd";
            String productCode = VALID_BARCODE_13.get(0);
            Double pricePerUnit = 12.0d;
            String note = "asd";

            ProductTypeManager.getInstance().updateProduct(productId, description, productCode, pricePerUnit, note);

        }

        /**
         * invalid value for product id ( Integer.MAX_VALUE ), expected
         * InvalidProductIdException
         * 
         * @throws InvalidPasswordException
         * @throws InvalidUsernameException
         * @throws UnauthorizedException
         * @throws InvalidProductDescriptionException
         * @throws InvalidPricePerUnitException
         * @throws InvalidProductIdException
         * @throws InvalidProductCodeException
         */
        @Test(expected = InvalidProductIdException.class)
        public void test_4_updateProduct() throws InvalidPasswordException, InvalidUsernameException,
                UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException,
                InvalidProductIdException, InvalidProductCodeException {

            AuthenticationManager.getInstance().login("admin", "admin");

            Integer productId = Integer.MAX_VALUE;
            String description = "asd";
            String productCode = VALID_BARCODE_13.get(0);
            Double pricePerUnit = 12.0d;
            String note = "asd";

            ProductTypeManager.getInstance().updateProduct(productId, description, productCode, pricePerUnit, note);

        }

        /**
         * invalid value for new description ( null ), expected
         * InvalidProductIdException
         * 
         * @throws InvalidPasswordException
         * @throws InvalidUsernameException
         * @throws UnauthorizedException
         * @throws InvalidProductDescriptionException
         * @throws InvalidPricePerUnitException
         * @throws InvalidProductIdException
         * @throws InvalidProductCodeException
         */
        @Test(expected = InvalidProductDescriptionException.class)
        public void test_5_updateProduct() throws InvalidPasswordException, InvalidUsernameException,
                UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException,
                InvalidProductIdException, InvalidProductCodeException {

            String description = null;
            String productCode = VALID_BARCODE_13.get(0);
            Double pricePerUnit = 12.0d;
            String note = "asd";

            AuthenticationManager.getInstance().login("admin", "admin");

            ProductType test = new ProductType();
            test.setProductDescription(description);
            test.setPricePerUnit(pricePerUnit);
            test.setNote(note);
            ProductTypeDao.getInstance().save(test);

            ProductTypeDao.getInstance().delete(test);

            ProductTypeManager.getInstance().updateProduct(test.getId(), description, productCode, pricePerUnit, note);

        }

        /**
         * invalid value for new description ( empty string ), expected
         * InvalidProductIdException
         * 
         * @throws InvalidPasswordException
         * @throws InvalidUsernameException
         * @throws UnauthorizedException
         * @throws InvalidProductDescriptionException
         * @throws InvalidPricePerUnitException
         * @throws InvalidProductIdException
         * @throws InvalidProductCodeException
         */
        @Test(expected = InvalidProductDescriptionException.class)
        public void test_6_updateProduct() throws InvalidPasswordException, InvalidUsernameException,
                UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException,
                InvalidProductIdException, InvalidProductCodeException {

            String description = "";
            String productCode = VALID_BARCODE_13.get(0);
            Double pricePerUnit = 12.0d;
            String note = "asd";

            AuthenticationManager.getInstance().login("admin", "admin");

            ProductType test = new ProductType();
            test.setProductDescription(description);
            test.setPricePerUnit(pricePerUnit);
            test.setNote(note);
            ProductTypeDao.getInstance().save(test);

            ProductTypeDao.getInstance().delete(test);

            ProductTypeManager.getInstance().updateProduct(test.getId(), description, productCode, pricePerUnit, note);

        }

        /**
         * invalid value for product code ( null ), expected InvalidProductCodeException
         * 
         * @throws InvalidPasswordException
         * @throws InvalidUsernameException
         * @throws UnauthorizedException
         * @throws InvalidProductDescriptionException
         * @throws InvalidPricePerUnitException
         * @throws InvalidProductIdException
         * @throws InvalidProductCodeException
         */
        @Test(expected = InvalidProductCodeException.class)
        public void test_7_updateProduct() throws InvalidPasswordException, InvalidUsernameException,
                UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException,
                InvalidProductIdException, InvalidProductCodeException {

            String description = "asd";
            String productCode = null;
            Double pricePerUnit = 12.0d;
            String note = "asd";

            AuthenticationManager.getInstance().login("admin", "admin");

            ProductType test = new ProductType();
            test.setProductDescription(description);
            test.setPricePerUnit(pricePerUnit);
            test.setNote(note);
            ProductTypeDao.getInstance().save(test);

            ProductTypeDao.getInstance().delete(test);

            ProductTypeManager.getInstance().updateProduct(test.getId(), description, productCode, pricePerUnit, note);

        }

        /**
         * invalid value for product code ( empty string ), expected
         * InvalidProductCodeException
         * 
         * @throws InvalidPasswordException
         * @throws InvalidUsernameException
         * @throws UnauthorizedException
         * @throws InvalidProductDescriptionException
         * @throws InvalidPricePerUnitException
         * @throws InvalidProductIdException
         * @throws InvalidProductCodeException
         */
        @Test(expected = InvalidProductCodeException.class)
        public void test_8_updateProduct() throws InvalidPasswordException, InvalidUsernameException,
                UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException,
                InvalidProductIdException, InvalidProductCodeException {

            Integer productId = null;
            String description = "asd";
            String productCode = "";
            Double pricePerUnit = 12.0d;
            String note = "asd";

            AuthenticationManager.getInstance().login("admin", "admin");

            ProductType test = new ProductType();
            test.setProductDescription(description);
            test.setPricePerUnit(pricePerUnit);
            test.setNote(note);
            ProductTypeDao.getInstance().save(test);

            ProductTypeDao.getInstance().delete(test);

            ProductTypeManager.getInstance().updateProduct(test.getId(), description, productCode, pricePerUnit, note);

        }

        /**
         * invalid value for product code ( not number ), expected
         * InvalidProductCodeException
         * 
         * @throws InvalidPasswordException
         * @throws InvalidUsernameException
         * @throws UnauthorizedException
         * @throws InvalidProductDescriptionException
         * @throws InvalidPricePerUnitException
         * @throws InvalidProductIdException
         * @throws InvalidProductCodeException
         */
        @Test(expected = InvalidProductCodeException.class)
        public void test_9_updateProduct() throws InvalidPasswordException, InvalidUsernameException,
                UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException,
                InvalidProductIdException, InvalidProductCodeException {

            Integer productId = null;
            String description = "asd";
            String productCode = NOT_VALID_BARCODE_13.get(1);
            Double pricePerUnit = 12.0d;
            String note = "asd";

            AuthenticationManager.getInstance().login("admin", "admin");

            ProductType test = new ProductType();
            test.setProductDescription(description);
            test.setPricePerUnit(pricePerUnit);
            test.setNote(note);
            ProductTypeDao.getInstance().save(test);

            ProductTypeDao.getInstance().delete(test);

            ProductTypeManager.getInstance().updateProduct(test.getId(), description, productCode, pricePerUnit, note);

        }

        /**
         * invalid value for product code ( not valid barcode - 13 ), expected
         * InvalidProductCodeException
         * 
         * @throws InvalidPasswordException
         * @throws InvalidUsernameException
         * @throws UnauthorizedException
         * @throws InvalidProductDescriptionException
         * @throws InvalidPricePerUnitException
         * @throws InvalidProductIdException
         * @throws InvalidProductCodeException
         */
        @Test(expected = InvalidProductCodeException.class)
        public void test_10_updateProduct() throws InvalidPasswordException, InvalidUsernameException,
                UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException,
                InvalidProductIdException, InvalidProductCodeException {

            Integer productId = null;
            String description = "asd";
            String productCode = NOT_VALID_BARCODE_13.get(0);
            Double pricePerUnit = 12.0d;
            String note = "asd";

            AuthenticationManager.getInstance().login("admin", "admin");

            ProductType test = new ProductType();
            test.setProductDescription(description);
            test.setPricePerUnit(pricePerUnit);
            test.setNote(note);
            ProductTypeDao.getInstance().save(test);

            ProductTypeDao.getInstance().delete(test);

            ProductTypeManager.getInstance().updateProduct(test.getId(), description, productCode, pricePerUnit, note);

        }

        /**
         * invalid value for product code ( not valid barcode - 12 ), expected
         * InvalidProductCodeException
         * 
         * @throws InvalidPasswordException
         * @throws InvalidUsernameException
         * @throws UnauthorizedException
         * @throws InvalidProductDescriptionException
         * @throws InvalidPricePerUnitException
         * @throws InvalidProductIdException
         * @throws InvalidProductCodeException
         */
        @Test(expected = InvalidProductCodeException.class)
        public void test_11_updateProduct() throws InvalidPasswordException, InvalidUsernameException,
                UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException,
                InvalidProductIdException, InvalidProductCodeException {

            String description = "asd";
            String productCode = NOT_VALID_BARCODE_12.get(0);
            Double pricePerUnit = 12.0d;
            String note = "asd";

            AuthenticationManager.getInstance().login("admin", "admin");

            ProductType test = new ProductType();
            test.setProductDescription(description);
            test.setPricePerUnit(pricePerUnit);
            test.setNote(note);
            ProductTypeDao.getInstance().save(test);

            ProductTypeDao.getInstance().delete(test);

            ProductTypeManager.getInstance().updateProduct(test.getId(), description, productCode, pricePerUnit, note);

        }

        /**
         * invalid value for product code ( not valid barcode - 13 ), expected
         * InvalidProductCodeException
         * 
         * @throws InvalidPasswordException
         * @throws InvalidUsernameException
         * @throws UnauthorizedException
         * @throws InvalidProductDescriptionException
         * @throws InvalidPricePerUnitException
         * @throws InvalidProductIdException
         * @throws InvalidProductCodeException
         */
        @Test(expected = InvalidProductCodeException.class)
        public void test_12_updateProduct() throws InvalidPasswordException, InvalidUsernameException,
                UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException,
                InvalidProductIdException, InvalidProductCodeException {

            Integer productId = null;
            String description = "asd";
            String productCode = NOT_VALID_BARCODE_13.get(0);
            Double pricePerUnit = 12.0d;
            String note = "asd";

            AuthenticationManager.getInstance().login("admin", "admin");

            ProductType test = new ProductType();
            test.setProductDescription(description);
            test.setPricePerUnit(pricePerUnit);
            test.setNote(note);
            ProductTypeDao.getInstance().save(test);

            ProductTypeDao.getInstance().delete(test);

            ProductTypeManager.getInstance().updateProduct(test.getId(), description, productCode, pricePerUnit, note);

        }

        /**
         * invalid value for product code ( not valid barcode - 14 ), expected
         * InvalidProductCodeException
         * 
         * @throws InvalidPasswordException
         * @throws InvalidUsernameException
         * @throws UnauthorizedException
         * @throws InvalidProductDescriptionException
         * @throws InvalidPricePerUnitException
         * @throws InvalidProductIdException
         * @throws InvalidProductCodeException
         */
        @Test(expected = InvalidProductCodeException.class)
        public void test_13_updateProduct() throws InvalidPasswordException, InvalidUsernameException,
                UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException,
                InvalidProductIdException, InvalidProductCodeException {

            Integer productId = null;
            String description = "asd";
            String productCode = NOT_VALID_BARCODE_14.get(0);
            Double pricePerUnit = 12.0d;
            String note = "asd";

            AuthenticationManager.getInstance().login("admin", "admin");

            ProductType test = new ProductType();
            test.setProductDescription(description);
            test.setPricePerUnit(pricePerUnit);
            test.setNote(note);
            ProductTypeDao.getInstance().save(test);

            ProductTypeDao.getInstance().delete(test);

            ProductTypeManager.getInstance().updateProduct(test.getId(), description, productCode, pricePerUnit, note);

        }

        /**
         * new barcode associated, expected false
         * 
         * @throws InvalidPasswordException
         * @throws InvalidUsernameException
         * @throws UnauthorizedException
         * @throws InvalidProductDescriptionException
         * @throws InvalidPricePerUnitException
         * @throws InvalidProductIdException
         * @throws InvalidProductCodeException
         */
        @Test
        public void test_14_updateProduct() throws InvalidPasswordException, InvalidUsernameException,
                UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException,
                InvalidProductIdException, InvalidProductCodeException {

            String description = "asd";
            String productCode = VALID_BARCODE_13.get(0);
            Double pricePerUnit = 12.0d;
            String note = "asd";

            AuthenticationManager.getInstance().login("admin", "admin");

            ProductType test = new ProductType();
            test.setBarCode(productCode);
            ProductTypeDao.getInstance().save(test);

            ProductType test1 = new ProductType();
            test1.setBarCode(VALID_BARCODE_13.get(1));
            ProductTypeDao.getInstance().save(test1);

            Boolean result = ProductTypeManager.getInstance().updateProduct(test.getId(), description,
                    test1.getBarCode(), pricePerUnit, note);

            ProductTypeDao.getInstance().delete(test);
            ProductTypeDao.getInstance().delete(test1);

            Assert.assertFalse(result);

        }

        /**
         * invalid value for price per unit(MIN VALUE), expected
         * InvalidPricePerUnitException
         * 
         * @throws InvalidPasswordException
         * @throws InvalidUsernameException
         * @throws UnauthorizedException
         * @throws InvalidProductDescriptionException
         * @throws InvalidPricePerUnitException
         * @throws InvalidProductIdException
         * @throws InvalidProductCodeException
         */
        @Test(expected = InvalidPricePerUnitException.class)
        public void test_15_updateProduct() throws InvalidPasswordException, InvalidUsernameException,
                UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException,
                InvalidProductIdException, InvalidProductCodeException {

            String description = "asd";
            String productCode = VALID_BARCODE_13.get(0);
            Double pricePerUnit = Double.MIN_VALUE;
            String note = "asd";

            AuthenticationManager.getInstance().login("admin", "admin");

            ProductType test = new ProductType();
            test.setBarCode(productCode);
            test.setProductDescription(description);
            test.setPricePerUnit(pricePerUnit);
            test.setNote(note);
            ProductTypeDao.getInstance().save(test);

            try {
                ProductTypeManager.getInstance().updateProduct(test.getId(), test.getProductDescription(),
                        test.getBarCode(), test.getPricePerUnit(), test.getNote());
            } catch (InvalidPricePerUnitException e) {
                ProductTypeDao.getInstance().delete(test);
                throw e;
            }

        }

        /**
         * invalid value for price per unit(MIN VALUE), expected
         * InvalidPricePerUnitException
         * 
         * @throws InvalidPasswordException
         * @throws InvalidUsernameException
         * @throws UnauthorizedException
         * @throws InvalidProductDescriptionException
         * @throws InvalidPricePerUnitException
         * @throws InvalidProductIdException
         * @throws InvalidProductCodeException
         */
        @Test(expected = InvalidPricePerUnitException.class)
        public void test_16_updateProduct() throws InvalidPasswordException, InvalidUsernameException,
                UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException,
                InvalidProductIdException, InvalidProductCodeException {

            String description = "asd";
            String productCode = VALID_BARCODE_13.get(0);
            Double pricePerUnit = Double.MIN_VALUE;
            String note = "asd";

            AuthenticationManager.getInstance().login("admin", "admin");

            ProductType test = new ProductType();
            test.setBarCode(productCode);
            test.setProductDescription(description);
            test.setPricePerUnit(pricePerUnit);
            test.setNote(note);
            ProductTypeDao.getInstance().save(test);

            try {
                ProductTypeManager.getInstance().updateProduct(test.getId(), test.getProductDescription(),
                        test.getBarCode(), test.getPricePerUnit(), test.getNote());
            } catch (InvalidPricePerUnitException e) {
                ProductTypeDao.getInstance().delete(test);
                throw e;
            }

        }

        /**
         * empty string
         */
        @Test
        public void test_1_checkProductCode() {
            Assert.assertFalse(ProductTypeManager.getInstance().checkProductCode(""));
        }

        /**
         * null
         */
        @Test
        public void test_2_checkProductCode() {
            Assert.assertFalse(ProductTypeManager.getInstance().checkProductCode(null));
        }

        /**
         * < 12
         */
        @Test
        public void test_3_checkProductCode() {
            Assert.assertFalse(
                    ProductTypeManager.getInstance().checkProductCode(VALID_BARCODE_12.get(0).substring(0, 11)));
        }

        /**
         * > 14
         */
        @Test
        public void test_4_checkProductCode() {
            Assert.assertFalse(ProductTypeManager.getInstance().checkProductCode(VALID_BARCODE_14.get(0) + "1"));
        }

        /**
         * not a number
         */
        @Test
        public void test_5_checkProductCode() {
            Assert.assertFalse(ProductTypeManager.getInstance().checkProductCode(NOT_VALID_BARCODE_14.get(1)));
        }

        /**
         * valid barcode 12
         */
        @Test
        public void test_6_checkProductCode() {
            Assert.assertTrue(ProductTypeManager.getInstance().checkProductCode(VALID_BARCODE_12.get(0)));
        }

        /**
         * valid barcode 13
         */
        @Test
        public void test_7_checkProductCode() {
            Assert.assertTrue(ProductTypeManager.getInstance().checkProductCode(VALID_BARCODE_13.get(0)));
        }

        /**
         * valid barcode 14
         */
        @Test
        public void test_8_checkProductCode() {
            Assert.assertTrue(ProductTypeManager.getInstance().checkProductCode(VALID_BARCODE_14.get(0)));
        }

        /**
         * Invalid value for product id (null)
         * 
         * @throws UnauthorizedException
         * @throws InvalidProductIdException
         */
        @Test(expected = InvalidProductIdException.class)
        public void test_1_deleteProductType() throws UnauthorizedException, InvalidProductIdException,
                InvalidPasswordException, InvalidUsernameException {

            AuthenticationManager.getInstance().login("admin", "admin");
            ProductTypeManager.getInstance().deleteProductType(0);
        }

        /**
         * invalid value for product id (0)
         * 
         * @throws UnauthorizedException
         * @throws InvalidProductIdException
         */
        @Test(expected = InvalidProductIdException.class)
        public void test_2_deleteProductType() throws UnauthorizedException, InvalidProductIdException,
                InvalidPasswordException, InvalidUsernameException {
            AuthenticationManager.getInstance().login("admin", "admin");
            ProductTypeManager.getInstance().deleteProductType(0);
        }

        /**
         * Invalid value for product id (< 0)
         * 
         * @throws UnauthorizedException
         * @throws InvalidProductIdException
         */
        @Test(expected = InvalidProductIdException.class)
        public void test_3_deleteProductType() throws UnauthorizedException, InvalidProductIdException,
                InvalidPasswordException, InvalidUsernameException {
            AuthenticationManager.getInstance().login("admin", "admin");
            ProductTypeManager.getInstance().deleteProductType(-1);
        }

        /**
         * id of a product not exists
         * 
         * @throws UnauthorizedException
         * @throws InvalidProductIdException
         */
        @Test
        public void test_4_deleteProductType() throws UnauthorizedException, InvalidProductIdException,
                InvalidPasswordException, InvalidUsernameException {

            ProductType test = new ProductType();
            ProductTypeDao.getInstance().save(test);
            ProductTypeDao.getInstance().delete(test);

            AuthenticationManager.getInstance().login("admin", "admin");
            Assert.assertFalse(ProductTypeManager.getInstance().deleteProductType(test.getId()));
        }

        /**
         * id of a product exists,
         * 
         * @throws UnauthorizedException
         * @throws InvalidProductIdException
         */
        @Test
        public void test_5_deleteProductType() throws UnauthorizedException, InvalidProductIdException,
                InvalidPasswordException, InvalidUsernameException {

            ProductType test = new ProductType();
            ProductTypeDao.getInstance().save(test);

            AuthenticationManager.getInstance().login("admin", "admin");
            Assert.assertTrue(ProductTypeManager.getInstance().deleteProductType(test.getId()));

        }

        /**
         * Invalid value for product id (null)
         * 
         * @throws UnauthorizedException
         * @throws InvalidProductIdException
         */
        @Test(expected = InvalidProductIdException.class)
        public void test_1_updateQuantity() throws UnauthorizedException, InvalidProductIdException,
                InvalidPasswordException, InvalidUsernameException {

            AuthenticationManager.getInstance().login("admin", "admin");

            ProductTypeManager.getInstance().updateQuantity(null, 1);
        }

        /**
         * invalid value for product id (0)
         * 
         * @throws UnauthorizedException
         * @throws InvalidProductIdException
         */
        @Test(expected = InvalidProductIdException.class)
        public void test_2_updateQuantity() throws UnauthorizedException, InvalidProductIdException,
                InvalidPasswordException, InvalidUsernameException {
            AuthenticationManager.getInstance().login("admin", "admin");
            ProductTypeManager.getInstance().updateQuantity(0, 0);
        }

        /**
         * Invalid value for product id (< 0)
         * 
         * @throws UnauthorizedException
         * @throws InvalidProductIdException
         */
        @Test(expected = InvalidProductIdException.class)
        public void test_3_updateQuantity() throws UnauthorizedException, InvalidProductIdException,
                InvalidPasswordException, InvalidUsernameException {
            AuthenticationManager.getInstance().login("admin", "admin");
            ProductTypeManager.getInstance().updateQuantity(-1, 0);
        }

        /**
         * update quantity
         * 
         * @throws UnauthorizedException
         * @throws InvalidProductIdException
         */
        @Test
        public void test_4_updateQuantity() throws UnauthorizedException, InvalidProductIdException,
                InvalidPasswordException, InvalidUsernameException {
            AuthenticationManager.getInstance().login("admin", "admin");

            ProductType test = new ProductType();
            test.setBarCode(VALID_BARCODE_12.get(0));
            test.setLocation("1-1-1");
            test.setQuantity(0);
            Integer toBeAdded = 1;
            ProductTypeDao.getInstance().save(test);

            Integer oldQuantity = test.getQuantity();

            Assert.assertTrue(ProductTypeManager.getInstance().updateQuantity(test.getId(), toBeAdded));

            ProductType updated = ProductTypeDao.getInstance().findById(test.getId());

            ProductTypeDao.getInstance().delete(test);
            Assert.assertTrue((updated.getQuantity() - (oldQuantity + toBeAdded)) == 0 && updated.getQuantity() >= 0);

        }

        /**
         * toBeAdded < 0 , result quantity < 0 , expected false
         * 
         * @throws UnauthorizedException
         * @throws InvalidProductIdException
         */
        @Test
        public void test_5_updateQuantity() throws UnauthorizedException, InvalidProductIdException,
                InvalidPasswordException, InvalidUsernameException {
            AuthenticationManager.getInstance().login("admin", "admin");

            ProductType test = new ProductType();
            test.setBarCode(VALID_BARCODE_12.get(0));
            test.setLocation("1-1-1");
            test.setQuantity(0);
            Integer toBeAdded = -1;
            ProductTypeDao.getInstance().save(test);

            Integer oldQuantity = test.getQuantity();

            Boolean result = ProductTypeManager.getInstance().updateQuantity(test.getId(), toBeAdded);

            ProductType updated = ProductTypeDao.getInstance().findById(test.getId());

            ProductTypeDao.getInstance().delete(test);

            Boolean result1 = (updated.getQuantity() - (oldQuantity + toBeAdded)) == 0 && updated.getQuantity() >= 0;

            Assert.assertFalse(result);
            Assert.assertFalse(result1);

            ProductTypeDao.getInstance().delete(test);

            Assert.assertFalse(result);

        }

        /**
         * product has no location , expected false
         * 
         * @throws UnauthorizedException
         * @throws InvalidProductIdException
         */
        @Test
        public void test_6_updateQuantity() throws UnauthorizedException, InvalidProductIdException,
                InvalidPasswordException, InvalidUsernameException {
            AuthenticationManager.getInstance().login("admin", "admin");

            ProductType test = new ProductType();
            test.setBarCode(VALID_BARCODE_12.get(0));
            test.setLocation(null);
            test.setQuantity(0);
            Integer toBeAdded = 1;
            ProductTypeDao.getInstance().save(test);

            Integer oldQuantity = test.getQuantity();

            Boolean result = ProductTypeManager.getInstance().updateQuantity(test.getId(), toBeAdded);

            ProductType updated = ProductTypeDao.getInstance().findById(test.getId());

            ProductTypeDao.getInstance().delete(test);

            Boolean result1 = (updated.getQuantity() - (oldQuantity + toBeAdded)) == 0 && updated.getQuantity() >= 0;

            Assert.assertFalse(result);
            Assert.assertFalse(result1);

        }
    }

    public static class TestBalanceOperationManager {
        /*
         * valid balance operation(CREDIT), toBeAdded > 0
         */
        @Test
        public void test_1_recordBalanceUpdate()
                throws InvalidUsernameException, InvalidPasswordException, UnauthorizedException {

            AuthenticationManager.getInstance().login("admin", "admin");

            Boolean result = BalanceOperationManager.getInstance().recordBalanceUpdate(1.0d);

            Assert.assertTrue(result);

        }

        /*
         * valid balance operation(DEBIT), toBeAdded < 0
         */
        @Test
        public void test_2_recordBalanceUpdate()
                throws InvalidUsernameException, InvalidPasswordException, UnauthorizedException {

            AuthenticationManager.getInstance().login("admin", "admin");

            it.polito.ezshop.model.BalanceOperation old = new it.polito.ezshop.model.BalanceOperation();
            old.setDate(LocalDate.now());
            old.setMoney(100d);
            old.setType("CREDIT");

            BalanceOperationDao.getInstance().save(old);

            Boolean result = BalanceOperationManager.getInstance().recordBalanceUpdate(-1.0d);

            Assert.assertTrue(result);

        }

        @Test
        public void test_3_recordBalanceUpdate()
                throws InvalidUsernameException, InvalidPasswordException, UnauthorizedException {

            AuthenticationManager.getInstance().login("admin", "admin");

            it.polito.ezshop.model.BalanceOperation old = new it.polito.ezshop.model.BalanceOperation();
            old.setDate(LocalDate.now());
            old.setMoney(100d);
            old.setType("CREDIT");

            BalanceOperationDao.getInstance().save(old);

            Boolean result = BalanceOperationManager.getInstance().recordBalanceUpdate(-200.0d);

            Assert.assertFalse(result);

        }

    }

    public static class TestOrderManager {

        public static class TestIssueOrder {

            private List<String> VALID_BARCODE_13;
            private List<String> NOT_VALID_BARCODE_13;
            private List<String> VALID_BARCODE_12;
            private List<String> NOT_VALID_BARCODE_12;
            private List<String> VALID_BARCODE_14;
            private List<String> NOT_VALID_BARCODE_14;

            @Before
            public void createUser() {
                User user = new User();
                user.setUsername("admin");
                user.setPassword("admin");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);
            }

            public TestIssueOrder() {

                VALID_BARCODE_13 = Arrays.asList("1111111111116", "1111111111123");
                NOT_VALID_BARCODE_13 = Arrays.asList("1111111111115", "11111111111a5");
                VALID_BARCODE_12 = Arrays.asList("111111111117");
                NOT_VALID_BARCODE_12 = Arrays.asList("111111111116", "11111111111a");
                VALID_BARCODE_14 = Arrays.asList("11111111111113");
                NOT_VALID_BARCODE_14 = Arrays.asList("11111111111112", "1111111111111a");

            }

            @Test
            public void test_9_issueOrder()
                    throws InvalidUsernameException, InvalidPasswordException, InvalidProductCodeException,
                    InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException {

                AuthenticationManager.getInstance().login("admin", "admin");

                ProductType pt = new ProductType();
                pt.setBarCode(VALID_BARCODE_12.get(0));
                pt.setLocation("1-a-1");
                pt.setPricePerUnit(1.0d);
                pt.setQuantity(100);

                ProductTypeDao.getInstance().save(pt);

                Assert.assertTrue(OrderManager.getInstance().issueOrder(pt.getBarCode(), 100, 1.0d) > 0);
            }

            @Test
            public void test_10_issueOrder()
                    throws InvalidUsernameException, InvalidPasswordException, InvalidProductCodeException,
                    InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException {

                AuthenticationManager.getInstance().login("admin", "admin");

                ProductType pt = new ProductType();
                pt.setBarCode(VALID_BARCODE_12.get(0));
                pt.setLocation("1-a-1");
                pt.setPricePerUnit(1.0d);
                pt.setQuantity(100);

                ProductTypeDao.getInstance().save(pt);
                ProductTypeDao.getInstance().delete(pt);

                Integer order = OrderManager.getInstance().issueOrder(VALID_BARCODE_12.get(0), 100, 1.0d);

                Assert.assertTrue(order <= 0);
            }

            @Test(expected = InvalidPricePerUnitException.class)
            public void test_8_issueOrder()
                    throws InvalidUsernameException, InvalidPasswordException, InvalidProductCodeException,
                    InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException {

                AuthenticationManager.getInstance().login("admin", "admin");

                ProductType pt = new ProductType();
                pt.setBarCode(VALID_BARCODE_12.get(0));
                pt.setLocation("1-a-1");
                pt.setPricePerUnit(1.0d);
                pt.setQuantity(100);

                ProductTypeDao.getInstance().save(pt);

                Integer order = OrderManager.getInstance().issueOrder(VALID_BARCODE_12.get(0), 1, 0d);

                Assert.assertFalse(order > 0);
            }

            @Test(expected = InvalidProductCodeException.class)
            public void test_6_issueOrder()
                    throws InvalidUsernameException, InvalidPasswordException, InvalidProductCodeException,
                    InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException {

                AuthenticationManager.getInstance().login("admin", "admin");

                Integer order = OrderManager.getInstance().issueOrder("asdasdasdasd", 1, 1.0d);

                Assert.assertFalse(order > 0);
            }

            @Test(expected = InvalidProductCodeException.class)
            public void test_4_issueOrder()
                    throws InvalidUsernameException, InvalidPasswordException, InvalidProductCodeException,
                    InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException {

                AuthenticationManager.getInstance().login("admin", "admin");

                Integer order = OrderManager.getInstance().issueOrder("123123123123123", 1, 1.0d);

                Assert.assertFalse(order > 0);
            }

            @Test(expected = InvalidProductCodeException.class)
            public void test_3_issueOrder()
                    throws InvalidUsernameException, InvalidPasswordException, InvalidProductCodeException,
                    InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException {

                AuthenticationManager.getInstance().login("admin", "admin");

                Integer order = OrderManager.getInstance().issueOrder("123456", 1, 1.0d);

                Assert.assertFalse(order > 0);
            }

            @Test(expected = InvalidProductCodeException.class)
            public void test_1_issueOrder()
                    throws InvalidUsernameException, InvalidPasswordException, InvalidProductCodeException,
                    InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException {

                AuthenticationManager.getInstance().login("admin", "admin");

                Integer order = OrderManager.getInstance().issueOrder("", 1, 1.0d);

                Assert.assertFalse(order > 0);
            }

            @Test(expected = InvalidProductCodeException.class)
            public void test_2_issueOrder()
                    throws InvalidUsernameException, InvalidPasswordException, InvalidProductCodeException,
                    InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException {

                AuthenticationManager.getInstance().login("admin", "admin");

                Integer order = OrderManager.getInstance().issueOrder(null, 1, 1.0d);

                Assert.assertFalse(order > 0);
            }

            @Test(expected = InvalidProductCodeException.class)
            public void test_5_issueOrder()
                    throws InvalidUsernameException, InvalidPasswordException, InvalidProductCodeException,
                    InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException {

                AuthenticationManager.getInstance().login("admin", "admin");

                Integer order = OrderManager.getInstance().issueOrder(NOT_VALID_BARCODE_12.get(0), 1, 1.0);

                Assert.assertFalse(order > 0);
            }

            @Test(expected = InvalidQuantityException.class)
            public void test_7_issueOrder()
                    throws InvalidUsernameException, InvalidPasswordException, InvalidProductCodeException,
                    InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException {

                AuthenticationManager.getInstance().login("admin", "admin");

                Integer order = OrderManager.getInstance().issueOrder(VALID_BARCODE_12.get(0), 0, 1.0);

                Assert.assertFalse(order > 0);
            }
        }

        public static class TestPayOrder {

            @Test(expected = InvalidOrderIdException.class)
            public void test_1_payOrderFor() throws InvalidUsernameException, InvalidPasswordException,
                    InvalidOrderIdException, UnauthorizedException {

                AuthenticationManager.getInstance().login("admin", "admin");

                OrderManager.getInstance().payOrder(-1);
            }

            @Test
            public void test_2_payOrder() throws InvalidUsernameException, InvalidPasswordException,
                    InvalidOrderIdException, UnauthorizedException {
                AuthenticationManager.getInstance().login("admin", "admin");

                Order order = new Order();
                order.setStatus(OrderStatusEnum.ORDERED.name());
                order.setProductCode("123123123125");
                order.setPricePerUnit(1.0d);
                order.setQuantity(100);

                order = OrderDao.getInstance().save(order);

                Assert.assertTrue(OrderManager.getInstance().payOrder(order.getOrderId()));

            }

            @Test
            public void test_3_payOrder() throws InvalidUsernameException, InvalidPasswordException,
                    InvalidOrderIdException, UnauthorizedException {
                AuthenticationManager.getInstance().login("admin", "admin");

                Order order = new Order();
                order.setStatus(OrderStatusEnum.PAYED.name());
                order.setProductCode("123123123125");
                order.setPricePerUnit(1.0d);
                order.setQuantity(100);

                order = OrderDao.getInstance().save(order);

                Assert.assertTrue(OrderManager.getInstance().payOrder(order.getOrderId()));

            }

            @Test
            public void test_4_payOrder() throws InvalidUsernameException, InvalidPasswordException,
                    InvalidOrderIdException, UnauthorizedException {
                AuthenticationManager.getInstance().login("admin", "admin");

                Order order = new Order();
                order.setStatus(OrderStatusEnum.ISSUED.name());
                order.setProductCode("123123123125");
                order.setPricePerUnit(1.0d);
                order.setQuantity(100);

                order = OrderDao.getInstance().save(order);

                Assert.assertTrue(OrderManager.getInstance().payOrder(order.getOrderId()));
            }

            @Test
            public void test_5_payOrder() throws InvalidUsernameException, InvalidPasswordException,
                    InvalidOrderIdException, UnauthorizedException {
                AuthenticationManager.getInstance().login("admin", "admin");

                Order order = new Order();
                order.setStatus(OrderStatusEnum.COMPLETED.name());
                order.setProductCode("123123123125");
                order.setPricePerUnit(1.0d);
                order.setQuantity(100);

                order = OrderDao.getInstance().save(order);

                Assert.assertFalse(OrderManager.getInstance().payOrder(order.getOrderId()));
            }
        }

        public static class TestRecordOrderArrival {

            private List<String> VALID_BARCODE_13;
            private List<String> NOT_VALID_BARCODE_13;
            private List<String> VALID_BARCODE_12;
            private List<String> NOT_VALID_BARCODE_12;
            private List<String> VALID_BARCODE_14;
            private List<String> NOT_VALID_BARCODE_14;

            @Before
            public void createUser() {
                User user = new User();
                user.setUsername("admin");
                user.setPassword("admin");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);
            }

            public TestRecordOrderArrival() {

                VALID_BARCODE_13 = Arrays.asList("1111111111116", "1111111111123");
                NOT_VALID_BARCODE_13 = Arrays.asList("1111111111115", "11111111111a5");
                VALID_BARCODE_12 = Arrays.asList("111111111117");
                NOT_VALID_BARCODE_12 = Arrays.asList("111111111116", "11111111111a");
                VALID_BARCODE_14 = Arrays.asList("11111111111113");
                NOT_VALID_BARCODE_14 = Arrays.asList("11111111111112", "1111111111111a");

            }

            @Test(expected = InvalidOrderIdException.class)
            public void test_1_recordOrderArrival() throws InvalidUsernameException, InvalidPasswordException,
                    InvalidOrderIdException, UnauthorizedException, InvalidLocationException {
                AuthenticationManager.getInstance().login("admin", "admin");

                Order order = new Order();
                order.setStatus(OrderStatusEnum.PAYED.name());
                order.setProductCode("123123123125");
                order.setPricePerUnit(1.0d);
                order.setQuantity(100);

                order = OrderDao.getInstance().save(order);

                Assert.assertTrue(OrderManager.getInstance().recordOrderArrival(order.getOrderId()));
            }

            @Test
            public void test_3_recordOrderArrival() throws InvalidUsernameException, InvalidPasswordException,
                    InvalidOrderIdException, UnauthorizedException, InvalidLocationException {
                AuthenticationManager.getInstance().login("admin", "admin");

                Order order = new Order();
                order.setStatus(OrderStatusEnum.COMPLETED.name());
                order.setProductCode("123123123125");
                order.setPricePerUnit(1.0d);
                order.setQuantity(100);

                ProductType product = ProductTypeDao.getInstance().findByBarCode("123123123125");

                Integer quantity = product.getQuantity();

                order = OrderDao.getInstance().save(order);

                Assert.assertTrue(OrderManager.getInstance().recordOrderArrival(order.getOrderId()));

                product = ProductTypeDao.getInstance().findByBarCode("123123123125");

                Assert.assertTrue(product.getQuantity() == (quantity + order.getQuantity()));
            }

            @Test
            public void test_5_recordOrderArrival() throws InvalidUsernameException, InvalidPasswordException,
                    InvalidOrderIdException, UnauthorizedException, InvalidLocationException {
                AuthenticationManager.getInstance().login("admin", "admin");

                Order order = new Order();
                order.setStatus(OrderStatusEnum.COMPLETED.name());
                order.setProductCode("123123123125");
                order.setPricePerUnit(1.0d);
                order.setQuantity(100);

                ProductType product = ProductTypeDao.getInstance().findByBarCode("123123123125");

                Integer quantity = product.getQuantity();

                order = OrderDao.getInstance().save(order);

                Assert.assertTrue(OrderManager.getInstance().recordOrderArrival(order.getOrderId()));

                product = ProductTypeDao.getInstance().findByBarCode("123123123125");

                Assert.assertTrue(product.getQuantity() == (quantity + order.getQuantity()));
            }

            @Test
            public void test_2_recordOrderArrival() throws InvalidOrderIdException, UnauthorizedException,
                    InvalidLocationException, InvalidUsernameException, InvalidPasswordException {
                AuthenticationManager.getInstance().login("admin", "admin");

                Order order = new Order();
                order.setStatus(OrderStatusEnum.ORDERED.name());
                order.setProductCode("123123123125");
                order.setPricePerUnit(1.0d);
                order.setQuantity(100);

                ProductType product = ProductTypeDao.getInstance().findByBarCode("123123123125");

                Integer quantity = product.getQuantity();

                order = OrderDao.getInstance().save(order);

                Assert.assertFalse(OrderManager.getInstance().recordOrderArrival(order.getOrderId()));

            }

            @Test
            public void test_4_recordOrderArrival() throws InvalidOrderIdException, UnauthorizedException,
                    InvalidLocationException, InvalidUsernameException, InvalidPasswordException {
                AuthenticationManager.getInstance().login("admin", "admin");

                Order order = new Order();
                order.setStatus(OrderStatusEnum.ISSUED.name());
                order.setProductCode("123123123125");
                order.setPricePerUnit(1.0d);
                order.setQuantity(100);

                ProductType product = ProductTypeDao.getInstance().findByBarCode("123123123125");

                Integer quantity = product.getQuantity();

                order = OrderDao.getInstance().save(order);

                Assert.assertFalse(OrderManager.getInstance().recordOrderArrival(order.getOrderId()));

            }
        }
    }

    /*
     * SALE TRANSACTION MANAGER
     * 
     */
    public static class checkAuthenticationManager {

        @BeforeClass
        public static void resetDB() {
            for (User user : UserDao.getInstance().findAll()) {
                UserDao.getInstance().delete(user);
            }
            AuthenticationManager.getInstance().logout();
        }
        /* invalid role, expected false */

        @Test
        public void test_1_checkAuthenticationManager() {

            User admin = new User();
            admin.setUsername("NoEntry");
            admin.setPassword("NoEntry");
            admin.setRole("ROLE");

            Assert.assertFalse(SaleTransactionManager.getInstance().checkAuthenticationManager());

        }
        /* invalid role(null), expected false */

        @Test
        public void test_2_checkAuthenticationManager() {

            User admin = new User();
            admin.setUsername("NoEntry");
            admin.setPassword("NoEntry");
            admin.setRole(null);

            Assert.assertFalse(SaleTransactionManager.getInstance().checkAuthenticationManager());

        }

        /*
         * nominal case, expected true
         * 
         */

        @Test
        public void test_3_checkAuthenticationManager() throws InvalidPasswordException, InvalidUsernameException {

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());
            Assert.assertTrue(SaleTransactionManager.getInstance().checkAuthenticationManager());
            AuthenticationManager.getInstance().logout();

        }

    }

    public static class StartSaleTransaction {
        /*
         * StartSaleTransaction
         */
        /*
         * invalid role, expected UnauthorizedException
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         */

        @Test(expected = UnauthorizedException.class)
        public void test_1_startSaleTransaction()
                throws UnauthorizedException, InvalidUsernameException, InvalidPasswordException {

            User admin = new User();
            admin.setUsername("NoEntry");
            admin.setPassword("NoEntry");
            admin.setRole("ROLE");

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            UserDao.getInstance().delete(admin);

            SaleTransactionManager.getInstance().startSaleTransaction();

        }

        /*
         * nominal case, SaleTransaction created successfully
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         */

        @Test
        public void test_2_startSaleTransaction()
                throws UnauthorizedException, InvalidUsernameException, InvalidPasswordException {

            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransactionManager.getInstance().startSaleTransaction();

            UserDao.getInstance().delete(admin);

        }
    }

    public static class addProductToSale {

        /*
         * invalid value for transaction id(< 0 ), expected
         * InvalidTransactionIdException
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test(expected = InvalidTransactionIdException.class)
        public void test_1_addProductToSale() throws InvalidTransactionIdException, InvalidProductCodeException,
                InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            Integer transactionId = -1;
            String productCode = "6291041500213";
            int amount = 5;

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());

            SaleTransactionDao.getInstance().save(sale);
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().addProductToSale(transactionId, productCode, amount);

        }

        /*
         * invalid value for transaction id(= null ), expected
         * InvalidTransactionIdException
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test(expected = InvalidTransactionIdException.class)
        public void test_2_addProductToSale() throws InvalidTransactionIdException, InvalidProductCodeException,
                InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            Integer transactionId = null;
            String productCode = "6291041500213";
            int amount = 3;

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());

            SaleTransactionDao.getInstance().save(sale);
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().addProductToSale(transactionId, productCode, amount);

        }

        /*
         * invalid value for product code (null), expected InvalidProductCodeException
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test(expected = InvalidProductCodeException.class)
        public void test_3_addProductToSale() throws InvalidTransactionIdException, InvalidProductCodeException,
                InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());
            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();
            String productCode = null;
            int amount = 3;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().addProductToSale(transactionId, productCode, amount);

        }

        /*
         * invalid value for product code (empty string), expected
         * InvalidProductCodeException
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test(expected = InvalidProductCodeException.class)
        public void test_4_addProductToSale() throws InvalidTransactionIdException, InvalidProductCodeException,
                InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());
            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();
            String productCode = "";
            int amount = 3;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().addProductToSale(transactionId, productCode, amount);

        }

        /*
         * invalid value for product code (wrong product code), expected
         * InvalidProductCodeException
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test(expected = InvalidProductCodeException.class)
        public void test_5_addProductToSale() throws InvalidTransactionIdException, InvalidProductCodeException,
                InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());
            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();
            String productCode = "1111111111115";
            int amount = 3;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().addProductToSale(transactionId, productCode, amount);

        }

        /*
         * invalid value for product code (not number), expected
         * InvalidProductCodeException
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test(expected = InvalidProductCodeException.class)
        public void test_6_addProductToSale() throws InvalidTransactionIdException, InvalidProductCodeException,
                InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());
            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();
            String productCode = "11111111111a5";
            int amount = 3;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().addProductToSale(transactionId, productCode, amount);

        }

        /*
         * invalid value for product code (bar code > 14), expected
         * InvalidProductCodeException
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test(expected = InvalidProductCodeException.class)
        public void test_7_addProductToSale() throws InvalidTransactionIdException, InvalidProductCodeException,
                InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());
            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();
            String productCode = "11111111111155431";
            int amount = 3;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().addProductToSale(transactionId, productCode, amount);

        }

        /*
         * invalid value for product code (bar code < 12), expected
         * InvalidProductCodeException
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test(expected = InvalidProductCodeException.class)
        public void test_8_addProductToSale() throws InvalidTransactionIdException, InvalidProductCodeException,
                InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());
            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();
            String productCode = "12321456743";
            int amount = 3;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().addProductToSale(transactionId, productCode, amount);

        }

        /*
         * invalid product (product = null), because the searched product code is not
         * there), expected false
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test
        public void test_9_addProductToSale() throws InvalidTransactionIdException, InvalidProductCodeException,
                InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());
            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();

            ProductType productType = new ProductType();
            productType.setQuantity(100);
            productType.setProductDescription("Pomodoro");
            productType.setBarCode("6291041500213");
            productType.setLocation("Any");
            productType.setPricePerUnit(5.00);
            productType.setNote("Nota");

            ProductTypeDao.getInstance().save(productType);

            String productCode = "8017596056993";
            int amount = 10;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            Assert.assertFalse(
                    SaleTransactionManager.getInstance().addProductToSale(transactionId, productCode, amount));

        }

        /*
         * invalid amount (< 0), expected InvalidQuantityException
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test(expected = InvalidQuantityException.class)
        public void test_10_addProductToSale() throws InvalidTransactionIdException, InvalidProductCodeException,
                InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());
            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();

            ProductType productType = new ProductType();
            productType.setQuantity(10);
            productType.setProductDescription("Pomodoro");
            productType.setBarCode("6291041500213");
            productType.setLocation("Any");
            productType.setPricePerUnit(5.00);
            productType.setNote("Nota");

            ProductTypeDao.getInstance().save(productType);

            String productCode = "6291041500213";
            int amount = -1;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().addProductToSale(transactionId, productCode, amount);

        }

        /*
         * invalid amount (amount > product.getQuantity() , expected false
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test
        public void test_11_addProductToSale() throws InvalidTransactionIdException, InvalidProductCodeException,
                InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());
            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();

            ProductType productType = new ProductType();
            productType.setQuantity(10);
            productType.setProductDescription("Pomodoro");
            productType.setBarCode("6291041500213");
            productType.setLocation("Any");
            productType.setPricePerUnit(5.00);
            productType.setNote("Nota");

            ProductTypeDao.getInstance().save(productType);

            String productCode = "6291041500213";
            int amount = 11;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            Assert.assertFalse(
                    SaleTransactionManager.getInstance().addProductToSale(transactionId, productCode, amount));

        }

        /*
         * invalid amount (amount = 0), expected false
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test
        public void test_12_addProductToSale() throws InvalidTransactionIdException, InvalidProductCodeException,
                InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());
            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();

            ProductType productType = new ProductType();
            productType.setQuantity(10);
            productType.setProductDescription("Pomodoro");
            productType.setBarCode("6291041500213");
            productType.setLocation("Any");
            productType.setPricePerUnit(5.00);
            productType.setNote("Nota");

            ProductTypeDao.getInstance().save(productType);

            String productCode = "6291041500213";
            int amount = 0;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            Assert.assertFalse(
                    SaleTransactionManager.getInstance().addProductToSale(transactionId, productCode, amount));

        }

        /*
         * close transaction , expected false
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test
        public void test_13_addProductToSale() throws InvalidTransactionIdException, InvalidProductCodeException,
                InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.CLOSED.name());
            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();

            ProductType productType = new ProductType();
            productType.setQuantity(10);
            productType.setProductDescription("Pomodoro");
            productType.setBarCode("6291041500213");
            productType.setLocation("Any");
            productType.setPricePerUnit(5.00);
            productType.setNote("Nota");

            ProductTypeDao.getInstance().save(productType);

            String productCode = "6291041500213";
            int amount = 5;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            Assert.assertFalse(
                    SaleTransactionManager.getInstance().addProductToSale(transactionId, productCode, amount));

        }

        /*
         * transaction is null , expected false
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test
        public void test_14_addProductToSale() throws InvalidTransactionIdException, InvalidProductCodeException,
                InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(null);
            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();

            ProductType productType = new ProductType();
            productType.setQuantity(10);
            productType.setProductDescription("Pomodoro");
            productType.setBarCode("6291041500213");
            productType.setLocation("Any");
            productType.setPricePerUnit(5.00);
            productType.setNote("Nota");

            ProductTypeDao.getInstance().save(productType);

            String productCode = "6291041500213";
            int amount = 5;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            Assert.assertFalse(
                    SaleTransactionManager.getInstance().addProductToSale(transactionId, productCode, amount));

        }

        /*
         * nominal case , add correctly
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test
        public void test_15_addProductToSale() throws InvalidTransactionIdException, InvalidProductCodeException,
                InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());
            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();

            ProductType productType = new ProductType();
            productType.setQuantity(10);
            productType.setProductDescription("Pomodoro");
            productType.setBarCode("6291041500213");
            productType.setLocation("Any");
            productType.setPricePerUnit(5.00);
            productType.setNote("Nota");

            ProductTypeDao.getInstance().save(productType);

            String productCode = "6291041500213";
            int amount = 2;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().startSaleTransaction();
            Assert.assertTrue(
                    SaleTransactionManager.getInstance().addProductToSale(transactionId, productCode, amount));

        }
    }

    public static class deleteProductFromSale {
        /*
         * invalid value for transaction id(< 0 ), expected
         * InvalidTransactionIdException
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test(expected = InvalidTransactionIdException.class)
        public void test_1_deleteProductFromSale() throws InvalidTransactionIdException, InvalidProductCodeException,
                InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            Integer transactionId = -1;
            String productCode = "6291041500213";
            int amount = 5;

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());

            SaleTransactionDao.getInstance().save(sale);
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().deleteProductFromSale(transactionId, productCode, amount);

        }

        /*
         * invalid value for transaction id(null ), expected
         * InvalidTransactionIdException
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test(expected = InvalidTransactionIdException.class)
        public void test_2_deleteProductFromSale() throws InvalidTransactionIdException, InvalidProductCodeException,
                InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            Integer transactionId = null;
            String productCode = "6291041500213";
            int amount = 5;

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());

            SaleTransactionDao.getInstance().save(sale);
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().deleteProductFromSale(transactionId, productCode, amount);

        }
        /*
         * invalid value for product code (null), expected InvalidProductCodeException
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test(expected = InvalidProductCodeException.class)
        public void test_3_deleteProductFromSale() throws InvalidTransactionIdException, InvalidProductCodeException,
                InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());
            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();
            String productCode = null;
            int amount = 3;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().deleteProductFromSale(transactionId, productCode, amount);

        }

        /*
         * invalid value for product code ("" Empty), expected
         * InvalidProductCodeException
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test(expected = InvalidProductCodeException.class)
        public void test_4_deleteProductFromSale() throws InvalidTransactionIdException, InvalidProductCodeException,
                InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());
            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();
            String productCode = "";
            int amount = 3;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().deleteProductFromSale(transactionId, productCode, amount);

        }

        /*
         * invalid value for product code (wrong check digit), expected
         * InvalidProductCodeException
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test(expected = InvalidProductCodeException.class)
        public void test_5_deleteProductFromSale() throws InvalidTransactionIdException, InvalidProductCodeException,
                InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());
            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();
            String productCode = "1111111111115";
            int amount = 3;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().deleteProductFromSale(transactionId, productCode, amount);

        }
        /*
         * invalid value for product code (not number), expected
         * InvalidProductCodeException
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test(expected = InvalidProductCodeException.class)
        public void test_6_deleteProductFromSale() throws InvalidTransactionIdException, InvalidProductCodeException,
                InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());
            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();
            String productCode = "11111111111a5";
            int amount = 3;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().deleteProductFromSale(transactionId, productCode, amount);

        }
        /*
         * invalid value for product code (productCode > 14), expected
         * InvalidProductCodeException
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test(expected = InvalidProductCodeException.class)
        public void test_7_deleteProductFromSale() throws InvalidTransactionIdException, InvalidProductCodeException,
                InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());
            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();
            String productCode = "11111111111111115";
            int amount = 3;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().deleteProductFromSale(transactionId, productCode, amount);

        }

        /*
         * invalid value for product code ( productCode < 12), expected
         * InvalidProductCodeException
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test(expected = InvalidProductCodeException.class)
        public void test_8_deleteProductFromSale() throws InvalidTransactionIdException, InvalidProductCodeException,
                InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());
            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();
            String productCode = "11111134";
            int amount = 3;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().deleteProductFromSale(transactionId, productCode, amount);

        }

        /*
         * invalid product ( null ) because code is exact but prodcut not exist,
         * expected false
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test
        public void test_9_deleteProductFromSale() throws InvalidTransactionIdException, InvalidProductCodeException,
                InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());
            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();
            String productCode = "8017596056993";
            int amount = 3;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            Assert.assertFalse(
                    SaleTransactionManager.getInstance().deleteProductFromSale(transactionId, productCode, amount));

        }
        /*
         * invalid amount (< 0), expected InvalidQuantityException
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test(expected = InvalidQuantityException.class)
        public void test_10_deleteProductFromSale() throws InvalidTransactionIdException, InvalidProductCodeException,
                InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());
            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();

            ProductType productType = new ProductType();
            productType.setQuantity(10);
            productType.setProductDescription("Pomodoro");
            productType.setBarCode("6291041500213");
            productType.setLocation("Any");
            productType.setPricePerUnit(5.00);
            productType.setNote("Nota");

            ProductTypeDao.getInstance().save(productType);

            String productCode = "6291041500213";
            int amount = -1;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().deleteProductFromSale(transactionId, productCode, amount);

        }

        /*
         * invalid amount (amount = 0), expected false
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test
        public void test_11_deleteProductFromSale() throws InvalidTransactionIdException, InvalidProductCodeException,
                InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());
            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();

            ProductType productType = new ProductType();
            productType.setQuantity(10);
            productType.setProductDescription("Pomodoro");
            productType.setBarCode("6291041500213");
            productType.setLocation("Any");
            productType.setPricePerUnit(5.00);
            productType.setNote("Nota");

            ProductTypeDao.getInstance().save(productType);

            String productCode = "6291041500213";
            int amount = 0;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            Assert.assertFalse(
                    SaleTransactionManager.getInstance().deleteProductFromSale(transactionId, productCode, amount));

        }

        /*
         * close transaction , expected false
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test
        public void test_12_deleteProductFromSale() throws InvalidTransactionIdException, InvalidProductCodeException,
                InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.CLOSED.name());
            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();

            ProductType productType = new ProductType();
            productType.setQuantity(10);
            productType.setProductDescription("Pomodoro");
            productType.setBarCode("6291041500213");
            productType.setLocation("Any");
            productType.setPricePerUnit(5.00);
            productType.setNote("Nota");

            ProductTypeDao.getInstance().save(productType);

            String productCode = "6291041500213";
            int amount = 5;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            Assert.assertFalse(
                    SaleTransactionManager.getInstance().deleteProductFromSale(transactionId, productCode, amount));

        }

        /*
         * transaction is null , expected false
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test
        public void test_13_deleteProductFromSale() throws InvalidTransactionIdException, InvalidProductCodeException,
                InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(null);
            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();

            ProductType productType = new ProductType();
            productType.setQuantity(10);
            productType.setProductDescription("Pomodoro");
            productType.setBarCode("6291041500213");
            productType.setLocation("Any");
            productType.setPricePerUnit(5.00);
            productType.setNote("Nota");

            ProductTypeDao.getInstance().save(productType);

            String productCode = "6291041500213";
            int amount = 5;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            Assert.assertFalse(
                    SaleTransactionManager.getInstance().deleteProductFromSale(transactionId, productCode, amount));

        }

        /*
         * nominal case , delete correctly(true)
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test
        public void test_14_deleteProductFromSale() throws InvalidTransactionIdException, InvalidProductCodeException,
                InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());
            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();

            ProductType productType = new ProductType();
            productType.setQuantity(10);
            productType.setProductDescription("Pomodoro");
            productType.setBarCode("6291041500213");
            productType.setLocation("Any");
            productType.setPricePerUnit(5.00);
            productType.setNote("Nota");

            ProductTypeDao.getInstance().save(productType);

            String productCode = "6291041500213";
            int amount = 5;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().startSaleTransaction();
            Assert.assertTrue(
                    SaleTransactionManager.getInstance().deleteProductFromSale(transactionId, productCode, amount));

        }

    }

    public static class applyDiscountRateToProduct {
        /*
         * invalid value for transaction id(< 0 ), expected
         * InvalidTransactionIdException
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test(expected = InvalidTransactionIdException.class)
        public void test_1_applyDiscountRateToProduct()
                throws InvalidTransactionIdException, InvalidProductCodeException, InvalidDiscountRateException,
                UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            Integer transactionId = -1;
            String productCode = "6291041500213";
            double discount = 0.5;

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());

            SaleTransactionDao.getInstance().save(sale);
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().applyDiscountRateToProduct(transactionId, productCode, discount);

        }

        /*
         * invalid value for transaction id(null ), expected
         * InvalidTransactionIdException
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test(expected = InvalidTransactionIdException.class)
        public void test_2_applyDiscountRateToProduct()
                throws InvalidTransactionIdException, InvalidProductCodeException, InvalidDiscountRateException,
                UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            Integer transactionId = null;
            String productCode = "6291041500213";
            double discount = 0.5;

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());

            SaleTransactionDao.getInstance().save(sale);
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().applyDiscountRateToProduct(transactionId, productCode, discount);

        }
        /*
         * invalid value for product code is null, expected InvalidProductCodeException
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidDiscountRateException
         */

        @Test(expected = InvalidProductCodeException.class)
        public void test_3_applyDiscountRateToProduct()
                throws InvalidTransactionIdException, InvalidProductCodeException, InvalidDiscountRateException,
                UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());

            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();
            String productCode = null;
            double discount = 0.5;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().applyDiscountRateToProduct(transactionId, productCode, discount);

        }

        /*
         * invalid value for product code is Empty, expected InvalidProductCodeException
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidDiscountRateException
         */

        @Test(expected = InvalidProductCodeException.class)
        public void test_4_applyDiscountRateToProduct()
                throws InvalidTransactionIdException, InvalidProductCodeException, InvalidDiscountRateException,
                UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());

            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();
            String productCode = "";
            double discount = 0.5;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().applyDiscountRateToProduct(transactionId, productCode, discount);

        }

        /*
         * invalid value for product code (wrong check digit), expected
         * InvalidProductCodeException
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test(expected = InvalidProductCodeException.class)
        public void test_5_deleteProductFromSale()
                throws InvalidTransactionIdException, InvalidProductCodeException, InvalidDiscountRateException,
                UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());

            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();
            String productCode = "1111111111115";
            double discount = 0.5;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().applyDiscountRateToProduct(transactionId, productCode, discount);

        }

        /*
         * invalid value for product code (not number), expected
         * InvalidProductCodeException
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test(expected = InvalidProductCodeException.class)
        public void test_6_deleteProductFromSale()
                throws InvalidTransactionIdException, InvalidProductCodeException, InvalidDiscountRateException,
                UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());

            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();
            String productCode = "11111111sd15";
            double discount = 0.5;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().applyDiscountRateToProduct(transactionId, productCode, discount);

        }

        /*
         * invalid value for product code (productCode.length >14), expected
         * InvalidProductCodeException
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test(expected = InvalidProductCodeException.class)
        public void test_7_deleteProductFromSale()
                throws InvalidTransactionIdException, InvalidProductCodeException, InvalidDiscountRateException,
                UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());

            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();
            String productCode = "1111111164323915";
            double discount = 0.5;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().applyDiscountRateToProduct(transactionId, productCode, discount);

        }

        /*
         * invalid value for product code (productCode.length < 12), expected
         * InvalidProductCodeException
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test(expected = InvalidProductCodeException.class)
        public void test_8_deleteProductFromSale()
                throws InvalidTransactionIdException, InvalidProductCodeException, InvalidDiscountRateException,
                UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());

            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();
            String productCode = "11163915";
            double discount = 0.5;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().applyDiscountRateToProduct(transactionId, productCode, discount);

        }

        /*
         * invalid product ( null ) because code is exact but prodcut not exist,
         * expected false
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidQuantityException
         */

        @Test
        public void test_9_deleteProductFromSale()
                throws InvalidTransactionIdException, InvalidProductCodeException, InvalidDiscountRateException,
                UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());

            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();
            String productCode = "8017596056993";
            double discount = 0.5;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            Assert.assertFalse(SaleTransactionManager.getInstance().applyDiscountRateToProduct(transactionId,
                    productCode, discount));
            ;

        }
        /*
         * invalid discount rate( > 1 ) , expected InvalidDiscountRateException
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidDiscountRateException
         */

        @Test(expected = InvalidDiscountRateException.class)
        public void test_10_applyDiscountRateToProduct()
                throws InvalidTransactionIdException, InvalidProductCodeException, InvalidDiscountRateException,
                UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());

            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();
            String productCode = "6291041500213";
            double discount = -0.1;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().applyDiscountRateToProduct(transactionId, productCode, discount);

        }

        /*
         * invalid discount rate( > 1 ) , expected InvalidDiscountRateException
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidDiscountRateException
         */

        @Test(expected = InvalidDiscountRateException.class)
        public void test_11_applyDiscountRateToProduct()
                throws InvalidTransactionIdException, InvalidProductCodeException, InvalidDiscountRateException,
                UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());

            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();
            String productCode = "6291041500213";
            double discount = 1.1;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().applyDiscountRateToProduct(transactionId, productCode, discount);

        }

        /*
         * transaction closed , expected false
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidDiscountRateException
         */

        @Test
        public void test_12_applyDiscountRateToProduct()
                throws InvalidTransactionIdException, InvalidProductCodeException, InvalidDiscountRateException,
                UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.CLOSED.name());

            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();
            String productCode = "6291041500213";
            double discount = 0.5;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            Assert.assertFalse(SaleTransactionManager.getInstance().applyDiscountRateToProduct(transactionId,
                    productCode, discount));

        }

        /*
         * transaction is null , expected false
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidDiscountRateException
         */

        @Test
        public void test_13_applyDiscountRateToProduct()
                throws InvalidTransactionIdException, InvalidProductCodeException, InvalidDiscountRateException,
                UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(null);

            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();
            String productCode = "6291041500213";
            double discount = 0.5;
            UserDao.getInstance().delete(admin);

            SaleTransactionDao.getInstance().delete(sale);

            Assert.assertFalse(SaleTransactionManager.getInstance().applyDiscountRateToProduct(transactionId,
                    productCode, discount));

        }

        /*
         * nominal case , expected true
         * 
         * @throws InvalidPasswordException
         * 
         * @throws InvalidUsernameException
         * 
         * @throws UnauthorizedException
         * 
         * @throws InvalidTransactionIdException
         * 
         * @throws InvalidProductCodeException
         * 
         * @throws InvalidDiscountRateException
         */

        @Test
        public void test_14_applyDiscountRateToProduct()
                throws InvalidTransactionIdException, InvalidProductCodeException, InvalidDiscountRateException,
                UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
            User admin = new User();
            admin.setUsername("Entry");
            admin.setPassword("Entry");
            admin.setRole(RoleTypeEnum.Administrator.name());

            UserDao.getInstance().save(admin);

            AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

            SaleTransaction sale = new SaleTransaction();
            sale.setTicketNumber(sale.getTicketNumber());
            sale.setStatus(SaleTransactionEnum.OPEN.name());

            SaleTransactionDao.getInstance().save(sale);
            Integer transactionId = sale.getTicketNumber();

            ProductType productType = new ProductType();
            productType.setQuantity(10);
            productType.setProductDescription("Pomodoro");
            productType.setBarCode("6291041500213");
            productType.setLocation("Any");
            productType.setPricePerUnit(5.00);
            productType.setNote("Nota");

            ProductTypeDao.getInstance().save(productType);

            String productCode = "6291041500213";
            double discount = 0.5;

            UserDao.getInstance().delete(admin);
            SaleTransactionDao.getInstance().delete(sale);

            SaleTransactionManager.getInstance().startSaleTransaction();
            Assert.assertTrue(SaleTransactionManager.getInstance().applyDiscountRateToProduct(transactionId,
                    productCode, discount));

        }

        public static class applyDiscountRateToSale {
            /*
             * invalid value for transaction id(null ), expected
             * InvalidTransactionIdException
             * 
             * @throws InvalidPasswordException
             * 
             * @throws InvalidUsernameException
             * 
             * @throws UnauthorizedException
             * 
             * @throws InvalidTransactionIdException
             * 
             * @throws InvalidDiscountRateException
             */

            @Test(expected = InvalidTransactionIdException.class)
            public void test_1_applyDiscountRateToSale()
                    throws InvalidTransactionIdException, InvalidDiscountRateException, UnauthorizedException,
                    InvalidUsernameException, InvalidPasswordException {
                User admin = new User();
                admin.setUsername("Entry");
                admin.setPassword("Entry");
                admin.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(admin);

                AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

                Integer transactionId = null;
                String productCode = "6291041500213";
                double discount = 0.5;

                SaleTransaction sale = new SaleTransaction();
                sale.setTicketNumber(sale.getTicketNumber());
                sale.setStatus(SaleTransactionEnum.OPEN.name());

                SaleTransactionDao.getInstance().save(sale);
                UserDao.getInstance().delete(admin);

                SaleTransactionDao.getInstance().delete(sale);

                SaleTransactionManager.getInstance().applyDiscountRateToSale(transactionId, discount);

            }

            /*
             * invalid value for transaction id(< 0 ), expected
             * InvalidTransactionIdException
             * 
             * @throws InvalidPasswordException
             * 
             * @throws InvalidUsernameException
             * 
             * @throws UnauthorizedException
             * 
             * @throws InvalidTransactionIdException
             * 
             * @throws InvalidDiscountRateException
             */

            @Test(expected = InvalidTransactionIdException.class)
            public void test_2_applyDiscountRateToSale()
                    throws InvalidTransactionIdException, InvalidDiscountRateException, UnauthorizedException,
                    InvalidUsernameException, InvalidPasswordException {
                User admin = new User();
                admin.setUsername("Entry");
                admin.setPassword("Entry");
                admin.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(admin);

                AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

                SaleTransaction sale = new SaleTransaction();
                sale.setTicketNumber(sale.getTicketNumber());
                sale.setStatus(SaleTransactionEnum.OPEN.name());

                Integer transactionId = sale.getTicketNumber();
                String productCode = "6291041500213";
                double discount = 0.5;
                SaleTransactionDao.getInstance().save(sale);
                UserDao.getInstance().delete(admin);

                SaleTransactionDao.getInstance().delete(sale);

                SaleTransactionManager.getInstance().applyDiscountRateToSale(transactionId, discount);

            }

            /*
             * invalid value for discount rate (< 0 ), expected
             * InvalidTransactionIdException
             * 
             * @throws InvalidPasswordException
             * 
             * @throws InvalidUsernameException
             * 
             * @throws UnauthorizedException
             * 
             * @throws InvalidTransactionIdException
             * 
             * @throws InvalidDiscountRateException
             */

            @Test(expected = InvalidDiscountRateException.class)
            public void test_3_applyDiscountRateToSale()
                    throws InvalidTransactionIdException, InvalidDiscountRateException, UnauthorizedException,
                    InvalidUsernameException, InvalidPasswordException {
                User admin = new User();
                admin.setUsername("Entry");
                admin.setPassword("Entry");
                admin.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(admin);

                AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

                SaleTransaction sale = new SaleTransaction();
                sale.setTicketNumber(sale.getTicketNumber());
                sale.setStatus(SaleTransactionEnum.OPEN.name());

                SaleTransactionDao.getInstance().save(sale);
                Integer transactionId = sale.getTicketNumber();
                double discount = -0.1;
                UserDao.getInstance().delete(admin);

                SaleTransactionDao.getInstance().delete(sale);

                SaleTransactionManager.getInstance().applyDiscountRateToSale(transactionId, discount);

            }

            /*
             * invalid value for discount rate (> 1 ), expected
             * InvalidTransactionIdException
             * 
             * @throws InvalidPasswordException
             * 
             * @throws InvalidUsernameException
             * 
             * @throws UnauthorizedException
             * 
             * @throws InvalidTransactionIdException
             * 
             * @throws InvalidDiscountRateException
             */

            @Test(expected = InvalidDiscountRateException.class)
            public void test_4_applyDiscountRateToSale()
                    throws InvalidTransactionIdException, InvalidDiscountRateException, UnauthorizedException,
                    InvalidUsernameException, InvalidPasswordException {
                User admin = new User();
                admin.setUsername("Entry");
                admin.setPassword("Entry");
                admin.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(admin);

                AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

                SaleTransaction sale = new SaleTransaction();
                sale.setTicketNumber(sale.getTicketNumber());
                sale.setStatus(SaleTransactionEnum.OPEN.name());

                SaleTransactionDao.getInstance().save(sale);
                Integer transactionId = sale.getTicketNumber();
                double discount = 1.1;
                UserDao.getInstance().delete(admin);

                SaleTransactionDao.getInstance().delete(sale);

                SaleTransactionManager.getInstance().applyDiscountRateToSale(transactionId, discount);

            }

            /*
             * transaction closed , expected false
             * 
             * @throws InvalidPasswordException
             * 
             * @throws InvalidUsernameException
             * 
             * @throws UnauthorizedException
             * 
             * @throws InvalidTransactionIdException
             * 
             * @throws InvalidDiscountRateException
             */

            @Test
            public void test_5_applyDiscountRateToSale()
                    throws InvalidTransactionIdException, InvalidDiscountRateException, UnauthorizedException,
                    InvalidUsernameException, InvalidPasswordException {
                User admin = new User();
                admin.setUsername("Entry");
                admin.setPassword("Entry");
                admin.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(admin);

                AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

                SaleTransaction sale = new SaleTransaction();
                sale.setTicketNumber(sale.getTicketNumber());
                sale.setStatus(SaleTransactionEnum.CLOSED.name());

                SaleTransactionDao.getInstance().save(sale);
                Integer transactionId = sale.getTicketNumber();
                double discount = 0.5;
                UserDao.getInstance().delete(admin);

                SaleTransactionDao.getInstance().delete(sale);

                Assert.assertFalse(
                        SaleTransactionManager.getInstance().applyDiscountRateToSale(transactionId, discount));

            }

            /*
             * transaction is null , expected false
             * 
             * @throws InvalidPasswordException
             * 
             * @throws InvalidUsernameException
             * 
             * @throws UnauthorizedException
             * 
             * @throws InvalidTransactionIdException
             * 
             * @throws InvalidDiscountRateException
             */

            @Test
            public void test_6_applyDiscountRateToSale()
                    throws InvalidTransactionIdException, InvalidDiscountRateException, UnauthorizedException,
                    InvalidUsernameException, InvalidPasswordException {
                User admin = new User();
                admin.setUsername("Entry");
                admin.setPassword("Entry");
                admin.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(admin);

                AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

                SaleTransaction sale = new SaleTransaction();
                sale.setTicketNumber(sale.getTicketNumber());
                sale.setStatus(null);

                SaleTransactionDao.getInstance().save(sale);
                Integer transactionId = sale.getTicketNumber();
                double discount = 0.5;
                UserDao.getInstance().delete(admin);

                SaleTransactionDao.getInstance().delete(sale);

                Assert.assertFalse(
                        SaleTransactionManager.getInstance().applyDiscountRateToSale(transactionId, discount));

            }

            /*
             * nominal case , expected true
             * 
             * @throws InvalidPasswordException
             * 
             * @throws InvalidUsernameException
             * 
             * @throws UnauthorizedException
             * 
             * @throws InvalidTransactionIdException
             * 
             * @throws InvalidDiscountRateException
             */

            @Test
            public void test_7_applyDiscountRateToSale()
                    throws InvalidTransactionIdException, InvalidDiscountRateException, UnauthorizedException,
                    InvalidUsernameException, InvalidPasswordException {
                User admin = new User();
                admin.setUsername("Entry");
                admin.setPassword("Entry");
                admin.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(admin);

                AuthenticationManager.getInstance().login(admin.getUsername(), admin.getPassword());

                SaleTransaction sale = new SaleTransaction();
                sale.setTicketNumber(sale.getTicketNumber());
                sale.setStatus(SaleTransactionEnum.CLOSED.name());

                SaleTransactionDao.getInstance().save(sale);
                Integer transactionId = sale.getTicketNumber();
                double discount = 0.5;
                UserDao.getInstance().delete(admin);

                SaleTransactionDao.getInstance().delete(sale);

                SaleTransactionManager.getInstance().startSaleTransaction();
                Assert.assertTrue(
                        SaleTransactionManager.getInstance().applyDiscountRateToSale(transactionId, discount));

            }

        }

    }

    public static class TestCustomerManager {

        public static class testDefineCustomer {

            @Rule
            public final ExpectedException exception = ExpectedException.none();

            @After
            public void resetDb() throws UnauthorizedException, InvalidCustomerIdException, InvalidPasswordException,
                    InvalidUsernameException {

                AuthenticationManager.getInstance().logout();

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                List<it.polito.ezshop.data.Customer> customers = CustomerManager.getInstance().getAllCustomers();
                for (it.polito.ezshop.data.Customer customer : customers) {
                    CustomerDao.getInstance().delete(CustomerManager.getInstance().getCustomer(customer.getId()));
                }

                List<it.polito.ezshop.data.User> users = UserManager.getInstance().getAllUsers();
                for (it.polito.ezshop.data.User deletedUser : users) {
                    UserDao.getInstance().delete(UserDao.getInstance().findById(deletedUser.getId()));
                }

                List<CustomerCard> cards = CustomerCardManager.getInstance().getAllCard();
                for (CustomerCard card : cards) {
                    CustomerCardDao.getInstance().delete(card);
                }
                AuthenticationManager.getInstance().logout();
            }

            @Test
            public void test_1_defineCustomer() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerNameException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                exception.expect(InvalidCustomerNameException.class);
                CustomerManager.getInstance().defineCustomer("");
            }

            /*
             * invalid customer name, expected InvalidCustomerNameException
             * 
             * @throws UnauthorizedException
             * 
             * @throws InvalidPasswordException
             * 
             * @throws InvalidUsernameException
             * 
             * @throws InvalidCustomerNameException
             */

            @Test
            public void test_2_defineCustomer() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerNameException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                exception.expect(InvalidCustomerNameException.class);
                CustomerManager.getInstance().defineCustomer(null);
            }

            /*
             * invalid customer name, expected InvalidCustomerNameException
             * 
             * @throws UnauthorizedException
             * 
             * @throws InvalidPasswordException
             * 
             * @throws InvalidUsernameException
             * 
             * @throws InvalidCustomerNameException
             */

            @Test
            public void test_3_defineCustomer() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerNameException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                CustomerManager.getInstance().defineCustomer("Genoveffa");

                exception.expect(InvalidCustomerNameException.class);
                CustomerManager.getInstance().defineCustomer("Genoveffa");
            }

            /*
             * invalid role, expected UnauthorizedException
             * 
             * @throws UnauthorizedException
             * 
             * @throws InvalidPasswordException
             * 
             * @throws InvalidUsernameException
             * 
             * @throws InvalidCustomerNameException
             */
            @Test
            public void test_4_defineCustomer() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerNameException {

                User badUser = new User();
                badUser.setUsername("baduser");
                badUser.setPassword("baduser");
                badUser.setRole("ROLE");

                UserDao.getInstance().save(badUser);

                AuthenticationManager.getInstance().login(badUser.getUsername(), badUser.getPassword());

                exception.expect(UnauthorizedException.class);
                CustomerManager.getInstance().defineCustomer("Genoveffa");
            }

            /*
             * nominal case
             * 
             * @throws UnauthorizedException
             * 
             * @throws InvalidPasswordException
             * 
             * @throws InvalidUsernameException
             * 
             * @throws InvalidCustomerNameException
             */

            @Test
            public void test_5_defineCustomer() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerNameException, InvalidCustomerIdException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                String customerName = "Genoveffa";
                Integer points = 0;

                Integer id = CustomerManager.getInstance().defineCustomer(customerName);
                it.polito.ezshop.model.Customer test = CustomerManager.getInstance().getCustomer(id);

                CustomerDao.getInstance().save(test);

                Assert.assertNotNull(test);
                Assert.assertEquals(customerName, test.getCustomerName());
                Assert.assertNull(test.getCustomerCard());
                Assert.assertEquals(points, test.getPoints());
            }
        }

        public static class testDeleteCustomer {

            @Rule
            public final ExpectedException exception = ExpectedException.none();

            @After
            public void resetDb() throws UnauthorizedException, InvalidCustomerIdException, InvalidPasswordException,
                    InvalidUsernameException {

                AuthenticationManager.getInstance().logout();

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                List<it.polito.ezshop.data.Customer> customers = CustomerManager.getInstance().getAllCustomers();
                for (it.polito.ezshop.data.Customer customer : customers) {
                    CustomerDao.getInstance().delete(CustomerManager.getInstance().getCustomer(customer.getId()));
                }

                List<it.polito.ezshop.data.User> users = UserManager.getInstance().getAllUsers();
                for (it.polito.ezshop.data.User deletedUser : users) {
                    UserDao.getInstance().delete(UserDao.getInstance().findById(deletedUser.getId()));
                }

                List<CustomerCard> cards = CustomerCardManager.getInstance().getAllCard();
                for (CustomerCard card : cards) {
                    CustomerCardDao.getInstance().delete(card);
                }
                AuthenticationManager.getInstance().logout();
            }

            @Test
            public void test_1_deleteCustomer() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerIdException {

                User badUser = new User();
                badUser.setUsername("baduser");
                badUser.setPassword("baduser");
                badUser.setRole("ROLE");

                UserDao.getInstance().save(badUser);

                AuthenticationManager.getInstance().login(badUser.getUsername(), badUser.getPassword());

                Customer customer = new Customer();
                CustomerDao.getInstance().save(customer);

                exception.expect(UnauthorizedException.class);
                CustomerManager.getInstance().deleteCustomer(customer.getId());
            }

            @Test
            public void test_2_deleteCustomer() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerIdException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());
                Customer customer = new Customer();
                CustomerDao.getInstance().save(customer);

                exception.expect(InvalidCustomerIdException.class);
                CustomerManager.getInstance().deleteCustomer(null);
            }

            @Test
            public void test_3_deleteCustomer() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerIdException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());
                Customer customer = new Customer();
                CustomerDao.getInstance().save(customer);

                exception.expect(InvalidCustomerIdException.class);
                CustomerManager.getInstance().deleteCustomer(-1);
            }

            @Test
            public void test_4_deleteCustomer() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerIdException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());
                Customer customer = new Customer();
                CustomerDao.getInstance().save(customer);

                Integer id = customer.getId();

                CustomerManager.getInstance().deleteCustomer(id);

                customer = CustomerDao.getInstance().findById(id);
                Assert.assertNull(customer);
            }
        }

        public static class testModifyCustomer {

            @Rule
            public final ExpectedException exception = ExpectedException.none();

            @After
            public void resetDb() throws UnauthorizedException, InvalidCustomerIdException, InvalidPasswordException,
                    InvalidUsernameException {

                AuthenticationManager.getInstance().logout();

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                List<it.polito.ezshop.data.Customer> customers = CustomerManager.getInstance().getAllCustomers();
                for (it.polito.ezshop.data.Customer customer : customers) {
                    CustomerDao.getInstance().delete(CustomerManager.getInstance().getCustomer(customer.getId()));
                }

                List<it.polito.ezshop.data.User> users = UserManager.getInstance().getAllUsers();
                for (it.polito.ezshop.data.User deletedUser : users) {
                    UserDao.getInstance().delete(UserDao.getInstance().findById(deletedUser.getId()));
                }

                List<CustomerCard> cards = CustomerCardManager.getInstance().getAllCard();
                for (CustomerCard card : cards) {
                    CustomerCardDao.getInstance().delete(card);
                }
                AuthenticationManager.getInstance().logout();
            }

            @Test
            public void test_1_modifyCustomer()
                    throws UnauthorizedException, InvalidPasswordException, InvalidUsernameException,
                    InvalidCustomerNameException, InvalidCustomerIdException, InvalidCustomerCardException {

                User badUser = new User();
                badUser.setUsername("baduser");
                badUser.setPassword("baduser");
                badUser.setRole("ROLE");

                UserDao.getInstance().save(badUser);

                AuthenticationManager.getInstance().login(badUser.getUsername(), badUser.getPassword());

                Integer id = 1;
                String customerName = "Genoveffa";
                String customerCard = null;
                Integer points = 0;

                Customer customer = new Customer();
                customer.setCustomerName(customerName);
                customer.setId(id);
                customer.setPoints(points);
                customer.setCustomerCard(customerCard);

                CustomerDao.getInstance().save(customer);

                exception.expect(UnauthorizedException.class);
                CustomerManager.getInstance().modifyCustomer(1, customerName, "");
            }

            /*
             * invalid new customer name, expected InvalidCustomerNameException
             * 
             * @throws UnauthorizedException
             * 
             * @throws InvalidPasswordException
             * 
             * @throws InvalidUsernameException
             * 
             * @throws InvalidCustomerNameException
             */

            @Test
            public void test_3_modifyCustomer()
                    throws UnauthorizedException, InvalidPasswordException, InvalidUsernameException,
                    InvalidCustomerNameException, InvalidCustomerIdException, InvalidCustomerCardException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                String customerName = "Genoveffa";
                String customerCard = null;
                Integer points = 0;

                String badNewCustomerName = null;

                Customer customer = new Customer();
                customer.setCustomerName(customerName);
                customer.setPoints(points);
                customer.setCustomerCard(customerCard);

                CustomerDao.getInstance().save(customer);

                exception.expect(InvalidCustomerNameException.class);
                CustomerManager.getInstance().modifyCustomer(customer.getId(), badNewCustomerName, "");
            }

            /*
             * invalid provided customer id, expected InvalidCustomerIdException
             * 
             * @throws UnauthorizedException
             * 
             * @throws InvalidPasswordException
             * 
             * @throws InvalidUsernameException
             * 
             * @throws InvalidCustomerNameException
             */

            @Test
            public void test_4_modifyCustomer()
                    throws UnauthorizedException, InvalidPasswordException, InvalidUsernameException,
                    InvalidCustomerNameException, InvalidCustomerIdException, InvalidCustomerCardException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                Integer id = 1;
                String customerName = "Genoveffa";
                String customerCard = null;
                Integer points = 0;

                Integer badId = null;

                Customer customer = new Customer();
                customer.setCustomerName(customerName);
                customer.setId(id);
                customer.setPoints(points);
                customer.setCustomerCard(customerCard);

                CustomerDao.getInstance().save(customer);

                exception.expect(InvalidCustomerIdException.class);
                CustomerManager.getInstance().modifyCustomer(badId, customerName, "");
            }

            @Test
            public void test_5_modifyCustomer()
                    throws UnauthorizedException, InvalidPasswordException, InvalidUsernameException,
                    InvalidCustomerNameException, InvalidCustomerIdException, InvalidCustomerCardException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                Integer id = 1;
                String customerName = "Genoveffa";
                String customerCard = null;
                Integer points = 0;

                Integer badId = -1;

                Customer customer = new Customer();
                customer.setCustomerName(customerName);
                customer.setId(id);
                customer.setPoints(points);
                customer.setCustomerCard(customerCard);

                CustomerDao.getInstance().save(customer);

                exception.expect(InvalidCustomerIdException.class);
                CustomerManager.getInstance().modifyCustomer(badId, customerName, "");
            }

            @Test
            public void test_2_modifyCustomer()
                    throws UnauthorizedException, InvalidPasswordException, InvalidUsernameException,
                    InvalidCustomerNameException, InvalidCustomerIdException, InvalidCustomerCardException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                String customerName = "Salomone";
                String customerCard = null;
                Integer points = 0;

                Customer customer = new Customer();
                customer.setCustomerName(customerName);
                customer.setPoints(points);
                customer.setCustomerCard(customerCard);

                CustomerDao.getInstance().save(customer);

                String customerName2 = "Genoveffa";

                Customer customer2 = new Customer();
                customer2.setCustomerName(customerName2);
                customer2.setPoints(points);
                customer2.setCustomerCard(customerCard);

                CustomerDao.getInstance().save(customer2);

                exception.expect(InvalidCustomerNameException.class);
                CustomerManager.getInstance().modifyCustomer(customer.getId(), customerName2, "");
            }

            @Test
            public void test_8_modifyCustomer()
                    throws UnauthorizedException, InvalidPasswordException, InvalidUsernameException,
                    InvalidCustomerNameException, InvalidCustomerIdException, InvalidCustomerCardException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                String customerName = "Genoveffa";
                String customerCard = null;
                Integer points = 0;

                Customer customer = new Customer();
                customer.setCustomerName(customerName);
                customer.setPoints(points);
                customer.setCustomerCard(customerCard);

                String newCustomerName = "Geno";
                String newCard = "";

                CustomerDao.getInstance().save(customer);

                Assert.assertEquals(customerName, customer.getCustomerName());
                Assert.assertNull(customer.getCustomerCard());
                Assert.assertEquals(points, customer.getPoints());

                CustomerManager.getInstance().modifyCustomer(customer.getId(), newCustomerName, newCard);

                customer = CustomerManager.getInstance().getCustomer(customer.getId());

                Assert.assertEquals(newCustomerName, customer.getCustomerName());
                Assert.assertNull(customer.getCustomerCard());
                Assert.assertEquals(points, customer.getPoints());
            }

            @Test
            public void test_7_modifyCustomer()
                    throws UnauthorizedException, InvalidPasswordException, InvalidUsernameException,
                    InvalidCustomerNameException, InvalidCustomerIdException, InvalidCustomerCardException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                String customerName = "Genoveffa";
                String customerCard = null;
                Integer points = 0;

                Customer customer = new Customer();
                customer.setCustomerName(customerName);
                customer.setPoints(points);
                customer.setCustomerCard(customerCard);

                String newCustomerName = "";
                String newCard = "";

                CustomerDao.getInstance().save(customer);

                Assert.assertEquals(customerName, customer.getCustomerName());
                Assert.assertNull(customer.getCustomerCard());
                Assert.assertEquals(points, customer.getPoints());

                CustomerManager.getInstance().modifyCustomer(customer.getId(), newCustomerName, newCard);

                customer = CustomerManager.getInstance().getCustomer(customer.getId());

                Assert.assertEquals(customerName, customer.getCustomerName());
            }

            @Test
            public void test_9_modifyCustomer()
                    throws UnauthorizedException, InvalidPasswordException, InvalidUsernameException,
                    InvalidCustomerNameException, InvalidCustomerIdException, InvalidCustomerCardException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                String customerName = "Genoveffa";
                Integer points = 0;

                Customer customer = new Customer();
                String cardCode = CustomerCardManager.getInstance().createCard();

                customer.setCustomerName(customerName);
                customer.setPoints(points);
                customer.setCustomerCard(null);

                String newCustomerName = customerName;

                CustomerDao.getInstance().save(customer);

                CustomerManager.getInstance().modifyCustomer(customer.getId(), newCustomerName, cardCode);

                customer = CustomerManager.getInstance().getCustomer(customer.getId());

                Assert.assertEquals(customerName, customer.getCustomerName());
                Assert.assertEquals(cardCode, customer.getCustomerCard());
                Assert.assertEquals(points, customer.getPoints());
            }

            @Test
            public void test_11_modifyCustomer()
                    throws UnauthorizedException, InvalidPasswordException, InvalidUsernameException,
                    InvalidCustomerNameException, InvalidCustomerIdException, InvalidCustomerCardException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                String customerName = "Genoveffa";
                Integer points = 0;

                Customer customer = new Customer();
                String cardCode = CustomerCardManager.getInstance().createCard();

                customer.setCustomerName(customerName);
                customer.setPoints(points);
                customer.setCustomerCard(null);

                String newCustomerName = customerName;

                CustomerDao.getInstance().save(customer);

                CustomerManager.getInstance().modifyCustomer(customer.getId(), newCustomerName, cardCode);

                String newCard = "";

                CustomerManager.getInstance().modifyCustomer(customer.getId(), newCustomerName, newCard);

                customer = CustomerManager.getInstance().getCustomer(customer.getId());

                Assert.assertEquals(customerName, customer.getCustomerName());
                Assert.assertNull(customer.getCustomerCard());
                Assert.assertEquals(points, customer.getPoints());
            }
        }

        public static class testAttachCardToCustomer {

            @Rule
            public final ExpectedException exception = ExpectedException.none();

            @After
            public void resetDb() throws UnauthorizedException, InvalidCustomerIdException, InvalidPasswordException,
                    InvalidUsernameException {

                AuthenticationManager.getInstance().logout();

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                List<it.polito.ezshop.data.Customer> customers = CustomerManager.getInstance().getAllCustomers();
                for (it.polito.ezshop.data.Customer customer : customers) {
                    CustomerDao.getInstance().delete(CustomerManager.getInstance().getCustomer(customer.getId()));
                }

                List<it.polito.ezshop.data.User> users = UserManager.getInstance().getAllUsers();
                for (it.polito.ezshop.data.User deletedUser : users) {
                    UserDao.getInstance().delete(UserDao.getInstance().findById(deletedUser.getId()));
                }

                List<CustomerCard> cards = CustomerCardManager.getInstance().getAllCard();
                for (CustomerCard card : cards) {
                    CustomerCardDao.getInstance().delete(card);
                }
                AuthenticationManager.getInstance().logout();
            }

            @Test
            public void test_1_attachCardToCustomer() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerIdException, InvalidCustomerCardException {

                User badUser = new User();
                badUser.setUsername("baduser");
                badUser.setPassword("baduser");
                badUser.setRole("ROLE");

                UserDao.getInstance().save(badUser);

                AuthenticationManager.getInstance().login(badUser.getUsername(), badUser.getPassword());

                String customerName = "Genoveffa";
                Integer points = 0;

                Customer customer = new Customer();
                customer.setCustomerName(customerName);
                customer.setPoints(points);
                customer.setCustomerCard(null);

                CustomerDao.getInstance().save(customer);

                CustomerCard card = new CustomerCard();

                CustomerCardDao.getInstance().save(card);

                exception.expect(UnauthorizedException.class);
                CustomerManager.getInstance().attachCardToCustomer(card.getCardCode(), customer.getId());
            }

            @Test
            public void test_2_attachCardToCustomer() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerIdException, InvalidCustomerCardException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                String customerName = "Genoveffa";
                Integer points = 0;

                Customer customer = new Customer();
                customer.setCustomerName(customerName);
                customer.setPoints(points);
                customer.setCustomerCard(null);

                CustomerDao.getInstance().save(customer);

                CustomerCard card = new CustomerCard();

                CustomerCardDao.getInstance().save(card);

                exception.expect(InvalidCustomerIdException.class);
                CustomerManager.getInstance().attachCardToCustomer(card.getCardCode(), null);
            }

            @Test
            public void test_3_attachCardToCustomer() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerIdException, InvalidCustomerCardException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                String customerName = "Genoveffa";
                Integer points = 0;

                Customer customer = new Customer();
                customer.setCustomerName(customerName);
                customer.setPoints(points);
                customer.setCustomerCard(null);

                CustomerDao.getInstance().save(customer);

                CustomerCard card = new CustomerCard();
                card.setCardCode("1000000001");

                CustomerCardDao.getInstance().save(card);

                exception.expect(InvalidCustomerIdException.class);
                CustomerManager.getInstance().attachCardToCustomer(card.getCardCode(), -1);
            }

            @Test
            public void test_4_attachCardToCustomer() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerIdException, InvalidCustomerCardException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                String customerName = "Genoveffa";
                Integer points = 0;

                Customer customer = new Customer();
                customer.setCustomerName(customerName);
                customer.setPoints(points);
                customer.setCustomerCard(null);

                CustomerDao.getInstance().save(customer);

                CustomerCard card = new CustomerCard();

                CustomerCardDao.getInstance().save(card);

                exception.expect(InvalidCustomerCardException.class);
                CustomerManager.getInstance().attachCardToCustomer("1", customer.getId());
            }

            @Test
            public void test_5_attachCardToCustomer() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerIdException, InvalidCustomerCardException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                String customerName = "Genoveffa";
                Integer points = 0;

                Customer customer = new Customer();
                customer.setCustomerName(customerName);
                customer.setPoints(points);
                customer.setCustomerCard(null);

                CustomerDao.getInstance().save(customer);

                CustomerCard card = new CustomerCard();

                CustomerCardDao.getInstance().save(card);

                exception.expect(InvalidCustomerCardException.class);
                CustomerManager.getInstance().attachCardToCustomer("", customer.getId());
            }

            @Test
            public void test_6_attachCardToCustomer() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerIdException, InvalidCustomerCardException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                String customerName = "Genoveffa";
                Integer points = 0;

                Customer customer = new Customer();
                customer.setCustomerName(customerName);
                customer.setPoints(points);
                customer.setCustomerCard(null);

                CustomerDao.getInstance().save(customer);

                CustomerCard card = new CustomerCard();

                CustomerCardDao.getInstance().save(card);

                exception.expect(InvalidCustomerCardException.class);
                CustomerManager.getInstance().attachCardToCustomer(null, customer.getId());
            }

            @Test
            public void test_7_attachCardToCustomer() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerIdException, InvalidCustomerCardException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                String customerName = "Genoveffa";
                Integer points = 0;

                Customer customer = new Customer();
                customer.setCustomerName(customerName);
                customer.setPoints(points);
                customer.setCustomerCard(null);

                CustomerDao.getInstance().save(customer);

                CustomerCard card = new CustomerCard();
                card.setCardCode("1000000001");

                CustomerCardDao.getInstance().save(card);

                CustomerManager.getInstance().attachCardToCustomer(card.getCardCode(), customer.getId());
                card = CustomerCardManager.getInstance().findByCode(card.getCardCode());
                customer = CustomerManager.getInstance().getCustomer(customer.getId());

                Assert.assertEquals(customer.getCustomerCard(), card.getCardCode());
            }
        }

        public static class testModifyPointsOnCard {

            @Rule
            public final ExpectedException exception = ExpectedException.none();

            @After
            public void resetDb() throws UnauthorizedException, InvalidCustomerIdException, InvalidPasswordException,
                    InvalidUsernameException {

                AuthenticationManager.getInstance().logout();

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                List<it.polito.ezshop.data.Customer> customers = CustomerManager.getInstance().getAllCustomers();
                for (it.polito.ezshop.data.Customer customer : customers) {
                    CustomerDao.getInstance().delete(CustomerManager.getInstance().getCustomer(customer.getId()));
                }

                List<it.polito.ezshop.data.User> users = UserManager.getInstance().getAllUsers();
                for (it.polito.ezshop.data.User deletedUser : users) {
                    UserDao.getInstance().delete(UserDao.getInstance().findById(deletedUser.getId()));
                }

                List<CustomerCard> cards = CustomerCardManager.getInstance().getAllCard();
                for (CustomerCard card : cards) {
                    CustomerCardDao.getInstance().delete(card);
                }
                AuthenticationManager.getInstance().logout();
            }

            @Test
            public void test_1_modifyPointsOnCard() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerCardException {

                User badUser = new User();
                badUser.setUsername("baduser");
                badUser.setPassword("baduser");
                badUser.setRole("ROLE");

                UserDao.getInstance().save(badUser);

                AuthenticationManager.getInstance().login(badUser.getUsername(), badUser.getPassword());

                String cardCode = "1000000001";
                Integer id = 1;
                String customerName = "Genoveffa";
                CustomerCard card = new CustomerCard();
                card.setCardCode(cardCode);
                card.setCardPoints(0);
                Integer points = 0;

                Customer customer = new Customer();
                customer.setCustomerName(customerName);
                customer.setId(id);
                customer.setPoints(points);
                customer.setCustomerCard(cardCode);

                CustomerCardDao.getInstance().save(card);
                CustomerDao.getInstance().save(customer);

                exception.expect(UnauthorizedException.class);
                CustomerManager.getInstance().modifyPointsOnCard(card.getCardCode(), 5);
            }

            /*
             * invalid customer card code, expected InvalidCustomerCardException
             * 
             * @throws UnauthorizedException
             * 
             * @throws InvalidPasswordException
             * 
             * @throws InvalidUsernameException
             * 
             * @throws InvalidCustomerNameException
             * 
             * @throws InvalidCustomerCardException
             */

            @Test
            public void test_2_modifyPointsOnCard() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerCardException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                String customerName = "Genoveffa";
                Integer points = 0;

                Customer customer = new Customer();
                String cardCode = null;

                customer.setCustomerName(customerName);
                customer.setPoints(points);
                customer.setCustomerCard(cardCode);

                CustomerDao.getInstance().save(customer);

                exception.expect(InvalidCustomerCardException.class);
                CustomerManager.getInstance().modifyPointsOnCard(cardCode, 5);
            }

            /*
             * invalid customer card code, expected InvalidCustomerCardException
             * 
             * @throws UnauthorizedException
             * 
             * @throws InvalidPasswordException
             * 
             * @throws InvalidUsernameException
             * 
             * @throws InvalidCustomerNameException
             * 
             * @throws InvalidCustomerCardException
             */

            @Test
            public void test_3_modifyPointsOnCard() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerCardException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                String customerName = "Genoveffa";
                Integer points = 0;

                Customer customer = new Customer();
                String cardCode = "1";

                customer.setCustomerName(customerName);
                customer.setPoints(points);
                customer.setCustomerCard(cardCode);

                CustomerDao.getInstance().save(customer);

                exception.expect(InvalidCustomerCardException.class);
                CustomerManager.getInstance().modifyPointsOnCard(cardCode, 5);
            }

            /*
             * invalid customer card code, expected InvalidCustomerCardException
             * 
             * @throws UnauthorizedException
             * 
             * @throws InvalidPasswordException
             * 
             * @throws InvalidUsernameException
             * 
             * @throws InvalidCustomerNameException
             * 
             * @throws InvalidCustomerCardException
             */

            @Test
            public void test_4_modifyPointsOnCard() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerCardException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                String customerName = "Genoveffa";
                Integer points = 0;

                Customer customer = new Customer();
                String cardCode = "1000000001";

                customer.setCustomerName(customerName);
                customer.setPoints(points);
                customer.setCustomerCard(cardCode);

                CustomerDao.getInstance().save(customer);

                exception.expect(InvalidCustomerCardException.class);
                CustomerManager.getInstance().modifyPointsOnCard(cardCode, 5);
            }

            /*
             * invalid number of added points, expected InvalidCustomerCardException
             * 
             * @throws UnauthorizedException
             * 
             * @throws InvalidPasswordException
             * 
             * @throws InvalidUsernameException
             * 
             * @throws InvalidCustomerNameException
             * 
             * @throws InvalidCustomerCardException
             */

            @Test
            public void test_5_modifyPointsOnCard() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerCardException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                String customerName = "Genoveffa";
                Integer points = 0;

                Customer customer = new Customer();
                String cardCode = CustomerCardManager.getInstance().createCard();

                customer.setCustomerName(customerName);
                customer.setPoints(points);
                customer.setCustomerCard(cardCode);

                CustomerDao.getInstance().save(customer);

                int pointsToBeAdded = -1;

                exception.expect(InvalidCustomerCardException.class);
                CustomerManager.getInstance().modifyPointsOnCard(cardCode, pointsToBeAdded);

            }

            @Test
            public void test_7_modifyPointsOnCard() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerCardException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                String customerName = "Genoveffa";
                Integer points = 0;

                Customer customer = new Customer();
                String cardCode = CustomerCardManager.getInstance().createCard();

                customer.setCustomerName(customerName);
                customer.setPoints(points);
                customer.setCustomerCard(cardCode);

                CustomerDao.getInstance().save(customer);

                int pointsToBeAdded = -1;

                exception.expect(InvalidCustomerCardException.class);
                CustomerManager.getInstance().modifyPointsOnCard("", pointsToBeAdded);

            }

            /*
             * nominal case, number of points added and updated successfully
             * 
             * @throws UnauthorizedException
             * 
             * @throws InvalidPasswordException
             * 
             * @throws InvalidUsernameException
             * 
             * @throws InvalidCustomerNameException
             * 
             * @throws InvalidCustomerCardException
             */

            @Test
            public void test_6_modifyPointsOnCard() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerCardException, InvalidCustomerIdException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                String customerName = "Genoveffa";
                int points = 0;

                Customer customer = new Customer();
                String cardCode = CustomerCardManager.getInstance().createCard();
                CustomerCard card = CustomerCardManager.getInstance().findByCode(cardCode);

                customer.setCustomerName(customerName);
                customer.setPoints(points);
                customer.setCustomerCard(cardCode);

                CustomerDao.getInstance().save(customer);

                card.setCustomerId(customer.getId());
                card.setCardPoints(0);

                CustomerCardDao.getInstance().save(card);

                int pointsToBeAdded = 5;

                CustomerManager.getInstance().modifyPointsOnCard(cardCode, pointsToBeAdded);

                customer = CustomerManager.getInstance().getCustomer(customer.getId());
                card = CustomerCardManager.getInstance().findByCode(cardCode);

                Integer expectedPoints = points + pointsToBeAdded;

                Assert.assertEquals(expectedPoints, customer.getPoints());
                Assert.assertEquals(card.getCardPoints(), customer.getPoints());

            }
        }
    }

    public static class TestCustomerCardManager {

        public static class testCreateCard {

            @Rule
            public final ExpectedException exception = ExpectedException.none();

            @After
            public void resetDb() throws UnauthorizedException, InvalidCustomerIdException, InvalidPasswordException,
                    InvalidUsernameException {

                AuthenticationManager.getInstance().logout();

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                List<it.polito.ezshop.data.Customer> customers = CustomerManager.getInstance().getAllCustomers();
                for (it.polito.ezshop.data.Customer customer : customers) {
                    CustomerDao.getInstance().delete(CustomerManager.getInstance().getCustomer(customer.getId()));
                }

                List<it.polito.ezshop.data.User> users = UserManager.getInstance().getAllUsers();
                for (it.polito.ezshop.data.User deletedUser : users) {
                    UserDao.getInstance().delete(UserDao.getInstance().findById(deletedUser.getId()));
                }

                List<CustomerCard> cards = CustomerCardManager.getInstance().getAllCard();
                for (CustomerCard card : cards) {
                    CustomerCardDao.getInstance().delete(card);
                }
                AuthenticationManager.getInstance().logout();
            }

            @Test
            public void test_1_createCard()
                    throws UnauthorizedException, InvalidPasswordException, InvalidUsernameException {

                User badUser = new User();
                badUser.setUsername("baduser");
                badUser.setPassword("baduser");
                badUser.setRole("ROLE");

                UserDao.getInstance().save(badUser);

                AuthenticationManager.getInstance().login(badUser.getUsername(), badUser.getPassword());

                exception.expect(UnauthorizedException.class);
                CustomerCardManager.getInstance().createCard();
            }

            @Test
            public void test_2_createCard()
                    throws UnauthorizedException, InvalidPasswordException, InvalidUsernameException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                String cardCode = CustomerCardManager.getInstance().createCard();
                CustomerCard card = CustomerCardManager.getInstance().findByCode(cardCode);

                Assert.assertNotNull(card);
                Assert.assertEquals(cardCode, card.getCardCode());
                Assert.assertEquals(10, card.getCardCode().length());
            }
        }

        public static class testDeleteCard {

            @Rule
            public final ExpectedException exception = ExpectedException.none();

            @After
            public void resetDb() throws UnauthorizedException, InvalidCustomerIdException, InvalidPasswordException,
                    InvalidUsernameException {

                AuthenticationManager.getInstance().logout();

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                List<it.polito.ezshop.data.Customer> customers = CustomerManager.getInstance().getAllCustomers();
                for (it.polito.ezshop.data.Customer customer : customers) {
                    CustomerDao.getInstance().delete(CustomerManager.getInstance().getCustomer(customer.getId()));
                }

                List<it.polito.ezshop.data.User> users = UserManager.getInstance().getAllUsers();
                for (it.polito.ezshop.data.User deletedUser : users) {
                    UserDao.getInstance().delete(UserDao.getInstance().findById(deletedUser.getId()));
                }

                List<CustomerCard> cards = CustomerCardManager.getInstance().getAllCard();
                for (CustomerCard card : cards) {
                    CustomerCardDao.getInstance().delete(card);
                }
                AuthenticationManager.getInstance().logout();
            }

            @Test
            public void test_1_deleteCard() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerCardException {

                User badUser = new User();
                badUser.setUsername("baduser");
                badUser.setPassword("baduser");
                badUser.setRole("ROLE");

                UserDao.getInstance().save(badUser);

                AuthenticationManager.getInstance().login(badUser.getUsername(), badUser.getPassword());

                CustomerCard card = new CustomerCard();
                card.setCardCode("1000000001");

                CustomerCardDao.getInstance().save(card);

                exception.expect(UnauthorizedException.class);
                CustomerCardManager.getInstance().deleteCard(card.getCardCode());
            }

            @Test
            public void test_2_deleteCard() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerCardException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                CustomerCard card = new CustomerCard();
                card.setCardCode("1000000001");

                CustomerCardDao.getInstance().save(card);

                exception.expect(InvalidCustomerCardException.class);
                CustomerCardManager.getInstance().deleteCard(null);
            }

            @Test
            public void test_3_deleteCard() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerCardException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                CustomerCard card = new CustomerCard();
                card.setCardCode("1000000001");

                CustomerCardDao.getInstance().save(card);

                exception.expect(InvalidCustomerCardException.class);
                CustomerCardManager.getInstance().deleteCard("1");
            }

            @Test
            public void test_4_deleteCard() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerCardException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                CustomerCard card = new CustomerCard();
                card.setCardCode("1000000001");

                exception.expect(InvalidCustomerCardException.class);
                CustomerCardManager.getInstance().deleteCard("1000000001");
            }

            @Test
            public void test_5_deleteCard() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerCardException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                CustomerCard card = new CustomerCard();
                card.setCardCode("1000000001");

                CustomerCardDao.getInstance().save(card);

                CustomerCardManager.getInstance().deleteCard(card.getCardCode());

                card = CustomerCardManager.getInstance().findByCode(card.getCardCode());

                Assert.assertNull(card);
            }
        }

        public static class testAttachCard {

            @Rule
            public final ExpectedException exception = ExpectedException.none();

            @After
            public void resetDb() throws UnauthorizedException, InvalidCustomerIdException, InvalidPasswordException,
                    InvalidUsernameException {

                AuthenticationManager.getInstance().logout();

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                List<it.polito.ezshop.data.Customer> customers = CustomerManager.getInstance().getAllCustomers();
                for (it.polito.ezshop.data.Customer customer : customers) {
                    CustomerDao.getInstance().delete(CustomerManager.getInstance().getCustomer(customer.getId()));
                }

                List<it.polito.ezshop.data.User> users = UserManager.getInstance().getAllUsers();
                for (it.polito.ezshop.data.User deletedUser : users) {
                    UserDao.getInstance().delete(UserDao.getInstance().findById(deletedUser.getId()));
                }

                List<CustomerCard> cards = CustomerCardManager.getInstance().getAllCard();
                for (CustomerCard card : cards) {
                    CustomerCardDao.getInstance().delete(card);
                }
                AuthenticationManager.getInstance().logout();
            }

            @Test
            public void test_1_attachCard() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerCardException, InvalidCustomerIdException {

                User badUser = new User();
                badUser.setUsername("baduser");
                badUser.setPassword("baduser");
                badUser.setRole("ROLE");

                UserDao.getInstance().save(badUser);

                AuthenticationManager.getInstance().login(badUser.getUsername(), badUser.getPassword());

                Customer customer = new Customer();

                CustomerCard card = new CustomerCard();
                card.setCardCode("1000000001");

                CustomerCardDao.getInstance().save(card);
                CustomerDao.getInstance().save(customer);

                exception.expect(UnauthorizedException.class);
                CustomerCardManager.getInstance().attachCard(card.getCardCode(), customer.getId());
            }

            @Test
            public void test_2_attachCard() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerCardException, InvalidCustomerIdException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                Customer customer = new Customer();

                CustomerCard card = new CustomerCard();
                card.setCardCode("1000000001");

                CustomerCardDao.getInstance().save(card);
                CustomerDao.getInstance().save(customer);

                exception.expect(InvalidCustomerCardException.class);
                CustomerCardManager.getInstance().attachCard(null, customer.getId());
            }

            @Test
            public void test_3_attachCard() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerCardException, InvalidCustomerIdException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                Customer customer = new Customer();

                CustomerCard card = new CustomerCard();
                card.setCardCode("1000000001");

                CustomerCardDao.getInstance().save(card);
                CustomerDao.getInstance().save(customer);

                exception.expect(InvalidCustomerCardException.class);
                CustomerCardManager.getInstance().attachCard("1", customer.getId());
            }

            @Test
            public void test_4_attachCard() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerCardException, InvalidCustomerIdException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                Customer customer = new Customer();

                CustomerCard card = new CustomerCard();
                card.setCardCode("1000000001");

                CustomerDao.getInstance().save(customer);

                exception.expect(InvalidCustomerCardException.class);
                CustomerCardManager.getInstance().attachCard("1000000001", customer.getId());
            }

            @Test
            public void test_5_attachCard() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerCardException, InvalidCustomerIdException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                Customer customer = new Customer();

                CustomerCard card = new CustomerCard();
                card.setCardCode("1000000001");

                CustomerCardDao.getInstance().save(card);
                CustomerDao.getInstance().save(customer);

                exception.expect(InvalidCustomerIdException.class);
                CustomerCardManager.getInstance().attachCard(card.getCardCode(), null);
            }

            @Test
            public void test_6_attachCard() throws UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidCustomerCardException, InvalidCustomerIdException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                Customer customer = new Customer();

                CustomerCard card = new CustomerCard();
                card.setCardCode("1000000001");

                CustomerCardDao.getInstance().save(card);
                CustomerDao.getInstance().save(customer);

                CustomerCardManager.getInstance().attachCard(card.getCardCode(), customer.getId());

                card = CustomerCardManager.getInstance().findByCode(card.getCardCode());

                Assert.assertEquals(customer.getId(), card.getCustomerId());
            }
        }
    }

    public static class TestReturnTransactionManager {

        public static class testStartReturnTransaction {

            @Rule
            public final ExpectedException exception = ExpectedException.none();

            @Before
            public void resetDB() {
                for (ReturnedProduct returnedProduct : ReturnedProductDao.getInstance().findAll()) {
                    ReturnedProductDao.getInstance().delete(returnedProduct);
                }
                for (ReturnTransaction returnTransaction : ReturnTransactionDao.getInstance().findAll()) {
                    ReturnTransactionDao.getInstance().delete(returnTransaction);
                }
                for (ProductType productType : ProductTypeDao.getInstance().findAll()) {
                    ProductTypeDao.getInstance().delete(productType);
                }
                for (TicketEntry ticketEntry : TicketEntryDao.getInstance().findAll()) {
                    TicketEntryDao.getInstance().delete(ticketEntry);
                }
            }

            @Test
            public void test_1_startReturnTransaction() throws InvalidPasswordException, InvalidUsernameException,
                    InvalidTicketNumberException, UnauthorizedException {

                User badUser = new User();
                badUser.setUsername("baduser");
                badUser.setPassword("baduser");
                badUser.setRole("ROLE");

                SaleTransaction saleTransaction = new SaleTransaction();

                SaleTransactionDao.getInstance().save(saleTransaction);

                UserDao.getInstance().save(badUser);

                AuthenticationManager.getInstance().login(badUser.getUsername(), badUser.getPassword());

                exception.expect(UnauthorizedException.class);
                ReturnTransactionManager.getInstance().startReturnTransaction(saleTransaction.getTicketNumber());
            }

            @Test
            public void test_2_startReturnTransaction() throws InvalidPasswordException, InvalidUsernameException,
                    InvalidTicketNumberException, UnauthorizedException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                SaleTransaction saleTransaction = new SaleTransaction();

                SaleTransactionDao.getInstance().save(saleTransaction);

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                Integer badTicketNumber = null;

                exception.expect(InvalidTicketNumberException.class);
                ReturnTransactionManager.getInstance().startReturnTransaction(badTicketNumber);
            }

            @Test
            public void test_3_startReturnTransaction() throws InvalidPasswordException, InvalidUsernameException,
                    InvalidTicketNumberException, UnauthorizedException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());

                SaleTransaction saleTransaction = new SaleTransaction();

                SaleTransactionDao.getInstance().save(saleTransaction);

                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                Integer badTicketNumber = 1000;

                exception.expect(InvalidTicketNumberException.class);
                ReturnTransactionManager.getInstance().startReturnTransaction(badTicketNumber);
            }

            @Test
            public void test_4_startReturnTransaction() throws InvalidPasswordException, InvalidUsernameException,
                    InvalidTicketNumberException, UnauthorizedException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());
                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();

                SaleTransactionDao.getInstance().save(saleTransaction);

                Integer returnTransactionId = ReturnTransactionManager.getInstance()
                        .startReturnTransaction(saleTransaction.getTicketNumber());

                ReturnTransaction returnTransaction = ReturnTransactionManager.getInstance()
                        .findById(returnTransactionId);

                Assert.assertEquals(returnTransactionId, returnTransaction.getId());
                Assert.assertEquals(saleTransaction.getTicketNumber(), returnTransaction.getTicketNumber());
                Assert.assertEquals(ReturnTransactionEnum.OPEN.name(), returnTransaction.getStatus());
            }
        }

        public static class testReturnProduct {

            @Rule
            public final ExpectedException exception = ExpectedException.none();

            @Before
            public void resetDB() {
                for (ReturnedProduct returnedProduct : ReturnedProductDao.getInstance().findAll()) {
                    ReturnedProductDao.getInstance().delete(returnedProduct);
                }
                for (ReturnTransaction returnTransaction : ReturnTransactionDao.getInstance().findAll()) {
                    ReturnTransactionDao.getInstance().delete(returnTransaction);
                }
                for (ProductType productType : ProductTypeDao.getInstance().findAll()) {
                    ProductTypeDao.getInstance().delete(productType);
                }
                for (TicketEntry ticketEntry : TicketEntryDao.getInstance().findAll()) {
                    TicketEntryDao.getInstance().delete(ticketEntry);
                }
            }

            @Test
            public void test_1_returnProduct()
                    throws InvalidPasswordException, InvalidUsernameException, UnauthorizedException,
                    InvalidQuantityException, InvalidTransactionIdException, InvalidProductCodeException {

                User badUser = new User();
                badUser.setUsername("baduser");
                badUser.setPassword("baduser");
                badUser.setRole("ROLE");

                UserDao.getInstance().save(badUser);
                AuthenticationManager.getInstance().login(badUser.getUsername(), badUser.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();

                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.OPEN.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);

                exception.expect(UnauthorizedException.class);
                ReturnTransactionManager.getInstance().returnProduct(returnTransaction.getId(), "6291041500213", 5);
            }

            /*
             * invalid transaction id, expected InvalidTransactionIdException
             * 
             * @throws InvalidPasswordException
             * 
             * @throws InvalidUsernameException
             * 
             * @throws InvalidQuantityException
             * 
             * @throws InvalidTransactionIdException
             * 
             * @throws InvalidProductCodeException
             * 
             * @throws UnauthorizedException
             */

            @Test
            public void test_2_returnProduct()
                    throws InvalidPasswordException, InvalidUsernameException, UnauthorizedException,
                    InvalidQuantityException, InvalidTransactionIdException, InvalidProductCodeException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());
                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();

                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.OPEN.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);

                exception.expect(InvalidTransactionIdException.class);
                ReturnTransactionManager.getInstance().returnProduct(-1, "6291041500213", 5);
            }

            /*
             * invalid transaction id, expected InvalidTransactionIdException
             * 
             * @throws InvalidPasswordException
             * 
             * @throws InvalidUsernameException
             * 
             * @throws InvalidQuantityException
             * 
             * @throws InvalidTransactionIdException
             * 
             * @throws InvalidProductCodeException
             * 
             * @throws UnauthorizedException
             */

            @Test
            public void test_3_returnProduct()
                    throws InvalidPasswordException, InvalidUsernameException, UnauthorizedException,
                    InvalidQuantityException, InvalidTransactionIdException, InvalidProductCodeException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());
                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();
                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.OPEN.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);

                exception.expect(InvalidTransactionIdException.class);
                ReturnTransactionManager.getInstance().returnProduct(null, "6291041500213", 5);
            }

            /*
             * invalid product code, expected InvalidProductCodeException
             * 
             * @throws InvalidPasswordException
             * 
             * @throws InvalidUsernameException
             * 
             * @throws InvalidQuantityException
             * 
             * @throws InvalidTransactionIdException
             * 
             * @throws InvalidProductCodeException
             * 
             * @throws UnauthorizedException
             */

            @Test
            public void test_4_returnProduct()
                    throws InvalidPasswordException, InvalidUsernameException, UnauthorizedException,
                    InvalidQuantityException, InvalidTransactionIdException, InvalidProductCodeException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());
                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();

                ProductType productType = new ProductType();
                productType.setQuantity(10);
                productType.setProductDescription("Pomodoro");
                productType.setBarCode("6291041500213");
                productType.setLocation("Any");
                productType.setPricePerUnit(50.00);
                productType.setNote("Nota");

                ProductTypeDao.getInstance().save(productType);

                SaleTransactionDao.getInstance().save(saleTransaction);

                TicketEntry ticketEntry = new TicketEntry();
                ticketEntry.setTicket_number(saleTransaction.getTicketNumber());
                ticketEntry.setDiscountRate(0.00);
                ticketEntry.setProductDescription(productType.getProductDescription());
                ticketEntry.setBarCode(productType.getBarCode());
                ticketEntry.setAmount(10);
                ticketEntry.setPricePerUnit(productType.getPricePerUnit());

                TicketEntryDao.getInstance().save(ticketEntry);

                List<TicketEntry> entries = new ArrayList<>();
                entries.add(ticketEntry);

                saleTransaction.setTicketEntries(entries);

                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.OPEN.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);

                exception.expect(InvalidProductCodeException.class);
                ReturnTransactionManager.getInstance().returnProduct(returnTransaction.getId(), "2", 5);
            }

            /*
             * invalid return amount, expected InvalidQuantityException
             * 
             * @throws InvalidPasswordException
             * 
             * @throws InvalidUsernameException
             * 
             * @throws InvalidQuantityException
             * 
             * @throws InvalidTransactionIdException
             * 
             * @throws InvalidProductCodeException
             * 
             * @throws UnauthorizedException
             */

            @Test
            public void test_5_returnProduct()
                    throws InvalidPasswordException, InvalidUsernameException, UnauthorizedException,
                    InvalidQuantityException, InvalidTransactionIdException, InvalidProductCodeException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());
                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();

                ProductType productType = new ProductType();
                productType.setQuantity(10);
                productType.setProductDescription("Pomodoro");
                productType.setBarCode("6291041500213");
                productType.setLocation("Any");
                productType.setPricePerUnit(50.00);
                productType.setNote("Nota");

                ProductTypeDao.getInstance().save(productType);

                SaleTransactionDao.getInstance().save(saleTransaction);

                TicketEntry ticketEntry = new TicketEntry();
                ticketEntry.setTicket_number(saleTransaction.getTicketNumber());
                ticketEntry.setDiscountRate(0.00);
                ticketEntry.setProductDescription(productType.getProductDescription());
                ticketEntry.setBarCode(productType.getBarCode());
                ticketEntry.setAmount(10);
                ticketEntry.setPricePerUnit(productType.getPricePerUnit());

                TicketEntryDao.getInstance().save(ticketEntry);

                List<TicketEntry> entries = new ArrayList<>();
                entries.add(ticketEntry);

                saleTransaction.setTicketEntries(entries);

                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.OPEN.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);

                exception.expect(InvalidQuantityException.class);
                ReturnTransactionManager.getInstance().returnProduct(returnTransaction.getId(), "6291041500213", 15);
            }

            @Test
            public void test_6_returnProduct() throws InvalidPasswordException, InvalidUsernameException,
                    UnauthorizedException, InvalidQuantityException, InvalidTransactionIdException,
                    InvalidProductCodeException, InvalidTicketNumberException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());
                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();

                ProductType productType = new ProductType();
                productType.setQuantity(10);
                productType.setProductDescription("Pomodoro");
                productType.setBarCode("6291041500213");
                productType.setLocation("Any");
                productType.setPricePerUnit(50.00);
                productType.setNote("Nota");

                ProductTypeDao.getInstance().save(productType);

                SaleTransactionDao.getInstance().save(saleTransaction);

                TicketEntry ticketEntry = new TicketEntry();
                ticketEntry.setTicket_number(saleTransaction.getTicketNumber());
                ticketEntry.setDiscountRate(0.00);
                ticketEntry.setProductDescription(productType.getProductDescription());
                ticketEntry.setBarCode(productType.getBarCode());
                ticketEntry.setAmount(10);
                ticketEntry.setPricePerUnit(productType.getPricePerUnit());

                TicketEntryDao.getInstance().save(ticketEntry);

                List<TicketEntry> entries = new ArrayList<>();
                entries.add(ticketEntry);

                saleTransaction.setTicketEntries(entries);

                SaleTransactionDao.getInstance().save(saleTransaction);

                Integer id = ReturnTransactionManager.getInstance()
                        .startReturnTransaction(saleTransaction.getTicketNumber());

                ReturnTransaction returnTransaction = ReturnTransactionDao.getInstance().findById(id);

                ReturnTransactionDao.getInstance().save(returnTransaction);

                ReturnTransactionManager.getInstance().returnProduct(returnTransaction.getId(), "6291041500213", 5);

                returnTransaction = ReturnTransactionManager.getInstance().findById(returnTransaction.getId());

                List<ReturnedProduct> returnedProducts = returnTransaction.getProducts();

                Assert.assertEquals(1, returnedProducts.size());
                Assert.assertEquals(ticketEntry.getBarCode(), returnedProducts.get(0).getBarCode());
                Assert.assertEquals(5, (int) returnedProducts.get(0).getAmount());
            }

            @Test
            public void test_7_returnProduct()
                    throws InvalidPasswordException, InvalidUsernameException, UnauthorizedException,
                    InvalidQuantityException, InvalidTransactionIdException, InvalidProductCodeException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());
                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();

                ProductType productType = new ProductType();
                productType.setQuantity(10);
                productType.setProductDescription("Pomodoro");
                productType.setBarCode("6291041500213");
                productType.setLocation("Any");
                productType.setPricePerUnit(50.00);
                productType.setNote("Nota");

                ProductTypeDao.getInstance().save(productType);

                SaleTransactionDao.getInstance().save(saleTransaction);

                TicketEntry ticketEntry = new TicketEntry();
                ticketEntry.setTicket_number(saleTransaction.getTicketNumber());
                ticketEntry.setDiscountRate(0.00);
                ticketEntry.setProductDescription(productType.getProductDescription());
                ticketEntry.setBarCode(productType.getBarCode());
                ticketEntry.setAmount(10);
                ticketEntry.setPricePerUnit(productType.getPricePerUnit());

                TicketEntryDao.getInstance().save(ticketEntry);

                List<TicketEntry> entries = new ArrayList<>();
                entries.add(ticketEntry);

                saleTransaction.setTicketEntries(entries);

                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.OPEN.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);

                exception.expect(InvalidProductCodeException.class);
                ReturnTransactionManager.getInstance().returnProduct(returnTransaction.getId(), null, 5);
            }

            @Test
            public void test_8_returnProduct()
                    throws InvalidPasswordException, InvalidUsernameException, UnauthorizedException,
                    InvalidQuantityException, InvalidTransactionIdException, InvalidProductCodeException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());
                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();

                ProductType productType = new ProductType();
                productType.setQuantity(10);
                productType.setProductDescription("Pomodoro");
                productType.setBarCode("6291041500213");
                productType.setLocation("Any");
                productType.setPricePerUnit(50.00);
                productType.setNote("Nota");

                ProductTypeDao.getInstance().save(productType);

                SaleTransactionDao.getInstance().save(saleTransaction);

                TicketEntry ticketEntry = new TicketEntry();
                ticketEntry.setTicket_number(saleTransaction.getTicketNumber());
                ticketEntry.setDiscountRate(0.00);
                ticketEntry.setProductDescription(productType.getProductDescription());
                ticketEntry.setBarCode(productType.getBarCode());
                ticketEntry.setAmount(10);
                ticketEntry.setPricePerUnit(productType.getPricePerUnit());

                TicketEntryDao.getInstance().save(ticketEntry);

                List<TicketEntry> entries = new ArrayList<>();
                entries.add(ticketEntry);

                saleTransaction.setTicketEntries(entries);

                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.OPEN.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);

                exception.expect(InvalidProductCodeException.class);
                ReturnTransactionManager.getInstance().returnProduct(returnTransaction.getId(), "", 5);
            }

            @Test
            public void test_9_returnProduct()
                    throws InvalidPasswordException, InvalidUsernameException, UnauthorizedException,
                    InvalidQuantityException, InvalidTransactionIdException, InvalidProductCodeException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());
                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();

                ProductType productType = new ProductType();
                productType.setQuantity(10);
                productType.setProductDescription("Pomodoro");
                productType.setBarCode("6291041500213");
                productType.setLocation("Any");
                productType.setPricePerUnit(50.00);
                productType.setNote("Nota");

                ProductTypeDao.getInstance().save(productType);

                SaleTransactionDao.getInstance().save(saleTransaction);

                TicketEntry ticketEntry = new TicketEntry();
                ticketEntry.setTicket_number(saleTransaction.getTicketNumber());
                ticketEntry.setDiscountRate(0.00);
                ticketEntry.setProductDescription(productType.getProductDescription());
                ticketEntry.setBarCode(productType.getBarCode());
                ticketEntry.setAmount(10);
                ticketEntry.setPricePerUnit(productType.getPricePerUnit());

                TicketEntryDao.getInstance().save(ticketEntry);

                List<TicketEntry> entries = new ArrayList<>();
                entries.add(ticketEntry);

                saleTransaction.setTicketEntries(entries);

                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.OPEN.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);

                exception.expect(InvalidQuantityException.class);
                ReturnTransactionManager.getInstance().returnProduct(returnTransaction.getId(), "6291041500213", -1);
            }
        }

        public static class testEndReturnTransaction {

            @Rule
            public final ExpectedException exception = ExpectedException.none();

            @Before
            public void resetDB() {
                for (ReturnedProduct returnedProduct : ReturnedProductDao.getInstance().findAll()) {
                    ReturnedProductDao.getInstance().delete(returnedProduct);
                }
                for (ReturnTransaction returnTransaction : ReturnTransactionDao.getInstance().findAll()) {
                    ReturnTransactionDao.getInstance().delete(returnTransaction);
                }
                for (ProductType productType : ProductTypeDao.getInstance().findAll()) {
                    ProductTypeDao.getInstance().delete(productType);
                }
                for (TicketEntry ticketEntry : TicketEntryDao.getInstance().findAll()) {
                    TicketEntryDao.getInstance().delete(ticketEntry);
                }
            }

            @Test
            public void test_1_endReturnTransaction() throws InvalidTransactionIdException, UnauthorizedException,
                    InvalidPasswordException, InvalidUsernameException {
                User badUser = new User();
                badUser.setUsername("baduser");
                badUser.setPassword("baduser");
                badUser.setRole("ROLE");

                UserDao.getInstance().save(badUser);
                AuthenticationManager.getInstance().login(badUser.getUsername(), badUser.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();

                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.OPEN.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);

                boolean commit = true;

                exception.expect(UnauthorizedException.class);
                ReturnTransactionManager.getInstance().endReturnTransaction(returnTransaction.getId(), commit);
            }

            /*
             * invalid return Id, expected InvalidTransactionIdException
             * 
             * @throws InvalidPasswordException
             * 
             * @throws InvalidUsernameException
             * 
             * @throws InvalidTransactionIdException
             * 
             * @throws UnauthorizedException
             */

            @Test
            public void test_2_endReturnTransaction() throws InvalidTransactionIdException, UnauthorizedException,
                    InvalidPasswordException, InvalidUsernameException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());
                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();

                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.OPEN.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);

                boolean commit = true;

                exception.expect(InvalidTransactionIdException.class);
                ReturnTransactionManager.getInstance().endReturnTransaction(-1, commit);
            }

            /*
             * invalid return Id, expected InvalidTransactionIdException
             * 
             * @throws InvalidPasswordException
             * 
             * @throws InvalidUsernameException
             * 
             * @throws InvalidTransactionIdException
             * 
             * @throws UnauthorizedException
             */

            @Test
            public void test_3_endReturnTransaction() throws InvalidTransactionIdException, UnauthorizedException,
                    InvalidPasswordException, InvalidUsernameException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());
                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();

                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.OPEN.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);

                boolean commit = true;

                exception.expect(InvalidTransactionIdException.class);
                ReturnTransactionManager.getInstance().endReturnTransaction(null, commit);
            }

            /*
             * nominal case with commit=true, sale transaction successfully updated and
             * return successfully closed
             * 
             * @throws InvalidPasswordException
             * 
             * @throws InvalidUsernameException
             * 
             * @throws InvalidTransactionIdException
             * 
             * @throws UnauthorizedException
             */

            @Test
            public void test_4_endReturnTransaction()
                    throws InvalidTransactionIdException, UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidQuantityException, InvalidProductCodeException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());
                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();

                ProductType productType = new ProductType();
                productType.setQuantity(10);
                productType.setProductDescription("Pomodoro");
                productType.setBarCode("6291041500213");
                productType.setLocation("Any");
                productType.setPricePerUnit(50.00);
                productType.setNote("Nota");

                ProductTypeDao.getInstance().save(productType);

                SaleTransactionDao.getInstance().save(saleTransaction);

                TicketEntry ticketEntry = new TicketEntry();
                ticketEntry.setTicket_number(saleTransaction.getTicketNumber());
                ticketEntry.setDiscountRate(0.00);
                ticketEntry.setProductDescription(productType.getProductDescription());
                ticketEntry.setBarCode(productType.getBarCode());
                ticketEntry.setAmount(10);
                ticketEntry.setPricePerUnit(productType.getPricePerUnit());

                TicketEntryDao.getInstance().save(ticketEntry);

                List<TicketEntry> entries = new ArrayList<>();
                entries.add(ticketEntry);

                saleTransaction.setTicketEntries(entries);
                saleTransaction.setPrice(500.00);
                saleTransaction.setDiscountRate(1);

                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.OPEN.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);
                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransactionManager.getInstance().returnProduct(returnTransaction.getId(), "6291041500213", 5);

                returnTransaction = ReturnTransactionManager.getInstance().findById(returnTransaction.getId());

                boolean commit = true;

                ReturnTransactionManager.getInstance().endReturnTransaction(returnTransaction.getId(), commit);

                double price = 5.00 * productType.getPricePerUnit();

                returnTransaction = ReturnTransactionManager.getInstance().findById(returnTransaction.getId());
                saleTransaction = SaleTransactionDao.getInstance().findById(saleTransaction.getTicketNumber());
                productType = ProductTypeManager.getInstance().getProductTypeByBarCode(productType.getBarCode());

                Assert.assertEquals(ReturnTransactionEnum.CLOSED.name(), returnTransaction.getStatus());
                Assert.assertEquals(price, returnTransaction.getPrice(), 0.1);
                Assert.assertEquals(250.00, saleTransaction.getPrice(), 0.1);
                Assert.assertEquals(5, saleTransaction.getTicketEntries().get(0).getAmount());
                Assert.assertEquals(15, (int) productType.getQuantity());

            }

            /*
             * nominal case with commit=false, revert changes
             * 
             * @throws InvalidPasswordException
             * 
             * @throws InvalidUsernameException
             * 
             * @throws InvalidTransactionIdException
             * 
             * @throws UnauthorizedException
             */

            @Test
            public void test_5_endReturnTransaction()
                    throws InvalidTransactionIdException, UnauthorizedException, InvalidPasswordException,
                    InvalidUsernameException, InvalidQuantityException, InvalidProductCodeException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());
                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();

                ProductType productType = new ProductType();
                productType.setQuantity(10);
                productType.setProductDescription("Pomodoro");
                productType.setBarCode("6291041500213");
                productType.setLocation("Any");
                productType.setPricePerUnit(50.00);
                productType.setNote("Nota");

                ProductTypeDao.getInstance().save(productType);

                SaleTransactionDao.getInstance().save(saleTransaction);

                TicketEntry ticketEntry = new TicketEntry();
                ticketEntry.setTicket_number(saleTransaction.getTicketNumber());
                ticketEntry.setDiscountRate(0.00);
                ticketEntry.setProductDescription(productType.getProductDescription());
                ticketEntry.setBarCode(productType.getBarCode());
                ticketEntry.setAmount(10);
                ticketEntry.setPricePerUnit(productType.getPricePerUnit());

                TicketEntryDao.getInstance().save(ticketEntry);

                List<TicketEntry> entries = new ArrayList<>();
                entries.add(ticketEntry);

                saleTransaction.setTicketEntries(entries);
                saleTransaction.setPrice(500.00);
                saleTransaction.setDiscountRate(1);

                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.OPEN.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);
                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransactionManager.getInstance().returnProduct(returnTransaction.getId(), "6291041500213", 5);

                returnTransaction = ReturnTransactionManager.getInstance().findById(returnTransaction.getId());

                boolean commit = false;

                ReturnTransactionManager.getInstance().endReturnTransaction(returnTransaction.getId(), commit);

                double price = 5.00 * productType.getPricePerUnit();

                saleTransaction = SaleTransactionDao.getInstance().findById(saleTransaction.getTicketNumber());
                productType = ProductTypeManager.getInstance().getProductTypeByBarCode(productType.getBarCode());

                Assert.assertEquals(price, returnTransaction.getPrice(), 0.1);
                Assert.assertEquals(500.00, saleTransaction.getPrice(), 0.1);
                Assert.assertEquals(10, saleTransaction.getTicketEntries().get(0).getAmount());
                Assert.assertEquals(10, (int) productType.getQuantity());

            }

            @Test
            public void test_6_endReturnTransaction() throws InvalidTransactionIdException, UnauthorizedException,
                    InvalidPasswordException, InvalidUsernameException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());
                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();

                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.CLOSED.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);

                boolean commit = true;

                exception.expect(InvalidTransactionIdException.class);
                ReturnTransactionManager.getInstance().endReturnTransaction(returnTransaction.getId(), commit);
            }
        }

        public static class testDeleteReturnTransaction {

            @Rule
            public final ExpectedException exception = ExpectedException.none();

            @Before
            public void resetDB() {
                for (ReturnedProduct returnedProduct : ReturnedProductDao.getInstance().findAll()) {
                    ReturnedProductDao.getInstance().delete(returnedProduct);
                }
                for (ReturnTransaction returnTransaction : ReturnTransactionDao.getInstance().findAll()) {
                    ReturnTransactionDao.getInstance().delete(returnTransaction);
                }
                for (ProductType productType : ProductTypeDao.getInstance().findAll()) {
                    ProductTypeDao.getInstance().delete(productType);
                }
                for (TicketEntry ticketEntry : TicketEntryDao.getInstance().findAll()) {
                    TicketEntryDao.getInstance().delete(ticketEntry);
                }
            }

            @Test
            public void test_1_deleteReturnTransaction() throws InvalidTransactionIdException, UnauthorizedException,
                    InvalidPasswordException, InvalidUsernameException {
                User badUser = new User();
                badUser.setUsername("baduser");
                badUser.setPassword("baduser");
                badUser.setRole("ROLE");

                UserDao.getInstance().save(badUser);
                AuthenticationManager.getInstance().login(badUser.getUsername(), badUser.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();

                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.OPEN.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);

                exception.expect(UnauthorizedException.class);
                ReturnTransactionManager.getInstance().deleteReturnTransaction(returnTransaction.getId());
            }

            @Test
            public void test_2_deleteReturnTransaction() throws InvalidTransactionIdException, UnauthorizedException,
                    InvalidPasswordException, InvalidUsernameException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());
                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();

                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.OPEN.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);

                exception.expect(InvalidTransactionIdException.class);
                ReturnTransactionManager.getInstance().deleteReturnTransaction(-1);
            }

            @Test
            public void test_3_deleteReturnTransaction() throws InvalidTransactionIdException, UnauthorizedException,
                    InvalidPasswordException, InvalidUsernameException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());
                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();

                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.OPEN.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);

                exception.expect(InvalidTransactionIdException.class);
                ReturnTransactionManager.getInstance().deleteReturnTransaction(null);
            }

            @Test
            public void test_4_deleteReturnTransaction() throws InvalidTransactionIdException, UnauthorizedException,
                    InvalidPasswordException, InvalidUsernameException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());
                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();

                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.OPEN.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);

                exception.expect(InvalidTransactionIdException.class);
                ReturnTransactionManager.getInstance().deleteReturnTransaction(returnTransaction.getId());
            }

            @Test
            public void test_5_deleteReturnTransaction() throws InvalidTransactionIdException, UnauthorizedException,
                    InvalidPasswordException, InvalidUsernameException, InvalidProductCodeException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());
                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();

                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.CLOSED.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);

                ProductType productType = new ProductType();
                productType.setQuantity(10);
                productType.setProductDescription("Pomodoro");
                productType.setBarCode("6291041500213");
                productType.setLocation("Any");
                productType.setPricePerUnit(50.00);
                productType.setNote("Nota");

                ProductTypeDao.getInstance().save(productType);

                SaleTransactionDao.getInstance().save(saleTransaction);

                TicketEntry ticketEntry = new TicketEntry();
                ticketEntry.setTicket_number(saleTransaction.getTicketNumber());
                ticketEntry.setDiscountRate(0.00);
                ticketEntry.setProductDescription(productType.getProductDescription());
                ticketEntry.setBarCode(productType.getBarCode());
                ticketEntry.setAmount(10);
                ticketEntry.setPricePerUnit(productType.getPricePerUnit());

                TicketEntryDao.getInstance().save(ticketEntry);

                List<TicketEntry> entries = new ArrayList<>();
                entries.add(ticketEntry);

                saleTransaction.setTicketEntries(entries);
                saleTransaction.setPrice(500.00);
                saleTransaction.setDiscountRate(1);

                SaleTransactionDao.getInstance().save(saleTransaction);

                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());

                ReturnTransactionDao.getInstance().save(returnTransaction);
                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnedProduct returnedProduct = new ReturnedProduct();
                returnedProduct.setReturnTransaction(returnTransaction);
                returnedProduct.setPrice(ticketEntry.getPricePerUnit());
                returnedProduct.setAmount(5);
                returnedProduct.setBarCode(ticketEntry.getBarCode());

                ReturnedProductDao.getInstance().save(returnedProduct);

                returnTransaction.addProduct(returnedProduct);
                returnTransaction.setPrice(250.00);
                saleTransaction.setPrice(250.00);
                ticketEntry.setAmount(5);
                productType.setQuantity(15);

                ReturnTransactionDao.getInstance().save(returnTransaction);
                SaleTransactionDao.getInstance().save(saleTransaction);
                TicketEntryDao.getInstance().save(ticketEntry);
                ProductTypeDao.getInstance().save(productType);

                ReturnTransactionManager.getInstance().deleteReturnTransaction(returnTransaction.getId());

                returnTransaction = ReturnTransactionManager.getInstance().findById(returnTransaction.getId());

                saleTransaction = SaleTransactionDao.getInstance().findById(saleTransaction.getTicketNumber());
                productType = ProductTypeManager.getInstance().getProductTypeByBarCode(productType.getBarCode());

                Assert.assertNull(returnTransaction);
                Assert.assertEquals(500.00, saleTransaction.getPrice(), 0.1);
                Assert.assertEquals(10, saleTransaction.getTicketEntries().get(0).getAmount());
                Assert.assertEquals(10, (int) productType.getQuantity());
            }
        }

        public static class testReturnCashPayment {

            @Rule
            public final ExpectedException exception = ExpectedException.none();

            @Before
            public void resetDB() {
                for (ReturnedProduct returnedProduct : ReturnedProductDao.getInstance().findAll()) {
                    ReturnedProductDao.getInstance().delete(returnedProduct);
                }
                for (ReturnTransaction returnTransaction : ReturnTransactionDao.getInstance().findAll()) {
                    ReturnTransactionDao.getInstance().delete(returnTransaction);
                }
                for (ProductType productType : ProductTypeDao.getInstance().findAll()) {
                    ProductTypeDao.getInstance().delete(productType);
                }
                for (TicketEntry ticketEntry : TicketEntryDao.getInstance().findAll()) {
                    TicketEntryDao.getInstance().delete(ticketEntry);
                }
            }

            @Test
            public void test_1_returnCashPayment() throws InvalidPasswordException, InvalidUsernameException,
                    UnauthorizedException, InvalidTransactionIdException {

                User badUser = new User();
                badUser.setUsername("baduser");
                badUser.setPassword("baduser");
                badUser.setRole("ROLE");

                SaleTransaction saleTransaction = new SaleTransaction();

                SaleTransactionDao.getInstance().save(saleTransaction);

                UserDao.getInstance().save(badUser);

                AuthenticationManager.getInstance().login(badUser.getUsername(), badUser.getPassword());

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.CLOSED.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);

                exception.expect(UnauthorizedException.class);
                ReturnTransactionManager.getInstance().returnCashPayment(returnTransaction.getId());
            }

            @Test
            public void test_2_returnCashPayment() throws InvalidPasswordException, InvalidUsernameException,
                    UnauthorizedException, InvalidTransactionIdException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());
                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();
                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.CLOSED.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);

                exception.expect(InvalidTransactionIdException.class);
                ReturnTransactionManager.getInstance().returnCashPayment(-1);
            }

            @Test
            public void test_3_returnCashPayment() throws InvalidPasswordException, InvalidUsernameException,
                    UnauthorizedException, InvalidTransactionIdException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());
                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();
                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.CLOSED.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);

                exception.expect(InvalidTransactionIdException.class);
                ReturnTransactionManager.getInstance().returnCashPayment(null);
            }

            @Test
            public void test_4_returnCashPayment() throws InvalidPasswordException, InvalidUsernameException,
                    UnauthorizedException, InvalidTransactionIdException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());
                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();
                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.OPEN.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);

                exception.expect(InvalidTransactionIdException.class);
                ReturnTransactionManager.getInstance().returnCashPayment(returnTransaction.getId());
            }

            @Test
            public void test_5_returnCashPayment() throws InvalidPasswordException, InvalidUsernameException,
                    UnauthorizedException, InvalidTransactionIdException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());
                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();
                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.CLOSED.name());
                returnTransaction.setPrice(10.00);

                ReturnTransactionDao.getInstance().save(returnTransaction);

                double price = ReturnTransactionManager.getInstance().returnCashPayment(returnTransaction.getId());

                Assert.assertEquals(price, returnTransaction.getPrice(), 0.1);
            }
        }

        public static class testReturnCreditCardPayment {

            @Rule
            public final ExpectedException exception = ExpectedException.none();

            @Before
            public void resetDB() {
                for (ReturnedProduct returnedProduct : ReturnedProductDao.getInstance().findAll()) {
                    ReturnedProductDao.getInstance().delete(returnedProduct);
                }
                for (ReturnTransaction returnTransaction : ReturnTransactionDao.getInstance().findAll()) {
                    ReturnTransactionDao.getInstance().delete(returnTransaction);
                }
                for (ProductType productType : ProductTypeDao.getInstance().findAll()) {
                    ProductTypeDao.getInstance().delete(productType);
                }
                for (TicketEntry ticketEntry : TicketEntryDao.getInstance().findAll()) {
                    TicketEntryDao.getInstance().delete(ticketEntry);
                }
            }

            @Test
            public void test_1_returnCreditCardPayment() throws InvalidPasswordException, InvalidUsernameException,
                    UnauthorizedException, InvalidTransactionIdException, InvalidCreditCardException {

                User badUser = new User();
                badUser.setUsername("baduser");
                badUser.setPassword("baduser");
                badUser.setRole("ROLE");

                SaleTransaction saleTransaction = new SaleTransaction();

                SaleTransactionDao.getInstance().save(saleTransaction);

                UserDao.getInstance().save(badUser);

                AuthenticationManager.getInstance().login(badUser.getUsername(), badUser.getPassword());

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.CLOSED.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);

                exception.expect(UnauthorizedException.class);
                ReturnTransactionManager.getInstance().returnCreditCardPayment(returnTransaction.getId(),
                        "4463888458791019");
            }

            @Test
            public void test_2_returnCreditCardPayment() throws InvalidPasswordException, InvalidUsernameException,
                    UnauthorizedException, InvalidTransactionIdException, InvalidCreditCardException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());
                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();

                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.CLOSED.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);

                exception.expect(InvalidTransactionIdException.class);
                ReturnTransactionManager.getInstance().returnCreditCardPayment(-1, "4463888458791019");
            }

            @Test
            public void test_3_returnCreditCardPayment() throws InvalidPasswordException, InvalidUsernameException,
                    UnauthorizedException, InvalidTransactionIdException, InvalidCreditCardException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());
                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();

                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.CLOSED.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);

                exception.expect(InvalidTransactionIdException.class);
                ReturnTransactionManager.getInstance().returnCreditCardPayment(null, "4463888458791019");
            }

            @Test
            public void test_4_returnCreditCardPayment() throws InvalidPasswordException, InvalidUsernameException,
                    UnauthorizedException, InvalidTransactionIdException, InvalidCreditCardException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());
                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();

                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.CLOSED.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);

                exception.expect(InvalidCreditCardException.class);
                ReturnTransactionManager.getInstance().returnCreditCardPayment(returnTransaction.getId(), "");
            }

            @Test
            public void test_5_returnCreditCardPayment() throws InvalidPasswordException, InvalidUsernameException,
                    UnauthorizedException, InvalidTransactionIdException, InvalidCreditCardException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());
                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();

                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.CLOSED.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);

                exception.expect(InvalidCreditCardException.class);
                ReturnTransactionManager.getInstance().returnCreditCardPayment(returnTransaction.getId(), null);
            }

            @Test
            public void test_6_returnCreditCardPayment() throws InvalidPasswordException, InvalidUsernameException,
                    UnauthorizedException, InvalidTransactionIdException, InvalidCreditCardException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());
                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();

                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.CLOSED.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);

                double price = ReturnTransactionManager.getInstance().returnCreditCardPayment(returnTransaction.getId(),
                        "4463888458791019");

                Assert.assertEquals(returnTransaction.getPrice(), price, 0.1);

            }

            @Test
            public void test_7_returnCreditCardPayment() throws InvalidPasswordException, InvalidUsernameException,
                    UnauthorizedException, InvalidTransactionIdException, InvalidCreditCardException {

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole(RoleTypeEnum.Administrator.name());
                UserDao.getInstance().save(user);

                AuthenticationManager.getInstance().login(user.getUsername(), user.getPassword());

                SaleTransaction saleTransaction = new SaleTransaction();

                SaleTransactionDao.getInstance().save(saleTransaction);

                ReturnTransaction returnTransaction = new ReturnTransaction();
                returnTransaction.setTicketNumber(saleTransaction.getTicketNumber());
                returnTransaction.setStatus(ReturnTransactionEnum.OPEN.name());

                ReturnTransactionDao.getInstance().save(returnTransaction);

                exception.expect(InvalidTransactionIdException.class);
                ReturnTransactionManager.getInstance().returnCreditCardPayment(returnTransaction.getId(),
                        "4463888458791019");
            }
        }
    }

    public static class TestEZShopDao {

        @BeforeClass
        public static void resetDB() {
            for (ReturnedProduct returnedProduct : ReturnedProductDao.getInstance().findAll()) {
                ReturnedProductDao.getInstance().delete(returnedProduct);
            }
            for (ReturnTransaction returnTransaction : ReturnTransactionDao.getInstance().findAll()) {
                ReturnTransactionDao.getInstance().delete(returnTransaction);
            }
            for (ProductType productType : ProductTypeDao.getInstance().findAll()) {
                ProductTypeDao.getInstance().delete(productType);
            }
            for (TicketEntry ticketEntry : TicketEntryDao.getInstance().findAll()) {
                TicketEntryDao.getInstance().delete(ticketEntry);
            }
            for (User user : UserDao.getInstance().findAll()) {
                UserDao.getInstance().delete(user);
            }
            for (Customer customer : CustomerDao.getInstance().findAll()) {
                CustomerDao.getInstance().delete(customer);
            }
            for (CustomerCard card : CustomerCardDao.getInstance().findAll()) {
                CustomerCardDao.getInstance().delete(card);
            }
            for (Order order : OrderDao.getInstance().findAll()) {
                OrderDao.getInstance().delete(order);
            }
            for (SaleTransaction transaction : SaleTransactionDao.getInstance().findAll()) {
                SaleTransactionDao.getInstance().delete(transaction);
            }
            for (BalanceOperation operation : BalanceOperationDao.getInstance().findAll()) {
                BalanceOperationDao.getInstance().delete(operation);
            }
        }

        @Test
        public void test_CustomerDao() {
            Customer customer = new Customer();
            String customerName = "Genoveffa";

            customer.setCustomerName(customerName);
            CustomerDao.getInstance().save(customer);

            Integer id = 1;
            Assert.assertEquals(id, customer.getId());

            customer = CustomerDao.getInstance().findById(id);
            Assert.assertEquals(customerName, customer.getCustomerName());
            Assert.assertEquals(id, customer.getId());

            List<Customer> customers = CustomerDao.getInstance().findByCriteria(new HashMap<String, Object>() {
                {
                    put("customerName", customerName);
                }
            });
            customer = customers.get(0);
            Assert.assertEquals(customerName, customer.getCustomerName());
            Assert.assertEquals(id, customer.getId());

            Customer customer2 = new Customer();
            String customerName2 = "Salomone";
            customer2.setCustomerName(customerName2);
            CustomerDao.getInstance().save(customer2);

            List<Customer> customerList = CustomerDao.getInstance().findAll();

            Assert.assertEquals(2, customerList.size());

            CustomerDao.getInstance().delete(customer);
            CustomerDao.getInstance().delete(customer2);
            Assert.assertNull(CustomerDao.getInstance().findById(1));
            Assert.assertNull(CustomerDao.getInstance().findById(2));
        }

        @Test
        public void test_BalanceOperationDao() {
            it.polito.ezshop.model.BalanceOperation balanceOperation = new it.polito.ezshop.model.BalanceOperation();
            double money = 50.00;

            balanceOperation.setMoney(money);
            BalanceOperationDao.getInstance().save(balanceOperation);

            int id = 1;
            Assert.assertEquals(id, balanceOperation.getBalanceId());

            balanceOperation = BalanceOperationDao.getInstance().findById(id);
            Assert.assertEquals(money, balanceOperation.getMoney(), 0);
            Assert.assertEquals(id, balanceOperation.getBalanceId());

            List<it.polito.ezshop.model.BalanceOperation> operations = BalanceOperationDao.getInstance()
                    .findByCriteria(new HashMap<String, Object>() {
                        {
                            put("money", money);
                        }
                    });
            balanceOperation = operations.get(0);
            Assert.assertEquals(money, balanceOperation.getMoney(), 0);
            Assert.assertEquals(id, balanceOperation.getBalanceId());

            it.polito.ezshop.model.BalanceOperation balanceOperation1 = new it.polito.ezshop.model.BalanceOperation();
            double money2 = 100.00;
            balanceOperation1.setMoney(money2);
            BalanceOperationDao.getInstance().save(balanceOperation1);

            List<BalanceOperation> operationList = BalanceOperationDao.getInstance().findAll();

            Assert.assertEquals(2, operationList.size());

            BalanceOperationDao.getInstance().delete(balanceOperation);
            BalanceOperationDao.getInstance().delete(balanceOperation1);
            Assert.assertNull(BalanceOperationDao.getInstance().findById(1));
            Assert.assertNull(BalanceOperationDao.getInstance().findById(2));
        }

        @Test
        public void test_CustomerCardDao() {
            CustomerCard card = new CustomerCard();
            Integer customerId = 1;

            card.setCustomerId(customerId);
            CustomerCardDao.getInstance().save(card);

            Integer id = 1;
            Assert.assertEquals(id, card.getId());

            card = CustomerCardDao.getInstance().findById(id);
            Assert.assertEquals(customerId, card.getCustomerId());
            Assert.assertEquals(id, card.getId());

            List<CustomerCard> cards = CustomerCardDao.getInstance().findByCriteria(new HashMap<String, Object>() {
                {
                    put("customerId", customerId);
                }
            });
            card = cards.get(0);
            Assert.assertEquals(customerId, card.getCustomerId());
            Assert.assertEquals(id, card.getId());

            CustomerCard card1 = new CustomerCard();
            Integer customerId1 = 2;
            card1.setCustomerId(customerId1);
            CustomerCardDao.getInstance().save(card1);

            List<CustomerCard> list = CustomerCardDao.getInstance().findAll();

            Assert.assertEquals(2, list.size());

            CustomerCardDao.getInstance().delete(card);
            CustomerCardDao.getInstance().delete(card1);
            Assert.assertNull(CustomerCardDao.getInstance().findById(1));
            Assert.assertNull(CustomerCardDao.getInstance().findById(2));
        }

        @Test
        public void test_OrderDao() {
            Order order = new Order();
            Integer balanceId = 1;

            order.setBalanceId(balanceId);
            order.setPricePerUnit(10.00);
            order.setQuantity(10);
            order.setStatus(OrderStatusEnum.ISSUED.name());

            OrderDao.getInstance().save(order);
            Integer id = 1;
            Assert.assertEquals(id, order.getOrderId());

            order = OrderDao.getInstance().findById(id);
            Assert.assertEquals(balanceId, order.getBalanceId());
            Assert.assertEquals(id, order.getOrderId());

            List<Order> operations = OrderDao.getInstance().findByCriteria(new HashMap<String, Object>() {
                {
                    put("balanceId", balanceId);
                }
            });
            order = operations.get(0);
            Assert.assertEquals(balanceId, order.getBalanceId());
            Assert.assertEquals(id, order.getOrderId());

            Order order1 = new Order();
            Integer balanceId1 = 2;
            order1.setBalanceId(balanceId1);
            order1.setPricePerUnit(10.00);
            order1.setQuantity(10);
            order1.setStatus(OrderStatusEnum.ISSUED.name());
            OrderDao.getInstance().save(order1);

            List<Order> operationList = OrderDao.getInstance().findAll();

            Assert.assertEquals(2, operationList.size());

            OrderDao.getInstance().delete(order);
            OrderDao.getInstance().delete(order1);
            Assert.assertNull(OrderDao.getInstance().findById(1));
            Assert.assertNull(OrderDao.getInstance().findById(2));
        }

        @Test
        public void test_ProductTypeDao() {
            ProductType productType = new ProductType();
            Integer quantity = 10;
            String description = "pomodoro";

            productType.setQuantity(quantity);
            productType.setProductDescription(description);

            ProductTypeDao.getInstance().save(productType);
            Integer id = 1;
            Assert.assertEquals(id, productType.getId());

            productType = ProductTypeDao.getInstance().findById(id);
            Assert.assertEquals(quantity, productType.getQuantity());
            Assert.assertEquals(id, productType.getId());

            List<ProductType> operations = ProductTypeDao.getInstance().findByCriteria(new HashMap<String, Object>() {
                {
                    put("quantity", quantity);
                }
            });
            productType = operations.get(0);
            Assert.assertEquals(quantity, productType.getQuantity());
            Assert.assertEquals(id, productType.getId());

            ProductType productType1 = new ProductType();
            Integer quantity1 = 10;
            productType1.setQuantity(quantity1);
            productType1.setProductDescription(description);

            ProductTypeDao.getInstance().save(productType1);

            List<ProductType> operationList = ProductTypeDao.getInstance().findAll();

            Assert.assertEquals(2, operationList.size());

            List<ProductType> list = ProductTypeDao.getInstance().findByDescription(description);

            Assert.assertEquals(2, list.size());

            ProductTypeDao.getInstance().delete(productType);
            ProductTypeDao.getInstance().delete(productType1);
            Assert.assertNull(ProductTypeDao.getInstance().findById(2));
            Assert.assertNull(ProductTypeDao.getInstance().findById(3));
        }

        @Test
        public void test_ReturnedProductDao() {
            ReturnedProduct returnedProduct = new ReturnedProduct();
            String barCode = "1234";

            returnedProduct.setBarCode(barCode);
            ReturnedProductDao.getInstance().save(returnedProduct);

            Integer id = 1;
            Assert.assertEquals(id, returnedProduct.getId());

            returnedProduct = ReturnedProductDao.getInstance().findById(id);
            Assert.assertEquals(barCode, returnedProduct.getBarCode());
            Assert.assertEquals(id, returnedProduct.getId());

            List<ReturnedProduct> cards = ReturnedProductDao.getInstance()
                    .findByCriteria(new HashMap<String, Object>() {
                        {
                            put("barCode", barCode);
                        }
                    });
            returnedProduct = cards.get(0);
            Assert.assertEquals(barCode, returnedProduct.getBarCode());
            Assert.assertEquals(id, returnedProduct.getId());

            ReturnedProduct returnedProduct1 = new ReturnedProduct();
            String barCode1 = "1235";
            returnedProduct1.setBarCode(barCode1);
            ReturnedProductDao.getInstance().save(returnedProduct1);

            List<ReturnedProduct> list = ReturnedProductDao.getInstance().findAll();

            Assert.assertEquals(2, list.size());

            ReturnedProductDao.getInstance().delete(returnedProduct);
            ReturnedProductDao.getInstance().delete(returnedProduct1);
            Assert.assertNull(ReturnedProductDao.getInstance().findById(1));
            Assert.assertNull(ReturnedProductDao.getInstance().findById(2));
        }

        @Test
        public void test_ReturnTransactionDao() {
            ReturnTransaction returnTransaction = new ReturnTransaction();
            Integer ticketNumber = 1;

            returnTransaction.setTicketNumber(ticketNumber);
            ReturnTransactionDao.getInstance().save(returnTransaction);

            Integer id = 1;
            Assert.assertEquals(id, returnTransaction.getId());

            returnTransaction = ReturnTransactionDao.getInstance().findById(id);
            Assert.assertEquals(ticketNumber, returnTransaction.getTicketNumber());
            Assert.assertEquals(id, returnTransaction.getId());

            List<ReturnTransaction> cards = ReturnTransactionDao.getInstance()
                    .findByCriteria(new HashMap<String, Object>() {
                        {
                            put("ticketNumber", ticketNumber);
                        }
                    });
            returnTransaction = cards.get(0);
            Assert.assertEquals(ticketNumber, returnTransaction.getTicketNumber());
            Assert.assertEquals(id, returnTransaction.getId());

            ReturnTransaction returnedTransaction1 = new ReturnTransaction();
            Integer ticketNumber1 = 1;
            returnedTransaction1.setTicketNumber(ticketNumber1);
            ReturnTransactionDao.getInstance().save(returnedTransaction1);

            List<ReturnTransaction> list = ReturnTransactionDao.getInstance().findAll();

            Assert.assertEquals(2, list.size());

            ReturnTransactionDao.getInstance().delete(returnTransaction);
            ReturnTransactionDao.getInstance().delete(returnedTransaction1);
            Assert.assertNull(ReturnTransactionDao.getInstance().findById(1));
            Assert.assertNull(ReturnTransactionDao.getInstance().findById(2));
        }

        @Test
        public void test_SaleTransactionDao() {
            SaleTransaction saleTransaction = new SaleTransaction();
            double price = 50.00;

            saleTransaction.setPrice(price);
            SaleTransactionDao.getInstance().save(saleTransaction);

            Integer id = 1;
            Assert.assertEquals(id, saleTransaction.getTicketNumber());

            saleTransaction = SaleTransactionDao.getInstance().findById(id);
            Assert.assertEquals(price, saleTransaction.getPrice(), 0);
            Assert.assertEquals(id, saleTransaction.getTicketNumber());

            List<SaleTransaction> cards = SaleTransactionDao.getInstance()
                    .findByCriteria(new HashMap<String, Object>() {
                        {
                            put("price", price);
                        }
                    });
            saleTransaction = cards.get(0);
            Assert.assertEquals(price, saleTransaction.getPrice(), 0);
            Assert.assertEquals(id, saleTransaction.getTicketNumber());

            SaleTransaction saleTransaction1 = new SaleTransaction();
            double price1 = 50.00;
            saleTransaction1.setPrice(price1);
            SaleTransactionDao.getInstance().save(saleTransaction1);

            List<SaleTransaction> list = SaleTransactionDao.getInstance().findAll();

            Assert.assertEquals(2, list.size());

            SaleTransactionDao.getInstance().delete(saleTransaction);
            SaleTransactionDao.getInstance().delete(saleTransaction1);
            Assert.assertNull(SaleTransactionDao.getInstance().findById(1));
            Assert.assertNull(SaleTransactionDao.getInstance().findById(2));
        }

        @Test
        public void test_TicketEntryDao() {
            TicketEntry ticketEntry = new TicketEntry();
            double price = 50.00;
            String barcode = "1234";

            ticketEntry.setBarCode(barcode);
            ticketEntry.setPricePerUnit(price);
            TicketEntryDao.getInstance().save(ticketEntry);

            Assert.assertEquals(barcode, ticketEntry.getBarCode());

            ticketEntry = TicketEntryDao.getInstance().findById(barcode);
            Assert.assertEquals(price, ticketEntry.getPricePerUnit(), 0);
            Assert.assertEquals(barcode, ticketEntry.getBarCode());

            List<TicketEntry> cards = TicketEntryDao.getInstance().findByCriteria(new HashMap<String, Object>() {
                {
                    put("pricePerUnit", price);
                }
            });
            ticketEntry = cards.get(0);
            Assert.assertEquals(price, ticketEntry.getPricePerUnit(), 0);
            Assert.assertEquals(barcode, ticketEntry.getBarCode());

            TicketEntry ticketEntry1 = new TicketEntry();
            double price1 = 50.00;
            String barcode1 = "1235";

            ticketEntry1.setBarCode(barcode1);
            ticketEntry1.setPricePerUnit(price1);
            TicketEntryDao.getInstance().save(ticketEntry1);

            List<TicketEntry> list = TicketEntryDao.getInstance().findAll();

            Assert.assertEquals(2, list.size());

            TicketEntryDao.getInstance().delete(ticketEntry);
            TicketEntryDao.getInstance().delete(ticketEntry1);
            Assert.assertNull(TicketEntryDao.getInstance().findById(barcode));
            Assert.assertNull(TicketEntryDao.getInstance().findById(barcode1));
        }

        @Test
        public void test_UserDao() {
            User user = new User();
            String username = "Ippolito";

            user.setUsername(username);
            UserDao.getInstance().save(user);

            Integer id = 1;
            Assert.assertEquals(id, user.getId());

            user = UserDao.getInstance().findById(id);
            Assert.assertEquals(username, user.getUsername());
            Assert.assertEquals(id, user.getId());

            List<User> cards = UserDao.getInstance().findByCriteria(new HashMap<String, Object>() {
                {
                    put("username", username);
                }
            });
            user = cards.get(0);
            Assert.assertEquals(username, user.getUsername());
            Assert.assertEquals(id, user.getId());

            User user1 = new User();
            String username1 = "Gandalf";
            user1.setUsername(username1);
            UserDao.getInstance().save(user1);

            List<User> list = UserDao.getInstance().findAll();

            Assert.assertEquals(2, list.size());

            UserDao.getInstance().delete(user);
            UserDao.getInstance().delete(user1);
            Assert.assertNull(UserDao.getInstance().findById(2));
            Assert.assertNull(UserDao.getInstance().findById(3));
        }
    }

}
