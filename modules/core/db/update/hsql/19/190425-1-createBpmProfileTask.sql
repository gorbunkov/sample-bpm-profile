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
    BPM_PROFILE_ID varchar(36),
    --
    primary key (ID)
);
