CREATE TABLE orders
(
 id        serial NOT NULL,
 user_id   integer NOT NULL,
 status_id integer default 1,
 price     numeric (10,2) NOT NULL,
 PRIMARY KEY ( id ),
 FOREIGN KEY ( user_id ) REFERENCES status ( id ) ON DELETE CASCADE,
 FOREIGN KEY ( status_id ) REFERENCES users ( id )
);


