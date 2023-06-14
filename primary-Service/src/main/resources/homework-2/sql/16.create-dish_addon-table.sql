CREATE TABLE dishes_addons
(
 addon_id integer NOT NULL,
 dish_id  integer NOT NULL,
 FOREIGN KEY ( dish_id ) REFERENCES dishes ( id ) ON DELETE CASCADE,
 FOREIGN KEY ( addon_id ) REFERENCES addons ( id ) ON DELETE CASCADE,
 PRIMARY KEY ( addon_id, dish_id )
);


