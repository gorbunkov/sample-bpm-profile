update BP_SALES_ORDER set DOC_NO = 0 where DOC_NO is null ;
alter table BP_SALES_ORDER alter column DOC_NO set not null ;
