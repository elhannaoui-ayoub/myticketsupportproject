ALTER SESSION SET CONTAINER = FREEPDB1;
CREATE TABLE users (
    id NUMBER GENERATED ALWAYS AS IDENTITY(START with 1 INCREMENT by 1) PRIMARY KEY,
    username VARCHAR2(255) NOT NULL UNIQUE,
    password VARCHAR2(255) NOT NULL,
    role VARCHAR2(20) CHECK (role IN ('EMPLOYEE', 'IT_SUPPORT')) NOT NULL
);
-- Tickets table
CREATE TABLE tickets (
    id NUMBER GENERATED ALWAYS AS IDENTITY(START with 1 INCREMENT by 1) PRIMARY KEY,
    title VARCHAR2(255) NOT NULL,
    description CLOB,  -- CLOB columns should not have NOT NULL inline
    priority VARCHAR2(10) NOT NULL,
    category VARCHAR2(10) NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    status VARCHAR2(20) DEFAULT 'NEW' NOT NULL,
    employee_id NUMBER NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT chk_priority CHECK (priority IN ('LOW', 'MEDIUM', 'HIGH')),
    CONSTRAINT chk_category CHECK (category IN ('NETWORK', 'HARDWARE', 'SOFTWARE', 'OTHER')),
    CONSTRAINT chk_status CHECK (status IN ('NEW', 'IN_PROGRESS', 'RESOLVED'))
);


-- History table to track ticket status changes
CREATE TABLE history (
    id NUMBER GENERATED ALWAYS AS IDENTITY(START with 1 INCREMENT by 1) PRIMARY KEY,
    ticket_id NUMBER NOT NULL,
    old_status VARCHAR2(20) CHECK (old_status IN ('NEW', 'IN_PROGRESS', 'RESOLVED')) NOT NULL,
    new_status VARCHAR2(20) CHECK (new_status IN ('NEW', 'IN_PROGRESS', 'RESOLVED')) NOT NULL,
    changed_by NUMBER NOT NULL, -- IT Support user who changed the status
    change_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (ticket_id) REFERENCES tickets(id) ON DELETE CASCADE,
    FOREIGN KEY (changed_by) REFERENCES users(id) ON DELETE CASCADE
);

-- Comments table
CREATE TABLE comments (
    id NUMBER GENERATED ALWAYS AS IDENTITY(START with 1 INCREMENT by 1) PRIMARY KEY,
    content CLOB NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    ticket_id NUMBER NOT NULL,
    user_id NUMBER NOT NULL,
    FOREIGN KEY (ticket_id) REFERENCES tickets(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
