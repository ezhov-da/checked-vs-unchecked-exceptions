CREATE TABLE EMPLOYEES
(
    NAME TEXT,
    AGE  INTEGER
);

INSERT INTO EMPLOYEES(NAME, AGE)
VALUES ('Vaughn Vernon', 56),
       ('Eric Evans', 34),
       ('Ieronim Bosch', 25);

SELECT *
FROM EMPLOYEES;