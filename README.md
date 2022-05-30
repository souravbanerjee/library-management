# library-management

The application is a simple library management system consists of three major Entities.
Book, User and Library.
To make the code simple and to avoid creating DB I used json as a database which is present in application.properties.
the server.port is defined by 9922 as my lapop's 8080 port was occupied with other process.

Note: As we are not majorly focusing on the database I avoided using user entity.

To run the application simply check out the code, build (clean install) and run LibrarymanagementsystemApplication as java application.
1. No in memory database is used.
2. No typical file system is used.

The data is used from json which is defined in application.properties as bookDatabase, libraryDatabase and usersDatabase respectively.

Configuring postman to fire each request to check the functionality, and wasting time on it I implemented swagger to make the application as simple as I could.
after running the application please use http://localhost:9922/swagger-ui/index.html 

services exposed:

**1. /book/listAllBooks :** to find all the books from book table (json data). if book table is empty, it will return nothing.

  output:
  
  [
  {
    "id": "1",
    "name": "Java Programming",
    "category": "Programing",
    "author": "Herbart Shield",
    "publisher": "TATA MC GRW Hills",
    "stock": 10
  },
  {
    "id": "2",
    "name": "C Programming",
    "category": "Programing",
    "author": "J Kanitkar",
    "publisher": "TATA MC GRW Hills",
    "stock": 10
  },
  {
    "id": "3",
    "name": "Complete Ramayana",
    "category": "Mythology",
    "author": "Devdutt Pattnaik",
    "publisher": "Gitapress",
    "stock": 10
  },
  {
    "id": "4",
    "name": "Complete Mahabharata",
    "category": "Mythology",
    "author": "Amish Tripathi",
    "publisher": "Gitapress",
    "stock": 0
  }
]

**2. /library/getLibraryDetails :** this is just for finding which book issued history of usrs.

  output:
  
  [
  {
    "id": 1,
    "userId": "1",
    "bookId": "1",
    "issueDate": "2022-05-28",
    "returnDate": ""
  },
  {
    "id": 2,
    "userId": "1",
    "bookId": "2",
    "issueDate": "2022-05-28",
    "returnDate": ""
  },
  {
    "id": 3,
    "userId": "2",
    "bookId": "2",
    "issueDate": "2022-05-28",
    "returnDate": ""
  }
]

**3. /library/issueBook:**

  Note: Please follow the below request pattern to issue Book.
  
  Request: 
          {
            "userId": "2",
            "bookId": "1"
          }
          
  Response:
          {
            "id": 2338503087551692300,
            "userId": "2",
            "bookId": "1",
            "issueDate": "2022-05-30",
            "returnDate": null
          }
          
 **4. /library/returnBook:**
 
 Note: Please follow the below request pattern to return Book(s).
 
 Request:
         [
          {
            "userId": "1",
            "bookId": "1"
          },
         {
            "userId": "1",
            "bookId": "2"
          }
        ]
        
  Response:
        [
          {
            "id": 1,
            "userId": "1",
            "bookId": "1",
            "issueDate": "2022-05-28",
            "returnDate": "2022-05-30"
          },
          {
            "id": 2,
            "userId": "1",
            "bookId": "2",
            "issueDate": "2022-05-28",
            "returnDate": "2022-05-30"
          }
        ]
    
***_Same request can be used in Postman to test_.**
