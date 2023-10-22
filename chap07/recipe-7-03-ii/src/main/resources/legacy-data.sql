INSERT INTO member (id, username, password) VALUES (1, 'admin', '{noop}admin');
INSERT INTO member (id, username, password) VALUES (2, 'user00', '{noop}user00');
INSERT INTO member (id, username, password) VALUES (3, 'user01', '{noop}user01');

INSERT INTO member_role (member_id, role) VALUES(2, 'USER');
INSERT INTO member_role (member_id, role) VALUES(3, 'USER');

INSERT INTO member_role (member_id, role) VALUES(1, 'USER');
INSERT INTO member_role (member_id, role) VALUES(1, 'ADMIN');
