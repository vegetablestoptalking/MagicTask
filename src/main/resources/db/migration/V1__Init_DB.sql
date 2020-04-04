create table changes (
    id bigint not null,
    date_update date,
    description varchar(255),
    task_id bigint,
    primary key (id));



create table hibernate_sequence (next_val bigint);
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );


create table task (
    id bigint not null,
    description varchar(255),
    name_task varchar(255),
    user_id bigint, primary key (id));


create table task_changes (
    task_id bigint not null,
    changes_id bigint not null);


create table user (
    id bigint not null,
    first_name varchar(255),
    password varchar(255) not null,
    second_name varchar(255),
    username varchar(255) not null,
    primary key (id))
engine=InnoDB;


alter table task_changes add constraint UK_j42n8e7h30shjjlt87llmqdjr unique (changes_id);
alter table changes add constraint FKkloycr25ub6redspsc3bwe1y5 foreign key (task_id) references task (id);
alter table task add constraint FK2hsytmxysatfvt0p1992cw449 foreign key (user_id) references user (id);
alter table task_changes add constraint FK6sn9d3dnqqw0ei1bsrns38dgv foreign key (changes_id) references changes (id);
alter table task_changes add constraint FKgwxhwpjelavu70568madxb56b foreign key (task_id) references task (id);
