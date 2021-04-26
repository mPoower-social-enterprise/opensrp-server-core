--clear data
TRUNCATE TABLE team.practitioner CASCADE;

-- insert data
INSERT INTO team.practitioner (id, identifier, active, name, user_id, username) VALUES
(1,'p1-identifier',TRUE,'first practitioner','1','p1'),
(2,'p2-identifier',TRUE,'second practitioner','2','p2');
--clear data
TRUNCATE TABLE team.practitioner_details CASCADE;

-- insert data
INSERT INTO team.practitioner_details (id, practitioner_id, created_date, email, enabled, first_name, gender, identifier, last_name, mobile, modified_date, uuid, creator, parent_user_id, enable_sim_print, ss_no, app_version, is_resync, on_maternity_leave) VALUES(1, 1, NULL, '', true, 'test', 'Male', '123456', 'SK', '01912773007', NULL, '1111111111', NULL, NULL, false, NULL, '1.3', 'yes', false),
(2, 2, NULL, '', false, 'test', 'Male', '123456', 'SS', '01912773007', NULL, '1111111111', NULL, NULL, true, NULL, '1.3', '', false);

--clear data
TRUNCATE TABLE core.location_tag CASCADE;

-- insert data
INSERT INTO core.location_tag (id,name,active,description) VALUES
(1,'Country',TRUE,'0'),
(2,'Division',TRUE,'1'),
(3,'District',TRUE,'2'),
(4,'City Corporation Upazila',TRUE,'3'),
(5,'Pourasabha',TRUE,'4'),
(6,'Union Ward',TRUE,'5'),
(7,'Village',TRUE,'6');

--clear data
TRUNCATE TABLE core.location CASCADE;
INSERT INTO core.location (id, json) VALUES (1, '{"id": "3734","locationTags":[{"active":true,"name":"Country","0":"Country","id":1}], "type": "Feature", "properties": {"uid": "41587456-b7c8-4c4e-b433-23a786f742fc", "code": "3734", "name": "Bangladesh", "type": "Intervention Unit", "status": "Active", "version": 0, "parentId": "", "geographicLevel": 4, "effectiveStartDate": "2015-01-01T0000.000+03:00"},"serverVersion": 1542378347104}');

INSERT INTO core.location_metadata (id, location_id, geojson_id, type, parent_id, uuid, status, server_version,name) VALUES (1, 1, '3734', 'Intervention Unit', '', '41587456-b7c8-4c4e-b433-23a786f742fc', 'ACTIVE', 1542378347104,'Bangladesh');

INSERT INTO core.location (id, json) VALUES (2, '{"id": "3735","locationTags":[{"active":true,"name":"Division","description":"1","id":2}], "type": "Feature", "properties": {"uid": "41587456-b7c8-4c4e-b433-23a786f742fd", "code": "3735", "name": "DHAKA", "type": "Intervention Unit", "status": "Active", "version": 0, "parentId": "3734", "geographicLevel": 4, "effectiveStartDate": "2015-01-01T0000.000+03:00"},"serverVersion": 1542378347104}');

INSERT INTO core.location_metadata (id, location_id, geojson_id, type, parent_id, uuid, status, server_version,name) VALUES (2, 2, '3735', 'Intervention Unit', '3734', '41587456-b7c8-4c4e-b433-23a786f742fd', 'ACTIVE', 1542378347104,'DHAKA');

INSERT INTO core.location (id, json) VALUES (3, '{"id": "3736","locationTags":[{"active":true,"name":"District","description":"2","id":3}], "type": "Feature", "properties": {"uid": "41587456-b7c8-4c4e-b433-23a786f742ff", "code": "3736", "name": "DHAKA:9266", "type": "Intervention Unit", "status": "Active", "version": 0, "parentId": "3735", "geographicLevel": 4, "effectiveStartDate": "2015-01-01T0000.000+03:00"},"serverVersion": 1542378347104}');

INSERT INTO core.location_metadata (id, location_id, geojson_id, type, parent_id, uuid, status, server_version,name) VALUES (3, 3, '3736', 'Intervention Unit', '3735', '41587456-b7c8-4c4e-b433-23a786f742ff', 'ACTIVE', 1542378347104,'DHAKA:9266');


INSERT INTO core.location (id, json) VALUES (4, '{"id": "3737","locationTags":[{"active":true,"name":"City Corporation Upazila","description":"3","id":4}], "type": "Feature", "properties": {"uid": "41587456-b7c8-4c4e-b433-23a786f742fb", "code": "3737", "name": "DHAKA NORTH CITY CORPORATION:9267", "type": "Intervention Unit", "status": "Active", "version": 0, "parentId": "3736", "geographicLevel": 4, "effectiveStartDate": "2015-01-01T0000.000+03:00"},"serverVersion": 1542378347104}');

INSERT INTO core.location_metadata (id, location_id, geojson_id, type, parent_id, uuid, status, server_version,name) VALUES (4, 4, '3737', 'Intervention Unit', '3736', '41587456-b7c8-4c4e-b433-23a786f742fb', 'ACTIVE', 1542378347104,'DHAKA NORTH CITY CORPORATION:9267');

INSERT INTO core.location (id, json) VALUES (5, '{"id": "3738","locationTags":[{"active":true,"name":"Pourasabha","description":"4","id":5}], "type": "Feature", "properties": {"uid": "41587456-b7c8-4c4e-b433-23a786f742fcc", "code": "3738", "name": "NOT POURASABHA:9268", "type": "Intervention Unit", "status": "Active", "version": 0, "parentId": "3737", "geographicLevel": 4, "effectiveStartDate": "2015-01-01T0000.000+03:00"},"serverVersion": 1542378347104}');

INSERT INTO core.location_metadata (id, location_id, geojson_id, type, parent_id, uuid, status, server_version,name) VALUES (5, 5, '3738', 'Intervention Unit', '3737', '41587456-b7c8-4c4e-b433-23a786f742fcc', 'ACTIVE', 1542378347104,'NOT POURASABHA:9268');

INSERT INTO core.location (id, json) VALUES (6, '{"id": "3739","locationTags":[{"active":true,"name":"Union Ward","description":"5","id":6}], "type": "Feature", "properties": {"uid": "41587456-b7c8-4c4e-b433-23a786f742fca", "code": "3739", "name": "WARD NO. 19 (PART):9269", "type": "Intervention Unit", "status": "Active", "version": 0, "parentId": "3738", "geographicLevel": 4, "effectiveStartDate": "2015-01-01T0000.000+03:00"},"serverVersion": 1542378347104}');

INSERT INTO core.location_metadata (id, location_id, geojson_id, type, parent_id, uuid, status, server_version,name) VALUES (6, 6, '3739', 'Intervention Unit', '3738', '41587456-b7c8-4c4e-b433-23a786f742fca', 'ACTIVE', 1542378347104,'NOT POURASABHA:9268');


INSERT INTO core.location (id, json) VALUES (7, '{"id": "3740","locationTags":[{"active":true,"name":"Village","description":"6","id":7}], "type": "Feature", "properties": {"uid": "41587456-b7c8-4c4e-b433-23a786f742fcb", "code": "3740", "name": "T&T COLONY-T&T Mosjid:9270", "type": "Intervention Unit", "status": "Active", "version": 0, "parentId": "3739", "geographicLevel": 4, "effectiveStartDate": "2015-01-01T0000.000+03:00"},"serverVersion": 1542378347104}');

INSERT INTO core.location_metadata (id, location_id, geojson_id, type, parent_id, uuid, status, server_version,name) VALUES (7, 7, '3740', 'Intervention Unit', '3739', '41587456-b7c8-4c4e-b433-23a786f742fcb', 'ACTIVE', 1542378347104,'NOT POURASABHA:9268');


--clear data
TRUNCATE TABLE core.location_tag_map CASCADE;

-- insert data
INSERT INTO core.location_tag_map (location_id, location_tag_id) VALUES(1, 1),(2, 2),(3, 3),(4, 4),(5, 5),(6, 6),(7, 7);

--clear data
TRUNCATE TABLE team.practitioner_group;

-- insert data

INSERT INTO team.practitioner_group (practitioner_id, practitioner_group_id) VALUES(1, 28),(2, 29);

--clear data
TRUNCATE TABLE team.practitioner_catchment_area;

-- insert data
INSERT INTO team.practitioner_catchment_area (id, created_date, location_id, parent_location_id, modified_date, practitioner_id) VALUES(1, NULL, 7, 6, NULL, 1);
INSERT INTO team.practitioner_catchment_area (id, created_date, location_id, parent_location_id, modified_date, practitioner_id) VALUES(2, NULL, 7, 6, NULL, 2);





