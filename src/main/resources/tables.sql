CREATE TABLE url_info (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    url VARCHAR(255),
    url_type VARCHAR(255),
    album VARCHAR(255),
    created TIMESTAMP
);

CREATE TABLE albums (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created TIMESTAMP NOT NULL
);