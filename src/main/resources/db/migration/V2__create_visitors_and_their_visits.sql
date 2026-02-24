CREATE TABLE IF NOT EXISTS visitors
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR,
    cabin_id   INTEGER REFERENCES cabins (id), -- Visitor exists per-cabin
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS visit_visitors
(
    id         SERIAL PRIMARY KEY,
    visit_id   INTEGER REFERENCES visits (id),
    visitor_id INTEGER REFERENCES visitors (id),
    UNIQUE (visit_id, visitor_id)
);

CREATE TABLE IF NOT EXISTS visit_visitor_periods
(
    id               SERIAL PRIMARY KEY,
    visit_visitor_id INTEGER REFERENCES visit_visitors (id),
    start_date       DATE NOT NULL,
    end_date         DATE NOT NULL
)
