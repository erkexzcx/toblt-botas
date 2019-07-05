# Visi galimi veiksmai

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
