CREATE TABLE users_roles
(
 role_id integer NOT NULL,
 user_id integer NOT NULL,
 FOREIGN KEY ( role_id ) REFERENCES roles ( id ) ON DELETE CASCADE,
 FOREIGN KEY ( user_id ) REFERENCES users ( id ) ON DELETE CASCADE,
 PRIMARY KEY ( role_id, user_id )
);

