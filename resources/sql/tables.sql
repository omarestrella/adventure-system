CREATE TABLE equipment (
    equipment_id INTEGER PRIMARY KEY AUTOINCREMENT,
    helm         STRING,
    body         STRING,
    arms         STRING,
    pants        STRING
);

CREATE TABLE inventory (
    inventory_id INTEGER PRIMARY KEY AUTOINCREMENT,
    gold         INTEGER
);

CREATE TABLE attributes (
    attributes_id INTEGER PRIMARY KEY AUTOINCREMENT,
    strength      INTEGER,
    intelligence  INTEGER,
    dexterity     INTEGER,
    constitution  INTEGER,
    speed         INTEGER,
    luck          INTEGER
);

CREATE TABLE weapons (
    weapons_id INTEGER PRIMARY KEY AUTOINCREMENT,
    [left]     STRING,
    [right]    STRING
);


CREATE TABLE players (
    player_id     INTEGER PRIMARY KEY AUTOINCREMENT,
    username      STRING,
    name          STRING,
    inventory_id  INTEGER REFERENCES inventory (inventory_id) ON DELETE CASCADE,
    attributes_id INTEGER REFERENCES attributes (attributes_id) ON DELETE CASCADE,
    equipment_id  INTEGER REFERENCES equipment (equipment_id) ON DELETE CASCADE,
    weapons_id    INTEGER REFERENCES weapons (weapons_id) ON DELETE CASCADE
);
