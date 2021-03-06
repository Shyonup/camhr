CREATE TABLE "IM_ACCOUNT" (
"ACCESS_ID" VARCHAR2(64) NULL,
"TOKEN" VARCHAR2(255) NULL,
"ACCOUNT" VARCHAR2(255) NOT NULL,
"TYPE" INTEGER NOT NULL,
"CREATE_AT" INTEGER NULL,
PRIMARY KEY ("ACCOUNT", "TYPE")
)
NOCOMPRESS
NOPARALLEL ;

ALTER TABLE "IM_ACCOUNT" ADD UNIQUE ("ACCESS_ID");
COMMENT ON COLUMN "IM_ACCOUNT"."ACCESS_ID" IS '云通信ID';
COMMENT ON COLUMN "IM_ACCOUNT"."TOKEN" IS '云通信账号token';
COMMENT ON COLUMN "IM_ACCOUNT"."ACCOUNT" IS '平台用户ID';
COMMENT ON COLUMN "IM_ACCOUNT"."TYPE" IS '平台账号类型：0，企业用户；1，求职用户';

