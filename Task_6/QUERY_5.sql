with query1 as (select id as id1,name as author_1,row_number() over ( ORDER BY DBMS_RANDOM.RANDOM()) as rownumber from AUTHORS where AUTHORS.ID <= (select count(*) from AUTHORS) / 2),
     query2 as (select id as id2,name as author_2,row_number() over ( ORDER BY DBMS_RANDOM.RANDOM()) as rownumber from AUTHORS where AUTHORS.ID > (select count(*) from AUTHORS) / 2)
/*order by DBMS_RANDOM.RANDOM()*/
select author_1,author_2 from query1 join query2 on (query1.rownumber = query2.rownumber) order by DBMS_RANDOM.RANDOM();
