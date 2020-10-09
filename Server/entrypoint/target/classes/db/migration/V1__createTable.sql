CREATE TABLE shipping_info (
        vessel_id UUID PRIMARY KEY NOT NULL,
        vessel_short_name VARCHAR(20) NOT NULL,
        incoming_voyage_number VARCHAR(20),
        outgoing_voyage_number VARCHAR(20),
        berth_time_required VARCHAR(30),
        expected_datetime_departure VARCHAR(30),
        berth_number VARCHAR(20),
        status VARCHAR(20),
        change_count integer,
        degree_change float,
        first_berth_time VARCHAR(30)
);

CREATE TABLE login_info (
    email VARCHAR(100) PRIMARY KEY NOT NULL,
    password VARCHAR(100) NOT NULL,
    verified integer
);

