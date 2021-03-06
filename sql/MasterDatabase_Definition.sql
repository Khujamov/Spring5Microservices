create user microservice with encrypted password 'microservice';

create database microservice with owner microservice;

-- To connect from Linux console using the new user
psql -d microservice -U microservice

create schema eat;

create table eat.user(
  id		serial		   not null   constraint user_pk primary key,
  name		varchar(128)   not null,
  active   	boolean        not null,
  password 	varchar(128)   not null,
  username 	varchar(64)    not null
);

create unique index user_username_uindex on eat.user(username);


create table eat.role(
  id     smallserial   not null   constraint role_pk primary key,
  name 	 varchar(64)   not null
);

create unique index role_role_uindex on eat.role(name);


create table eat.user_role(
  user_id   smallint 	not null	constraint user_role_user_id_fk references eat.user,
  role_id   int 		not null    constraint user_role_role_id_fk references eat.role,
  constraint user_role_pk primary key (user_id, role_id)
);

create index user_role_user_id_index on eat.user_role (user_id);


create table eat.ingredient(
  id	smallserial   not null   constraint ingredient_pk primary key,
  name 	varchar(64)   not null
);

create unique index ingredient_name_uindex on eat.ingredient (name);


create table eat.pizza(
  id     smallserial        not null   constraint pizza_pk primary key,
  name 	 varchar(64),
  cost   double precision   not null
);

create unique index pizza_name_uindex on eat.pizza (name);


create table eat.pizza_ingredient(
  pizza_id        smallint   not null   constraint pizza_ingredient_pizza_id_fk references eat.pizza,
  ingredient_id   smallint   not null   constraint pizza_ingredient_ingredient_id_fk references eat.ingredient,
  constraint pizza_ingredient_pk primary key (pizza_id, ingredient_id)
);


create table eat.order(
  id        serial                        not null   constraint order_pk primary key,
  code      varchar(64)                   not null,
  created   timestamp without time zone   not null
);

create unique index order_code_uindex on eat.order (code);


create table eat.order_line(
  id         serial             not null    constraint order_line_pk primary key,
  order_id   int                not null    constraint order_line_order_id_fk references eat.order,
  pizza_id   smallint           not null    constraint order_line_pizza_id_fk references eat.pizza,
  cost       double precision   not null,
  amount     smallint           not null
);


----------- SECURITY -----------

create schema security;

create table security.oauth_client_details (
  client_id                 varchar(64)     constraint oauth_client_details_pk primary key,
  resource_ids              varchar(256),
  client_secret             varchar(128)    not null,
  scope                     varchar(256),
  authorized_grant_types    varchar(256),
  web_server_redirect_uri   varchar(256),
  authorities               varchar(256),
  access_token_validity     int             not null,
  refresh_token_validity    int             not null,
  additional_information    text,
  autoapprove               varchar(256)
);


create table security.jwt_client_details (
  client_id                  varchar(64)    constraint jwt_client_details_pk primary key,
  client_secret              varchar(128)   not null,
  signature_secret           varchar(256)   not null,
  signature_algorithm        varchar(16)    not null,
  authentication_generator   varchar(128)   not null,
  token_type                 varchar(32)    not null,
  use_jwe                    boolean        not null,
  access_token_validity      int            not null,
  refresh_token_validity     int            not null
);
