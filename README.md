# rest-cinema-app
` Rest, Spring Boot, Spring Data JPA, MySql, Java 13`

REST API to managing system of distribution tickets.
 There are two main ROLE'S that you can receive:
 **ADMIN AND USER**
 
###### ADMIN - _role will be checked by password_
He is able to change all existing values like Movies, 
Places, Seances, Tickets and Users. Also Admin can add new values 
for the those tables.

###### USER - _role will be checked by password_
He have his own account placed in users table at date base. 
He can marge favorite movies, buy tickets, filtering movies 
and getting ticket purchase history. Also he may send JSON file 
on his e-mail _(option will be able in the future)_ with history or
other information about favorite films.
