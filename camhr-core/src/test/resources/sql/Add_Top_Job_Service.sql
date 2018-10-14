-- 2018-10-12 【添加常量ITEM_ID】【添加 职位置顶 服务包】【添加 职位置顶 服务项】
DECLARE
pServiceId number;
itemId number := 13;
 begin
   SELECT SEQ_P_SERVICE.nextval INTO pServiceId FROM DUAL;

   INSERT INTO B_ITEM (ID, NAME_ZH, NAME_EN, NAME_KH, STATUS, SORT_ORDER)
   VALUES (itemId, '职位置顶', 'Top Job', 'Top Job', 0, 101);

   INSERT INTO P_SERVICE (
    ID             ,
    NAME_EN        ,
    NAME_ZH        ,
    NAME_KH        ,
    PRICE          ,
    UDATE          ,
    END_DATE       ,
    STATUS         ,
    DESCRIPTION_EN ,
    DESCRIPTION_ZH ,
    DESCRIPTION_KH ,
    SORT_ORDER
  ) VALUES (
    pServiceId,
    'Top Job',
    'Top Job',
    'Top Job',
    10.00,
    to_date('2018-10-13', 'yyyy-mm-dd'),
    to_date('2038-01-01', 'yyyy-mm-dd'),
    0,
    'Top Job',
    'Top Job',
    'Top Job',
    10
  );

  INSERT INTO P_SERVICE_ITEM (
    ID         ,
    SERVICE_ID ,
    ITEM_ID    ,
    AMOUNT     ,
    EXPDATE    ,
    DISPLAY
  ) VALUES (
    SEQ_P_SERVICE_ITEM.nextval,
    pServiceId,
    itemId,
    1,
    30,
    1
  );
end;