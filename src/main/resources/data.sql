INSERT INTO parking.roles (id, name) VALUES (1, 'ADMIN');
INSERT INTO parking.roles (id, name) VALUES (2, 'USER');


-- PASSWORD IS "111111"
INSERT INTO parking.users (id, email, first_name, last_name, password, phone, username, uuid) VALUES (1, 'test@abv.bg', 'Ваня', 'Михова', 'df012e5f3a50924a4270343b6874ee5bbb54eae1435fe4e130e278885acb350159677c22aea1449edaf0268e983b68bb', '0888448004', 'Vanya', '91071588-ae2f-4396-8a46-a7cc3e3d86e0');

INSERT INTO parking.user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO parking.user_roles (user_id, role_id) VALUES (1, 2);

INSERT INTO parking.parking_spots (id, available, location) VALUES (1, false, 'A1');
INSERT INTO parking.parking_spots (id, available, location) VALUES (2, true, 'A2');
INSERT INTO parking.parking_spots (id, available, location) VALUES (3, false, 'B1');
INSERT INTO parking.parking_spots (id, available, location) VALUES (4, true, 'B2');

-- INSERT INTO parking.vehicles (id, license_plate, user_id) VALUES (1, 'CT7051BX', 1);
-- INSERT INTO parking.vehicles (id, license_plate, user_id) VALUES (2, 'CT0080BA', 1);

