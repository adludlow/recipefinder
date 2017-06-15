create sequence INGREDIENT_ID_SEQ
    start with 1
    increment by 1
    minvalue 1
    no maxvalue
    cache 1;

alter table INGREDIENT_ID_SEQ owner to recipe_admin;

create table INGREDIENT (
    ID bigint not null,
    CREATED_TIMESTAMP timestamp without time zone not null,
    UPDATED_TIMESTAMP timestamp without time zone not null,
    INGREDIENT character varying(255)
);
alter table INGREDIENT owner to recipe_admin;
create index on INGREDIENT (INGREDIENT);

create sequence IWA_ID_SEQ
    start with 1
    increment by 1
    minvalue 1
    no maxvalue
    cache 1;

CREATE TABLE INGREDIENT_WITH_AMOUNT (
    id bigint NOT NULL,
    created_timestamp timestamp without time zone NOT NULL,
    updated_timestamp timestamp without time zone NOT NULL,
    amount character varying(255),
    full_ingredient_text character varying(255),
    raw_ingredient character varying(255),
    uom character varying(255),
    ingredient_id bigint,
    recipe_id bigint NOT NULL
);
ALTER TABLE ingredient_with_amount OWNER TO recipe_admin;

create sequence method_step_id_seq
    start with 1
    increment by 1
    minvalue 1
    no maxvalue
    cache 1;

CREATE TABLE method_step (
    id bigint NOT NULL,
    step_text character varying(1000),
    recipe_id bigint
);


ALTER TABLE method_step OWNER TO recipe_admin;

create sequence recipe_id_seq
    start with 1
    increment by 1
    minvalue 1
    no maxvalue
    cache 1;

CREATE TABLE recipe (
    id bigint NOT NULL,
    created_timestamp timestamp without time zone NOT NULL,
    updated_timestamp timestamp without time zone NOT NULL,
    name character varying(255),
    url character varying(255)
);

ALTER TABLE recipe OWNER TO recipe_admin;


CREATE TABLE recipe_ingredients (
    recipe_id bigint NOT NULL,
    ingredient_id bigint NOT NULL
);

ALTER TABLE recipe_ingredients OWNER TO recipe_admin;

ALTER TABLE ONLY ingredient
    ADD CONSTRAINT ingredient_pkey PRIMARY KEY (id);

ALTER TABLE ONLY ingredient_with_amount
    ADD CONSTRAINT ingredient_with_amount_pkey PRIMARY KEY (id);

ALTER TABLE ONLY method_step
    ADD CONSTRAINT method_step_pkey PRIMARY KEY (id);

ALTER TABLE ONLY recipe
    ADD CONSTRAINT recipe_pkey PRIMARY KEY (id);

ALTER TABLE ONLY recipe_ingredients
    ADD CONSTRAINT fk_recipe_ingredients_ingredient FOREIGN KEY (ingredient_id) REFERENCES ingredient(id);

ALTER TABLE ONLY ingredient_with_amount
    ADD CONSTRAINT fk_iwa_recipe FOREIGN KEY (recipe_id) REFERENCES recipe(id);

ALTER TABLE ONLY recipe_ingredients
    ADD CONSTRAINT fk_ri_recipe FOREIGN KEY (recipe_id) REFERENCES recipe(id);

ALTER TABLE ONLY ingredient_with_amount
    ADD CONSTRAINT fk_iwa_ingredient FOREIGN KEY (ingredient_id) REFERENCES ingredient(id);

ALTER TABLE ONLY method_step
    ADD CONSTRAINT fk_ms_recipe FOREIGN KEY (recipe_id) REFERENCES recipe(id);
