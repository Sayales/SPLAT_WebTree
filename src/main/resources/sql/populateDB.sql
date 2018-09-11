DELETE FROM db_tree_objects;
ALTER SEQUENCE global_seq RESTART WITH 100;
INSERT INTO db_tree_objects (id, parent_id, text_value, children)
VALUES (1, NULL, 'First', TRUE ),
  (2,1, 'Second_1', FALSE ),
  (3, 1, 'Second_2', TRUE ),
  (4, 3, 'Third', FALSE );