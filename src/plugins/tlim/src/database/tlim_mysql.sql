drop table if exists ofchat;

drop table if exists ofcustomoffline;

drop table if exists ofmessage;

drop table if exists ofpubact;

drop table if exists ofsession;

drop table if exists ofstatus;

drop table if exists ofstyle;

drop table if exists ofsubscriber;

/*==============================================================*/
/* Table: ofchat                                                */
/*==============================================================*/
create table ofchat
(
   chat_id              varchar(64) not null comment '主键',
   chat_name            varchar(64) comment '名称',
   chat_user            varchar(64) comment '聊天者某方',
   chat_other           varchar(64) comment '聊天者另一方',
   chat_create_time     varchar(64) comment '创建时间',
   chat_modify_time     varchar(64) comment '修改时间',
   chat_pic             varchar(64) comment '图标',
   primary key (chat_id)
)
   DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: ofcustomoffline                                       */
/*==============================================================*/
create table ofcustomoffline
(
   id_                  int not null auto_increment comment '主键',
   msg_id               varchar(64) comment '消息id',
   msg_type             varchar(20) comment '消息类型',
   msg_from             varchar(64) comment '发送者',
   msg_to               varchar(64) comment '接收者',
   msg_time             varchar(20) comment '发送时间',
   body                 varchar(3920) comment '消息体',
   session_id           varchar(64) comment '会话id',
   msg_status           int comment '消息状态0：web与mobile未收1：web已收但mobile未收',
   delete_user          varchar(64) comment '所属用户，清理离线消息时使用的',
   primary key (id_)
);

/*==============================================================*/
/* Table: ofmessage                                             */
/*==============================================================*/
create table ofmessage
(
   id_                  int not null auto_increment comment '主键',
   msg_id               varchar(64) comment '消息id',
   msg_type             varchar(20) comment '消息类型',
   msg_from             varchar(64) comment '发送人',
   msg_to               varchar(64) comment '接收人',
   msg_time             varchar(20) comment '发送时间',
   body                 varchar(3920) comment '消息体',
   session_id           varchar(64) comment '会话id',
   primary key (id_)
)
   DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: ofpubact                                              */
/*==============================================================*/
create table ofpubact
(
   id_                  int not null auto_increment comment '主键',
   title                varchar(256) comment '标题',
   content              varchar(3920) comment '内容',
   user_id              varchar(64) comment '创建用户',
   ts                   varchar(64) comment '时间戳',
   session_id           varchar(64) comment '会话id',
   primary key (id_)
)
   DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: ofsession                                             */
/*==============================================================*/
create table ofsession
(
   session_id           varchar(64) not null comment '会话id',
   session_type         int comment '会话类型',
   session_name         varchar(256) comment '会话名称',
   session_create_time  varchar(20) comment '创建时间',
   session_modify_time  varchar(20) comment '修改时间',
   session_delete_time  varchar(20) comment '删除时间',
   session_valid        int comment '是否有效0：有效1：无效',
   session_user         varchar(64) comment '会话创建者，管理员',
   session_pic          varchar(256) comment '会话图标',
   primary key (session_id)
)
   DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: ofstatus                                              */
/*==============================================================*/
create table ofstatus
(
   id_                  int not null auto_increment comment '主键',
   msg_id               varchar(64) comment '消息id',
   sender               varchar(64) comment '发送者',
   reader               varchar(64) comment '接收者',
   status               int comment '状态0：未读1：已读',
   primary key (id_)
)
   DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: ofstyle                                               */
/*==============================================================*/
create table ofstyle
(
   style_id             int not null auto_increment comment '主键',
   style_name           varchar(64) comment '风格名称',
   style_value          int comment '风格值',
   user_id              varchar(64) comment '用户id',
   primary key (style_id)
)
   DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: ofsubscriber                                          */
/*==============================================================*/
create table ofsubscriber
(
   user_id              varchar(64) not null comment '用户id',
   user_name            varchar(256) comment '用户名',
   acct_login           varchar(64) comment '登录名',
   pic                  varchar(256) comment '图标地址',
   session_id           varchar(64) not null comment '会话id',
   ts                   varchar(20) comment '时间戳',
   primary key (user_id, session_id)
)
   DEFAULT CHARACTER SET = utf8;




INSERT INTO ofVersion(name,version) values('tlim',1);

INSERT INTO ofProperty(name,propValue,encrypted) values('route.all-resources','true',0);



