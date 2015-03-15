INSERT INTO account (id, name, password, salt, created, updated, status, email, config) VALUES (1, 'test.user', 'f0e734ab8910dee9762d0ee07964288dd8ffd95be9ab646af02ba1c1256e5037', '3994c7aea794c1cf', '2014-12-07 13:52:56.805', '2014-12-07 13:53:16.062', 0, 'test.user@test.com', '{"firstName":"Test","lastName":"User"}');

INSERT INTO plan (id, name, goal, creator, used) VALUES (1, 'Main plan', 0, 1, true);

INSERT INTO phase (id, position, goal, description, plan, weeks) VALUES (1, 1, 1, 'Main phase', 1, 3);

INSERT INTO workout (id, week_day, phase, muscles, position) VALUES (1, 1, 1, '2', 1);

INSERT INTO description (id, name, movie_url, description, party_muscles, type, equipment, level, mechanics, force) VALUES (1, '{"pl":"pl_1","en":"en_1"}', 'url_1', NULL, 0, 0, NULL, 0, 0, 0);
INSERT INTO description (id, name, movie_url, description, party_muscles, type, equipment, level, mechanics, force) VALUES (2, '{"pl":"pl_2","en":"en_2"}', 'url_2', NULL, 2, 1, NULL, 1, 1, 1);
INSERT INTO description (id, name, movie_url, description, party_muscles, type, equipment, level, mechanics, force) VALUES (3, '{"pl":"pl_3","en":"en_3"}', 'url_3', NULL, 9, 2, NULL, 2, 0, 2);
INSERT INTO description (id, name, movie_url, description, party_muscles, type, equipment, level, mechanics, force) VALUES (4, '{"pl":"pl_4","en":"en_4"}', 'url_4', NULL, 1, 4, NULL, 2, 1, 2);
INSERT INTO description (id, name, movie_url, description, party_muscles, type, equipment, level, mechanics, force) VALUES (5, '{"pl":"pl_5","en":"en_5"}', 'url_5', '<h1>test 5</h1>', 12, 3, NULL, 1, 0, 0);
INSERT INTO description (id, name, movie_url, description, party_muscles, type, equipment, level, mechanics, force) VALUES (6, '{"pl":"pl_6","en":"en_6"}', NULL, NULL, 7, 6, NULL, 0, 1, 1);
INSERT INTO description (id, name, movie_url, description, party_muscles, type, equipment, level, mechanics, force) VALUES (7, '{"pl":"pl_7","en":"en_7"}', 'url_7', '<h1>test 7</h1>', 1, 5, NULL, 0, 0, 1);
INSERT INTO description (id, name, movie_url, description, party_muscles, type, equipment, level, mechanics, force) VALUES (8, '{"pl":"pl_8","en":"en_8"}', 'url_8', NULL, 3, 2, NULL, 1, 1, 2);

INSERT INTO exercise (id, workout, description, reps, position, super_set) VALUES (1, 1, 2, '12;12;12;12', 1, 1);
INSERT INTO exercise (id, workout, description, reps, position, super_set) VALUES (2, 1, 3, '12;10;8;6', 2, 2);

INSERT INTO user_workout (id, account, date, remind, comment, workout) VALUES (1, 1, '2014-12-07 13:52:56.805', false, null, 1);

INSERT INTO execution (id, reps, weights, exercise, confirm, comment, workout) VALUES (1,'12;12;12;12','30;30;30;30',1,false,null,1);
INSERT INTO execution (id, reps, weights, exercise, confirm, comment, workout) VALUES (2,'12;12;11;12','30;30;30.5;30',2,false,null,1);

INSERT INTO equipment (id, type, data, length, weight, strength) VALUES (3, 4, '{"connectedLoad": false,"type":"STRAIGHT"}', 180, 8, 150);
INSERT INTO equipment (id, type, data, length, weight, strength) VALUES (1, 0, '{"handles":6}', 120, NULL, 200);
INSERT INTO equipment (id, type, data, length, weight, strength) VALUES (2, 1, '{"height":43}', 110, NULL, 200);
INSERT INTO equipment (id, type, data, length, weight, strength) VALUES (4, 3, NULL, NULL, 10, NULL);
INSERT INTO equipment (id, type, data, length, weight, strength) VALUES (26, 6, '{"levels":7,"height":{"min":80,"max":137}}', NULL, NULL, NULL);
INSERT INTO equipment (id, type, data, length, weight, strength) VALUES (27, 2, '{"connectedLoad":false}', 45, 2, 50);
INSERT INTO equipment (id, type, data, length, weight, strength) VALUES (29, 5, '{"handles":4}', NULL, NULL, 150);