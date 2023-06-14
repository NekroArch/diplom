CREATE TABLE ordered_dishes
(
 quantity integer NOT NULL,
 dish_item_id  integer NOT NULL,
 order_id integer NOT NULL,
 FOREIGN KEY ( order_id ) REFERENCES orders ( id ) ON DELETE CASCADE,
 FOREIGN KEY ( dish_item_id ) REFERENCES dish_items ( id ) ON DELETE CASCADE,
 PRIMARY KEY ( dish_item_id, order_id )
);


