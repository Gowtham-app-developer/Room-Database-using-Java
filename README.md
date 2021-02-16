# Room-Database-using-Java

- CRUD Operations using Room Database

## Room Database

- Room is a persistence library, part of the Android Jetpack.
- Room provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite.
- It makes it easier to work with SQLiteDatabase objects in your app, decreasing the amount of boilerplate code and verifying SQL queries at compile time.

## Components of Room

- There are having 3 components in room.

__Entity:__ 

- Instead of creating the SQLite table, we will create the Entity. Entity is nothing but a model class annotated with @Entity. 
- The variables of this class is our columns, and the class is our table.

__Database:__ 

- It is an abstract class where we define all our entities.

__DAO:__ 

- DAO Stands for Data Access Object. 
- It is an interface that defines all the operations that we need to perform in our database.
