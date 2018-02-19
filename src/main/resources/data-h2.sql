INSERT into USERS VALUES
(1, 'w@w.w', 'Wojtek', 'Korobacz', '$2a$10$p1wsTFf9F6aRmhuTzD1HZuRq0q5wdmUfWpudpJLujo0SFsIKsonTC'),
(2, 'p@p.p', 'Pawel', 'Pawlowski', '$2a$10$2QgdN.Qs1HVxGR18qsELtOksDDOsIFtvBlWff08rloq8FbPOEdlxS'),
(3, 'u@u.u', 'Ula', 'Ulowska', '$2a$10$2QgdN.Qs1HVxGR18qsELtOksDDOsIFtvBlWff08rloq8FbPOEdlxS');

--1 hasło: w
--2 hasło: p
--3 hasło: p

INSERT into ROLE VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER');

INSERT into USERS_ROLES VALUES
(1,1),
(2,2);