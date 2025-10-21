CREATE TABLE "Content" (
  "id" SERIAL PRIMARY KEY,
  "name" varchar(64) UNIQUE NOT NULL,
  "description" text,
  "used_symbols" text UNIQUE NOT NULL
);

CREATE TABLE "Algorithm" (
  "id" SERIAL PRIMARY KEY,
  "name" varchar(16) UNIQUE NOT NULL,
  "key_size" int NOT NULL,
  "mode" varchar(16) NOT NULL
);

CREATE TABLE "User" (
  "id" SERIAL PRIMARY KEY,
  "username" varchar(255) UNIQUE NOT NULL,
  "email" varchar(255) UNIQUE NOT NULL,
  "password_hash" varchar(255) NOT NULL
);

CREATE TABLE "Tag" (
  "id" SERIAL PRIMARY KEY,
  "name" varchar(64) UNIQUE NOT NULL,
  "description" text,
  "color_hex" varchar(7) NOT NULL DEFAULT '#FFFFFF'
);

CREATE TABLE "Password" (
  "id" SERIAL PRIMARY KEY,
  "user_id" int NOT NULL,
  "encrypted_password" bytea NOT NULL,
  "content_id" int NOT NULL,
  "algorithm_id" int NOT NULL,
  "salt" varchar(255) NOT NULL,
  "length" int NOT NULL,
  "updated_at" timestamp NOT NULL
);

CREATE TABLE "UserTag" (
  "user_id" int NOT NULL,
  "tag_id" int NOT NULL,
  PRIMARY KEY ("user_id", "tag_id")
);

CREATE INDEX ON "Password" ("user_id");

ALTER TABLE "Password" ADD FOREIGN KEY ("user_id") REFERENCES "User" ("id");

ALTER TABLE "Password" ADD FOREIGN KEY ("content_id") REFERENCES "Content" ("id");

ALTER TABLE "Password" ADD FOREIGN KEY ("algorithm_id") REFERENCES "Algorithm" ("id");

ALTER TABLE "UserTag" ADD FOREIGN KEY ("user_id") REFERENCES "User" ("id");

ALTER TABLE "UserTag" ADD FOREIGN KEY ("tag_id") REFERENCES "Tag" ("id");


-- Insert values

insert into "Content" (name, description, used_symbols) values
('Letters', 'Latin letters only: A-Z, a-z', 'abcdefghijklmnopqrstuvwxyzABCDEFGIHJKLMNOPQRSTUVWXYZ'),
('Digits', 'Digits only: 0-9', '0123456789'),
('Special Chars', 'Special charachters only', '!@#$%^&*()_-+={[]};:''",<.>/?\|~`'),
('Letters, Digits', 'Letin letters and digits', 'abcdefghijklmnopqrstuvwxyzABCDEFGIHJKLMNOPQRSTUVWXYZ0123456789'),
('Letters, Special Chars', 'Latin letters and special characters', 'abcdefghijklmnopqrstuvwxyzABCDEFGIHJKLMNOPQRSTUVWXYZ!@#$%^&*()_-+={[]};:''",<.>/?\|~`'),
('Digits, Special Chars', 'Digits and special characters', '0123456789!@#$%^&*()_-+={[]};:''",<.>/?\|~`'),
('Mix', 'Latin letters, digits and special characters', 'abcdefghijklmnopqrstuvwxyzABCDEFGIHJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_-+={[]};:''",<.>/?\|~`');

insert into "Algorithm" (name, key_size, mode) values
('AES', 128, 'ECB'),
('DES', 56, 'ECB'),
('DESede', 168, 'ECB');
