CREATE TABLE addons
(
 id          serial NOT NULL,
 category_id integer NOT NULL,
 name        varchar(255) NOT NULL,
 price       numeric (10,2) NOT NULL,
 PRIMARY KEY ( id ),
 FOREIGN KEY ( category_id ) REFERENCES addons_category ( id ) ON DELETE CASCADE
);


