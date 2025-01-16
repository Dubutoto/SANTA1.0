USE
SANTA;

-- 1. Categories
CREATE TABLE Categories
(
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(20) NOT NULL UNIQUE, -- 카테고리 이름
    description VARCHAR(100)
);

-- 2. Manufacturers
CREATE TABLE Manufacturers
(
    manufacturer_id INT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(50) NOT NULL,
    location        VARCHAR(20),
    contact         VARCHAR(50) CHECK (
        contact REGEXP '^010-[0-9]{3,4}-[0-9]{4}$' OR
        contact REGEXP '^(02|0[3-6][1-4]|05[1-5]|06[1-4])-[0-9]{3}-[0-9]{4}$'
)
    );

-- 3. Suppliers
CREATE TABLE Suppliers
(
    supplier_id INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    contact     VARCHAR(50) CHECK (
        contact REGEXP '^010-[0-9]{3,4}-[0-9]{4}$' OR
        contact REGEXP '^(02|0[3-6][1-4]|05[1-5]|06[1-4])-[0-9]{3}-[0-9]{4}$'
) ,
    location VARCHAR(255)
);

-- 4. Warehouses
CREATE TABLE Warehouses
(
    warehouse_id INT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(20) NOT NULL,
    location     VARCHAR(25) NOT NULL,
    capacity     INT         NOT NULL CHECK (capacity >= 0), -- 창고 용량은 음수가 될 수 없음
    latitude DECIMAL(11, 8) NOT NULL,          -- 위도 (소수점 8자리까지)
    longitude DECIMAL(11, 8) NOT NULL          -- 경도 (소수점 8자리까지)
);

-- 5. Branches
CREATE TABLE Branches
(
    branch_id INT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(100) NOT NULL,
    location  VARCHAR(255) NOT NULL,
    latitude DECIMAL(11, 8) NOT NULL,          -- 위도 (소수점 8자리까지)
    longitude DECIMAL(11, 8) NOT NULL          -- 경도 (소수점 8자리까지)
);

-- 6. EmployeeRoles
CREATE TABLE EmployeeRoles
(
    employee_code VARCHAR(15) PRIMARY KEY,         -- 사원코드 (고유)
    role          ENUM('root', 'general') NOT NULL -- 역할 (루트 관리자, 일반 관리자)
);

-- 7. Administrators
CREATE TABLE Administrators
(
    admin_id      INT AUTO_INCREMENT PRIMARY KEY,      -- 관리자 ID (고유)
    user_id       VARCHAR(50) UNIQUE  NOT NULL,        -- 로그인용 사용자 ID (중복 불가)
    password      VARCHAR(255)        NOT NULL,        -- 로그인용 비밀번호 (암호화 필요)
    employee_code VARCHAR(50) UNIQUE  NOT NULL,        -- 사원코드
    role          ENUM('root', 'general') NOT NULL,    -- 관리자 역할 (루트 관리자, 일반 관리자)
    name          VARCHAR(100)        NOT NULL,        -- 관리자 이름
    email         VARCHAR(100) UNIQUE NOT NULL,        -- 이메일 (중복 불가)
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 생성일
    CONSTRAINT fk_employee_code FOREIGN KEY (employee_code) REFERENCES EmployeeRoles (employee_code)
);

-- 8. Products
CREATE TABLE Products
(
    product_id      INT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(255) NOT NULL,
    description     TEXT,
    category_id     INT,
    price DOUBLE NOT NULL,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    manufacturer_id INT,
    FOREIGN KEY (category_id) REFERENCES Categories (category_id) ON DELETE SET NULL,
    FOREIGN KEY (manufacturer_id) REFERENCES Manufacturers (manufacturer_id) ON DELETE SET NULL
);

-- 9. Administrator_Warehouses
CREATE TABLE Administrator_Warehouses
(
    admin_warehouse_id INT AUTO_INCREMENT PRIMARY KEY,      -- 관계 ID
    admin_id           INT NOT NULL,                        -- 관리자 ID
    warehouse_id       INT NOT NULL,                        -- 창고 ID
    assigned_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 할당 시간
    FOREIGN KEY (admin_id) REFERENCES Administrators (admin_id) ON DELETE CASCADE,
    FOREIGN KEY (warehouse_id) REFERENCES Warehouses (warehouse_id) ON DELETE CASCADE
);

-- 10. Warehouse_Inventory
CREATE TABLE Warehouse_Inventory
(
    inventory_id INT AUTO_INCREMENT PRIMARY KEY,
    warehouse_id INT NOT NULL,
    product_id   INT,
    quantity     INT NOT NULL CHECK (quantity >= 0),
    last_updated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (warehouse_id) REFERENCES Warehouses (warehouse_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES Products (product_id) ON DELETE SET NULL,
    UNIQUE (warehouse_id, product_id)
);

-- 11. Orders
CREATE TABLE Orders
(
    order_id     INT AUTO_INCREMENT PRIMARY KEY,    -- 주문 ID
    warehouse_id INT NOT NULL,                      -- 배송될 창고
    branch_id    INT,                               -- 주문 발생 지점
    product_id   INT NOT NULL,                      -- 주문된 상품 ID
    quantity     INT NOT NULL CHECK (quantity > 0), -- 주문된 상품 수량
    status       ENUM('대기', '승인', '거절') NOT NULL,   -- 주문 상태
    order_date   DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (warehouse_id) REFERENCES Warehouses (warehouse_id) ON DELETE CASCADE,
    FOREIGN KEY (branch_id) REFERENCES Branches (branch_id) ON DELETE SET NULL,
    FOREIGN KEY (product_id) REFERENCES Products (product_id) ON DELETE CASCADE
);

-- 12. Supplier_Products
CREATE TABLE Supplier_Products
(
    supplier_product_id INT AUTO_INCREMENT PRIMARY KEY,
    supplier_id         INT NOT NULL,
    product_id          INT NOT NULL,
    UNIQUE (supplier_id, product_id),
    FOREIGN KEY (supplier_id) REFERENCES Suppliers (supplier_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES Products (product_id) ON DELETE CASCADE
);

-- 13. Incoming
CREATE TABLE Incoming
(
    incoming_id   INT AUTO_INCREMENT PRIMARY KEY,
    warehouse_id  INT NOT NULL,
    product_id    INT NOT NULL,
    supplier_id   INT,
    quantity      INT NOT NULL CHECK (quantity > 0),
    incoming_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (warehouse_id) REFERENCES Warehouses (warehouse_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES Products (product_id) ON DELETE CASCADE,
    FOREIGN KEY (supplier_id) REFERENCES Suppliers (supplier_id) ON DELETE SET NULL
);

-- 14. Outgoing
CREATE TABLE Outgoing
(
    outgoing_id   INT AUTO_INCREMENT PRIMARY KEY,
    warehouse_id  INT NOT NULL,
    product_id    INT,
    order_id      INT,
    branch_id     INT,
    quantity      INT NOT NULL CHECK (quantity > 0),
    outgoing_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status        ENUM('대기', '승인', '거절') NOT NULL,
    FOREIGN KEY (warehouse_id) REFERENCES Warehouses (warehouse_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES Products (product_id) ON DELETE SET NULL,
    FOREIGN KEY (order_id) REFERENCES Orders (order_id) ON DELETE SET NULL,
    FOREIGN KEY (branch_id) REFERENCES Orders (branch_id) ON DELETE SET NULL
);

-- 15. Board
CREATE TABLE Board
(
    boardId   INT AUTO_INCREMENT PRIMARY KEY,
    boardType ENUM('NOTICE', 'GENERAL') NOT NULL,
    authorId  INT  NOT NULL,
    title     VARCHAR(255),
    content   TEXT NOT NULL,
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (authorId) REFERENCES Administrators (admin_id) ON DELETE CASCADE
);

-- 16. reply (replyId PK, boardId→Board(boardId)로 수정)
CREATE TABLE reply
(
    replyId  INT AUTO_INCREMENT PRIMARY KEY,
    boardId  INT  NOT NULL,
    authorId INT  NOT NULL,
    content  TEXT NOT NULL,
    FOREIGN KEY (boardId) REFERENCES Board (boardId) ON DELETE CASCADE,
    FOREIGN KEY (authorId) REFERENCES Administrators (admin_id) ON DELETE CASCADE
);

-- 17. Transit
CREATE TABLE Transit
(
    transit_id   INT AUTO_INCREMENT PRIMARY KEY,
    outgoing_id  INT NOT NULL,
    warehouse_id INT,
    branch_id    INT,
    product_id   INT,
    quantity     INT NOT NULL CHECK (quantity > 0),
    transit_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status       ENUM('배송대기', '배송완료', '배송취소', '배송중') NOT NULL,
    FOREIGN KEY (outgoing_id) REFERENCES Outgoing (outgoing_id) ON DELETE CASCADE,
    FOREIGN KEY (warehouse_id) REFERENCES Warehouses (warehouse_id) ON DELETE SET NULL,
    FOREIGN KEY (branch_id) REFERENCES Branches (branch_id) ON DELETE SET NULL,
    FOREIGN KEY (product_id) REFERENCES Products (product_id) ON DELETE SET NULL
);

-- 18. password_reset_tokens
CREATE TABLE password_reset_tokens
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    email      VARCHAR(255) NOT NULL,
    token      VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 19. notice_categories
CREATE TABLE notice_categories
(
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50) NOT NULL COMMENT '카테고리 이름'
);

-- 20. Notice
CREATE TABLE Notice
(
    notice_id   INT AUTO_INCREMENT PRIMARY KEY,
    author_id   INT  NOT NULL,
    title       VARCHAR(255),
    content     TEXT NOT NULL,
    parent_id   INT      DEFAULT NULL,
    category_id INT  NOT NULL,
    views       INT      DEFAULT 0,
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES Administrators (admin_id) ON DELETE CASCADE,
    FOREIGN KEY (parent_id) REFERENCES Notice (notice_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES notice_categories (category_id)
);


INSERT INTO Categories (name, description)
VALUES ('프로틴', '단백질보충제'),
       ('비타민제', '각종비타민제'),
       ('의약품', '진통제, 항생제 등등'),
       ('위생/소모품', '마스크, 장갑 등등');

INSERT INTO Manufacturers (name, location, contact)
VALUES
    ('MedicoPharm Inc.', '서울, 대한민국', '02-123-4567'),
    ('GlobalCare Medical', '뉴욕, 미국', '010-234-5678'),
    ('GreenLife Labs', '프랑크푸르트, 독일', '010-3456-7890'),
    ('WellnessTech Co.', '도쿄, 일본', '010-4567-8901'),
    ('BioHealth Solutions', '시드니, 호주', '010-5678-9012');


INSERT INTO Suppliers (name, contact, location)
VALUES ('HealthPlus Distribution', '010-6789-1234', '서울, 대한민국'),
       ('GlobalMed Supplies', '02-345-6789', '로스앤젤레스, 미국'),
       ('CareLink Wholesale', '010-2345-6789', '파리, 프랑스'),
       ('WellCare Logistics', '010-4567-8901', '상하이, 중국'),
       ('PrimeMed Partners', '02-678-1234', '런던, 영국');

-- 4. Warehouses 테이블
INSERT INTO Warehouses (name, location, capacity, latitude, longitude)
VALUES ('서울 헬스케어 창고', '서울특별시 금천구 가산동', 2000, 37.4751739, 126.8804459),
       ('경기 헬스케어 창고', '경기도 용인시 처인구', 1500, 37.1830288, 127.3274336),
       ('부산 헬스케어 창고', '부산광역시 강서구 생곡동', 2000, 35.1392401, 128.8724573),
       ('대구 헬스케어 창고', '대구광역시 북구 침산1동', 1800, 35.9016264, 128.5869174),
       ('광주 헬스케어 창고', '광주광역시 북구 각화동', 1200, 35.1837145, 126.9321178);

-- 5. Branches 테이블
INSERT INTO Branches (name, location, latitude, longitude)
VALUES
-- 서울
('강남헬스케어센터', '서울특별시 강남구 삼성동', 37.5134507, 127.0527081),
('역삼헬스케어센터', '서울특별시 강남구 역삼동', 37.5000000, 127.0360000),
('서초헬스케어센터', '서울특별시 서초구 서초동', 37.4910000, 127.0070000),
('송파헬스케어센터', '서울특별시 송파구 잠실동', 37.5140000, 127.1000000),
('종로헬스케어센터', '서울특별시 종로구 종로1가', 37.5700000, 126.9800000),
('상암헬스케어센터', '서울특별시 마포구 상암동', 37.5790000, 126.8900000),
('여의도헬스케어센터', '서울특별시 영등포구 여의도동', 37.5240000, 126.9300000),
('청량리헬스케어센터', '서울특별시 동대문구 청량리동', 37.5800000, 127.0500000),
('성수헬스케어센터', '서울특별시 성동구 성수동', 37.5440000, 127.0500000),
('불광헬스케어센터', '서울특별시 은평구 불광동', 37.6100000, 126.9300000),

-- 경기
('수원헬스케어센터', '경기도 수원시 팔달구 인계동', 37.2630000, 127.0280000),
('분당헬스케어센터', '경기도 성남시 분당구 정자동', 37.3590000, 127.1050000),
('일산헬스케어센터', '경기도 고양시 일산동구 장항동', 37.6610000, 126.7680000),
('용인헬스케어센터', '경기도 용인시 기흥구 보정동', 37.3120000, 127.1080000),
('평촌헬스케어센터', '경기도 안양시 동안구 평촌동', 37.3940000, 126.9560000),
('부천헬스케어센터', '경기도 부천시 원미구 중동', 37.5030000, 126.7660000),
('동탄헬스케어센터', '경기도 화성시 동탄면', 37.2080000, 127.0710000),
('다산헬스케어센터', '경기도 남양주시 다산동', 37.6360000, 127.2160000),
('평택헬스케어센터', '경기도 평택시 비전동', 36.9920000, 127.1120000),
('의정부헬스케어센터', '경기도 의정부시 신곡동', 37.7380000, 127.0340000),

-- 인천
('청라헬스케어센터', '인천광역시 서구 청라동', 37.5370339, 126.6424644),
('구월헬스케어센터', '인천광역시 남동구 구월동', 37.4480000, 126.7310000),
('송도헬스케어센터', '인천광역시 연수구 송도동', 37.3820000, 126.6560000),
('부평헬스케어센터', '인천광역시 부평구 부평동', 37.4890000, 126.7240000),
('계산헬스케어센터', '인천광역시 계양구 계산동', 37.5380000, 126.7370000),
('주안헬스케어센터', '인천광역시 미추홀구 주안동', 37.4630000, 126.6500000),
('송림헬스케어센터', '인천광역시 동구 송림동', 37.4840000, 126.6350000),
('운서헬스케어센터', '인천광역시 중구 운서동', 37.4920000, 126.4930000),
('강화헬스케어센터', '인천광역시 강화군 강화읍', 37.7460000, 126.4870000),
('검단헬스케어센터', '인천광역시 서구 검단동', 37.598, 126.675),

-- 대구
('중구헬스케어센터', '대구광역시 중구 동인동', 35.869, 128.606),
('칠성헬스케어센터', '대구광역시 북구 칠성동', 35.885, 128.612),
('신암헬스케어센터', '대구광역시 동구 신암동', 35.881, 128.631),
('내당헬스케어센터', '대구광역시 서구 내당동', 35.870, 128.559),
('대명헬스케어센터', '대구광역시 남구 대명동', 35.846, 128.594),
('범어헬스케어센터', '대구광역시 수성구 범어동', 35.857, 128.630),
('상인헬스케어센터', '대구광역시 달서구 상인동', 35.825, 128.529),
('화원헬스케어센터', '대구광역시 달성군 화원읍', 35.801, 128.500),
('삼덕헬스케어센터', '대구광역시 중구 삼덕동', 35.866, 128.601),
('산격헬스케어센터', '대구광역시 북구 산격동', 35.896, 128.612),

-- 광주
('학동헬스케어센터', '광주광역시 동구 학동', 35.147, 126.923),
('치평헬스케어센터', '광주광역시 서구 치평동', 35.151, 126.878),
('봉선헬스케어센터', '광주광역시 남구 봉선동', 35.129, 126.902),
('용봉헬스케어센터', '광주광역시 북구 용봉동', 35.176, 126.912),
('송정헬스케어센터', '광주광역시 광산구 송정동', 35.139, 126.791),
('계림헬스케어센터', '광주광역시 동구 계림동', 35.155, 126.929),
('화정헬스케어센터', '광주광역시 서구 화정동', 35.144, 126.885),
('주월헬스케어센터', '광주광역시 남구 주월동', 35.134, 126.889),
('두암헬스케어센터', '광주광역시 북구 두암동', 35.176, 126.937),
('월계헬스케어센터', '광주광역시 광산구 월계동', 35.191, 126.805),

-- 부산
('해운대헬스케어센터', '부산광역시 해운대구 우동', 35.163, 129.163),
('광안헬스케어센터', '부산광역시 수영구 광안동', 35.153, 129.118),
('명륜헬스케어센터', '부산광역시 동래구 명륜동', 35.205, 129.083),
('연산헬스케어센터', '부산광역시 연제구 연산동', 35.184, 129.081),
('대연헬스케어센터', '부산광역시 남구 대연동', 35.136, 129.101),
('화명헬스케어센터', '부산광역시 북구 화명동', 35.235, 129.013),
('하단헬스케어센터', '부산광역시 사하구 하단동', 35.104, 128.966),
('장전헬스케어센터', '부산광역시 금정구 장전동', 35.238, 129.089),
('동대신헬스케어센터', '부산광역시 서구 동대신동', 35.097, 129.019),
('중앙헬스케어센터', '부산광역시 중구 중앙동', 35.106, 129.032);


INSERT INTO EmployeeRoles (employee_code, role)
VALUES ('KGY001', 'root'),
       ('EMP002', 'general'),
       ('EMP003', 'general'),
       ('KGY004', 'root'),
       ('EMP005', 'general'),
       ('EMP006', 'general'),
       ('KGY003', 'root'),
       ('EMP008', 'general'),
       ('EMP009', 'general'),
       ('KGY002', 'root'),
       ('EMP011', 'general'),
       ('EMP012', 'general'),
       ('EMP013', 'root'),
       ('EMP014', 'general'),
       ('EMP015', 'general'),
       ('EMP016', 'root'),
       ('EMP017', 'general'),
       ('EMP018', 'general'),
       ('ANG123', 'root'),
       ('ANG124', 'general');

-- Administrators 테이블 생략(직접 회원가입 할것)

---------------------------------------------

-- Products 테이블
INSERT INTO Products (name, description, category_id, price, manufacturer_id)
VALUES ('유청 프로틴 파우더', '고단백 유청 단백질 보충제', 1, 45000, 1),
       ('카제인 프로틴', '서서히 흡수되는 카제인 단백질', 1, 55000, 2),
       ('식물성 프로틴', '채식주의자를 위한 식물성 단백질', 1, 60000, 3),
       ('프로틴 바', '간편하게 섭취할 수 있는 단백질 바', 1, 25000, 4),
       ('프로틴 쉐이크 믹스', '운동 후 섭취하는 단백질 쉐이크', 1, 40000, 5),
       ('종합 비타민', '하루 필수 비타민을 모두 포함', 2, 30000, 1),
       ('비타민 C', '면역력 증진을 위한 고용량 비타민 C', 2, 15000, 2),
       ('비타민 D', '뼈 건강을 위한 비타민 D', 2, 20000, 3),
       ('오메가3 캡슐', '심혈관 건강을 위한 오메가3', 2, 40000, 4),
       ('멀티비타민 젤리', '맛있게 섭취하는 젤리형 멀티비타민', 2, 35000, 5),
       ('소염 진통제', '근육통과 관절염 완화', 3, 8000, 1),
       ('항생제 캡슐', '감염 치료를 위한 항생제', 3, 12000, 2),
       ('수면 보조제', '불면증 완화를 위한 보조제', 3, 20000, 3),
       ('알러지 약', '알레르기 증상 완화', 3, 15000, 4),
       ('위장약', '소화불량 및 위장 보호를 위한 의약품', 3, 10000, 5),
       ('일회용 마스크', '3중 필터로 보호력을 높인 마스크', 4, 5000, 1),
       ('일회용 장갑', '위생 관리를 위한 라텍스 장갑', 4, 8000, 2),
       ('의료용 거즈', '상처 소독 및 치료용 거즈', 4, 7000, 3),
       ('손 소독제', '99.9% 세균 제거를 위한 손 소독제', 4, 12000, 4),
       ('일회용 주사기', '위생적이고 안전한 의료용 주사기', 4, 15000, 5);

-- 생략하세요 이 테이블 더미데이터(회원가입 하고 해야함)
INSERT INTO Administrator_Warehouses (admin_id, warehouse_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 6),
       (7, 7),
       (8, 8),
       (9, 9),
       (10, 10),
       (11, 11),
       (12, 12),
       (13, 13),
       (14, 14),
       (15, 15),
       (16, 16),
       (17, 17),
       (18, 18),
       (19, 19),
       (20, 20);

INSERT INTO Warehouse_Inventory (warehouse_id, product_id, quantity)
VALUES
-- 서울 창고 (capacity: 2000, 일부 남음)
(1, 1, 400),
(1, 2, 300),
(1, 3, 200),
(1, 4, 250),
(1, 5, 500), -- 대량 보관

-- 경기 창고 (capacity: 1500, 일부 남음)
(2, 6, 250),
(2, 7, 150),
(2, 8, 400),
(2, 9, 300),
(2, 10, 200),

-- 부산 창고 (capacity: 2000, 일부 남음)
(3, 11, 600),
(3, 12, 400),
(3, 13, 300),
(3, 14, 200),
(3, 15, 300),

-- 대구 창고 (capacity: 1800, 일부 남음)
(4, 16, 400),
(4, 17, 300),
(4, 18, 200),
(4, 19, 500),
(4, 20, 100),

-- 광주 창고 (capacity: 1200, 일부 남음)
(5, 1, 200),
(5, 3, 300),
(5, 5, 400),
(5, 7, 100);

-- 주문테이블
INSERT INTO Orders (warehouse_id, branch_id, product_id, quantity, status)
VALUES (1, 1, 1, 50, '승인'),
       (1, 2, 2, 30, '대기'),
       (1, 3, 3, 20, '승인'),
       (1, 4, 4, 10, '승인'),
       (1, 5, 5, 40, '대기'),
       (1, 6, 6, 15, '승인'),
       (1, 7, 7, 25, '승인'),
       (1, 8, 8, 30, '승인'),

       (2, 9, 9, 50, '승인'),
       (2, 10, 10, 40, '거절'),
       (2, 11, 11, 35, '대기'),
       (2, 12, 12, 20, '승인'),
       (2, 13, 13, 30, '승인'),
       (2, 14, 14, 25, '승인'),
       (2, 15, 15, 10, '승인'),
       (2, 16, 16, 15, '대기'),

       (3, 17, 17, 50, '승인'),
       (3, 18, 18, 40, '승인'),
       (3, 19, 19, 35, '대기'),
       (3, 20, 20, 30, '승인'),
       (3, 1, 1, 25, '승인'),
       (3, 2, 2, 20, '승인'),
       (3, 3, 3, 15, '대기'),
       (3, 4, 4, 10, '승인'),

       (4, 5, 5, 50, '승인'),
       (4, 6, 6, 40, '거절'),
       (4, 7, 7, 30, '대기'),
       (4, 8, 8, 25, '승인'),
       (4, 9, 9, 20, '승인'),
       (4, 10, 10, 15, '승인'),
       (4, 11, 11, 10, '승인'),
       (4, 12, 12, 5, '대기'),

       (5, 13, 13, 50, '승인'),
       (5, 14, 14, 40, '승인'),
       (5, 15, 15, 30, '승인'),
       (5, 16, 16, 25, '승인'),
       (5, 17, 17, 20, '승인'),
       (5, 18, 18, 15, '대기'),
       (5, 19, 19, 10, '승인'),
       (5, 20, 20, 5, '거절');

-- 공급자, 제품 연결 테이블
INSERT INTO Supplier_Products (supplier_id, product_id)
VALUES (1, 1),
       (1, 5),
       (1, 10),
       (1, 15),
       (1, 20),
       (2, 2),
       (2, 4),
       (2, 8),
       (2, 12),
       (2, 16),
       (3, 3),
       (3, 6),
       (3, 9),
       (3, 14),
       (3, 18),
       (4, 7),
       (4, 11),
       (4, 13),
       (4, 17),
       (4, 19),
       (5, 1),
       (5, 3),
       (5, 5),
       (5, 8),
       (5, 12),
       (1, 9),
       (1, 13),
       (2, 6),
       (2, 19),
       (3, 2),
       (3, 10),
       (4, 4),
       (4, 15),
       (5, 7),
       (5, 14),
       (1, 18),
       (2, 11),
       (3, 16),
       (4, 20),
       (5, 17);

-- 입고
INSERT INTO Incoming (warehouse_id, product_id, supplier_id, quantity, incoming_date)
VALUES (1, 1, 1, 500, '2024-12-01 10:00:00'),  -- 서울 창고 입고
       (1, 2, 2, 300, '2024-12-02 11:00:00'),
       (1, 3, 3, 250, '2024-12-03 09:00:00'),
       (1, 4, 4, 400, '2024-12-04 15:00:00'),
       (1, 5, 5, 600, '2024-12-05 14:30:00'),

       (2, 6, 1, 300, '2024-12-01 12:00:00'),  -- 경기 창고 입고
       (2, 7, 2, 200, '2024-12-02 13:00:00'),
       (2, 8, 3, 450, '2024-12-03 14:00:00'),
       (2, 9, 4, 350, '2024-12-04 16:00:00'),
       (2, 10, 5, 250, '2024-12-05 17:00:00'),

       (3, 11, 1, 700, '2024-12-01 09:30:00'), -- 부산 창고 입고
       (3, 12, 2, 500, '2024-12-02 10:30:00'),
       (3, 13, 3, 400, '2024-12-03 11:30:00'),
       (3, 14, 4, 300, '2024-12-04 12:30:00'),
       (3, 15, 5, 400, '2024-12-05 13:30:00');

-- 출고
INSERT INTO Outgoing (warehouse_id, product_id, order_id, branch_id, quantity, status, outgoing_date)
VALUES
    (1, 1, 1, 1, 100, '승인', '2024-12-06 09:00:00'),    -- 서울 창고 출고
    (1, 2, 2, 2, 100, '승인', '2024-12-06 10:00:00'),
    (1, 3, 3, 3, 50, '승인', '2024-12-07 11:00:00'),
    (1, 4, 4, 4, 150, '승인', '2024-12-08 12:00:00'),
    (1, 5, 5, 5, 200, '승인', '2024-12-09 13:00:00'),
    (2, 6, 6, 6, 150, '승인', '2024-12-06 14:00:00'),    -- 경기 창고 출고
    (2, 7, 7, 7, 100, '승인', '2024-12-07 15:00:00'),
    (2, 8, 8, 8, 200, '승인', '2024-12-08 16:00:00'),
    (2, 9, 9, 9, 150, '승인', '2024-12-09 17:00:00'),
    (2, 10, 10, 10, 100, '승인', '2024-12-10 18:00:00'),
    (3, 11, 11, 11, 200, '승인', '2024-12-06 19:00:00'), -- 부산 창고 출고
    (3, 12, 12, 12, 150, '승인', '2024-12-07 20:00:00'),
    (3, 13, 13, 13, 100, '승인', '2024-12-08 21:00:00'),
    (3, 14, 14, 14, 150, '승인', '2024-12-09 22:00:00'),
    (3, 15, 15, 15, 200, '승인', '2024-12-10 23:00:00'),
    (4, 16, 16, 16, 100, '승인', '2024-12-06 08:00:00'), -- 대구 창고 출고
    (4, 17, 17, 17, 50, '승인', '2024-12-07 09:00:00'),
    (4, 18, 18, 18, 100, '승인', '2024-12-08 10:00:00'),
    (4, 19, 19, 19, 200, '승인', '2024-12-09 11:00:00'),
    (4, 20, 20, 20, 50, '승인', '2024-12-10 12:00:00'),

-- 새로운 데이터 추가: '대기' 상태
    (1, 2, NULL, 2, 80, '대기', '2024-12-11 10:00:00'),   -- 서울 창고
    (3, 14, NULL, 14, 120, '대기', '2024-12-11 15:00:00'), -- 부산 창고
    (5, 7, NULL, 7, 60, '대기', '2024-12-12 13:00:00'),    -- 광주 창고

-- 새로운 데이터 추가: '거절' 상태
    (2, 9, NULL, 9, 150, '거절', '2024-12-13 17:00:00'),   -- 경기 창고
    (4, 20, NULL, 20, 50, '거절', '2024-12-14 12:00:00');   -- 대구 창고


-- 15. 게시판 테이블(직접 글 작성 해볼것)

-- 16. 댓글 테이블(직접 댓글 작성 해볼것)

-- 17. 배송 테이블
INSERT INTO Transit (outgoing_id, warehouse_id, branch_id, product_id, quantity, transit_date, status)
VALUES
-- 1. 서울 창고 -> 경기 브랜치, 배송완료
(1, 1, 2, 1, 100, '2024-12-06 15:00:00', '배송완료'),
-- 2. 경기 창고 -> 부산 브랜치, 배송중
(6, 2, 11, 6, 150, '2024-12-07 14:00:00', '배송중'),
-- 3. 부산 창고 -> 대구 브랜치, 배송대기
(11, 3, 16, 11, 200, '2024-12-08 10:00:00', '배송대기'),
-- 4. 대구 창고 -> 광주 브랜치, 배송완료
(16, 4, 23, 16, 100, '2024-12-09 11:00:00', '배송완료'),
-- 5. 광주 창고 -> 서울 브랜치, 배송중
(21, 5, 1, 1, 50, '2024-12-10 12:00:00', '배송중'),

-- 6. 서울 창고 -> 부산 브랜치, 배송완료
(2, 1, 12, 2, 100, '2024-12-11 13:00:00', '배송완료'),
-- 7. 경기 창고 -> 광주 브랜치, 배송대기
(7, 2, 22, 7, 100, '2024-12-12 14:00:00', '배송대기'),
-- 8. 부산 창고 -> 서울 브랜치, 배송완료
(12, 3, 1, 12, 150, '2024-12-13 15:00:00', '배송완료'),
-- 9. 대구 창고 -> 경기 브랜치, 배송취소
(17, 4, 6, 17, 50, '2024-12-14 16:00:00', '배송취소'),
-- 10. 광주 창고 -> 대구 브랜치, 배송완료
(24, 5, 18, 7, 50, '2024-12-15 17:00:00', '배송완료');

-- 18. 비밀번호 찾기용 토큰 테이블(더미데이터 필요없음)

-- 19. notice_categories
INSERT INTO notice_categories (name)
VALUES ("[공지사항]"),
       ("[업데이트/새로운 기능]"),
       ("[이벤트]"),
       ("[교육/가이드]"),
       ("[운영안내]"),
       ("[긴급공지]"),
       ("[보안/안전]");

-- 20. 공지 테이블 (직접 작성 해볼것)



-- 대시 보드 테스트용 추가 데이터
-- 김근영

