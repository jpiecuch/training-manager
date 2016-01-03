INSERT INTO account (id, name, password, salt, created, updated, status, email, config, provider, social_type) VALUES (1, 'test.user', 'dcc4cec778c00b632fba26da142d95d0b46a05e0a5f944a0484346c0656def67', 'jp88', '2014-12-07 13:52:56.805', '2014-12-07 13:53:16.062', 0, 'test.user@test.com', '{"firstName":"Test","lastName":"User"}', 0, 3);

INSERT INTO role (id, name, permissions, modifiable) VALUES (1, 'ADMIN', '0,1,2', true);

INSERT INTO account_role (account, role) VALUES (1,1);

INSERT INTO account_record (id, type, value, date, account) VALUES (1, 0, '80.0', '2014-12-07 00:00:00', 1);

INSERT INTO plan (id, name, goal, creator, used) VALUES (1, 'Main plan', 0, 1, false);

INSERT INTO phase (id, position, goal, description, plan, weeks) VALUES (1, 1, 1, 'First phase', 1, 3);
INSERT INTO phase (id, position, goal, description, plan, weeks) VALUES (2, 2, 1, 'Second phase', 1, 3);

INSERT INTO workout (id, week_day, phase, muscles) VALUES (1, 0, 1, '2');
INSERT INTO workout (id, week_day, phase, muscles) VALUES (2, 1, 1, '3;5');
INSERT INTO workout (id, week_day, phase, muscles) VALUES (3, 3, 2, '7');
INSERT INTO workout (id, week_day, phase, muscles) VALUES (4, 5, 2, '11');

INSERT INTO description (id, name, movie_url, description, party_muscles, type, equipment, level, mechanics, force, sides, sets) VALUES (1, '{"pl":"pl_1","en":"en_1"}', 'url_1', NULL, 0, 0, NULL, 0, 0, 0, 0, 0);
INSERT INTO description (id, name, movie_url, description, party_muscles, type, equipment, level, mechanics, force, sides, sets) VALUES (2, '{"pl":"pl_2","en":"en_2"}', 'url_2', NULL, 2, 1, NULL, 1, 1, 1,0, 0);
INSERT INTO description (id, name, movie_url, description, party_muscles, type, equipment, level, mechanics, force, sides, sets) VALUES (3, '{"pl":"pl_3","en":"en_3"}', 'url_3', NULL, 9, 2, NULL, 2, 0, 2, 0, 0);
INSERT INTO description (id, name, movie_url, description, party_muscles, type, equipment, level, mechanics, force, sides, sets) VALUES (4, '{"pl":"pl_4","en":"en_4"}', 'url_4', NULL, 1, 4, NULL, 2, 1, 2, 0, 0);
INSERT INTO description (id, name, movie_url, description, party_muscles, type, equipment, level, mechanics, force, sides, sets) VALUES (5, '{"pl":"pl_5","en":"en_5"}', 'url_5', '<h1>test 5</h1>', 12, 3, NULL, 1, 0, 0, 1, 1);
INSERT INTO description (id, name, movie_url, description, party_muscles, type, equipment, level, mechanics, force, sides, sets) VALUES (6, '{"pl":"pl_6","en":"en_6"}', NULL, NULL, 7, 6, NULL, 0, 1, 1, 1, 1);
INSERT INTO description (id, name, movie_url, description, party_muscles, type, equipment, level, mechanics, force, sides, sets) VALUES (7, '{"pl":"pl_7","en":"en_7"}', 'url_7', '<h1>test 7</h1>', 1, 5, NULL, 0, 0, 1, 1, 1);
INSERT INTO description (id, name, movie_url, description, party_muscles, type, equipment, level, mechanics, force, sides, sets) VALUES (8, '{"pl":"pl_8","en":"en_8"}', 'url_8', NULL, 3, 2, NULL, 1, 1, 2, 1, 1);

INSERT INTO exercise (id, workout, description, reps, position, super_set) VALUES (1, 1, 2, '12;12;12;12', 1, 1);
INSERT INTO exercise (id, workout, description, reps, position, super_set) VALUES (2, 1, 3, '12;10;8;6', 1, 2);
INSERT INTO exercise (id, workout, description, reps, position, super_set) VALUES (3, 2, 7, '12;10;8;6', 1, 1);
INSERT INTO exercise (id, workout, description, reps, position, super_set) VALUES (4, 2, 8, '12;10;8;6', 2, 1);
INSERT INTO exercise (id, workout, description, reps, position, super_set) VALUES (5, 3, 5, '12;10;8;6;2', 1, 1);
INSERT INTO exercise (id, workout, description, reps, position, super_set) VALUES (6, 4, 1, '12;12;12', 1, 1);

INSERT INTO user_workout (id, account, date, remind, comment, workout, state) VALUES (1, 1, '2014-12-07 00:00:00', false, null, 1, 0);

INSERT INTO execution (id, result, exercise, state, comment, workout) VALUES (1,'{"results":[{"side":null,"weights":[80.0,80.0,80.0],"sets":[15,15,15]}]}',1,0,null,1);
INSERT INTO execution (id, result, exercise, state, comment, workout) VALUES (2,'{"results":[{"side":"LEFT","weights":[17.0,18.0,19.25,20.25,21.25],"sets":[12,10,8,8,6]},{"side":"RIGHT","weights":[17.0,18.0,19.25,20.25,21.25],"sets":[12,10,8,8,6]}]}',2,0,null,1);

INSERT INTO equipment (id, type, data, length, weight, strength) VALUES (3, 4, '{"connectedLoad": false,"type":"STRAIGHT"}', 180, 8, 150);
INSERT INTO equipment (id, type, data, length, weight, strength) VALUES (1, 0, '{"handles":6}', 120, NULL, 200);
INSERT INTO equipment (id, type, data, length, weight, strength) VALUES (2, 1, '{"height":43}', 110, NULL, 200);
INSERT INTO equipment (id, type, data, length, weight, strength) VALUES (4, 3, NULL, NULL, 10, NULL);
INSERT INTO equipment (id, type, data, length, weight, strength) VALUES (26, 6, '{"levels":7,"height":{"min":80,"max":137}}', NULL, NULL, NULL);
INSERT INTO equipment (id, type, data, length, weight, strength) VALUES (27, 2, '{"connectedLoad":false}', 45, 2, 50);
INSERT INTO equipment (id, type, data, length, weight, strength) VALUES (29, 5, '{"handles":4}', NULL, NULL, 150);