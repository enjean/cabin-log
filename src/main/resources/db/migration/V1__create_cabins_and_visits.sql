CREATE TABLE IF NOT EXISTS cabins
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS visits
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR,
    cabin_id   INTEGER REFERENCES cabins (id),
    start_date DATE,
    end_date   DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);