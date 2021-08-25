insert into author (id, name) values (1, 'TestAuthor1');
insert into author (id, name) values (2, 'TestAuthor2');
insert into genre (id, name) values (1, 'TestGenre1');
insert into genre (id, name) values (2, 'TestGenre2');
insert into genre (id, name) values (3, 'TestGenre3');
insert into book (id, name, author_id, genre_id) values (1, 'TestBook1', 1, 1);
insert into book (id, name, author_id, genre_id) values (2, 'TestBook2', 1, 2);
insert into comment (id, book_id, text) values(1, 1, 'TestComment1ForBook1');
insert into comment (id, book_id, text) values(2, 2, 'TestComment1ForBook2');
insert into comment (id, book_id, text) values(3, 2, 'TestComment2ForBook2');
insert into user(id, login, password) values(1, 'user', '$2a$10$upDS8XmWIPHBpnAI7K6sL.Iqueo1F68KoJocGXWue3kIY/.sbxele'); -- pwd
insert into user(id, login, password) values(2, 'admin', '$2a$10$V2QtN/sA9cenfuhqiv62wuGmQEik.jTHNaIG8Q1D37hIptZfSijjC'); -- password
insert into user(id, login, password) values(3, 'test_user', '$2a$10$QBzwtQPnbLpEdxbK45HFBOABcQmB4rfsy7cV.gwx6sXHyp369N1Ya'); -- pass
insert into role(id, name) values(1, 'admin');
insert into role(id, name) values(2, 'user');
insert into role(id, name) values(3, 'test_role');
insert into user_role(id, user_id, role_id) values(1, 1, 2);
insert into user_role(id, user_id, role_id) values(2, 2, 1);
insert into user_role(id, user_id, role_id) values(3, 3, 2);
insert into user_role(id, user_id, role_id) values(4, 3, 3);