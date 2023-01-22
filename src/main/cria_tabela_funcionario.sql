CREATE TABLE FUNCIONARIO(
	ID NUMBER GENERATED ALWAYS AS IDENTITY,
	NOME VARCHAR(200) NOT NULL,
	DATA_NASCIMENTO DATE NOT NULL,
	SALARIO NUMBER(8, 2) NOT NULL,
	FUNCAO VARCHAR2(400) NOT NULL,
	PRIMARY KEY(ID)
);