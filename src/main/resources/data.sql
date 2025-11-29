-- ==========================
-- Create user table
-- ==========================
CREATE TABLE app_user (
    user_id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255)
);

-- ==========================
-- Insert sample users
-- ==========================
INSERT INTO app_user (user_id, name, email, password)
VALUES
('1111-2222-3333', 'Bhumika', 'bhumika@gmail.com', '$2a$12$q5t/SFItGmE2jVX7ITT.vuOMz/Iuo5VSbw77n7.uNSGl3XasoQiki'),
('4444-5555-6666', 'Om', 'om@gmail.com', '$2a$12$q5t/SFItGmE2jVX7ITT.vuOMz/Iuo5VSbw77n7.uNSGl3XasoQiki'),
('7777-8888-9999', 'Re', 're@gmail.com', '$2a$12$q5t/SFItGmE2jVX7ITT.vuOMz/Iuo5VSbw77n7.uNSGl3XasoQiki'),
('aaaa-bbbb-cccc', 'Sneha', 'sneha@gmail.com', '$2a$12$q5t/SFItGmE2jVX7ITT.vuOMz/Iuo5VSbw77n7.uNSGl3XasoQiki'),
('dddd-eeee-ffff', 'Amit', 'amit@gmail.com', '$2a$12$q5t/SFItGmE2jVX7ITT.vuOMz/Iuo5VSbw77n7.uNSGl3XasoQiki');



-- ==========================
-- Create group table
-- ==========================
CREATE TABLE app_group (
    group_id VARCHAR(255) PRIMARY KEY,
    group_name VARCHAR(255)
);

-- ==========================
-- Insert one sample group
-- ==========================
INSERT INTO app_group (group_id, group_name)
VALUES ('g1-1234-5678', 'Trip Group');


-- ==========================
-- Create Many-to-Many join table
-- ==========================
CREATE TABLE group_users (
    group_id VARCHAR(255),
    user_id VARCHAR(255),
    PRIMARY KEY (group_id, user_id),
    FOREIGN KEY (group_id) REFERENCES app_group(group_id),
    FOREIGN KEY (user_id) REFERENCES app_user(user_id)
);

-- ==========================
-- Add users to the group
-- ==========================
INSERT INTO group_users (group_id, user_id)
VALUES
('g1-1234-5678', '1111-2222-3333'),
('g1-1234-5678', '4444-5555-6666'),
('g1-1234-5678', '7777-8888-9999'),
('g1-1234-5678', 'aaaa-bbbb-cccc');
