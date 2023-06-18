CREATE TABLE dishes_ingredients
(
 ingredient_id   integer NOT NULL,
 dish_id         integer NOT NULL,
 volume          decimal,
 FOREIGN KEY ( ingredient_id ) REFERENCES ingredients ( id ) ON DELETE CASCADE,
 FOREIGN KEY ( dish_id ) REFERENCES dishes ( id ) ON DELETE CASCADE,
 PRIMARY KEY ( ingredient_id, dish_id )
);


