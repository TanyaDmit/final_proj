--table for client
drop table if exists clients;

drop sequence if exists clients_id;

create sequence clients_id start 100000001;

create table clients(
	id_client bigint NOT NULL DEFAULT nextval('clients_id'), --разобраться с генерацией
	last_name varchar(50),
	first_name varchar(50),
	patronymic varchar(50),
	email varchar(50),
	telephone varchar(20) primary key
);

select * from clients;

--table for postal_office
drop table if exists postal_offices;

drop sequence if exists postal_offices_id;

create sequence postal_offices_id start 10001;

create table postal_offices (
	id_office bigint NOT NULL DEFAULT nextval('postal_offices_id'),
	num_office int primary key,
	description_office text
);

select * from postal_offices;

--table for packages
drop table if exists packages;

drop sequence if exists packages_id;

create sequence packages_id start 10000000001;

create table packages(
	id_package bigint not null default nextval('packages_id'),
	
);

create table packages ();

--table for messages


























