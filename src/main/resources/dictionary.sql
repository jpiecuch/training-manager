INSERT INTO users (id, name, password, salt, first_name, last_name, created, updated, version) VALUES (1, 'j.piecuch', 'dcc4cec778c00b632fba26da142d95d0b46a05e0a5f944a0484346c0656def67', 'jp88', 'Jakub', 'Piecuch', '2013-12-22 09:48:46.11', '2013-12-22 09:49:24.542', 1);

INSERT INTO units (id, name, description, short_name) VALUES (2, 'milimetr', '', 'mm');
INSERT INTO units (id, name, description, short_name) VALUES (1, 'kilogram', '', 'kg');
INSERT INTO units (id, name, description, short_name) VALUES (3, 'metr', '', 'm');
INSERT INTO units (id, name, description, short_name) VALUES (4, 'centrymetr', '', 'cm');

INSERT INTO bars (id, handles_no, length_of, length_of_unit, strength, strength_unit) VALUES (1, 6, 115, 4, 200, 1);

INSERT INTO benches (id, length_of, length_of_unit, height, height_unit, type) VALUES (1, 110, 4, 43, 4, 0);

INSERT INTO dumbbells (id, length_of, length_of_unit, weight, weight_unit, diameter, diameter_unit, connected_load) VALUES (1, 45, 4, 2, 1, 25, 2, false);
INSERT INTO dumbbells (id, length_of, length_of_unit, weight, weight_unit, diameter, diameter_unit, connected_load) VALUES (2, 45, 4, 2, 1, 25, 2, false);

INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (3, 'Wiosłowanie sztangą', 'http://www.youtube.com/v/zq5eBuxKsMY?version=3&amp;hl=pl_PL&amp;rel=0', NULL, 1);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (4, 'Martwy ciąg', 'http://www.youtube.com/v/HjHIW_nTzTA?version=3&amp;hl=pl_PL&amp;rel=0', NULL, 1);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (6, 'Skłony ze sztangą na barkach', 'http://www.youtube.com/v/PE5gauPLpIc?version=3&amp;hl=pl_PL&amp;rel=0', NULL, 1);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (26, 'Podciąganie na drążku podchwytem', 'http://www.youtube.com/v/M4Q9M4AMjvQ?version=3&amp;hl=pl_PL', NULL, 1);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (29, 'Wiosłowanie końcem sztangi', 'http://www.youtube.com/v/_7uJakDkVCM?version=3&amp;hl=pl_PL', NULL, 1);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (30, 'Podciąganie na drążku za głowę', 'http://www.youtube.com/v/jlBhe0kffYo?version=3&amp;hl=pl_PL', NULL, 1);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (31, 'Podciąganie na niskim drążku', 'http://www.youtube.com/v/kJXCIDcvnNg?version=3&amp;hl=pl_PL', NULL, 1);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (32, 'Yates rows', 'http://www.youtube.com/v/aETWa2h1Pko?version=3&amp;hl=pl_PL', NULL, 1);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (19, 'Uginanie przedramion ze sztangą podchwytem', 'http://www.youtube.com/v/zvoKbd3hVnQ?version=3&amp;hl=pl_PL&amp;rel=0', NULL, 2);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (2, 'Wyciskanie sztangi na ławce poziomej leżąc', 'http://www.youtube.com/v/lvGciMRi_zU?version=3&amp;hl=pl_PL&amp;rel=0', NULL, 3);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (12, 'Rozpiętki na ławce poziomej', 'http://www.youtube.com/v/aHG78PBG8d8?version=3&amp;hl=pl_PL&amp;rel=0', NULL, 3);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (13, 'Wyciskanie sztangi do szyi', 'http://www.youtube.com/v/-FeROpZDpo4?version=3&amp;hl=pl_PL&amp;rel=0', NULL, 3);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (21, 'Podciąganie sztangi do brody', 'http://www.youtube.com/v/oAHol4jeS_0?version=3&amp;hl=pl_PL&amp;rel=0', NULL, 6);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (14, 'Trzymanie statyczne ze sztangą', 'http://www.youtube.com/v/DhF7hoyrY2I?version=3&amp;hl=pl_PL&amp;rel=0', NULL, 4);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (16, 'Przysiad ze sztangą na barkach', 'http://www.youtube.com/v/aPIjuTVlm6w?hl=pl_PL&amp;version=3&amp;rel=0', NULL, 5);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (17, 'Syzyfki', 'http://www.youtube.com/v/YMQi0ZqnAYU?version=3&amp;hl=pl_PL&amp;rel=0', NULL, 5);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (18, 'Martwy ciąg na prostych nogach', 'http://www.youtube.com/v/lIb1MeuDxuU?version=3&amp;hl=pl_PL&amp;rel=0', NULL, 5);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (54, 'Zgięcia grzbietowe stopy z hantlami', 'http://www.youtube.com/v/Aj3Y58DQVI0?hl=pl_PL&amp;version=3', NULL, 5);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (55, 'Wykroki ze sztangielkami', 'http://www.youtube.com/v/NnJqBiQ1bCc?hl=pl_PL&amp;version=3', NULL, 5);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (56, 'Wykroki ze sztangą', 'http://www.youtube.com/v/pEN-dCjh5cQ?hl=pl_PL&amp;version=3', NULL, 5);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (22, 'Wyciskanie żołnierskie', 'http://www.youtube.com/v/ReP4ha2o22Y?version=3&amp;hl=pl_PL&amp;rel=0', NULL, 6);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (37, 'Wyciskanie sztangielek', 'http://www.youtube.com/v/1qhc84I3QBo?hl=pl_PL&amp;version=3', NULL, 6);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (39, 'Wiosłowanie sztangą / sztangielkami', 'http://www.youtube.com/v/oRrLhUkX7J0?version=3&amp;hl=pl_PL', NULL, 6);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (41, 'Wyciskanie sztangi zza głowy', 'http://www.youtube.com/v/4zL7YlXxZ8c?hl=pl_PL&amp;version=3', NULL, 6);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (40, 'Wyciskanie sztangi sprzed głowy', 'http://www.youtube.com/v/cQg2f3xfJq0?version=3&amp;hl=pl_PL', NULL, 6);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (42, 'Unoszenie sztangielek w opadzie', 'http://www.youtube.com/v/dIbE_iYjMnc?hl=pl_PL&amp;version=3', NULL, 6);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (43, 'Unoszenie sztangielek bokiem', 'http://www.youtube.com/v/vU50wUxydDo?version=3&amp;hl=pl_PL', NULL, 6);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (44, 'Unoszenie sztangi w przód', 'http://www.youtube.com/v/QtoT8qOHZO0?version=3&amp;hl=pl_PL', NULL, 6);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (45, 'Rotacja zewnętrzna ramion', '//www.youtube.com/v/PmelDXmSzKk?hl=pl_PL&amp;version=3', NULL, 6);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (46, 'Rotacja wewnętrzna ramion', 'http://www.youtube.com/v/x3tD41vvFh8?hl=pl_PL&amp;version=3', NULL, 6);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (47, 'Przenoszenie sztangi nad głową siedząc lub stojąc', 'http://www.youtube.com/v/y0LrKST-V_4?hl=pl_PL&amp;version=3', NULL, 6);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (23, 'Wzniosy barków ze sztangielkami - szrugsy', 'http://www.youtube.com/v/7kKQpAdOsXM?version=3&amp;hl=pl_PL', NULL, 6);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (8, 'Wycisaknie francuskie siedząc', 'http://www.youtube.com/v/pV5vQNtFbmw?version=3&amp;hl=pl_PL&amp;rel=0', NULL, 7);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (10, 'Wyciskanie francuskie hantla jednorącz', 'http://www.youtube.com/v/wWz4w5_1y9Q?version=3&amp;hl=pl_PL&amp;rel=0', NULL, 7);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (11, 'Wyciskanie sztangi na ławce w wąskim uchwycie', 'http://www.youtube.com/v/uPYgAn579ng?hl=pl_PL&amp;version=3&amp;rel=0', NULL, 7);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (33, 'Wyciskanie francuskie sztangą leżąc na ziemi', 'http://www.youtube.com/v/0qbO_GSNACA?hl=pl_PL&amp;version=3', NULL, 7);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (34, 'Prostowanie przedramion ze sztangielkami w opadzie tułowia / jednorącz w oparciu o ławkę', 'http://www.youtube.com/v/3YQPLkTdPFw?version=3&amp;hl=pl_PL', NULL, 7);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (35, 'Prostowanie przedramienia ze sztangielką w oparciu o ławkę', 'http://www.youtube.com/v/RIyDcns7V2M?version=3&amp;hl=pl_PL', NULL, 7);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (36, 'Wyciskanie francuskie sztangi leżąc / sztangielki oburącz', 'http://www.youtube.com/v/GTYZFPoCoIo?version=3&amp;hl=pl_PL', NULL, 7);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (24, 'Skręty ze sztangą na barkach', 'http://www.youtube.com/v/zwCzZMCCGSE?version=3&amp;hl=pl_PL&amp;rel=0', NULL, 0);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (15, 'Spięcia brzucha leżąc', 'http://www.youtube.com/v/wAaCxUXHu8M?version=3&amp;hl=pl_PL&amp;rel=0', NULL, 0);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (27, 'Unoszenie nóg w zwisie na drążku', 'http://www.youtube.com/v/q21oUW1M96s?version=3&amp;hl=pl_PL', NULL, 0);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (48, 'Unoszenie nóg w leżeniu na ławce skośnej lub płaskiej', 'http://www.youtube.com/v/uDLU8MfMFDc?hl=pl_PL&amp;version=3', NULL, 0);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (49, 'Skłony tułowia z przyciąganiem kolan na ławce', 'http://www.youtube.com/v/HE3V1m0dw5M?version=3&amp;hl=pl_PL', NULL, 0);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (50, 'Skłony boczne ze sztangielką', 'http://www.youtube.com/v/gRjfwA_W1y0?hl=pl_PL&amp;version=3', NULL, 0);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (51, 'Scyzoryki', 'http://www.youtube.com/v/rwnb6DGyxQA?version=3&amp;hl=pl_PL', NULL, 0);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (52, 'Nożyce poziome', 'http://www.youtube.com/v/OF4UAXxEmp0?hl=pl_PL&amp;version=3', NULL, 0);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (65, 'Uginanie przedramion ze sztangielkami stojąc z supinacją / siedząc', 'http://www.youtube.com/v/9lxJ7NyiYYA?version=3&amp;hl=pl_PL', NULL, 2);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (66, 'Uginanie przedramion ze sztangielkami stojąc uchwyt młotkowy', 'http://www.youtube.com/v/jYvLvxIWEvM?version=3&amp;hl=pl_PL', NULL, 2);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (67, 'Uginanie przedramion ze sztangą siedząc', 'http://www.youtube.com/v/7o4655VxR0A?hl=pl_PL&amp;version=3', NULL, 2);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (68, 'Uginanie przedramion ze sztangą nachwytem / ze sztangielkami', 'http://www.youtube.com/v/vAYSB8I0LRg?hl=pl_PL&amp;version=3', NULL, 2);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (69, 'Uginanie przedramienia ze sztangielką w oparciu o kolano', 'http:////www.youtube.com/v/bLcvij6UXP4?hl=pl_PL&amp;version=3', NULL, 2);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (70, 'Siódemkowanie', 'http://www.youtube.com/v/fPR5u0824Ck?hl=pl_PL&amp;version=3', NULL, 2);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (1, 'Wyciskanie sztangielek na ławce poziomej leżąc', 'http://www.youtube.com/v/02z5uTM5bRY?hl=pl_PL&amp;version=3&amp;rel=0', 'dupa', 3);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (72, 'Wyciskanie hantli złączonych młotkowo', 'http://www.youtube.com/v/G9L8l4N01-k?version=3&amp;hl=pl_PL', NULL, 3);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (73, 'Siódemkowanie (klatka piersiowa)', 'http://www.youtube.com/v/Ya8F8kcxA2g?version=3&amp;hl=pl_PL', NULL, 3);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (74, 'Przenoszenie sztangielki w leżeniu w poprzek ławki poziomej', 'http://www.youtube.com/v/JpVnRFa1gJU?hl=pl_PL&amp;version=3', NULL, 3);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (53, 'Nożyce pionowe', 'http://www.youtube.com/v/xGOzhRghSSU?hl=pl_PL&amp;version=3', NULL, 0);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (75, 'Spięcia brzucha leżąc z jednoczesnym podciąganiem nogi', 'http://www.youtube.com/v/b0eEPNFT5Ww?version=3&amp;hl=pl_PL', NULL, 0);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (28, 'Wiosłowanie Pendlay''a', 'http://www.youtube.com/v/aXZGAnzIhtE?hl=pl_PL&amp;version=3', NULL, 1);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (90, 'Podciąganie na drążku do klatki', 'http://www.youtube.com/v/O6UqLTkFJFk?hl=pl_PL&amp;version=3', NULL, 1);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (91, 'Wiosłowanie sztangielką', 'http://www.youtube.com/v/pYcpY20QaE8?hl=pl_PL&amp;version=3', NULL, 1);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (89, 'Pompki', '', NULL, 3);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (61, 'Przysiad ze sztangielkami trzymanymi z przodu na barkach', 'http://www.youtube.com/v/rMvtX7jLw4c?hl=pl_PL&amp;version=3', NULL, 5);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (62, 'Przysiad ze sztangą z przodu', 'http://www.youtube.com/v/OnOGn8zC1Ps?hl=pl_PL&amp;version=3', NULL, 5);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (63, 'Odwrotne wspięcia stojąc', 'http://www.youtube.com/v/uCE-E2jw5Gg?version=3&amp;hl=pl_PL', NULL, 5);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (64, 'Hack przysiad ze sztangą', 'http://www.youtube.com/v/U5-BK6TpDRI?hl=pl_PL&amp;version=3', NULL, 5);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (20, 'Wspięcia na palce stojąc', 'http://www.youtube.com/v/Wri0VppFWCY?hl=pl_PL&amp;version=3', NULL, 5);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (88, 'Wysoki step ze sztangą/sztangielkami', 'http://www.youtube.com/watch?v=Jyb_oHDHJZo', NULL, 5);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (84, 'Arnoldki', 'http://www.youtube.com/v/Q1tpgtNxgeQ?hl=pl_PL&amp;version=3', NULL, 6);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (85, 'Odwodzenie ramienia w bok w opadzie tułowia jednorącz', 'http://www.youtube.com/v/_JnOcm7yRTk?hl=pl_PL&amp;version=3', NULL, 6);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (86, 'Odwodzenie ramion w tył ze sztangą w opadzie tułowia "Adam Małysz"', 'http://www.youtube.com/v/lZ1PWmZaZDA?hl=pl_PL&amp;version=3', NULL, 6);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (87, 'Wyciskanie sztangi sprzed klatki piersiowej podchwytem siedząc', 'http://www.youtube.com/v/3pKesUXuP8M?version=3&amp;hl=pl_PL', NULL, 6);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (82, 'Wzniosy barków ze sztangą za plecami - szrugsy', 'http://www.youtube.com/v/f1tY23EYg5o?version=3&amp;hl=pl_PL', NULL, 6);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (83, 'Wzniosy barków ze sztangą - szrugsy', 'http://www.youtube.com/v/8SMJ56w_sPM?hl=pl_PL&amp;version=3', NULL, 6);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (92, 'Wyciskanie sztangielki zza głowy', 'http://www.youtube.com/v/-Vyt2QdsR7E?version=3&amp;hl=pl_PL', NULL, 7);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (76, 'Unoszenie bioder w leżeniu tyłem', 'http://www.youtube.com/v/2x0QHiK209Q?hl=pl_PL&amp;version=3', NULL, 0);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (77, 'Unoszenie bioder ze skrętem w leżeniu tyłem "Korkociąg"', 'http://www.youtube.com/v/uLhdORSKvDg?version=3&amp;hl=pl_PL', NULL, 0);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (78, 'Skłony boczne tułowia ze sztangą', 'http://www.youtube.com/v/YzmEX_Mvfd0?hl=pl_PL&amp;version=3', NULL, 0);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (79, 'Skręty bioder na boki, leżąc - "Świeca"', 'http://www.youtube.com/v/loK4GJAcudc?hl=pl_PL&amp;version=3', NULL, 0);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (80, 'Skręty biodrami na drążku w zwisie', 'http://www.youtube.com/v/_uUBY0gcUR0?version=3&amp;hl=pl_PL', NULL, 0);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (81, 'Skręty tułowia z obciążeniem w siadzie równoważnym', 'http://www.youtube.com/v/Atl6nfJGRhg?version=3&amp;hl=pl_PL', NULL, 0);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (93, 'Brzuszki', '', NULL, 0);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (57, 'Wykroki z hantlami lub sztangą w tył', 'http://www.youtube.com/v/vhGsQ2xRdTc?hl=pl_PL&amp;version=3', NULL, 5);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (58, 'Wspięcia na palce ze sztangielkami', 'http://www.youtube.com/v/LceExQXg4MM?hl=pl_PL&amp;version=3', NULL, 5);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (59, 'Wspięcia na palce siedząc ze sztangą', 'http://www.youtube.com/v/YuY_vJNkGKs?hl=pl_PL&amp;version=3', NULL, 5);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (60, 'Uginanie nóg leżąc z hantlem między stopami', 'http://www.youtube.com/v/xhTprFWnJQw?version=3&amp;hl=pl_PL', NULL, 5);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (38, 'Unoszenie sztangielki w leżeniu', 'http://www.youtube.com/v/WvQX9sWtF0o?hl=pl_PL&amp;version=3', NULL, 6);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (94, 'Prostowanie nóg na prasie siedząc', NULL, NULL, 5);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (95, 'Uginanie nóg na prasie leżąc', NULL, NULL, 5);
INSERT INTO exercises (id, name, movie_url, description, party_muscles) VALUES (96, 'Wiosłowanie na prasie', NULL, NULL, 1);

INSERT INTO loads (id, weight, hole_diameter, weight_unit, hole_diameter_unit) VALUES (9, 1.25, 30, 1, 2);
INSERT INTO loads (id, weight, hole_diameter, weight_unit, hole_diameter_unit) VALUES (3, 5, 30, 1, 2);
INSERT INTO loads (id, weight, hole_diameter, weight_unit, hole_diameter_unit) VALUES (4, 5, 30, 1, 2);
INSERT INTO loads (id, weight, hole_diameter, weight_unit, hole_diameter_unit) VALUES (1, 10, 30, 1, 2);
INSERT INTO loads (id, weight, hole_diameter, weight_unit, hole_diameter_unit) VALUES (2, 10, 30, 1, 2);
INSERT INTO loads (id, weight, hole_diameter, weight_unit, hole_diameter_unit) VALUES (7, 2.5, 30, 1, 2);
INSERT INTO loads (id, weight, hole_diameter, weight_unit, hole_diameter_unit) VALUES (8, 1.25, 30, 1, 2);
INSERT INTO loads (id, weight, hole_diameter, weight_unit, hole_diameter_unit) VALUES (10, 1.25, 30, 1, 2);
INSERT INTO loads (id, weight, hole_diameter, weight_unit, hole_diameter_unit) VALUES (11, 1.25, 30, 1, 2);
INSERT INTO loads (id, weight, hole_diameter, weight_unit, hole_diameter_unit) VALUES (15, 2.5, 30, 1, 2);
INSERT INTO loads (id, weight, hole_diameter, weight_unit, hole_diameter_unit) VALUES (5, 2.5, 30, 1, 2);
INSERT INTO loads (id, weight, hole_diameter, weight_unit, hole_diameter_unit) VALUES (6, 2.5, 30, 1, 2);
INSERT INTO loads (id, weight, hole_diameter, weight_unit, hole_diameter_unit) VALUES (12, 15, 29, 1, 2);
INSERT INTO loads (id, weight, hole_diameter, weight_unit, hole_diameter_unit) VALUES (13, 15, 29, 1, 2);

INSERT INTO necks (id, weight, weight_unit, diameter, diameter_unit, length_of, length_of_unit, connected_load, type) VALUES (2, 6, 1, 28, 2, 120, 4, false, 1);
INSERT INTO necks (id, weight, weight_unit, diameter, diameter_unit, length_of, length_of_unit, connected_load, type) VALUES (1, 8, 1, 25, 2, 180, 4, false, 0);

INSERT INTO press (id, handles_no, strength, strength_unit) VALUES (1, 4, 150, 1);

INSERT INTO stands (id, height_min, height_min_unit, height_max, height_max_unit, levels) VALUES (1, 80, 4, 137, 4, 7);
INSERT INTO stands (id, height_min, height_min_unit, height_max, height_max_unit, levels) VALUES (2, 80, 4, 137, 4, 7);

INSERT INTO calendars (id, name, "user") VALUES (1, 'Kalendarz - "TRUDNE POCZĄTKI"', 1);