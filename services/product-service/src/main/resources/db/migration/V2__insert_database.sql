INSERT INTO category (id, description, name) VALUES
    (1, 'Electronics and gadgets', 'Electronics'),
    (2, 'Home and kitchen appliances', 'Home Appliances'),
    (3, 'Books and stationery', 'Books'),
    (4, 'Health and personal care items', 'Health'),
    (5, 'Fashion and apparel', 'Fashion');

INSERT INTO product (id, description, name, available_quantity, price, category_id) VALUES
    (1, 'Smartphone with 128GB storage', 'Smartphone', 50, 699.99, 1),
    (2, '4K Ultra HD TV 55"', 'Television', 20, 1099.99, 1),
    (3, 'Non-stick cooking pan', 'Cooking Pan', 100, 25.50, 2),
    (4, 'Electric blender with two speed settings', 'Blender', 35, 49.99, 2),
    (5, 'Inspirational self-help book', 'Self-Help Book', 60, 12.99, 3),
    (6, 'Novel by popular author', 'Novel', 80, 9.99, 3),
    (7, 'Organic multivitamins', 'Multivitamins', 40, 19.99, 3),
    (8, 'Shampoo for all hair types', 'Shampoo', 120, 5.99, 4),
    (9, 'Mens casual T-shirt', 'T-Shirt', 200, 15.99, 5),
    (10, 'Womens denim jeans', 'Jeans', 150, 39.99, 5);