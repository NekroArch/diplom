CREATE TABLE dish_items
(
    id   serial NOT NULL,
    dish_id integer NOT NULL,
    price numeric (10,2) not null,
    PRIMARY KEY ( id ),
    FOREIGN KEY ( dish_id ) REFERENCES dishes ( id ) ON DELETE CASCADE
);
