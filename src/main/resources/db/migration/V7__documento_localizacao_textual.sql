-- V7: a localização do documento de identificação passa a ser textual.
--
-- O bairro e a província são lidos do BI/passaporte por OCR. Como o texto pode
-- não corresponder exactamente ao catálogo (acentos, abreviaturas, nomes antigos),
-- guarda-se o valor tal como aparece no documento.
--
-- As colunas bairro_id/provincia_id (V5) mantêm-se como referências opcionais
-- para uma futura resolução automática. Não são usadas pela entidade neste momento.

ALTER TABLE public.documento_de_identificacao
    ADD COLUMN IF NOT EXISTS bairro_de varchar(120),
    ADD COLUMN IF NOT EXISTS provincia_de varchar(120);

COMMENT ON COLUMN public.documento_de_identificacao.bairro_de IS
    'Bairro tal como aparece no documento (OCR), sem resolução de FK.';
COMMENT ON COLUMN public.documento_de_identificacao.provincia_de IS
    'Província tal como aparece no documento (OCR), sem resolução de FK.';
