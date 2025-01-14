INSERT INTO users (id, name, password, email)
VALUES
    ('610205aa-b5b3-47c0-b3de-b73e0468b955', 'Alice', 'password123', 'alice@example.com'),
    ('9475b208-0ebb-40bb-91e6-93cd0e748d2e', 'Bob', 'password123', 'bob@example.com'),
    ('077f9d35-c00f-471c-993a-e057c69a72d4', 'Charlie', 'password123', 'charlie@example.com');

INSERT INTO posts (image, content, date, user_id)
VALUES
    ('image1.png', 'Post 1 by Alice', NOW(), (SELECT id FROM users WHERE email = 'alice@example.com')),
    ('image2.png', 'Post 2 by Bob', NOW(), (SELECT id FROM users WHERE email = 'bob@example.com'));

INSERT INTO likes (user_id, post_id)
VALUES
    ((SELECT id FROM users WHERE email = 'alice@example.com'), (SELECT id FROM posts WHERE content = 'Post 2 by Bob')),
    ((SELECT id FROM users WHERE email = 'bob@example.com'), (SELECT id FROM posts WHERE content = 'Post 1 by Alice'));
