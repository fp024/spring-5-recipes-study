INSERT INTO users (username, password, enabled) VALUES ('user00', '{bcrypt}$2a$10$RRMggW9wP3d4pD34xnD5Oe3.WitfSUJQ2xYt8V6dhXX6QOJnb3fmS', 1);
INSERT INTO users (username, password, enabled) VALUES ('admin', '{bcrypt}$2a$10$4k68vngvW27pKYnjZe8qG.xzAR515YxKULjgHabszjX1CrhKc2mEi', 1);
INSERT INTO users (username, password, enabled) VALUES ('user01', '{bcrypt}$2a$10$vL.Zffr5FCU5o4AJN.Z7heB3rEFNB6VoQKmGLqWEEhurhZJhdz9ny', 1);

INSERT INTO authorities (username, authority) VALUES('user00', 'USER');
INSERT INTO authorities (username, authority) VALUES('user01', 'USER');

INSERT INTO authorities (username, authority) VALUES('admin', 'USER');
INSERT INTO authorities (username, authority) VALUES('admin', 'ADMIN');
