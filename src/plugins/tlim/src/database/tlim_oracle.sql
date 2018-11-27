

/*==============================================================*/
/* table: ofchat                                              */
/*==============================================================*/
create table ofchat  (
    chat_id            varchar2(64)                    not null,
    chat_name          varchar2(64),
    chat_user          varchar2(64),
    chat_other         varchar2(64),
    chat_create_time   varchar2(64),
    chat_modify_time   varchar2(64),
    chat_pic           varchar2(64),
    constraint pk_ofchat primary key (chat_id)
);


/*==============================================================*/
/* table: ofcustomoffline                                     */
/*==============================================================*/
create table ofcustomoffline  (
   id_                varchar2(64)                       not null,
   msg_id             varchar2(64),
   msg_type           varchar2(20),
   msg_from           varchar2(64),
   msg_to             varchar2(64),
   msg_time           varchar2(20),
   body               varchar2(3920),
   session_id         varchar2(64),
   msg_status         integer,
   delete_user        varchar2(64),
   constraint pk_ofcustomoffline primary key (id_)
);

/*==============================================================*/
/* table: ofmessage                                           */
/*==============================================================*/
create table ofmessage  (
   id_                varchar2(64)                      not null,
   msg_id             varchar2(64),
   msg_type           varchar2(20),
   msg_from           varchar2(64),
   msg_to             varchar2(64),
   msg_time           varchar2(20),
   body               varchar2(3920),
   session_id         varchar2(64),
   constraint pk_ofmessage primary key (id_)
);

/*==============================================================*/
/* table: ofpubact                                            */
/*==============================================================*/
create table ofpubact  (
    id_                varchar2(64)                       not null,
    title              varchar2(256),
    content            varchar2(3920),
    user_id            varchar2(64),
    ts                 varchar2(64),
    session_id         varchar2(64),
    constraint pk_ofpubact primary key (id_)
);

/*==============================================================*/
/* table: ofsession                                           */
/*==============================================================*/
create table ofsession  (
   session_id         varchar2(64)                    not null,
   session_type       integer,
   session_name       varchar2(256),
   session_create_time varchar2(20),
   session_modify_time varchar2(20),
   session_delete_time varchar2(20),
   session_valid      integer,
   session_user       varchar2(64),
   session_pic        varchar2(256),
   constraint pk_ofsession primary key (session_id)
);

/*==============================================================*/
/* table: ofstatus                                            */
/*==============================================================*/
create table ofstatus  (
  id_                varchar2(64)                       not null,
  msg_id             varchar2(64),
  sender             varchar2(64),
  reader             varchar2(64),
  status             integer,
  constraint pk_ofstatus primary key (id_)
);


/*==============================================================*/
/* table: ofstyle                                             */
/*==============================================================*/
create table ofstyle  (
   style_id           varchar2(64)                       not null,
   style_name         varchar2(64),
   style_value        integer,
   user_id            varchar2(64),
   constraint pk_ofstyle primary key (style_id)
);

/*==============================================================*/
/* table: ofsubscriber                                        */
/*==============================================================*/
create table ofsubscriber  (
    user_id            varchar2(64)                    not null,
    user_name          varchar2(256),
    acct_login         varchar2(64),
    pic                varchar2(256),
    session_id         varchar2(64)                    not null,
    ts                 varchar2(20),
    constraint pk_ofsubscriber primary key (user_id, session_id)
);


/*
insert into ofversion(name,version) values('tlim',1);

insert into ofproperty(name,propvalue,encrypted) values('route.all-resources','true',0);
*/
