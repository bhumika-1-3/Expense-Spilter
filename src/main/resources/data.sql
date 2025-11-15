CREATE TABLE app_user (
    user_id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255)
);

INSERT INTO app_user (user_id, name, email)
VALUES ('1111-2222-3333', 'Bhumika', 'bhumika@gmail.com');

INSERT INTO app_user (user_id, name, email)
VALUES ('4444-5555-6666', 'Om', 'Om@gmail.com');