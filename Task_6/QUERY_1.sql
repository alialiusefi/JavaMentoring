create view "query1"
    as
    (select count(news.id) as newsCount
     from news
              inner join authors a on news.author_id = a.id)
    union all
        (select avg(comments.id) as avgCountComments from comments
            inner join news n on comments.news_id = n.id
            inner join authors a2 on comments.author_id = 1)
    union all
        (select tags.id/*,count(tags.name)*/ as popularTagID from tags
            inner join news_to_tag ntt on tags.id = ntt.tag_id
            inner join news n2 on ntt.news_id = n2.id
            inner join authors a3 on n2.author_id = 1
            group by tags.id order by count(tags.id) desc fetch first 1 row only);

select * from "query1";