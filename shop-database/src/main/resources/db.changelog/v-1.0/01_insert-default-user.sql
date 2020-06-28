INSERT INTO `users` (`user_name`, `password`)
VALUE ('admin', '$2y$12$DyzDZOJueZahZRqrBd6iaOJCtquDDAA4lnmaCJeHHo/jfTraeaPFO'), ('guest', '$2y$12$Zt2tc.JnVVnv8W5GctcuGOcfAah1NsDl2SW.5Cwl9tJWfXeaYsMHK');

GO

INSERT INTO `roles` (`name`) VALUE ('ROLE_ADMIN'), ('ROLE_GUEST');

GO

INSERT INTO `users_roles`(`user_id`, `role_id`)
SELECT (SELECT id FROM `users` WHERE `user_name` = 'admin'), (SELECT id FROM `roles` WHERE `name` = 'ROLE_ADMIN')
UNION ALL
SELECT (SELECT id FROM `users` WHERE `user_name` = 'guest'), (SELECT id FROM `roles` WHERE `name` = 'ROLE_GUEST')

GO