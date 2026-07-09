 CREATE TABLE users (
	id TEXT PRIMARY KEY UNIQUE NOT NULL,
	login TEXT UNIQUE NOT NULL,
	password TEXT  NOT NULL,
	role TEXT NOT NULL,
    google_id TEXT UNIQUE,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ,
    created_by VARCHAR(255),
    updated_by VARCHAR(255)
 );