USE payments;

ALTER USER 'root' IDENTIFIED WITH mysql_native_password BY 'seed';

flush privileges;

CREATE TABLE IF NOT EXISTS payment (
	id int NOT NULL AUTO_INCREMENT,
	cardNumber int NOT NULL,
	value decimal(15,4) NOT NULL,
	status varchar(255) NOT NULL,
	createdAt datetime NOT NULL,
	version int NOT NULL,
    PRIMARY KEY(id)
);