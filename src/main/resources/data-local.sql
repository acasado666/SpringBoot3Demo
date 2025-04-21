-- INSERT INTO department (id, name, created_at, created_by, updated_at, updated_by)
-- VALUES (1, 'Engineering', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
--
-- INSERT INTO employee (id, name, department_id, created_at, created_by, updated_at, updated_by)
-- VALUES (1, 'Alice', 1, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin'),
--        (2, 'Bob', 1, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
--

-- Insert Departments
INSERT INTO department (id, name, created_at, created_by, updated_at, updated_by)
VALUES
    (1, 'Engineering', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'),
    (2, 'HR', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system');

-- Insert Employees
INSERT INTO employee (id, name, department_id, created_at, created_by, updated_at, updated_by)
VALUES
    (1, 'Alice Smith', 1, CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'),
    (2, 'Bob Johnson', 1, CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'),
    (3, 'Carol White', 2, CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system');
