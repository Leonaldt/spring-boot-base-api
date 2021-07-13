INSERT INTO USER(name, email, password) VALUES('Leonidas', 'leonidas.lima@itbam.org.br', '$2a$10$gt5zyY27gFbHDv2EtpbH/OvLPR2kGU.y410GwUU7G4AZBa7WtZuK6');

INSERT INTO PROFILE(id, name) VALUES (1, 'ROLE_USER');

INSERT INTO USER_PROFILES(user_id, profiles_id) VALUES(1 ,1);