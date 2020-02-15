insert into authors (id, name)
values (1,'Roma Shagun'),(2,'Ruslan Senatrev');

insert into news (id, title, creation_date, author_id)
VALUES (1,'Roma has died','2001-01-01',2),
       (2,'Ruslan has died','2002-02-02',1),
       (3,'Ruslan shas died','2002-02-02',1);

insert into comments (id, content, author_id, news_id, creation_date)
values (1,'Cooment here',1,1,now());

insert into tags (id, name)
values (1,'tag1'),(2,'tag2'),(3,'tag3'),(4,'tag4');

insert into news_to_tag (news_id, tag_id)
values (1,1),(1,2),(2,1),(2,2),(2,3),
       (3,1),(3,2),(3,3),(3,4);