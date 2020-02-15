create view "query1"
    as
    (select count(public.news.id)
     from news
              inner join authors a on news.author_id = a.id)
    union
        (select avg(public.comments.id) from comments
            inner join news n on comments.news_id = n.id
            inner join authors a2 on comments.author_id = 1)
    union
        (select public.tags.id/*,count(tags.name)*/ from tags
            inner join news_to_tag ntt on tags.id = ntt.tag_id
            inner join news n2 on ntt.news_id = n2.id
            inner join authors a3 on n2.author_id = 1
            group by tags.id order by count(tags.id) desc limit 1);

select * from "query1";