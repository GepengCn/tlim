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

  if exists(select 1 from sys.extended_properties p where
  p.major_id = object_id('ofchat')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'chat_id')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofchat', 'column', 'chat_id'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '主键',
       'user', @CurrentUser, 'table', 'ofchat', 'column', 'chat_id'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofchat')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'chat_name')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofchat', 'column', 'chat_name'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '名称',
       'user', @CurrentUser, 'table', 'ofchat', 'column', 'chat_name'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofchat')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'chat_user')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofchat', 'column', 'chat_user'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '聊天者某方',
       'user', @CurrentUser, 'table', 'ofchat', 'column', 'chat_user'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofchat')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'chat_other')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofchat', 'column', 'chat_other'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '聊天者另一方',
       'user', @CurrentUser, 'table', 'ofchat', 'column', 'chat_other'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofchat')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'chat_create_time')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofchat', 'column', 'chat_create_time'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '创建时间',
       'user', @CurrentUser, 'table', 'ofchat', 'column', 'chat_create_time'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofchat')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'chat_modify_time')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofchat', 'column', 'chat_modify_time'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '修改时间',
       'user', @CurrentUser, 'table', 'ofchat', 'column', 'chat_modify_time'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofchat')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'chat_pic')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofchat', 'column', 'chat_pic'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '图标',
       'user', @CurrentUser, 'table', 'ofchat', 'column', 'chat_pic'
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

  if exists(select 1 from sys.extended_properties p where
  p.major_id = object_id('ofcustomoffline')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'id_')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofcustomoffline', 'column', 'id_'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '主键',
       'user', @CurrentUser, 'table', 'ofcustomoffline', 'column', 'id_'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofcustomoffline')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'msg_id')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofcustomoffline', 'column', 'msg_id'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '消息id',
       'user', @CurrentUser, 'table', 'ofcustomoffline', 'column', 'msg_id'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofcustomoffline')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'msg_type')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofcustomoffline', 'column', 'msg_type'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '消息类型',
       'user', @CurrentUser, 'table', 'ofcustomoffline', 'column', 'msg_type'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofcustomoffline')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'msg_from')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofcustomoffline', 'column', 'msg_from'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '发送者',
       'user', @CurrentUser, 'table', 'ofcustomoffline', 'column', 'msg_from'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofcustomoffline')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'msg_to')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofcustomoffline', 'column', 'msg_to'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '接收者',
       'user', @CurrentUser, 'table', 'ofcustomoffline', 'column', 'msg_to'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofcustomoffline')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'msg_time')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofcustomoffline', 'column', 'msg_time'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '发送时间',
       'user', @CurrentUser, 'table', 'ofcustomoffline', 'column', 'msg_time'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofcustomoffline')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'body')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofcustomoffline', 'column', 'body'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '消息体',
       'user', @CurrentUser, 'table', 'ofcustomoffline', 'column', 'body'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofcustomoffline')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'session_id')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofcustomoffline', 'column', 'session_id'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '会话id',
       'user', @CurrentUser, 'table', 'ofcustomoffline', 'column', 'session_id'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofcustomoffline')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'msg_status')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofcustomoffline', 'column', 'msg_status'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '消息状态0：web与mobile未收1：web已收但mobile未收',
       'user', @CurrentUser, 'table', 'ofcustomoffline', 'column', 'msg_status'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofcustomoffline')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'delete_user')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofcustomoffline', 'column', 'delete_user'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '所属用户，清理离线消息时使用的',
       'user', @CurrentUser, 'table', 'ofcustomoffline', 'column', 'delete_user'
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

  if exists(select 1 from sys.extended_properties p where
  p.major_id = object_id('ofmessage')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'id_')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofmessage', 'column', 'id_'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '主键',
       'user', @CurrentUser, 'table', 'ofmessage', 'column', 'id_'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofmessage')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'msg_id')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofmessage', 'column', 'msg_id'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '消息id',
       'user', @CurrentUser, 'table', 'ofmessage', 'column', 'msg_id'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofmessage')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'msg_type')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofmessage', 'column', 'msg_type'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '消息类型',
       'user', @CurrentUser, 'table', 'ofmessage', 'column', 'msg_type'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofmessage')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'msg_from')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofmessage', 'column', 'msg_from'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '发送人',
       'user', @CurrentUser, 'table', 'ofmessage', 'column', 'msg_from'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofmessage')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'msg_to')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofmessage', 'column', 'msg_to'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '接收人',
       'user', @CurrentUser, 'table', 'ofmessage', 'column', 'msg_to'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofmessage')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'msg_time')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofmessage', 'column', 'msg_time'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '发送时间',
       'user', @CurrentUser, 'table', 'ofmessage', 'column', 'msg_time'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofmessage')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'body')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofmessage', 'column', 'body'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '消息体',
       'user', @CurrentUser, 'table', 'ofmessage', 'column', 'body'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofmessage')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'session_id')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofmessage', 'column', 'session_id'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '会话id',
       'user', @CurrentUser, 'table', 'ofmessage', 'column', 'session_id'
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

  if exists(select 1 from sys.extended_properties p where
  p.major_id = object_id('ofpubact')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'id_')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofpubact', 'column', 'id_'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '主键',
       'user', @CurrentUser, 'table', 'ofpubact', 'column', 'id_'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofpubact')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'title')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofpubact', 'column', 'title'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '标题',
       'user', @CurrentUser, 'table', 'ofpubact', 'column', 'title'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofpubact')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'content')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofpubact', 'column', 'content'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '内容',
       'user', @CurrentUser, 'table', 'ofpubact', 'column', 'content'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofpubact')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'user_id')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofpubact', 'column', 'user_id'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '创建用户',
       'user', @CurrentUser, 'table', 'ofpubact', 'column', 'user_id'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofpubact')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'ts')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofpubact', 'column', 'ts'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '时间戳',
       'user', @CurrentUser, 'table', 'ofpubact', 'column', 'ts'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofpubact')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'session_id')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofpubact', 'column', 'session_id'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '会话id',
       'user', @CurrentUser, 'table', 'ofpubact', 'column', 'session_id'
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

  if exists(select 1 from sys.extended_properties p where
  p.major_id = object_id('ofsession')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'session_id')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofsession', 'column', 'session_id'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '会话id',
       'user', @CurrentUser, 'table', 'ofsession', 'column', 'session_id'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofsession')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'session_type')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofsession', 'column', 'session_type'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '会话类型',
       'user', @CurrentUser, 'table', 'ofsession', 'column', 'session_type'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofsession')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'session_name')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofsession', 'column', 'session_name'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '会话名称',
       'user', @CurrentUser, 'table', 'ofsession', 'column', 'session_name'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofsession')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'session_create_time')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofsession', 'column', 'session_create_time'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '创建时间',
       'user', @CurrentUser, 'table', 'ofsession', 'column', 'session_create_time'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofsession')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'session_modify_time')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofsession', 'column', 'session_modify_time'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '修改时间',
       'user', @CurrentUser, 'table', 'ofsession', 'column', 'session_modify_time'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofsession')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'session_delete_time')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofsession', 'column', 'session_delete_time'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '删除时间',
       'user', @CurrentUser, 'table', 'ofsession', 'column', 'session_delete_time'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofsession')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'session_valid')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofsession', 'column', 'session_valid'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '是否有效0：有效1：无效',
       'user', @CurrentUser, 'table', 'ofsession', 'column', 'session_valid'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofsession')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'session_user')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofsession', 'column', 'session_user'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '会话创建者，管理员',
       'user', @CurrentUser, 'table', 'ofsession', 'column', 'session_user'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofsession')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'session_pic')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofsession', 'column', 'session_pic'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '会话图标',
       'user', @CurrentUser, 'table', 'ofsession', 'column', 'session_pic'
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

  if exists(select 1 from sys.extended_properties p where
  p.major_id = object_id('ofstatus')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'id_')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofstatus', 'column', 'id_'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '主键',
       'user', @CurrentUser, 'table', 'ofstatus', 'column', 'id_'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofstatus')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'msg_id')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofstatus', 'column', 'msg_id'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '消息id',
       'user', @CurrentUser, 'table', 'ofstatus', 'column', 'msg_id'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofstatus')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'sender')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofstatus', 'column', 'sender'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '发送者',
       'user', @CurrentUser, 'table', 'ofstatus', 'column', 'sender'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofstatus')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'reader')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofstatus', 'column', 'reader'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '接收者',
       'user', @CurrentUser, 'table', 'ofstatus', 'column', 'reader'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofstatus')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'status')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofstatus', 'column', 'status'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '状态0：未读1：已读',
       'user', @CurrentUser, 'table', 'ofstatus', 'column', 'status'
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

  if exists(select 1 from sys.extended_properties p where
  p.major_id = object_id('ofstyle')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'style_id')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofstyle', 'column', 'style_id'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '主键',
       'user', @CurrentUser, 'table', 'ofstyle', 'column', 'style_id'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofstyle')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'style_name')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofstyle', 'column', 'style_name'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '风格名称',
       'user', @CurrentUser, 'table', 'ofstyle', 'column', 'style_name'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofstyle')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'style_value')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofstyle', 'column', 'style_value'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '风格值',
       'user', @CurrentUser, 'table', 'ofstyle', 'column', 'style_value'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofstyle')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'user_id')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofstyle', 'column', 'user_id'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '用户id',
       'user', @CurrentUser, 'table', 'ofstyle', 'column', 'user_id'
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

  if exists(select 1 from sys.extended_properties p where
  p.major_id = object_id('ofsubscriber')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'user_id')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofsubscriber', 'column', 'user_id'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '用户id',
       'user', @CurrentUser, 'table', 'ofsubscriber', 'column', 'user_id'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofsubscriber')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'user_name')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofsubscriber', 'column', 'user_name'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '用户名',
       'user', @CurrentUser, 'table', 'ofsubscriber', 'column', 'user_name'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofsubscriber')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'acct_login')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofsubscriber', 'column', 'acct_login'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '登录名',
       'user', @CurrentUser, 'table', 'ofsubscriber', 'column', 'acct_login'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofsubscriber')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'pic')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofsubscriber', 'column', 'pic'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '图标地址',
       'user', @CurrentUser, 'table', 'ofsubscriber', 'column', 'pic'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofsubscriber')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'session_id')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofsubscriber', 'column', 'session_id'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '会话id',
       'user', @CurrentUser, 'table', 'ofsubscriber', 'column', 'session_id'
       go

       if exists(select 1 from sys.extended_properties p where
       p.major_id = object_id('ofsubscriber')
       and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'ts')
  )
begin
declare @CurrentUser sysname
select @CurrentUser = user_name()
       execute sp_dropextendedproperty 'MS_Description',
       'user', @CurrentUser, 'table', 'ofsubscriber', 'column', 'ts'

end


select @CurrentUser = user_name()
       execute sp_addextendedproperty 'MS_Description',
       '时间戳',
       'user', @CurrentUser, 'table', 'ofsubscriber', 'column', 'ts'
       go


create trigger "CLR Trigger_ofcustomoffline" on ofcustomoffline  insert as
external name %Assembly.GeneratedName%.
go


create trigger "CLR Trigger_ofmessage" on ofmessage  insert as
external name %Assembly.GeneratedName%.
go


create trigger "CLR Trigger_ofpubact" on ofpubact  insert as
external name %Assembly.GeneratedName%.
go


create trigger "CLR Trigger_ofstatus" on ofstatus  insert as
external name %Assembly.GeneratedName%.
go


create trigger "CLR Trigger_ofstyle" on ofstyle  insert as
external name %Assembly.GeneratedName%.
go
