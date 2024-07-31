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

CREATE TABLE "Student" (
    "studentId" INTEGER AUTO_INCREMENT PRIMARY KEY,
    "rank" VARCHAR(30) NOT NULL,
    "dateBegan" DATE NOT NULL,
    "weight" INTEGER,
    "height" INTEGER
);

