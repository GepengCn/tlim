drop table "ofchat" cascade constraints;

drop table "ofmessage" cascade constraints;

drop table "ofpubact" cascade constraints;

drop table "ofsession" cascade constraints;

drop table "ofstatus" cascade constraints;

drop table "ofstyle" cascade constraints;

drop table "ofsubscriber" cascade constraints;

/*==============================================================*/
/* Table: "ofchat"                                              */
/*==============================================================*/
create table "ofchat"
(
   "chat_id"            VARCHAR2(64)         not null,
   "chat_name"          VARCHAR2(64),
   "chat_user"          VARCHAR2(64),
   "chat_other"         VARCHAR2(64),
   "chat_create_time"   VARCHAR2(64),
   "chat_modify_time"   VARCHAR2(64),
   "chat_pic"           VARCHAR2(64),
   constraint PK_OFCHAT primary key ("chat_id")
);

/*==============================================================*/
/* Table: "ofmessage"                                           */
/*==============================================================*/
create table "ofmessage"
(
   "id_"                INTEGER              not null,
   "msg_id"             VARCHAR2(64),
   "msg_type"           VARCHAR2(20),
   "msg_from"           VARCHAR2(64),
   "msg_to"             VARCHAR2(64),
   "msg_time"           VARCHAR2(20),
   "body"               VARCHAR2(4096),
   "session_id"         VARCHAR2(64),
   constraint PK_OFMESSAGE primary key ("id_")
);

/*==============================================================*/
/* Table: "ofpubact"                                            */
/*==============================================================*/
create table "ofpubact"
(
   "id_"                INTEGER              not null,
   "title"              VARCHAR2(256),
   "content"            VARCHAR2(4096),
   "user_id"            VARCHAR2(64),
   "ts"                 VARCHAR2(64),
   "session_id"         VARCHAR2(64),
   constraint PK_OFPUBACT primary key ("id_")
);

/*==============================================================*/
/* Table: "ofsession"                                           */
/*==============================================================*/
create table "ofsession"
(
   "session_id"         VARCHAR2(64)         not null,
   "session_type"       INTEGER,
   "session_name"       VARCHAR2(256),
   "session_create_time" VARCHAR2(20),
   "session_modify_time" VARCHAR2(20),
   "session_delete_time" VARCHAR2(20),
   "session_valid"      INTEGER,
   "session_user"       VARCHAR2(64),
   "session_pic"        VARCHAR2(256),
   constraint PK_OFSESSION primary key ("session_id")
);

/*==============================================================*/
/* Table: "ofstatus"                                            */
/*==============================================================*/
create table "ofstatus"
(
   "id_"                INTEGER              not null,
   "msg_id"             VARCHAR2(64),
   "msg_to"             VARCHAR2(64),
   "msg_type"           VARCHAR2(64),
   "status"             INTEGER,
   "session_id"         VARCHAR2(64),
   constraint PK_OFSTATUS primary key ("id_")
);

/*==============================================================*/
/* Table: "ofstyle"                                             */
/*==============================================================*/
create table "ofstyle"
(
   "style_id"           INTEGER              not null,
   "style_name"         VARCHAR2(64),
   "style_value"        INTEGER,
   "user_id"            VARCHAR2(64),
   constraint PK_OFSTYLE primary key ("style_id")
);

/*==============================================================*/
/* Table: "ofsubscriber"                                        */
/*==============================================================*/
create table "ofsubscriber"
(
   "user_id"            VARCHAR2(64)         not null,
   "user_name"          VARCHAR2(256),
   "acct_login"         VARCHAR2(64),
   "pic"                VARCHAR2(128),
   "session_id"         VARCHAR2(64)         not null,
   "ts"                 VARCHAR2(20),
   constraint PK_OFSUBSCRIBER primary key ("user_id", "session_id")
);
