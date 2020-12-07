alter table employees add created_at timestamp;
alter table employees add last_modified_at timestamp;
alter table employees add created_by varchar2(255);
alter table employees add last_modified_by varchar2(255);