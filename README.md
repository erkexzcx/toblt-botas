# toblt-botas

Demonstracinė versija - daug dalykų neveikia ir viskas, kas veikia - reikia redaguoti/pasirinkti koreguojant programos kodą. Pats kodas ne stebūklas, bet pasistengiau surašyti komentarus.

### Ką jis daro?
Tai yra žaidimo [tob.lt](http://tob.lt/) botas. Jo tikslas - žaisti už jus. :)

### Ką jis sugeba?
Realiai sugeba viską, ką sugeba žmogus (kad neatrodytų kaip bot'as). Vienintelė problema - viskas ką jis sugeba tai pagrinde kovoti (veiksmuoti), todėl moderatoriai labai greitai pastebė, kad žaidėjas be jokio levelio padaro 10 000 veiksmų per dieną. :D Apibendrintai botas gali atlikti šiuos veiksmus:
  - Paziurek `src/main/java/activityTemplates/` direktoriją. Su laiku pridedu naujų.
  - 100% apeiti anti bot patikrinimus.
  - Išspręsti žaidimo captchas (pvz įmant iš šiukšlyno daiktus kur reikia įvesti captcha).
  - Išgyventi tinklo/serverio laikinus sutrikimus (nesustoja jeigu dingtų internetas).
  - Informuoti viską per Telegram aplikaciją naudojantis bot'u. Kalba eina apie moderatorių žinutes - bot'as parašys jums į PM per Telegram su žinutės tekstu nuo moderatoriaus ir duos 2 minutes atrašyti botui žinutę. Jeigu nespėsite - išsiųs random tekstą, o jei spesite - bot'as nusiųs moderatoriui atgal jūsų parašytą žinutė ir toliau veiksmuos. Profit. :)))
  - Palaiko neribotą skaičių tob.lt accountų

### Kaip jį pasileisti?

Šis projektas nėra užbaigtas ir jame trūksta daug esminių dalykų (pvz konfiguracijos failų), todėl visa žaidimo logika ir boto veiksmavimas nurodomas programos kode, programa yra iš naujo sukompiliuojama ir bot'as arba bot'ai pasileidžia veiksmuoti.

ŠI PROGRAMA VEIKIA TIK ANT LINUX! Įmanoma užportinti ir ant Windows, tačiau neturiu tam nei noro, nei pačių Windowsų.

**DISCLAIMER**: Šį maven projektą galima lengvai importuoti į naujausią NetBeans IDE ir iš jo leisti. Vienintelis skirtumas - NetBeans reikia leisti su komanda `LC_ALL=C LC_CTYPE=C LC_NUMERIC=C netbeans` (antraip Tesseract aplikacija neveiks ir programa crashins).

#### 1. Atsisiusti butinus packages ####
```
# Arch Linux
sudo pacman -S jdk8-openjdk tesseract tesseract-data-eng tesseract-data-lit imagemagick maven git

# Debian 9
sudo apt-get install openjdk-8-jdk imagemagick tesseract-ocr tesseract-ocr-eng tesseract-ocr-lit maven git
```
#### 2. Atsisiusti tob.lt bota ####
```
git clone https://github.com/erkexzcx/toblt-botas.git
cd toblt-botas
```
#### 3. Sukonfiguruoti tob.lt bota ####
Visa reikiama informacija yra butina uzsipildyti! Persok prie Step #5, kad butu aiskiau :)
```
cp Main.java src/main/java/core/Main.java
vim src/main/java/core/Main.java
```
#### 4. Paleisti tob.lt bota ####
```
mvn compile
LC_ALL=C LC_CTYPE=C LC_NUMERIC=C mvn exec:java -Dexec.mainClass=core.Main
```

#### 5. Kokie galimi veiksmai? ####

**Veiksmavimo pakūrimo instrukcija (kasimui + lydimui)**
Pirmiausia nurodome player objektą (slaptazodi paimame is bet kokio zaidime esancio URL):
```
Player p1 = new Player("nick", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
```
Tada nusistatome su kokiais itemais dirbsime (pvz kasime alava ir gaminsime plyteles:
```
Item ruda = db.getItemById("O1"); // Rūdos ID (paimame is URL). Alavas
Item plytele = db.getItemById("B1"); // Plyteles ID (paimame is URL). Alavo plytele
```
Tada sukuriame veiksmų objektus:
```
Kasimas kasimas = new Kasimas(player1, ruda);
Lydimas lydimas = new Lydimas(player1, plytele);
```
Galiausiai pradedame veiksmavimą:
```
new KasimasLydimasActivity(p1, kasimas, lydimas, plytele).startThread();
```
Paaiskinimas:
`p1` - zaidejo objektas.
`kasimas` - vienas is `KasimasLydimasActivity` dalių.
`lydimas` - vienas is `KasimasLydimasActivity` dalių.
`plytele` - galutinis veiksmavimo produktas, kuri botas automatiškai pardavinės.

**Galimi veiksmavimai**
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

### Ką dar reikia/turėčiau žinoti apie veiksmavimą su bot'u?
  - Moderatoriai ne kvaili - jeigu su visiškai nauju acc tik kovojat ir surenkat kelis tūkstančius veiksmų nepasikėlę jokio lygio, tai kartais net neklausia ir trina acc. Čia gal net ne moderatoriai, o pats @finx. :)
  - Kartais galima gaut IP ban.
  - Moderatoriai ne tik rašo "Tikrinu", bet ir pvz "Parašyk 'Aš ne botas'". Neparašius užbanina.
  - Visi veiksmuojantys acc turi IP adresų istoriją. Tai reiškia, kad @finx gali lengvai ištrinti visus acc pagal IP adresą.
  - Niekada nepermetinėkit daiktų/pinigų/kronų iš vieno boto acc į kitą. Moderatoriai juk gali matyti. Tam yra siukslynas - is vieno acc ismeti, is kito paimi.

### Papasakok plačiau apie kodą - noriu prisidėti programuodamas X veiksmą
Visa pagrindinė informacija slypi `src/main/java/core/Main.java` klasėje.

Taip pat klasė `src/main/java/core/Player.java` turi visą kritinę bei pagrindinę informaciją reikalingą boto veikimui (pvz telegram bot, db referencai ir t.t.)

Didžiausia boto dalis yra `src/main/java/core/Navigator.java` klasėje. Ši klasė veikia tarsi library boto naršymui ir navigavimui. Taip pat ši klasė automatiškai pasirūpina naujomis PM žinutėmis, anti-bot patikra bei automatiniu palaukimu nuo per greito veiksmų darymo.

O visa kita yra tiesiog unikalūs scriptukai - direktorijoje `src/main/java/actions` yra paprasti "pirmyn-atgal" veiksmai be jokių loop'ų. Loop'ai atliekami iš failų, kurie yra direktorijoje `src/main/java/activityTemplates`.

Yra keletas įdomesnių "scriptukų", kurie laikomi direktorijoje `src/main/java/subroutines`. Pavyzdys - apėjimas anti bot patikrinimo ir t.t.
