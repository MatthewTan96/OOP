drop database if exists psa;

create database psa;
use psa;

CREATE TABLE IF NOT EXISTS UserAccount (
        email VARCHAR(30), 
        password VARCHAR(30),
        PRIMARY KEY(email)
    );
       

CREATE TABLE IF NOT EXISTS ShippingInfo (
        vesselName VARCHAR(20), 
        incomingVoyage VARCHAR(20),
        outgoingVoyage VARCHAR(20),
        bethingTime datetime,
        departureTime datetime, 
        berthNo VARCHAR(20),
        status VARCHAR(20),
        PRIMARY KEY(vesselName, incomingVoyage, outgoingVoyage)
    );

INSERT INTO UserAccount (email, password) VALUES ('Amy@outlook.com', '123');
INSERT INTO UserAccount (email, password) VALUES ('Bill@outlook.com', '123');
INSERT INTO UserAccount (email, password) VALUES ('Charles@outlook.com', '123');
INSERT INTO UserAccount (email, password) VALUES ('Doraemon@outlook.com', '123');

INSERT INTO ShippingInfo (vesselName, incomingVoyage, outgoingVoyage, bethingTime, departureTime, berthNo, status) 
    VALUES ('APL OREGON', '0FD0GE1M', '0FD0GE1M', '2020-07-14 12:55:00', '2020-07-15 07:40:00','P34','UNBERTHED');  
INSERT INTO ShippingInfo (vesselName, incomingVoyage, outgoingVoyage, bethingTime, departureTime, berthNo, status) 
    VALUES ('ASL TRANSPORTER 3', 'N20713', '0713N2', '2020-07-14 08:20:00', '2020-07-14 09:55:00','R1','UNBERTHED');    
INSERT INTO ShippingInfo (vesselName, incomingVoyage, outgoingVoyage, bethingTime, departureTime, berthNo, status) 
    VALUES ('ATAKA II', 'B6094', 'S9094', '2020-07-14 09:15:00', '2020-07-14 16:40:0','J1b','UNBERTHED');    