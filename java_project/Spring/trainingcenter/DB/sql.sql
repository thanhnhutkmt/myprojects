/*CREATE DATABASE TrainingCenter;
USE TrainingCenter;*/
--
DROP TABLE IF EXISTS learners;
CREATE TABLE learners (
	LearnerID int unique auto_increment not null, PRIMARY KEY (LearnerID),
    LastName varchar(255),
    MiddleName varchar(255),
    FirstName varchar(255),
    Address varchar(255),
    Sex boolean,
    BirthDate long,
    CreatedDate long,
    Email1 varchar(255) not null,
    Email2 varchar(255),
    Email3 varchar(255),
    Mobile varchar(30) not null,
    HomePhone varchar(30),
    WorkPhone varchar(30),
    Image varchar(255),
    IDcard varchar(30) not null unique,
	IDpassport varchar(30) unique,
    Notes text
) COLLATE utf8_unicode_ci;
INSERT INTO learners values ("1", "Nguyễn", "Văn", "A", "20 Ngô Quyền, P.1, Q.1", true, 315532800000, 1483574400000, "nva@gmail.com", "", "", "09112345678", "", "", null, "1234567890", null, "");
INSERT INTO learners values ("2", "Nguyễn", "Thị", "B", "21 Nguyễn Trãi, P.2, Q.2", false, 315932800000, 1489574400000, "nvb@gmail.com", "", "", "09212345678", "", "", null, "2234567890", null, "");
INSERT INTO learners values ("3", "Nguyễn", "Văn", "C", "22 Nguyễn Khuyến, P.3, Q.3", true, 335532800000, 1463574400000, "nvc@gmail.com", "", "", "09312345678", "", "", null, "3234567890", null, "");
INSERT INTO learners values ("4", "Nguyễn", "Thị", "D", "23 Điện Biên Phủ, P.4, Q.4", false, 915532800000, 1483474400000, "nvd@gmail.com", "", "", "09412345678", "", "", null, "4234567890", null, "");
INSERT INTO learners values ("5", "Nguyễn", "Văn", "E", "24 Võ Văn Kiệt, P.5, Q.5", true, 515532800000, 1483579400000, "nve@gmail.com", "", "", "09512345678", "", "", null, "1534567890", null, "");
INSERT INTO learners values ("6", "Nguyễn", "Thị", "F", "25 Mai Chí Thọ, P.6, Q.6", false, 715532800000, 1423574400000, "nvf@gmail.com", "", "", "09612345678", "", "", null, "6234567890", null, "");
INSERT INTO learners values ("7", "Nguyễn", "Văn", "G", "26 Bạch Đằng, P.7, Q.7", true, 395532800000, 1483174400000, "nvg@gmail.com", "", "", "09712345678", "", "", null, "7234567890", null, "");
INSERT INTO learners values ("8", "Nguyễn", "Thị", "H", "27 Lê Lợi, P.8, Q.8", false, 399532800000, 1483504400000, "nvh@gmail.com", "", "", "09812345678", "", "", null, "8234567890", null, "");
INSERT INTO learners values ("9", "Nguyễn", "Văn", "I", "28 Lê Lai, P.9, Q.9", true, 319932800000, 1483074400000, "nvi@gmail.com", "", "", "09912345678", "", "", null, "9234567890", null, "");
INSERT INTO learners values ("10", "Nguyễn", "Thị", "J", "29 Nguyễn Huệ, P.10, Q.10", false, 395032800000, 1403574400000, "nvj@gmail.com", "", "", "09312345678", "", "", null, "1034567890", null, "");
INSERT INTO learners values ("11", "Nguyễn", "Văn", "K", "30 Nguyễn Hoàng, P.11, Q.11", true, 600532800000, 1083574400000, "nvk@gmail.com", "", "", "09322345678", "", "", null, "1134567890", null, "");
INSERT INTO learners values ("12", "Nguyễn", "Thị", "L", "31 Xa Lộ Hà Nội, P.12, Q.12", false, 317532800000, 1283574400000, "nvl@gmail.com", "", "", "09332345678", "", "", null, "1236567890", null, "");
SELECT * FROM learners;

--
DROP TABLE IF EXISTS courses;
CREATE TABLE courses (
	CourseID int unique auto_increment not null, PRIMARY KEY (CourseID),
    Title varchar(255) not null,
    CreatedDate long,
    GroupOfCourse varchar(255),
    Description varchar(1024) unique,
    Fee varchar(9) not null,
	Duration varchar(10)
) COLLATE utf8_unicode_ci;
INSERT INTO courses values (1, "Lập trình Java cơ bản", 1483228800000, "Lập trình", "Lập trình Java cơ bản cho người mới", "3000000", "36h");
INSERT INTO courses values (2, "Lập trình Java nâng cao", 1483228800000, "Lập trình", "Lập trình Java nâng cao cho người đã học cơ bản", "3000000", "36h");
INSERT INTO courses values (3, "Lập trình C và C++", 1483228800000, "Lập trình", "Lập trình C và C++ cho người mới làm quen lập trình", "2000000", "36h");
INSERT INTO courses values (4, "Lập trình Android cơ bản", 1483228800000, "Lập trình", "Lập trình Android cơ bản cho người mới", "3000000", "36h");
INSERT INTO courses values (5, "Lập trình Android nâng cao", 1483228800000, "Lập trình", "Lập trình Android nâng cao cho người đã biết cơ bản", "3000000", "36h");
INSERT INTO courses values (6, "Lập trình iOS cơ bản", 1483228800000, "Lập trình", "Lập trình iOS cơ bản cho người mới", "3000000", "36h");
INSERT INTO courses values (7, "Lập trình iOS nâng cao", 1483228800000, "Lập trình", "Lập trình iOS nâng cao cho người đã biết cơ bản", "3000000", "36h");
INSERT INTO courses values (8, "Lập trình WindowPhone cơ bản", 1483228800000, "Lập trình", "Lập trình WindowPhone cơ bản cho người mới", "3000000", "36h");
INSERT INTO courses values (9, "Lập trình WindowPhone nâng cao", 1483228800000, "Lập trình", "Lập trình WindowPhone nâng cao cho người đã biết cơ bản", "3000000", "36h");
INSERT INTO courses values (10, "Lập trình Java web", 1483228800000, "Lập trình", "Lập trình Java web cho người đã biết cơ bản", "5000000", "36h");
INSERT INTO courses values (11, "Lập trình vi xử lý AT89, PIC, AVR", 1483228800000, "Điện tử", "Lập trình các dòng vi xử lý phổ biến 8 bit", "3000000", "18h");
INSERT INTO courses values (12, "Kỹ thuật số", 1483228800000, "Điện tử", "Kiến thức về số cho người mới", "5000000", "18h");
INSERT INTO courses values (13, "Lập trình ARM cơ bản", 1483228800000, "Điện tử", "Lập trình ARM cơ bản cho người mới", "2000000", "18h");
INSERT INTO courses values (14, "Lập trình ARM nâng cao", 1483228800000, "Điện tử", "Lập trình ARM nâng cao cho người đã biết cơ bản", "2000000", "18h");
INSERT INTO courses values (15, "Lập trình Adruino cơ bản", 1483228800000, "Điện tử", "Lập trình Adruino cho người mới", "4000000", "18h");
INSERT INTO courses values (16, "Lập trình Adruino nâng cao", 1483228800000, "Điện tử", "Lập trình Adruino cho người đã biết cơ bản", "4000000", "18h");
INSERT INTO courses values (17, "Điện tử cơ bản", 1483228800000, "Điện tử", "Kiến thức điện tử cho người mới", "2500000", "18h");
INSERT INTO courses values (18, "Điện tử ứng dụng", 1483228800000, "Điện tử", "Điện tử cho người đã biết cơ bản", "2500000", "18h");
INSERT INTO courses values (19, "Tin học văn phòng", 1483228800000, "Tin học", "Tin học cho giới văn phòng, sinh viên", "5000000", "36h");
INSERT INTO courses values (20, "Microsoft Word", 1483228800000, "Tin học", "Sử dụng phẩn mềm MS Word", "3000000", "18h");
INSERT INTO courses values (21, "Microsoft Excel", 1483228800000, "Tin học", "Sử dụng phẩn mềm MS Excel", "3000000", "18h");
INSERT INTO courses values (22, "Microsoft PowerPoint", 1483228800000, "Tin học", "Sử dụng phẩn mềm MS Powerpoint", "3000000", "18h");
INSERT INTO courses values (23, "Chứng chỉ tin học A", 1483228800000, "Tin học", "Học và luyện thi chứng chỉ A", "5000000", "36h");
INSERT INTO courses values (24, "Chứng chỉ tin học B", 1483228800000, "Tin học", "Học và luyện thi chứng chỉ B", "7000000", "36h");
INSERT INTO courses values (25, "Chứng chỉ tin học C", 1483228800000, "Tin học", "Học và luyện thi chứng chỉ C", "9000000", "36h");
INSERT INTO courses values (26, "Marketing online", 1483228800000, "Bán hàng", "Quảng cáo online", "2000000", "18h");
INSERT INTO courses values (27, "Quản lý dự án Agile Scrum", 1483228800000, "Quản lý", "Quản lý dự án theo quy trình Agile Scrum", "20000000", "48h");
INSERT INTO courses values (28, "Quản lý dự án PMP", 1483228800000, "Quản lý", "Quản lý dự án theo chuẩn PMI", "9000000", "36h");
INSERT INTO courses values (29, "Quản lý dự án bằng Primavera", 1483228800000, "Quản lý", "Sử dụng Primavera trong quản lý dự án", "8000000", "18h");
INSERT INTO courses values (30, "Anh văn giao tiếp cơ bản", 1483228800000, "Ngoại ngữ", "Anh văn giao tiếp cho người mới", "2000000", "36h");
INSERT INTO courses values (31, "Anh văn giao tiếp nâng cao", 1483228800000, "Ngoại ngữ", "Anh văn giao tiếp cho người đã biết cơ bản", "2000000", "36h");
INSERT INTO courses values (32, "Anh văn sơ cấp", 1483228800000, "Ngoại ngữ", "Ngữ pháp và từ vựng Anh văn cho người mới", "6000000", "72h");
INSERT INTO courses values (33, "Anh văn trung cấp", 1483228800000, "Ngoại ngữ", "Ngữ pháp và từ vựng Anh văn cho người đã biết cơ bản", "3000000", "36h");
INSERT INTO courses values (34, "Anh văn cao cấp", 1483228800000, "Ngoại ngữ", "Ngữ pháp và từ vựng Anh văn nâng cao", "4000000", "24h");
INSERT INTO courses values (35, "IELTS 4.5", 1483228800000, "Ngoại ngữ", "Luyện thi IELTS 4.5", "2500000", "36h");
INSERT INTO courses values (36, "IELTS 5.5", 1483228800000, "Ngoại ngữ", "Luyện thi IELTS 5.5", "4000000", "36h");
INSERT INTO courses values (37, "IELTS 6.5", 1483228800000, "Ngoại ngữ", "Luyện thi IELTS 6.5", "8500000", "36h");
INSERT INTO courses values (38, "IELTS 7.5", 1483228800000, "Ngoại ngữ", "Luyện thi IELTS 7.5", "10000000", "48h");
INSERT INTO courses values (39, "TOEIC 500", 1483228800000, "Ngoại ngữ", "Luyện thi TOEIC 500", "5000000", "36h");
INSERT INTO courses values (40, "TOEIC 650", 1483228800000, "Ngoại ngữ", "Luyện thi TOEIC 650", "6000000", "36h");
INSERT INTO courses values (41, "TOEIC 800", 1483228800000, "Ngoại ngữ", "Luyện thi TOEIC 800", "7000000", "48h");
SELECT * FROM courses;
--
DROP TABLE IF EXISTS classes;
CREATE TABLE classes (
	ClassID int unique auto_increment not null,  PRIMARY KEY (ClassID),
    CourseID int not null, FOREIGN KEY (CourseID) REFERENCES courses(CourseID),
    OpenDate long,
    TimeTable varchar(255) not null,
    Room varchar(10) not null
) COLLATE utf8_unicode_ci;
INSERT INTO classes values (1, 1, 1484870400000, "2-4-6", "A1");
INSERT INTO classes values (42, 1, 1484870400000, "2-4-6", "A4");
INSERT INTO classes values (2, 2, 1484870400000, "3-5-7", "A2");
INSERT INTO classes values (3, 3, 1484870400000, "7CN", "A3");
INSERT INTO classes values (4, 4, 1484870400000, "3-5-7", "A4");
INSERT INTO classes values (5, 5, 1484870400000, "2-4-6", "A5");
INSERT INTO classes values (6, 6, 1484870400000, "2-4-6", "B1");
INSERT INTO classes values (7, 7, 1484870400000, "3-5-7", "B2");
INSERT INTO classes values (8, 8, 1484870400000, "7CN", "B3");
INSERT INTO classes values (9, 9, 1484870400000, "3-5-7", "B4");
INSERT INTO classes values (10, 10, 1484870400000, "2-4-6", "B5");
INSERT INTO classes values (11, 11, 1484870400000, "2-4-6", "C1");
INSERT INTO classes values (12, 12, 1484870400000, "3-5-7", "C2");
INSERT INTO classes values (13, 13, 1484870400000, "7CN", "C3");
INSERT INTO classes values (14, 14, 1484870400000, "3-5-7", "C4");
INSERT INTO classes values (15, 15, 1484870400000, "2-4-6", "C5");
INSERT INTO classes values (16, 16, 1484870400000, "2-4-6", "D1");
INSERT INTO classes values (17, 17, 1484870400000, "3-5-7", "D2");
INSERT INTO classes values (18, 18, 1484870400000, "7CN", "D3");
INSERT INTO classes values (19, 19, 1484870400000, "3-5-7", "D4");
INSERT INTO classes values (20, 20, 1484870400000, "2-4-6", "D5");
INSERT INTO classes values (21, 21, 1484870400000, "2-4-6", "E1");
INSERT INTO classes values (22, 22, 1484870400000, "3-5-7", "E2");
INSERT INTO classes values (23, 23, 1484870400000, "7CN", "E3");
INSERT INTO classes values (24, 24, 1484870400000, "3-5-7", "E4");
INSERT INTO classes values (25, 25, 1484870400000, "2-4-6", "E5");
INSERT INTO classes values (26, 26, 1484870400000, "2-4-6", "F1");
INSERT INTO classes values (27, 27, 1484870400000, "3-5-7", "F2");
INSERT INTO classes values (28, 28, 1484870400000, "7CN", "F3");
INSERT INTO classes values (29, 29, 1484870400000, "3-5-7", "F4");
INSERT INTO classes values (30, 30, 1484870400000, "2-4-6", "F5");
INSERT INTO classes values (31, 31, 1484870400000, "2-4-6", "G1");
INSERT INTO classes values (32, 32, 1484870400000, "3-5-7", "G2");
INSERT INTO classes values (33, 33, 1484870400000, "7CN", "G3");
INSERT INTO classes values (34, 34, 1484870400000, "3-5-7", "G4");
INSERT INTO classes values (35, 35, 1484870400000, "2-4-6", "G5");
INSERT INTO classes values (36, 36, 1484870400000, "2-4-6", "H1");
INSERT INTO classes values (37, 37, 1484870400000, "3-5-7", "H2");
INSERT INTO classes values (38, 38, 1484870400000, "7CN", "H3");
INSERT INTO classes values (39, 39, 1484870400000, "3-5-7", "H4");
INSERT INTO classes values (40, 40, 1484870400000, "2-4-6", "H5");
INSERT INTO classes values (41, 41, 1484870400000, "7CN", "H6");
INSERT INTO classes values (43, 1, 1484870400000, "7CN", "H6");
SELECT * FROM classes;
--
DROP TABLE IF EXISTS registries;
CREATE TABLE registries (
	RegistryID int unique auto_increment not null,
	ClassID int not null,
    LearnerID int not null,
    RegisteredDate long,
    Deposit varchar(9),
    FeeStatus boolean,
    PRIMARY KEY (RegistryID),
    FOREIGN KEY (ClassID) REFERENCES classes(ClassID),
    FOREIGN KEY (LearnerID) REFERENCES learners(LearnerID)
) COLLATE utf8_unicode_ci;
INSERT INTO registries values (1, 1, 1, 1487548800000, "", true);
INSERT INTO registries values (2, 1, 2, 1487548800000, "", true);
INSERT INTO registries values (3, 1, 3, 1487548800000, "", true);
INSERT INTO registries values (4, 1, 4, 1487548800000, "", true);
INSERT INTO registries values (5, 1, 5, 1487548800000, "", true);
INSERT INTO registries values (6, 1, 6, 1487548800000, "", true);
INSERT INTO registries values (7, 1, 7, 1487548800000, "", true);
INSERT INTO registries values (8, 1, 8, 1487548800000, "", true);
INSERT INTO registries values (9, 1, 9, 1487548800000, "", false);
INSERT INTO registries values (10, 1, 10, 1487548800000, "", true);
INSERT INTO registries values (11, 1, 11, 1487548800000, "", true);
INSERT INTO registries values (12, 1, 12, 1487548800000, "", false);
INSERT INTO registries values (13, 2, 1, 1487548800000, "", true);
INSERT INTO registries values (14, 2, 2, 1487548800000, "", true);
INSERT INTO registries values (15, 2, 3, 1487548800000, "", true);
INSERT INTO registries values (16, 2, 4, 1487548800000, "", true);
INSERT INTO registries values (17, 2, 5, 1487548800000, "", true);
INSERT INTO registries values (18, 2, 6, 1487548800000, "", true);
INSERT INTO registries values (19, 2, 7, 1487548800000, "", true);
INSERT INTO registries values (20, 2, 8, 1487548800000, "", true);
INSERT INTO registries values (21, 2, 9, 1487548800000, "", false);
INSERT INTO registries values (22, 2, 10, 1487548800000, "", true);
INSERT INTO registries values (23, 2, 11, 1487548800000, "", true);
INSERT INTO registries values (24, 2, 12, 1487548800000, "", false);
INSERT INTO registries values (25, 3, 1, 1487548800000, "", true);
INSERT INTO registries values (26, 3, 2, 1487548800000, "", true);
INSERT INTO registries values (27, 3, 3, 1487548800000, "", true);
INSERT INTO registries values (28, 3, 4, 1487548800000, "", true);
INSERT INTO registries values (29, 3, 5, 1487548800000, "", true);
INSERT INTO registries values (30, 3, 6, 1487548800000, "", true);
INSERT INTO registries values (31, 3, 7, 1487548800000, "", true);
INSERT INTO registries values (32, 3, 8, 1487548800000, "", true);
INSERT INTO registries values (33, 3, 9, 1487548800000, "", false);
INSERT INTO registries values (34, 3, 10, 1487548800000, "", true);
INSERT INTO registries values (35, 3, 11, 1487548800000, "", true);
INSERT INTO registries values (36, 3, 12, 1487548800000, "", false);
INSERT INTO registries values (37, 4, 1, 1487548800000, "", true);
INSERT INTO registries values (38, 4, 2, 1487548800000, "", true);
INSERT INTO registries values (39, 4, 3, 1487548800000, "", true);
INSERT INTO registries values (40, 4, 4, 1487548800000, "", true);
INSERT INTO registries values (41, 4, 5, 1487548800000, "", true);
INSERT INTO registries values (42, 5, 6, 1487548800000, "", true);
INSERT INTO registries values (43, 5, 10, 1487548800000, "", true);
INSERT INTO registries values (44, 5, 11, 1487548800000, "", true);
INSERT INTO registries values (45, 5, 12, 1487548800000, "", false);
INSERT INTO registries values (46, 5, 6, 1487548800000, "", true);
INSERT INTO registries values (47, 5, 7, 1487548800000, "", true);
INSERT INTO registries values (48, 6, 8, 1487548800000, "", true);
INSERT INTO registries values (49, 6, 9, 1487548800000, "", false);
INSERT INTO registries values (50, 6, 10, 1487548800000, "", true);
INSERT INTO registries values (51, 6, 11, 1487548800000, "", true);
INSERT INTO registries values (52, 6, 12, 1487548800000, "", false);
INSERT INTO registries values (53, 7, 1, 1487548800000, "", true);
INSERT INTO registries values (54, 7, 2, 1487548800000, "", true);
INSERT INTO registries values (55, 7, 7, 1487548800000, "", true);
INSERT INTO registries values (56, 7, 8, 1487548800000, "", true);
INSERT INTO registries values (57, 7, 9, 1487548800000, "", false);
INSERT INTO registries values (58, 7, 10, 1487548800000, "", true);
INSERT INTO registries values (59, 7, 11, 1487548800000, "", true);
INSERT INTO registries values (60, 7, 12, 1487548800000, "", false);
INSERT INTO registries values (61, 8, 4, 1487548800000, "", true);
INSERT INTO registries values (62, 8, 5, 1487548800000, "", true);
INSERT INTO registries values (63, 8, 6, 1487548800000, "", true);
INSERT INTO registries values (64, 8, 10, 1487548800000, "", true);
INSERT INTO registries values (65, 8, 11, 1487548800000, "", true);
INSERT INTO registries values (66, 8, 12, 1487548800000, "", false);
INSERT INTO registries values (67, 9, 1, 1487548800000, "", true);
INSERT INTO registries values (68, 9, 2, 1487548800000, "", true);
INSERT INTO registries values (69, 9, 3, 1487548800000, "", true);
INSERT INTO registries values (70, 9, 4, 1487548800000, "", true);
INSERT INTO registries values (71, 9, 7, 1487548800000, "", true);
INSERT INTO registries values (72, 9, 8, 1487548800000, "", true);
INSERT INTO registries values (73, 10, 9, 1487548800000, "", false);
INSERT INTO registries values (74, 10, 10, 1487548800000, "", true);
INSERT INTO registries values (75, 10, 11, 1487548800000, "", true);
INSERT INTO registries values (76, 10, 12, 1487548800000, "", false);
INSERT INTO registries values (77, 10, 1, 1487548800000, "", true);
INSERT INTO registries values (78, 10, 7, 1487548800000, "", true);
INSERT INTO registries values (79, 11, 8, 1487548800000, "", true);
INSERT INTO registries values (80, 11, 9, 1487548800000, "", false);
INSERT INTO registries values (81, 11, 10, 1487548800000, "", true);
INSERT INTO registries values (82, 11, 11, 1487548800000, "", true);
INSERT INTO registries values (83, 11, 12, 1487548800000, "", false);
INSERT INTO registries values (84, 11, 1, 1487548800000, "", true);
INSERT INTO registries values (85, 11, 2, 1487548800000, "", true);
INSERT INTO registries values (86, 11, 3, 1487548800000, "", true);
INSERT INTO registries values (87, 11, 4, 1487548800000, "", true);
INSERT INTO registries values (88, 12, 11, 1487548800000, "", true);
INSERT INTO registries values (89, 12, 12, 1487548800000, "", false);
INSERT INTO registries values (90, 12, 1, 1487548800000, "", true);
INSERT INTO registries values (91, 12, 2, 1487548800000, "", true);
INSERT INTO registries values (92, 12, 3, 1487548800000, "", true);
INSERT INTO registries values (93, 12, 4, 1487548800000, "", true);
INSERT INTO registries values (94, 12, 5, 1487548800000, "", true);
INSERT INTO registries values (95, 12, 6, 1487548800000, "", true);
INSERT INTO registries values (96, 13, 7, 1487548800000, "", true);
INSERT INTO registries values (97, 13, 8, 1487548800000, "", true);
INSERT INTO registries values (98, 13, 9, 1487548800000, "", false);
INSERT INTO registries values (99, 13, 4, 1487548800000, "", true);
INSERT INTO registries values (100, 13, 5, 1487548800000, "", true);
INSERT INTO registries values (101, 13, 6, 1487548800000, "", true);
INSERT INTO registries values (102, 13, 7, 1487548800000, "", true);
INSERT INTO registries values (103, 14, 8, 1487548800000, "", true);
INSERT INTO registries values (104, 14, 9, 1487548800000, "", false);
INSERT INTO registries values (105, 14, 10, 1487548800000, "", true);
INSERT INTO registries values (106, 14, 11, 1487548800000, "", true);
INSERT INTO registries values (107, 14, 12, 1487548800000, "", false);
INSERT INTO registries values (108, 14, 1, 1487548800000, "", true);
INSERT INTO registries values (109, 14, 2, 1487548800000, "", true);
INSERT INTO registries values (110, 14, 3, 1487548800000, "", true);
INSERT INTO registries values (111, 15, 11, 1487548800000, "", true);
INSERT INTO registries values (112, 15, 12, 1487548800000, "", false);
INSERT INTO registries values (113, 15, 1, 1487548800000, "", true);
INSERT INTO registries values (114, 15, 2, 1487548800000, "", true);
INSERT INTO registries values (115, 15, 3, 1487548800000, "", true);
INSERT INTO registries values (116, 15, 4, 1487548800000, "", true);
INSERT INTO registries values (117, 15, 5, 1487548800000, "", true);
INSERT INTO registries values (118, 15, 2, 1487548800000, "", true);
INSERT INTO registries values (119, 16, 3, 1487548800000, "", true);
INSERT INTO registries values (120, 16, 4, 1487548800000, "", true);
INSERT INTO registries values (121, 16, 5, 1487548800000, "", true);
INSERT INTO registries values (122, 16, 6, 1487548800000, "", true);
INSERT INTO registries values (123, 16, 7, 1487548800000, "", true);
INSERT INTO registries values (124, 16, 8, 1487548800000, "", true);
INSERT INTO registries values (125, 17, 9, 1487548800000, "", false);
INSERT INTO registries values (126, 17, 10, 1487548800000, "", true);
INSERT INTO registries values (127, 17, 11, 1487548800000, "", true);
INSERT INTO registries values (128, 17, 12, 1487548800000, "", false);
INSERT INTO registries values (129, 17, 6, 1487548800000, "", true);
INSERT INTO registries values (130, 17, 7, 1487548800000, "", true);
INSERT INTO registries values (131, 17, 8, 1487548800000, "", true);
INSERT INTO registries values (132, 18, 9, 1487548800000, "", false);
INSERT INTO registries values (133, 18, 10, 1487548800000, "", true);
INSERT INTO registries values (134, 18, 4, 1487548800000, "", true);
INSERT INTO registries values (135, 18, 5, 1487548800000, "", true);
INSERT INTO registries values (136, 18, 6, 1487548800000, "", true);
INSERT INTO registries values (137, 18, 7, 1487548800000, "", true);
INSERT INTO registries values (138, 18, 8, 1487548800000, "", true);
INSERT INTO registries values (139, 19, 4, 1487548800000, "", true);
INSERT INTO registries values (140, 19, 5, 1487548800000, "", true);
INSERT INTO registries values (141, 19, 6, 1487548800000, "", true);
INSERT INTO registries values (142, 19, 7, 1487548800000, "", true);
INSERT INTO registries values (143, 19, 8, 1487548800000, "", true);
INSERT INTO registries values (144, 19, 9, 1487548800000, "", false);
INSERT INTO registries values (145, 19, 10, 1487548800000, "", true);
INSERT INTO registries values (146, 20, 11, 1487548800000, "", true);
INSERT INTO registries values (147, 20, 12, 1487548800000, "", false);
INSERT INTO registries values (148, 20, 5, 1487548800000, "", true);
INSERT INTO registries values (149, 20, 6, 1487548800000, "", true);
INSERT INTO registries values (150, 20, 7, 1487548800000, "", true);
INSERT INTO registries values (151, 20, 8, 1487548800000, "", true);
INSERT INTO registries values (152, 21, 9, 1487548800000, "", false);
INSERT INTO registries values (153, 21, 10, 1487548800000, "", true);
INSERT INTO registries values (154, 21, 7, 1487548800000, "", true);
INSERT INTO registries values (155, 21, 8, 1487548800000, "", true);
INSERT INTO registries values (156, 21, 9, 1487548800000, "", false);
INSERT INTO registries values (157, 21, 10, 1487548800000, "", true);
INSERT INTO registries values (158, 21, 11, 1487548800000, "", true);
INSERT INTO registries values (159, 22, 5, 1487548800000, "", true);
INSERT INTO registries values (160, 22, 6, 1487548800000, "", true);
INSERT INTO registries values (161, 22, 7, 1487548800000, "", true);
INSERT INTO registries values (162, 22, 8, 1487548800000, "", true);
INSERT INTO registries values (163, 22, 9, 1487548800000, "", false);
INSERT INTO registries values (164, 22, 10, 1487548800000, "", true);
INSERT INTO registries values (165, 23, 11, 1487548800000, "", true);
INSERT INTO registries values (166, 23, 12, 1487548800000, "", false);
INSERT INTO registries values (167, 23, 1, 1487548800000, "", true);
INSERT INTO registries values (168, 23, 2, 1487548800000, "", true);
INSERT INTO registries values (169, 23, 7, 1487548800000, "", true);
INSERT INTO registries values (170, 23, 8, 1487548800000, "", true);
INSERT INTO registries values (171, 23, 9, 1487548800000, "", false);
INSERT INTO registries values (172, 24, 10, 1487548800000, "", true);
INSERT INTO registries values (173, 24, 11, 1487548800000, "", true);
INSERT INTO registries values (174, 24, 12, 1487548800000, "", false);
INSERT INTO registries values (175, 24, 1, 1487548800000, "", true);
INSERT INTO registries values (176, 24, 5, 1487548800000, "", true);
INSERT INTO registries values (177, 24, 6, 1487548800000, "", true);
INSERT INTO registries values (178, 24, 7, 1487548800000, "", true);
INSERT INTO registries values (179, 25, 8, 1487548800000, "", true);
INSERT INTO registries values (180, 25, 9, 1487548800000, "", false);
INSERT INTO registries values (181, 25, 10, 1487548800000, "", true);
INSERT INTO registries values (182, 25, 11, 1487548800000, "", true);
INSERT INTO registries values (183, 25, 12, 1487548800000, "", false);
INSERT INTO registries values (184, 25, 1, 1487548800000, "", true);
INSERT INTO registries values (185, 25, 2, 1487548800000, "", true);
INSERT INTO registries values (186, 26, 3, 1487548800000, "", true);
INSERT INTO registries values (187, 26, 4, 1487548800000, "", true);
INSERT INTO registries values (188, 26, 5, 1487548800000, "", true);
INSERT INTO registries values (189, 26, 6, 1487548800000, "", true);
INSERT INTO registries values (190, 26, 7, 1487548800000, "", true);
INSERT INTO registries values (191, 26, 8, 1487548800000, "", true);
INSERT INTO registries values (192, 26, 12, 1487548800000, "", false);
INSERT INTO registries values (193, 27, 1, 1487548800000, "", true);
INSERT INTO registries values (194, 27, 2, 1487548800000, "", true);
INSERT INTO registries values (195, 27, 3, 1487548800000, "", true);
INSERT INTO registries values (196, 27, 4, 1487548800000, "", true);
INSERT INTO registries values (197, 27, 5, 1487548800000, "", true);
INSERT INTO registries values (198, 27, 6, 1487548800000, "", true);
INSERT INTO registries values (199, 27, 12, 1487548800000, "", false);
INSERT INTO registries values (200, 27, 1, 1487548800000, "", true);
INSERT INTO registries values (201, 28, 2, 1487548800000, "", true);
INSERT INTO registries values (202, 28, 3, 1487548800000, "", true);
INSERT INTO registries values (203, 28, 4, 1487548800000, "", true);
INSERT INTO registries values (204, 28, 5, 1487548800000, "", true);
INSERT INTO registries values (205, 28, 6, 1487548800000, "", true);
INSERT INTO registries values (206, 28, 7, 1487548800000, "", true);
INSERT INTO registries values (207, 28, 8, 1487548800000, "", true);
INSERT INTO registries values (208, 28, 9, 1487548800000, "", false);
INSERT INTO registries values (209, 29, 10, 1487548800000, "", true);
INSERT INTO registries values (210, 29, 11, 1487548800000, "", true);
INSERT INTO registries values (211, 29, 12, 1487548800000, "", false);
INSERT INTO registries values (212, 29, 1, 1487548800000, "", true);
INSERT INTO registries values (213, 29, 2, 1487548800000, "", true);
INSERT INTO registries values (214, 29, 3, 1487548800000, "", true);
INSERT INTO registries values (215, 29, 4, 1487548800000, "", true);
INSERT INTO registries values (216, 30, 9, 1487548800000, "", false);
INSERT INTO registries values (217, 30, 10, 1487548800000, "", true);
INSERT INTO registries values (218, 30, 11, 1487548800000, "", true);
INSERT INTO registries values (219, 30, 12, 1487548800000, "", false);
INSERT INTO registries values (220, 30, 1, 1487548800000, "", true);
INSERT INTO registries values (221, 30, 2, 1487548800000, "", true);
INSERT INTO registries values (222, 30, 3, 1487548800000, "", true);
INSERT INTO registries values (223, 30, 4, 1487548800000, "", true);
INSERT INTO registries values (224, 31, 5, 1487548800000, "", true);
INSERT INTO registries values (225, 31, 6, 1487548800000, "", true);
INSERT INTO registries values (226, 31, 7, 1487548800000, "", true);
INSERT INTO registries values (227, 31, 8, 1487548800000, "", true);
INSERT INTO registries values (228, 31, 9, 1487548800000, "", false);
INSERT INTO registries values (229, 31, 3, 1487548800000, "", true);
INSERT INTO registries values (230, 31, 4, 1487548800000, "", true);
INSERT INTO registries values (231, 31, 5, 1487548800000, "", true);
INSERT INTO registries values (232, 32, 6, 1487548800000, "", true);
INSERT INTO registries values (233, 32, 7, 1487548800000, "", true);
INSERT INTO registries values (234, 32, 8, 1487548800000, "", true);
INSERT INTO registries values (235, 32, 9, 1487548800000, "", false);
INSERT INTO registries values (236, 32, 10, 1487548800000, "", true);
INSERT INTO registries values (237, 32, 11, 1487548800000, "", true);
INSERT INTO registries values (238, 32, 4, 1487548800000, "", true);
INSERT INTO registries values (239, 33, 5, 1487548800000, "", true);
INSERT INTO registries values (240, 33, 6, 1487548800000, "", true);
INSERT INTO registries values (241, 33, 7, 1487548800000, "", true);
INSERT INTO registries values (242, 33, 8, 1487548800000, "", true);
INSERT INTO registries values (243, 33, 9, 1487548800000, "", false);
INSERT INTO registries values (244, 33, 4, 1487548800000, "", true);
INSERT INTO registries values (245, 33, 5, 1487548800000, "", true);
INSERT INTO registries values (246, 33, 6, 1487548800000, "", true);
INSERT INTO registries values (247, 33, 7, 1487548800000, "", true);
INSERT INTO registries values (248, 33, 8, 1487548800000, "", true);
INSERT INTO registries values (249, 34, 9, 1487548800000, "", false);
INSERT INTO registries values (250, 34, 10, 1487548800000, "", true);
INSERT INTO registries values (251, 34, 4, 1487548800000, "", true);
INSERT INTO registries values (252, 34, 5, 1487548800000, "", true);
INSERT INTO registries values (253, 34, 6, 1487548800000, "", true);
INSERT INTO registries values (254, 34, 7, 1487548800000, "", true);
INSERT INTO registries values (255, 34, 8, 1487548800000, "", true);
INSERT INTO registries values (256, 34, 3, 1487548800000, "", true);
INSERT INTO registries values (257, 34, 4, 1487548800000, "", true);
INSERT INTO registries values (258, 34, 5, 1487548800000, "", true);
INSERT INTO registries values (259, 35, 6, 1487548800000, "", true);
INSERT INTO registries values (260, 35, 7, 1487548800000, "", true);
INSERT INTO registries values (261, 35, 1, 1487548800000, "", true);
INSERT INTO registries values (262, 35, 2, 1487548800000, "", true);
INSERT INTO registries values (263, 35, 3, 1487548800000, "", true);
INSERT INTO registries values (264, 35, 4, 1487548800000, "", true);
INSERT INTO registries values (265, 35, 5, 1487548800000, "", true);
INSERT INTO registries values (266, 35, 6, 1487548800000, "", true);
INSERT INTO registries values (267, 36, 7, 1487548800000, "", true);
INSERT INTO registries values (268, 36, 8, 1487548800000, "", true);
INSERT INTO registries values (269, 36, 9, 1487548800000, "", false);
INSERT INTO registries values (270, 36, 10, 1487548800000, "", true);
INSERT INTO registries values (271, 36, 4, 1487548800000, "", true);
INSERT INTO registries values (272, 36, 5, 1487548800000, "", true);
INSERT INTO registries values (273, 37, 6, 1487548800000, "", true);
INSERT INTO registries values (274, 37, 7, 1487548800000, "", true);
INSERT INTO registries values (275, 37, 8, 1487548800000, "", true);
INSERT INTO registries values (276, 37, 9, 1487548800000, "", false);
INSERT INTO registries values (277, 37, 10, 1487548800000, "", true);
INSERT INTO registries values (278, 37, 11, 1487548800000, "", true);
INSERT INTO registries values (279, 38, 12, 1487548800000, "", false);
INSERT INTO registries values (280, 38, 1, 1487548800000, "", true);
INSERT INTO registries values (281, 38, 2, 1487548800000, "", true);
INSERT INTO registries values (282, 38, 7, 1487548800000, "", true);
INSERT INTO registries values (283, 38, 8, 1487548800000, "", true);
INSERT INTO registries values (284, 38, 9, 1487548800000, "", false);
INSERT INTO registries values (285, 38, 10, 1487548800000, "", true);
INSERT INTO registries values (286, 38, 11, 1487548800000, "", true);
INSERT INTO registries values (287, 39, 12, 1487548800000, "", false);
INSERT INTO registries values (288, 39, 1, 1487548800000, "", true);
INSERT INTO registries values (289, 39, 2, 1487548800000, "", true);
INSERT INTO registries values (290, 39, 3, 1487548800000, "", true);
INSERT INTO registries values (291, 39, 4, 1487548800000, "", true);
INSERT INTO registries values (292, 40, 5, 1487548800000, "", true);
INSERT INTO registries values (293, 40, 9, 1487548800000, "", false);
INSERT INTO registries values (294, 40, 10, 1487548800000, "", true);
INSERT INTO registries values (295, 40, 11, 1487548800000, "", true);
INSERT INTO registries values (296, 40, 12, 1487548800000, "", false);
INSERT INTO registries values (297, 40, 1, 1487548800000, "", true);
INSERT INTO registries values (298, 41, 2, 1487548800000, "", true);
INSERT INTO registries values (299, 41, 7, 1487548800000, "", true);
INSERT INTO registries values (300, 41, 8, 1487548800000, "", true);
INSERT INTO registries values (301, 41, 9, 1487548800000, "", false);
INSERT INTO registries values (302, 41, 10, 1487548800000, "", true);
INSERT INTO registries values (303, 41, 11, 1487548800000, "", true);
INSERT INTO registries values (304, 41, 12, 1487548800000, "", false);
SELECT * FROM registries;
--
DROP TABLE IF EXISTS examination;
CREATE TABLE examination (
	ExamID int unique auto_increment not null, PRIMARY KEY (ExamID),
	ClassID int not null, FOREIGN KEY (ClassID) REFERENCES classes(ClassID),
    LearnerID int not null, FOREIGN KEY (LearnerID) REFERENCES learners(LearnerID),
    ExamDate long,
    Mark varchar(10),
    ResultType varchar(10),
    Result boolean,
    ExamRoom varchar(10) not null
) COLLATE utf8_unicode_ci;
INSERT INTO examination values (1, 1, 7,  1503187200000, 5, "", true, "A7");
INSERT INTO examination values (2, 1, 11,  1503187200000, 9, "", true, "A11");
INSERT INTO examination values (3, 1, 11,  1503187200000, 2, "", false, "A11");
INSERT INTO examination values (4, 1, 9,  1503187200000, 3, "", false, "A9");
INSERT INTO examination values (5, 2, 6,  1503187200000, 7, "", true, "A6");
INSERT INTO examination values (6, 2, 11,  1503187200000, 2, "", false, "A11");
INSERT INTO examination values (7, 2, 9,  1503187200000, 5, "", true, "A9");
INSERT INTO examination values (8, 2, 12,  1503187200000, 7, "", true, "A12");
INSERT INTO examination values (9, 2, 7,  1503187200000, 2, "", false, "A7");
INSERT INTO examination values (10, 2, 5,  1503187200000, 8, "", true, "A5");
INSERT INTO examination values (11, 2, 9,  1503187200000, 6, "", true, "A9");
INSERT INTO examination values (12, 3, 7,  1503187200000, 5, "", true, "A7");
INSERT INTO examination values (13, 3, 2,  1503187200000, 6, "", true, "A2");
INSERT INTO examination values (14, 3, 12,  1503187200000, 2, "", false, "A12");
INSERT INTO examination values (15, 3, 10,  1503187200000, 8, "", true, "A10");
INSERT INTO examination values (16, 3, 2,  1503187200000, 5, "", true, "A2");
INSERT INTO examination values (17, 3, 12,  1503187200000, 5, "", true, "A12");
INSERT INTO examination values (18, 3, 11,  1503187200000, 1, "", false, "A11");
INSERT INTO examination values (19, 4, 5,  1503187200000, 4, "", false, "A5");
INSERT INTO examination values (20, 4, 11,  1503187200000, 3, "", false, "A11");
INSERT INTO examination values (21, 4, 3,  1503187200000, 10, "", true, "A3");
INSERT INTO examination values (22, 4, 7,  1503187200000, 2, "", false, "A7");
INSERT INTO examination values (23, 4, 2,  1503187200000, 7, "", true, "A2");
INSERT INTO examination values (24, 4, 6,  1503187200000, 2, "", false, "A6");
INSERT INTO examination values (25, 4, 7,  1503187200000, 3, "", false, "A7");
INSERT INTO examination values (26, 5, 8,  1503187200000, 6, "", true, "A8");
INSERT INTO examination values (27, 5, 10,  1503187200000, 9, "", true, "A10");
INSERT INTO examination values (28, 5, 6,  1503187200000, 10, "", true, "A6");
INSERT INTO examination values (29, 5, 12,  1503187200000, 10, "", true, "A12");
INSERT INTO examination values (30, 5, 10,  1503187200000, 9, "", true, "A10");
INSERT INTO examination values (31, 5, 10,  1503187200000, 2, "", false, "A10");
INSERT INTO examination values (32, 5, 8,  1503187200000, 10, "", true, "A8");
INSERT INTO examination values (33, 6, 3,  1503187200000, 10, "", true, "A3");
INSERT INTO examination values (34, 6, 4,  1503187200000, 0, "", false, "A4");
INSERT INTO examination values (35, 6, 7,  1503187200000, 3, "", false, "A7");
INSERT INTO examination values (36, 6, 3,  1503187200000, 0, "", false, "A3");
INSERT INTO examination values (37, 6, 10,  1503187200000, 7, "", true, "A10");
INSERT INTO examination values (38, 6, 3,  1503187200000, 9, "", true, "A3");
INSERT INTO examination values (39, 6, 7,  1503187200000, 1, "", false, "A7");
INSERT INTO examination values (40, 7, 9,  1503187200000, 6, "", true, "A9");
INSERT INTO examination values (41, 7, 6,  1503187200000, 10, "", true, "A6");
INSERT INTO examination values (42, 7, 10,  1503187200000, 9, "", true, "A10");
INSERT INTO examination values (43, 7, 12,  1503187200000, 2, "", false, "A12");
INSERT INTO examination values (44, 7, 10,  1503187200000, 9, "", true, "A10");
INSERT INTO examination values (45, 7, 6,  1503187200000, 8, "", true, "A6");
INSERT INTO examination values (46, 7, 5,  1503187200000, 7, "", true, "A5");
INSERT INTO examination values (47, 8, 6,  1503187200000, 8, "", true, "A6");
INSERT INTO examination values (48, 8, 9,  1503187200000, 2, "", false, "A9");
INSERT INTO examination values (49, 8, 1,  1503187200000, 5, "", true, "A1");
INSERT INTO examination values (50, 8, 7,  1503187200000, 7, "", true, "A7");
INSERT INTO examination values (51, 8, 7,  1503187200000, 5, "", true, "A7");
INSERT INTO examination values (52, 8, 8,  1503187200000, 5, "", true, "A8");
INSERT INTO examination values (53, 8, 4,  1503187200000, 9, "", true, "A4");
INSERT INTO examination values (54, 9, 4,  1503187200000, 8, "", true, "A4");
INSERT INTO examination values (55, 9, 12,  1503187200000, 2, "", false, "A12");
INSERT INTO examination values (56, 9, 10,  1503187200000, 9, "", true, "A10");
INSERT INTO examination values (57, 9, 3,  1503187200000, 9, "", true, "A3");
INSERT INTO examination values (58, 9, 9,  1503187200000, 2, "", false, "A9");
INSERT INTO examination values (59, 9, 2,  1503187200000, 4, "", false, "A2");
INSERT INTO examination values (60, 9, 6,  1503187200000, 0, "", false, "A6");
INSERT INTO examination values (61, 10, 12,  1503187200000, 9, "", true, "A12");
INSERT INTO examination values (62, 10, 8,  1503187200000, 0, "", false, "A8");
INSERT INTO examination values (63, 10, 10,  1503187200000, 9, "", true, "A10");
INSERT INTO examination values (64, 10, 7,  1503187200000, 9, "", true, "A7");
INSERT INTO examination values (65, 10, 6,  1503187200000, 2, "", false, "A6");
INSERT INTO examination values (66, 10, 3,  1503187200000, 4, "", false, "A3");
INSERT INTO examination values (67, 10, 7,  1503187200000, 5, "", true, "A7");
INSERT INTO examination values (68, 11, 7,  1503187200000, 2, "", false, "A7");
INSERT INTO examination values (69, 11, 1,  1503187200000, 3, "", false, "A1");
INSERT INTO examination values (70, 11, 2,  1503187200000, 3, "", false, "A2");
INSERT INTO examination values (71, 11, 4,  1503187200000, 10, "", true, "A4");
INSERT INTO examination values (72, 11, 10,  1503187200000, 9, "", true, "A10");
INSERT INTO examination values (73, 11, 6,  1503187200000, 4, "", false, "A6");
INSERT INTO examination values (74, 11, 10,  1503187200000, 9, "", true, "A10");
INSERT INTO examination values (75, 12, 5,  1503187200000, 9, "", true, "A5");
INSERT INTO examination values (76, 12, 7,  1503187200000, 10, "", true, "A7");
INSERT INTO examination values (77, 12, 5,  1503187200000, 5, "", true, "A5");
INSERT INTO examination values (78, 12, 5,  1503187200000, 6, "", true, "A5");
INSERT INTO examination values (79, 12, 2,  1503187200000, 2, "", false, "A2");
INSERT INTO examination values (80, 12, 10,  1503187200000, 7, "", true, "A10");
INSERT INTO examination values (81, 12, 5,  1503187200000, 3, "", false, "A5");
INSERT INTO examination values (82, 13, 6,  1503187200000, 1, "", false, "A6");
INSERT INTO examination values (83, 13, 4,  1503187200000, 1, "", false, "A4");
INSERT INTO examination values (84, 13, 10,  1503187200000, 1, "", false, "A10");
INSERT INTO examination values (85, 13, 7,  1503187200000, 1, "", false, "A7");
INSERT INTO examination values (86, 13, 11,  1503187200000, 1, "", false, "A11");
INSERT INTO examination values (87, 13, 5,  1503187200000, 5, "", true, "A5");
INSERT INTO examination values (88, 13, 2,  1503187200000, 1, "", false, "A2");
INSERT INTO examination values (89, 14, 1,  1503187200000, 1, "", false, "A1");
INSERT INTO examination values (90, 14, 12,  1503187200000, 5, "", true, "A12");
INSERT INTO examination values (91, 14, 1,  1503187200000, 9, "", true, "A1");
INSERT INTO examination values (92, 14, 5,  1503187200000, 8, "", true, "A5");
INSERT INTO examination values (93, 14, 1,  1503187200000, 3, "", false, "A1");
INSERT INTO examination values (94, 14, 3,  1503187200000, 7, "", true, "A3");
INSERT INTO examination values (95, 14, 6,  1503187200000, 1, "", false, "A6");
INSERT INTO examination values (96, 15, 8,  1503187200000, 3, "", false, "A8");
INSERT INTO examination values (97, 15, 12,  1503187200000, 4, "", false, "A12");
INSERT INTO examination values (98, 15, 8,  1503187200000, 1, "", false, "A8");
INSERT INTO examination values (99, 15, 5,  1503187200000, 6, "", true, "A5");
INSERT INTO examination values (100, 15, 7,  1503187200000, 7, "", true, "A7");
INSERT INTO examination values (101, 15, 1,  1503187200000, 3, "", false, "A1");
INSERT INTO examination values (102, 15, 6,  1503187200000, 5, "", true, "A6");
INSERT INTO examination values (103, 16, 7,  1503187200000, 9, "", true, "A7");
INSERT INTO examination values (104, 16, 2,  1503187200000, 3, "", false, "A2");
INSERT INTO examination values (105, 16, 3,  1503187200000, 5, "", true, "A3");
INSERT INTO examination values (106, 16, 7,  1503187200000, 9, "", true, "A7");
INSERT INTO examination values (107, 16, 6,  1503187200000, 5, "", true, "A6");
INSERT INTO examination values (108, 16, 4,  1503187200000, 1, "", false, "A4");
INSERT INTO examination values (109, 16, 8,  1503187200000, 6, "", true, "A8");
INSERT INTO examination values (110, 17, 1,  1503187200000, 6, "", true, "A1");
INSERT INTO examination values (111, 17, 6,  1503187200000, 2, "", false, "A6");
INSERT INTO examination values (112, 17, 10,  1503187200000, 4, "", false, "A10");
INSERT INTO examination values (113, 17, 10,  1503187200000, 4, "", false, "A10");
INSERT INTO examination values (114, 17, 2,  1503187200000, 8, "", true, "A2");
INSERT INTO examination values (115, 17, 10,  1503187200000, 4, "", false, "A10");
INSERT INTO examination values (116, 17, 2,  1503187200000, 4, "", false, "A2");
INSERT INTO examination values (117, 18, 3,  1503187200000, 5, "", true, "A3");
INSERT INTO examination values (118, 18, 6,  1503187200000, 1, "", false, "A6");
INSERT INTO examination values (119, 18, 8,  1503187200000, 9, "", true, "A8");
INSERT INTO examination values (120, 18, 3,  1503187200000, 9, "", true, "A3");
INSERT INTO examination values (121, 18, 9,  1503187200000, 10, "", true, "A9");
INSERT INTO examination values (122, 18, 12,  1503187200000, 5, "", true, "A12");
INSERT INTO examination values (123, 18, 8,  1503187200000, 2, "", false, "A8");
INSERT INTO examination values (124, 19, 8,  1503187200000, 1, "", false, "A8");
INSERT INTO examination values (125, 19, 9,  1503187200000, 8, "", true, "A9");
INSERT INTO examination values (126, 19, 1,  1503187200000, 4, "", false, "A1");
INSERT INTO examination values (127, 19, 8,  1503187200000, 1, "", false, "A8");
INSERT INTO examination values (128, 19, 9,  1503187200000, 4, "", false, "A9");
INSERT INTO examination values (129, 19, 3,  1503187200000, 3, "", false, "A3");
INSERT INTO examination values (130, 19, 3,  1503187200000, 4, "", false, "A3");
INSERT INTO examination values (131, 20, 7,  1503187200000, 4, "", false, "A7");
INSERT INTO examination values (132, 20, 3,  1503187200000, 6, "", true, "A3");
INSERT INTO examination values (133, 20, 8,  1503187200000, 4, "", false, "A8");
INSERT INTO examination values (134, 20, 10,  1503187200000, 0, "", false, "A10");
INSERT INTO examination values (135, 20, 10,  1503187200000, 7, "", true, "A10");
INSERT INTO examination values (136, 20, 1,  1503187200000, 5, "", true, "A1");
INSERT INTO examination values (137, 20, 7,  1503187200000, 9, "", true, "A7");
INSERT INTO examination values (138, 21, 8,  1503187200000, 2, "", false, "A8");
INSERT INTO examination values (139, 21, 7,  1503187200000, 4, "", false, "A7");
INSERT INTO examination values (140, 21, 12,  1503187200000, 7, "", true, "A12");
INSERT INTO examination values (141, 21, 10,  1503187200000, 8, "", true, "A10");
INSERT INTO examination values (142, 21, 10,  1503187200000, 9, "", true, "A10");
INSERT INTO examination values (143, 21, 11,  1503187200000, 1, "", false, "A11");
INSERT INTO examination values (144, 21, 10,  1503187200000, 0, "", false, "A10");
INSERT INTO examination values (145, 22, 9,  1503187200000, 9, "", true, "A9");
INSERT INTO examination values (146, 22, 11,  1503187200000, 0, "", false, "A11");
INSERT INTO examination values (147, 22, 6,  1503187200000, 5, "", true, "A6");
INSERT INTO examination values (148, 22, 3,  1503187200000, 3, "", false, "A3");
INSERT INTO examination values (149, 22, 4,  1503187200000, 3, "", false, "A4");
INSERT INTO examination values (150, 22, 5,  1503187200000, 8, "", true, "A5");
INSERT INTO examination values (151, 22, 7,  1503187200000, 6, "", true, "A7");
INSERT INTO examination values (152, 23, 5,  1503187200000, 1, "", false, "A5");
INSERT INTO examination values (153, 23, 1,  1503187200000, 8, "", true, "A1");
INSERT INTO examination values (154, 23, 2,  1503187200000, 7, "", true, "A2");
INSERT INTO examination values (155, 23, 2,  1503187200000, 6, "", true, "A2");
INSERT INTO examination values (156, 23, 7,  1503187200000, 1, "", false, "A7");
INSERT INTO examination values (157, 23, 7,  1503187200000, 6, "", true, "A7");
INSERT INTO examination values (158, 23, 3,  1503187200000, 2, "", false, "A3");
INSERT INTO examination values (159, 24, 5,  1503187200000, 3, "", false, "A5");
INSERT INTO examination values (160, 24, 5,  1503187200000, 5, "", true, "A5");
INSERT INTO examination values (161, 24, 5,  1503187200000, 5, "", true, "A5");
INSERT INTO examination values (162, 24, 6,  1503187200000, 6, "", true, "A6");
INSERT INTO examination values (163, 24, 7,  1503187200000, 1, "", false, "A7");
INSERT INTO examination values (164, 24, 5,  1503187200000, 3, "", false, "A5");
INSERT INTO examination values (165, 24, 3,  1503187200000, 5, "", true, "A3");
INSERT INTO examination values (166, 25, 11,  1503187200000, 8, "", true, "A11");
INSERT INTO examination values (167, 25, 5,  1503187200000, 6, "", true, "A5");
INSERT INTO examination values (168, 25, 10,  1503187200000, 3, "", false, "A10");
INSERT INTO examination values (169, 25, 10,  1503187200000, 9, "", true, "A10");
INSERT INTO examination values (170, 25, 11,  1503187200000, 7, "", true, "A11");
INSERT INTO examination values (171, 25, 3,  1503187200000, 2, "", false, "A3");
INSERT INTO examination values (172, 25, 4,  1503187200000, 1, "", false, "A4");
INSERT INTO examination values (173, 26, 2,  1503187200000, 8, "", true, "A2");
INSERT INTO examination values (174, 26, 1,  1503187200000, 4, "", false, "A1");
INSERT INTO examination values (175, 26, 4,  1503187200000, 6, "", true, "A4");
INSERT INTO examination values (176, 26, 7,  1503187200000, 7, "", true, "A7");
INSERT INTO examination values (177, 26, 8,  1503187200000, 6, "", true, "A8");
INSERT INTO examination values (178, 26, 6,  1503187200000, 0, "", false, "A6");
INSERT INTO examination values (179, 26, 11,  1503187200000, 0, "", false, "A11");
INSERT INTO examination values (180, 27, 3,  1503187200000, 3, "", false, "A3");
INSERT INTO examination values (181, 27, 2,  1503187200000, 4, "", false, "A2");
INSERT INTO examination values (182, 27, 8,  1503187200000, 7, "", true, "A8");
INSERT INTO examination values (183, 27, 9,  1503187200000, 6, "", true, "A9");
INSERT INTO examination values (184, 27, 7,  1503187200000, 7, "", true, "A7");
INSERT INTO examination values (185, 27, 4,  1503187200000, 9, "", true, "A4");
INSERT INTO examination values (186, 27, 4,  1503187200000, 4, "", false, "A4");
INSERT INTO examination values (187, 28, 8,  1503187200000, 7, "", true, "A8");
INSERT INTO examination values (188, 28, 12,  1503187200000, 10, "", true, "A12");
INSERT INTO examination values (189, 28, 5,  1503187200000, 0, "", false, "A5");
INSERT INTO examination values (190, 28, 8,  1503187200000, 8, "", true, "A8");
INSERT INTO examination values (191, 28, 1,  1503187200000, 8, "", true, "A1");
INSERT INTO examination values (192, 28, 11,  1503187200000, 3, "", false, "A11");
INSERT INTO examination values (193, 28, 3,  1503187200000, 8, "", true, "A3");
INSERT INTO examination values (194, 29, 1,  1503187200000, 5, "", true, "A1");
INSERT INTO examination values (195, 29, 10,  1503187200000, 2, "", false, "A10");
INSERT INTO examination values (196, 29, 11,  1503187200000, 5, "", true, "A11");
INSERT INTO examination values (197, 29, 8,  1503187200000, 8, "", true, "A8");
INSERT INTO examination values (198, 29, 2,  1503187200000, 7, "", true, "A2");
INSERT INTO examination values (199, 29, 5,  1503187200000, 7, "", true, "A5");
INSERT INTO examination values (200, 29, 8,  1503187200000, 1, "", false, "A8");
INSERT INTO examination values (201, 30, 4,  1503187200000, 4, "", false, "A4");
INSERT INTO examination values (202, 30, 9,  1503187200000, 7, "", true, "A9");
INSERT INTO examination values (203, 30, 4,  1503187200000, 4, "", false, "A4");
INSERT INTO examination values (204, 30, 3,  1503187200000, 2, "", false, "A3");
INSERT INTO examination values (205, 30, 3,  1503187200000, 3, "", false, "A3");
INSERT INTO examination values (206, 30, 2,  1503187200000, 1, "", false, "A2");
INSERT INTO examination values (207, 30, 9,  1503187200000, 7, "", true, "A9");
INSERT INTO examination values (208, 31, 7,  1503187200000, 1, "", false, "A7");
INSERT INTO examination values (209, 31, 5,  1503187200000, 6, "", true, "A5");
INSERT INTO examination values (210, 31, 10,  1503187200000, 0, "", false, "A10");
INSERT INTO examination values (211, 31, 10,  1503187200000, 2, "", false, "A10");
INSERT INTO examination values (212, 31, 8,  1503187200000, 3, "", false, "A8");
INSERT INTO examination values (213, 31, 6,  1503187200000, 4, "", false, "A6");
INSERT INTO examination values (214, 31, 3,  1503187200000, 2, "", false, "A3");
INSERT INTO examination values (215, 32, 3,  1503187200000, 0, "", false, "A3");
INSERT INTO examination values (216, 32, 4,  1503187200000, 10, "", true, "A4");
INSERT INTO examination values (217, 32, 9,  1503187200000, 9, "", true, "A9");
INSERT INTO examination values (218, 32, 3,  1503187200000, 1, "", false, "A3");
INSERT INTO examination values (219, 32, 10,  1503187200000, 10, "", true, "A10");
INSERT INTO examination values (220, 32, 5,  1503187200000, 1, "", false, "A5");
INSERT INTO examination values (221, 32, 8,  1503187200000, 4, "", false, "A8");
INSERT INTO examination values (222, 33, 10,  1503187200000, 3, "", false, "A10");
INSERT INTO examination values (223, 33, 11,  1503187200000, 4, "", false, "A11");
INSERT INTO examination values (224, 33, 9,  1503187200000, 4, "", false, "A9");
INSERT INTO examination values (225, 33, 5,  1503187200000, 5, "", true, "A5");
INSERT INTO examination values (226, 33, 12,  1503187200000, 3, "", false, "A12");
INSERT INTO examination values (227, 33, 8,  1503187200000, 8, "", true, "A8");
INSERT INTO examination values (228, 33, 7,  1503187200000, 8, "", true, "A7");
INSERT INTO examination values (229, 34, 3,  1503187200000, 6, "", true, "A3");
INSERT INTO examination values (230, 34, 5,  1503187200000, 9, "", true, "A5");
INSERT INTO examination values (231, 34, 7,  1503187200000, 9, "", true, "A7");
INSERT INTO examination values (232, 34, 10,  1503187200000, 5, "", true, "A10");
INSERT INTO examination values (233, 34, 11,  1503187200000, 6, "", true, "A11");
INSERT INTO examination values (234, 34, 10,  1503187200000, 2, "", false, "A10");
INSERT INTO examination values (235, 34, 9,  1503187200000, 3, "", false, "A9");
INSERT INTO examination values (236, 35, 5,  1503187200000, 5, "", true, "A5");
INSERT INTO examination values (237, 35, 2,  1503187200000, 1, "", false, "A2");
INSERT INTO examination values (238, 35, 11,  1503187200000, 1, "", false, "A11");
INSERT INTO examination values (239, 35, 8,  1503187200000, 3, "", false, "A8");
INSERT INTO examination values (240, 35, 10,  1503187200000, 3, "", false, "A10");
INSERT INTO examination values (241, 35, 12,  1503187200000, 9, "", true, "A12");
INSERT INTO examination values (242, 35, 9,  1503187200000, 1, "", false, "A9");
INSERT INTO examination values (243, 36, 10,  1503187200000, 10, "", true, "A10");
INSERT INTO examination values (244, 36, 5,  1503187200000, 0, "", false, "A5");
INSERT INTO examination values (245, 36, 11,  1503187200000, 3, "", false, "A11");
INSERT INTO examination values (246, 36, 2,  1503187200000, 4, "", false, "A2");
INSERT INTO examination values (247, 36, 11,  1503187200000, 0, "", false, "A11");
INSERT INTO examination values (248, 36, 11,  1503187200000, 7, "", true, "A11");
INSERT INTO examination values (249, 36, 6,  1503187200000, 9, "", true, "A6");
INSERT INTO examination values (250, 37, 4,  1503187200000, 2, "", false, "A4");
INSERT INTO examination values (251, 37, 8,  1503187200000, 7, "", true, "A8");
INSERT INTO examination values (252, 37, 11,  1503187200000, 5, "", true, "A11");
INSERT INTO examination values (253, 37, 12,  1503187200000, 7, "", true, "A12");
INSERT INTO examination values (254, 37, 12,  1503187200000, 10, "", true, "A12");
INSERT INTO examination values (255, 37, 2,  1503187200000, 2, "", false, "A2");
INSERT INTO examination values (256, 37, 6,  1503187200000, 9, "", true, "A6");
INSERT INTO examination values (257, 38, 2,  1503187200000, 9, "", true, "A2");
INSERT INTO examination values (258, 38, 10,  1503187200000, 6, "", true, "A10");
INSERT INTO examination values (259, 38, 7,  1503187200000, 2, "", false, "A7");
INSERT INTO examination values (260, 38, 4,  1503187200000, 3, "", false, "A4");
INSERT INTO examination values (261, 38, 11,  1503187200000, 9, "", true, "A11");
INSERT INTO examination values (262, 38, 5,  1503187200000, 9, "", true, "A5");
INSERT INTO examination values (263, 38, 5,  1503187200000, 4, "", false, "A5");
INSERT INTO examination values (264, 39, 7,  1503187200000, 8, "", true, "A7");
INSERT INTO examination values (265, 39, 11,  1503187200000, 3, "", false, "A11");
INSERT INTO examination values (266, 39, 11,  1503187200000, 4, "", false, "A11");
INSERT INTO examination values (267, 39, 10,  1503187200000, 5, "", true, "A10");
INSERT INTO examination values (268, 39, 11,  1503187200000, 3, "", false, "A11");
INSERT INTO examination values (269, 39, 4,  1503187200000, 6, "", true, "A4");
INSERT INTO examination values (270, 39, 2,  1503187200000, 7, "", true, "A2");
INSERT INTO examination values (271, 40, 11,  1503187200000, 5, "", true, "A11");
INSERT INTO examination values (272, 40, 3,  1503187200000, 9, "", true, "A3");
INSERT INTO examination values (273, 40, 7,  1503187200000, 4, "", false, "A7");
INSERT INTO examination values (274, 40, 4,  1503187200000, 3, "", false, "A4");
INSERT INTO examination values (275, 40, 12,  1503187200000, 5, "", true, "A12");
INSERT INTO examination values (276, 40, 2,  1503187200000, 2, "", false, "A2");
INSERT INTO examination values (277, 40, 7,  1503187200000, 7, "", true, "A7");
INSERT INTO examination values (278, 41, 3,  1503187200000, 7, "", true, "A3");
INSERT INTO examination values (279, 41, 12,  1503187200000, 3, "", false, "A12");
INSERT INTO examination values (280, 41, 1,  1503187200000, 3, "", false, "A1");
INSERT INTO examination values (281, 41, 11,  1503187200000, 0, "", false, "A11");
INSERT INTO examination values (282, 41, 2,  1503187200000, 7, "", true, "A2");
INSERT INTO examination values (283, 41, 2,  1503187200000, 5, "", true, "A2");
INSERT INTO examination values (284, 41, 2,  1503187200000, 2, "", false, "A2");
SELECT * FROM examination;
--
DROP TABLE IF EXISTS feedbacks;
CREATE TABLE feedbacks (
	FeedbackID int unique auto_increment not null, PRIMARY KEY (FeedbackID),
	FullName varchar(255),
    Phone varchar(30),
    Email varchar(50),
    Title varchar(255),
    Content text,
    submittedDate long    
) COLLATE utf8_unicode_ci;
INSERT INTO feedbacks values (1, "Nguyễn Văn A", "03434", "a03434@gmail.com", "Góp ý", "dsfdsfs1503187200000",  1531008000000);
Select * from feedbacks;
--
DROP TABLE IF EXISTS users;
CREATE TABLE users (
	Userid varchar(50) unique not null, PRIMARY KEY (Userid),
    Pwd varchar(30),
    Role varchar(50),
    Email varchar(50),
    CreatedDate long    
) COLLATE utf8_unicode_ci;
INSERT INTO users values ("admin", "ekinsdns394kndsl29", "admin", "seafec2014@gmail.com", 1531008000000);
INSERT INTO users values ("nhanvien", "kdjfl3kljfdl893", "moderator", "", 1531008000000);
Select * from users;

select co.Title, b.TimeTable, b.Room, b.OpenDate, b.Deposit, co.Fee, b.FeeStatus from 
	(select a.LearnerID, a.ClassID, a.Deposit, a.FeeStatus, cl.OpenDate, cl.TimeTable, cl.Room, cl.CourseID from 
		(select l.learnerid, r.classid, r.Deposit, r.FeeStatus from (select * from learners where IDcard='0123456789') l 
		inner join registries r on l.learnerid=r.learnerid) a 
	inner join classes cl on a.classid=cl.classid) 
b inner join courses co on co.CourseID=b.CourseID;

--
DROP TABLE IF EXISTS favcourses;
CREATE TABLE favcourses (
	favID int unique auto_increment not null, PRIMARY KEY (favID),
	CourseID int not null, FOREIGN KEY (CourseID) REFERENCES courses(CourseID), 
    LearnerID int not null, FOREIGN KEY (LearnerID) REFERENCES learners(LearnerID)
) COLLATE utf8_unicode_ci;
INSERT INTO favcourses values(1, 1, 12);
SELECT * FROM favcourses;

DROP TABLE IF EXISTS message;
CREATE TABLE message (
	id int unique auto_increment not null, PRIMARY KEY (id),
	msg text not null,
    createDate long,
    sendDate long
) COLLATE utf8_unicode_ci;

INSERT INTO message values(1, "Hello", 1, 13);
INSERT INTO message values(2, "Hello", 1, 13);
SELECT * FROM message;