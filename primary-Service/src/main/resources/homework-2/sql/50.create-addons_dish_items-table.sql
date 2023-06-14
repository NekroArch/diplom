CREATE TABLE addons_dish_items
(
    dish_items_id integer not null,
    addons_id integer NOT NULL,
    quantity integer not null,
    PRIMARY KEY ( dish_items_id, addons_id ),
    FOREIGN KEY ( dish_items_id ) REFERENCES dish_items ( id ) ON DELETE CASCADE,
    FOREIGN KEY ( addons_id ) REFERENCES addons ( id ) ON DELETE CASCADE
);
