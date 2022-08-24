-- Club Table (List of all the Clubs) --
CREATE TABLE IF NOT EXISTS club (
    club_id UUID NOT NULL PRIMARY KEY,
    username VARCHAR(200) NOT NULL,
    email VARCHAR(200) NOT NULL,
    encoded_password VARCHAR(200) NOT NULL,
    name VARCHAR(200) NOT NULL,
    description VARCHAR(500),
    meet_time VARCHAR(500),
    profile_picture_url VARCHAR(200)
);

-- Social Media Links (Club Instagram, Discord, Classroom, etc. links) --
CREATE TABLE IF NOT EXISTS socials (
    social_id UUID NOT NULL PRIMARY KEY,
    club_id UUID NOT NULL,
    social_name VARCHAR(200) NOT NULL,
    social_link VARCHAR(200) NOT NULL,
    FOREIGN KEY (club_id) REFERENCES club(club_id)
);

CREATE TABLE IF NOT EXISTS category (
    category_id UUID NOT NULL PRIMARY KEY,
    category_name VARCHAR(200) NOT NULL
);

-- Club Tags --
CREATE TABLE IF NOT EXISTS club_categories (
    id UUID NOT NULL PRIMARY KEY,
    club_id UUID NOT NULL,
    category_id UUID NOT NULL,
    FOREIGN KEY (club_id) REFERENCES club(club_id),
    FOREIGN KEY (category_id) REFERENCES category(category_id)
);

-- Posts List --
CREATE TABLE IF NOT EXISTS post (
    post_id UUID NOT NULL PRIMARY KEY,
    sender UUID NOT NULL,
    title VARCHAR(200) NOT NULL,
    text_content VARCHAR(500),
    media_url VARCHAR(500),
    FOREIGN KEY (sender) REFERENCES club(club_id)
);

-- Post Tabs --
CREATE TABLE IF NOT EXISTS post_tab (
    tab_id UUID NOT NULL PRIMARY KEY,
    post_id UUID NOT NULL,
    header VARCHAR(200) NOT NULL,
    text_content VARCHAR(500),
    media_url VARCHAR(200),
    FOREIGN KEY (post_id) REFERENCES post(post_id)
);

-- Featured Clubs --
CREATE TABLE IF NOT EXISTS featured_clubs (
    club_id UUID NOT NULL PRIMARY KEY,
    text_content VARCHAR(500),
    media_url VARCHAR(200),
    FOREIGN KEY (club_id) REFERENCES club(club_id)
);

CREATE TABLE IF NOT EXISTS users (
    user_id UUID NOT NULL PRIMARY KEY,
    user_email VARCHAR(200) NOT NULL,
    username VARCHAR(200) NOT NULL,
    encoded_password VARCHAR(200) NOT NULL,
    user_role VARCHAR(200) NOT NULL
);

CREATE TABLE IF NOT EXISTS reset_password_requests (
    request_id UUID NOT NULL PRIMARY KEY,
    club_id UUID NOT NULL,
    reset_code VARCHAR(200) NOT NULL,
    expiration_date TIMESTAMP NOT NULL,
    FOREIGN KEY (club_id) REFERENCES club(club_id)
);