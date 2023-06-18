CREATE TABLE dishes
(
 id      serial NOT NULL,
 name    varchar(100) NOT NULL,
 base_price numeric(10,2) not null,
 menu_id integer NOT NULL DEFAULT 1,
 PRIMARY KEY ( id ),
 FOREIGN KEY ( menu_id ) REFERENCES menu ( id ) ON DELETE CASCADE
);

