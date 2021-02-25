
delete from order_info_order_element_list;
delete from order_element;
delete from order_info;

insert into order_info (order_id,  customer_building, customer_city, customer_country, customer_email,
 first_name, last_name, order_date, payment_method, postcode, customer_room, shipping_type, status, customer_street, order_total)
values (1,  '143', 'Moscow', 'Russia', 'john@mail.com', 'John', 'Phillips', null, 'Card', 198398, '115',
 'Courier', 'ok', 'anyStreet', 240.0);
