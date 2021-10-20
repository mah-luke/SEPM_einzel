-- insert initial test data
-- the IDs are hardcoded to enable references between further test data
-- negative IDs are used to not interfere with user-entered data and allow clean deletion of test data

DELETE FROM horse WHERE id < 0;
DELETE FROM food WHERE id < 0;

INSERT INTO food (id, name, description, calories)
VALUES  (-1, 'Hay', 'yummy', 1.23456789),
        (-2, 'Grass1', 'fresh', 4.56789),
        (-3, 'Flowers', 'desc', 3.021),
        (-4, 'Carrot', 'orange', 20),
        (-5, 'Bananaaaa', 'zummz', 666),
        (-6, 'Apple', 'red or green', 7),
        (-7, 'Orange', 'orange', 2),
        (-8, 'Grain', null, 1.203),
        (-9, 'Eggs', 'small', 0.02),
        (-10, 'Cereal', null, 3.430)
;

INSERT INTO horse (id, name, DESCRIPTION, DOB, SEX, FOODID, FATHERID, MOTHERID)
VALUES  (-1, 'Wendy', 'wendys descr', '2000-01-01', 'Female', -1, null, null),
        (-2, 'Eduard', 'eduards descr', '2001-02-02', 'Male', -2, null, null),
        (-3, 'Belinda', 'Julias Horse <3', '1998-04-29', 'Female', -5, null, null),
        (-4, 'Mickey', null, '1997-05-01', 'Female', -3, null, null),
        (-5, 'Joker', null, '2005-02-02', 'Male', -1, null, null),
        (-6, 'Luna', null, '2011-01-11', 'Female', -2, null, null),
        (-7, 'Badger', null, '2001-01-01', 'Male', -1, null, null),
        (-8, 'Willi', null, '2008-01-01', 'Female', -6, null, null),
        (-9, 'Holly', null, '2011-05-01', 'Female', -3, null, null),
        (-10, 'Tini', null, '2011-04-01', 'Female', -3, -5, -4)
;
