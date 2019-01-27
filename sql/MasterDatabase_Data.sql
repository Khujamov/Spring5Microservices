INSERT INTO eat.pizza (id, name, cost) 
VALUES (1, 'Margherita', 7)
      ,(2, 'Marinara', 7.5)
      ,(3, 'Carbonara', 8.25)
      ,(4, 'Frutti di Mare', 6.5)
      ,(5, 'Pugliese', 7.5)
      ,(6, 'Hawaiian', 8);

SELECT setval('eat.pizza_id_seq', (SELECT count(*) FROM eat.pizza));


INSERT INTO eat.ingredient (id, name) 
VALUES (1, 'Cheese')
      ,(2, 'Ham')
      ,(3, 'Onion')
      ,(4, 'Pineapple')
      ,(5, 'Chicken')
      ,(6, 'Tomato sauce')
      ,(7, 'Mozzarella')
      ,(8, 'Oregano')
      ,(9, 'Garlic')
      ,(10, 'Mushrooms')
      ,(11, 'Parmesan')
      ,(12, 'Egg')
      ,(13, 'Bacon')
      ,(14, 'Basil')
      ,(15, 'Seafood');

SELECT setval('eat.ingredient_id_seq', (SELECT count(*) FROM eat.ingredient));


INSERT INTO eat.pizza_ingredient (pizza_id, ingredient_id) 
VALUES (1, 6), (1, 7), (1, 8)
      ,(2, 6), (2, 9), (2, 14)
      ,(3, 6), (3, 7), (3, 11), (3, 12), (3, 13)
      ,(4, 6), (4, 15)
      ,(5, 6), (5, 7), (5, 8), (5, 3)
      ,(6, 6), (6, 1), (6, 4) (6, 2);


INSERT INTO eat.order (id, code, created) 
VALUES (1, 'Order 1', '2018-12-31 16:00:00'::TIMESTAMP)
      ,(2, 'Order 2', '2019-01-02 18:00:00'::TIMESTAMP)
      ,(3, 'Order 3', '2019-01-05 13:30:00'::TIMESTAMP);
  
SELECT setval('eat.order_id_seq', (SELECT count(*) FROM eat.order));


INSERT INTO eat.order_line(id, order_id, pizza_id, cost, amount)
VALUES (1, 1, 2, 15, 2)
      ,(2, 2, 4, 6.5, 1), (3, 2, 6, 8, 1)
      ,(4, 3, 5, 22.5, 3), (5, 3, 1, 14, 2), (6, 3, 3, 8.25, 1);

SELECT setval('eat.order_line_id_seq', (SELECT count(*) FROM eat.order_line));
