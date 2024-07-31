DROP TABLE "Profile" IF EXISTS;
DROP TABLE "Student" IF EXISTS;

CREATE TABLE "Profile" (
    "profileId" INTEGER AUTO_INCREMENT PRIMARY KEY,
    "firstName" VARCHAR(50) NOT NULL,
    "middleName" VARCHAR(50),
    "lastName" VARCHAR(50) NOT NULL,
    "preferredName" VARCHAR(30),
    "dateOfBirth" DATE NOT NULL,
    "sex" CHAR(1) NOT NULL,
    "phoneNumber" VARCHAR(50)
);

CREATE TABLE "Address" (
    "addressId" INTEGER AUTO_INCREMENT PRIMARY KEY,
    "addressLine1" VARCHAR(50),
    "addressLine2" VARCHAR(50),
    "city" VARCHAR(50),
    "state" VARCHAR(30),
    "zipcode" VARCHAR(30)
);

--CREATE TABLE "Promotions" (
--    "promotionId" INTEGER AUTO_INCREMENT PRIMARY KEY,
--    "oldRank" VARCHAR(50) NOT NULL,
--    "newRank" VARCHAR(50), NOT NULL,
--    "date" DATE NOT NULL
--);

CREATE TABLE "Student" (
    "studentId" INTEGER AUTO_INCREMENT PRIMARY KEY,
    "rank" VARCHAR(30) NOT NULL,
    "dateBegan" DATE NOT NULL,
    "weight" INTEGER,
    "height" INTEGER
);

