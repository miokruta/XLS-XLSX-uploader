  CREATE TABLE "spreadsheet_files" (
  "file_id" SERIAL PRIMARY KEY,
  "name" varchar(255) NOT NULL,
  "file_content" bytea NOT NULL,
  "extension" varchar(8) NOT NULL,
  UNIQUE  (file_id, name)
);
