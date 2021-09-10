insert into genre (id, name) values (1, 'KafkaTestGenre1');
insert into genre (id, name) values (2, 'TestGenre2');
insert into genre (id, name) values (3, 'TestGenre3');
insert into user(id, login, password) values(1, 'user', '$2a$10$V2QtN/sA9cenfuhqiv62wuGmQEik.jTHNaIG8Q1D37hIptZfSijjC'); -- password
insert into role(id, name) values(1, 'ROLE_USER');
insert into user_role(id, user_id, role_id) values(1, 1, 1);