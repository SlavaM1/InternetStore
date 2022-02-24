insert into usr (id, active, password, username)
    VALUES (1,true, '$2y$08$1JlKlU0vcITpo5wFSp07.u0s9KpmJpbtRbj.tf/0Is5M8qU2Q0QxK', 'admin');

insert into user_role (user_id, roles)
VALUES (1, 'USER'), (1, 'ADMIN');