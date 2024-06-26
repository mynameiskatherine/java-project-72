DROP TABLE IF EXISTS urls, url_checks;

CREATE TABLE urls (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    created_at TIMESTAMP
);

CREATE TABLE url_checks (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    url_id BIGINT REFERENCES urls(id),
    status_code INT,
    h1 VARCHAR(255),
    title VARCHAR(255),
    description VARCHAR(4000),
    created_at TIMESTAMP
);
