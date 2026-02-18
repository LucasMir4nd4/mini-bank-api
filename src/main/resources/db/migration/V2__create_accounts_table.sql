CREATE TABLE accounts (
    id BIGSERIAL PRIMARY KEY,
    account_number VARCHAR(50) NOT NULL UNIQUE,
    balance DOUBLE PRECISION NOT NULL,
    active BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL,

    customer_id BIGINT NOT NULL,

    CONSTRAINT fk_account_customer
        FOREIGN KEY (customer_id)
        REFERENCES customers(id)
        ON DELETE CASCADE
);