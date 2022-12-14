DROP FUNCTION IF EXISTS "rest".change_period;
CREATE OR REPLACE FUNCTION "rest".change_period (
		_symbol_id INTEGER
		,_period_id INTEGER
		,_date_from INTEGER
		,_date_to INTEGER
		)
	RETURNS TABLE (
			start_ts_utc INTEGER
			,end_ts_utc INTEGER
			,OPEN INTEGER
			,high INTEGER
			,low INTEGER
			,CLOSE INTEGER
			,spread INTEGER
			,volume INTEGER
			) LANGUAGE plpgsql AS
$FUNC$
DECLARE
_period_minute INTEGER;
_period_hour INTEGER;
_period_day INTEGER;
_period_month INTEGER;
_period_interval interval;

BEGIN
    SELECT
        p.minute, p.hour, p.day, p.month, p.interval
    INTO
        _period_minute, _period_hour, _period_day, _period_month, _period_interval
    FROM rest.periods p
    WHERE id = _period_id;

	RETURN QUERY

	SELECT 
		extract(epoch FROM make_timestamptz(year::INT, month::INT, day::INT, hour::INT, minute::INT, 00.00))::INT AS start_ts_utc
		,extract(epoch FROM (make_timestamptz(year::INT, month::INT, day::INT, hour::INT, minute::INT, 00.00) + _period_interval))::INT AS end_ts_utc
		,(array_agg(t.open)) [1]::INT AS open
		,max(t.high)::INT AS high
		,min(t.low)::INT AS low
		,(array_agg(t.close)) [array_upper(array_agg(t.close), 1)]::INT AS close
		,(array_agg(t.spread)) [array_upper(array_agg(t.spread), 1)]::INT AS spread
		,sum(t.volume)::INT AS volume
	FROM (
		SELECT 
			c.start_ts_utc
			,date_part('year', to_timestamp(c.start_ts_utc))::INT AS year
			,(date_part('month', to_timestamp(c.start_ts_utc))::INT / _period_month * _period_month)::INT AS month
			,CASE
				WHEN (date_part('day', to_timestamp(c.start_ts_utc))::INT / _period_day * _period_day)::INT = 0
					THEN 1
				ELSE (date_part('day', to_timestamp(c.start_ts_utc))::INT / _period_day * _period_day)::INT
				END AS day
			,(date_part('hour', to_timestamp(c.start_ts_utc))::INT / _period_hour * _period_hour)::INT AS hour
			,(date_part('minute', to_timestamp(c.start_ts_utc))::INT / _period_minute * _period_minute)::INT AS minute
			,c.open
			,c.high
			,c.low
			,c.close
			,c.volume
			,c.spread
		FROM rest.candles c
		WHERE c.symbol_id = _symbol_id
			AND c.end_ts_utc BETWEEN _date_from
				AND _date_to
		ORDER BY c.start_ts_utc
		) t
	GROUP BY year, month, day, hour, minute, _period_interval
    ORDER BY start_ts_utc;
END $FUNC$;