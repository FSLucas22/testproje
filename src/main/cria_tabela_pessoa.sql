CREATE TABLE PESSOA(
	ID NUMBER GENERATED ALWAYS AS IDENTITY,
	NOME VARCHAR(200) NOT NULL,
	DATA_NASCIMENTO DATE NOT NULL,
	PRIMARY KEY(ID)
);
/

CREATE TABLE FUNCIONARIO(
	ID NUMBER GENERATED ALWAYS AS IDENTITY,
	NOME VARCHAR(200) NOT NULL,
	DATA_NASCIMENTO DATE NOT NULL,
	SALARIO
	PRIMARY KEY(ID)
)