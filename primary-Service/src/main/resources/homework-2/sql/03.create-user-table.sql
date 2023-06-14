CREATE TABLE users
(
 id         serial NOT NULL,
 first_name varchar(255) NOT NULL,
 last_name  varchar(255) NOT NULL,
 mail       varchar(255) NOT NULL,
 phone      varchar(255) NOT NULL,
 password   varchar(255) NOT NULL,
 PRIMARY KEY ( id ) 
);

