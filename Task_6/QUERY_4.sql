
/*Custom function*/
select news.id, news.CONTENT,LISTTAGSBYNEWSID(news.id,',') from news;

/*Oracle 10g db feature*/
