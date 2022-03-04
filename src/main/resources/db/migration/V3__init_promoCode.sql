create table promo_code (
    id bigint not null auto_increment,
    discount integer not null,
    first_promo_code datetime,
    last_promo_code datetime,
    promo_code varchar(255),
    active_code bit not null,
    primary key (id)) engine=InnoDB;