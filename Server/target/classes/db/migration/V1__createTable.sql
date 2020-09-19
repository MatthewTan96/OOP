CREATE TABLE shipping_info (
        vessel_id VARCHAR(20) PRIMARY KEY,
        vessel_short_name VARCHAR(20),
        incoming_voyage_number VARCHAR(20),
        outgoing_voyage_number VARCHAR(20),
        berth_time_required VARCHAR(30),
        expected_datetime_departure VARCHAR(30),
        berth_number VARCHAR(20),
        status VARCHAR(20),
        change_count integer,
        degree_change float,
        first_birth_time VARCHAR(30)
);