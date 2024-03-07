# Unit Testing Documentation

Authors:

Date:

Version:

# Contents

- [Black Box Unit Tests](#black-box-unit-tests)

- [White Box Unit Tests](#white-box-unit-tests)

# Black Box Unit Tests

    <Define here criteria, predicates and the combination of predicates for each function of each class.
    Define test cases to cover all equivalence classes and boundary conditions.
    In the table, report the description of the black box test case and (traceability) the correspondence with the JUnit test case writing the
    class and method name that contains the test case>
    <JUnit test classes must be in src/test/java/it/polito/ezshop   You find here, and you can use,  class TestEzShops.java that is executed
    to start tests
    >

## Class ReturnTransactionManager

### **Class ReturnTransactionManager - method startReturnTransaction**

**Criteria for method startReturnTransaction:**

- Validity of ticketNumber
- Existence with sale transaction with given ticketNumber
- Validity of logged user's role

**Predicates for method startReturnTransaction:**

| Criteria                                              | Predicate     |
| ----------------------------------------------------- | ------------- |
| Validity of ticketNumber                              | Valid         |
|                                                       | NULL          |
| Existence of sale transaction with given ticketNumber | Yes           |
|                                                       | No            |
| Validity of logged user's role                        | Administrator |
|                                                       | Cashier       |
|                                                       | Shop Manager  |
|                                                       | Invalid Role  |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |

**Combination of predicates**:

| Validity of ticketNumber | Existence of sale transaction with given ticketNumber | Validity of logged user's role       | Valid / Invalid | Description of the test case                                                                                                     | JUnit test case                 |
| ------------------------ | ----------------------------------------------------- | ------------------------------------ | --------------- | -------------------------------------------------------------------------------------------------------------------------------- | ------------------------------- |
| \*                       | \*                                                    | Invalid Role                         | Invalid         | Log in with user account that has invalid role <br  /><br  /> startReturnTransaction(1)-->Unauthorized Exception                 | test_1_startReturnTransaction() |
| NULL                     | Yes                                                   | Administrator, Cashier, Shop Manager | Invalid         | startReturnTransaction(NULL) <br  /><br  /> --> InvalidTicketNumberException                                                     | test_2_startReturnTransaction() |
| Valid                    | No                                                    | "                                    | Invalid         | Pass in input a ticket number not present in the DB <br  /><br  /> startReturnTransaction(1000) --> InvalidTicketNumberException | test_3_startReturnTransaction() |
| Valid                    | Yes                                                   | Administrator, Cashier, Shop Manager | Valid           | Pass correct inputs <br  /><br  /> startReturnTransaction (1) --> returnTransactioId                                             | test_4_startReturnTransaction() |

### **Class ReturnTransactionManager - method returnProduct**

**Criteria for method returnProduct:**

- Validity of logged user's role
- Validity of returnId
- Validity of productCode
- Valid returnAmount
- Existence of returnTransaction in DB
- Existence of productCode in linked sale transaction

**Predicates for method returnProduct:**

| Criteria                                            | Predicate        |
| --------------------------------------------------- | ---------------- |
| Validity of returnId                                | Valid            |
|                                                     | NULL             |
| Validity of productCode                             | Valid            |
|                                                     | Empty String     |
|                                                     | NULL             |
| Valid returnAmount                                  | (0, amount sold] |
|                                                     | (minint, 0]      |
|                                                     | > sold amount    |
| Validity of logged user's role                      | Administrator    |
|                                                     | Cashier          |
|                                                     | Shop Manager     |
|                                                     | Invalid Role     |
| Existence of returnTransaction in DB                | Yes              |
|                                                     | No               |
| Existence of productCode in linked sale transaction | Yes              |
|                                                     | No               |

**Boundaries**:

| Criteria           | Boundary values |
| ------------------ | --------------- |
| Valid returnAmount | -1,0            |

**Combination of predicates**:

| Validity of returnId | Existence of returnTransaction in DB | Validity of productCode | Existence of productCode in linked sale transaction | Valid returnAmount | Validity of logged user's role      | Valid / Invalid | Description of the test case                                                                                                                                 | JUnit test case        |
| -------------------- | ------------------------------------ | ----------------------- | --------------------------------------------------- | ------------------ | ----------------------------------- | --------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------ | ---------------------- |
| \*                   | \*                                   | \*                      | \*                                                  | \*                 | Invalid Role                        | Invalid         | Log in with user account that has invalid role <br  /><br  /> returnProduct(_, _, \*)-->Unauthorized Exception                                               | test_1_returnProduct() |
| Valid                | No                                   | \*                      | \*                                                  | \*                 | Administrator, Cashier, ShopManager | Invalid         | returnProduct(-1, _, _)-->InvalidTransactionIdException                                                                                                      | test_2_returnProduct() |
| NULL                 | \*                                   | \*                      | \*                                                  | \*                 | Administrator, Cashier, ShopManager | Invalid         | returnProduct(NULL, _, _)-->InvalidTransactionIdException                                                                                                    | test_3_returnProduct() |
| Valid                | Yes                                  | Empty                   | \*                                                  | \*                 | Administrator, Cashier, ShopManager | Invalid         | returnProduct("1", "", \*)-->InvalidProductCodeException                                                                                                     | test_8_returnProduct() |
| "                    | "                                    | NULL                    | \*                                                  | \*                 | Administrator, Cashier, ShopManager | Invalid         | returnProduct("1", NULL, \*)-->InvalidTransactionIdException                                                                                                 | test_7_returnProduct() |
| "                    | "                                    | Valid                   | No                                                  | \*                 | Administrator, Cashier, ShopManager | Invalid         | returnProduct("1", "6291041500213", \*)-->InvalidProductCodeException                                                                                        | test_4_returnProduct() |
| "                    | "                                    | "                       | Yes                                                 | (minint, 0)        | Administrator, Cashier, ShopManager | Invalid         | returnProduct("1", "6291041500213", -1)-->InvalidQuantityException                                                                                           | test_9_returnProduct() |
| "                    | "                                    | "                       | Yes                                                 | \> sold amount     | Administrator, Cashier, ShopManager | Invalid         | returnProduct("1", "6291041500213", 15)-->InvalidQuantityException                                                                                           | test_5_returnProduct() |
| "                    | "                                    | "                       | Yes                                                 | <= sold amount     | Administrator, Cashier, ShopManager | Valid           | returnProduct("1", "6291041500213", 10)<br  /><br  />-->product amount updated<br  /><br  />-->return price updated<br  /><br  />-->sale transaction updated | test_6_returnProduct() |

### **Class ReturnTransactionManager - method endReturnTransaction**

**Criteria for method endReturnTransaction:**

- Validity of returnId
- Existence of returnTransaction in DB
- Validity of return transaction status
- Validity of logged user's role

**Predicates for method endReturnTransaction:**

| Criteria                              | Predicate     |
| ------------------------------------- | ------------- |
| Validity of returnId                  | Valid         |
|                                       | NULL          |
| Existence of returnTransaction in DB  | Yes           |
|                                       | No            |
| Validity of return transaction status | OPEN          |
|                                       | CLOSED        |
| Validity of logged user's role        | Administrator |
|                                       | Cashier       |
|                                       | Shop Manager  |
|                                       | Invalid Role  |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |

**Combination of predicates**:

| Validity of returnId | Existence of returnTransaction in DB | Validity of return transaction status for given commit | Validity of logged user's role      | Valid / Invalid | Description of the test case                                                                                                                            | JUnit test case               |
| -------------------- | ------------------------------------ | ------------------------------------------------------ | ----------------------------------- | --------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------- |
| \*                   | \*                                   | \*                                                     | Invalid Role                        | Invalid         | Log in with user account that has invalid role <br  /><br  /> endReturnTransaction(_, _)-->Unauthorized Exception                                       | test_1_endReturnTransaction() |
| Valid                | No                                   | \*                                                     | Administrator, Cashier, ShopManager | Invalid         | endReturnTransaction(-1, \*)-->InvalidTransactionIdException                                                                                            | test_2_endReturnTransaction() |
| NULL                 | \*                                   | \*                                                     | "                                   | Invalid         | endReturnTransaction(NULL, \*)-->InvalidTransactionIdException                                                                                          | test_3_endReturnTransaction() |
| Valid                | Yes                                  | CLOSED                                                 | "                                   | Invalid         | endReturnTransaction(1, \*)-->InvalidTransactionIdException                                                                                             | test_6_endReturnTransaction() |
| Valid                | Yes                                  | OPEN                                                   | "                                   | Valid           | endReturnTransaction(1, true)<br  /><br  />--> save return transaction to DB with CLOSED status                                                         | test_4_endReturnTransaction() |
| Valid                | Yes                                  | OPEN                                                   | "                                   | Valid           | endReturnTransaction(1, false)<br  /><br  />--> rollback changes to inventory and sale transaction <br  /><br  /> --> delete return transaction from DB | test_5_endReturnTransaction() |

### **Class ReturnTransactionManager - method deleteReturnTransaction**

**Criteria for method endReturnTransaction:**

- Validity of returnId
- Existence of returnTransaction in DB
- Validity of logged user's role
- Validity of return transaction status

**Predicates for method deleteReturnTransaction:**

| Criteria                              | Predicate     |
| ------------------------------------- | ------------- |
| Validity of returnId                  | Valid         |
|                                       | NULL          |
| Existence of returnTransaction in DB  | Yes           |
|                                       | No            |
| Validity of logged user's role        | Administrator |
|                                       | Cashier       |
|                                       | Shop Manager  |
|                                       | Invalid Role  |
| Validity of return transaction status | OPEN          |
|                                       | CLOSED        |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |

**Combination of predicates**:

| Validity of returnId | Existence of returnTransaction in DB | Validity of return transaction status | Validity of logged user's role      | Valid / Invalid | Description of the test case                                                                                                                       | JUnit test case                  |
| -------------------- | ------------------------------------ | ------------------------------------- | ----------------------------------- | --------------- | -------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------- |
| \*                   | \*                                   | \*                                    | Invalid Role                        | Invalid         | Log in with user account that has invalid role <br  /><br  /> deleteReturnTransaction(\*)-->Unauthorized Exception                                 | test_1_deleteReturnTransaction() |
| Valid                | No                                   | \*                                    | Administrator, Cashier, ShopManager | Invalid         | deleteReturnTransaction(-1)-->InvalidTransactionIdException                                                                                        | test_2_deleteReturnTransaction() |
| NULL                 | \*                                   | \*                                    | \*                                  | Invalid         | deleteReturnTransaction(NULL)-->InvalidTransactionIdException                                                                                      | test_3_deleteReturnTransaction() |
| Valid                | Yes                                  | OPEN                                  | "                                   | Invalid         | deleteReturnTransaction(1)-->InvalidTransactionIdException                                                                                         | test_4_deleteReturnTransaction() |
| Valid                | Yes                                  | CLOSED                                | "                                   | Valid           | deleteReturnTransaction(1)<br  /><br  />--> delete return transaction from DB <br  /><br  />--> rollback changes to inventory and sale transaction | test_5_deleteReturnTransaction() |

### **Class ReturnTransactionManager - method returnCashPayment**

**Criteria for method returnCashPayment:**

- Validity of returnId
- Existence of returnTransaction in DB
- Validity of logged user's role
- Validity of return transaction status

**Predicates for method returnCashPayment:**

| Criteria                              | Predicate     |
| ------------------------------------- | ------------- |
| Validity of returnId                  | Valid         |
|                                       | NULL          |
| Existence of returnTransaction in DB  | Yes           |
|                                       | No            |
| Validity of logged user's role        | Administrator |
|                                       | Cashier       |
|                                       | Shop Manager  |
|                                       | Invalid Role  |
| Validity of return transaction status | OPEN          |
|                                       | CLOSED        |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |

**Combination of predicates**:

| Validity of returnId | Existence of returnTransaction in DB | Validity of return transaction status | Validity of logged user's role      | Valid / Invalid | Description of the test case                                                                                 | JUnit test case            |
| -------------------- | ------------------------------------ | ------------------------------------- | ----------------------------------- | --------------- | ------------------------------------------------------------------------------------------------------------ | -------------------------- |
| \*                   | \*                                   | \*                                    | Invalid Role                        | Invalid         | Log in with user account that has invalid role <br  /><br  /> returnCashPayment(\*)-->Unauthorized Exception | test_1_returnCashPayment() |
| Valid                | No                                   | \*                                    | Administrator, Cashier, ShopManager | Invalid         | returnCashPayment(-1)-->InvalidTransactionIdException                                                        | test_2_returnCashPayment() |
| NULL                 | \*                                   | \*                                    | \*                                  | Invalid         | returnCashPayment(NULL)-->InvalidTransactionIdException                                                      | test_3_returnCashPayment() |
| Valid                | Yes                                  | OPEN                                  | "                                   | Invalid         | returnCashPayment(1)-->InvalidTransactionIdException                                                         | test_4_returnCashPayment() |
| Valid                | Yes                                  | CLOSED                                | "                                   | Valid           | returnCashPayment(1)<br  /><br  />--> record balance update                                                  | test_5_returnCashPayment() |

### **Class ReturnTransactionManager - method returnCreditCardPayment**

**Criteria for method returnCreditCardPayment:**

- Validity of returnId
- Existence of returnTransaction in DB
- Validity of logged user's role
- Validity of return transaction status
- Validity of credit card

**Predicates for method returnCreditCardPayment:**

| Criteria                              | Predicate                             |
| ------------------------------------- | ------------------------------------- |
| Validity of returnId                  | Valid                                 |
|                                       | NULL                                  |
| Existence of returnTransaction in DB  | Yes                                   |
|                                       | No                                    |
| Validity of logged user's role        | Administrator                         |
|                                       | Cashier                               |
|                                       | Shop Manager                          |
|                                       | Invalid Role                          |
| Validity of return transaction status | OPEN                                  |
|                                       | CLOSED                                |
| Validity of credit card               | Valid (checked with Luhn's algorithm) |
|                                       | Empty                                 |
|                                       | NULL                                  |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |

**Combination of predicates**:

| Validity of returnId | Existence of returnTransaction in DB | Validity of return transaction status | Validity of credit card | Validity of logged user's role      | Valid / Invalid | Description of the test case                                                                                         | JUnit test case                  |
| -------------------- | ------------------------------------ | ------------------------------------- | ----------------------- | ----------------------------------- | --------------- | -------------------------------------------------------------------------------------------------------------------- | -------------------------------- |
| \*                   | \*                                   | \*                                    | \*                      | Invalid Role                        | Invalid         | Log in with user account that has invalid role <br  /><br  /> returnCreditCardPayment(_, _)-->Unauthorized Exception | test_1_returnCreditCardPayment() |
| Valid                | No                                   | \*                                    | \*                      | Administrator, Cashier, ShopManager | Invalid         | returnCreditCardPayment(-1, \*)-->InvalidTransactionIdException                                                      | test_2_returnCreditCardPayment() |
| NULL                 | \*                                   | \*                                    | \*                      | "                                   | Invalid         | returnCreditCardPayment(NULL, \*)-->InvalidTransactionIdException                                                    | test_3_returnCreditCardPayment() |
| Valid                | Yes                                  | OPEN                                  | \*                      | "                                   | Invalid         | returnCreditCardPayment(1, \*)-->InvalidTransactionIdException                                                       | test_7_returnCreditCardPayment() |
| Valid                | Yes                                  | CLOSED                                | Empty                   | "                                   | Invalid         | returnCreditCardPayment(1, "")-->InvalidCreditCardException                                                          | test_4_returnCreditCardPayment() |
| Valid                | Yes                                  | CLOSED                                | NULL                    | "                                   | Invalid         | returnCreditCardPayment(1, NULL)-->InvalidCreditCardException                                                        | test_5_returnCreditCardPayment() |
| Valid                | Yes                                  | CLOSED                                | Valid                   | "                                   | Valid           | returnCashPayment(1)<br  /><br  />--> record balance update                                                          | test_6_returnCreditCardPayment() |

## Class CustomerManager

### **Class CustomerManager - method defineCustomer**

**Criteria for method defineCustomer:**

- Validity of customerName
- Validity of logged user's role

**Predicates for method defineCustomer:**

| Criteria                       | Predicate              |
| ------------------------------ | ---------------------- |
| Validity of customerName       | Valid (must be unique) |
|                                | Empty                  |
|                                | NULL                   |
| Validity of logged user's role | Administrator          |
|                                | Cashier                |
|                                | Shop Manager           |
|                                | Invalid Role           |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |

**Combination of predicates**:

| Validity of customerName | Validity of logged user's role       | Valid / Invalid | Description of the test case                                                                                   | JUnit test case         |
| ------------------------ | ------------------------------------ | --------------- | -------------------------------------------------------------------------------------------------------------- | ----------------------- |
| \*                       | Invalid Role                         | Invalid         | Log in with user account that has invalid role <br  /><br  /> defineCustomer(\*)-->Unauthorized Exception      | test_4_defineCustomer() |
| NULL                     | Administrator, Cashier, Shop Manager | Invalid         | startReturnTransaction(NULL) <br  /><br  /> --> InvalidCustomerNameException                                   | test_2_defineCustomer() |
| Empty                    | "                                    | Invalid         | startReturnTransaction("") <br  /><br  /> --> InvalidCustomerNameException                                     | test_1_defineCustomer() |
| Valid                    | "                                    | Invalid         | defineCustomer("Genoveffa"); <br  />defineCustomer("Genoveffa")<br  /><br  /> --> InvalidCustomerNameException | test_3_defineCustomer() |
| Valid                    | Administrator, Cashier, Shop Manager | Valid           | Pass correct inputs <br  /><br  /> defineCustomer ("Genoveffa") --> customer saved in DB, id returned          | test_5_defineCustomer() |

### **Class CustomerManager - method deleteCustomer**

**Criteria for method deleteCustomer:**

- Validity of customerId
- Existence of customer with given id
- Validity of logged user's role

**Predicates for method deleteCustomer:**

| Criteria                            | Predicate     |
| ----------------------------------- | ------------- |
| Validity of customerId              | Valid         |
|                                     | NULL          |
| Existence of customer with given id | Yes           |
|                                     | No            |
| Validity of logged user's role      | Administrator |
|                                     | Cashier       |
|                                     | Shop Manager  |
|                                     | Invalid Role  |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |

**Combination of predicates**:

| Validity of customerId | Existence of customer with given id | Validity of logged user's role       | Valid / Invalid | Description of the test case                                                                              | JUnit test case         |
| ---------------------- | ----------------------------------- | ------------------------------------ | --------------- | --------------------------------------------------------------------------------------------------------- | ----------------------- |
| \*                     | \*                                  | Invalid Role                         | Invalid         | Log in with user account that has invalid role <br  /><br  /> deleteCustomer(\*)-->Unauthorized Exception | test_1_deleteCustomer() |
| NULL                   | \*                                  | Administrator, Cashier, Shop Manager | Invalid         | deleteCustomer(NULL) <br  /><br  /> --> InvalidCustomerIdException                                        | test_2_deleteCustomer() |
| Valid                  | No                                  | "                                    | Invalid         | deleteCustomer(-1);<br  /><br  /> --> InvalidCustomerIdException                                          | test_3_deleteCustomer() |
| Valid                  | Yes                                 | Administrator, Cashier, Shop Manager | Valid           | Pass correct inputs <br  /><br  /> deleteCustomer (1) --> customer deleted from DB                        | test_4_deleteCustomer() |

### **Class CustomerManager - method modifyCustomer**

**Criteria for method modifyCustomer:**

- Validity of customerId
- Existence of customer with given id
- Validity of newCustomerName
- Validity of newCustomerCard
- Validity of logged user's role

**Predicates for method modifyCustomer:**

| Criteria                            | Predicate              |
| ----------------------------------- | ---------------------- |
| Validity of customerId              | Valid                  |
|                                     | NULL                   |
| Existence of customer with given id | Yes                    |
|                                     | No                     |
| Validity of newCustomerName         | Valid (must be unique) |
|                                     | Empty                  |
|                                     | NULL                   |
| Validity of newCustomerCard         | Valid                  |
|                                     | length != 10           |
|                                     | Empty                  |
|                                     | NULL                   |
| Validity of logged user's role      | Administrator          |
|                                     | Cashier                |
|                                     | Shop Manager           |
|                                     | Invalid Role           |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |

**Combination of predicates**:

| Validity of customerId | Existence of customer with given id | Validity of newCustomerName | Validity of newCustomerCard | Validity of logged user's role       | Valid / Invalid | Description of the test case                                                                                                                       | JUnit test case          |
| ---------------------- | ----------------------------------- | --------------------------- | --------------------------- | ------------------------------------ | --------------- | -------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------ |
| \*                     | \*                                  | \*                          | \*                          | Invalid Role                         | Invalid         | Log in with user account that has invalid role <br  /><br  /> modifyCustomer(_, _, \*)-->Unauthorized Exception                                    | test_1_modifyCustomer()  |
| NULL                   | \*                                  | \*                          | \*                          | Administrator, Cashier, Shop Manager | Invalid         | modifyCustomer(NULL, _, _)-->InvalidCustomerIdException                                                                                            | test_4_modifyCustomer()  |
| Valid                  | No                                  | \*                          | \*                          | "                                    | Invalid         | modifyCustomer(-1, _, _)-->InvalidCustomerIdException                                                                                              | test_5_modifyCustomer()  |
| "                      | Yes                                 | NULL                        | \*                          | "                                    | Invalid         | modifyCustomer(1, NULL, \*)-->InvalidCustomerNameException                                                                                         | test_3_modifyCustomer()  |
| "                      | "                                   | Valid                       | \*                          | "                                    | Invalid         | defineCustomer("Salomone")-->id=1<br  />defineCustomer("Genoveffa")-->id=2<br  />modifyCustomer(1, "Genoveffa", \*)-->InvalidCustomerNameException | test_2_modifyCustomer()  |
| "                      | "                                   | Valid                       | length != 10                | "                                    | Invalid         | defineCustomer("Salomone")-->id=1<br  /> modifyCustomer(1, \*, "1")-->InvalidCustomerCardException                                                 | test_6_modifyCustomer()  |
| "                      | "                                   | Empty                       | \*                          | "                                    | Valid           | modifyCustomer(1, "", \*)-->customer name is unchanged                                                                                             | test_7_modifyCustomer()  |
| "                      | "                                   | Valid                       | Empty                       | "                                    | Valid           | defineCustomer("Salomone")-->id=1; <br   />modifyCustomer(1, "Genoveffa", "")<br   />-->customer name is changed                                   | test_8_modifyCustomer()  |
| "                      | "                                   | "                           | NULL                        | "                                    | Valid           | defineCustomer("Salomone")-->id=1; modifyCustomer(1, \*, NULL)<br   />-->customer card is detached and deleted                                     | test_11_modifyCustomer() |
| "                      | "                                   | "                           | Valid                       | "                                    | Valid           | defineCustomer("Salomone")-->id=1; modifyCustomer(1, \*, "1000000001")<br   />-->customer card is attached                                         | test_9_modifyCustomer()  |

### **Class CustomerManager - method attachCardToCustomer**

**Criteria for method attachCardToCustomer:**

- Validity of customerId
- Existence of customer with given id
- Validity of customerCard
- Validity of logged user's role

**Predicates for method attachCardToCustomer:**

| Criteria                            | Predicate     |
| ----------------------------------- | ------------- |
| Validity of customerId              | Valid         |
|                                     | NULL          |
| Existence of customer with given id | Yes           |
|                                     | No            |
| Validity of customerCard            | Valid         |
|                                     | length != 10  |
|                                     | Empty         |
|                                     | NULL          |
| Validity of logged user's role      | Administrator |
|                                     | Cashier       |
|                                     | Shop Manager  |
|                                     | Invalid Role  |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |

**Combination of predicates**:

| Validity of customerId | Existence of customer with given id | Validity of customerCard | Validity of logged user's role       | Valid / Invalid | Description of the test case                                                                                      | JUnit test case               |
| ---------------------- | ----------------------------------- | ------------------------ | ------------------------------------ | --------------- | ----------------------------------------------------------------------------------------------------------------- | ----------------------------- |
| \*                     | \*                                  | \*                       | Invalid Role                         | Invalid         | Log in with user account that has invalid role <br  /><br  /> attachCardToCustomer(_, _)-->Unauthorized Exception | test_1_attachCardToCustomer() |
| NULL                   | \*                                  | \*                       | Administrator, Cashier, Shop Manager | Invalid         | attachCardToCustomer(\*, NULL)-->InvalidCustomerIdException                                                       | test_2_attachCardToCustomer() |
| Valid                  | No                                  | \*                       | "                                    | Invalid         | attachCardToCustomer(\*, -1)-->InvalidCustomerIdException                                                         | test_3_attachCardToCustomer() |
| "                      | Yes                                 | length != 10             | "                                    | Invalid         | attachCardToCustomer("1", \*)-->InvalidCustomerCardException                                                      | test_4_attachCardToCustomer() |
| "                      | "                                   | Empty                    | "                                    | Invalid         | attachCardToCustomer("1", "")-->InvalidCustomerCardException                                                      | test_5_attachCardToCustomer() |
| "                      | "                                   | NULL                     | "                                    | Invalid         | attachCardToCustomer("1", NULL)-->InvalidCustomerCardException                                                    | test_6_attachCardToCustomer() |
| "                      | "                                   | Valid                    | "                                    | Valid           | defineCustomer("Salomone")-->id=1; attachCardToCustomer("1", "1000000001")<br   />-->customer card is attached    | test_7_attachCardToCustomer() |

### **Class CustomerManager - method modifyPointsOnCard**

**Criteria for method modifyPointsOnCard:**

- Validity of customerCard
- Existence of customer card in DB
- Validity of pointsToBeAdded
- Validity of logged user's role

**Predicates for method modifyPointsOnCard:**

| Criteria                         | Predicate                             |
| -------------------------------- | ------------------------------------- |
| Validity of customerCard         | Valid                                 |
|                                  | length != 10                          |
|                                  | Empty                                 |
|                                  | NULL                                  |
| Existence of customer card in DB | Yes                                   |
|                                  | No                                    |
| Validity of pointsToBeAdded      | current points + pointsToBeAdded >= 0 |
|                                  | current points + pointsToBeAdded < 0  |
| Validity of logged user's role   | Administrator                         |
|                                  | Cashier                               |
|                                  | Shop Manager                          |
|                                  | Invalid Role                          |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |

**Combination of predicates**:

| Validity of customerCard | Existence of customer card in DB | Validity of pointsToBeAdded           | Validity of logged user's role       | Valid / Invalid | Description of the test case                                                                                    | JUnit test case             |
| ------------------------ | -------------------------------- | ------------------------------------- | ------------------------------------ | --------------- | --------------------------------------------------------------------------------------------------------------- | --------------------------- |
| \*                       | \*                               | \*                                    | Invalid Role                         | Invalid         | Log in with user account that has invalid role <br  /><br  /> modifyPointsOnCard(_, _)-->Unauthorized Exception | test_1_modifyPointsOnCard() |
| length != 10             | \*                               | \*                                    | Administrator, Cashier, Shop Manager | Invalid         | modifyPointsOnCard("1", \*)-->InvalidCustomerCardException                                                      | test_3_modifyPointsOnCard() |
| Empty                    | \*                               | \*                                    | "                                    | Invalid         | modifyPointsOnCard("", \*)-->InvalidCustomerCardException                                                       | test_7_modifyPointsOnCard() |
| NULL                     | \*                               | \*                                    | "                                    | Invalid         | modifyPointsOnCard(NULL, \*)-->InvalidCustomerCardException                                                     | test_2_modifyPointsOnCard() |
| Valid                    | No                               | \*                                    | "                                    | Invalid         | modifyPointsOnCard("1100000000", \*)--> InvalidCustomerCardException                                            | test_4_modifyPointsOnCard() |
| "                        | Yes                              | current points + pointsToBeAdded < 0  | "                                    | Invalid         | modifyPointsOnCard("1100000000", -1)--> InvalidCustomerCardException                                            | test_5_modifyPointsOnCard() |
| "                        | "                                | current points + pointsToBeAdded >= 0 | "                                    | Valid           | modifyPointsOnCard("1100000000", 10)--> points on card updated                                                  | test_6_modifyPointsOnCard() |

## Class CustomerCardManager

### **Class CustomerCardManager - method createCard**

**Criteria for method createCard:**

- Validity of logged user's role

**Predicates for method createCard:**

| Criteria                       | Predicate     |
| ------------------------------ | ------------- |
| Validity of logged user's role | Administrator |
|                                | Cashier       |
|                                | Shop Manager  |
|                                | Invalid Role  |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |

**Combination of predicates**:

| Validity of logged user's role       | Valid / Invalid | Description of the test case                                                                        | JUnit test case     |
| ------------------------------------ | --------------- | --------------------------------------------------------------------------------------------------- | ------------------- |
| Invalid Role                         | Invalid         | Log in with user account that has invalid role <br  /><br  /> createCard()-->Unauthorized Exception | test_1_createCard() |
| Administrator, Cashier, Shop Manager | Valid           | createCard()<br  />-->10 digit unique card code returned and stored in DB                           | test_2_createCard() |

### **Class CustomerCardManager - method deleteCard**

**Criteria for method deleteCard:**

- Validity of logged user's role
- Validity of cardCode
- Existence of card in DB

**Predicates for method deleteCard:**

| Criteria                       | Predicate     |
| ------------------------------ | ------------- |
| Validity of logged user's role | Administrator |
|                                | Cashier       |
|                                | Shop Manager  |
|                                | Invalid Role  |
| Validity of cardCode           | Valid         |
|                                | length != 10  |
|                                | NULL          |
| Existence of card in DB        | Yes           |
|                                | No            |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |

**Combination of predicates**:

| Validity of logged user's role       | Validity of cardCode | Existence of card in DB | Valid / Invalid | Description of the test case                                                                          | JUnit test case     |
| ------------------------------------ | -------------------- | ----------------------- | --------------- | ----------------------------------------------------------------------------------------------------- | ------------------- |
| Invalid Role                         | \*                   | \*                      | Invalid         | Log in with user account that has invalid role <br  /><br  /> deleteCard(\*)-->Unauthorized Exception | test_1_deleteCard() |
| Administrator, Cashier, Shop Manager | NULL                 | \*                      | Invalid         | deleteCard(NULL)<br  />-->InvalidCustomerCardException                                                | test_2_deleteCard() |
| "                                    | length != 10         | \*                      | Invalid         | deleteCard("1")<br  />-->InvalidCustomerCardException                                                 | test_3_deleteCard() |
| "                                    | Valid                | No                      | Invalid         | deleteCard("1000000001")<br  />-->InvalidCustomerCardException                                        | test_4_deleteCard() |
| "                                    | "                    | Yes                     | Valid           | deleteCard("1000000001")<br  />-->customer card removed from DB                                       | test_5_deleteCard() |

### **Class CustomerCardManager - method attachCard**

**Criteria for method attachCard:**

- Validity of logged user's role
- Validity of cardCode
- Existence of card in DB
- Validity of customer id

**Predicates for method attachCard:**

| Criteria                       | Predicate     |
| ------------------------------ | ------------- |
| Validity of logged user's role | Administrator |
|                                | Cashier       |
|                                | Shop Manager  |
|                                | Invalid Role  |
| Validity of cardCode           | Valid         |
|                                | length != 10  |
|                                | NULL          |
| Existence of card in DB        | Yes           |
|                                | No            |
| Validity of customer id        | Valid         |
|                                | NULL          |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |

**Combination of predicates**:

| Validity of logged user's role       | Validity of cardCode | Existence of card in DB | Validity of customer id | Valid / Invalid | Description of the test case                                                                            | JUnit test case     |
| ------------------------------------ | -------------------- | ----------------------- | ----------------------- | --------------- | ------------------------------------------------------------------------------------------------------- | ------------------- |
| Invalid Role                         | \*                   | \*                      | \*                      | Invalid         | Log in with user account that has invalid role <br  /><br  /> attachCard(_, _)-->Unauthorized Exception | test_1_attachCard() |
| Administrator, Cashier, Shop Manager | NULL                 | \*                      | \*                      | Invalid         | attachCard(NULL, \*)<br  />-->InvalidCustomerCardException                                              | test_2_attachCard() |
| "                                    | length != 10         | \*                      | \*                      | Invalid         | attachCard("1", \*)<br  />-->InvalidCustomerCardException                                               | test_3_attachCard() |
| "                                    | Valid                | No                      | \*                      | Invalid         | attachCard("1000000001", \*)<br  />-->InvalidCustomerCardException                                      | test_4_attachCard() |
| "                                    | "                    | Yes                     | NULL                    | Invalid         | attachCard("1000000001", NULL)<br  />-->InvalidCustomerIdException                                      | test_5_attachCard() |
| "                                    | "                    | "                       | Valid                   | Valid           | attachCard("1000000001", 1)<br  />-->card attached to customer                                          | test_6_attachCard() |

### Class _OrderManager_ - method _issueOrder_

- productCode
- quantity
- pricePerUnit
- orderId
- product exists

**Predicates for method _issueOrder_ :**

| Criteria     | Predicate              |
| ------------ | ---------------------- |
| productCode  | null                   |
|              | ""                     |
|              | productCode.length <12 |
|              | productCode.length >14 |
|              | wrong check digit      |
|              | not number             |
|              | valid check digit      |
| quantity     | (minint,0)             |
|              | [0,maxint)             |
| pricePerUnit | (mindouble,0)          |
|              | [0,maxdouble)          |

**Boundaries**:

| Criteria     | Boundary values |
| ------------ | --------------- |
| quantity     | -1,0            |
| pricePerUnit | -1,0            |

**Combination of predicates**:

| productCode            | quantity   | pricePerUnit  | product exists | Valid / Invalid | Description of the test case                                                          | JUnit test case    |
| ---------------------- | ---------- | ------------- | -------------- | --------------- | ------------------------------------------------------------------------------------- | ------------------ |
| null                   | \*         | \*            | \*             | Invalid         | T(null,1,1.0) -> error                                                                | test_1_issueOrder  |
| ""                     | \*         | \*            | \*             | Invalid         | T("",1,1.0)-> error                                                                   | test_2_issueOrder  |
| productCode.length <12 | \*         | \*            | \*             | Invalid         | T("123456",1,1.0)-> error                                                             | test_3_issueOrder  |
| productCode.length >14 | \*         | \*            | \*             | Invalid         | T("123123123123123",1,1.0) -> error                                                   | test_4_issueOrder  |
| wrong check digit      | \*         | \*            | \*             | Invalid         | T("123123123123",1,1.0) -> error                                                      | test_5_issueOrder  |
| not number             | \*         | \*            | \*             | Invalid         | T("asdasdasdasd",1,1.0) -> error                                                      | test_6_issueOrder  |
| \*                     | (minint,0) | \*            | \*             | Invalid         | T("123123123125",-1,1.0) -> error                                                     | test_7_issueOrder  |
| \*                     | \*         | (mindouble,0) | \*             | Invalid         | T("123123123125",1,-1.0) -> error                                                     | test_8_issueOrder  |
| valid check digit      | [0,maxint) | [0,maxdouble) | yes            | Valid           | T("1231231231232",1,1.0),T("12312312312333",1,1.0),T("123123123125",1,1.0) ->integer  | test_9_issueOrder  |
| valid check digit      | [0,maxint) | [0,maxdouble) | no             | Valid           | T("1231231231232",1,1.0),T("12312312312333",1,1.0),T("123123123125",1,1.0) -> integer | test_10_issueOrder |

### Class _OrderManager_ - method _payOrder_

- orderId
- state

**Predicates for method _payOrder_ :**

| Criteria | Predicate             |
| -------- | --------------------- |
| orderId  | (minint,0)            |
|          | [0,maxint)            |
| state    | ORDERED,ISSUED,ISSUED |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
| orderId  | -1,0            |

**Combination of predicates**:

| orderId    | state     | Valid / Invalid | Description of the test case | JUnit test case |
| ---------- | --------- | --------------- | ---------------------------- | --------------- |
| (minint,0) | \*        | Invalid         | T(-1) -> error               | test_1_payOrder |
| [0,maxint) | ORDERED   | Valid           | T(1) -> true                 | test_2_payOrder |
| [0,maxint) | PAYED     | Valid           | T(1) -> true                 | test_3_payOrder |
| [0,maxint) | ISSUED    | Valid           | T(1) -> true                 | test_4_payOrder |
| [0,maxint) | COMPLETED | Valid           | T(1) -> false                | test_5_payOrder |

### Class _OrderManager_ - method _recordOrderArrival_

- orderId
- state

**Predicates for method _recordOrderArrival_ :**

| Criteria | Predicate            |
| -------- | -------------------- |
| orderId  | (minint,0)           |
|          | [0,maxint)           |
| state    | ORDERED,ISSUED,PAYED |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
| orderId  | -1,0            |

**Combination of predicates**:

| orderId    | state     | Valid / Invalid | Description of the test case | JUnit test case           |
| ---------- | --------- | --------------- | ---------------------------- | ------------------------- |
| (minint,0) | \*        | Invalid         | T(-1) -> error               | test_1_recordOrderArrival |
| [0,maxint) | ORDERED   | Valid           | T(1) -> false                | test_2_recordOrderArrival |
| [0,maxint) | PAYED     | Valid           | T(1) -> true                 | test_3_recordOrderArrival |
| [0,maxint) | ISSUED    | Valid           | T(1) -> false                | test_4_recordOrderArrival |
| [0,maxint) | COMPLETED | Valid           | T(1) -> true                 | test_5_recordOrderArrival |

### Class _BalanceOperationManager_ - method _getCreditsAndDebits_

- from
- to

**Predicates for method _getCreditsAndDebits_ :**

| Criteria | Predicate |
| -------- | --------- |
| from     | null      |
|          | <= to     |
|          | > to      |
| to       | null      |
|          | >= from   |
|          | < from    |

**Combination of predicates**:

| from  | to      | Valid / Invalid | Description of the test case | JUnit test case            |
| ----- | ------- | --------------- | ---------------------------- | -------------------------- |
| null  | \*      | Valid           | T(null,'2021-06-26')         | test_1_getCreditsAndDebits |
| \*    | null    | Valid           | T('2021-06-26',null)         | test_2_getCreditsAndDebits |
| null  | null    | Valid           | T(null,null)                 | test_3_getCreditsAndDebits |
| <= to | \*      | Valid           | T('2021-06-26','2021-06-26') | test_4_getCreditsAndDebits |
| > to  | \*      | Invalid         | T('2021-06-26','2021-06-25') | test_5_getCreditsAndDebits |
| \*    | < from  | Invalid         | T('2021-06-26','2021-06-25') | test_6_getCreditsAndDebits |
| \*    | >= from | Valid           | T('2021-06-26','2021-06-26') | test_7_getCreditsAndDebits |

### Class _OrderManager_ - method _recordBalanceUpdate_

- toBeAdded
- currentBalance
- newBalance

**Predicates for method _recordBalanceUpdate_ :**

| Criteria       | Predicate     |
| -------------- | ------------- |
| toBeAdded      | [mindouble,0) |
| toBeAdded      | (0,maxdouble] |
| currentBalance | (0,maxdouble] |
| newBalance     | (0,maxdouble] |
| newBalance     | [mindouble,0) |

**Combination of predicates**:

| toBeAdded     | currentBalance | newBalance     | Valid / Invalid | Description of the test case | JUnit test case            |
| ------------- | -------------- | -------------- | --------------- | ---------------------------- | -------------------------- |
| [mindouble,0) | \*             | [mindouble,0)  | Invalid         | T(-1) -> error               | test_1_recordBalanceUpdate |
| ""            | \*             | (0,maxdouble]) | Valid           | T(-1) -> error               | test_2_recordBalanceUpdate |
| (0,maxdouble] | \*             | \*             | Valid           | T(1)                         | test_3_recordBalanceUpdate |

### Class _ProductTypeManager_ - method _createProductType_

- description
- productCode
- pricePerUnit
- note

**Predicates for method _createProductType_ :**

| Criteria     | Predicate              |
| ------------ | ---------------------- |
| description  | null                   |
|              | ""                     |
| productCode  | null                   |
|              | ""                     |
|              | productCode.length <12 |
|              | productCode.length >14 |
|              | wrong check digit      |
|              | not number             |
|              | valid check digit      |
| pricePerUnit | (mindouble,0)          |
|              | (0,maxdouble)          |
| note         | \*                     |

**Boundaries**:

| Criteria     | Boundary values |
| ------------ | --------------- |
| pricePerUnit | -1,0            |

**Combination of predicates**:

| productCode            | description | pricePerUnit  | note | Valid / Invalid | Description of the test case        | JUnit test case           |
| ---------------------- | ----------- | ------------- | ---- | --------------- | ----------------------------------- | ------------------------- |
| null                   | \*          | \*            | \*   | Invalid         | T(null,"asd",1.0,null)              | test_1_createProductType  |
| ""                     | \*          | \*            | \*   | Invalid         | T("","asd",1.0,null)                | test_2_createProductType  |
| productCode.length <12 | \*          | \*            | \*   | Invalid         | T("12312312312","asd",1.0,null)     | test_3_createProductType  |
| productCode.length >14 | \*          | \*            | \*   | Invalid         | T("123123123123123","asd",1.0,null) | test_4_createProductType  |
| wrong check digit      | \*          | \*            | \*   | Invalid         | T("12312312312310","asd",1.0,null)  | test_5_createProductType  |
| not number             | \*          | \*            | \*   | Invalid         | T("1231231231231a","asd",1.0,null)  | test_6_createProductType  |
| \*                     | null        | \*            | \*   | Invalid         | T("12312312312319",null,1.0,null)   | test_7_createProductType  |
| \*                     | ""          | \*            | \*   | Invalid         | T("12312312312319","",1.0,null)     | test_8_createProductType  |
| \*                     | \*          | (mindouble,0) | \*   | Invalid         | T("12312312312319","",0,null)       | test_9_createProductType  |
| valid check digit      | \*          | [0,maxdouble) | \*   | Valid           | T("12312312312319","asd",1.0,null)  | test_10_createProductType |

### Class _ProductTypeManager_ - method _updateProduct_

- id
- newDescription
- newCode
- newPrice
- newNote
- existingProductCode

**Predicates for method _updateProduct_ :**

| Criteria            | Predicate              |
| ------------------- | ---------------------- |
| id                  | (minint,0)             |
|                     | [1,maxint]             |
| newDescription      | null                   |
|                     | ""                     |
| newCode             | null                   |
|                     | ""                     |
|                     | productCode.length <12 |
|                     | productCode.length >14 |
|                     | wrong check digit      |
|                     | not number             |
|                     | valid check digit      |
| newPrice            | (mindouble,0)          |
|                     | (0,maxdouble)          |
| newNote             | \*                     |
| existingProductCode | null                   |
| existingProductCode | !=null                 |

**Boundaries**:

| Criteria     | Boundary values |
| ------------ | --------------- |
| pricePerUnit | -1,0            |

**Combination of predicates**:

| productCode            | description | pricePerUnit  | existingProductCode | note | Valid / Invalid | Description of the test case        | JUnit test case       |
| ---------------------- | ----------- | ------------- | ------------------- | ---- | --------------- | ----------------------------------- | --------------------- |
| null                   | \*          | \*            | \*                  | \*   | Invalid         | T(null,"asd",1.0,null)              | test_1_updateProduct  |
| ""                     | \*          | \*            | \*                  | \*   | Invalid         | T("","asd",1.0,null)                | test_2_updateProduct  |
| productCode.length <12 | \*          | \*            | \*                  | \*   | Invalid         | T("12312312312","asd",1.0,null)     | test_3_updateProduct  |
| productCode.length >14 | \*          | \*            | \*                  | \*   | Invalid         | T("123123123123123","asd",1.0,null) | test_4_updateProduct  |
| wrong check digit      | \*          | \*            | \*                  | \*   | Invalid         | T("12312312312310","asd",1.0,null)  | test_5_updateProduct  |
| not number             | \*          | \*            | \*                  | \*   | Invalid         | T("1231231231231a","asd",1.0,null)  | test_6_updateProduct  |
| \*                     | null        | \*            | \*                  | \*   | Invalid         | T("12312312312319",null,1.0,null)   | test_7_updateProduct  |
| \*                     | ""          | \*            | \*                  | \*   | Invalid         | T("12312312312319","",1.0,null)     | test_8_updateProduct  |
| \*                     | \*          | (mindouble,0) | \*                  | \*   | Invalid         | T("12312312312319","",0,null)       | test_9_updateProduct  |
| valid check digit      | \*          | [0,maxdouble) | null                | \*   | Valid           | T("12312312312319","asd",1.0,null)  | test_10_updateProduct |
| valid check digit      | \*          | [0,maxdouble) | !null               | \*   | Invalid         | T("12312312312319","asd",1.0,null)  | test_11_updateProduct |

### Class _ProductTypeManager_ - method _checkProductCode_

- productCode

**Predicates for method _checkProductCode_ :**

| Criteria    | Predicate              |
| ----------- | ---------------------- |
| productCode | null                   |
|             | ""                     |
|             | productCode.length <12 |
|             | productCode.length >14 |
|             | wrong check digit      |
|             | not number             |
|             | valid check digit      |

**Combination of predicates**:

| productCode            | Valid / Invalid | Description of the test case | JUnit test case         |
| ---------------------- | --------------- | ---------------------------- | ----------------------- |
| null                   | Invalid         | T(null)                      | test_1_checkProductCode |
| ""                     | Invalid         | T("")                        | test_2_checkProductCode |
| productCode.length <12 | Invalid         | T("12312312312")             | test_3_checkProductCode |
| productCode.length >14 | Invalid         | T("123123123123123")         | test_4_checkProductCode |
| wrong check digit      | Invalid         | T("12312312312310")          | test_5_checkProductCode |
| not number             | Invalid         | T("1231231231231a")          | test_6_checkProductCode |

### Class _ProductTypeManager_ - method _deleteProductType_

- productCode
- productExists

**Predicates for method _deleteProductType_ :**

| Criteria      | Predicate  |
| ------------- | ---------- |
| id            | (minint,0) |
|               | (0,maxint] |
| productExists | yes        |
| productExists | no         |

**Combination of predicates**:

| id         | productExists | Valid / Invalid | Description of the test case | JUnit test case          |
| ---------- | ------------- | --------------- | ---------------------------- | ------------------------ |
| (minint,0) | \*            | Invalid         | T(-1),T(0)                   | test_1_deleteProductType |
| (0,maxint] | yes           | Valid           | T(1)                         | test_2_deleteProductType |
| (0,maxint] | no            | Valid           | T(1)                         | test_3_deleteProductType |

### Class _ProductTypeManager_ - method _getProductTypeByBarCode_

- productCode
- product exists

**Predicates for method _getProductTypeByBarCode_ :**

| Criteria       | Predicate              |
| -------------- | ---------------------- |
| productCode    | null                   |
|                | ""                     |
|                | productCode.length <12 |
|                | productCode.length >14 |
|                | wrong check digit      |
|                | not number             |
|                | valid check digit      |
|                | valid check digit      |
| product exists | yes                    |
|                | no                     |

**Combination of predicates**:

| productCode            | product exists | Valid / Invalid | Description of the test case        | JUnit test case                |
| ---------------------- | -------------- | --------------- | ----------------------------------- | ------------------------------ |
| null                   | \*             | Invalid         | T(null,"asd",1.0,null)              | test_1_getProductTypeByBarCode |
| ""                     | \*             | Invalid         | T("","asd",1.0,null)                | test_2_getProductTypeByBarCode |
| productCode.length <12 | \*             | Invalid         | T("12312312312","asd",1.0,null)     | test_3_getProductTypeByBarCode |
| productCode.length >14 | \*             | Invalid         | T("123123123123123","asd",1.0,null) | test_4_getProductTypeByBarCode |
| wrong check digit      | \*             | Invalid         | T("12312312312310","asd",1.0,null)  | test_5_getProductTypeByBarCode |
| not number             | \*             | Invalid         | T("1231231231231a","asd",1.0,null)  | test_6_getProductTypeByBarCode |
| \*                     | yes            | Valid           | T("1231231231231a","asd",1.0,null)  | test_8_getProductTypeByBarCode |
| \*                     | no             | Valid           | T("1231231231231a","asd",1.0,null)  | test_9_getProductTypeByBarCode |

### Class _ProductTypeManager_ - method _getProductTypeByDesciption_

- productCode

**Predicates for method _getProductTypeByDesciption_ :**

| Criteria    | Predicate |
| ----------- | --------- |
| description | \*        |

**Combination of predicates**:

| description | Valid / Invalid | Description of the test case | JUnit test case                   |
| ----------- | --------------- | ---------------------------- | --------------------------------- |
| \*          | Valid           | T(""),T(null),T("asd")       | test_1_getProductTypeByDesciption |

### Class _ProductTypeManager_ - method _updateQuantity_

- productId
- toBeAdded
- productExists

**Predicates for method _updateQuantity_ :**

| Criteria        | Predicate    |
| --------------- | ------------ |
| productId       | (minint,0)   |
|                 | (0,maxint)   |
| toBeAdded       | (minint,0)   |
| toBeAdded       | (0,maxint]   |
| currentQuantity | >= toBeAdded |
| productExists   | yes          |
| productExists   | no           |

**Combination of predicates**:

| productId  | toBeAdded  | currentQuantity | productExists | Valid / Invalid | Description of the test case | JUnit test case       |
| ---------- | ---------- | --------------- | ------------- | --------------- | ---------------------------- | --------------------- |
| (minint,0) | \*         | \*              | \*            | Invalid         | T(-1,1,1)                    | test_1_updateQuantity |
| (0,maxint) | (minint,0) | >= toBeAdded    | no            | Valid           | T(1,-1,1)                    | test_2_updateQuantity |
| (0,maxint) | (minint,0) | >= toBeAdded    | yes           | Valid           | T(1,-1,1)                    | test_3_updateQuantity |
| (0,maxint) | (0,maxint] | \*              | yes           | Valid           | T(1,1,1)                     | test_4_updateQuantity |
| (0,maxint) | (0,maxint] | \*              | no            | Valid           | T(1,1,1)                     | test_5_updateQuantity |

### Class _ProductTypeManager_ - method _updateLocation_

- productId
- toBeAdded
- productExists

**Predicates for method _updateLocation_ :**

| Criteria      | Predicate   |
| ------------- | ----------- |
| productId     | (minint,0)  |
|               | (0,maxint)  |
| newPos        | matchRegex  |
| newPos        | !matchRegex |
| productExists | yes         |
| productExists | no          |

**Combination of predicates**:

| productId  | newPos      | productExists | Valid / Invalid | Description of the test case | JUnit test case       |
| ---------- | ----------- | ------------- | --------------- | ---------------------------- | --------------------- |
| (minint,0) | \*          | \*            | Invalid         | T(0,"1-a-1"),T(-1,"1-a-1")   | test_1_updateLocation |
| (0,maxint) | matchRegex  | no            | Valid           | T(1,"1-a-1")                 | test_2_updateLocation |
| (0,maxint) | matchRegex  | yes           | Valid           | T(1,"1-a-1")                 | test_3_updateLocation |
| (0,maxint) | !matchRegex | \*            | Invalid         | T(1,"1-a-A"),T(1,"1-a--AAA") | test_4_updateLocation |

### Class _SaleTransactionManager_

### Method _checkAuthenticationManager_:

**Criteria for method _checkAuthenticationManager_:**

- Validity User

**Predicates for method _checkAuthenticationManager_:**

| Criteria      | Predicate |
| ------------- | --------- |
| Validity User | Valid     |
|               | Invalid   |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|          |                 |
|          |                 |

**Combination of predicates**:

| Validity User | Valid / Invalid | Description of the test case | JUnit test case |
| ------------- | --------------- | ---------------------------- | --------------- |
| Invalid       | Invalid         | T() -> false                 | TO DO           |
| Valid         | Valid           | T() -> true                  | TO DO           |

### Method _startSaleTransaction_:

**Criteria for method _startSaleTransaction_:**

- Validity User

**Predicates for method _startSaleTransaction_:**

| Criteria      | Predicate |
| ------------- | --------- |
| Validity User | Valid     |
|               | Invalid   |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|          |                 |
|          |                 |

**Combination of predicates**:

| Validity User | Valid / Invalid | Description of the test case | JUnit test case                                                                            |
| ------------- | --------------- | ---------------------------- | ------------------------------------------------------------------------------------------ |
| Invalid       | Invalid         | T() -> UnauthorizedException | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_1_startSaleTransaction |
| Valid         | Valid           | T() -> id transaction        | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_2_startSaleTransaction |

### Method _addProductToSale_:

**Criteria for method _addProductToSale_:**

- transactionId
- productCode
- amount
- currentProduct
- SaleTransaction

**Predicates for method _addProductToSale_:**

| Criteria        | Predicate                           |
| --------------- | ----------------------------------- |
| transactionId   | null                                |
|                 | (min_int,0]                         |
|                 | (0,max_int)                         |
| productCode     | null                                |
|                 | "" (isEmpty)                        |
|                 | wrong check digit                   |
|                 | not number                          |
|                 | productCode.length >14              |
|                 | productCode.length <12              |
|                 | valid check digit                   |
| currentProduct  | Valid                               |
|                 | null                                |
| amount          | (min_int,0)                         |
|                 | amount > currentProduct.getQuantity |
|                 | (0,currentProduct.getQuantity ]     |
| SaleTransaction | SaleTransactionEnum.OPEN            |
|                 | SaleTransactionEnum.CLOSE           |
|                 | null                                |

**Boundaries**:

| Criteria      | Boundary values |
| ------------- | --------------- |
| transactionId | -1,0            |
| amount        | -1,0            |

**Combination of predicates**:

| transactionId | productCode             | currentProduct | amount                              | SaleTransaction           | Valid / Invalid | Description of the test case                                     | JUnit test case                                                                         |
| ------------- | ----------------------- | -------------- | ----------------------------------- | ------------------------- | --------------- | ---------------------------------------------------------------- | --------------------------------------------------------------------------------------- |
| (min_int,0]   | \*                      | \*             | \*                                  | \*                        | Invalid         | T(-1,"9788871527741",3) ->throws InvalidTransactionIdException   | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_1_addProductToSale  |
| null          | \*                      | \*             | \*                                  | \*                        | Invalid         | T(null,"9788871527741",3) ->throws InvalidTransactionIdException | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_2_addProductToSale  |
| \*            | null                    | \*             | \*                                  | \*                        | Invalid         | T(1,null,3) ->throws InvalidProductCodeException                 | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_3_addProductToSale  |
| \*            | ""(isEmpty)             | \*             | \*                                  | \*                        | Invalid         | T(1," ",3) ->throws InvalidProductCodeException                  | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_4_addProductToSale  |
| \*            | wrong check digit       | \*             | \*                                  | \*                        | Invalid         | T(1,"1111111111115",3) ->throws InvalidProductCodeException      | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_5_addProductToSale  |
| \*            | not number              | \*             | \*                                  | \*                        | Invalid         | T(1,"1111111111a5",3) ->throws InvalidProductCodeException       | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_6_addProductToSale  |
| \*            | productCode.length >14  | \*             | \*                                  | \*                        | Invalid         | T(1,"111111111167445",3) ->throws InvalidProductCodeException    | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_7_addProductToSale  |
| \*            | productCode.length < 12 | \*             | \*                                  | \*                        | Invalid         | T(1,"11114738",3) ->throws InvalidProductCodeException           | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_8_addProductToSale  |
| \*            | \*                      | null           | \*                                  | \*                        | Invalid         | T(1,"11114738",3) ->false (barcode not present)                  | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_9_addProductToSale  |
| \*            | \*                      | \*             | (min_int,0)                         | \*                        | Invalid         | T(1,"11114738",-1) ->throws InvalidQuantityException             | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_10_addProductToSale |
| \*            | \*                      | \*             | amount > currentProduct.getQuantity | \*                        | Invalid         | T(1,"11114738",60) ->throws InvalidQuantityException             | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_11_addProductToSale |
| \*            | \*                      | \*             | 0                                   | \*                        | Invalid         | T(1,"11114738",0) ->false                                        | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_12_addProductToSale |
| \*            | \*                      | \*             | \*                                  | SaleTransactionEnum.CLOSE | Invalid         | T(1,"9788871527741",3) ->false                                   | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_13_addProductToSale |
| \*            | \*                      | \*             | \*                                  | null                      | Invalid         | T(1,"9788871527741",3) ->false                                   | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_14_addProductToSale |
| (0,max_int)   | valid check digit       | valid          | (0,currentProduct.getQuantity       | SaleTransactionEnum.OPEN  | Valid           | T(1,"9788871527741",3) ->add product to Sale                     | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_15_addProductToSale |

### Method _deleteProductFromSale_:

**Criteria for method _deleteProductFromSale_:**

- transactionId
- productCode
- amount
- currentProduct
- SaleTransaction

**Predicates for method _deleteProductFromSale_:**

| Criteria        | Predicate                       |
| --------------- | ------------------------------- |
| transactionId   | null                            |
|                 | (min_int,0]                     |
|                 | (0,max_int)                     |
| productCode     | null                            |
|                 | "" (isEmpty)                    |
|                 | wrong check digit               |
|                 | not number                      |
|                 | productCode.length >14          |
|                 | productCode.length <12          |
|                 | valid check digit               |
| currentProduct  | Valid                           |
|                 | null                            |
| amount          | (min_int,0)                     |
|                 | 0                               |
|                 | (0,currentProduct.getQuantity ] |
| SaleTransaction | SaleTransactionEnum.OPEN        |
|                 | SaleTransactionEnum.CLOSE       |
|                 | null                            |

**Boundaries**:

| Criteria      | Boundary values |
| ------------- | --------------- |
| transactionId | -1,0            |
| amount        | -1,0            |

**Combination of predicates**:

| transactionId | productCode             | currentProduct | amount                        | SaleTransaction           | Valid / Invalid | Description of the test case                                       | JUnit test case                                                                              |
| ------------- | ----------------------- | -------------- | ----------------------------- | ------------------------- | --------------- | ------------------------------------------------------------------ | -------------------------------------------------------------------------------------------- |
| (min_int,0]   | \*                      | \*             | \*                            | \*                        | Invalid         | T(-1,"9788871527741",3) ->throws InvalidTransactionIdException     | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_1_deleteProductFromSale  |
| null          | \*                      | \*             | \*                            | \*                        | Invalid         | T(null,"9788871527741",3) ->throws InvalidTransactionIdException   | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_2_deleteProductFromSale  |
| \*            | null                    | \*             | \*                            | \*                        | Invalid         | T(1,null,3) ->throws InvalidProductCodeException                   | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_3_deleteProductFromSale  |
| \*            | ""(isEmpty)             | \*             | \*                            | \*                        | Invalid         | T(1," ",3) ->throws InvalidProductCodeException                    | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_4_deleteProductFromSale  |
| \*            | wrong check digit       | \*             | \*                            | \*                        | Invalid         | T(1,"1111111111115",3) ->throws InvalidProductCodeException        | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_5_deleteProductFromSale  |
| \*            | not number              | \*             | \*                            | \*                        | Invalid         | T(1,"1111111111a5",3) ->throws InvalidProductCodeException         | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_6_deleteProductFromSale  |
| \*            | productCode.length >14  | \*             | \*                            | \*                        | Invalid         | T(1,"111111111167445",3) ->throws InvalidProductCodeException      | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_7_deleteProductFromSale  |
| \*            | productCode.length < 12 | \*             | \*                            | \*                        | Invalid         | T(1,"11114738",3) ->throws InvalidProductCodeException             | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_8_deleteProductFromSale  |
| \*            | \*                      | null           | \*                            | \*                        | Invalid         | T(1,"11114738",3) ->false (barcode is exact but product not exist) | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_9_deleteProductFromSale  |
| \*            | \*                      | \*             | (min_int,0)                   | \*                        | Invalid         | T(1,"11114738",-1) ->throws InvalidQuantityException               | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_10_deleteProductFromSale |
| \*            | \*                      | \*             | 0                             | \*                        | Invalid         | T(1,"11114738",0) ->false                                          | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_11_deleteProductFromSale |
| \*            | \*                      | \*             | \*                            | SaleTransactionEnum.CLOSE | Invalid         | T(1,"9788871527741",3) ->false                                     | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_12_deleteProductFromSale |
| \*            | \*                      | \*             | \*                            | null                      | Invalid         | T(1,"9788871527741",3) ->false                                     | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_13_deleteProductFromSale |
| (0,max_int)   | valid check digit       | valid          | (0,currentProduct.getQuantity | SaleTransactionEnum.OPEN  | Valid           | T(1,"9788871527741",3) ->delete product to Sale(true)              | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_14_deleteProductFromSale |

### Method _applyDiscountRateToProduct_:

**Criteria for method _applyDiscountRateToProduct_:**

- transactionId
- productCode
- discountRate
- currentProduct
- SaleTransaction

**Predicates for method _applyDiscountRateToProduct_:**

| Criteria        | Predicate                 |
| --------------- | ------------------------- |
| transactionId   | null                      |
|                 | (min_int,0]               |
|                 | (0,max_int)               |
| productCode     | null                      |
|                 | "" (isEmpty)              |
|                 | wrong check digit         |
|                 | not number                |
|                 | productCode.length >14    |
|                 | productCode.length <12    |
|                 | valid check digit         |
| currentProduct  | Valid                     |
|                 | null                      |
| discount rate   | (min_int,0] U (1,max_int) |
|                 | (0,1]                     |
| SaleTransaction | SaleTransactionEnum.OPEN  |
|                 | SaleTransactionEnum.CLOSE |
|                 | null                      |

**Boundaries**:

| Criteria      | Boundary values |
| ------------- | --------------- |
| transactionId | -1,0            |
| discountRate  | -0,1            |
| discountRate  | 1,1             |

**Combination of predicates**:

| transactionId | productCode             | currentProduct | discountRate   | SaleTransaction           | Valid / Invalid | Description of the test case                                       | JUnit test case                                                                                   |
| ------------- | ----------------------- | -------------- | -------------- | ------------------------- | --------------- | ------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------- |
| (min_int,0]   | \*                      | \*             | \*             | \*                        | Invalid         | T(-1,"9788871527741",0,5) ->throws InvalidTransactionIdException   | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_1_applyDiscountRateToProduct  |
| null          | \*                      | \*             | \*             | \*                        | Invalid         | T(null,"9788871527741",0,5) ->throws InvalidTransactionIdException | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_2_applyDiscountRateToProduct  |
| \*            | null                    | \*             | \*             | \*                        | Invalid         | T(1,null,0,5) ->throws InvalidProductCodeException                 | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_3_applyDiscountRateToProduct  |
| \*            | ""(isEmpty)             | \*             | \*             | \*                        | Invalid         | T(1," ",0,5) ->throws InvalidProductCodeException                  | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_4_applyDiscountRateToProduct  |
| \*            | wrong check digit       | \*             | \*             | \*                        | Invalid         | T(1,"1111111111115",0,5) ->throws InvalidProductCodeException      | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_5_applyDiscountRateToProduct  |
| \*            | not number              | \*             | \*             | \*                        | Invalid         | T(1,"1111111111a5",0,5) ->throws InvalidProductCodeException       | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_6_applyDiscountRateToProduct  |
| \*            | productCode.length >14  | \*             | \*             | \*                        | Invalid         | T(1,"111111111167445",0,5) ->throws InvalidProductCodeException    | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_7_applyDiscountRateToProduct  |
| \*            | productCode.length < 12 | \*             | \*             | \*                        | Invalid         | T(1,"11114738",0,5) ->throws InvalidProductCodeException           | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_8_applyDiscountRateToProduct  |
| \*            | \*                      | null           | \*             | \*                        | Invalid         | T(1,"11114738",0,5) ->false (barcode not present)                  | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_9_applyDiscountRateToProduct  |
| \*            | \*                      | \*             | (min_double,0] | \*                        | Invalid         | T(1,"11114738",-1) ->throws InvalidDiscountRateException           | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_10_applyDiscountRateToProduct |
| \*            | \*                      | \*             | (1,max_double) | \*                        | Invalid         | T(1,"11114738",1,1) ->InvalidDiscountRateException                 | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_11_applyDiscountRateToProduct |
| \*            | \*                      | \*             | \*             | SaleTransactionEnum.CLOSE | Invalid         | T(1,"6291041500213",0,5) ->false                                   | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_12_applyDiscountRateToProduct |
| \*            | \*                      | \*             | \*             | null                      | Invalid         | T(1,"9788871527741",0,5) ->false                                   | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_13_applyDiscountRateToProduct |
| (0,max_int)   | valid check digit       | valid          | (0,1]          | SaleTransactionEnum.OPEN  | Valid           | T(1,"8017596056993",0,5) ->delete product to Sale(true)            | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_14_applyDiscountRateToProduct |

### Method _applyDiscountRateToSale_:

**Criteria for method _applyDiscountRateToSale_:**

- transactionId
- discountRate
- SaleTransaction

**Predicates for method _applyDiscountRateToSale_:**

| Criteria        | Predicate                 |
| --------------- | ------------------------- |
| transactionId   | null                      |
|                 | (min_int,0]               |
|                 | (0,max_int)               |
| discount rate   | (min_int,0] U (1,max_int) |
|                 | (0,1]                     |
| SaleTransaction | SaleTransactionEnum.OPEN  |
|                 | SaleTransactionEnum.CLOSE |
|                 | null                      |

**Boundaries**:

| Criteria      | Boundary values |
| ------------- | --------------- |
| transactionId | -1,0            |
| discountRate  | -0,1            |
| discountRate  | 1,1             |

**Combination of predicates**:

| transactionId | discountRate | SaleTransaction           | Valid / Invalid | Description of the test case               | JUnit test case                                                                               |
| ------------- | ------------ | ------------------------- | --------------- | ------------------------------------------ | --------------------------------------------------------------------------------------------- |
| null          | \*           | \*                        | Invalid         | T(null,0,5)->InvalidTransactionIdException | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_1_applyDiscountrateToSale |
| (min_int,0]   | \*           | \*                        | Invalid         | T(-1,0,5)->InvalidTransactionIdException   | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_2_applyDiscountrateToSale |
| \*            | (min_int,0]  | \*                        | Invalid         | T(1,-1)->InvalidDiscountRateException      | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_3_applyDiscountrateToSale |
| \*            | (1,max_int)  | \*                        | Invalid         | T(1,1,1)->InvalidDiscountRateException     | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_4_applyDiscountrateToSale |
| \*            | \*           | SaleTransactionEnum.CLOSE | Invalid         | T(1,0,5)->false                            | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_5_applyDiscountrateToSale |
| \*            | \*           | null                      | Invalid         | T(1,0,5)->false                            | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_6_applyDiscountrateToSale |
| (0,max_int)   | (0,1]        | SaleTransactionEnum.OPEN  | Valid           | T(1,0,5)-> apply Discount Rate To Sale     | test.java.it.polito.ezshop.acceptanceTests.TestSaleTransaction.test_7_applyDiscountrateToSale |

### Method _computePointsForSale_:

**Criteria for method _computePointsForSale_:**

- transactionId
- SaleTransaction

**Predicates for method _computePointsForSale_:**

| Criteria        | Predicate                  |
| --------------- | -------------------------- |
| transactionId   | null                       |
|                 | (min_int,0]                |
|                 | (0,max_int)                |
| SaleTransaction | SaleTransactionEnum.OPEN   |
|                 | SaleTransactionEnum.CLOSED |
|                 | SaleTransactionEnum.PAYED  |
|                 | null                       |

**Boundaries**:

| Criteria      | Boundary values |
| ------------- | --------------- |
| transactionId | -1,0            |

**Combination of predicates**:

| transactionId | SaleTransaction                                                                     | Valid / Invalid | Description of the test case             | JUnit test case |
| ------------- | ----------------------------------------------------------------------------------- | --------------- | ---------------------------------------- | --------------- |
| null          | \*                                                                                  | Invalid         | T(null)->InvalidTransactionIdException   | TO DO           |
| (min_int,0]   | \*                                                                                  | Invalid         | T(-1)->InvalidTransactionIdException     | TO DO           |
| \*            | null                                                                                | Invalid         | T(1)-> -1                                | TO DO           |
| (0,max_int)   | SaleTransactionEnum.OPEN OR SaleTransactionEnum.PAYED OR SaleTransactionEnum.CLOSED | Valid           | T(1)-> (openTransaction.getPrice() / 10) | TO DO           |

### Method _endSaleTransaction_:

**Criteria for method _endSaleTransaction_:**

- transactionId
- SaleTransaction

**Predicates for method _endSaleTransaction_:**

| Criteria        | Predicate                  |
| --------------- | -------------------------- |
| transactionId   | null                       |
|                 | (min_int,0]                |
|                 | (0,max_int)                |
| SaleTransaction | SaleTransactionEnum.OPEN   |
|                 | SaleTransactionEnum.CLOSED |
|                 | SaleTransactionEnum.PAYED  |
|                 | null                       |

**Boundaries**:

| Criteria      | Boundary values |
| ------------- | --------------- |
| transactionId | -1,0            |

**Combination of predicates**:

| transactionId | SaleTransaction            | Valid / Invalid | Description of the test case           | JUnit test case |
| ------------- | -------------------------- | --------------- | -------------------------------------- | --------------- |
| null          | \*                         | Invalid         | T(null)->InvalidTransactionIdException | TO DO           |
| (min_int,0]   | \*                         | Invalid         | T(-1)->InvalidTransactionIdException   | TO DO           |
| \*            | null                       | Invalid         | T(1)-> false                           | TO DO           |
| \*            | SaleTransactionEnum.CLOSED | Invalid         | T(1)-> false                           | TO DO           |
| \*            | SaleTransactionEnum.PAYED  | Invalid         | T(1)-> false                           | TO DO           |
| (0,max_int)   | SaleTransactionEnum.OPEN   | Valid           | T(1)-> true                            | TO DO           |

### Method _deleteSaleTransaction_:

**Criteria for method _deleteSaleTransaction_:**

- transactionId
- SaleTransaction

**Predicates for method _deleteSaleTransaction_:**

| Criteria        | Predicate                  |
| --------------- | -------------------------- |
| transactionId   | null                       |
|                 | (min_int,0]                |
|                 | (0,max_int)                |
| SaleTransaction | SaleTransactionEnum.OPEN   |
|                 | SaleTransactionEnum.CLOSED |
|                 | SaleTransactionEnum.PAYED  |
|                 | null                       |

**Boundaries**:

| Criteria      | Boundary values |
| ------------- | --------------- |
| transactionId | -1,0            |

**Combination of predicates**:

| transactionId | SaleTransaction            | Valid / Invalid | Description of the test case           | JUnit test case |
| ------------- | -------------------------- | --------------- | -------------------------------------- | --------------- |
| null          | \*                         | Invalid         | T(null)->InvalidTransactionIdException | TO DO           |
| (min_int,0]   | \*                         | Invalid         | T(-1)->InvalidTransactionIdException   | TO DO           |
| \*            | null                       | Invalid         | T(1)-> false                           | TO DO           |
| \*            | SaleTransactionEnum.PAYED  | Invalid         | T(1)-> false                           | TO DO           |
| (0,max_int)   | SaleTransactionEnum.OPEN   | Valid           | T(1)-> true                            | TO DO           |
| (0,max_int)   | SaleTransactionEnum.CLOSED | Valid           | T(1)-> true                            | TO DO           |

### Method _getSaleTransaction_:

**Criteria for method _getSaleTransaction_:**

- transactionId

**Predicates for method _getSaleTransaction_:**

| Criteria      | Predicate   |
| ------------- | ----------- |
| transactionId | null        |
|               | (min_int,0] |
|               | (0,max_int) |

**Boundaries**:

| Criteria      | Boundary values |
| ------------- | --------------- |
| transactionId | -1,0            |

**Combination of predicates**:

| transactionId | Valid / Invalid | Description of the test case                                    | JUnit test case |
| ------------- | --------------- | --------------------------------------------------------------- | --------------- |
| null          | Invalid         | T(null)->InvalidTransactionIdException                          | TO DO           |
| (min_int,0]   | Invalid         | T(-1)->InvalidTransactionIdException                            | TO DO           |
| (0,max_int)   | Invalid         | T(1)->SaleTransactionDao.getInstance().findById(transactionId); | TO DO           |

### Method _receiveCashPayment_:

**Criteria for method _receiveCashPayment_:**

- transactionId
- cash
- SaleTransaction

**Predicates for method _receiveCashPayment_:**

| Criteria        | Predicate                       |
| --------------- | ------------------------------- |
| transactionId   | null                            |
|                 | (min_int,0]                     |
|                 | (0,max_int)                     |
| Cash            | (min_double, 0]                 |
|                 | cash < openTransaction.getPrice |
|                 | (0,max_double)                  |
| SaleTransaction | SaleTransactionEnum.OPEN        |
|                 | SaleTransactionEnum.CLOSED      |
|                 | SaleTransactionEnum.PAYED       |
|                 | null                            |

**Boundaries**:

| Criteria      | Boundary values |
| ------------- | --------------- |
| transactionId | -1,0            |
| Cash          | -0,1            |

**Combination of predicates**:

| transactionId | Cash                            | SaleTransaction            | Valid / Invalid | Description of the test case                 | JUnit test case                |
| ------------- | ------------------------------- | -------------------------- | --------------- | -------------------------------------------- | ------------------------------ |
| null          | \*                              | \*                         | Invalid         | T(null,10)->InvalidTransactionIdException    | TO DO                          |
| (min_int,0]   | \*                              | \*                         | Invalid         | T(-1,10)->InvalidTransactionIdException      | TO DO                          |
| \*            | (min_double, 0]                 | \*                         | Invalid         | T(1,-0,1)->InvalidPaymentException           | TO DO                          |
| \*            | cash < openTransaction.getPrice | \*                         | Invalid         | T(1,1)-> -1                                  | TO DO                          |
| \*            | \*                              | null                       | Invalid         | T(1,10)-> -1                                 | TO DO                          |
| \*            | \*                              | SaleTransactionEnum.CLOSED | Invalid         | T(1,10)-> -1                                 | TO DO                          |
| (0,max_int)   | (0,max_double)                  | SaleTransactionEnum.PAYED  | Valid           | T(1,10)-> (cash - openTransaction.getPrice() | TO DO(di questo non sono certo |
| (0,max_int)   | (0,max_double)                  | SaleTransactionEnum.CLOSED | Valid           | T(1,10)-> (cash - openTransaction.getPrice() | TO DO                          |

### Method _receiveCreditCardPayment_:

**Criteria for method _receiveCreditCardPayment_:**

- transactionId
- creditCard
- SaleTransaction

**Predicates for method _receiveCreditCardPayment_:**

| Criteria        | Predicate                  |
| --------------- | -------------------------- |
| transactionId   | null                       |
|                 | (min_int,0]                |
|                 | (0,max_int)                |
| Creditcard      | null                       |
|                 | ""(isEmpty)                |
|                 | creditCard.length() < 16   |
|                 | creditCard.length() > 16   |
|                 | creditCard.length() = 16   |
| SaleTransaction | SaleTransactionEnum.OPEN   |
|                 | SaleTransactionEnum.CLOSED |
|                 | null                       |

**Boundaries**:

| Criteria      | Boundary values |
| ------------- | --------------- |
| transactionId | -1,0            |

**Combination of predicates**:

| transactionId | creditCard               | SaleTransaction            | Valid / Invalid | Description of the test case                              | JUnit test case |
| ------------- | ------------------------ | -------------------------- | --------------- | --------------------------------------------------------- | --------------- |
| null          | \*                       | \*                         | Invalid         | T(null,"5333654687698754")->InvalidTransactionIdException | TO DO           |
| (min_int,0]   | \*                       | \*                         | Invalid         | T(-1,"5333654687698754")->InvalidTransactionIdException   | TO DO           |
| \*            | null                     | \*                         | Invalid         | T(1,null)->InvalidCreditCardException                     | TO DO           |
| \*            | ""(isEmpty)              | \*                         | Invalid         | T(1,"")-> InvalidCreditCardException                      | TO DO           |
| \*            | creditCard.length() < 16 | \*                         | Invalid         | T(1,"53336546876987")-> InvalidCreditCardException        | TO DO           |
| \*            | creditCard.length() > 16 | \*                         | Invalid         | T(1,"533365468769875467")-> InvalidCreditCardException    | TO DO           |
| \*            | \*                       | null                       | Invalid         | T(1,"5333654687698754")-> false                           | TO DO           |
| \*            | \*                       | SaleTransactionEnum.CLOSED | Invalid         | T(1,"5333654687698754")-> false                           | TO DO           |
| (0,max_int)   | creditCard.length = 16   | SaleTransactionEnum.OPEN   | Valid           | T(1,"5333654687698754")-> true                            | TO DO           |

### Class _UserManager_

### Method _createUser_:

**Criteria for method _createUser_:**

- username
- password
- role

**Predicates for method _createUser_:**

| Criteria | Predicate   |
| -------- | ----------- |
| username | null        |
|          | ""(isEmpty) |
|          | Valid       |
| password | null        |
|          | ""(isEmpty) |
|          | Valid       |
| role     | null        |
|          | Invalid     |
|          | Valid       |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|          |                 |

**Combination of predicates**:

| user        | password    | Role    | Valid / Invalid | Description of the test case                                | JUnit test case |
| ----------- | ----------- | ------- | --------------- | ----------------------------------------------------------- | --------------- |
| null        | \*          | \*      | Invalid         | T(null,"admin","Administrator")->InvalidUsernameException   | TO DO           |
| ""(isEmpty) | \*          | \*      | Invalid         | T("","admin","Administrator")->InvalidUsernameException     | TO DO           |
| \*          | null        | \*      | Invalid         | T("admin","null","Administrator")->InvalidPasswordException | TO DO           |
| \*          | ""(isEmpty) | \*      | Invalid         | T("admin","","Administrator")->InvalidPasswordException     | TO DO           |
| \*          | \*          | null    | Invalid         | T("admin","admin",null)->InvalidRoleException               | TO DO           |
| \*          | \*          | Invalid | Invalid         | T("admin","admin","Nessuno")->InvalidRoleException          | TO DO           |
| valid       | valid       | valid   | Valid           | T("admin","admin","Administrator")->newUser                 | TO DO           |

### Test cases definition

    <JUnit test classes must be in src/test/java/it/polito/ezshop>
    <Report here all the created JUnit test cases, and the units/classes under test >
    <For traceability write the class and method name that contains the test case>

| Unit name | JUnit test case |
| --------- | --------------- | --- |
|           |                 |
|           |                 |
|           |                 |     |

### Code coverage report

    <Add here the screenshot report of the statement and branch coverage obtained using
    the Eclemma tool. >

### Loop coverage analysis

    <Identify significant loops in the units and reports the test cases
    developed to cover zero, one or multiple iterations >

| Unit name | Loop rows | Number of iterations | JUnit test case |
| --------- | --------- | -------------------- | --------------- | --- |
|           |           |                      |                 |
|           |           |                      |                 |
|           |           |                      |                 |     |
