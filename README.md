## IOT Bay Web store
## UTS Introduction to Software Development - Assignment 2

Author: Rhys Hanrahan (11000801)

This is a readme for the Assignment 2 software release 1.

The steps to use this software follow a standard JSP Web Application.

* Open Netbeans 8.0 or greater.
* The assignment1 folder is the root of the project. So in netbeans go to File -> Open project and browse to uts-iotbay folder.
* An existing Netbeans project will be detected to open, named iotbay.
* Click "Run Project" (F6) to build and run the website.
* Browser will pop up on your local glassfish server with the new project's URL.

The project contains:
- An admin user: rhys@nexusone.com.au with password 123
- A customer user: some@guy2.com with password 123

If you re-create the database using data.sql then only the rhys@nexusone.com.au user will be present.

To resiger a staff user, enter the passcode "Escalate" during registration.

### Running the database

This release contains all database related materials in the "database" sub-folder of the project.

The recommended way to setup the database is to create the database from our existing iotdb as this has all the records and test data in it.

See here for how to create a new database from a backup copy: 
- https://db.apache.org/derby//docs/10.2/adminguide/tadmincrtdbbkup.html
- https://db.apache.org/derby//docs/10.2/adminguide/tadminhubbkup44.html
- https://stackoverflow.com/questions/20933116/does-copy-pasting-apache-derby-db-files-into-another-system-make-it-work-fine

For example you could use the connection string: `jdbc:derby:sample;restoreFrom=c:\projects\uts-iotbay\database\iotdb`

And this should re-create the database called iotdb in your Netbeans JavaDB instance.

To start with an empty database you can create the following:
* Name: iotdb
* User: iotdb
* Password: iotdb

Then run:

* schema.sql to build the table structure and all relationships
* data.sql to insert some sample data with user "rhys@nexusone.com.au" and password "123"

NOTE: data.sql is incomplete. 
