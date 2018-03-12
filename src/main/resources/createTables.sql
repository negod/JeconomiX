CREATE TABLE IF NOT EXISTS bill_category (
	id VARCHAR(36) NOT NULL, 
	updatedDate TIMESTAMP NOT NULL, 
	name VARCHAR(36) NOT NULL,
	PRIMARY KEY (id), 	
	UNIQUE (name) 
);

CREATE TABLE IF NOT EXISTS expense_category (
	id VARCHAR(36) NOT NULL, 
	updatedDate TIMESTAMP NOT NULL, 
	name VARCHAR(36) NOT NULL,
	PRIMARY KEY (id), 	
	UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS company_type(
    id VARCHAR(36) NOT NULL, 
    updatedDate TIMESTAMP NOT NULL, 
    name VARCHAR(36) NOT NULL, 
    PRIMARY KEY (id), 
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS company (
    id VARCHAR(36) NOT NULL, 
    updatedDate TIMESTAMP NOT NULL, 
    name VARCHAR(200) NOT NULL, 
    bill_category_id VARCHAR(36) NULL, 
    expense_category_id VARCHAR(36) NULL, 
    company_type_id VARCHAR(36) NULL, 
    PRIMARY KEY (id), 
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS transaction_type(
    id VARCHAR(36) NOT NULL, 
    updatedDate TIMESTAMP NOT NULL, 
    name VARCHAR(36)  NOT NULL, 
    PRIMARY KEY (id), 
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS company_transaction(
    id VARCHAR(36) NOT NULL, 
    updatedDate TIMESTAMP NOT NULL, 
    transDate DATE NOT NULL, 
    sum DECIMAL NOT NULL,
    saldo DECIMAL NULL,
    company_id VARCHAR(36) NOT NULL,
    transaction_type_id VARCHAR(36) NULL,
    PRIMARY KEY (id)
);

ALTER TABLE company ADD FOREIGN KEY (bill_category_id) REFERENCES bill_category (id);
ALTER TABLE company ADD FOREIGN KEY (expense_category_id) REFERENCES expense_category (id);
ALTER TABLE company ADD FOREIGN KEY (company_type_id) REFERENCES company_type (id);
ALTER TABLE company_transaction ADD FOREIGN KEY (company_id) REFERENCES company (id);
ALTER TABLE company_transaction ADD FOREIGN KEY (transaction_type_id) REFERENCES transaction_type (id);





