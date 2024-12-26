CREATE TABLE IF NOT EXISTS users (
  id integer NOT NULL GENERATED BY DEFAULT AS IDENTITY,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(512) NOT NULL,
  CONSTRAINT pk_user PRIMARY KEY (id),
  CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS item (
  id integer NOT NULL GENERATED BY DEFAULT AS IDENTITY,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(512) NOT NULL,
  request VARCHAR(512),
  available boolean NOT NULL,
  owner_id integer NOT NULL,
  CONSTRAINT pk_item PRIMARY KEY (id),
  CONSTRAINT pk_user_id FOREIGN KEY (owner_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS comment (
  id integer NOT NULL GENERATED BY DEFAULT AS IDENTITY,
  text VARCHAR(255) NOT NULL,
  item_id integer NOT NULL,
  author_id integer NOT NULL,
  "created" timestamp NOT NULL DEFAULT now(),
  CONSTRAINT pk_comment PRIMARY KEY (id),
  CONSTRAINT pk_comment_item_id FOREIGN KEY (item_id) REFERENCES item (id),
  CONSTRAINT pk_comment_user_id FOREIGN KEY (author_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS booking (
  id integer NOT NULL GENERATED BY DEFAULT AS IDENTITY,
  "start" timestamp NOT NULL,
  "end" timestamp NOT NULL,
  item_id integer NOT NULL,
  booker_id integer,
  status VARCHAR(255) NOT NULL,
  CONSTRAINT pk_booking PRIMARY KEY (id),
  CONSTRAINT pk_booking_id FOREIGN KEY (item_id) REFERENCES item (id),
  CONSTRAINT pk_booker_id FOREIGN KEY (booker_id) REFERENCES users (id)
);