-- Creating auto increment fields: https://db.apache.org/derby/docs/10.0/manuals/develop/develop132.html

-- Data types: https://db.apache.org/derby/docs/10.7/ref/crefsqlj31068.html	

CREATE TABLE Users (
   ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
   CustomerID INTEGER NOT NULL UNIQUE,
   Email VARCHAR(60) NOT NULL UNIQUE,
   Password VARCHAR(64) NOT NULL,
   AccessLevel INTEGER NOT NULL,
   Biography VARCHAR(255),
   BirthDate TIMESTAMP,
   Gender INTEGER,
   CreatedDate TIMESTAMP NOT NULL,
   CreatedBy INTEGER NOT NULL,
   ModifiedDate TIMESTAMP,
   ModifiedBy INTEGER,
   PRIMARY KEY (ID)
);


CREATE TABLE Customers (
   ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
   FirstName VARCHAR(30) NOT NULL,
   LastName VARCHAR(30) NOT NULL,
   Email VARCHAR(60) NOT NULL UNIQUE,
   CreatedDate TIMESTAMP NOT NULL,
   CreatedBy INTEGER NOT NULL,
   ModifiedDate TIMESTAMP,
   ModifiedBy INTEGER,
   PRIMARY KEY (ID)
);

CREATE TABLE Addresses (
   ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
   CustomerID INTEGER NOT NULL,
   UserID INTEGER,
   DefaultShippingAddress INTEGER NOT NULL DEFAULT 0,
   DefaultBillingAddress INTEGER NOT NULL DEFAULT 0,
   AddressPrefix1 VARCHAR(30),
   StreetNumber INTEGER NOT NULL,
   StreetName VARCHAR(60) NOT NULL,
   StreetType VARCHAR(20) NOT NULL,
   Suburb VARCHAR(60) NOT NULL,
   State VARCHAR(30) NOT NULL,
   Postcode VARCHAR(4) NOT NULL,
   Country VARCHAR(30) NOT NULL,
   BirthDate TIMESTAMP,
   CreatedDate TIMESTAMP NOT NULL,
   CreatedBy INTEGER NOT NULL,
   ModifiedDate TIMESTAMP,
   ModifiedBy INTEGER,
   PRIMARY KEY (ID)
);

CREATE TABLE PaymentMethods (
   ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
   CustomerID INTEGER NOT NULL,
   UserID INTEGER,
   DefaultPayment INTEGER NOT NULL DEFAULT 0,
   PaymentType INTEGER NOT NULL DEFAULT 0,
   CardName VARCHAR(80) NOT NULL,
   CardNumber VARCHAR(19) NOT NULL,
   CardCVV VARCHAR(3) NOT NULL,
   CreatedDate TIMESTAMP NOT NULL,
   CreatedBy INTEGER NOT NULL,
   ModifiedDate TIMESTAMP,
   ModifiedBy INTEGER,
   PRIMARY KEY (ID)
);

CREATE TABLE Orders (
   ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
   CustomerID INTEGER NOT NULL,
   UserID INTEGER,
   CurrencyID INTEGER NOT NULL DEFAULT 1,
   BillingAddressID INTEGER NOT NULL,
   ShippingAddressID INTEGER NOT NULL,
   PaymentMethodID INTEGER NOT NULL,
   TotalCost DECIMAL(10,2) NOT NULL,
   Status INTEGER NOT NULL DEFAULT 0,
   CreatedDate TIMESTAMP  NOT NULL,
   CreatedBy INTEGER NOT NULL,
   ModifiedDate TIMESTAMP,
   ModifiedBy INTEGER,
   PRIMARY KEY (ID)
);

CREATE TABLE OrderLines (
   ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
   OrderID INTEGER NOT NULL,
   ProductID INTEGER NOT NULL,
   Quantity INTEGER NOT NULL,
   UnitPrice DECIMAL(7,2) NOT NULL,
   CreatedDate TIMESTAMP NOT NULL,
   CreatedBy INTEGER  NOT NULL,
   ModifiedDate TIMESTAMP,
   ModifiedBy INTEGER,
   PRIMARY KEY (ID)
);

CREATE TABLE Products (
   ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
   Name VARCHAR(20) NOT NULL,
   CurrencyID INTEGER NOT NULL,
   Description VARCHAR(100),
   InitialQuantity INTEGER NOT NULL,
   CurrentQuantity INTEGER NOT NULL,
   CategoryID INTEGER,
   LastReorderDate TIMESTAMP,
   Price DECIMAL(7,2) NOT NULL,
   Image VARCHAR(128),
   CreatedDate TIMESTAMP NOT NULL,
   CreatedBy INTEGER NOT NULL,
   ModifiedDate TIMESTAMP,
   ModifiedBy INTEGER,
   PRIMARY KEY (ID)
);

CREATE TABLE ProductCategories (
   ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
   Name VARCHAR(20) NOT NULL,
   Description VARCHAR(100),
   Image VARCHAR(128),
   CreatedDate TIMESTAMP NOT NULL,
   CreatedBy INTEGER NOT NULL,
   ModifiedDate TIMESTAMP,
   ModifiedBy INTEGER,
   PRIMARY KEY (ID)
);


CREATE TABLE ProductReviews (
   ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
   CustomerID INTEGER NOT NULL,
   ProductID INTEGER NOT NULL,
   Text VARCHAR(255),
   Rating INTEGER NOT NULL,
   CreatedDate TIMESTAMP NOT NULL,
   CreatedBy INTEGER NOT NULL,
   ModifiedDate TIMESTAMP,
   ModifiedBy INTEGER,
   PRIMARY KEY (ID)
);


CREATE TABLE Currencies (
   ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
   Name VARCHAR(30) NOT NULL,
   Abbreviation VARCHAR(3) NOT NULL,
   CostConversionRate DECIMAL(7,6) NOT NULL,
   RetailConversionRate DECIMAL(7,6) NOT NULL,
   CreatedDate TIMESTAMP NOT NULL,
   CreatedBy INTEGER NOT NULL,
   ModifiedDate TIMESTAMP,
   ModifiedBy INTEGER,
   PRIMARY KEY (ID)
);

CREATE TABLE PaymentTransactions (
   ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
   CustomerID INTEGER NOT NULL,
   OrderID INTEGER NOT NULL,
   CurrencyID INTEGER NOT NULL,
   Amount DECIMAL(10,2) NOT NULL,
   Description VARCHAR(255),
   PaymentGatewayTransaction VARCHAR(255) NOT NULL UNIQUE,
   Status INTEGER NOT NULL DEFAULT 0,
   CreatedDate TIMESTAMP NOT NULL,
   CreatedBy INTEGER  NOT NULL,
   ModifiedDate TIMESTAMP,
   ModifiedBy INTEGER,
   PRIMARY KEY (ID)
);

--- Now setup foreign keys
-- Addresses

ALTER TABLE Addresses
ADD FOREIGN KEY (CustomerID)
REFERENCES Customers (ID);

ALTER TABLE Addresses
ADD FOREIGN KEY (UserID)
REFERENCES Users (ID);

ALTER TABLE Addresses
ADD FOREIGN KEY (CreatedBy)
REFERENCES Customers (ID);

ALTER TABLE Addresses
ADD FOREIGN KEY (ModifiedBy)
REFERENCES Customers (ID);

-- Currencies

ALTER TABLE Currencies
ADD FOREIGN KEY (CreatedBy)
REFERENCES Customers (ID);

ALTER TABLE Currencies
ADD FOREIGN KEY (ModifiedBy)
REFERENCES Customers (ID);

-- Customers

ALTER TABLE Customers
ADD FOREIGN KEY (CreatedBy)
REFERENCES Customers (ID);

ALTER TABLE Customers
ADD FOREIGN KEY (ModifiedBy)
REFERENCES Customers (ID);


-- OrderLines

ALTER TABLE OrderLines
ADD FOREIGN KEY (OrderID)
REFERENCES Orders (ID);

ALTER TABLE OrderLines
ADD FOREIGN KEY (ProductID)
REFERENCES Products (ID);

ALTER TABLE OrderLines
ADD FOREIGN KEY (CreatedBy)
REFERENCES Customers (ID);

ALTER TABLE OrderLines
ADD FOREIGN KEY (ModifiedBy)
REFERENCES Customers (ID);

-- Orders

ALTER TABLE Orders
ADD FOREIGN KEY (CustomerID)
REFERENCES Customers (ID);

ALTER TABLE Orders
ADD FOREIGN KEY (UserID)
REFERENCES Users (ID);

ALTER TABLE Orders
ADD FOREIGN KEY (CurrencyID)
REFERENCES Currencies (ID);

ALTER TABLE Orders                                                         
ADD FOREIGN KEY (BillingAddressID)                                                        
REFERENCES Addresses (ID); 

ALTER TABLE Orders
ADD FOREIGN KEY (ShippingAddressID)
REFERENCES Addresses (ID);

ALTER TABLE Orders                                                              
ADD FOREIGN KEY (PaymentMethodID)                                             
REFERENCES PaymentMethods (ID);  

ALTER TABLE Orders
ADD FOREIGN KEY (CreatedBy)
REFERENCES Customers (ID);

ALTER TABLE Orders
ADD FOREIGN KEY (ModifiedBy)
REFERENCES Customers (ID);

-- PaymentMethods

ALTER TABLE PaymentMethods
ADD FOREIGN KEY (CustomerID)
REFERENCES Customers (ID);

ALTER TABLE PaymentMethods
ADD FOREIGN KEY (UserID)
REFERENCES Users (ID);

ALTER TABLE PaymentMethods
ADD FOREIGN KEY (CreatedBy)
REFERENCES Customers (ID);

ALTER TABLE PaymentMethods
ADD FOREIGN KEY (ModifiedBy)
REFERENCES Customers (ID);

-- PaymentTransactions

ALTER TABLE PaymentTransactions
ADD FOREIGN KEY (CustomerID)
REFERENCES Customers (ID);

ALTER TABLE PaymentTransactions
ADD FOREIGN KEY (OrderID)
REFERENCES Orders (ID);

ALTER TABLE PaymentTransactions
ADD FOREIGN KEY (CurrencyID)
REFERENCES Currencies (ID);

ALTER TABLE PaymentTransactions
ADD FOREIGN KEY (CreatedBy)
REFERENCES Customers (ID);

ALTER TABLE PaymentTransactions
ADD FOREIGN KEY (ModifiedBy)
REFERENCES Customers (ID);

-- ProductCategories

ALTER TABLE ProductCategories
ADD FOREIGN KEY (CreatedBy)
REFERENCES Customers (ID);

ALTER TABLE ProductCategories
ADD FOREIGN KEY (ModifiedBy)
REFERENCES Customers (ID);

-- ProductReviews

ALTER TABLE ProductReviews
ADD FOREIGN KEY (CustomerID)
REFERENCES Customers (ID);

ALTER TABLE ProductReviews
ADD FOREIGN KEY (ProductID)
REFERENCES Products (ID);

ALTER TABLE ProductReviews
ADD FOREIGN KEY (CreatedBy)
REFERENCES Customers (ID);

ALTER TABLE ProductReviews
ADD FOREIGN KEY (ModifiedBy)
REFERENCES Customers (ID);

-- Products

ALTER TABLE Products
ADD FOREIGN KEY (CategoryID)
REFERENCES ProductCategories (ID);

ALTER TABLE Products
ADD FOREIGN KEY (CurrencyID)
REFERENCES Currencies (ID);

ALTER TABLE Products
ADD FOREIGN KEY (CreatedBy)
REFERENCES Customers (ID);

ALTER TABLE Products
ADD FOREIGN KEY (ModifiedBy)
REFERENCES Customers (ID);

-- Users

ALTER TABLE Users
ADD FOREIGN KEY (CustomerID)
REFERENCES Customers (ID);

ALTER TABLE Users
ADD FOREIGN KEY (CreatedBy)
REFERENCES Customers (ID);

ALTER TABLE Users
ADD FOREIGN KEY (ModifiedBy)
REFERENCES Customers (ID);
