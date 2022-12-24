Drop Database if exists ChattingSystem;
Create database ChattingSystem;
Use ChattingSystem;

-- Tạo Bảng
Create table TaiKhoan (
	username varchar(20),
    pass tinytext,
    hoten varchar(50) charset utf8mb4,
    email tinytext,
    dob date,
    diachi tinytext,
    gioitinh bool,
    isAdmin bool,
    thoigiandangnhap datetime,
    ngaytao datetime,
    primary key (username)
);

Create table BanBe (
	user_username varchar(20),
    friend_username varchar(20),
    primary key (user_username, friend_username)
);

Create table LoiMoiKetBan (
	receiver_username varchar(20),
	sender_username varchar(20),
    primary key (receiver_username, sender_username)
);

Create table Nhom (
	ID_nhom varchar(8),
    tennhom varchar(30) charset utf8mb4,
    tinnhan longtext,
    ngaytaonhom datetime,
    primary key (ID_nhom)
);

Create table ThanhVienNhom (
	ID_nhom varchar(8),
    username varchar(20),
    isGroupAdmin bool,
    primary key (ID_nhom, username)
);

Create table LichSuDangNhap (
	username varchar(20),
    thoigiandangnhap datetime,
    primary key (username, thoigiandangnhap)
);

-- Tạo Khóa Ngoại
Alter Table BanBe
Add Foreign Key(user_username) references TaiKhoan(username);

Alter Table BanBe
Add Foreign Key(friend_username) references TaiKhoan(username);

Alter Table ThanhVienNhom
Add Foreign Key(ID_nhom) references Nhom(ID_nhom);

Alter Table ThanhVienNhom
Add Foreign Key(username) references TaiKhoan(username);

Alter Table LichSuDangNhap
Add Foreign Key(username) references TaiKhoan(username);

Alter Table LoiMoiKetBan
Add Foreign Key(receiver_username) references TaiKhoan(username),
Add Foreign Key(sender_username) references TaiKhoan(username);



-- Tạo dữ liệu mẫu
insert into TaiKhoan(username, pass, hoten, email, dob, diachi, gioitinh, isAdmin, thoigiandangnhap, ngaytao)
values 
("nnquang", "123", "Nguyễn Ngọc Quang", "nnquang20@clc.fitus.edu.vn", "2002-01-01", "227 Nguyễn Văn Cừ", true, false, current_timestamp(), "2022-12-22"),
("htlam", "123", "Hà Tuấn Lâm", "htlam20@clc.fitus.edu.vn", "2002-01-01", "227 Nguyễn Văn Cừ", true, false, current_timestamp(), "2022-12-22"),
("ntphu", "123", "Nguyễn Thiên Phú", "ntphu20@clc.fitus.edu.vn", "2002-01-01", "227 Nguyễn Văn Cừ", true, false, current_timestamp(), "2022-12-22"),
("tghuy", "123", "Trương Gia Huy", "tghuy20@clc.fitus.edu.vn", "2002-01-01", "227 Nguyễn Văn Cừ", true, false, current_timestamp(), "2022-12-22"),
("lhminh", "123", "Lưu Hoàng Minh", "lhminh20@clc.fitus.edu.vn", "2002-01-01", "227 Nguyễn Văn Cừ", true, false, current_timestamp(), "2022-12-22"),
("admin1", "123", "Admin 1", "admin1@gmail.com", "2002-01-01", "227 Nguyễn Văn Cừ", true, true, current_timestamp(), "2022-12-22"),
("dmtung", "123", "Dương Minh Tùng", "dmtung20@clc.fitus.edu.vn", "2002-01-01", "227 Nguyễn Văn Cừ", true, false, current_timestamp(), "2022-12-22");

insert into BanBe(user_username, friend_username)
values ("nnquang", "htlam"),
	("nnquang", "ntphu"),
	("nnquang", "tghuy"),
	("nnquang", "lhminh"),
	("htlam", "nnquang"),
	("htlam", "ntphu"),
	("htlam", "tghuy"),
	("htlam", "lhminh"),
	("ntphu", "nnquang"),
	("ntphu", "htlam"),
	("ntphu", "tghuy"),
	("ntphu", "lhminh"),
	("tghuy", "nnquang"),
	("tghuy", "htlam"),
	("tghuy", "ntphu"),
	("tghuy", "lhminh"),
	("lhminh", "nnquang"),
	("lhminh", "htlam"),
	("lhminh", "ntphu"),
	("lhminh", "tghuy");
    
insert into LoiMoiKetBan(sender_username, receiver_username)
values ("nnquang", "htlam"),
	("nnquang", "ntphu"),
	("htlam", "nnquang"),
	("htlam", "ntphu"),
	("ntphu", "nnquang"),
	("ntphu", "htlam"),
	("tghuy", "nnquang"),
	("tghuy", "htlam"),
	("lhminh", "nnquang"),
	("lhminh", "htlam");