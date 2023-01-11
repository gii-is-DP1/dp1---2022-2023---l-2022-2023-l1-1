-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'owner1','owner');

INSERT INTO users(username,password,enabled) VALUES ('alvgonfri','1234',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (4,'alvgonfri','player');
INSERT INTO users(username,password,enabled) VALUES ('davgonher1','ado',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (5,'davgonher1','player');
INSERT INTO users(username,password,enabled) VALUES ('migmanalv','miguel1',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (6,'migmanalv','player');
INSERT INTO users(username,password,enabled) VALUES ('player1','1234',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (8,'player1','player');
INSERT INTO users(username,password,enabled) VALUES ('player2','1234',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (9,'player2','player');
INSERT INTO users(username,password,enabled) VALUES ('player3','1234',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (10,'player3','player');
INSERT INTO users(username,password,enabled) VALUES ('player4','1234',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (11,'player4','player');
INSERT INTO users(username,password,enabled) VALUES ('player5','1234',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (12,'player5','player');

-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'vet1','veterinarian');

INSERT INTO vets(id, first_name,last_name) VALUES (1, 'James', 'Carter');
INSERT INTO vets(id, first_name,last_name) VALUES (2, 'Helen', 'Leary');
INSERT INTO vets(id, first_name,last_name) VALUES (3, 'Linda', 'Douglas');
INSERT INTO vets(id, first_name,last_name) VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets(id, first_name,last_name) VALUES (5, 'Henry', 'Stevens');
INSERT INTO vets(id, first_name,last_name) VALUES (6, 'Sharon', 'Jenkins');

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'cat');
INSERT INTO types VALUES (2, 'dog');
INSERT INTO types VALUES (3, 'lizard');
INSERT INTO types VALUES (4, 'snake');
INSERT INTO types VALUES (5, 'bird');
INSERT INTO types VALUES (6, 'hamster');
INSERT INTO types VALUES (7, 'turtle');

INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner1');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');
INSERT INTO owners VALUES (11, 'Alvaro', 'Gonzalez', '2335 Independence La.', 'Waunakee', '6085555488', 'alvgonfri');
INSERT INTO owners VALUES (12, 'David', 'Gonzalez', '638 Cardinal Ave.', 'Sun Prairie', '60855517450', 'davgonher1');

INSERT INTO pets(id,name,birth_date ,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (14, 'MyPet', '2012-06-08', 4, 11);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (15, 'Doggo', '2010-09-05', 2, 12);

INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');

INSERT INTO players(id,online,playing,username) VALUES
(1, FALSE, FALSE , 'alvgonfri'),
(2, FALSE, FALSE , 'davgonher1'),
(3, FALSE, FALSE, 'migmanalv'),
(5, FALSE, FALSE , 'player1'),
(6, FALSE, FALSE , 'player2'),
(7, FALSE, FALSE , 'player3'),
(8, FALSE, FALSE , 'player4'),
(9, FALSE, FALSE , 'player5');

INSERT INTO suffragium_cards(id,loyals_votes,traitors_votes,vote_limit) VALUES
(1,2,3,8),
(2,0,0,12),
(3,5,3,12),
(4,6,6,13),
(5,0,0,13);

INSERT INTO turns(id,current_turn) VALUES 
(1, 1),
(2, 5),
(3, 1),
(4, 1),
(5, 1);

INSERT INTO games(id,name,public_game,state,num_players,start_date,end_date,round,turn_id,stage,winners,suffragium_card_id) VALUES
(1,'Mi primera partida', 0, 'STARTING', 5, null, null, 'FIRST', 1, 'VOTING', null, 1),
(2,'Partida rapida', 1, 'STARTING', 6, '2022-10-27 10:00:00', null, 'FIRST', 2, 'END_OF_TURN', null, 2),
(3,'Partida de principiantes', 0, 'FINISHED', 6, '2022-10-30 10:00:00', '2022-10-30 11:00:00', 'FIRST', 3, 'VOTING', 'LOYALS', 3),
(4,'New game', 1, 'FINISHED', 6, '2022-11-15 23:59:58', '2022-11-16 00:25:01', 'FIRST', 4, 'VOTING', 'LOYALS', 4),
(5,'Testing decks', 1, 'STARTING', 7, null, null, 'FIRST', 5, 'VOTING', null, null);


INSERT INTO player_infos(id,creator,spectator,game_id,player_id) VALUES 
(1,true,false,2,1),
(2,false,false,2,2),
(3,false,false,2,3),
(5,false,false,2,5),
(6,false,true,2,6),
(7,false,false,2,7),

(8,false,false,1,3),

(30,false,false,3,1),
(31,false,false,3,5),

(40,true,false,4,1),

(50,true,false,5,1),
(51,false,false,5,2),
(52,false,false,5,3),
(53,false,false,5,5),
(54,false,false,5,6),
(55,false,false,5,7),
(56,false,false,5,8);

INSERT INTO achievements(id,name,type,description,threshold) VALUES 
(1,'Casual player','GAMES','You have played <THRESHOLD> games.',10.0),
(2,'Advanced player','GAMES','You have played <THRESHOLD> games.',50.0),
(3,'Addicted player','GAMES','You have played <THRESHOLD> games.',100.00),
(4,'Win basic','VICTORY','You have won <THRESHOLD> games.',5.0),
(5,'Win medium','VICTORY','You have won <THRESHOLD> games.',25.0),
(6,'Invincible','VICTORY','You have won <THRESHOLD> games.',50.00),
(7,'win loyal basic','VICTORY','You have won <THRESHOLD> games as loyal.',10.00),
(8,'win loyal medium','VICTORY','You have won <THRESHOLD> games as loyal.',20.0),
(9,'win merchant basic','VICTORY','You have won <THRESHOLD> games as merchant.',10.0),
(10,'win merchant medium','VICTORY','You have won <THRESHOLD> games as merchant.',20.00),
(11,'win traitor basic','VICTORY','You have won <THRESHOLD> games as traitor.',10.0),
(12,'win traitor medium','VICTORY','You have won <THRESHOLD> games as traitor.',20.0),
(13,'Meeting new players','FRIENDS','You have <THRESHOLD> friends.',1.00),
(14,'friends medium','FRIENDS','You have <THRESHOLD> friends.',10.00),
(15,'friends advanced','FRIENDS','You have <THRESHOLD> friends.',50.00),
(16,'time prueba','TIME','You have played <THRESHOLD> minutes.',50.00);

INSERT INTO faction_cards(type) VALUES 
('LOYAL'), ('TRAITOR'), ('MERCHANT');

INSERT INTO vote_cards(type) VALUES 
('GREEN'), ('RED'), ('YELLOW');

INSERT INTO decks(id, role_cards,player_id,game_id) VALUES 
(1, 'CONSUL',1,2),
(2, 'PRETOR',5,2),
(3, 'EDIL',2,2),
(4, 'EDIL',3,2),
(5, 'NO_ROL',6,2),
(6, 'NO_ROL',7,2),
(7, 'EDIL', 3, 1);

INSERT INTO decks_faction_cards(deck_id, faction_cards_type) VALUES 
(1, 'LOYAL'),
(2,'TRAITOR'),
(3,'MERCHANT'),
(4,'MERCHANT'),
(5,'TRAITOR'),
(6,'LOYAL');

INSERT INTO decks_vote_cards(deck_id, vote_cards_type) VALUES 
(3, 'YELLOW'),
(3, 'RED'),
(4,'GREEN'),
(4,'RED');

INSERT INTO invitations(invitation_type,message,accepted,sender_id,recipient_id) VALUES
('FRIENDSHIP', 'Hi, could we be friends?', FALSE, 1, 3),
('FRIENDSHIP', 'Hi, could we start a friendship?', FALSE, 2, 1),
('FRIENDSHIP', 'I am player1', FALSE, 5, 1),
('FRIENDSHIP', 'I am player2', TRUE, 6, 1);

