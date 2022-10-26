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
		id 				BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
		symbol_id 		BIGINT NOT NULL UNIQUE,
		name 			TEXT NOT NULL UNIQUE,
		base_currency 	TEXT NOT NULL,
		quote_currency 	TEXT NOT NULL,
		digits 			INT NOT NULL,
		created_at 		TIMESTAMP WITH TIME ZONE NOT NULL,
		updated_at 		TIMESTAMP WITH TIME ZONE NOT NULL
		)
	CREATE TABLE periods(
		id 			BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
		period_id 	BIGINT NOT NULL UNIQUE,
		name 		TEXT NOT NULL UNIQUE,
		minute 		INT NOT NULL,
		hour 		INT NOT NULL,
		day 		INT NOT NULL, 
		month 		INT NOT NULL,
		interval 	TEXT NOT NULL,
        created_at 	TIMESTAMP WITH TIME ZONE NOT NULL,
        updated_at 	TIMESTAMP WITH TIME ZONE NOT NULL
		)
	CREATE TABLE bars(
		id 				BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
		symbol_id 		BIGINT NOT NULL,
		period_id		BIGINT NOT NULL,
		start_dt	 	TIMESTAMP WITH TIME ZONE NOT NULL,
		end_dt	 		TIMESTAMP WITH TIME ZONE NOT NULL,
		open 			INT NOT NULL,
		high 			INT NOT NULL,
		low 			INT NOT NULL,
		close 			INT NOT NULL,
		spread 			INT NOT NULL,
		volume 			INT NOT NULL,
		created_at 		TIMESTAMP WITH TIME ZONE NOT NULL,
		updated_at 		TIMESTAMP WITH TIME ZONE NOT NULL,
	 	FOREIGN KEY (symbol_id) REFERENCES symbols (symbol_id) ON DELETE CASCADE,
	 	FOREIGN KEY (period_id) REFERENCES periods (period_id),
	 	UNIQUE(symbol_id, start_ts_utc, end_ts_utc)
	 	)
	CREATE TABLE ticks(
		id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
		symbol_id BIGINT NOT NULL,
		ts_utc BIGINT NOT NULL,
		bid BIGINT NOT NULL,
		ask BIGINT NOT NULL,
		flags INT NOT NULL,
		FOREIGN KEY (symbol_id) REFERENCES symbols (symbol_id) ON DELETE CASCADE
		)
	;


INSERT INTO "rest".symbols (symbol_id, name, digits, created_at, updated_at)
VALUES
(1, "AUDCAD", "AUD", "CAD", 5, NOW(), NOW())
,(2, "AUDCHF", "AUD", "CHF", 5, NOW(), NOW())
,(3, "AUDJPY", "AUD", "JPY", 3, NOW(), NOW())
,(4, "AUDNZD", "AUD", "NZD", 5, NOW(), NOW())
,(5, "AUDUSD", "AUD", "USD", 5, NOW(), NOW())
,(6, "CADCHF", "CAD", "CHF", 5, NOW(), NOW())
,(7, "CADJPY", "CAD", "JPY", 3, NOW(), NOW())
,(8, "CHFJPY", "CHF", "JPY", 3, NOW(), NOW())
,(9, "EURAUD", "EUR", "AUD", 5, NOW(), NOW())
,(10, "EURCAD", "EUR", "CAD", 5, NOW(), NOW())
,(11, "EURCHF", "EUR", "CHF", 5, NOW(), NOW())
,(12, "EURGBP", "EUR", "GBP", 5, NOW(), NOW())
,(13, "EURJPY", "EUR", "JPY", 3, NOW(), NOW())
,(14, "EURNZD", "EUR", "NZD", 5, NOW(), NOW())
,(15, "EURUSD", "EUR", "USD", 5, NOW(), NOW())
,(16, "GBPAUD", "GBP", "AUD", 5, NOW(), NOW())
,(17, "GBPCAD", "GBP", "CAD", 5, NOW(), NOW())
,(18, "GBPCHF", "GBP", "CHF", 5, NOW(), NOW())
,(19, "GBPJPY", "GBP", "JPY", 3, NOW(), NOW())
,(20, "GBPNZD", "GBP", "NZD", 5, NOW(), NOW())
,(21, "GBPUSD", "GBP", "USD", 5, NOW(), NOW())
,(22, "NZDCAD", "NZD", "CAD", 5, NOW(), NOW())
,(23, "NZDCHF", "NZD", "CHF", 5, NOW(), NOW())
,(24, "NZDJPY", "NZD", "JPY", 3, NOW(), NOW())
,(25, "NZDUSD", "NZD", "USD", 5, NOW(), NOW())
,(26, "USDCAD", "USD", "CAD", 5, NOW(), NOW())
,(27, "USDCHF", "USD", "CHF", 5, NOW(), NOW())
,(28, "USDJPY", "USD", "JPY", 3, NOW(), NOW());

INSERT INTO "rest".periods (period_id, name, minute, hour, day, month, interval, created_at, updated_at)
VALUES
(1, 'M1', 1, 1, 1, 1, '1 minute', NOW(), NOW()),
(2, 'M5', 5, 1, 1, 1, '5 minutes', NOW(), NOW()),
(3, 'M10', 10, 1, 1, 1, '10 minutes', NOW(), NOW()),
(4, 'M15', 15, 1, 1, 1, '15 minutes', NOW(), NOW()),
(5, 'M30', 30, 1, 1, 1, '30 minutes', NOW(), NOW()),
(6, 'H1', 60, 1, 1, 1, '1 hour', NOW(), NOW()),
(7, 'H4', 60, 4, 1, 1, '4 hours', NOW(), NOW()),
(8, 'DAY', 60, 24, 1, 1, '1 day', NOW(), NOW()),
(9, 'MONTH', 60, 24, 31, 1, '1 month', NOW(), NOW());
