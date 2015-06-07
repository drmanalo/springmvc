-- Increase the Vnumber to make sure that flyway will execute this last
insert into users (id, active, username, password, role) values (1, 1, 'admin', '$2a$10$MItoUN2ie39nBeUB8LcZtOlPSqwgi2n3YWmwGzSg9LzBv1wbBKPzK', 'ROLE_ADMIN');
insert into users (id, active, username, password, role) values (2, 1, 'guest', '$2a$10$umFpaXJZEmPpX5ksqUc2M.uDyer616QLlg2q.hmniFEo1faNcAExe', 'ROLE_USER');