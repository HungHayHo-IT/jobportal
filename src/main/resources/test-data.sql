-- 1. Tạo Users Type
MERGE INTO users_type (user_type_id, user_type_name)
KEY(user_type_id)
VALUES (1, 'Recruiter'), (2, 'Job Seeker');

-- 2. Tạo Users
MERGE INTO users (user_id, email, is_active, password, registration_date, user_type_id)
KEY(user_id)
VALUES
(1, 'recruiter@test.com', true, 'pass123', CURRENT_TIMESTAMP(), 1),
(2, 'seeker@test.com', true, 'pass123', CURRENT_TIMESTAMP(), 2),
(3, 'recruiter2@test.com', true, 'pass123', CURRENT_TIMESTAMP(), 1);

-- 3. Tạo Recruiter Profile (Liên kết với User ID 1)
MERGE INTO recruiter_profile (user_account_id, city, company, country, first_name, last_name, state, profile_photo)
KEY(user_account_id)
VALUES (1, 'Hue', 'FPT Software', 'Vietnam', 'Tuan', 'Nguyen', 'Thua Thien Hue', 'avatar.png');

-- 4. Tạo Job Seeker Profile (Liên kết với User ID 2)
MERGE INTO job_seeker_profile (user_account_id, city, country, first_name, last_name, state, employment_type, resume)
KEY(user_account_id)
VALUES (2, 'Da Nang', 'Vietnam', 'An', 'Le', 'Da Nang', 'Full-Time', 'cv.pdf');

-- 5. Tạo Job Company & Job Location
MERGE INTO job_company (id, name, logo) KEY(id) VALUES (1, 'Google Vietnam', 'google_logo.png');
MERGE INTO job_company (id, name, logo) KEY(id) VALUES (2, 'Viettel', 'viettel_logo.png');

MERGE INTO job_location (id, city, country, state) KEY(id) VALUES (1, 'Ho Chi Minh', 'Vietnam', 'HCM');
MERGE INTO job_location (id, city, country, state) KEY(id) VALUES (2, 'Hanoi', 'Vietnam', 'Hanoi');

-- 6. Tạo Job Post Activity
MERGE INTO job_post_activity (job_post_id, description_of_job, job_title, job_type, posted_date, remote, salary, job_company_id, job_location_id, posted_by_id)
KEY(job_post_id)
VALUES
(100, 'We need a Java Expert', 'Senior Java Developer', 'Full-Time', CURRENT_TIMESTAMP(), 'Remote-Hybrid', '2000$', 1, 1, 1),
(101, 'Frontend ReactJS', 'Frontend Dev', 'Part-Time', CURRENT_TIMESTAMP(), 'On-Site', '1000$', 2, 2, 1);