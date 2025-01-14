CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       name VARCHAR(100),
                       password VARCHAR(100),
                       email VARCHAR(100) UNIQUE
);

CREATE TABLE posts (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       image TEXT,
                       content TEXT,
                       date TIMESTAMP,
                       user_id UUID REFERENCES users(id)
);

CREATE TABLE likes (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       user_id UUID REFERENCES users(id),
                       post_id UUID REFERENCES posts(id)
);