--table for client
drop table if exists clients;

create table clients(
	id_client int, --разобраться с генерацией
	last_name varchar(50),
	first_name varchar(50),
	patronymic varchar(50),
	email varchar(50),
	telephone varchar(20),
	constraint all_clients primary key(telephone)
);

select * from clients;

--table for postal_office
drop table if exists postal_offices;

create table postal_offices (
	id_office int, --?
	num_office int,
	description_office text
);

select * from postal_offices;
--table for packages
drop table if exists packages;

create table packages ();


--table for messages