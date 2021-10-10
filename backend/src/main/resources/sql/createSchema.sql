DROP TABLE IF EXISTS horse; -- FIXME: remove line
DROP TABLE IF EXISTS food;


CREATE TABLE IF NOT EXISTS food
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(255) NOT NULL,
    description     VARCHAR(255),
    calories        DOUBLE
);

CREATE TABLE IF NOT EXISTS horse
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(255) NOT NULL,
    description     VARCHAR(255),
    dob             DATE NOT NULL,
    sex             VARCHAR(255) NOT NULL CHECK (sex in ('Male', 'Female')),
    foodId          BIGINT,

    FOREIGN KEY (foodId) REFERENCES food(id)
);
