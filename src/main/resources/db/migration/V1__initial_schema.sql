CREATE TABLE users (
    id SERIAL PRIMARY KEY,

    name VARCHAR(255) NOT NULL,

    email VARCHAR(255) NOT NULL UNIQUE,

    password VARCHAR(255) NOT NULL,

    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE messages (
    id BIGSERIAL PRIMARY KEY,

    name VARCHAR(255) NOT NULL,

    email VARCHAR(255) NOT NULL,

    phone VARCHAR(20) NOT NULL,

    message TEXT NOT NULL,

    mark_read BOOLEAN DEFAULT FALSE,

    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE gallery (
    id BIGSERIAL PRIMARY KEY,

    image VARCHAR(255) NOT NULL,

    title VARCHAR(255) NOT NULL,

    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE donations (
    id BIGSERIAL PRIMARY KEY,

    name VARCHAR(255) NOT NULL,

    email VARCHAR(255) NOT NULL,

    phone VARCHAR(20) NOT NULL,

    amount BIGINT NOT NULL,

    reference VARCHAR(255) UNIQUE,

    status VARCHAR(255) DEFAULT 'PENDING',

    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);


INSERT INTO users (
    name,
    email,
    password,
    created_at,
    updated_at
) VALUES (
             'Admin',
             'info@mamaclare.org',
             '$2a$12$Xn1yzNyoSW3Uj.n/KeDw.etk6xcyJ2BNSB.fLFfqNpMdUlnn7pqIy',
             CURRENT_TIMESTAMP,
             CURRENT_TIMESTAMP
         );




