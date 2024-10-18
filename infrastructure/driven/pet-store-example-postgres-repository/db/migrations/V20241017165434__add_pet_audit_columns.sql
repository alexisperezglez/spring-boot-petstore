alter table pets
  add column created_by varchar(50);
alter table pets
  add column created_at timestamp default current_timestamp;
alter table pets
  add column updated_by varchar(50);
alter table pets
  add column updated_at timestamp default current_timestamp;
