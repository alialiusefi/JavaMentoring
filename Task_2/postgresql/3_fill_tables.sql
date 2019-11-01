insert into tag(tag_name)
values ('Accessories'),
       ('Food'),
       ('Hotel'),
       ('Travel'),
       ('Free'),
       ('Sale'),
       ('English'),
       ('Procrastination');

insert into giftcertificates(name, description, price, date_created, date_modified, duration_till_expiry)
VALUES ('ACME Discount Voucher', 'Discount while shopping', 20.00, '2012-09-09', '2014-12-01', 5),
       ('Discount Voucher', 'Discount while shopping', 20.00, '2010-09-09', '2012-12-01', 1),
       ('Free Food Certificate', 'BBQ and Sauce', 420.69, '2011-01-09', '2011-12-01', 1);

insert into tagged_giftcertificates(tag_id, gift_certificate_id)
VALUES (1, 1),
       (2, 1),
       (4, 2),
       (3, 2),
       (8, 3),
       (7, 3),
       (6, 3);

insert into users(username, password, enabled) VALUES
('admin','admin',true);

insert into authorities(user_id, userstatus)
VALUES (1, 1);

insert into orders(ordercost, timestamp)
values (20.00, '2016-06-22 19:10:25-07');

insert into order_giftcertificate(order_id, giftcertificate_id) VALUES 
(1,1);

insert into order_user(order_id, user_id)
VALUES (1, 1);