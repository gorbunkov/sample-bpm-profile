-- begin BP_PURCHASE_ORDER
create table BP_PURCHASE_ORDER (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    DOC_NO integer,
    DOC_DATE date,
    DESCRIPTION varchar(255),
    AMOUNT varchar(255),
    PROCESS_STATE integer,
    --
    primary key (ID)
)^
-- end BP_PURCHASE_ORDER
-- begin BP_SALES_ORDER
create table BP_SALES_ORDER (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    DOC_NO integer,
    DOC_DATE date,
    CUSTOMER varchar(255),
    AMOUNT decimal(19, 2),
    PROCESS_STATE integer,
    --
    primary key (ID)
)^
-- end BP_SALES_ORDER
-- begin BP_BPM_PROFILE
create table BP_BPM_PROFILE (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    DESCRIPTION varchar(255),
    PROC_MODEL_ID varchar(36),
    AD_HOC_USER_SELECTION boolean,
    ENTITY_NAME varchar(255),
    PROC_DEFINITION_ID varchar(36),
    DELEGATION_TARGET_GROUP integer,
    --
    primary key (ID)
)^
-- end BP_BPM_PROFILE
-- begin BP_BPM_PROFILE_USER
create table BP_BPM_PROFILE_USER (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    EXT_PROC_ROLE_ID varchar(36),
    LIMIT_AMOUNT decimal(19, 2),
    USER_ID varchar(36),
    BPM_PROFILE_ID varchar(36),
    --
    primary key (ID)
)^
-- end BP_BPM_PROFILE_USER
-- begin BP_BPM_PROFILE_ROLE
create table BP_BPM_PROFILE_ROLE (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    ROLE_ID varchar(36),
    PROC_ROLE_ID varchar(36),
    LIMIT_AMOUNT decimal(19, 2),
    BPM_PROFILE_ID varchar(36),
    --
    primary key (ID)
)^
-- end BP_BPM_PROFILE_ROLE
-- begin BP_BPM_PROFILE_TASK
create table BP_BPM_PROFILE_TASK (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    TENANT_ID varchar(255),
    --
    PROC_TASK_ID varchar(36),
    EMAIL_TEMPLATE_ID varchar(36),
    AMOUNT_FIELD_NAME varchar(255),
    SEND_LINK boolean,
    SEND_DOC_ATTACHMENT boolean,
    REMINDER_DAYS decimal(19, 2),
    ESCALATION_DAYS decimal(19, 2),
    BPM_PROFILE_ID varchar(36),
    --
    primary key (ID)
)^
-- end BP_BPM_PROFILE_TASK
-- begin SEC_ROLE
alter table SEC_ROLE add column TENANT_ID varchar(255) ^
alter table SEC_ROLE add column DTYPE varchar(100) ^
update SEC_ROLE set DTYPE = 'bp$ExtRole' where DTYPE is null ^
-- end SEC_ROLE
-- begin EMAILTEMPLATES_EMAIL_TEMPLATE
alter table EMAILTEMPLATES_EMAIL_TEMPLATE add column TENANT_ID varchar(255) ^
-- end EMAILTEMPLATES_EMAIL_TEMPLATE
-- begin BPM_PROC_ROLE
alter table BPM_PROC_ROLE add column TENANT_ID varchar(255) ^
alter table BPM_PROC_ROLE add column DTYPE varchar(100) ^
update BPM_PROC_ROLE set DTYPE = 'bp$ExtProcRole' where DTYPE is null ^
-- end BPM_PROC_ROLE
-- begin BPM_PROC_TASK
alter table BPM_PROC_TASK add column TENANT_ID varchar(255) ^
alter table BPM_PROC_TASK add column DTYPE varchar(100) ^
update BPM_PROC_TASK set DTYPE = 'bpm$ProcTask' where DTYPE is null ^
-- end BPM_PROC_TASK
-- begin BPM_PROC_MODEL
alter table BPM_PROC_MODEL add column TENANT_ID varchar(255) ^
alter table BPM_PROC_MODEL add column DTYPE varchar(100) ^
update BPM_PROC_MODEL set DTYPE = 'EXT' where DTYPE is null ^
-- end BPM_PROC_MODEL
