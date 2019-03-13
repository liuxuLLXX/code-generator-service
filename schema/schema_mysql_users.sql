SET FOREIGN_KEY_CHECKS = 0;

-- -----------------------------------------------------------------------------------------------
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS sys_users;

create table sys_users
(
   id                   bigint(20) not null auto_increment,
   username             varchar(50) not null comment '用户名',
   name                 varchar(100) not null comment '姓名',
   password             varchar(250) not null comment '密码',
   enabled              tinyint(1) not null comment '是否有效',
   create_time          datetime not null comment '创建时间',
   update_time          datetime not null comment '更新时间',
   status               char(1) not null comment '状态',
   primary key (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

alter table sys_users comment '用户';
create unique index udx_users_username on sys_users(username);

-- -----------------------------------------------------------------------------------------------

DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS sys_user_authorities;

create table sys_user_authorities
(
   username             varchar(50) not null comment '用户名',
   authority            varchar(50) not null comment '权限',
   create_time          datetime not null comment '创建时间'
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

CREATE UNIQUE INDEX udx_user_authorities_username ON sys_user_authorities (username, authority);

-- -----------------------------------------------------------------------------------------------

DROP TABLE IF EXISTS groups;
DROP TABLE IF EXISTS sys_groups;

create table sys_groups
(
   id                   bigint(20) not null auto_increment,
   code                 varchar(50) not null comment '编码',
   name                 varchar(150) not null comment '名称',
   create_time          datetime not null comment '创建时间',
   update_time          datetime not null comment '更新时间',
   status               char(1) not null comment '状态',
   level                tinyint unsigned not null comment '级别:1-9',
   parent_id            bigint(20) comment '上级ID',
   primary key (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=0;

alter table sys_groups comment '用户组';
create unique index udx_groups_code on sys_groups(code);
-- -----------------------------------------------------------------------------------------------

DROP TABLE IF EXISTS group_authorities;
DROP TABLE IF EXISTS sys_group_authorities;

create table sys_group_authorities
(
   group_id             bigint(20) not null,
   authority            varchar(50) not null comment '权限',
   create_time          datetime not null comment '创建时间'
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

alter table sys_group_authorities comment '用户组权限';
create unique index udx_group_authorities on sys_group_authorities(group_id, authority);

-- -----------------------------------------------------------------------------------------------

DROP TABLE IF EXISTS group_members;
DROP TABLE IF EXISTS sys_group_members;

create table sys_group_members
(
   id                   bigint(20) not null auto_increment,
   username             varchar(50) not null comment '用户名',
   group_id             bigint(20) not null,
   create_time          datetime not null comment '创建时间',
   primary key (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=0;

alter table sys_group_members comment '用户组员';
create unique index udx_grop_members_ug on sys_group_members(username, group_id);
-- -----------------------------------------------------------------------------------------------

drop table if exists role;
drop table if exists sys_role;

create table sys_role
(
   code                 varchar(50) not null comment '编码',
   name                 varchar(150) not null comment '名称',
   create_time          datetime not null comment '创建时间',
   username             varchar(50) not null comment '创建人',
   primary key (code)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

alter table sys_role comment '角色';

-- -----------------------------------------------------------------------------------------------

drop table if exists resource;
drop table if exists sys_resource;

create table sys_resource
(
   id                   bigint(20) not null auto_increment,
   create_time          datetime not null comment '创建时间',
   name                 varchar(100) not null comment '名称',
   type                 varchar(50) not null comment '类型',
   url                  varchar(500) not null comment '资源路径',
   parent_id            bigint(20) comment '父级ID',
   primary key (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

alter table sys_resource comment '资源';

-- -----------------------------------------------------------------------------------------------

drop table if exists resource_authorities;
drop table if exists sys_resource_authorities;

create table sys_resource_authorities
(
   resource_id          bigint(20) not null comment '资源ID',
   authority            varchar(50) not null comment '权限',
   create_time          datetime not null comment '创建时间'
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

alter table sys_resource_authorities comment '访问资源所需权限';
create unique index udx_resource_authorities on sys_resource_authorities(resource_id, authority);

-- ACL Schema ------------------------------------------------------------------------------------------------
-- stores the security identities recognised by the ACL system.
-- These can be unique principals or authorities which may apply to multiple principals.
DROP TABLE IF EXISTS acl_sid;
DROP TABLE IF EXISTS sys_acl_sid;

CREATE TABLE sys_acl_sid (
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	principal BOOLEAN NOT NULL,
	sid VARCHAR(100) NOT NULL,
	UNIQUE KEY uk_sys_acl_sid (sid, principal)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- defines the domain object types to which ACLs apply.
-- The class column stores the Java class name of the object.
DROP TABLE IF EXISTS acl_class;
DROP TABLE IF EXISTS sys_acl_class;

CREATE TABLE sys_acl_class (
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	class VARCHAR(100) NOT NULL,
	class_id_type VARCHAR(100) NOT NULL,
	UNIQUE KEY uk_acl_class (class)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- stores the object identity definitions of specific domain objects
DROP TABLE IF EXISTS acl_object_identity;
DROP TABLE IF EXISTS sys_acl_object_identity;

CREATE TABLE sys_acl_object_identity (
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	object_id_class BIGINT UNSIGNED NOT NULL,
	object_id_identity VARCHAR(36) NOT NULL,
	parent_object BIGINT UNSIGNED,
	owner_sid BIGINT UNSIGNED,
	entries_inheriting BOOLEAN NOT NULL,
	UNIQUE KEY uk_sys_acl_object_identity (object_id_class, object_id_identity),
	CONSTRAINT fk_sys_acl_object_identity_parent FOREIGN KEY (parent_object) REFERENCES sys_acl_object_identity (id),
	CONSTRAINT fk_sys_acl_object_identity_class FOREIGN KEY (object_id_class) REFERENCES sys_acl_class (id),
	CONSTRAINT fk_sys_acl_object_identity_owner FOREIGN KEY (owner_sid) REFERENCES sys_acl_sid (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

--  stores the ACL permissions which apply to a specific object identity and security identity
DROP TABLE IF EXISTS acl_entry;
DROP TABLE IF EXISTS sys_acl_entry;

CREATE TABLE sys_acl_entry (
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	acl_object_identity BIGINT UNSIGNED NOT NULL,
	ace_order INTEGER NOT NULL,
	sid BIGINT UNSIGNED NOT NULL,
	mask INTEGER UNSIGNED NOT NULL,
	granting BOOLEAN NOT NULL,
	audit_success BOOLEAN NOT NULL,
	audit_failure BOOLEAN NOT NULL,
	UNIQUE KEY uk_sys_acl_entry (acl_object_identity, ace_order),
	CONSTRAINT fk_acl_entry_object FOREIGN KEY (acl_object_identity) REFERENCES sys_acl_object_identity (id),
	CONSTRAINT fk_acl_entry_acl FOREIGN KEY (sid) REFERENCES sys_acl_sid (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- ----------------------------------------------------------------------------------------------------------------------------
insert into sys_groups(id, code, name, create_time, update_time, status, level, parent_id)
values
(1, 'root', '根', now(), now(), 1, 1, null);

insert into sys_group_members(username, group_id, create_time)
values
('root', 1, now());

insert into sys_group_authorities(group_id, authority, create_time)
values
(1, 'ROLE_USER', now());

insert into sys_role(code, name, create_time, username)
values
('ROLE_ROOT', '超级管理员', now(), 'root'),
('ROLE_ADMIN', '管理员', now(), 'root'),
('ROLE_USER', '普通用户', now(), 'root');

-- root/123456
insert into sys_users(username, name, password, enabled, create_time, update_time, status)
values('root', '根用户', '{bcrypt}$2a$10$dOu.jNU1Dh1jOqUujRyMvO0kCKuXV7ZEyySFN40y4xu6gASKJbGQu', 1, now(), now(), '1');

insert into sys_user_authorities(username, authority, create_time)
values
('root', 'ROLE_ROOT', now()),
('root', 'ROLE_ADMIN', now()),
('root', 'ROLE_USER', now());
