create table categories (
    id bigint not null auto_increment,
    name_category varchar(255),
    primary key (id)) engine=InnoDB;

create table categories_market (
    market_id bigint not null,
    categories_id bigint not null) engine=InnoDB;

create table market (
    id bigint not null auto_increment,
    filename varchar(255),
    full_text varchar(255),
    name varchar(255),
    price float not null,
    primary key (id)) engine=InnoDB;

create table order_details (
    id_order bigint not null auto_increment,
    client_data varchar(255),
    data_order datetime,
    final_price double precision,
    order_details varchar(255),
    status_order varchar(255),
    user_id bigint,
    primary key (id_order)) engine=InnoDB;

create table user_role (
    user_id bigint not null,
    roles varchar(255)) engine=InnoDB;

create table usr (
    id bigint not null auto_increment,
    activation_code varchar(255),
    active bit not null,
    email varchar(255),
    password varchar(255),
    recovery_code varchar(255),
    username varchar(255),
    primary key (id)) engine=InnoDB;

alter table categories_market
    add constraint FK1x4834csnfjq1qnw6euslijbs
        foreign key (categories_id) references categories (id);

alter table categories_market
    add constraint FKcfj67ijobtbm7xom6920lcswi
        foreign key (market_id) references market (id);

alter table order_details
    add constraint FK3oxf38i4p4mtohuo5t5dr1ajb
        foreign key (user_id) references usr (id);

alter table user_role
    add constraint FKfpm8swft53ulq2hl11yplpr5
        foreign key (user_id) references usr (id);