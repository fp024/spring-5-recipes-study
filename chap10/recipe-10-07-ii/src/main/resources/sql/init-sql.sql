--  EmbeddedDataSource를 사용할까하다가...
--  트랜젝션 동작후의 변화를 봐야해서, 
--  HSQLDB를 띄우고 아래 초기화 스크립트를 한번 실행해주고 변화를 보는게
--  나아서 그것은 사용하지 않았다.
--
--  책은 PostgreSQL 로 되어있긴한데, HSQLDB에서도 잘 동작한다.
--  server.database.{번호}=file:~/hsqldb-data/spring-5-recipes-study-chap10
--  server.dbname.{번호}=spring-5-recipes-study-chap10 */

DROP TABLE IF EXISTS ACCOUNT; 
DROP TABLE IF EXISTS BOOK_STOCK;
DROP TABLE IF EXISTS BOOK;

CREATE TABLE IF NOT EXISTS BOOK (
    ISBN         VARCHAR(50)    NOT NULL,
    BOOK_NAME    VARCHAR(100)   NOT NULL,
    PRICE        INT,
    PRIMARY KEY (ISBN)
);

CREATE TABLE IF NOT EXISTS BOOK_STOCK (
    ISBN     VARCHAR(50)    NOT NULL,
    STOCK    INT            NOT NULL,
    PRIMARY KEY (ISBN),
    CONSTRAINT positive_stock CHECK (STOCK >= 0)
);

CREATE TABLE IF NOT EXISTS ACCOUNT (
    USERNAME    VARCHAR(50)    NOT NULL,
    BALANCE     INT            NOT NULL,
    PRIMARY KEY (USERNAME),
    CONSTRAINT positive_balance CHECK (BALANCE >= 0)
);

INSERT INTO BOOK(ISBN, BOOK_NAME, PRICE) VALUES( '0001', 'The First Book', 30);
INSERT INTO BOOK(ISBN, BOOK_NAME, PRICE) VALUES( '0002', 'The Second Book', 50);

INSERT INTO BOOK_STOCK(ISBN, STOCK) VALUES('0001', 10);
INSERT INTO BOOK_STOCK(ISBN, STOCK) VALUES('0002', 10);

INSERT INTO ACCOUNT(USERNAME, BALANCE) VALUES('user1', 40);
