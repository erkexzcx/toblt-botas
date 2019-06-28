# Tob.lt žaidimo autokėlėjas

Demonstracinė versija. Daug dalykų neužbaigta.

### Galimybės
  - Veiksmuoti. Daug, įvairiai ir gerai veiksmuoti
  - Apeiti anti bot patikrinimus
  - Apeiti žaidimo captchas
  - Išgyventi tinklo/serverio sutrikimus
  - Informuoti per Telegram apie naujas kitų žaidėjų gautas PM

### Paleidimo instrukcijos

**DISCLAIMER #1**: Šis projektas nėra užbaigtas ir jame trūksta daug esminių dalykų (pvz konfiguracijos failų), todėl visa žaidimo logika ir boto veiksmavimas nurodomas programos kode, programa yra iš naujo sukompiliuojama ir bot'as arba bot'ai pasileidžia veiksmuoti.

**DISCLAIMER #2**: Šią dieną programa veikia tik ant Linux.

**DISCLAIMER #3**: Šį maven projektą galima lengvai įimportuoti į naujausią NetBeans IDE ir iš jos paleisti.

#### 1. Atsisiųsti būtinus dependencies ####
```
# Arch Linux
sudo pacman -S jdk8-openjdk tesseract tesseract-data-eng tesseract-data-lit imagemagick maven git

# Debian 9
sudo apt-get install openjdk-8-jdk tesseract-ocr tesseract-ocr-eng tesseract-ocr-lit imagemagick maven git
```
#### 2. Paruošti projektą naudojimui ####
```
# Atsisiunciame bota ir einam i jo dir'a:
git clone https://github.com/erkexzcx/toblt-botas.git
cd toblt-botas

# Nukopijuojam faila (just do it):
cp Main.java src/main/java/core/Main.java

# Pasicustomizinam bota pagal save (daugiau info: step 3 ir step4):
vim src/main/java/core/Main.java

# Rekompiliuojam bot'a ir paleidziam:
mvn compile
LC_ALL=C LC_CTYPE=C LC_NUMERIC=C mvn exec:java -Dexec.mainClass=core.Main

# Kartais norime programa atsinaujinti - tai daroma su sita komanda:
git pull
```
#### 3. Boto konfigūravimo pavyzdys ####

**Veiksmavimo pakūrimo instrukcija (kasimui + lydimui)**
Pirmiausia nurodome player objektą (slaptazodi paimame is bet kokio zaidime esancio URL):
```
Player p1 = new Player("nickname", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
```
Tada nusistatome su kokiais itemais dirbsime (pvz kasime alava ir gaminsime plyteles):
```
Item ruda = db.getItemById("O1"); // Rūdos ID (paimame is URL). Alavas
Item plytele = db.getItemById("B1"); // Plyteles ID (paimame is URL). Alavo plytele
```
Tada sukuriame veiksmų objektus:
```
Kasimas kasimas = new Kasimas(p1, ruda);
Lydimas lydimas = new Lydimas(p1, plytele);
```
Galiausiai pradedame veiksmavimą:
```
new KasimasLydimasActivity(p1, kasimas, lydimas, plytele).startThread();
```
Paaiskinimas:
`p1` - zaidejo objektas.
`kasimas` - vienas is `KasimasLydimasActivity` dalių.
`lydimas` - vienas is `KasimasLydimasActivity` dalių.
`plytele` - galutinis veiksmavimo produktas, kuri botas automatiškai pardavinės užsipildžius inventoriui.

#### 4. Galimi veiksmavimai ####

Kirtimas:
```
Item malka = db.getItemById("MA9"); // Malkos ID (paimame is URL)
Kirtimas k = new Kirtimas(p1, malka);
new KirtimasActivity(p1, k, malka).startThread();
```
Kirtimas + Craftingas:
```
Item malka = db.getItemById("MA5"); // Malkos ID (paimame is URL)
Item lankas = db.getItemById("L5"); // Lanko ID (paimame is URL). Turi but butinai lankas!
Kirtimas k = new Kirtimas(p1, malka);
Crafting c = new Crafting(p1, lankas);
new KirtimasCraftingasActivity(p1, k, c, lankas).startThread();
```
Kasimas + Lydimas:
```
Item ruda = db.getItemById("O3"); // Rūdos ID (paimame is URL). Molis
Item plytele = db.getItemById("B4"); // Plyteles ID (paimame is URL). Plyta
Kasimas kasimas = new Kasimas(player1, ruda);
Lydimas lydimas = new Lydimas(player1, plytele);
new KasimasLydimasActivity(player1, kasimas, lydimas, plytele).startThread();
```
Kasimas + Lydimas + Kaldinimas:
```
Item ruda = db.getItemById("O1"); // Rūdos ID (paimame is URL). Alavas
Item plytele = db.getItemById("B1"); // Plyteles ID (paimame is URL). Alavo plytele
Item kardas = db.getItemById("K1"); // Ginklo ID (paimame is URL). Alavo durklas
Kasimas kasimas = new Kasimas(p1, ruda);
Lydimas lydimas = new Lydimas(p1, plytele);
Kaldinimas kaldinimas = new Kaldinimas(p1, kardas);
new KasimasLydimasKaldinimasActivity(p1, kasimas, lydimas, kaldinimas, kardas).startThread();
```
Kovojimas:
```
Kovojimas k = new Kovojimas(p1, Kovojimas.PRIESAS_ZIURKE); // Kolkas tik ziurke
new KovojimasActivity(p1, k).startThread();
```
Šiukšlyno daigtų surinkinėjimas ir pardavinėjimas, pasiliekant kai kuriuos daiktus:
```
Item[] doNotSellThese = new Item[]{
  new Item("LEM"),
  new Item("KEY1"),
  new Item("KEY2"),
  new Item("KEY3"),
  new Item("KEY4"),
  new Item("KEY5"),
  new Item("KEY6")
};
new BomzavimasActivity(p1, doNotSellThese).startThread();
```

### Papildoma informacija apie autokėlėjų naudojimą
  - Moderatoriai kartais trina jūsų acc net neatsiklausę jeigu paliekate visiškai naują acc tik kovoti ir surenkate kelis tūkstančius veiksmų. Reikia kelti visus lygius po truputį ir viskas bus gerai.
  - Kartais galima gauti IP ban su acc ištrynimu.
  - Moderatoriai ne tik rašo "Tikrinu", bet ir pvz "Parašyk 'Aš ne botas'". Neparašius užbanina. Automatizuoti atrašymo **NE-Į-MA-NO-MA**.
  - Visi veiksmuojantys accountai turi IP adresų istoriją. Tai reiškia, kad @finx gali ištrinti **visus** accountus pagal IP adresą.
  - Moderatoriai gali skaityti visų žaidėjų PM susirašinėjimus. Tai reiškia, kad mainais persimėtyti pinigus/itemus/kronas negalima. Reikia tai daryti per šiukšlyną (vienas išmeta, kitas paima).
  

### Noriu prisidėti prie boto programavimo, bet nesuprantu kaip pradėti.
Visa pradinė informacija slypi `src/main/java/core/Main.java` klasėje.

Taip pat klasė `src/main/java/core/Player.java` turi visą kritinę bei pagrindinę informaciją reikalingą boto veikimui (pvz telegram bot, db referencai ir t.t.)

Didžiausia ir sunkiausia boto dalis yra `src/main/java/core/Navigator.java` klasėje. Ši klasė veikia tarsi library boto naršymui ir navigavimui. Taip pat ši klasė automatiškai pasirūpina naujomis PM žinutėmis, anti-bot patikra bei automatiniu palaukimu nuo per greito veiksmų darymo.

O visa kita yra tiesiog unikalūs scriptukai - direktorijoje `src/main/java/actions` yra paprasti "pirmyn-atgal" veiksmai be jokių loop'ų. Loop'ai atliekami iš failų, kurie yra direktorijoje `src/main/java/activityTemplates`.

Yra keletas įdomesnių "scriptukų", kurie laikomi direktorijoje `src/main/java/subroutines`. Pavyzdys - apėjimas anti bot patikrinimo ir t.t.
