CREATE TABLE IF NOT EXISTS public.documento_de_identificacao(
    id uuid PRIMARY KEY,
    is_active boolean,
    created_at timestamp,
    updated_at timestamp,
    nome_completo varchar(255),
    numero_do_bi varchar(255),
    nome_do_pai varchar(255),
    nome_da_mae varchar(255),
    bairro varchar(255),
    residencia varchar(255),
    natural_de varchar(255),
    provincia_de varchar(255),
    data_de_nascimento Date,
    sexo varchar(20) CHECK (sexo IN ('MASCULINO', 'FEMININO')),
    estado_civil varchar(100) CHECK (estado_civil IN ('SOLTEIRO', 'CASADO', 'DIVORCIADO', 'VIUVO','SOLTEIRA', 'CASADA', 'DIVORCIADA', 'VIUVA')),
    emitido_em DATE,
    valido_ate DATE,
    altura decimal,
    tipo_docuemento varchar(20) CHECK (tipo_docuemento IN ('BI', 'PASSAPORTE')),
    codigo_do_pais varchar(100),
    profissao varchar(100),
    numero_do_passaporte varchar(100),
    numero_pessoal varchar(100)
);

CREATE TABLE  IF NOT EXISTS public.usuario(
    id uuid PRIMARY KEY,
    nome varchar(255)  NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    numero_de_telefone VARCHAR(255) NOT NULL UNIQUE,
    is_active boolean,
    created_at timestamp,
    updated_at timestamp
);

CREATE TABLE IF NOT EXISTS public.usuario_cliente(
    id uuid PRIMARY KEY,
    is_active boolean,
    created_at timestamp,
    updated_at timestamp,
    sexo VARCHAR(20) CHECK (sexo IN ('MASCULINO', 'FEMININO')),
    usuario_id uuid NOT NULL UNIQUE,
    FOREIGN KEY (usuario_id) REFERENCES public.usuario(id) ON DELETE CASCADE

);

CREATE TABLE IF NOT EXISTS public.usuario_fornecedor(
    id uuid PRIMARY KEY,
    is_active boolean,
    created_at timestamp,
    updated_at timestamp,
    sexo VARCHAR(20) CHECK (sexo IN ('MASCULINO', 'FEMININO')),
    usuario_id uuid NOT NULL UNIQUE,
    identidade_validada boolean,
    documento_de_identificacao uuid,
    FOREIGN KEY (usuario_id) REFERENCES public.usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (documento_de_identificacao) REFERENCES public.documento_de_identificacao(id) ON DELETE CASCADE
    );


CREATE TABLE IF NOT EXISTS public.usuario_gestor(
    id uuid PRIMARY KEY,
    is_active boolean,
    created_at timestamp,
    updated_at timestamp,
    usuario_id uuid NOT NULL UNIQUE,
    numero_do_funcionario varchar(255) unique,
    identidade_validada boolean ,
    documento_de_identificacao uuid,
    FOREIGN KEY (usuario_id) REFERENCES public.usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (documento_de_identificacao) REFERENCES public.documento_de_identificacao(id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS public.usuario_admin(
    id uuid PRIMARY KEY,
    is_active boolean,
    created_at timestamp,
    updated_at timestamp,
    usuario_id uuid NOT NULL UNIQUE,
    FOREIGN KEY (usuario_id) REFERENCES public.usuario(id) ON DELETE CASCADE

    )