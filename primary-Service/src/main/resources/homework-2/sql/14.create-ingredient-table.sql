CREATE TABLE ingredients
(
 id              serial NOT NULL,
 name            varchar(100) NOT NULL,
 measure_unit_id integer DEFAULT 1,
 quantity        integer NOT NULL,
 PRIMARY KEY ( id ),
 FOREIGN KEY ( measure_unit_id ) REFERENCES measure_units ( id ) ON DELETE CASCADE
);


