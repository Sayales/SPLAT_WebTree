
DROP TABLE IF EXISTS db_tree_objects;
DROP TABLE IF EXISTS db_tree_object;
DROP SEQUENCE IF EXISTS global_seq CASCADE;

CREATE SEQUENCE global_seq START 100;

CREATE TABLE db_tree_objects (
  id INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  parent_id INTEGER,
  text_value VARCHAR(255),
  children boolean
);
