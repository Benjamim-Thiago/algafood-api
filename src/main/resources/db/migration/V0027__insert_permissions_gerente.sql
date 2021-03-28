-- Adiciona permissoes no grupo do gerente
insert into group_permission (group_id, permission_id)
select 1, id from permissions;


