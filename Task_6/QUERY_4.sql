
/*Custom function*/
select news.id, news.CONTENT,LISTTAGSBYNEWSID(news.id,',') from news;

/*Oracle 10g db feature*/
select news.id, news.CONTENT,listagg(T.NAME, ',')
from news inner join NEWS_TO_TAG NTT on NEWS.ID = 2
inner join TAGS T on NTT.TAG_ID = T.ID group by news.id, news.CONTENT;