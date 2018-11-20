drop table if exists ofchat;

drop table if exists ofmessage;

drop table if exists ofpubact;

drop table if exists ofsession;

drop table if exists ofstatus;

drop table if exists ofstyle;

drop table if exists ofsubscriber;

DROP TABLE if exists ofcustomoffline;


/*==============================================================*/
/* Table: ofchat                                                */
/*==============================================================*/
create table ofchat
(
   chat_id              varchar(64) not null,
   chat_name            varchar(64),
   chat_user            varchar(64),
   chat_other           varchar(64),
   chat_create_time     varchar(64),
   chat_modify_time     varchar(64),
   chat_pic             varchar(64),
   primary key (chat_id)
)
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: ofmessage                                             */
/*==============================================================*/
create table ofmessage
(
   id_                  int not null AUTO_INCREMENT,
   msg_id               varchar(64),
   msg_type             varchar(20),
   msg_from             varchar(64),
   msg_to               varchar(64),
   msg_time             varchar(20),
   body                 varchar(4096),
   session_id           varchar(64),
   primary key (id_)
)
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: ofpubact                                              */
/*==============================================================*/
create table ofpubact
(
   id_                  int not null AUTO_INCREMENT,
   title                varchar(256),
   content              varchar(4096),
   user_id              varchar(64),
   ts                   varchar(64),
   session_id           varchar(64),
   primary key (id_)
)
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: ofsession                                             */
/*==============================================================*/
create table ofsession
(
   session_id           varchar(64) not null,
   session_type         int,
   session_name         varchar(256),
   session_create_time  varchar(20),
   session_modify_time  varchar(20),
   session_delete_time  varchar(20),
   session_valid        int,
   session_user         varchar(64),
   session_pic          varchar(256),
   primary key (session_id)
)
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: ofstatus                                              */
/*==============================================================*/
create table ofstatus
(
   id_                  int not null AUTO_INCREMENT,
   msg_id               varchar(64),
   reader                 varchar(64),
   status               int,
   primary key (id_)
)
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: ofstyle                                               */
/*==============================================================*/
create table ofstyle
(
   style_id             int not null AUTO_INCREMENT,
   style_name           varchar(64),
   style_value          int,
   user_id              varchar(64),
   primary key (style_id)
)
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: ofsubscriber                                          */
/*==============================================================*/
create table ofsubscriber
(
   user_id              varchar(64) not null,
   user_name            varchar(256),
   acct_login           varchar(64),
   pic                  varchar(128),
   session_id           varchar(64) not null,
   ts                   varchar(20),
   primary key (user_id, session_id)
)
DEFAULT CHARACTER SET = utf8;


CREATE TABLE ofcustomoffline (
id_ int NOT NULL AUTO_INCREMENT,
msg_id varchar(64) NULL,
msg_type varchar(20) NULL,
msg_from varchar(64) NULL,
msg_to varchar(64) NULL,
msg_time varchar(20) NULL,
body varchar(3960) NULL,
session_id varchar(64) NULL,
msg_status int NULL,
PRIMARY KEY (id_)
)
DEFAULT CHARACTER SET = utf8;



INSERT INTO ofVersion(name,version) values('tlim',1);

INSERT INTO ofProperty(name,propValue,encrypted) values('route.all-resources','true',0);



