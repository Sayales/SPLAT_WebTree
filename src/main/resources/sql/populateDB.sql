DELETE FROM db_tree_objects;
ALTER SEQUENCE global_seq RESTART WITH 1000;
INSERT INTO db_tree_objects (id, parent_id, text_value)
VALUES (1, NULL, 'First'),
  (2,1, 'Second_1'),
  (3, 1, 'Second_2'),
  (4, 3, 'Third');