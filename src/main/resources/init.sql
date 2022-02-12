CREATE TABLE changelogs(change_logid bigserial primary key, date_created timestamp without time zone NOT NULL, date_modified timestamp without time zone NOT NULL, inserted_by bigint NOT NULL, narration varchar(200) NOT NULL);
CREATE TABLE roles(roleid bigserial primary key,active integer NOT NULL,date_created timestamp without time zone,date_modified timestamp without time zone,description varchar(150) NOT NULL,inserted_by integer,role_name varchar(50) NOT NULL,updated_by integer);
CREATE TABLE clients(clientid bigserial primary key, active integer, client_desc varchar(255), client_logo varchar(255), client_name varchar(255), date_created timestamp without time zone, date_modified timestamp without time zone, inserted_by integer, password_expiry_age integer, updated_by integer);
CREATE TABLE users(userid bigserial primary key, active varchar(100), authorities varchar(100), can_access_ui varchar(100),clientid bigint,created_by integer,date_activated timestamp without time zone,date_created timestamp without time zone,date_modified timestamp without time zone,date_password_changed timestamp without time zone,email_address varchar(100),full_name varchar(100),id_number varchar(100),is_not_locked integer,last_login_date timestamp without time zone,last_login_display_date timestamp without time zone,msisdn varchar(100),password varchar(100),password_attempts integer,roleid integer,updated_by integer,user_name varchar(100),CONSTRAINT fkbmanbod0d65fkdnlcs4s56qv6 FOREIGN KEY (clientid) REFERENCES public.clients (clientid) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE);
CREATE TABLE requesttypes(request_typeid bigserial primary key,active integer NOT NULL,created_by bigint NOT NULL,date_created timestamp without time zone,date_modified timestamp without time zone,request_type_name varchar(200) NOT NULL,updated_by bigint NOT NULL);
CREATE TABLE expiryperiod(expiry_periodid bigserial primary key,active integer,created_by bigint,date_created timestamp without time zone,date_modified timestamp without time zone,expiry_period integer NOT NULL,updated_by bigint);

insert into roles(active, description, role_name, inserted_by, updated_by) values(1, 'admin role','ROLE_ADMIN',1,1);
insert into roles(active, description, role_name, inserted_by, updated_by) values(1, 'editor role','ROLE_EDITOR',1,1);

insert into clients(active, client_desc, client_logo, client_name, inserted_by, updated_by,date_created, date_modified, password_expiry_age)values(1, 'hello','hello', 'CELLULANT',1,1,now(),now(), 1);
insert into clients(active, client_desc, client_logo, client_name, inserted_by, updated_by,date_created, date_modified, password_expiry_age)values(2, 'hello2','hello2', 'KCB',1,1,now(),now(), 1);

insert into users(active, can_access_ui, clientid, created_by, email_address, full_name, id_number, is_not_locked, msisdn, password, password_attempts, roleid, updated_by, user_name) values ('Active', 'Yes', 1, 1,'admin@gmail.com', 'admin',2222223,0, 3232321,'$2a$10$FJHr4e7lpwl04g7BL1oE0uTgpqBRdU4n4Hw8nHlOZ3Feb2kk3AxJC',0,1,0,'admin');
insert into users(active, can_access_ui, clientid, created_by, email_address, full_name, id_number, is_not_locked, msisdn, password, password_attempts, roleid, updated_by, user_name) values ('Active', 'Yes', 1, 1,'edit@gmail.com', 'edit',2222224,0, 3232322,'$2a$10$FJHr4e7lpwl04g7BL1oE0uTgpqBRdU4n4Hw8nHlOZ3Feb2kk3AxJC',0,1,0,'edit');

CREATE TABLE users_roles(user_userid bigint NOT NULL,roles_roleid integer NOT NULL,CONSTRAINT fk8wvn2euuxv9igsp8jh5ehu1mt FOREIGN KEY (roles_roleid) REFERENCES public.roles (roleid) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION, CONSTRAINT fka447bcrqmmjlpn3gnn89gstbl FOREIGN KEY (user_userid) REFERENCES public.users (userid) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE);
--insert into roles(active, description, role_name, inserted_by, updated_by) values(1, 'admin role','ROLE_ADMIN',1,1);
--insert into roles(active, description, role_name, inserted_by, updated_by) values(2, 'user role','USER_ADMIN',1,1);

insert into users_roles(user_userid, roles_roleid) values (1,1);
insert into users_roles(user_userid, roles_roleid) values (2,2);
