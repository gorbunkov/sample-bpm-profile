alter table BP_BPM_PROFILE_USER add constraint FK_BP_BPM_PROFILE_USER_ON_USER foreign key (USER_ID) references SEC_USER(ID);
create index IDX_BP_BPM_PROFILE_USER_ON_USER on BP_BPM_PROFILE_USER (USER_ID);
