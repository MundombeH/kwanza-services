-- V5: alinhar o schema com as entidades e introduzir o perfil activo.

-- 1. Corrigir o typo no tipo de documento (se a coluna antiga ainda existir).
DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = 'public'
          AND table_name = 'documento_de_identificacao'
          AND column_name = 'tipo_docuemento'
    ) THEN
        ALTER TABLE public.documento_de_identificacao
            RENAME COLUMN tipo_docuemento TO tipo_documento;
    END IF;
END $$;

-- 2. Documento de identificação: substituir os campos textuais por referências.
ALTER TABLE public.documento_de_identificacao
    ADD COLUMN IF NOT EXISTS bairro_id uuid,
    ADD COLUMN IF NOT EXISTS provincia_id uuid;

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'fk_documento_bairro') THEN
        ALTER TABLE public.documento_de_identificacao
            ADD CONSTRAINT fk_documento_bairro
                FOREIGN KEY (bairro_id) REFERENCES public.bairros(id) ON DELETE SET NULL;
    END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'fk_documento_provincia') THEN
        ALTER TABLE public.documento_de_identificacao
            ADD CONSTRAINT fk_documento_provincia
                FOREIGN KEY (provincia_id) REFERENCES public.provincias(id) ON DELETE SET NULL;
    END IF;
END $$;

ALTER TABLE public.documento_de_identificacao
    DROP COLUMN IF EXISTS bairro,
    DROP COLUMN IF EXISTS provincia_de;

-- 3. Corrigir o typo na tabela de associação users_roles.
DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = 'public'
          AND table_name = 'users_roles'
          AND column_name = 'ususario_id'
    ) THEN
        ALTER TABLE public.users_roles
            RENAME COLUMN ususario_id TO usuario_id;
    END IF;
END $$;

ALTER TABLE public.usuario
    ADD COLUMN IF NOT EXISTS perfil_ativo varchar(30);
