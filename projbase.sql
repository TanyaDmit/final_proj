drop table if exists packages;

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
--drop table if exists packages;

drop sequence if exists packages_id;

create sequence packages_id start 10000000001;

create table packages(
	id_package bigint primary key not null default nextval('packages_id'),
	telephone_sender varchar(20) references clients(telephone),
	num_office_recipient bigint references postal_offices(num_office),
	telephone varchar(20),
	last_name varchar(50),
	first_name varchar(50),
	patronymic varchar(50),
	status varchar(20),
	date_of_create timestamp,
	date_change_status timestamp not null default now()
);

select * from packages;


--table for messages
drop table if exists messages;

drop sequence if exists messages_id;

create sequence messages_id start 10000000001;

create table messages(
	messages_id bigint not null default nextval('messages_id'),
	num_package bigint references packages(id_package),
	text_message text,
	status varchar(20)
);

select * from messages;
