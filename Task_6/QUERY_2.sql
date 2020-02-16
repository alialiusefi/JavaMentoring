select authorName
from (select public.authors.name as authorName, avg(length(n.content)) as avg
      from authors
               inner join news n on public.authors.id = n.author_id
      where length(n.content) > 3000
      group by public.authors.name) as b
where avg > 500;
