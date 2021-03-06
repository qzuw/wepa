INSERT INTO Person (id, name, password, student_number) VALUES (1, 'Teacher A', '$2a$10$.jdTkIapRyBg/VeFNGTMCO/Ffrses5N3z.QoZRn2ahzZj3m0U8ntS', '000000000');
INSERT INTO Person (id, name, password, student_number) VALUES (2, 'Teacher B', '$2a$10$.jdTkIapRyBg/VeFNGTMCO/Ffrses5N3z.QoZRn2ahzZj3m0U8ntS', '000000001');

INSERT INTO Person (id, name, password, student_number) VALUES (3, 'Assistant A', '$2a$10$vlyZ/cJkHbYFiUm8oD4np.JO4VI0J3HBOoiVpSbMnf/JIh2E1CAku', '111111111');
INSERT INTO Person (id, name, password, student_number) VALUES (4, 'Assistant B', '$2a$10$vlyZ/cJkHbYFiUm8oD4np.JO4VI0J3HBOoiVpSbMnf/JIh2E1CAku', '111111112');

INSERT INTO Person (id, name, password, student_number) VALUES (5, 'Student A', '$2a$10$MXfDn3no7h4qtlEguFxFd.piNhwKDloDZEfKpG/vmx47OpKn3.VkW', '222222222');
INSERT INTO Person (id, name, student_number) VALUES (6, 'Student B', '222222223');
INSERT INTO Person (name, student_number) VALUES ('Student C', '222222224');
INSERT INTO Person (name, student_number) VALUES ('Student D', '222222225');
INSERT INTO Person (name, student_number) VALUES ('Student E', '222222226');
INSERT INTO Person (name, student_number) VALUES ('Student F', '222222227');
INSERT INTO Person (name, student_number) VALUES ('Student G', '222222228');
INSERT INTO Person (name, student_number) VALUES ('Student H', '222222229');
INSERT INTO Person (name, student_number) VALUES ('Student I', '222222230');
INSERT INTO Person (name, student_number) VALUES ('Student J', '222222231');
INSERT INTO Person (name, student_number) VALUES ('Student K', '222222232');
INSERT INTO Person (name, student_number) VALUES ('Student L', '222222233');
INSERT INTO Person (name, student_number) VALUES ('Student M', '222222234');
INSERT INTO Person (name, student_number) VALUES ('Student N', '222222235');
INSERT INTO Person (name, student_number) VALUES ('Student O', '222222236');
INSERT INTO Person (name, student_number) VALUES ('Student P', '222222237');
INSERT INTO Person (name, student_number) VALUES ('Student Q', '222222238');
INSERT INTO Person (name, student_number) VALUES ('Student R', '222222239');

INSERT INTO Course (name, course_start, course_end) VALUES ('Test course A', '2016-12-9', '2017-1-19');
INSERT INTO Course (name, course_start, course_end) VALUES ('Test course B', '1998-9-1', '2020-5-19');

INSERT INTO Course_assistants (courses_assisted_id, assistants_id) VALUES (1, 3);
INSERT INTO Course_assistants (courses_assisted_id, assistants_id) VALUES (2, 3);
INSERT INTO Course_assistants (courses_assisted_id, assistants_id) VALUES (2, 4);

INSERT INTO Course_students (courses_attended_id, students_id) VALUES (1, 5);
INSERT INTO Course_students (courses_attended_id, students_id) VALUES (2, 5);
INSERT INTO Course_students (courses_attended_id, students_id) VALUES (1, 6);
INSERT INTO Course_students (courses_attended_id, students_id) VALUES (1, 7);
INSERT INTO Course_students (courses_attended_id, students_id) VALUES (1, 8);
INSERT INTO Course_students (courses_attended_id, students_id) VALUES (1, 9);
INSERT INTO Course_students (courses_attended_id, students_id) VALUES (1, 10);
INSERT INTO Course_students (courses_attended_id, students_id) VALUES (1, 11);
INSERT INTO Course_students (courses_attended_id, students_id) VALUES (1, 12);
INSERT INTO Course_students (courses_attended_id, students_id) VALUES (1, 13);
INSERT INTO Course_students (courses_attended_id, students_id) VALUES (1, 14);
INSERT INTO Course_students (courses_attended_id, students_id) VALUES (1, 15);
INSERT INTO Course_students (courses_attended_id, students_id) VALUES (1, 16);
INSERT INTO Course_students (courses_attended_id, students_id) VALUES (1, 17);
INSERT INTO Course_students (courses_attended_id, students_id) VALUES (1, 18);
INSERT INTO Course_students (courses_attended_id, students_id) VALUES (1, 19);
INSERT INTO Course_students (courses_attended_id, students_id) VALUES (1, 20);
INSERT INTO Course_students (courses_attended_id, students_id) VALUES (1, 21);

INSERT INTO Person_authorities (person_id, authorities) VALUES (1, 'TEACHER');
INSERT INTO Person_authorities (person_id, authorities) VALUES (2, 'TEACHER');
INSERT INTO Person_authorities (person_id, authorities) VALUES (3, 'ASSISTANT');
INSERT INTO Person_authorities (person_id, authorities) VALUES (4, 'ASSISTANT');
INSERT INTO Person_authorities (person_id, authorities) VALUES (5, 'STUDENT');
INSERT INTO Person_authorities (person_id, authorities) VALUES (6, 'STUDENT');
INSERT INTO Person_authorities (person_id, authorities) VALUES (7, 'STUDENT');
INSERT INTO Person_authorities (person_id, authorities) VALUES (8, 'STUDENT');
INSERT INTO Person_authorities (person_id, authorities) VALUES (9, 'STUDENT');
INSERT INTO Person_authorities (person_id, authorities) VALUES (10, 'STUDENT');
INSERT INTO Person_authorities (person_id, authorities) VALUES (11, 'STUDENT');
INSERT INTO Person_authorities (person_id, authorities) VALUES (12, 'STUDENT');
INSERT INTO Person_authorities (person_id, authorities) VALUES (13, 'STUDENT');
INSERT INTO Person_authorities (person_id, authorities) VALUES (14, 'STUDENT');
INSERT INTO Person_authorities (person_id, authorities) VALUES (15, 'STUDENT');
INSERT INTO Person_authorities (person_id, authorities) VALUES (16, 'STUDENT');
INSERT INTO Person_authorities (person_id, authorities) VALUES (17, 'STUDENT');
INSERT INTO Person_authorities (person_id, authorities) VALUES (18, 'STUDENT');
INSERT INTO Person_authorities (person_id, authorities) VALUES (19, 'STUDENT');
INSERT INTO Person_authorities (person_id, authorities) VALUES (20, 'STUDENT');
INSERT INTO Person_authorities (person_id, authorities) VALUES (21, 'STUDENT');
INSERT INTO Person_authorities (person_id, authorities) VALUES (22, 'STUDENT');

INSERT INTO Week (description, week, course_id) VALUES ('Week 1', 1, 1);
INSERT INTO Week (description, week, course_id) VALUES ('Week 2', 2, 1);
INSERT INTO Week (description, week, course_id) VALUES ('Week 3', 3, 1);
INSERT INTO Week (description, week, course_id) VALUES ('Test week 1', 1, 2);
INSERT INTO Week (description, week, course_id) VALUES ('Test week 2', 2, 2);

INSERT INTO Exercise (description, week_id) VALUES ('Exercise 1 for week 1', 1);
INSERT INTO Exercise (description, week_id) VALUES ('Exercise 2 for week 1', 1);
INSERT INTO Exercise (description, week_id) VALUES ('Exercise 3 for week 1', 1);
INSERT INTO Exercise (description, week_id) VALUES ('Exercise 1 for week 2', 2);
INSERT INTO Exercise (description, week_id) VALUES ('Exercise 2 for week 2', 2);
INSERT INTO Exercise (description, week_id) VALUES ('Exercise 1 for week 3', 3);
INSERT INTO Exercise (description, week_id) VALUES ('Exercise 2 for week 3', 3);
INSERT INTO Exercise (description, week_id) VALUES ('Exercise 3 for week 3', 3);
INSERT INTO Exercise (description, week_id) VALUES ('Exercise 1 for week 1', 4);
INSERT INTO Exercise (description, week_id) VALUES ('Exercise 2 for week 1', 4);
INSERT INTO Exercise (description, week_id) VALUES ('Exercise 1 for week 1', 5);

