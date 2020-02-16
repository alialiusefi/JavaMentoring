select authorName
from (select authors.name as authorName, avg(length(n.content)) as avg
      from authors
               inner join news n on authors.id = n.author_id
      where length(n.content) > 3000
      group by authors.name) /*as b*/
where avg > 500;
