insert into groups (id, name) values (1, 'Gerente'), (2, 'Vendedor'), (3, 'Secretária'), (4, 'Cadastrador');

insert into permissions (id, name, description) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissions (id, name, description) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');
insert into permissions (id, name, description) values (3, 'CONSULTAR_FORMAS_PAGAMENTO', 'Permite consultar formas de pagamento');
insert into permissions (id, name, description) values (4, 'EDITAR_FORMAS_PAGAMENTO', 'Permite criar ou editar formas de pagamento');
insert into permissions (id, name, description) values (5, 'CONSULTAR_CIDADES', 'Permite consultar cidades');
insert into permissions (id, name, description) values (6, 'EDITAR_CIDADES', 'Permite criar ou editar cidades');
insert into permissions (id, name, description) values (7, 'CONSULTAR_ESTADOS', 'Permite consultar estados');
insert into permissions (id, name, description) values (8, 'EDITAR_ESTADOS', 'Permite criar ou editar estados');
insert into permissions (id, name, description) values (9, 'CONSULTAR_USUARIOS', 'Permite consultar usuários');
insert into permissions (id, name, description) values (10, 'EDITAR_USUARIOS', 'Permite criar ou editar usuários');
insert into permissions (id, name, description) values (11, 'CONSULTAR_RESTAURANTES', 'Permite consultar restaurantes');
insert into permissions (id, name, description) values (12, 'EDITAR_RESTAURANTES', 'Permite criar, editar ou gerenciar restaurantes');
insert into permissions (id, name, description) values (13, 'CONSULTAR_PRODUTOS', 'Permite consultar produtos');
insert into permissions (id, name, description) values (14, 'EDITAR_PRODUTOS', 'Permite criar ou editar produtos');
insert into permissions (id, name, description) values (15, 'CONSULTAR_PEDIDOS', 'Permite consultar pedidos');
insert into permissions (id, name, description) values (16, 'GERENCIAR_PEDIDOS', 'Permite gerenciar pedidos');
insert into permissions (id, name, description) values (17, 'GERAR_RELATORIOS', 'Permite gerar relatórios');


-- Adiciona permissoes no grupo do vendedor
insert into group_permission (group_id, permission_id)
select 2, id from permissions where name like 'CONSULTAR_%';

insert into group_permission (group_id, permission_id) values (2, 14);

-- Adiciona permissoes no grupo do auxiliar
insert into group_permission (group_id, permission_id)
select 3, id from permissions where name like 'CONSULTAR_%';

-- Adiciona permissoes no grupo cadastrador
insert into group_permission (group_id, permission_id)
select 4, id from permissions where name like '%_RESTAURANTES' or name like '%_PRODUTOS';
