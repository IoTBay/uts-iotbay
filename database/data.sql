-- Customers

INSERT INTO Customers (FirstName, LastName, Email, Phone, CreatedDate)
VALUES ('Rhys', 'Hanrahan', 'rhys@nexusone.com.au', '0414111111', '2020-05-19 09:17:00');

UPDATE Customers SET CreatedBy = 1 WHERE ID = 1;

-- Users

INSERT INTO Users (CustomerID, Email, Password, AccessLevel, BirthDate, Gender, CreatedDate, CreatedBy)
VALUES (1, 'rhys@nexusone.com.au', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 10, '1991-06-14 00:00:00', 1, '2020-05-19 09:17:00', 1);

-- Currencies

INSERT INTO Currencies ("NAME", Abbreviation, CostConversionRate, RetailConversionRate, CreatedDate, CreatedBy)
VALUES ('Australian Dollar', 'AUD', 0.66, 0.69, '2020-05-19 17:04:00', 1);

INSERT INTO CURRENCIES ("NAME", ABBREVIATION, COSTCONVERSIONRATE, RetailConversionRate, CreatedDate, CreatedBy) VALUES ('US Dollar', 'USD', 1.00, 1.00, '2020-06-08 00:00:00', 1);

INSERT INTO CURRENCIES ("NAME", ABBREVIATION, COSTCONVERSIONRATE, RetailConversionRate, CreatedDate, CreatedBy) VALUES ('Canadian Dollar', 'CAD', 1.00, 1.00, '2020-06-08 00:00:00', 1);

INSERT INTO CURRENCIES ("NAME", ABBREVIATION, COSTCONVERSIONRATE, RetailConversionRate, CreatedDate, CreatedBy) VALUES ('Chinese Yuan', 'CNY', 1.00, 1.00, '2020-06-08 00:00:00', 1);

INSERT INTO CURRENCIES ("NAME", ABBREVIATION, COSTCONVERSIONRATE, RetailConversionRate, CreatedDate, CreatedBy) VALUES ('Euro', 'EUR', 1.00, 1.00, '2020-06-08 00:00:00', 1);

INSERT INTO CURRENCIES ("NAME", ABBREVIATION, COSTCONVERSIONRATE, RetailConversionRate, CreatedDate, CreatedBy) VALUES ('British Pound', 'GBP', 1.00, 1.00, '2020-06-08 00:00:00', 1); 

INSERT INTO CURRENCIES ("NAME", ABBREVIATION, COSTCONVERSIONRATE, RetailConversionRate, CreatedDate, CreatedBy) VALUES ('Indian Rupee', 'INR', 1.00, 1.00, '2020-06-08 00:00:00', 1);

INSERT INTO CURRENCIES ("NAME", ABBREVIATION, COSTCONVERSIONRATE, RetailConversionRate, CreatedDate, CreatedBy) VALUES ('Swiff Frank', 'CHF', 1.00, 1.00, '2020-06-08 00:00:00', 1);

INSERT INTO CURRENCIES ("NAME", ABBREVIATION, COSTCONVERSIONRATE, RetailConversionRate, CreatedDate, CreatedBy) VALUES ('US Dollar', 'USD', 1.00, 1.00, '2020-06-08 00:00:00', 1);

INSERT INTO CURRENCIES ("NAME", ABBREVIATION, COSTCONVERSIONRATE, RetailConversionRate, CreatedDate, CreatedBy) VALUES ('Czech Koruna', 'CZK', 1.00, 1.00, '2020-06-08 00:00:00', 1);

INSERT INTO CURRENCIES ("NAME", ABBREVIATION, COSTCONVERSIONRATE, RetailConversionRate, CreatedDate, CreatedBy) VALUES ('Cuban Peso', 'CUP', 1.00, 1.00, '2020-06-08 00:00:00', 1);

INSERT INTO CURRENCIES ("NAME", ABBREVIATION, COSTCONVERSIONRATE, RetailConversionRate, CreatedDate, CreatedBy) VALUES ('Dominican Peso', 'DOP', 1.00, 1.00, '2020-06-08 00:00:00', 1);

INSERT INTO CURRENCIES ("NAME", ABBREVIATION, COSTCONVERSIONRATE, RetailConversionRate, CreatedDate, CreatedBy) VALUES ('Mexican Peso', 'MXN', 1.00, 1.00, '2020-06-08 00:00:00', 1);

INSERT INTO CURRENCIES ("NAME", ABBREVIATION, COSTCONVERSIONRATE, RetailConversionRate, CreatedDate, CreatedBy) VALUES ('Russian Ruble', 'RUB', 1.00, 1.00, '2020-06-08 00:00:00', 1);

INSERT INTO CURRENCIES ("NAME", ABBREVIATION, COSTCONVERSIONRATE, RetailConversionRate, CreatedDate, CreatedBy) VALUES ('Iraqi Dinar', 'IQD', 1.00, 1.00, '2020-06-08 00:00:00', 1);

INSERT INTO CURRENCIES ("NAME", ABBREVIATION, COSTCONVERSIONRATE, RetailConversionRate, CreatedDate, CreatedBy) VALUES ('Japanese Yen', 'JPY', 1.00, 1.00, '2020-06-08 00:00:00', 1);

INSERT INTO CURRENCIES ("NAME", ABBREVIATION, COSTCONVERSIONRATE, RetailConversionRate, CreatedDate, CreatedBy) VALUES ('NZ Dollar', 'NZD', 1.00, 1.00, '2020-06-08 00:00:00', 1);

INSERT INTO CURRENCIES ("NAME", ABBREVIATION, COSTCONVERSIONRATE, RetailConversionRate, CreatedDate, CreatedBy) VALUES ('Guinean Franc', 'GNF', 1.00, 1.00, '2020-06-08 00:00:00', 1);

INSERT INTO CURRENCIES ("NAME", ABBREVIATION, COSTCONVERSIONRATE, RetailConversionRate, CreatedDate, CreatedBy) VALUES ('Philippine Peso', 'PHP', 1.00, 1.00, '2020-06-08 00:00:00', 1);

INSERT INTO CURRENCIES ("NAME", ABBREVIATION, COSTCONVERSIONRATE, RetailConversionRate, CreatedDate, CreatedBy) VALUES ('Thai Baht', 'THB', 1.00, 1.00, '2020-06-08 00:00:00', 1);

INSERT INTO CURRENCIES ("NAME", ABBREVIATION, COSTCONVERSIONRATE, RetailConversionRate, CreatedDate, CreatedBy) VALUES ('Swedish Krona', 'SEK', 1.00, 1.00, '2020-06-08 00:00:00', 1);

INSERT INTO CURRENCIES ("NAME", ABBREVIATION, COSTCONVERSIONRATE, RetailConversionRate, CreatedDate, CreatedBy) VALUES ('Vietnamese Dong', 'VND', 1.00, 1.00, '2020-06-08 00:00:00', 1);


-- Addresses

-- ProductCategories

INSERT INTO ProductCategories ("NAME", Description, Image, CreatedDate, CreatedBy)
VALUES ('Transistors', 'There are some transistors in here.', 'transistor1.jpg', '2020-05-19 09:17:00', 1);

-- Products

INSERT INTO Products ("NAME", Description, InitialQuantity, CurrentQuantity, CategoryID, LastReorderDate, Price, Image, CreatedDate, CreatedBy)
VALUES ('Transistor Thing 1', 'This is a transistor thing', 100, 10, 1, '2020-05-19 10:00:00', 1.95, 'transistor1.jpg', '2020-05-19 09:17:00', 1);

