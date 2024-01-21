DROP TABLE vehicle IF EXISTS;
CREATE TABLE vehicle
(
    vehicle_no VARCHAR(10) NOT NULL,
    color      VARCHAR(10),
    wheel      INT,
    seat       INT,
    PRIMARY KEY (vehicle_no)
);

INSERT INTO vehicle (vehicle_no, color, wheel, seat)
VALUES ('TEM1000', 'Yellow', 6, 6);
INSERT INTO vehicle (vehicle_no, color, wheel, seat)
VALUES ('TEM1001', 'Gray', 4, 2);

INSERT INTO vehicle (vehicle_no, color, wheel, seat)
VALUES ('EX0001', 'Gray', 4, 4);
