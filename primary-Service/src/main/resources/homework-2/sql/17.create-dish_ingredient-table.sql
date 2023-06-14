CREATE TABLE dishes_ingredients
(
 ingredient_id   integer NOT NULL,
 dish_id         integer NOT NULL,
 measure_unit_id integer DEFAULT 1,
 volume          decimal,
 FOREIGN KEY ( ingredient_id ) REFERENCES ingredients ( id ) ON DELETE CASCADE,
 FOREIGN KEY ( dish_id ) REFERENCES dishes ( id ) ON DELETE CASCADE,
 FOREIGN KEY ( measure_unit_id ) REFERENCES measure_units ( id ) ON DELETE CASCADE,
 PRIMARY KEY ( ingredient_id, dish_id )
);


