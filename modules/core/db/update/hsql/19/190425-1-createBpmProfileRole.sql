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
);
