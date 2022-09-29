SET TIMEZONE TO 'UTC';

DROP DATABASE IF EXISTS forex;
CREATE DATABASE forex
	WITH
	OWNER = postgres
	TEMPLATE = template0
	ENCODING = 'UTF8'
  	LC_COLLATE = 'en_US.UTF-8'
  	LC_CTYPE = 'en_US.UTF-8';

\c forex;

DROP SCHEMA IF EXISTS "rest" CASCADE;

CREATE SCHEMA "rest"
	CREATE TABLE symbols(
		id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
		name TEXT NOT NULL,
		digits INT NOT NULL,
		created_at TIMESTAMP WITH TIME ZONE NOT NULL,
		updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
		UNIQUE(name)
		)
	CREATE TABLE periods(
		id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
		name TEXT NOT NULL,
		minute INT NOT NULL,
		hour INT NOT NULL,
		day INT NOT NULL, 
		month INT NOT NULL,
		interval TEXT NOT NULL,
		UNIQUE(name) 
		)
	CREATE TABLE candles(
		id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
		symbol_id INT NOT NULL,
		start_ts_utc INT NOT NULL,
		end_ts_utc INT NOT NULL,
		open INT NOT NULL,
		high INT NOT NULL,
		low INT NOT NULL,
		close INT NOT NULL,
		spread INT NOT NULL,
		volume INT NOT NULL,
		created_at TIMESTAMP WITH TIME ZONE NOT NULL,
		updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
	 	FOREIGN KEY (symbol_id) REFERENCES symbols (id) ON DELETE CASCADE,
	 	UNIQUE(symbol_id, start_ts_utc, end_ts_utc)
	);


INSERT INTO "rest".symbols (name, digits, created_at, updated_at)
VALUES
('EURGBP', 5, NOW(), NOW())
,('EURJPY', 3, NOW(), NOW())
,('EURUSD', 5, NOW(), NOW())
,('GBPJPY', 3, NOW(), NOW())
,('GBPUSD', 5, NOW(), NOW())
,('USDJPY', 3, NOW(), NOW())
;

INSERT INTO "rest".periods (name, minute, hour, day, month, interval)
VALUES
('M1', 1, 1, 1, 1, '1 minute')
,('M5', 5, 1, 1, 1, '5 minutes')
,('M10', 10, 1, 1, 1, '10 minutes')
,('M15', 15, 1, 1, 1, '15 minutes')
,('M30', 30, 1, 1, 1, '30 minutes')
,('H1', 60, 1, 1, 1, '1 hour')
,('H4', 60, 4, 1, 1, '4 hours')
,('DAY', 60, 24, 1, 1, '1 day')
,('MONTH', 60, 24, 31, 1, '1 month')
;
