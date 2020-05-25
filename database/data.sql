-- Addresses

-- Currencies

INSERT INTO Currencies ("NAME", Abbreviation, CostConversionRate, RetailConversionRate, CreatedDate, CreatedBy)
VALUES ('Australian Dollar', 'AUD', 0.66, 0.69, '2020-05-19 17:04:00', 1);

-- Customers

INSERT INTO Customers (FirstName, LastName, Email, CreatedDate, CreatedBy)
VALUES ('Rhys', 'Hanrahan', 'rhys@nexusone.com.au', '2020-05-19 09:17:00', 1);

-- Users

INSERT INTO Users (CustomerID, Email, Password, AccessLevel, BirthDate, Gender, CreatedDate, CreatedBy)
VALUES (1, 'rhys@nexusone.com.au', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 10, '1991-06-14 00:00:00', 1, '2020-05-19 09:17:00', 1);

-- ProductCategories

INSERT INTO ProductCategories ("NAME", Description, Image, CreatedDate, CreatedBy)
VALUES ('Transistors', 'There are some transistors in here.', 'transistor1.jpg', '2020-05-19 09:17:00', 1);

-- Products

INSERT INTO Products ("NAME", Description, InitialQuantity, CurrentQuantity, CategoryID, LastReorderDate, Price, Image, CreatedDate, CreatedBy)
VALUES ('Transistor Thing 1', 'This is a transistor thing', 100, 10, 1, '2020-05-19 10:00:00', 1.95, 'transistor1.jpg', '2020-05-19 09:17:00', 1);

