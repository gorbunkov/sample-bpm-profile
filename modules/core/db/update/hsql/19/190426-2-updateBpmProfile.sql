alter table BP_BPM_PROFILE alter column PROC_MODEL_ID rename to PROC_MODEL_ID__U43064 ^
drop index IDX_BP_BPM_PROFILE_ON_PROC_MODEL ;
alter table BP_BPM_PROFILE drop constraint FK_BP_BPM_PROFILE_ON_PROC_MODEL ;
