CREATE TABLE MEDICINES
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    manufacturer VARCHAR(255) NOT NULL,
    expiry_date  DATE         NOT NULL,
    stock        INT          NOT NULL
);