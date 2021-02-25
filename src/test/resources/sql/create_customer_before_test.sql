
delete from customer;

-- password: 12345678
insert into customer (customer_id, active, building, city, country, customer_date_of_birth,
 customer_email_address, customer_first_name, customer_last_name, customer_password, customer_phone_number,
  postcode, customer_role, room, street)
values (12, true, null, null, null, null, 'test@mail.com', 'Ivan', 'Ivanov',
 '$2a$08$eApn9x3qPiwp6cBVRYaDXed3J/usFEkcZbuc3FDa74bKOpUzHR.S.', null, null, 'ADMIN', null, null);

insert into customer (customer_id, active, building, city, country, customer_date_of_birth,
 customer_email_address, customer_first_name, customer_last_name, customer_password, customer_phone_number,
  postcode, customer_role, room, street)
values (11, true, null, 'Moscow', 'Russia', null, 'john@mail.com', 'John', 'Phillips',
 '$2a$08$eApn9x3qPiwp6cBVRYaDXed3J/usFEkcZbuc3FDa74bKOpUzHR.S.', null, null, 'CUSTOMER', null, null);
