# EJB-Transactions - Shop Basket application
This is an example "Add to basket" project using EJB and TransactionManagement:

This project uses Container managed transactions for adding items to a basket. During the "basket checkout" program updates stock in the DB. But firstly, before commiting transaction it checks the basket's products quantity. If any product's amount is bigger then products in stock (in DB) the transaction is rolled back and in DB there are original values. 

EJB Container/Servers are transaction servers and handles transactions context propagation and distributed transactions. Transactions can be managed by the container or by custom code handling in bean's code.

* Container Managed Transactions − In this type, the container manages the transaction states.

* Bean Managed Transactions − In this type, the developer manages the life cycle of transaction states.

EJB 3.0 has specified following attributes of transactions, which EJB containers implement −

* REQUIRED − Indicates that business method has to be executed within transaction, otherwise a new transaction will be started for that method.

* REQUIRES_NEW − Indicates that a new transaction, is to be started for the business method.

* SUPPORTS − Indicates that business method will execute as part of transaction.

* NOT_SUPPORTED − Indicates that business method should not be executed as part of transaction.

* MANDATORY − Indicates that business method will execute as part of transaction, otherwise exception will be thrown.

* NEVER − Indicates if business method executes as part of transaction, then an exception will be thrown.

This project uses REQUIRED, MANDATORY and NOT_SUPPORTED Transaction attibutes.
