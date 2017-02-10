DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS restaurants;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 100;

CREATE TABLE restaurants (
  id    INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name  VARCHAR NOT NULL,
  votes INTEGER NOT NULL    DEFAULT 0
);

CREATE TABLE dishes (
  id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name          VARCHAR NOT NULL,
  description   TEXT    NOT NULL,
  calories      INTEGER NOT NULL    DEFAULT 500,
  restaurant_id INTEGER NOT NULL,
  FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);

CREATE TABLE users
(
  id                  INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name                VARCHAR NOT NULL,
  email               VARCHAR NOT NULL,
  password            VARCHAR NOT NULL,
  registered          TIMESTAMP           DEFAULT now(),
  enabled             BOOL                DEFAULT TRUE,
  voted_restaurant_id INTEGER,
  FOREIGN KEY (voted_restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX users_unique_email_idx
  ON users (email);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR,
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);


