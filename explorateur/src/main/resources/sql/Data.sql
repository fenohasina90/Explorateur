-- classe
insert into classes (nom, age) values 
('Ami', 10),
('Compagnon', 11),
('Eclaireur', 12),
('Pionner', 13),
('Voyageur', 14),
('Guide', 15);

-- roles_staff
insert into roles_staff (role_name) values 
('directeur'),
('co-directeur'),
('secretariat'),
('instructeur');

-- parent
INSERT INTO parents (nom, prenom, adresse, telephone) VALUES
('Rakoto', 'Andry', 'Antananarivo', '0341234567'),
('Rabe', 'Fanja', 'Toamasina', '0329876543'),
('Randriamampianina', 'Hery', 'Fianarantsoa', '0334567890'),
('Rasolofoniaina', 'Voahirana', 'Mahajanga', '0381122334'),
('Razafindrakoto', 'Mamy', 'Antsirabe', '0349988776');

-- enfant
INSERT INTO enfants (nom, prenom, date_naissance, adresse, parent_id, bapteme) VALUES
('Rakoto', 'Tiana', '2012-05-14', 'Antananarivo', 1, '2020-08-22'),
('Rakoto', 'Kanto', '2014-11-03', 'Antananarivo', 1, NULL),

('Rabe', 'Aina', '2011-02-19', 'Toamasina', 2, '2019-06-15'),
('Rabe', 'Miora', '2013-07-25', 'Toamasina', 2, NULL),

('Randriamampianina', 'Toky', '2010-09-10', 'Fianarantsoa', 3, '2018-12-01'),

('Rasolofoniaina', 'Fitia', '2015-01-30', 'Mahajanga', 4, NULL),
('Rasolofoniaina', 'Tsiry', '2012-04-18', 'Mahajanga', 4, '2021-03-10'),

('Razafindrakoto', 'Onja', '2011-06-07', 'Antsirabe', 5, '2019-09-05'),
('Razafindrakoto', 'Lova', '2014-10-21', 'Antsirabe', 5, NULL);

