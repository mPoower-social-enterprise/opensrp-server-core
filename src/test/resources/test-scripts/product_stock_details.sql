--clear data
TRUNCATE TABLE core.product CASCADE;

-- insert data
INSERT INTO core.product (id, created_date, description, "name", purchase_price, sell_to, selling_price, status, "timestamp", modified_date, uuid, creator, updated_by, "type") 
VALUES( 1,null, 'Women package', 'Women package', 150, 0, 200, 'ACTIVE', 0, null, 'uuid', 1, 1,'PRODUCT');

--clear data
TRUNCATE TABLE core."_stock" CASCADE;

INSERT INTO core."_stock" (id, created_date, modified_date, uuid, creator, updated_by, stock_id, challan, nogod_roshid_no)
VALUES(1, null, null, '21f4cee6-cd10-418a-8ef6-47c8728b3f12', 0, 0, '123456', '', '');

--clear data
TRUNCATE TABLE core."_stock_details" CASCADE;

INSERT INTO core."_stock_details" (id, branch_id, created_date, credit, debit, expirey_date, product_id, receive_date, reference_type, sell_or_pass_to, status, "timestamp", modified_date, uuid, creator, stock_id, invoice_number, months, years, "month", "year", start_date, stock_in_id, challan, nogod_roshid_no) 
VALUES(1, 2, '2020-11-01', 0, 5, '2020-11-01', 1, '2020-11-01', 'PASS', 1, 'ACTIVE', 1606054400519, '2020-11-01', '', 0, 1, 'invoice_number', 0, 0, 11, 2020, '2020-11-01', '6793984661', '', '');

