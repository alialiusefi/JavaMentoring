insert into tag(tag_name)
 values ('Accesories'),
        ('Food'),
        ('Hotel'),
        ('Travel');

insert into giftcertificates(name, description, price, date_created, date_modified, duration_till_expiry)
 VALUES ('ACME Discount Voucher','Discount while shopping',20.00,'2012-09-09','2014-12-01',5);

insert into tagged_giftcertificates(tag_id, gift_certificate_id)
VALUES (1, 1),
       (2, 1);