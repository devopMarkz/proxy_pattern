CREATE TABLE tb_user (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE
);

CREATE TABLE tb_role (
    id BIGSERIAL PRIMARY KEY,
    role VARCHAR(255) NOT NULL
);

CREATE TABLE tb_role_user (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,

    PRIMARY KEY (user_id, role_id),

    CONSTRAINT fk_user
      FOREIGN KEY (user_id)
          REFERENCES tb_user(id)
          ON DELETE CASCADE,

    CONSTRAINT fk_role
      FOREIGN KEY (role_id)
          REFERENCES tb_role(id)
          ON DELETE CASCADE
);