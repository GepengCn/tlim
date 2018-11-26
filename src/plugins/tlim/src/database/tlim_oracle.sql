-- Type package declaration
create or replace package PDTypes
as
TYPE ref_cursor IS REF CURSOR;
end;

-- Integrity package declaration
create or replace package IntegrityPackage AS
procedure InitNestLevel;
function GetNestLevel return number;
procedure NextNestLevel;
procedure PreviousNestLevel;
end IntegrityPackage;
/

-- Integrity package definition
create or replace package body IntegrityPackage AS
NestLevel number;

-- Procedure to initialize the trigger nest level
procedure InitNestLevel is
begin
NestLevel := 0;
end;


-- Function to return the trigger nest level
function GetNestLevel return number is
begin
if NestLevel is null then
NestLevel := 0;
end if;
return(NestLevel);
end;

-- Procedure to increase the trigger nest level
procedure NextNestLevel is
begin
if NestLevel is null then
NestLevel := 0;
end if;
NestLevel := NestLevel + 1;
end;

-- Procedure to decrease the trigger nest level
procedure PreviousNestLevel is
begin
NestLevel := NestLevel - 1;
end;

end IntegrityPackage;
/


drop trigger "tib_ofcustomoffline"
/

drop trigger "tib_ofmessage"
/

drop trigger "tib_ofpubact"
/

drop trigger "tib_ofstatus"
/

drop trigger "tib_ofstyle"
/

drop table "ofchat" cascade constraints
   /

drop table "ofcustomoffline" cascade constraints
   /

drop table "ofmessage" cascade constraints
   /

drop table "ofpubact" cascade constraints
   /

drop table "ofsession" cascade constraints
   /

drop table "ofstatus" cascade constraints
   /

drop table "ofstyle" cascade constraints
   /

drop table "ofsubscriber" cascade constraints
   /

drop sequence "S_ofcustomoffline"
/

drop sequence "S_ofmessage"
/

drop sequence "S_ofpubact"
/

drop sequence "S_ofstatus"
/

drop sequence "S_ofstyle"
/

create sequence "S_ofcustomoffline"
/

create sequence "S_ofmessage"
/

create sequence "S_ofpubact"
/

create sequence "S_ofstatus"
/

create sequence "S_ofstyle"
/

/*==============================================================*/
/* Table: "ofchat"                                              */
/*==============================================================*/
create table "ofchat"  (
                          "chat_id"            VARCHAR2(64)                    not null,
                          "chat_name"          VARCHAR2(64),
                          "chat_user"          VARCHAR2(64),
                          "chat_other"         VARCHAR2(64),
                          "chat_create_time"   VARCHAR2(64),
                          "chat_modify_time"   VARCHAR2(64),
                          "chat_pic"           VARCHAR2(64),
                          constraint PK_OFCHAT primary key ("chat_id")
)
   /

   comment on column "ofchat"."chat_id" is
   '主键'
   /

   comment on column "ofchat"."chat_name" is
   '名称'
   /

   comment on column "ofchat"."chat_user" is
   '聊天者某方'
   /

   comment on column "ofchat"."chat_other" is
   '聊天者另一方'
   /

   comment on column "ofchat"."chat_create_time" is
   '创建时间'
   /

   comment on column "ofchat"."chat_modify_time" is
   '修改时间'
   /

   comment on column "ofchat"."chat_pic" is
   '图标'
   /

/*==============================================================*/
/* Table: "ofcustomoffline"                                     */
/*==============================================================*/
create table "ofcustomoffline"  (
                                   "id_"                NUMBER(6)                       not null,
                                   "msg_id"             VARCHAR2(64),
                                   "msg_type"           VARCHAR2(20),
                                   "msg_from"           VARCHAR2(64),
                                   "msg_to"             VARCHAR2(64),
                                   "msg_time"           VARCHAR2(20),
                                   "body"               VARCHAR2(3920),
                                   "session_id"         VARCHAR2(64),
                                   "msg_status"         INTEGER,
                                   "delete_user"        VARCHAR2(64),
                                   constraint PK_OFCUSTOMOFFLINE primary key ("id_")
)
   /

   comment on column "ofcustomoffline"."id_" is
   '主键'
   /

   comment on column "ofcustomoffline"."msg_id" is
   '消息id'
   /

   comment on column "ofcustomoffline"."msg_type" is
   '消息类型'
   /

   comment on column "ofcustomoffline"."msg_from" is
   '发送者'
   /

   comment on column "ofcustomoffline"."msg_to" is
   '接收者'
   /

   comment on column "ofcustomoffline"."msg_time" is
   '发送时间'
   /

   comment on column "ofcustomoffline"."body" is
   '消息体'
   /

   comment on column "ofcustomoffline"."session_id" is
   '会话id'
   /

   comment on column "ofcustomoffline"."msg_status" is
   '消息状态0：web与mobile未收1：web已收但mobile未收'
   /

   comment on column "ofcustomoffline"."delete_user" is
   '所属用户，清理离线消息时使用的'
   /

/*==============================================================*/
/* Table: "ofmessage"                                           */
/*==============================================================*/
create table "ofmessage"  (
                             "id_"                NUMBER(6)                       not null,
                             "msg_id"             VARCHAR2(64),
                             "msg_type"           VARCHAR2(20),
                             "msg_from"           VARCHAR2(64),
                             "msg_to"             VARCHAR2(64),
                             "msg_time"           VARCHAR2(20),
                             "body"               VARCHAR2(3920),
                             "session_id"         VARCHAR2(64),
                             constraint PK_OFMESSAGE primary key ("id_")
)
   /

   comment on column "ofmessage"."id_" is
   '主键'
   /

   comment on column "ofmessage"."msg_id" is
   '消息id'
   /

   comment on column "ofmessage"."msg_type" is
   '消息类型'
   /

   comment on column "ofmessage"."msg_from" is
   '发送人'
   /

   comment on column "ofmessage"."msg_to" is
   '接收人'
   /

   comment on column "ofmessage"."msg_time" is
   '发送时间'
   /

   comment on column "ofmessage"."body" is
   '消息体'
   /

   comment on column "ofmessage"."session_id" is
   '会话id'
   /

/*==============================================================*/
/* Table: "ofpubact"                                            */
/*==============================================================*/
create table "ofpubact"  (
                            "id_"                NUMBER(6)                       not null,
                            "title"              VARCHAR2(256),
                            "content"            VARCHAR2(3920),
                            "user_id"            VARCHAR2(64),
                            "ts"                 VARCHAR2(64),
                            "session_id"         VARCHAR2(64),
                            constraint PK_OFPUBACT primary key ("id_")
)
   /

   comment on column "ofpubact"."id_" is
   '主键'
   /

   comment on column "ofpubact"."title" is
   '标题'
   /

   comment on column "ofpubact"."content" is
   '内容'
   /

   comment on column "ofpubact"."user_id" is
   '创建用户'
   /

   comment on column "ofpubact"."ts" is
   '时间戳'
   /

   comment on column "ofpubact"."session_id" is
   '会话id'
   /

/*==============================================================*/
/* Table: "ofsession"                                           */
/*==============================================================*/
create table "ofsession"  (
                             "session_id"         VARCHAR2(64)                    not null,
                             "session_type"       INTEGER,
                             "session_name"       VARCHAR2(256),
                             "session_create_time" VARCHAR2(20),
                             "session_modify_time" VARCHAR2(20),
                             "session_delete_time" VARCHAR2(20),
                             "session_valid"      INTEGER,
                             "session_user"       VARCHAR2(64),
                             "session_pic"        VARCHAR2(256),
                             constraint PK_OFSESSION primary key ("session_id")
)
   /

   comment on column "ofsession"."session_id" is
   '会话id'
   /

   comment on column "ofsession"."session_type" is
   '会话类型'
   /

   comment on column "ofsession"."session_name" is
   '会话名称'
   /

   comment on column "ofsession"."session_create_time" is
   '创建时间'
   /

   comment on column "ofsession"."session_modify_time" is
   '修改时间'
   /

   comment on column "ofsession"."session_delete_time" is
   '删除时间'
   /

   comment on column "ofsession"."session_valid" is
   '是否有效0：有效1：无效'
   /

   comment on column "ofsession"."session_user" is
   '会话创建者，管理员'
   /

   comment on column "ofsession"."session_pic" is
   '会话图标'
   /

/*==============================================================*/
/* Table: "ofstatus"                                            */
/*==============================================================*/
create table "ofstatus"  (
                            "id_"                NUMBER(6)                       not null,
                            "msg_id"             VARCHAR2(64),
                            "sender"             VARCHAR2(64),
                            "reader"             VARCHAR2(64),
                            "status"             INTEGER,
                            constraint PK_OFSTATUS primary key ("id_")
)
   /

   comment on column "ofstatus"."id_" is
   '主键'
   /

   comment on column "ofstatus"."msg_id" is
   '消息id'
   /

   comment on column "ofstatus"."sender" is
   '发送者'
   /

   comment on column "ofstatus"."reader" is
   '接收者'
   /

   comment on column "ofstatus"."status" is
   '状态0：未读1：已读'
   /

/*==============================================================*/
/* Table: "ofstyle"                                             */
/*==============================================================*/
create table "ofstyle"  (
                           "style_id"           NUMBER(6)                       not null,
                           "style_name"         VARCHAR2(64),
                           "style_value"        INTEGER,
                           "user_id"            VARCHAR2(64),
                           constraint PK_OFSTYLE primary key ("style_id")
)
   /

   comment on column "ofstyle"."style_id" is
   '主键'
   /

   comment on column "ofstyle"."style_name" is
   '风格名称'
   /

   comment on column "ofstyle"."style_value" is
   '风格值'
   /

   comment on column "ofstyle"."user_id" is
   '用户id'
   /

/*==============================================================*/
/* Table: "ofsubscriber"                                        */
/*==============================================================*/
create table "ofsubscriber"  (
                                "user_id"            VARCHAR2(64)                    not null,
                                "user_name"          VARCHAR2(256),
                                "acct_login"         VARCHAR2(64),
                                "pic"                VARCHAR2(256),
                                "session_id"         VARCHAR2(64)                    not null,
                                "ts"                 VARCHAR2(20),
                                constraint PK_OFSUBSCRIBER primary key ("user_id", "session_id")
)
   /

   comment on column "ofsubscriber"."user_id" is
   '用户id'
   /

   comment on column "ofsubscriber"."user_name" is
   '用户名'
   /

   comment on column "ofsubscriber"."acct_login" is
   '登录名'
   /

   comment on column "ofsubscriber"."pic" is
   '图标地址'
   /

   comment on column "ofsubscriber"."session_id" is
   '会话id'
   /

   comment on column "ofsubscriber"."ts" is
   '时间戳'
   /


create trigger "tib_ofcustomoffline" before insert
on "ofcustomoffline" for each row
declare
integrity_error  exception;
errno            integer;
errmsg           char(200);
dummy            integer;
found            boolean;

begin
--  Column ""id_"" uses sequence S_ofcustomoffline
select S_ofcustomoffline.NEXTVAL INTO :new."id_" from dual;

--  Errors handling
exception
when integrity_error then
raise_application_error(errno, errmsg);
end;
/


create trigger "tib_ofmessage" before insert
on "ofmessage" for each row
declare
integrity_error  exception;
errno            integer;
errmsg           char(200);
dummy            integer;
found            boolean;

begin
--  Column ""id_"" uses sequence S_ofmessage
select S_ofmessage.NEXTVAL INTO :new."id_" from dual;

--  Errors handling
exception
when integrity_error then
raise_application_error(errno, errmsg);
end;
/


create trigger "tib_ofpubact" before insert
on "ofpubact" for each row
declare
integrity_error  exception;
errno            integer;
errmsg           char(200);
dummy            integer;
found            boolean;

begin
--  Column ""id_"" uses sequence S_ofpubact
select S_ofpubact.NEXTVAL INTO :new."id_" from dual;

--  Errors handling
exception
when integrity_error then
raise_application_error(errno, errmsg);
end;
/


create trigger "tib_ofstatus" before insert
on "ofstatus" for each row
declare
integrity_error  exception;
errno            integer;
errmsg           char(200);
dummy            integer;
found            boolean;

begin
--  Column ""id_"" uses sequence S_ofstatus
select S_ofstatus.NEXTVAL INTO :new."id_" from dual;

--  Errors handling
exception
when integrity_error then
raise_application_error(errno, errmsg);
end;
/


create trigger "tib_ofstyle" before insert
on "ofstyle" for each row
declare
integrity_error  exception;
errno            integer;
errmsg           char(200);
dummy            integer;
found            boolean;

begin
--  Column ""style_id"" uses sequence S_ofstyle
select S_ofstyle.NEXTVAL INTO :new."style_id" from dual;

--  Errors handling
exception
when integrity_error then
raise_application_error(errno, errmsg);
end;
/


INSERT INTO ofVersion(name,version) values('tlim',1);

INSERT INTO ofProperty(name,propValue,encrypted) values('route.all-resources','true',0);

