
/*Custom function*/
select news.id, news.CONTENT,listTagsByNewsID(news.id,',') from news;

/*Oracle 10g db feature*/
select news.id, news.CONTENT,listagg(T.NAME, ',')
from news inner join NEWS_TO_TAG NTT on NEWS.ID = NTT.NEWS_ID
inner join TAGS T on NTT.TAG_ID = T.ID group by news.id, news.CONTENT;

insert into news (id, content, creation_date, author_id)
VALUES (99,
        'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec qu',
        '01-JAN-2001', 1);

insert into tags (id, name)
values (99, 'tag5');

insert into news_to_tag (news_id, tag_id)
values (99, 99);
/*todo: all news^, incorrect tags*/