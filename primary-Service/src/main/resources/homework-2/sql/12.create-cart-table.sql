CREATE TABLE carts
(
 id      serial NOT NULL,
 user_id integer NOT NULL,
 PRIMARY KEY ( id ),
 FOREIGN KEY ( user_id ) REFERENCES users ( id ) ON DELETE CASCADE,
 UNIQUE (user_id, id)
);


