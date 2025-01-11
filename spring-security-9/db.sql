
CREATE TABLE users (
       id UUID PRIMARY KEY,
       email VARCHAR(255) UNIQUE NOT NULL,
       password VARCHAR(255) NOT NULL,
       first_name VARCHAR(255) NOT NULL,
       last_name VARCHAR(255) NOT NULL,
       roles TEXT[] NOT NULL,
       branch TEXT NOT NULL CHECK (branch IN ('IT', 'CS', 'ME', 'ETC', 'CE')),
       year TEXT NOT NULL CHECK (year IN ('FIRST_YEAR', 'SECOND_YEAR', 'THIRD_YEAR', 'FOURTH_YEAR'))
    );


CREATE TABLE book (
      id UUID PRIMARY KEY,
      isbn VARCHAR(20) UNIQUE NOT NULL,
      title VARCHAR(255) NOT NULL,
      description TEXT,
      authors TEXT[],
      borrowed BOOLEAN NOT NULL DEFAULT FALSE,
      borrowed_by UUID REFERENCES users(id),
      branch TEXT NOT NULL CHECK (branch IN ('IT', 'CS', 'ME', 'ETC', 'CE')),
      year TEXT NOT NULL CHECK (year IN ('FIRST_YEAR', 'SECOND_YEAR', 'THIRD_YEAR', 'FOURTH_YEAR'))
    );



INSERT INTO users (id, email, password, first_name, last_name, roles, branch, year) VALUES
('c47641ee-e63c-4c13-8cd2-1c2490aee0b3', 'bruce.wayne@example.com', '$2a$10$epekWs3wAOK8L9tsq6IF6.zuqtmG/n4vS8uiNMsA.tEeeKui8LChG', 'Bruce', 'Wayne', ARRAY['LIBRARY_USER'], 'ME', 'SECOND_YEAR'),
('9af8d19b-c53a-41dc-b43e-251d77f4fc2b', 'bruce.banner@example.com', '$2a$10$0Urj.KcovlDj5a1GXU9lX.GtP215cCptMdOoZPJEDdmFnhfZYBaQC', 'Bruce', 'Banner', ARRAY['LIBRARY_USER'], 'CS', 'THIRD_YEAR'),
('40c5ad0d-41f7-494b-8157-33fad16012aa', 'peter.parker@example.com', '$2a$10$2.8IDZnMUu/9QXrUQf3ZMeMGXcZlB3MnzTe/Dh6AqoGadnuY328By', 'Peter', 'Parker', ARRAY['LIBRARY_CURATOR'], 'IT', 'FOURTH_YEAR'),
('0d2c04f1-e25f-41b5-b4cd-3566a081200f', 'clark.kent@example.com', '$2a$10$Bn1ckSw2pfRivnDW0.A1euyVpurIuzaMnCa6az/bb4.iCZg0K5z.K', 'Clark', 'Kent', ARRAY['LIBRARY_ADMIN'], 'ETC', 'FIRST_YEAR'),
('a7365322-0aac-4602-83b6-380bccb786e2', 'old@example.com', '$2a$10$OpAVDaWAu2hERfz6tjtLgeJBRSm/4UVy4H3Ma2KBLWYiq2aMVPOOK', 'Library', 'OldEncryption', ARRAY['LIBRARY_USER'], 'CE', 'SECOND_YEAR'),
('f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'sam.dane@example.com', '$2a$10$ZbDeW8zvSr406mchsfiM6u3GRPIZO/jKRfTq9r1mIkzORV535PqaW', 'Sam', 'Dane', ARRAY['LIBRARY_USER'], 'ME', 'SECOND_YEAR');

INSERT INTO book (id, isbn, title, description, authors, borrowed, borrowed_by, branch, year) VALUES
('f9bf70d6-e56d-4cab-be6b-294cd05f599f', '9780132350884', 'Clean Code', 'A Handbook of Agile Software Craftsmanship', ARRAY['Bob C. Martin'], TRUE, 'c47641ee-e63c-4c13-8cd2-1c2490aee0b3', 'CS', 'THIRD_YEAR'),
('3038627d-627e-448d-8422-0a5705c9e8f1', '9781449374648', 'Cloud Native Java', 'Guide to building cloud-native applications with Spring Boot and Cloud Foundry', ARRAY['Josh Long', 'Kenny Bastiani'], FALSE, NULL, 'IT', 'FOURTH_YEAR'),
('081314cb-4abf-43e5-9b38-7d7261edb10d', '9781617291203', 'Spring in Action', 'Hands-on guide to Spring Framework 4', ARRAY['Craig Walls'], FALSE, NULL, 'ME', 'SECOND_YEAR'),
('02c3d1fb-ca32-46bd-818f-704012b3fe9c', '9781942788003', 'The DevOps Handbook', 'Comprehensive guide to DevOps practices', ARRAY['Gene Kim', 'Jez Humble', 'Patrick Debois', 'John Willis'], FALSE, NULL, 'ETC', 'FIRST_YEAR');

select * from users;

select * from book;
