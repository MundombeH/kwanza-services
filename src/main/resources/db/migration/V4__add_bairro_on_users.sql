ALTER TABLE IF EXISTS public.usuario_cliente
    add   COLUMN IF NOT  EXISTS bairro_id uuid;



-- 3. Adicionar FK
ALTER TABLE public.usuario_cliente
    ADD CONSTRAINT fk_usuario_cliente
    FOREIGN KEY (bairro_id) REFERENCES public.bairros(id) ON DELETE CASCADE;

ALTER TABLE IF EXISTS public.usuario_fornecedor
    add COLUMN IF NOT  EXISTS bairro_id uuid;



-- 3. Adicionar FK
ALTER TABLE public.usuario_fornecedor
    ADD CONSTRAINT fk_usuario_fornecedor
        FOREIGN KEY (bairro_id) REFERENCES public.bairros(id) ON DELETE CASCADE;