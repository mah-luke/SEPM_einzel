-- insert initial test data
-- the IDs are hardcoded to enable references between further test data
-- negative IDs are used to not interfere with user-entered data and allow clean deletion of test data

INSERT INTO food (id, name, description, calories)
VALUES (1, 'Whey', 'yummy', 1.23456789),
       (2, 'Grass', 'fresh', 4.56789)
;


DELETE FROM horse where id < 0;

INSERT INTO horse (id, name, DESCRIPTION, DOB, SEX, FOODID)
VALUES (1, 'Wendy', 'wendys descr', '2000-01-01', 'Female', 1),
       (2, 'Eduard', 'eduards descr', '2001-02-02', 'Male', 2),
       (3, 'Belinda', 'julias horse', '1998-01-01', 'Female', 1)
;
