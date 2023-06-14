CREATE TABLE roles_privileges
(
 privilege_id integer NOT NULL,
 role_id      integer NOT NULL,
 FOREIGN KEY ( privilege_id ) REFERENCES privileges ( id ) ON DELETE CASCADE,
 FOREIGN KEY ( role_id ) REFERENCES roles ( id ) ON DELETE CASCADE,
 PRIMARY KEY ( privilege_id, role_id )
);
