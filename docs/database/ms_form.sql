# CREATE DATABASE ms_form;

DROP TABLE IF EXISTS ms_form.define;
CREATE TABLE ms_form.define
(
    id      bigint       NOT NULL,
    name    varchar(255) NOT NULL,
    form_id bigint       NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS ms_form.component;
CREATE TABLE ms_form.component
(
    id         bigint        NOT NULL,
    root_id    bigint        NOT NULL,
    parent_id  bigint        NULL,
    name       varchar(255)  NOT NULL,
    type       varchar(40)   NOT NULL,
    properties varchar(1600) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (root_id) REFERENCES ms_form.component (id) ON DELETE CASCADE,
    FOREIGN KEY (parent_id) REFERENCES ms_form.component (id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS ms_form.data_table;
CREATE TABLE ms_form.data_table
(
    form_id  bigint NOT NULL,
    table_id bigint NOT NULL,
    PRIMARY KEY (form_id, table_id)
);

DROP TABLE IF EXISTS ms_form.data_relation;
CREATE TABLE ms_form.data_relation
(
    form_id     bigint NOT NULL,
    relation_id bigint NOT NULL,
    PRIMARY KEY (form_id, relation_id)
);