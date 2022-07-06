-- Câu 9: cho s? 8988.80 vui lòng xu?t ra ??nh d?ng $8,988.800
SELECT TO_CHAR( 8988.80, '$9,999.999' ) FROM DUAL;


-- Câu 10: cho s? 8988.80, 820988.80 vui lòng xu?t ra ??nh d?ng $8,000.000, $820,000.000
SELECT TO_CHAR( TRUNC(8988.80, -3), '$9,999.999' ), TO_CHAR( TRUNC(820988.80 , -3), '$999,999.999' )
FROM DUAL;


-- Câu 12: Vi?t Câu SQL xu?t ra, Ngày hi?n t?i, này hôm qua, ngày mai
SELECT 
SYSDATE + INTERVAL '-1' DAY AS YESTERDAY, 
SYSDATE as NOW, 
SYSDATE + INTERVAL '1' DAY AS TOMORROW
FROM dual;


-- Câu 13: Ta có table (TB_ORD), yêu c?u vi?t câu SQL ?? generate ORD_NO có ?ô dài 10 t? v?i format sau: yyyymmdd000Seq, ví d? hnay là 20191028 và ch?a có seq nào thì ORD_NO s? là 201910280001, và n?u ?ã t?n t?i ORD_NO 201910280001 thì nó s? là 201910280002
SELECT 
    CONCAT(TO_CHAR(SYSDATE,'yyyyMMdd'), (
        SELECT(TO_CHAR(count(*) + 1, 'fm0000'))
        FROM TB_ORD 
        WHERE ord_dttm LIKE CONCAT(TO_CHAR(SYSDATE,'yyyyMMdd'),'____'))
        )
FROM TB_ORD
WHERE ROWNUM =1;


-- Câu 14
-- A) Vi?t câu SQL tìm CUST_GRP_ID sao cho: CUST_GRP_HRCHY_CD có I ho?c C nh?ng không có G
SELECT DISTINCT(CUST_GRP_ID)
FROM MDM_CUSTOMER A
WHERE CUST_GRP_ID IS NOT NULL
AND NOT EXISTS (SELECT CUST_GRP_ID FROM MDM_CUSTOMER B WHERE CUST_GRP_HRCHY_CD = 'G' AND A.CUST_GRP_ID=B.CUST_GRP_ID);

-- B) Vi?t câu SQL tìm CUST_GRP_ID sao cho: CUST_GRP_HRCHY_CD có G và có I nh?ng không có C
SELECT DISTINCT(CUST_GRP_ID)
FROM MDM_CUSTOMER A
WHERE CUST_GRP_ID IS NOT NULL
AND NOT EXISTS (SELECT CUST_GRP_ID FROM MDM_CUSTOMER B WHERE CUST_GRP_HRCHY_CD = 'C' AND A.CUST_GRP_ID=B.CUST_GRP_ID);


-- Câu 15: 
-- 1)	L?y max(PROD_UNIT_AMT)
SELECT MAX(PROD_UNIT_AMT)
FROM TB_PROD;
-- 2)	L?y  giá tr? min(PROD_UNIT_AMT)
SELECT MIN(PROD_UNIT_AMT)
FROM TB_PROD;
-- 3)	L?y giá tr? trung bình PROD_UNIT_AMT
SELECT AVG(PROD_UNIT_AMT)
FROM TB_PROD;
-- 4)	L?y tên c?a s?n ph?m có PROD_UNIT_AMT l?n nh?t
SELECT prod_unit_amt AS MAX_AMT, prod_nm AS MAX_NAME, B.minp AS MIN_AMT, B.avgp AS AVG
from tb_prod A,(SELECT MAX(prod_unit_amt) AS MAXP, MIN(prod_unit_amt) AS MINP, AVG(prod_unit_amt) AS AVGP from tb_prod) B
WHERE prod_unit_amt = (SELECT MAX(PROD_UNIT_AMT) FROM tb_prod) AND A.prod_unit_amt = B.maxp;


--Câu 16:
--A) Vi?t câu SQL l?y ra top3 s?n ph?m ?c bán nhi?u nh?t.
SELECT * 
FROM (
  SELECT PRO_CD, DENSE_RANK() OVER(ORDER BY COUNT(*) DESC) AS TOP
  FROM TB_ORD
  GROUP BY PRO_CD
  ) A
WHERE A.TOP <=  3;

--B) Vi?t câu SQL l?y ra cái ORD_DT, ORD_TM, PROD_CD g?n nh?t theo CUST_NO
SELECT *
FROM ( 
    SELECT CUST_NO,ORD_DTTM, ROW_NUMBER () OVER (PARTITION BY CUST_NO ORDER BY ORD_DTTM DESC) rn
    FROM TB_ORD
    GROUP BY CUST_NO, ORD_DTTM
  ) A
  WHERE A.RN = 1;

--C) vi?t câu SQL report xem trong tháng 06, 07, 08, 09 c?a 2019 s?n ph?m có mã code là 00001 bán ?c bao nhiêu cái.
SELECT
    a.mon,
    nvl(b.pro_cd, '00001') AS pro_cd,
    nvl(b.total, 0)
FROM
    (
        SELECT DISTINCT
            ( substr(ord_dttm, 1, 6) ) AS mon
        FROM
            tb_ord
        WHERE
            substr(ord_dttm, 1, 5) LIKE '20190'
            AND substr(ord_dttm, 6, 1) IN ( '6', '7', '8',
                                            '9' )
    ) a
    LEFT OUTER JOIN (
        SELECT
            a.mon,
            pro_cd,
            COUNT(pro_cd) AS total
        FROM
            (
                SELECT
                    substr(ord_dttm, 1, 6) AS mon,
                    pro_cd
                FROM
                    tb_ord
                WHERE
                    substr(ord_dttm, 1, 5) LIKE '20190'
                    AND substr(ord_dttm, 6, 1) IN ( '6', '7', '8',
                                                    '9' )
                    AND pro_cd LIKE '00001'
            ) a
        GROUP BY
            mon,
            pro_cd
    ) b ON a.mon = b.mon;
    
--D) Gi? s? lúc ??u s?n ph?n 00001 có 100 cái, vi?t report ?? tính s? l??ng remain theo tháng 06, 07, 08, 09
SELECT
    doo.pro_cd,
    doo.total,
    100 - nvl(SUM(doo.total)
              OVER(PARTITION BY doo.pro_cd
                   ORDER BY
                       doo.mon
              ), 0) AS remain
FROM
    (
        SELECT
            a.mon                  AS mon,
            nvl(b.pro_cd, '00001') AS pro_cd,
            nvl(b.total, 0)        AS total
        FROM
            (
                SELECT DISTINCT
                    ( substr(ord_dttm, 1, 6) ) AS mon
                FROM
                    tb_ord
                WHERE
                    substr(ord_dttm, 1, 5) LIKE '20190'
                    AND substr(ord_dttm, 6, 1) IN ( '6', '7', '8', '9' )
            ) a
            LEFT OUTER JOIN (
                SELECT
                    a.mon,
                    pro_cd,
                    COUNT(pro_cd) AS total
                FROM
                    (
                        SELECT
                            substr(ord_dttm, 1, 6) AS mon,
                            pro_cd
                        FROM
                            tb_ord
                        WHERE
                            substr(ord_dttm, 1, 5) LIKE '20190'
                            AND substr(ord_dttm, 6, 1) IN ( '6', '7', '8', '9' )
                            AND pro_cd LIKE '00001'
                    ) a
                GROUP BY
                    mon,
                    pro_cd
            ) b ON a.mon = b.mon
    ) doo;