--clear data
TRUNCATE TABLE core.target_details CASCADE;

-- insert data
INSERT INTO core.target_details (id,branch_id, created_date, end_date, percentage, product_id, quantity, start_date, status, "timestamp", unit, modified_date, user_id, uuid, creator, "month", "year", updated_by, "day", target_type, role_id, is_personal) 
VALUES(1, 2,'2021-01-13 16:09:18', '2021-01-13', '0', 1, 5, '2021-01-13', 'ACTIVE', 1610532558497, 'quantity', null, 1, '2cb8eb94-cd5e-4cc7-b2a9-e5eadcca767d', 1, 1, 2021, 0, 13, 'ROLE', 28, 'No');


