# PostgreSQL

## Prerequisites:
* Installed PostgreSQL >= 14.5.1

## Create database

1. Create database, schema, tables.

Execute `create_database.sql` script in terminal using command:
```bash
psql -U postgres -d postgres -a -f path/to/script/create_database.sql
```

2. Create functions
Execute `change_period_func.sql` script in terminal using command:
```bash
psql -U postgres -d forex -a -f path/to/script/change_period_func.sql
```
