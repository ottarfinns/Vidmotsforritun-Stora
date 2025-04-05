# Viðburðarstjórinn

Viðburðarstjórinn er JavaFX forrit sem gerir notendum kleift að stjórna viðburðum. Forritið er skrifað í Java og notar Maven sem verkefnisstjórnunartól til að byggja og keyra forritið.

Forritið gerir þér kleift að:
- Búa til nýja viðburði
- Bæta við upplýsingum um viðburðinn
- Vista viðburði
- Eyða viðburðum
- Búa til endurtekna viðburði
- Fá yfirlit yfir alla vistaða viðburði

ATH það gæti þurft að 'skrolla' aðeins niður til að fá stýringar fyrir myndbandið.

## Uppsetning

Þetta forrit er byggt með Java 21, nánar tiltekið 'temurin-21', og krefst þess að notandi sé með útgáfu af Maven sem er 3.8 eða nýrra.

## Bygging og keyrsla með Maven

Til þess að byggja og keyra forritið er hægt að nota innbyggða Maven tólið í IntelliJ með því að opna Maven flipann og smella á eftirfarandi hnappa

- EventManager > Plugins > compiler > compiler:compile
- EventManager > Plugins > javafx > javafx:run

Þetta er einnig hægt að gera af skipanalínu. Með skipanalínutóli er farið í rótarmöppu verkefnisins og eftirfarandi skipanir keyrðar.

```bash
mvn clean install
```

```bash
mvn javafx:run
```
Athugið að EventManager2 er rótarmappa forritsins
