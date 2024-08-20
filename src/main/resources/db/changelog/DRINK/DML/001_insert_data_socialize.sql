INSERT INTO counties (id, name) VALUES
                                    (uuid_generate_v4(), 'Alba'),
                                    (uuid_generate_v4(), 'Arad'),
                                    (uuid_generate_v4(), 'Argeș'),
                                    (uuid_generate_v4(), 'Bacău'),
                                    (uuid_generate_v4(), 'Bihor'),
                                    (uuid_generate_v4(), 'Bistrița-Năsăud'),
                                    (uuid_generate_v4(), 'Botoșani'),
                                    (uuid_generate_v4(), 'Brașov'),
                                    (uuid_generate_v4(), 'Brăila'),
                                    (uuid_generate_v4(), 'Buzău'),
                                    (uuid_generate_v4(), 'Caraș-Severin'),
                                    (uuid_generate_v4(), 'Călărași'),
                                    (uuid_generate_v4(), 'Cluj'),
                                    (uuid_generate_v4(), 'Constanța'),
                                    (uuid_generate_v4(), 'Covasna'),
                                    (uuid_generate_v4(), 'Dâmbovița'),
                                    (uuid_generate_v4(), 'Dolj'),
                                    (uuid_generate_v4(), 'Galați'),
                                    (uuid_generate_v4(), 'Giurgiu'),
                                    (uuid_generate_v4(), 'Gorj'),
                                    (uuid_generate_v4(), 'Harghita'),
                                    (uuid_generate_v4(), 'Hunedoara'),
                                    (uuid_generate_v4(), 'Ialomița'),
                                    (uuid_generate_v4(), 'Iași'),
                                    (uuid_generate_v4(), 'Ilfov'),
                                    (uuid_generate_v4(), 'Maramureș'),
                                    (uuid_generate_v4(), 'Mehedinți'),
                                    (uuid_generate_v4(), 'Mureș'),
                                    (uuid_generate_v4(), 'Neamț'),
                                    (uuid_generate_v4(), 'Olt'),
                                    (uuid_generate_v4(), 'Prahova'),
                                    (uuid_generate_v4(), 'Sălaj'),
                                    (uuid_generate_v4(), 'Satu Mare'),
                                    (uuid_generate_v4(), 'Sibiu'),
                                    (uuid_generate_v4(), 'Suceava'),
                                    (uuid_generate_v4(), 'Teleorman'),
                                    (uuid_generate_v4(), 'Timiș'),
                                    (uuid_generate_v4(), 'Tulcea'),
                                    (uuid_generate_v4(), 'Vaslui'),
                                    (uuid_generate_v4(), 'Vâlcea'),
                                    (uuid_generate_v4(), 'Vrancea'),
                                    (uuid_generate_v4(), 'București');

-- Alba
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Alba Iulia', (SELECT id FROM counties WHERE name = 'Alba')),
                                                (uuid_generate_v4(), 'Aiud', (SELECT id FROM counties WHERE name = 'Alba')),
                                                (uuid_generate_v4(), 'Blaj', (SELECT id FROM counties WHERE name = 'Alba')),
                                                (uuid_generate_v4(), 'Câmpeni', (SELECT id FROM counties WHERE name = 'Alba')),
                                                (uuid_generate_v4(), 'Ocna Mureș', (SELECT id FROM counties WHERE name = 'Alba')),
                                                (uuid_generate_v4(), 'Teiuș', (SELECT id FROM counties WHERE name = 'Alba')),
                                                (uuid_generate_v4(), 'Zlatna', (SELECT id FROM counties WHERE name = 'Alba'));

-- Arad
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Arad', (SELECT id FROM counties WHERE name = 'Arad')),
                                                (uuid_generate_v4(), 'Chișineu-Criș', (SELECT id FROM counties WHERE name = 'Arad')),
                                                (uuid_generate_v4(), 'Curtici', (SELECT id FROM counties WHERE name = 'Arad')),
                                                (uuid_generate_v4(), 'Ineu', (SELECT id FROM counties WHERE name = 'Arad')),
                                                (uuid_generate_v4(), 'Lipova', (SELECT id FROM counties WHERE name = 'Arad')),
                                                (uuid_generate_v4(), 'Nădlac', (SELECT id FROM counties WHERE name = 'Arad')),
                                                (uuid_generate_v4(), 'Pâncota', (SELECT id FROM counties WHERE name = 'Arad')),
                                                (uuid_generate_v4(), 'Pecica', (SELECT id FROM counties WHERE name = 'Arad')),
                                                (uuid_generate_v4(), 'Sântana', (SELECT id FROM counties WHERE name = 'Arad')),
                                                (uuid_generate_v4(), 'Sebiș', (SELECT id FROM counties WHERE name = 'Arad'));

-- Argeș
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Pitești', (SELECT id FROM counties WHERE name = 'Argeș')),
                                                (uuid_generate_v4(), 'Câmpulung', (SELECT id FROM counties WHERE name = 'Argeș')),
                                                (uuid_generate_v4(), 'Curtea de Argeș', (SELECT id FROM counties WHERE name = 'Argeș')),
                                                (uuid_generate_v4(), 'Mioveni', (SELECT id FROM counties WHERE name = 'Argeș')),
                                                (uuid_generate_v4(), 'Ștefănești', (SELECT id FROM counties WHERE name = 'Argeș')),
                                                (uuid_generate_v4(), 'Costești', (SELECT id FROM counties WHERE name = 'Argeș')),
                                                (uuid_generate_v4(), 'Topoloveni', (SELECT id FROM counties WHERE name = 'Argeș'));

-- Bacău
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Bacău', (SELECT id FROM counties WHERE name = 'Bacău')),
                                                (uuid_generate_v4(), 'Onești', (SELECT id FROM counties WHERE name = 'Bacău')),
                                                (uuid_generate_v4(), 'Moinești', (SELECT id FROM counties WHERE name = 'Bacău')),
                                                (uuid_generate_v4(), 'Comănești', (SELECT id FROM counties WHERE name = 'Bacău')),
                                                (uuid_generate_v4(), 'Buhuși', (SELECT id FROM counties WHERE name = 'Bacău')),
                                                (uuid_generate_v4(), 'Dărmănești', (SELECT id FROM counties WHERE name = 'Bacău')),
                                                (uuid_generate_v4(), 'Târgu Ocna', (SELECT id FROM counties WHERE name = 'Bacău'));

-- Bihor
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Oradea', (SELECT id FROM counties WHERE name = 'Bihor')),
                                                (uuid_generate_v4(), 'Alesd', (SELECT id FROM counties WHERE name = 'Bihor')),
                                                (uuid_generate_v4(), 'Beiuș', (SELECT id FROM counties WHERE name = 'Bihor')),
                                                (uuid_generate_v4(), 'Marghita', (SELECT id FROM counties WHERE name = 'Bihor')),
                                                (uuid_generate_v4(), 'Salonta', (SELECT id FROM counties WHERE name = 'Bihor')),
                                                (uuid_generate_v4(), 'Ștei', (SELECT id FROM counties WHERE name = 'Bihor')),
                                                (uuid_generate_v4(), 'Vașcău', (SELECT id FROM counties WHERE name = 'Bihor'));

-- Bistrița-Năsăud
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Bistrița', (SELECT id FROM counties WHERE name = 'Bistrița-Năsăud')),
                                                (uuid_generate_v4(), 'Năsăud', (SELECT id FROM counties WHERE name = 'Bistrița-Năsăud'));

-- Botoșani
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Botoșani', (SELECT id FROM counties WHERE name = 'Botoșani')),
                                                (uuid_generate_v4(), 'Dorohoi', (SELECT id FROM counties WHERE name = 'Botoșani'));

-- Brașov
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Brașov', (SELECT id FROM counties WHERE name = 'Brașov')),
                                                (uuid_generate_v4(), 'Codlea', (SELECT id FROM counties WHERE name = 'Brașov')),
                                                (uuid_generate_v4(), 'Făgăraș', (SELECT id FROM counties WHERE name = 'Brașov')),
                                                (uuid_generate_v4(), 'Ghimbav', (SELECT id FROM counties WHERE name = 'Brașov')),
                                                (uuid_generate_v4(), 'Predeal', (SELECT id FROM counties WHERE name = 'Brașov')),
                                                (uuid_generate_v4(), 'Râșnov', (SELECT id FROM counties WHERE name = 'Brașov')),
                                                (uuid_generate_v4(), 'Rupea', (SELECT id FROM counties WHERE name = 'Brașov')),
                                                (uuid_generate_v4(), 'Săcele', (SELECT id FROM counties WHERE name = 'Brașov')),
                                                (uuid_generate_v4(), 'Victoria', (SELECT id FROM counties WHERE name = 'Brașov')),
                                                (uuid_generate_v4(), 'Zărnești', (SELECT id FROM counties WHERE name = 'Brașov'));

-- Brăila
INSERT INTO locations (id, name, county_id) VALUES
    (uuid_generate_v4(), 'Brăila', (SELECT id FROM counties WHERE name = 'Brăila'));

-- Buzău
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Buzău', (SELECT id FROM counties WHERE name = 'Buzău')),
                                                (uuid_generate_v4(), 'Nehoiu', (SELECT id FROM counties WHERE name = 'Buzău')),
                                                (uuid_generate_v4(), 'Pătârlagele', (SELECT id FROM counties WHERE name = 'Buzău')),
                                                (uuid_generate_v4(), 'Pogoanele', (SELECT id FROM counties WHERE name = 'Buzău'));

-- Caraș-Severin
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Reșița', (SELECT id FROM counties WHERE name = 'Caraș-Severin')),
                                                (uuid_generate_v4(), 'Anina', (SELECT id FROM counties WHERE name = 'Caraș-Severin')),
                                                (uuid_generate_v4(), 'Băile Herculane', (SELECT id FROM counties WHERE name = 'Caraș-Severin')),
                                                (uuid_generate_v4(), 'Bocșa', (SELECT id FROM counties WHERE name = 'Caraș-Severin')),
                                                (uuid_generate_v4(), 'Caransebeș', (SELECT id FROM counties WHERE name = 'Caraș-Severin')),
                                                (uuid_generate_v4(), 'Moldova Nouă', (SELECT id FROM counties WHERE name = 'Caraș-Severin')),
                                                (uuid_generate_v4(), 'Oravița', (SELECT id FROM counties WHERE name = 'Caraș-Severin')),
                                                (uuid_generate_v4(), 'Oțelu Roșu', (SELECT id FROM counties WHERE name = 'Caraș-Severin'));

-- Călărași
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Călărași', (SELECT id FROM counties WHERE name = 'Călărași')),
                                                (uuid_generate_v4(), 'Oltenița', (SELECT id FROM counties WHERE name = 'Călărași'));

-- Cluj
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Cluj-Napoca', (SELECT id FROM counties WHERE name = 'Cluj')),
                                                (uuid_generate_v4(), 'Câmpia Turzii', (SELECT id FROM counties WHERE name = 'Cluj')),
                                                (uuid_generate_v4(), 'Dej', (SELECT id FROM counties WHERE name = 'Cluj')),
                                                (uuid_generate_v4(), 'Gherla', (SELECT id FROM counties WHERE name = 'Cluj')),
                                                (uuid_generate_v4(), 'Huedin', (SELECT id FROM counties WHERE name = 'Cluj')),
                                                (uuid_generate_v4(), 'Turda', (SELECT id FROM counties WHERE name = 'Cluj'));

-- Constanța
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Constanța', (SELECT id FROM counties WHERE name = 'Constanța')),
                                                (uuid_generate_v4(), 'Băneasa', (SELECT id FROM counties WHERE name = 'Constanța')),
                                                (uuid_generate_v4(), 'Cernavodă', (SELECT id FROM counties WHERE name = 'Constanța')),
                                                (uuid_generate_v4(), 'Eforie', (SELECT id FROM counties WHERE name = 'Constanța')),
                                                (uuid_generate_v4(), 'Hârșova', (SELECT id FROM counties WHERE name = 'Constanța')),
                                                (uuid_generate_v4(), 'Mangalia', (SELECT id FROM counties WHERE name = 'Constanța')),
                                                (uuid_generate_v4(), 'Medgidia', (SELECT id FROM counties WHERE name = 'Constanța')),
                                                (uuid_generate_v4(), 'Murfatlar', (SELECT id FROM counties WHERE name = 'Constanța')),
                                                (uuid_generate_v4(), 'Năvodari', (SELECT id FROM counties WHERE name = 'Constanța')),
                                                (uuid_generate_v4(), 'Negru Vodă', (SELECT id FROM counties WHERE name = 'Constanța')),
                                                (uuid_generate_v4(), 'Ovidiu', (SELECT id FROM counties WHERE name = 'Constanța')),
                                                (uuid_generate_v4(), 'Techirghiol', (SELECT id FROM counties WHERE name = 'Constanța'));

-- Covasna
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Sfântu Gheorghe', (SELECT id FROM counties WHERE name = 'Covasna')),
                                                (uuid_generate_v4(), 'Târgu Secuiesc', (SELECT id FROM counties WHERE name = 'Covasna')),
                                                (uuid_generate_v4(), 'Covasna', (SELECT id FROM counties WHERE name = 'Covasna'));

-- Dâmbovița
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Târgoviște', (SELECT id FROM counties WHERE name = 'Dâmbovița')),
                                                (uuid_generate_v4(), 'Fieni', (SELECT id FROM counties WHERE name = 'Dâmbovița')),
                                                (uuid_generate_v4(), 'Găești', (SELECT id FROM counties WHERE name = 'Dâmbovița')),
                                                (uuid_generate_v4(), 'Pucioasa', (SELECT id FROM counties WHERE name = 'Dâmbovița')),
                                                (uuid_generate_v4(), 'Răcari', (SELECT id FROM counties WHERE name = 'Dâmbovița')),
                                                (uuid_generate_v4(), 'Titu', (SELECT id FROM counties WHERE name = 'Dâmbovița'));

-- Dolj
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Craiova', (SELECT id FROM counties WHERE name = 'Dolj')),
                                                (uuid_generate_v4(), 'Băilești', (SELECT id FROM counties WHERE name = 'Dolj')),
                                                (uuid_generate_v4(), 'Calafat', (SELECT id FROM counties WHERE name = 'Dolj')),
                                                (uuid_generate_v4(), 'Dăbuleni', (SELECT id FROM counties WHERE name = 'Dolj')),
                                                (uuid_generate_v4(), 'Filiași', (SELECT id FROM counties WHERE name = 'Dolj')),
                                                (uuid_generate_v4(), 'Segarcea', (SELECT id FROM counties WHERE name = 'Dolj'));

-- Galați
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Galați', (SELECT id FROM counties WHERE name = 'Galați')),
                                                (uuid_generate_v4(), 'Târgu Bujor', (SELECT id FROM counties WHERE name = 'Galați')),
                                                (uuid_generate_v4(), 'Tecuci', (SELECT id FROM counties WHERE name = 'Galați'));

-- Giurgiu
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Giurgiu', (SELECT id FROM counties WHERE name = 'Giurgiu')),
                                                (uuid_generate_v4(), 'Bolintin-Vale', (SELECT id FROM counties WHERE name = 'Giurgiu')),
                                                (uuid_generate_v4(), 'Mihăilești', (SELECT id FROM counties WHERE name = 'Giurgiu'));

-- Gorj
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Târgu Jiu', (SELECT id FROM counties WHERE name = 'Gorj')),
                                                (uuid_generate_v4(), 'Bumbești-Jiu', (SELECT id FROM counties WHERE name = 'Gorj')),
                                                (uuid_generate_v4(), 'Motru', (SELECT id FROM counties WHERE name = 'Gorj')),
                                                (uuid_generate_v4(), 'Novaci', (SELECT id FROM counties WHERE name = 'Gorj')),
                                                (uuid_generate_v4(), 'Rovinari', (SELECT id FROM counties WHERE name = 'Gorj')),
                                                (uuid_generate_v4(), 'Târgu Cărbunești', (SELECT id FROM counties WHERE name = 'Gorj')),
                                                (uuid_generate_v4(), 'Țicleni', (SELECT id FROM counties WHERE name = 'Gorj'));

-- Harghita
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Miercurea Ciuc', (SELECT id FROM counties WHERE name = 'Harghita')),
                                                (uuid_generate_v4(), 'Băile Tușnad', (SELECT id FROM counties WHERE name = 'Harghita')),
                                                (uuid_generate_v4(), 'Borsec', (SELECT id FROM counties WHERE name = 'Harghita')),
                                                (uuid_generate_v4(), 'Bălan', (SELECT id FROM counties WHERE name = 'Harghita')),
                                                (uuid_generate_v4(), 'Cristuru Secuiesc', (SELECT id FROM counties WHERE name = 'Harghita')),
                                                (uuid_generate_v4(), 'Gheorgheni', (SELECT id FROM counties WHERE name = 'Harghita')),
                                                (uuid_generate_v4(), 'Odorheiu Secuiesc', (SELECT id FROM counties WHERE name = 'Harghita')),
                                                (uuid_generate_v4(), 'Vlăhița', (SELECT id FROM counties WHERE name = 'Harghita'));

-- Hunedoara
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Deva', (SELECT id FROM counties WHERE name = 'Hunedoara')),
                                                (uuid_generate_v4(), 'Brad', (SELECT id FROM counties WHERE name = 'Hunedoara')),
                                                (uuid_generate_v4(), 'Hațeg', (SELECT id FROM counties WHERE name = 'Hunedoara')),
                                                (uuid_generate_v4(), 'Hunedoara', (SELECT id FROM counties WHERE name = 'Hunedoara')),
                                                (uuid_generate_v4(), 'Orăștie', (SELECT id FROM counties WHERE name = 'Hunedoara')),
                                                (uuid_generate_v4(), 'Petroșani', (SELECT id FROM counties WHERE name = 'Hunedoara')),
                                                (uuid_generate_v4(), 'Simeria', (SELECT id FROM counties WHERE name = 'Hunedoara')),
                                                (uuid_generate_v4(), 'Uricani', (SELECT id FROM counties WHERE name = 'Hunedoara')),
                                                (uuid_generate_v4(), 'Vulcan', (SELECT id FROM counties WHERE name = 'Hunedoara'));

-- Ialomița
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Slobozia', (SELECT id FROM counties WHERE name = 'Ialomița')),
                                                (uuid_generate_v4(), 'Amara', (SELECT id FROM counties WHERE name = 'Ialomița')),
                                                (uuid_generate_v4(), 'Fetești', (SELECT id FROM counties WHERE name = 'Ialomița')),
                                                (uuid_generate_v4(), 'Fierbinți-Târg', (SELECT id FROM counties WHERE name = 'Ialomița')),
                                                (uuid_generate_v4(), 'Țăndărei', (SELECT id FROM counties WHERE name = 'Ialomița')),
                                                (uuid_generate_v4(), 'Urziceni', (SELECT id FROM counties WHERE name = 'Ialomița'));

-- Iași
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Iași', (SELECT id FROM counties WHERE name = 'Iași')),
                                                (uuid_generate_v4(), 'Hârlău', (SELECT id FROM counties WHERE name = 'Iași')),
                                                (uuid_generate_v4(), 'Pașcani', (SELECT id FROM counties WHERE name = 'Iași')),
                                                (uuid_generate_v4(), 'Podu Iloaiei', (SELECT id FROM counties WHERE name = 'Iași')),
                                                (uuid_generate_v4(), 'Târgu Frumos', (SELECT id FROM counties WHERE name = 'Iași'));

-- Ilfov
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'București', (SELECT id FROM counties WHERE name = 'București')),
                                                (uuid_generate_v4(), 'Buftea', (SELECT id FROM counties WHERE name = 'Ilfov')),
                                                (uuid_generate_v4(), 'Chitila', (SELECT id FROM counties WHERE name = 'Ilfov')),
                                                (uuid_generate_v4(), 'Măgurele', (SELECT id FROM counties WHERE name = 'Ilfov')),
                                                (uuid_generate_v4(), 'Otopeni', (SELECT id FROM counties WHERE name = 'Ilfov')),
                                                (uuid_generate_v4(), 'Pantelimon', (SELECT id FROM counties WHERE name = 'Ilfov')),
                                                (uuid_generate_v4(), 'Popești-Leordeni', (SELECT id FROM counties WHERE name = 'Ilfov')),
                                                (uuid_generate_v4(), 'Voluntari', (SELECT id FROM counties WHERE name = 'Ilfov'));

-- Maramureș
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Baia Mare', (SELECT id FROM counties WHERE name = 'Maramureș')),
                                                (uuid_generate_v4(), 'Borșa', (SELECT id FROM counties WHERE name = 'Maramureș')),
                                                (uuid_generate_v4(), 'Cavnic', (SELECT id FROM counties WHERE name = 'Maramureș')),
                                                (uuid_generate_v4(), 'Dragomirești', (SELECT id FROM counties WHERE name = 'Maramureș')),
                                                (uuid_generate_v4(), 'Sighișoara', (SELECT id FROM counties WHERE name = 'Maramureș'));

-- Mehedinți
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Drobeta-Turnu Severin', (SELECT id FROM counties WHERE name = 'Mehedinți')),
                                                (uuid_generate_v4(), 'Baia de Aramă', (SELECT id FROM counties WHERE name = 'Mehedinți')),
                                                (uuid_generate_v4(), 'Strehaia', (SELECT id FROM counties WHERE name = 'Mehedinți')),
                                                (uuid_generate_v4(), 'Vânju Mare', (SELECT id FROM counties WHERE name = 'Mehedinți'));

-- Mureș
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Târgu Mureș', (SELECT id FROM counties WHERE name = 'Mureș')),
                                                (uuid_generate_v4(), 'Iernut', (SELECT id FROM counties WHERE name = 'Mureș')),
                                                (uuid_generate_v4(), 'Luduș', (SELECT id FROM counties WHERE name = 'Mureș')),
                                                (uuid_generate_v4(), 'Reghin', (SELECT id FROM counties WHERE name = 'Mureș')),
                                                (uuid_generate_v4(), 'Sângeorgiu de Pădure', (SELECT id FROM counties WHERE name = 'Mureș')),
                                                (uuid_generate_v4(), 'Sovata', (SELECT id FROM counties WHERE name = 'Mureș')),
                                                (uuid_generate_v4(), 'Târnăveni', (SELECT id FROM counties WHERE name = 'Mureș'));

-- Neamț
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Piatra Neamț', (SELECT id FROM counties WHERE name = 'Neamț')),
                                                (uuid_generate_v4(), 'Bicaz', (SELECT id FROM counties WHERE name = 'Neamț')),
                                                (uuid_generate_v4(), 'Roman', (SELECT id FROM counties WHERE name = 'Neamț')),
                                                (uuid_generate_v4(), 'Târgu Neamț', (SELECT id FROM counties WHERE name = 'Neamț'));

-- Olt
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Slatina', (SELECT id FROM counties WHERE name = 'Olt')),
                                                (uuid_generate_v4(), 'Balș', (SELECT id FROM counties WHERE name = 'Olt')),
                                                (uuid_generate_v4(), 'Corabia', (SELECT id FROM counties WHERE name = 'Olt')),
                                                (uuid_generate_v4(), 'Drăgănești-Olt', (SELECT id FROM counties WHERE name = 'Olt')),
                                                (uuid_generate_v4(), 'Piatra Olt', (SELECT id FROM counties WHERE name = 'Olt')),
                                                (uuid_generate_v4(), 'Potcoava', (SELECT id FROM counties WHERE name = 'Olt'));

-- Prahova
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Ploiești', (SELECT id FROM counties WHERE name = 'Prahova')),
                                                (uuid_generate_v4(), 'Azuga', (SELECT id FROM counties WHERE name = 'Prahova')),
                                                (uuid_generate_v4(), 'Băicoi', (SELECT id FROM counties WHERE name = 'Prahova')),
                                                (uuid_generate_v4(), 'Boldești-Scăeni', (SELECT id FROM counties WHERE name = 'Prahova')),
                                                (uuid_generate_v4(), 'Breaza', (SELECT id FROM counties WHERE name = 'Prahova')),
                                                (uuid_generate_v4(), 'Bușteni', (SELECT id FROM counties WHERE name = 'Prahova')),
                                                (uuid_generate_v4(), 'Comarnic', (SELECT id FROM counties WHERE name = 'Prahova')),
                                                (uuid_generate_v4(), 'Mizil', (SELECT id FROM counties WHERE name = 'Prahova')),
                                                (uuid_generate_v4(), 'Plopeni', (SELECT id FROM counties WHERE name = 'Prahova')),
                                                (uuid_generate_v4(), 'Sinaia', (SELECT id FROM counties WHERE name = 'Prahova')),
                                                (uuid_generate_v4(), 'Slănic', (SELECT id FROM counties WHERE name = 'Prahova')),
                                                (uuid_generate_v4(), 'Urlati', (SELECT id FROM counties WHERE name = 'Prahova')),
                                                (uuid_generate_v4(), 'Vălenii de Munte', (SELECT id FROM counties WHERE name = 'Prahova'));

-- Satu Mare
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Satu Mare', (SELECT id FROM counties WHERE name = 'Satu Mare')),
                                                (uuid_generate_v4(), 'Ardud', (SELECT id FROM counties WHERE name = 'Satu Mare')),
                                                (uuid_generate_v4(), 'Carei', (SELECT id FROM counties WHERE name = 'Satu Mare')),
                                                (uuid_generate_v4(), 'Livada', (SELECT id FROM counties WHERE name = 'Satu Mare')),
                                                (uuid_generate_v4(), 'Negrești-Oaș', (SELECT id FROM counties WHERE name = 'Satu Mare'));

-- Sălaj
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Zalău', (SELECT id FROM counties WHERE name = 'Sălaj')),
                                                (uuid_generate_v4(), 'Cehu Silvaniei', (SELECT id FROM counties WHERE name = 'Sălaj')),
                                                (uuid_generate_v4(), 'Jibou', (SELECT id FROM counties WHERE name = 'Sălaj')),
                                                (uuid_generate_v4(), 'Șimleu Silvaniei', (SELECT id FROM counties WHERE name = 'Sălaj'));

-- Sibiu
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Sibiu', (SELECT id FROM counties WHERE name = 'Sibiu')),
                                                (uuid_generate_v4(), 'Agnita', (SELECT id FROM counties WHERE name = 'Sibiu')),
                                                (uuid_generate_v4(), 'Avrig', (SELECT id FROM counties WHERE name = 'Sibiu')),
                                                (uuid_generate_v4(), 'Cisnădie', (SELECT id FROM counties WHERE name = 'Sibiu')),
                                                (uuid_generate_v4(), 'Copșa Mică', (SELECT id FROM counties WHERE name = 'Sibiu')),
                                                (uuid_generate_v4(), 'Dumbrăveni', (SELECT id FROM counties WHERE name = 'Sibiu')),
                                                (uuid_generate_v4(), 'Miercurea Sibiului', (SELECT id FROM counties WHERE name = 'Sibiu')),
                                                (uuid_generate_v4(), 'Ocna Sibiului', (SELECT id FROM counties WHERE name = 'Sibiu')),
                                                (uuid_generate_v4(), 'Săliște', (SELECT id FROM counties WHERE name = 'Sibiu')),
                                                (uuid_generate_v4(), 'Tălmaciu', (SELECT id FROM counties WHERE name = 'Sibiu')),
                                                (uuid_generate_v4(), 'Avrig', (SELECT id FROM counties WHERE name = 'Sibiu')),
                                                (uuid_generate_v4(), 'Mediaș', (SELECT id FROM counties WHERE name = 'Sibiu'));

-- Suceava
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Suceava', (SELECT id FROM counties WHERE name = 'Suceava')),
                                                (uuid_generate_v4(), 'Câmpulung Moldovenesc', (SELECT id FROM counties WHERE name = 'Suceava')),
                                                (uuid_generate_v4(), 'Fălticeni', (SELECT id FROM counties WHERE name = 'Suceava')),
                                                (uuid_generate_v4(), 'Gura Humorului', (SELECT id FROM counties WHERE name = 'Suceava')),
                                                (uuid_generate_v4(), 'Rădăuți', (SELECT id FROM counties WHERE name = 'Suceava')),
                                                (uuid_generate_v4(), 'Siret', (SELECT id FROM counties WHERE name = 'Suceava')),
                                                (uuid_generate_v4(), 'Vatra Dornei', (SELECT id FROM counties WHERE name = 'Suceava'));

-- Teleorman
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Alexandria', (SELECT id FROM counties WHERE name = 'Teleorman')),
                                                (uuid_generate_v4(), 'Roșiori de Vede', (SELECT id FROM counties WHERE name = 'Teleorman')),
                                                (uuid_generate_v4(), 'Turnu Măgurele', (SELECT id FROM counties WHERE name = 'Teleorman')),
                                                (uuid_generate_v4(), 'Zimnicea', (SELECT id FROM counties WHERE name = 'Teleorman')),
                                                (uuid_generate_v4(), 'Videle', (SELECT id FROM counties WHERE name = 'Teleorman'));

-- Timiș
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Timișoara', (SELECT id FROM counties WHERE name = 'Timiș')),
                                                (uuid_generate_v4(), 'Buziaș', (SELECT id FROM counties WHERE name = 'Timiș')),
                                                (uuid_generate_v4(), 'Ciacova', (SELECT id FROM counties WHERE name = 'Timiș')),
                                                (uuid_generate_v4(), 'Deta', (SELECT id FROM counties WHERE name = 'Timiș')),
                                                (uuid_generate_v4(), 'Făget', (SELECT id FROM counties WHERE name = 'Timiș')),
                                                (uuid_generate_v4(), 'Gătaia', (SELECT id FROM counties WHERE name = 'Timiș')),
                                                (uuid_generate_v4(), 'Jimbolia', (SELECT id FROM counties WHERE name = 'Timiș')),
                                                (uuid_generate_v4(), 'Lugoj', (SELECT id FROM counties WHERE name = 'Timiș')),
                                                (uuid_generate_v4(), 'Recaș', (SELECT id FROM counties WHERE name = 'Timiș')),
                                                (uuid_generate_v4(), 'Sânnicolau Mare', (SELECT id FROM counties WHERE name = 'Timiș'));

-- Tulcea
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Tulcea', (SELECT id FROM counties WHERE name = 'Tulcea')),
                                                (uuid_generate_v4(), 'Babadag', (SELECT id FROM counties WHERE name = 'Tulcea')),
                                                (uuid_generate_v4(), 'Isaccea', (SELECT id FROM counties WHERE name = 'Tulcea')),
                                                (uuid_generate_v4(), 'Măcin', (SELECT id FROM counties WHERE name = 'Tulcea')),
                                                (uuid_generate_v4(), 'Sulina', (SELECT id FROM counties WHERE name = 'Tulcea'));

-- Vaslui
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Vaslui', (SELECT id FROM counties WHERE name = 'Vaslui')),
                                                (uuid_generate_v4(), 'Bârlad', (SELECT id FROM counties WHERE name = 'Vaslui')),
                                                (uuid_generate_v4(), 'Huși', (SELECT id FROM counties WHERE name = 'Vaslui'));

-- Vâlcea
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Râmnicu Vâlcea', (SELECT id FROM counties WHERE name = 'Vâlcea')),
                                                (uuid_generate_v4(), 'Băbeni', (SELECT id FROM counties WHERE name = 'Vâlcea')),
                                                (uuid_generate_v4(), 'Băile Govora', (SELECT id FROM counties WHERE name = 'Vâlcea')),
                                                (uuid_generate_v4(), 'Bălcești', (SELECT id FROM counties WHERE name = 'Vâlcea')),
                                                (uuid_generate_v4(), 'Brezoi', (SELECT id FROM counties WHERE name = 'Vâlcea')),
                                                (uuid_generate_v4(), 'Călimănești', (SELECT id FROM counties WHERE name = 'Vâlcea')),
                                                (uuid_generate_v4(), 'Drăgășani', (SELECT id FROM counties WHERE name = 'Vâlcea')),
                                                (uuid_generate_v4(), 'Horezu', (SELECT id FROM counties WHERE name = 'Vâlcea')),
                                                (uuid_generate_v4(), 'Ocnele Mari', (SELECT id FROM counties WHERE name = 'Vâlcea'));

-- Vrancea
INSERT INTO locations (id, name, county_id) VALUES
                                                (uuid_generate_v4(), 'Focșani', (SELECT id FROM counties WHERE name = 'Vrancea')),
                                                (uuid_generate_v4(), 'Adjud', (SELECT id FROM counties WHERE name = 'Vrancea')),
                                                (uuid_generate_v4(), 'Mărășești', (SELECT id FROM counties WHERE name = 'Vrancea'));