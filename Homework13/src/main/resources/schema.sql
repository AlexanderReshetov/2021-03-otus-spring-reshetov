DROP TABLE IF EXISTS author;
CREATE TABLE author(id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255));
DROP TABLE IF EXISTS genre;
CREATE TABLE genre(id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255));
DROP TABLE IF EXISTS book;
CREATE TABLE book(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    author_id BIGINT,
    genre_id BIGINT,
    CONSTRAINT fkBookAuthor FOREIGN KEY (author_id) REFERENCES author(id) ON UPDATE RESTRICT ON DELETE RESTRICT,
    CONSTRAINT fkBookGenre FOREIGN KEY (genre_id) REFERENCES genre(id) ON UPDATE RESTRICT ON DELETE RESTRICT);
DROP TABLE IF EXISTS comment;
CREATE TABLE comment(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    book_id BIGINT,
    text VARCHAR(10000),
    CONSTRAINT fkCommentBook FOREIGN KEY (book_id) REFERENCES book(id) ON UPDATE CASCADE ON DELETE CASCADE);
CREATE TABLE user(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(255),
    password VARCHAR(255));
CREATE TABLE role(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255));
CREATE TABLE user_role(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    role_id BIGINT,
    CONSTRAINT fkUserRoleUser FOREIGN KEY (user_id) REFERENCES user(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fkUserRoleRole FOREIGN KEY (role_id) REFERENCES role(id) ON UPDATE CASCADE ON DELETE RESTRICT);