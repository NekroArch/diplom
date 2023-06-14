CREATE TABLE cart_dishes
(
 dish_item_id integer NOT NULL,
 cart_id integer NOT NULL,
 quantity integer NOT NULL DEFAULT 1,
 FOREIGN KEY ( dish_item_id ) REFERENCES dish_items ( id ) ON DELETE CASCADE,
 FOREIGN KEY ( cart_id ) REFERENCES carts ( id ) ON DELETE CASCADE,
 PRIMARY KEY ( dish_item_id, cart_id )
);

