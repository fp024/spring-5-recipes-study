INSERT INTO users (username, password, enabled) VALUES ('user00', '{noop}user00', 1);
INSERT INTO users (username, password, enabled) VALUES ('admin', '{noop}admin', 1);
INSERT INTO users (username, password, enabled) VALUES ('user01', '{noop}user01', 0);

INSERT INTO authorities (username, authority) VALUES('user00', 'USER');
INSERT INTO authorities (username, authority) VALUES('user01', 'USER');

INSERT INTO authorities (username, authority) VALUES('admin', 'USER');
INSERT INTO authorities (username, authority) VALUES('admin', 'ADMIN');
