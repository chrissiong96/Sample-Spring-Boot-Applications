CREATE TABLE users (
    id int IDENTITY(1000,1) NOT NULL PRIMARY KEY,
    details VARCHAR(255),
    created_date DATETIME,
    updated_date DATETIME,
    status VARCHAR(1),
    "version" int
);