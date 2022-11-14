CREATE SCHEMA embroidery;

CREATE TABLE embroidery.roles
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL
);

INSERT INTO embroidery.roles (id, name)
VALUES (1, 'ADMIN'),
       (2, 'DESIGNER'),
       (3, 'USER');

CREATE TABLE embroidery.permissions
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL
);

INSERT INTO embroidery.permissions (id, name)
VALUES (1, 'OWNER'),
       (2, 'EDITOR'),
       (3, 'READER');

CREATE TABLE embroidery.users
(
    id           SERIAL PRIMARY KEY,
    username     VARCHAR(100) UNIQUE                      NOT NULL,
    first_name   VARCHAR(50),
    last_name    VARCHAR(50),
    image        BYTEA,
    phone_number VARCHAR(11),
    email        VARCHAR(100) UNIQUE                      NOT NULL,
    role_id      INTEGER REFERENCES embroidery.roles (id) NOT NULL,
    password     VARCHAR(100)                             NOT NULL
);

CREATE TABLE embroidery.designer_profiles
(
    id                SERIAL PRIMARY KEY,
    user_designer_id  INTEGER REFERENCES embroidery.users (id) ON DELETE CASCADE NOT NULL,
    experienced_since TIMESTAMP                                                  NOT NULL,
    description       VARCHAR(500)                                               NOT NULL
);

CREATE TABLE embroidery.folders
(
    id                  SERIAL PRIMARY KEY,
    name                VARCHAR(50)                                                NOT NULL,
    parent_folder_id    INTEGER REFERENCES embroidery.folders (id) ON DELETE CASCADE,
    creator_designer_id INTEGER REFERENCES embroidery.users (id) ON DELETE CASCADE NOT NULL
);

CREATE TABLE embroidery.designs
(
    id                  SERIAL PRIMARY KEY,
    name                VARCHAR(50)                                                  NOT NULL,
    file                BYTEA                                                        NOT NULL,
    folder_id           INTEGER REFERENCES embroidery.folders (id) ON DELETE CASCADE NOT NULL,
    creator_designer_id INTEGER REFERENCES embroidery.users (id) ON DELETE CASCADE   NOT NULL
);

CREATE TABLE embroidery.designers_designs
(
    id               SERIAL PRIMARY KEY,
    user_designer_id INTEGER REFERENCES embroidery.users (id) ON DELETE CASCADE       NOT NULL,
    design_id        INTEGER REFERENCES embroidery.designs (id) ON DELETE CASCADE     NOT NULL,
    permission_id    INTEGER REFERENCES embroidery.permissions (id) ON DELETE CASCADE NOT NULL
);

CREATE TABLE embroidery.placement_positions
(
    id                     SERIAL PRIMARY KEY,
    name                   VARCHAR(50)                         NOT NULL,
    anchor                 INTEGER DEFAULT 1                   NOT NULL,
    top_margin_position    DECIMAL DEFAULT 0,
    bottom_margin_position DECIMAL DEFAULT 0,
    left_margin_position   DECIMAL DEFAULT 0,
    right_margin_position  DECIMAL DEFAULT 0,
    height_percent         DECIMAL CHECK (height_percent <= 1) NOT NULL,
    width_percent          DECIMAL CHECK (width_percent <= 1)  NOT NULL
);

CREATE TABLE embroidery.model_photos
(
    id                    SERIAL PRIMARY KEY,
    file                  BYTEA                                                                    NOT NULL,
    design_id             INTEGER REFERENCES embroidery.designs (id) ON DELETE CASCADE             NOT NULL,
    placement_position_id INTEGER REFERENCES embroidery.placement_positions (id) ON DELETE CASCADE NOT NULL
);

CREATE TABLE embroidery.tags
(
    id    SERIAL PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    count INTEGER DEFAULT 0
);

CREATE TABLE embroidery.designs_tags
(
    tag_id    INTEGER REFERENCES embroidery.tags (id) ON DELETE CASCADE    NOT NULL,
    design_id INTEGER REFERENCES embroidery.designs (id) ON DELETE CASCADE NOT NULL,
    PRIMARY KEY (tag_id, design_id)
);

CREATE TABLE embroidery.placement_positions_tags
(
    tag_id                INTEGER REFERENCES embroidery.tags (id) ON DELETE CASCADE                NOT NULL,
    placement_position_id INTEGER REFERENCES embroidery.placement_positions (id) ON DELETE CASCADE NOT NULL,
    PRIMARY KEY (tag_id, placement_position_id)
);

CREATE TABLE embroidery.model_photos_tags
(
    tag_id         INTEGER REFERENCES embroidery.tags (id) ON DELETE CASCADE    NOT NULL,
    model_photo_id INTEGER REFERENCES embroidery.designs (id) ON DELETE CASCADE NOT NULL,
    PRIMARY KEY (tag_id, model_photo_id)
);

CREATE TABLE embroidery.posts
(
    id                SERIAL PRIMARY KEY,
    user_designer_id  INTEGER REFERENCES embroidery.users (id) ON DELETE CASCADE   NOT NULL,
    design_id         INTEGER REFERENCES embroidery.designs (id) ON DELETE CASCADE NOT NULL,
    description       VARCHAR(300)                                                 NOT NULL,
    creation_datetime TIMESTAMP                                                    NOT NULL
);

CREATE TABLE embroidery.comments
(
    id                SERIAL PRIMARY KEY,
    user_id           INTEGER REFERENCES embroidery.users (id) ON DELETE CASCADE NOT NULL,
    post_id           INTEGER REFERENCES embroidery.posts (id) ON DELETE CASCADE NOT NULL,
    text              VARCHAR(300)                                               NOT NULL,
    creation_datetime TIMESTAMP                                                  NOT NULL
);

CREATE TABLE embroidery.likes
(
    post_id    INTEGER REFERENCES embroidery.posts (id) ON DELETE CASCADE NOT NULL,
    user_id    INTEGER REFERENCES embroidery.users (id) ON DELETE CASCADE NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (post_id, user_id)
);

CREATE TABLE embroidery.jwt_tokens
(
    id       SERIAL PRIMARY KEY,
    token    VARCHAR(500)                                               NOT NULL,
    type     VARCHAR(50)                                                NOT NULL,
    provider VARCHAR(20) DEFAULT 'LOCAL'                                NOT NULL,
    user_id  INTEGER REFERENCES embroidery.users (id) ON DELETE CASCADE NOT NULL
);