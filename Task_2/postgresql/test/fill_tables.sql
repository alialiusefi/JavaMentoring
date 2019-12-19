select giftcertificates.id
     , giftcertificates.name
     , giftcertificates.description
     , giftcertificates.price
     , giftcertificates.date_created
     , giftcertificates.date_modified
     , giftcertificates.duration_till_expiry
     , giftcertificates.isforsale
from giftcertificates
         inner join tagged_giftcertificates on giftcertificates.id = gift_certificate_id
         inner join tag on tagged_giftcertificates.tag_id = tag.id
where (giftcertificates.isforsale = true and (public.consists(?, tag.tag_name)));