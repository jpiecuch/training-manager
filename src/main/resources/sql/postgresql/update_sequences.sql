SELECT SETVAL('account_id_seq', (SELECT MAX(id) FROM account));
SELECT SETVAL('phase_id_seq', (SELECT MAX(id) FROM phase));
SELECT SETVAL('plan_id_seq', (SELECT MAX(id) FROM plan));
SELECT SETVAL('workout_id_seq', (SELECT MAX(id) FROM workout));
SELECT SETVAL('exercise_id_seq', (SELECT MAX(id) FROM exercise));
SELECT SETVAL('description_id_seq', (SELECT MAX(id) FROM description));
SELECT SETVAL('equipment_id_seq', (SELECT MAX(id) FROM equipment));
