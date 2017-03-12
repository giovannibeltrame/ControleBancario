conn system/admin;

drop sequence sequsuario;
drop sequence seqgerente;
drop sequence seqcliente;
drop sequence seqagencia;
drop sequence seqconta;
drop sequence seqtransacao;
drop table usuario cascade constraints;
drop table gerente cascade constraints;
drop table cliente cascade constraints;
drop table agencia cascade constraints;
drop table conta cascade constraints;
drop table transacao cascade constraints;


CREATE SEQUENCE SEQUSUARIO;
CREATE TABLE Usuario (
                idusuario INTEGER NOT NULL,
                cpf VARCHAR2(11) NOT NULL,
                nome VARCHAR2(64) NOT NULL,
                telefone VARCHAR2(16),
                login VARCHAR2(32) NOT NULL,
                senha VARCHAR2(256) NOT NULL,
                CONSTRAINT IDUSUARIO PRIMARY KEY (idusuario)
);

CREATE SEQUENCE SEQAGENCIA;
CREATE TABLE Agencia (
                idagencia INTEGER NOT NULL,
                nome VARCHAR2(32),
                telefone VARCHAR2(16),
                endereco VARCHAR2(128),
                CONSTRAINT IDAGENCIA PRIMARY KEY (idagencia)
);


CREATE SEQUENCE SEQGERENTE;
CREATE TABLE Gerente (
                idgerente INTEGER NOT NULL,
                idagencia INTEGER NOT NULL,
                idusuario INTEGER NOT NULL,
                CONSTRAINT IDGERENTE PRIMARY KEY (idgerente)
);


CREATE SEQUENCE SEQCLIENTE;
CREATE TABLE Cliente (
                idcliente INTEGER NOT NULL,
                idgerente INTEGER NOT NULL,
                idusuario INTEGER NOT NULL,
                CONSTRAINT IDCLIENTE PRIMARY KEY (idcliente)
);


CREATE SEQUENCE SEQCONTA;
CREATE TABLE Conta (
                idconta INTEGER NOT NULL,
                idagencia INTEGER NOT NULL,
                idcliente INTEGER NOT NULL,
                tipo CHAR(1) NOT NULL,
                CONSTRAINT PKCONTA PRIMARY KEY (idconta, idagencia)
);


CREATE SEQUENCE SEQTRANSACAO;
CREATE TABLE Transacao (
                idtransacao INTEGER NOT NULL,
                valor NUMBER(15,2) NOT NULL,
                tipo CHAR(1) NOT NULL,
                data TIMESTAMP NOT NULL,
                descricao VARCHAR2(128),
                juros NUMBER(15,2),
                idcontaorigem INTEGER,
                idagenciaorigem INTEGER,
                idcontadestino INTEGER,
                idagenciadestino INTEGER,
                CONSTRAINT IDTRANSACAO PRIMARY KEY (idtransacao)
);


ALTER TABLE Cliente ADD CONSTRAINT USUARIO_CLIENTE_FK
FOREIGN KEY (idusuario)
REFERENCES Usuario (idusuario)
NOT DEFERRABLE;

ALTER TABLE Gerente ADD CONSTRAINT USUARIO_GERENTE_FK
FOREIGN KEY (idusuario)
REFERENCES Usuario (idusuario)
NOT DEFERRABLE;

ALTER TABLE Gerente ADD CONSTRAINT AGENCIA_GERENTE_FK
FOREIGN KEY (idagencia)
REFERENCES Agencia (idagencia)
NOT DEFERRABLE;

ALTER TABLE Conta ADD CONSTRAINT AGENCIA_CONTA_FK
FOREIGN KEY (idagencia)
REFERENCES Agencia (idagencia)
NOT DEFERRABLE;

ALTER TABLE Cliente ADD CONSTRAINT GERENTE_CLIENTE_FK
FOREIGN KEY (idgerente)
REFERENCES Gerente (idgerente)
NOT DEFERRABLE;

ALTER TABLE Conta ADD CONSTRAINT CLIENTE_CONTA_FK
FOREIGN KEY (idcliente)
REFERENCES Cliente (idcliente)
NOT DEFERRABLE;

ALTER TABLE Transacao ADD CONSTRAINT CONTA_TRANSACAO_FK
FOREIGN KEY (idcontaorigem, idagenciaorigem)
REFERENCES Conta (idconta, idagencia)
NOT DEFERRABLE;

ALTER TABLE Transacao ADD CONSTRAINT CONTA_TRANSACAO_FK1
FOREIGN KEY (idcontadestino, idagenciadestino)
REFERENCES Conta (idconta, idagencia)
NOT DEFERRABLE;


create or replace function logar(plogin in varchar, psenha in varchar)
return integer
	is
		vid integer := 0;
	begin
		
		select idusuario into vid from usuario 
			where login = plogin and senha = psenha;
			
		return vid;
		
		EXCEPTION 
			WHEN OTHERS THEN vid := -1;
			
		return vid;
	end;
/

create or replace procedure incluir(pnome in varchar, pcpf in varchar, ptelefone in varchar, plogin in varchar, psenha in varchar,
mensagem out varchar)
as 
	total number;
begin
	
	select count(*) into total from usuario where login = plogin;
	
	if total > 0 then
		mensagem := 'Login ja cadastrado no sistema!';
	else
		insert into usuario values(sequsuario.nextval, pcpf, pnome, ptelefone, plogin, psenha);
		commit;
		
		mensagem := 'Usuario cadastrado!';
	end if;
end;
/

var msg varchar2(40);
exec incluir('Teste', '99999999999', '9999-9999', 'teste', '123', :msg);
select :msg from dual;

insert into agencia values(seqagencia.nextval, 'Agencia 1', '3333-3333', 'Av. Mal. Floriano Peixoto, 2660');
insert into gerente values(seqgerente.nextval, 1, 2);
commit;

alter table usuario modify cpf varchar(14);
alter table conta add saldo number(15,2) not null;


CREATE SEQUENCE SEQEMPRESTIMO;
CREATE TABLE Emprestimo (
                idemprestimo INTEGER NOT NULL,
                descricao VARCHAR2(64) NOT NULL,
                valortotal NUMBER(15,2) NOT NULL,
                valorparcela NUMBER(15,2) NOT NULL,
                juros NUMBER(15,2) NOT NULL,
                parcelas INTEGER NOT NULL,
                CONSTRAINT IDEMPRESTIMO PRIMARY KEY (idemprestimo)
);

alter table conta add parcelasapagar integer;


CREATE TABLE SaldoHistorico (
                idconta INTEGER NOT NULL,
                idagencia INTEGER NOT NULL,
                data TIMESTAMP NOT NULL,
                saldo NUMBER(15,2) NOT NULL,
                CONSTRAINT PKSALDOHISTORICO PRIMARY KEY (idconta, idagencia, data)
);

ALTER TABLE SaldoHistorico ADD CONSTRAINT CONTA_SALDOHISTORICO_FK
FOREIGN KEY (idconta, idagencia)
REFERENCES Conta (idconta, idagencia)
NOT DEFERRABLE;

commit;