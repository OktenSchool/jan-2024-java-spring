update products set temp_availability = 0 where availability = 'AVAILABLE';
update products set temp_availability = 1 where availability = 'OUT_OF_STOCK';
update products set temp_availability = 2 where availability = 'DISCONTINUED';
update products set temp_availability = 3 where availability = 'NOT_AVAILABLE';