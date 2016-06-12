insert into role(id, name) values (1, 'ROLE_ADMIN'), (2, 'ROLE_WRITE'), (3, 'ROLE_READ');
insert into auth(id, name) values (1, 'AUTH_ADMIN'), (2, 'AUTH_WRITE'), (3, 'AUTH_READ');
insert into rel_role_auth(id, id_role, id_auth) values (1, 1, 1), (2, 1, 2), (3, 1, 3), (4, 2, 2), (5, 2, 3), (6, 3, 3);

insert into user(id, username, password, active, name, surname, email, id_role) values (1, 'admin', '$2a$10$dqQWBmJUN4DiTpsBgcf5zerORT9fz4b04uQ5bZ/o0ShE3/CsMruRO', true, 'Admin', 'McAdmin', 'admin@talaialde.com', 1);

insert into vat(id, value) values (1, 21);
