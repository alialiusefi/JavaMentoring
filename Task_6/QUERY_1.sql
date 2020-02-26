create or replace view "query1"
    as
    (select a.id as authors_id, count(news.id) as result
     from news
              inner join authors a on news.author_id = a.id group by a.id)
    union all
        (select a2.id as authors_id, avg(comments.id) as result from comments
            inner join news n on comments.news_id = n.id
            inner join authors a2 on comments.author_id = a2.ID group by a2.id)
    union all
        (select a3.ID as authors_id, tags.id/*,count(tags.name)*/ as result from tags
            inner join news_to_tag ntt on tags.id = ntt.tag_id
            inner join news n2 on ntt.news_id = n2.id
            inner join authors a3 on n2.author_id = a3.ID
            group by a3.ID, tags.id order by count(tags.id) desc fetch first 1 row only);

select * from "query1";