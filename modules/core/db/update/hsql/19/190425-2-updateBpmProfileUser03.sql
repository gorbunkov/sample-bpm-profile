alter table BP_BPM_PROFILE_USER alter column USER_ID rename to USER_ID__U94568 ^
drop index IDX_BP_BPM_PROFILE_USER_ON_USER ;
alter table BP_BPM_PROFILE_USER drop constraint FK_BP_BPM_PROFILE_USER_ON_USER ;
