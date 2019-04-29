update BP_BPM_PROFILE set ENTITY_NAME = '' where ENTITY_NAME is null ;
alter table BP_BPM_PROFILE alter column ENTITY_NAME set not null ;
-- update BP_BPM_PROFILE set PROC_DEFINITION_ID = <default_value> where PROC_DEFINITION_ID is null ;
alter table BP_BPM_PROFILE alter column PROC_DEFINITION_ID set not null ;
