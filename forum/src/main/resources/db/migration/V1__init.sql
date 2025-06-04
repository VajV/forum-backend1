-- Категории
CREATE TABLE category (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(50) NOT NULL UNIQUE
);

-- Посты
CREATE TABLE post (
                      id SERIAL PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      content TEXT,
                      category_id INTEGER REFERENCES category(id) ON DELETE CASCADE
);

-- Комментарии
CREATE TABLE comment (
                         id SERIAL PRIMARY KEY,
                         author VARCHAR(50) NOT NULL,
                         content VARCHAR(1000) NOT NULL,
                         created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                         post_id INTEGER REFERENCES post(id) ON DELETE CASCADE
);