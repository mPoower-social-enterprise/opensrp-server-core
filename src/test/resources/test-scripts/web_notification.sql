--clear data
TRUNCATE TABLE core.web_notification CASCADE;

-- insert data
INSERT INTO core.web_notification (id, branch_id, created_date, creator, district_id, division_id, notification, title, role_id, send_date, send_time_hour, send_time_minute, status, "timestamp", "type", upazila_id, modified_date, updated_by, uuid, send_date_and_time, notification_type, stock_details_id, meeting_training_date_and_time)
VALUES(1, 2, '2020-11-22', 1, 0, 0, 'test notification', 'test', 0, '2020-11-23', 10, 18, 'ACTIVE', 1606105080000, 'SEND', 0, '2020-11-23 10:18:36', 0, '46943e13-265b-4526-b33c-1725855a4c95', '2020-11-23 10:18', 'general', 0, '2021-02-09 02:30');


--clear data
TRUNCATE TABLE core.web_notification_user CASCADE;

-- insert data
INSERT INTO core.web_notification_user (id, user_id, web_notification_id) VALUES(1, 1, 1);


