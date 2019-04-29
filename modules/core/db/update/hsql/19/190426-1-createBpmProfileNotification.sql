create table BP_BPM_PROFILE_NOTIFICATION (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TASK_ID varchar(255),
    EMAIL_TEMPLATE_ID varchar(36),
    BPM_PROFILE_ID varchar(36),
    --
    primary key (ID)
);