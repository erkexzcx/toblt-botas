# Tob.lt žaidimo autokėlėjas

Demonstracinė versija. Daug dalykų neužbaigta.

### Galimybės
  - Veiksmuoti. Daug, įvairiai ir gerai veiksmuoti
  - Apeiti anti bot patikrinimus
  - Apeiti žaidimo captchas
  - Išgyventi tinklo/serverio sutrikimus
  - Informuoti per Telegram apie naujas kitų žaidėjų gautas PM
  - Palaiko neribotą bot'ų skaičių

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

# Einam i programos direktorija:
cd toblt-botas

# Nukopijuojam faila (just do it):
cp Main.java src/main/java/core/Main.java

# Pasicustomizinam bota pagal save (daugiau info: step 3 ir step4):
vim src/main/java/core/Main.java

# Rekompiliuojam bot'a ir paleidziam (gali uztrukti labai ilgai ant low-end devices):
./compilerun.sh

# Kartais norime programa atsinaujinti - tai daroma su sita komanda:
git pull
```
#### 3. Boto konfigūravimo pavyzdys ####

**Veiksmavimo pakūrimo instrukcija (kasimui + lydimui)**
Pirmiausia nurodome bot objektą (slaptazodi paimame is bet kokio zaidime esancio URL):
```
Bot b1 = new Bot("nickname", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
```
Tada nusistatome su kokiais itemais dirbsime (pvz kasime alava ir gaminsime plyteles):
```
Item ruda = db.getItemById("O1"); // Rūdos ID (paimame is URL). Alavas
Item plytele = db.getItemById("B1"); // Plyteles ID (paimame is URL). Alavo plytele
```
Tada sukuriame veiksmų objektus:
```
Kasimas kasimas = new Kasimas(b1, ruda);
Lydimas lydimas = new Lydimas(b1, plytele);
```
Tada sukuriame musu veiksmavima (activity):
```
Activity activity = new KasimasLydimasActivity(b1, kasimas, lydimas, plytele);
```
Galiausiai paleidziama veiksmavima:
```
b1.setActivity(activity).startActivity();
```
Paaiskinimas:
  - `b1` - zaidejo objektas.
  - `kasimas` - vienas is `KasimasLydimasActivity` dalių.
  - `lydimas` - vienas is `KasimasLydimasActivity` dalių.
  - `plytele` - galutinis veiksmavimo produktas, kuri botas automatiškai pardavinės užsipildžius inventoriui.
  - `activity` - Veiksmavimo objektas.

#### 4. Galimi veiksmavimai ####

Kirtimas:
```
Item malka = db.getItemById("MA9"); // Malkos ID (paimame is URL)
Kirtimas k = new Kirtimas(b1, malka);
Activity activity = new KirtimasActivity(b1, k, malka);
b1.setActivity(activity).startActivity();
```
Kirtimas + Craftingas:
```
Item malka = db.getItemById("MA5"); // Malkos ID (paimame is URL)
Item lankas = db.getItemById("L5"); // Lanko ID (paimame is URL). Turi but butinai lankas!
Kirtimas k = new Kirtimas(b1, malka);
Crafting c = new Crafting(b1, lankas);
Activity activity = new KirtimasCraftingasActivity(b1, k, c, lankas);
b1.setActivity(activity).startActivity();
```
Kasimas:
```
Item ruda = db.getItemById("O3");
Kasimas kasimas = new Kasimas(b1, ruda);
Activity activity = new KasimasActivity(b1, kasimas, ruda);
b1.setActivity(activity).startActivity();
```
Kasimas + Brangakmeniu apdirbimas:
```
// Brangakmeniu apdirbimas veiklos
// Apdirbti brangakmeniai automatiskai bus parduoti
// Pries kasant paziurek kokius brangakmenius iskasi - jie bus nurodyti cia
BrangakmeniuApdirbimas[] b = new BrangakmeniuApdirbimas[]{
	new BrangakmeniuApdirbimas(b1, db.getItemById("NB3")),
	new BrangakmeniuApdirbimas(b1, db.getItemById("NB4")),
	new BrangakmeniuApdirbimas(b1, db.getItemById("NB5"))
};

// Kasimo stuff:
Item itemToSell = db.getItemById("O6");
Kasimas kasimas = new Kasimas(b1, itemToSell);
Activity a = new KasimasBrangakmeniuApdirbimasActivity(b1, kasimas, b, itemToSell);
b1.setActivity(a).startActivity();
```
Kasimas + Lydimas:
```
Item ruda = db.getItemById("O3"); // Rūdos ID (paimame is URL). Molis
Item plytele = db.getItemById("B4"); // Plyteles ID (paimame is URL). Plyta
Kasimas kasimas = new Kasimas(b1, ruda);
Lydimas lydimas = new Lydimas(b1, plytele);
Activity activity = new KasimasLydimasActivity(b1, kasimas, lydimas, plytele);
b1.setActivity(activity).startActivity();
```
Kasimas + Lydimas + Kaldinimas:
```
Item ruda = db.getItemById("O1"); // Rūdos ID (paimame is URL). Alavas
Item plytele = db.getItemById("B1"); // Plyteles ID (paimame is URL). Alavo plytele
Item kardas = db.getItemById("K1"); // Ginklo ID (paimame is URL). Alavo durklas
Kasimas kasimas = new Kasimas(b1, ruda);
Lydimas lydimas = new Lydimas(b1, plytele);
Kaldinimas kaldinimas = new Kaldinimas(b1, kardas);
Activity activity = new KasimasLydimasKaldinimasActivity(b1, kasimas, lydimas, kaldinimas, kardas);
b1.setActivity(activity).startActivity();
```
Kovojimas:
```
Kovojimas k = new Kovojimas(b1, Kovojimas.PRIESAS_ZIURKE); // Kolkas tik ziurke
Activity activity = new KovojimasActivity(b1, k);
b1.setActivity(activity).startActivity();
```
Slayer + Kovojimas:
```
Slayer slayer = new Slayer(b1, Slayer.KILL_1_10);
Kovojimas kovojimas = new Kovojimas(b1, Kovojimas.PRIESAS_ZIURKE); // Kolkas tik ziurke
Activity activity = new SlayerKovojimasActivity(b1, slayer, kovojimas);
b1.setActivity(activity).startActivity();
```
Grybavimas:
```
Item item = db.getItemById("GR4");
Grybavimas grybavimas = new Grybavimas(b1, item);
Activity activity = new GrybavimasActivity(b1, grybavimas, item);
b1.setActivity(activity).startActivity();
```
Grybavimas + Amatų potingas:
```
Item grybas = db.getItemById("GR9");
Item potion = db.getItemById("PA9");
Grybavimas grybavimas = new Grybavimas(b1, grybas);
AmatuPotingas amatuPotingas = new AmatuPotingas(b1, potion);
Activity a = new GrybavimasAmatuPotingasActivity(b1, grybavimas, amatuPotingas, potion);
b1.setActivity(a).startActivity();
```
Šiukšlyno daigtų surinkinėjimas ir pardavinėjimas, pasiliekant kai kuriuos daiktus:
```
Item[] doNotSellThese = new Item[]{
  Activity activity = new Item("LEM"),
  Activity activity = new Item("KEY1"),
  Activity activity = new Item("KEY2"),
  Activity activity = new Item("KEY3"),
  Activity activity = new Item("KEY4"),
  Activity activity = new Item("KEY5"),
  Activity activity = new Item("KEY6")
};
Activity activity = new BomzavimasActivity(b1, doNotSellThese);
b1.setActivity(activity).startActivity();
```

### Papildoma informacija apie autokėlėjų naudojimą
  - Moderatoriai kartais trina jūsų acc net neatsiklausę jeigu paliekate visiškai naują acc tik kovoti ir surenkate kelis tūkstančius veiksmų. Reikia kelti visus lygius po truputį ir viskas bus gerai.
  - Kartais galima gauti IP ban su acc ištrynimu.
  - Moderatoriai ne tik rašo "Tikrinu", bet ir pvz "Parašyk 'Aš ne botas'". Neparašius užbanina. Automatizuoti atrašymo **NE-Į-MA-NO-MA**.
  - Visi veiksmuojantys accountai turi IP adresų istoriją. Tai reiškia, kad @finx gali ištrinti **visus** accountus pagal IP adresą.
  - Moderatoriai gali skaityti visų žaidėjų PM susirašinėjimus. Tai reiškia, kad mainais persimėtyti pinigus/itemus/kronas negalima. Reikia tai daryti per šiukšlyną (vienas išmeta, kitas paima).
  

### Noriu prisidėti prie boto programavimo, bet nesuprantu kaip pradėti.
Visa pradinė informacija slypi `src/main/java/core/Main.java` klasėje.

Taip pat klasė `src/main/java/core/Bot.java` turi visą kritinę bei pagrindinę informaciją reikalingą boto veikimui (pvz telegram bot, db referencai ir t.t.)

Didžiausia ir sunkiausia boto dalis yra `src/main/java/core/Navigator.java` klasėje. Ši klasė veikia tarsi library boto naršymui ir navigavimui. Taip pat ši klasė automatiškai pasirūpina naujomis PM žinutėmis, anti-bot patikra bei automatiniu palaukimu nuo per greito veiksmų darymo.

O visa kita yra tiesiog unikalūs scriptukai - direktorijoje `src/main/java/actions` yra paprasti "pirmyn-atgal" veiksmai be jokių loop'ų. Loop'ai atliekami iš failų, kurie yra direktorijoje `src/main/java/activityTemplates`.

Yra keletas įdomesnių "scriptukų", kurie laikomi direktorijoje `src/main/java/subroutines`. Pavyzdys - apėjimas anti bot patikrinimo ir t.t.
