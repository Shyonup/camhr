-- 2018-10-09
CREATE TABLE U_TWITTER(
    ID NUMBER(10,0) NOT NULL PRIMARY KEY,
    USER_ID  NUMBER(10,0),
    CONTENT VARCHAR2(4000 BYTE),
    IMAGES VARCHAR2(4000 BYTE),
    ANONYMOUS NUMBER(1,0),
    LIKES  NUMBER(8,0),
    COMMENTS NUMBER(8,0),
    STATUS NUMBER(1,0) DEFAULT 1,
    CREATE_AT DATE DEFAULT sysdate,
    UPDATE_AT Date
)
CREATE SEQUENCE SEQ_U_TWITTER INCREMENT BY 1 START WITH 1 ;
COMMENT ON COLUMN "U_TWITTER"."USER_ID" IS '求职者id';
COMMENT ON COLUMN "U_TWITTER"."CONTENT" IS '动态内容';
COMMENT ON COLUMN "U_TWITTER"."IMAGES" IS '动态图片路径';
COMMENT ON COLUMN "U_TWITTER"."ANONYMOUS" IS '是否匿名 1:不匿名 0：匿名';
COMMENT ON COLUMN "U_TWITTER"."LIKES" IS '点赞数';
COMMENT ON COLUMN "U_TWITTER"."COMMENTS" IS '评论数';
COMMENT ON COLUMN "U_TWITTER"."STATUS" IS '动态的状态 1:正常 0：删除';
COMMENT ON COLUMN "U_TWITTER"."CREATE_AT" IS '创建时间';
COMMENT ON COLUMN "U_TWITTER"."UPDATE_AT" IS '修改时间';