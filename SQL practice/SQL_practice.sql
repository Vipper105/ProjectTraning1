-- C�u 9: cho s? 8988.80 vui l�ng xu?t ra ??nh d?ng $8,988.800
SELECT TO_CHAR( 8988.80, '$9,999.999' ) FROM DUAL;


-- C�u 10: cho s? 8988.80, 820988.80 vui l�ng xu?t ra ??nh d?ng $8,000.000, $820,000.000
SELECT TO_CHAR( TRUNC(8988.80, -3), '$9,999.999' ), TO_CHAR( TRUNC(820988.80 , -3), '$999,999.999' )
FROM DUAL;


-- C�u 12: Vi?t C�u SQL xu?t ra, Ng�y hi?n t?i, n�y h�m qua, ng�y mai
SELECT 
SYSDATE + INTERVAL '-1' DAY AS YESTERDAY, 
SYSDATE as NOW, 
SYSDATE + INTERVAL '1' DAY AS TOMORROW
FROM dual;


-- C�u 13: Ta c� table (TB_ORD), y�u c?u vi?t c�u SQL ?? generate ORD_NO c� ?� d�i 10 t? v?i format sau: yyyymmdd000Seq, v� d? hnay l� 20191028 v� ch?a c� seq n�o th� ORD_NO s? l� 201910280001, v� n?u ?� t?n t?i ORD_NO 201910280001 th� n� s? l� 201910280002
SELECT 
    CONCAT(TO_CHAR(SYSDATE,'yyyyMMdd'), (
        SELECT(TO_CHAR(count(*) + 1, 'fm0000'))
        FROM TB_ORD 
        WHERE ord_dttm LIKE CONCAT(TO_CHAR(SYSDATE,'yyyyMMdd'),'____'))
        )
FROM TB_ORD
WHERE ROWNUM =1;


-- C�u 14
-- A) Vi?t c�u SQL t�m CUST_GRP_ID sao cho: CUST_GRP_HRCHY_CD c� I ho?c C nh?ng kh�ng c� G
SELECT B.CUST_GRP_ID
FROM (
    SELECT CUST_GRP_ID, COUNT(*) OVER (PARTITION BY CUST_GRP_ID) volunm, CUST_GRP_HRCHY_CD
    FROM MDM_CUSTOMER
    GROUP BY CUST_GRP_ID, CUST_GRP_HRCHY_CD
    ) B
WHERE volunm =1 AND CUST_GRP_HRCHY_CD != 'G';


-- B) Vi?t c�u SQL t�m CUST_GRP_ID sao cho: CUST_GRP_HRCHY_CD c� G v� c� I nh?ng kh�ng c� C
SELECT B.CUST_GRP_ID
FROM (
    SELECT CUST_GRP_ID, COUNT(*) OVER (PARTITION BY CUST_GRP_ID) volunm, CUST_GRP_HRCHY_CD
    FROM MDM_CUSTOMER
    GROUP BY CUST_GRP_ID, CUST_GRP_HRCHY_CD
    ) B
WHERE volunm =1 AND CUST_GRP_HRCHY_CD != 'C';


-- C�u 15: 
-- 1)	L?y max(PROD_UNIT_AMT)
-- 2)	L?y  gi� tr? min(PROD_UNIT_AMT)
-- 3)	L?y gi� tr? trung b�nh PROD_UNIT_AMT
-- 4)	L?y t�n c?a s?n ph?m c� PROD_UNIT_AMT l?n nh?t
SELECT prod_unit_amt AS MAX_AMT, prod_nm AS MAX_NAME, B.minp AS MIN_AMT, B.avgp AS AVG
from tb_prod A,(SELECT MAX(prod_unit_amt) AS MAXP, MIN(prod_unit_amt) AS MINP, AVG(prod_unit_amt) AS AVGP from tb_prod) B
WHERE prod_unit_amt = (SELECT MAX(PROD_UNIT_AMT) FROM tb_prod) AND A.prod_unit_amt = B.maxp;


--C�u 16:
--A) Vi?t c�u SQL l?y ra top3 s?n ph?m ?c b�n nhi?u nh?t.
SELECT * 
FROM (
  SELECT PRO_CD, DENSE_RANK() OVER(ORDER BY COUNT(*) DESC) AS TOP
  FROM TB_ORD
  GROUP BY PRO_CD
  ) A
WHERE A.TOP <=  3;

--B) Vi?t c�u SQL l?y ra c�i ORD_DT, ORD_TM, PROD_CD g?n nh?t theo CUST_NO
SELECT *
FROM ( 
    SELECT CUST_NO,ORD_DTTM, ROW_NUMBER () OVER (PARTITION BY CUST_NO ORDER BY ORD_DTTM DESC) rn
    FROM TB_ORD
    GROUP BY CUST_NO, ORD_DTTM
  ) A
  WHERE A.RN = 1;

--C) vi?t c�u SQL report xem trong th�ng 06, 07, 08, 09 c?a 2019 s?n ph?m c� m� code l� 00001 b�n ?c bao nhi�u c�i.
SELECT A.DT, B.PRO_CD, NVL(B.TOTAL,0) AS TOTAL
FROM
  (
    SELECT '201906' AS DT FROM DUAL
    UNION ALL   
    SELECT '201907' AS DT FROM DUAL
    UNION ALL 
    SELECT '201908' AS DT FROM DUAL
    UNION ALL 
    SELECT '201909' AS DT FROM DUAL
    ) A 
    LEFT OUTER JOIN 
        (SELECT B.PRO_CD, SUBSTR(B.ORD_DTTM, 1, 6) AS ORD_DTTM,  COUNT(*) AS TOTAL 
        FROM TB_ORD B
        GROUP BY B.PRO_CD, SUBSTR(B.ORD_DTTM, 1, 6)
        ) B 
        PARTITION BY (B.PRO_CD)
    ON A.DT = B.ORD_DTTM;
    
--D) Gi? s? l�c ??u s?n ph?n 00001 c� 100 c�i, vi?t report ?? t�nh s? l??ng remain theo th�ng 06, 07, 08, 09
SELECT B.PRO_CD,A.DT, NVL(B.TOTAL,0) AS TOTAL, 100 - NVL(SUM(B.TOTAL) OVER (PARTITION BY B.PRO_CD ORDER BY A.DT),0) AS REMAIN
FROM
  (
    SELECT '201906' AS DT FROM DUAL
    UNION ALL 
    SELECT '201907' AS DT FROM DUAL
    UNION ALL 
    SELECT '201908' AS DT FROM DUAL
    UNION ALL 
    SELECT '201909' AS DT FROM DUAL
    ) A 
    LEFT OUTER JOIN 
    (SELECT B.PRO_CD, SUBSTR(B.ORD_DTTM, 1, 6) AS ORD_DTTM,  COUNT(*)
 AS TOTAL 
      FROM TB_ORD B
      WHERE B.PRO_CD = '00001'
      GROUP BY B.PRO_CD, SUBSTR(B.ORD_DTTM, 1, 6)
    ) B PARTITION BY (B.PRO_CD)
    ON A.DT = B.ORD_DTTM;
    
    
 SELECT INV.JO_CRR_CD
     , INV.RLANE_CD
     , INV.LOCL_CURR_CD
     , INV.INV_NO
     , INV.CSR_NO
     , INV.APRO_FLG
     , INV.CUST_VNDR_CNT_CD
     , INV.CUST_VNDR_SEQ
     , INV.PRNR_REF_NO
     , INV.CUST_VNDR_ENG_NM
     , SUM(INV.REV_ACT_AMT) AS INV_REV_ACT_AMT
     , SUM(INV.EXP_ACT_AMT) AS INV_EXP_ACT_AMT
  FROM (
    SELECT INV.JO_CRR_CD
     , STL.RLANE_CD
     , NVL(INV.INV_CURR_CD, INV.LOCL_CURR_CD) AS LOCL_CURR_CD
     , DTL.ACT_AMT
     , INV.INV_NO
     , NVL(INV.SLP_TP_CD||INV.SLP_FUNC_CD||INV.SLP_OFC_CD||INV.SLP_ISS_DT||INV.SLP_SER_NO,'N/A') AS CSR_NO
     , NVL(CSR.APRO_FLG, 'N') AS APRO_FLG
     , DECODE(INV.RE_DIVR_CD,'R',SUBSTR(INV.PRNR_REF_NO,1,2), NULL) AS CUST_VNDR_CNT_CD
     , DECODE(INV.RE_DIVR_CD,'R',SUBSTR(INV.PRNR_REF_NO,3), INV.PRNR_REF_NO) AS CUST_VNDR_SEQ
     , INV.PRNR_REF_NO
     , DECODE('R', STL.RE_DIVR_CD, NVL(DTL.LOCL_AMT, DTL.ACT_AMT), 0) AS REV_ACT_AMT
     , DECODE('E', STL.RE_DIVR_CD, NVL(DTL.LOCL_AMT, DTL.ACT_AMT), 0) AS EXP_ACT_AMT
     , CASE WHEN INV.RE_DIVR_CD = 'R' THEN
                 ( SELECT M.CUST_LGL_ENG_NM
                     FROM MDM_CUSTOMER M
                    WHERE M.DELT_FLG = 'N'
                      AND M.CUST_CNT_CD   = SUBSTR(INV.PRNR_REF_NO,1,2)
                      AND M.CUST_SEQ      = SUBSTR(INV.PRNR_REF_NO,3) )
            ELSE
                 ( SELECT M.VNDR_LGL_ENG_NM
                     FROM MDM_VENDOR M
                    WHERE M.DELT_FLG = 'N'
                      AND M.VNDR_SEQ      = INV.PRNR_REF_NO )
       END AS CUST_VNDR_ENG_NM
    FROM JOO_INVOICE INV
     , JOO_INV_DTL DTL
     , JOO_STL_TGT STL
     , JOO_CSR     CSR
    WHERE 1=1 

    AND INV.RJCT_CMB_FLG  = 'N'
    AND DTL.ACCT_YRMON    = INV.ACCT_YRMON
    AND DTL.JO_CRR_CD     = INV.JO_CRR_CD
    AND DTL.INV_NO        = INV.INV_NO
    AND DTL.RE_DIVR_CD    = INV.RE_DIVR_CD
    AND STL.THEA_STL_FLG  = INV.THEA_STL_FLG
    AND STL.VSL_CD        = DTL.VSL_CD
    AND STL.SKD_VOY_NO    = DTL.SKD_VOY_NO
    AND STL.SKD_DIR_CD    = DTL.SKD_DIR_CD
    AND STL.REV_DIR_CD    = DTL.REV_DIR_CD
    AND STL.REV_YRMON     = DTL.REV_YRMON
    AND STL.STL_VVD_SEQ   = DTL.STL_VVD_SEQ
    AND INV.SLP_TP_CD     = CSR.SLP_TP_CD(+)
    AND INV.SLP_FUNC_CD   = CSR.SLP_FUNC_CD(+)
    AND INV.SLP_OFC_CD    = CSR.SLP_OFC_CD(+)
    AND INV.SLP_ISS_DT    = CSR.SLP_ISS_DT(+)
    AND INV.SLP_SER_NO    = CSR.SLP_SER_NO(+)
    ) INV
 WHERE 1=1
 GROUP BY GROUPING SETS (
                            (JO_CRR_CD, RLANE_CD, CSR_NO, APRO_FLG, CUST_VNDR_CNT_CD, CUST_VNDR_SEQ, PRNR_REF_NO, CUST_VNDR_ENG_NM, LOCL_CURR_CD, INV_NO),
                         (LOCL_CURR_CD, INV_NO),
                         (LOCL_CURR_CD))
 ORDER BY INV_NO, JO_CRR_CD
 
 
 
 
 
 
 
 
 
 
 