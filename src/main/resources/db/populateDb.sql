DELETE FROM user_roles;
DELETE FROM dishes;
DELETE FROM restaurants;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100;



INSERT INTO restaurants(name, votes) VALUES
  ('Claud Mone', 1),
  ('Puzata hata', 3);

INSERT INTO dishes (name, description, calories, restaurant_id) VALUES
  ('Baked potato', 'Perfect your spud technique then pile our filling ideas high', 500, 100),
  ('Spicy turkey sweet potatoes', 'Transform turkey thigh mince into a low-fat, low-calorie, chilli-style filling for soft, creamy baked sweet potatoes', 600, 100),
  ('Pizza baked potato', 'This budget-friendly supper combines two favourites in one dish. Top your jacket spuds with cheese, tomato, pepperoni and basil', 700, 100),
  ('Baked potatoes with spicy dhal', 'Cook red lentils with cumin, mustard seeds and turmeric and serve with a fluffy jacket potato and chutney', 800, 101),
  ('Turkey chilli jacket potatoes', 'Turkey mince is cheap and lean - flavour Mexican-style with cumin and paprika and serve in crisp baked potatoes', 900, 101),
  ('Classic jacket potatoes', 'Make the perfect jacket potato - crispy on the outside and meltingly soft in the middle', 1000, 101);

-- admin
INSERT INTO users (name, email, password, voted_restaurant_id)
VALUES ('Admin', 'admin@gmail.com', '$2a$10$bGaoPP7m9P3PoE/JzeMC2eFzL89.VfcFvKCnMq.x1e5LjcZ2A7t2O', 100);

-- controller
INSERT INTO users (name, email, password, voted_restaurant_id) VALUES
  ('User_1', 'user@yandex.ru', '$2a$10$J6bbFasiQW5wJBqJEIBwnOZxpvad0/dTM5l/dFxnwHUk6XCDCS6/S', 101),
  ('User_2', 'user@hotmail.ru', '$2a$10$gns1WfMqS.UU0Gaj/4g0re7p6Ut4adJtiPINbrYMwi./I2LiOW042', 101),
  ('User_3', 'user@mail.ru', '$2a$10$KYIwKqjxC6UEo.XjFSxFJ.Wc1dX7kSsaR5.vX8jc32uE2VAI78JnC', 101);


INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_ADMIN', 108),
  ('ROLE_USER', 108),
  ('ROLE_USER', 109),
  ('ROLE_USER', 110),
  ('ROLE_USER', 111);