INSERT INTO roles (id, name) VALUES (1, 'ADMIN');
INSERT INTO roles (id, name) VALUES (2, 'USER');


-- PASSWORD IS "111111"
INSERT INTO users (id, email, first_name, last_name, password, phone, username, uuid) VALUES
                                                                                          (1, 'test@abv.bg', 'Тест', 'Тестов', 'df012e5f3a50924a4270343b6874ee5bbb54eae1435fe4e130e278885acb350159677c22aea1449edaf0268e983b68bb', '0888448004', 'Testtt', '91071588-ae2f-4396-8a46-a7cc3e3d86e0'),
                                                                                          (2, 'john.doe@example.com', 'John', 'Doe', 'df012e5f3a50924a4270343b6874ee5bbb54eae1435fe4e130e278885acb350159677c22aea1449edaf0268e983b68bb', '0888448005', 'johnnn', 'd1a3c7e5-12e2-4396-8a46-a7cc3e3d86e1'),
                                                                                          (3, 'jane.smith@example.com', 'Jane', 'Smith', 'df012e5f3a50924a4270343b6874ee5bbb54eae1435fe4e130e278885acb350159677c22aea1449edaf0268e983b68bb', '0888448006', 'janeeee', 'd1a3c7e5-12e2-4396-8a46-a7cc3e3d86e2'),
                                                                                          (4, 'alice.wonderland@example.com', 'Alice', 'Wonderland', 'df012e5f3a50924a4270343b6874ee5bbb54eae1435fe4e130e278885acb350159677c22aea1449edaf0268e983b68bb', '0888448007', 'aliceeeee', 'd1a3c7e5-12e2-4396-8a46-a7cc3e3d86e3'),
                                                                                          (5, 'bob.builder@example.com', 'Bob', 'Builder', 'df012e5f3a50924a4270343b6874ee5bbb54eae1435fe4e130e278885acb350159677c22aea1449edaf0268e983b68bb', '0888448008', 'bobbbbb', 'd1a3c7e5-12e2-4396-8a46-a7cc3e3d86e4');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (4, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (5, 2);

INSERT INTO parking_spots (id, available, location) VALUES (1, false, 'A1');
INSERT INTO parking_spots (id, available, location) VALUES (2, true, 'A2');
INSERT INTO parking_spots (id, available, location) VALUES (3, true, 'A3');
INSERT INTO parking_spots (id, available, location) VALUES (4, true, 'A4');
INSERT INTO parking_spots (id, available, location) VALUES (5, false, 'A5');
INSERT INTO parking_spots (id, available, location) VALUES (6, true, 'A6');
INSERT INTO parking_spots (id, available, location) VALUES (7, false, 'B1');
INSERT INTO parking_spots (id, available, location) VALUES (8, true, 'B2');
INSERT INTO parking_spots (id, available, location) VALUES (9, true, 'B3');
INSERT INTO parking_spots (id, available, location) VALUES (10, true, 'B4');
INSERT INTO parking_spots (id, available, location) VALUES (11, false, 'B5');
INSERT INTO parking_spots (id, available, location) VALUES (12, true, 'B6');
INSERT INTO parking_spots (id, available, location) VALUES (13, true, 'C1');
INSERT INTO parking_spots (id, available, location) VALUES (14, false, 'C2');
INSERT INTO parking_spots (id, available, location) VALUES (15, true, 'C3');
INSERT INTO parking_spots (id, available, location) VALUES (16, true, 'C4');
INSERT INTO parking_spots (id, available, location) VALUES (17, true, 'C5');
INSERT INTO parking_spots (id, available, location) VALUES (18, false, 'C6');

INSERT INTO bank_cards(id, card_number, user_id) VALUES (1,'BG21312342343FR',1)