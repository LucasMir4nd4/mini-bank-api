CREATE TABLE customers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    email VARCHAR(150) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,

    CONSTRAINT uk_customer_cpf UNIQUE (cpf),
    CONSTRAINT uk_customer_email UNIQUE (email)
);
