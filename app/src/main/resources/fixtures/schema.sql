DROP TABLE IF EXISTS urls, url_checks;

CREATE TABLE urls (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    CONSTRAINT pk_urls PRIMARY KEY (id),
    name VARCHAR(255),
    created_at TIMESTAMP
);

CREATE TABLE url_checks (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    CONSTRAINT pk_url_checks PRIMARY KEY (id),
    url_id BIGINT,
    CONSTRAINT fk_url_id FOREIGN KEY(url_id) REFERENCES urls(id),
    status_code INT,
    h1 VARCHAR(255),
    title VARCHAR(255),
    description VARCHAR(4000),
    created_at TIMESTAMP
);
