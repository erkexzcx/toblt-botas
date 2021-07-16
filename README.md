# DEMESIO - Sukuriau kita tob.lt autokeleja, kuris yra uzbaigtas ir su kuriuo galima kelti visus levelius vienu metu - https://github.com/erkexzcx/tobot

# Tob.lt žaidimo autokėlėjas

Demonstracinė versija. Daug dalykų neužbaigta.

 - Dėl developmento skaityti [čia](https://github.com/erkexzcx/toblt-botas/blob/master/Development.md#for-developers).
 - Veiksmų sąrašas yra [čia](https://github.com/erkexzcx/toblt-botas/blob/master/Usage.md).

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
Paaiškinimas:
  - `b1` - zaidejo objektas.
  - `kasimas` - vienas is `KasimasLydimasActivity` dalių.
  - `lydimas` - vienas is `KasimasLydimasActivity` dalių.
  - `plytele` - galutinis veiksmavimo produktas, kuri botas automatiškai pardavinės užsipildžius inventoriui.
  - `activity` - Veiksmavimo objektas.
  
Galimų veiksmų sąrašas yra [čia](https://github.com/erkexzcx/toblt-botas/blob/master/Usage.md).
.
### Papildoma informacija apie autokėlėjų naudojimą
  - Moderatoriai kartais trina jūsų acc net neatsiklausę jeigu paliekate visiškai naują acc tik kovoti ir surenkate kelis tūkstančius veiksmų. Reikia kelti visus lygius po truputį ir viskas bus gerai.
  - Kartais galima gauti IP ban su acc ištrynimu.
  - Moderatoriai ne tik rašo "Tikrinu", bet ir pvz "Parašyk 'Aš ne botas'". Neparašius užbanina. Automatizuoti atrašymo **NE-Į-MA-NO-MA**.
  - Visi veiksmuojantys accountai turi IP adresų istoriją. Tai reiškia, kad @finx gali ištrinti **visus** accountus pagal IP adresą.
  - Moderatoriai gali skaityti visų žaidėjų PM susirašinėjimus. Tai reiškia, kad mainais persimėtyti pinigus/itemus/kronas negalima. Reikia tai daryti per šiukšlyną (vienas išmeta, kitas paima).
  
