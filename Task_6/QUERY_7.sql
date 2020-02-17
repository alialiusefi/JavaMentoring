/*run this on oracle db manager credentials: system:admin*/


SELECT file_name, autoextensible, round(bytes/1024/1024,2) as usedbytes_in_mb,
       round(maxbytes/1024/1024,2) as total_space_in_mb,
       round((maxbytes - bytes)/1024/1024,2) as available_space_in_mb,
       round((round((maxbytes - bytes)/1024/1024,2) / round(maxbytes/1024/1024,2)) * 100,2) as used_percent
FROM DBA_DATA_FILES;

