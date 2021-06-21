CREATE DATABASE my_db;

CREATE TABLE place (
                       id integer NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       number integer,
                       name varchar(25),
                       square NUMERIC(4,1),
                       floor integer,
                       type varchar(50)
);


CREATE TABLE meter (
                       id integer NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       meter_number varchar(25),
                       place_id integer REFERENCES place (id)
);


CREATE TABLE meter_data (
                            id integer NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                            meter_id integer REFERENCES meter(id),
                            data integer,
                            data_date date
);


CREATE TABLE renter (
                        id integer NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                        name varchar(50),
                        ogrn varchar(25),
                        inn varchar(25),
                        registr_date date,
                        address varchar(500),
                        director_name varchar(200),
                        contact_name varchar(200),
                        phone varchar(25)
) ;


CREATE TABLE contract (
                          id integer NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                          number varchar(25),
                          contract_date date,
                          fare integer,
                          start_date date,
                          finish_date date,
                          payment_day integer,
                          place_id integer REFERENCES place (id),
                          renter_id integer REFERENCES renter (id)
);


CREATE TABLE account (
                         id integer NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                         account_number varchar(25),
                         contract_id integer REFERENCES contract (id)
);


CREATE TABLE account_data (
                              id integer NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                              account_id integer REFERENCES account (id),
                              payment integer,
                              payment_date date,
                              payment_purpose varchar(1000)
);