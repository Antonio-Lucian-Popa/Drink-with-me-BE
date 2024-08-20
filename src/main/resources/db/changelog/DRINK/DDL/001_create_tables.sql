-- Ensure the UUID extension is enabled
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Creating the counties table
CREATE TABLE counties (
                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                          name VARCHAR(255) NOT NULL UNIQUE
);

-- Creating the locations table
CREATE TABLE locations (
                           id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                           name VARCHAR(255) NOT NULL,
                           county_id UUID NOT NULL,
                           FOREIGN KEY (county_id) REFERENCES counties(id)
);

-- Creating the users table
CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       first_name VARCHAR(30) NOT NULL,
                       last_name VARCHAR(30) NOT NULL,
                       email VARCHAR(50) NOT NULL,
                       password VARCHAR(100) NOT NULL,
                       birthday TIMESTAMP NOT NULL,
                       gender VARCHAR(255),
                       profile_image VARCHAR(255),
                       role VARCHAR(20) NOT NULL,
                       occupation VARCHAR(36),
                       lives_in VARCHAR(36),
                       enabled BOOLEAN NOT NULL
);

-- Creating the posts table
CREATE TABLE posts (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       description VARCHAR(50) NOT NULL,
                       location_id UUID NOT NULL,
                       created_at TIMESTAMP NOT NULL,
                       user_id UUID NOT NULL,
                       FOREIGN KEY (location_id) REFERENCES locations(id) ON DELETE CASCADE,
                       FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Creating the comments table
CREATE TABLE comments (
                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                          value VARCHAR(100) NOT NULL,
                          created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                          post_id UUID NOT NULL,
                          user_id UUID NOT NULL,
                          parent_id UUID,
                          FOREIGN KEY (post_id) REFERENCES posts(id),
                          FOREIGN KEY (user_id) REFERENCES users(id),
                          FOREIGN KEY (parent_id) REFERENCES comments(id)
);

-- Creating the post_images table for the ElementCollection
CREATE TABLE post_images (
                             post_id UUID NOT NULL,
                             image_filename VARCHAR(255) NOT NULL,
                             FOREIGN KEY (post_id) REFERENCES posts(id)
);

-- Creating the user_post_likes join table for the ManyToMany relationship
CREATE TABLE user_post_likes (
                                 user_id UUID NOT NULL,
                                 post_id UUID NOT NULL,
                                 PRIMARY KEY (user_id, post_id),
                                 FOREIGN KEY (user_id) REFERENCES users(id),
                                 FOREIGN KEY (post_id) REFERENCES posts(id)
);

-- Creating user followers and following relationship tables
CREATE TABLE user_followers (
                                user_id UUID NOT NULL,
                                follower_id UUID NOT NULL,
                                PRIMARY KEY (user_id, follower_id),
                                FOREIGN KEY (user_id) REFERENCES users(id),
                                FOREIGN KEY (follower_id) REFERENCES users(id)
);
