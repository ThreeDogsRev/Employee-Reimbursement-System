CREATE TYPE employee_role AS ENUM (
    'EMPLOYEE', 
    'MANAGER', 
    'ADMIN'
);

CREATE TYPE reimbursement_status AS ENUM (
    'PENDING', 
    'APPROVED', 
    'DENIED'
);

CREATE TYPE reimbursement_type AS ENUM (
    'TRAVEL', 
    'LODGING', 
    'FOOD',
    'OTHER'
);