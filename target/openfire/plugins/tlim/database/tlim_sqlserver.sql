if exists (select 1
          from sysobjects
          where id = object_id('"CLR Trigger_ofcustomoffline"')
          and type = 'TR')
   drop trigger "CLR Trigger_ofcustomoffline"
go

if exists (select 1
          from sysobjects
          where id = object_id('"CLR Trigger_ofmessage"')
          and type = 'TR')
   drop trigger "CLR Trigger_ofmessage"
go

if exists (select 1
          from sysobjects
          where id = object_id('"CLR Trigger_ofpubact"')
          and type = 'TR')
   drop trigger "CLR Trigger_ofpubact"
go

if exists (select 1
          from sysobjects
          where id = object_id('"CLR Trigger_ofstatus"')
          and type = 'TR')
   drop trigger "CLR Trigger_ofstatus"
go

if exists (select 1
          from sysobjects
          where id = object_id('"CLR Trigger_ofstyle"')
          and type = 'TR')
   drop trigger "CLR Trigger_ofstyle"
go

if exists (select 1
            from  sysobjects
           where  id = object_id('ofchat')
            and   type = 'U')
   drop table ofchat
go

if exists (select 1
            from  sysobjects
           where  id = object_id('ofcustomoffline')
            and   type = 'U')
   drop table ofcustomoffline
go

if exists (select 1
            from  sysobjects
           where  id = object_id('ofmessage')
            and   type = 'U')
   drop table ofmessage
go

if exists (select 1
            from  sysobjects
           where  id = object_id('ofpubact')
            and   type = 'U')
   drop table ofpubact
go

if exists (select 1
            from  sysobjects
           where  id = object_id('ofsession')
            and   type = 'U')
   drop table ofsession
go

if exists (select 1
            from  sysobjects
           where  id = object_id('ofstatus')
            and   type = 'U')
   drop table ofstatus
go

if exists (select 1
            from  sysobjects
           where  id = object_id('ofstyle')
            and   type = 'U')
   drop table ofstyle
go

if exists (select 1
            from  sysobjects
           where  id = object_id('ofsubscriber')
            and   type = 'U')
   drop table ofsubscriber
go

/*==============================================================*/
/* Table: ofchat                                                */
/*==============================================================*/
create table ofchat (
   chat_id              varchar(64)          not null,
   chat_name            varchar(64)          null,
   chat_user            varchar(64)          null,
   chat_other           varchar(64)          null,
   chat_create_time     varchar(64)          null,
   chat_modify_time     varchar(64)          null,
   chat_pic             varchar(64)          null,
   constraint PK_OFCHAT primary key nonclustered (chat_id)
)
go


/*==============================================================*/
/* Table: ofcustomoffline                                       */
/*==============================================================*/
create table ofcustomoffline (
   id_                  numeric(6)           identity,
   msg_id               varchar(64)          null,
   msg_type             varchar(20)          null,
   msg_from             varchar(64)          null,
   msg_to               varchar(64)          null,
   msg_time             varchar(20)          null,
   body                 varchar(3920)        null,
   session_id           varchar(64)          null,
   msg_status           int                  null,
   delete_user          varchar(64)          null,
   constraint PK_OFCUSTOMOFFLINE primary key nonclustered (id_)
)
go


/*==============================================================*/
/* Table: ofmessage                                             */
/*==============================================================*/
create table ofmessage (
   id_                  numeric(6)           identity,
   msg_id               varchar(64)          null,
   msg_type             varchar(20)          null,
   msg_from             varchar(64)          null,
   msg_to               varchar(64)          null,
   msg_time             varchar(20)          null,
   body                 varchar(3920)        null,
   session_id           varchar(64)          null,
   constraint PK_OFMESSAGE primary key nonclustered (id_)
)
go

/*==============================================================*/
/* Table: ofpubact                                              */
/*==============================================================*/
create table ofpubact (
   id_                  numeric(6)           identity,
   title                varchar(256)         null,
   content              varchar(3920)        null,
   user_id              varchar(64)          null,
   ts                   varchar(64)          null,
   session_id           varchar(64)          null,
   constraint PK_OFPUBACT primary key nonclustered (id_)
)
go

/*==============================================================*/
/* Table: ofsession                                             */
/*==============================================================*/
create table ofsession (
   session_id           varchar(64)          not null,
   session_type         int                  null,
   session_name         varchar(256)         null,
   session_create_time  varchar(20)          null,
   session_modify_time  varchar(20)          null,
   session_delete_time  varchar(20)          null,
   session_valid        int                  null,
   "session_user"       varchar(64)          null,
   session_pic          varchar(256)         null,
   constraint PK_OFSESSION primary key nonclustered (session_id)
)
go

/*==============================================================*/
/* Table: ofstatus                                              */
/*==============================================================*/
create table ofstatus (
   id_                  numeric(6)           identity,
   msg_id               varchar(64)          null,
   sender               varchar(64)          null,
   reader               varchar(64)          null,
   status               int                  null,
   constraint PK_OFSTATUS primary key nonclustered (id_)
)
go


/*==============================================================*/
/* Table: ofstyle                                               */
/*==============================================================*/
create table ofstyle (
   style_id             numeric(6)           identity,
   style_name           varchar(64)          null,
   style_value          int                  null,
   user_id              varchar(64)          null,
   constraint PK_OFSTYLE primary key nonclustered (style_id)
)
go


/*==============================================================*/
/* Table: ofsubscriber                                          */
/*==============================================================*/
create table ofsubscriber (
   user_id              varchar(64)          not null,
   user_name            varchar(256)         null,
   acct_login           varchar(64)          null,
   pic                  varchar(256)         null,
   session_id           varchar(64)          not null,
   ts                   varchar(20)          null,
   constraint PK_OFSUBSCRIBER primary key nonclustered (user_id, session_id)
)
go
