CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,

    from_account_id BIGINT NOT NULL,
    to_account_id BIGINT NOT NULL,

    amount NUMERIC(19,2) NOT NULL,

    status VARCHAR(20) NOT NULL,

    description VARCHAR(255),

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_transaction_from_account
        FOREIGN KEY (from_account_id)
        REFERENCES accounts (id)
        ON DELETE RESTRICT,

    CONSTRAINT fk_transaction_to_account
        FOREIGN KEY (to_account_id)
        REFERENCES accounts (id)
        ON DELETE RESTRICT
);