# For developers

Visa pradinė informacija slypi `src/main/java/core/Main.java` klasėje.

Taip pat klasė `src/main/java/core/Bot.java` turi visą kritinę bei pagrindinę informaciją reikalingą boto veikimui (pvz telegram bot, db referencai ir t.t.)

Didžiausia ir sunkiausia boto dalis yra `src/main/java/core/Navigator.java` klasėje. Ši klasė veikia tarsi library boto naršymui ir navigavimui. Taip pat ši klasė automatiškai pasirūpina naujomis PM žinutėmis, anti-bot patikra bei automatiniu palaukimu nuo per greito veiksmų darymo.

O visa kita yra tiesiog unikalūs scriptukai - direktorijoje `src/main/java/actions` yra paprasti "pirmyn-atgal" veiksmai be jokių loop'ų. Loop'ai atliekami iš failų, kurie yra direktorijoje `src/main/java/activityTemplates`.

Yra keletas įdomesnių "scriptukų", kurie laikomi direktorijoje `src/main/java/subroutines`. Pavyzdys - apėjimas anti bot patikrinimo ir t.t.
