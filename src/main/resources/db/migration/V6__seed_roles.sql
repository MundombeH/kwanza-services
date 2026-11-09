-- Roles base do sistema.
-- Idempotente: pode correr sobre uma BD que já tenha algumas roles criadas pelo PerfilService.
INSERT INTO public.roles (id, descricao, is_active, created_at, updated_at)
VALUES
    (gen_random_uuid(), 'ROLE_CLIENTE', true, now(), now()),
    (gen_random_uuid(), 'ROLE_FORNECEDOR', true, now(), now()),
    (gen_random_uuid(), 'ROLE_GESTOR', true, now(), now()),
    (gen_random_uuid(), 'ROLE_ADMIN', true, now(), now())
ON CONFLICT (descricao) DO NOTHING;
