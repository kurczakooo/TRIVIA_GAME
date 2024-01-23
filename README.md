# TRIVIA GAME
## Opis
Gra polegająca na rywalizacji w odpowiadaniu na pytania z wiedzy ogólnej, aby uzyskać lepszy wynik od przeciwnika. 
Gracze na zmianę wybierają kategorie pytania, i starają się udzielić jak najszybszej odpowiedzi. Jest na to 30 
sekund, i w zależności od szybkości odpowiedzi dostajemy punkty oznaczone '$'. Każdy gracz przechodzi przez 5 rund, 
pytania są unikalne w każdej z nich. GRA DZIAŁA TYLKO NA MASZYNIE LOKALNEJ!

---

## Użyta technologia:
- Java (Oracle OpenJDK version 21.02.2)
- JavaFX (javafx: 21-ea-24)
- sqlite-jdbc-3.40.1.0
- do spakowania projektu użyliśmy Maven
- i dostępnych pluginów i zależności opisanych w pliku "pom.xml"

  
---


## Instrukcja użytkowania programu:
### Grę można uruchomić na dwa sposoby, jednak do obu potrzebujemy mieć zainstalowaną Jave wersję min. 21
Link do pobrania instalatora javy: https://www.oracle.com/pl/java/technologies/downloads/

Instalator pozwala na to że Java automatycznie zostanie dodana do zmiennych środowiskowych naszego komputera.

Po zainstalowaniu otworzymy cmd i wpisujemy *'java --version'*. Jeśli uzyskamy komunikat o wersji zainstalowanej Javy to możemy przejść dalej.

----

### Sposób używając samej Javy
1.  Wchodzimy w projekcie w folder **target->app->bin**
2. w miejscu gdzie podana jest ścieżka folderu u góry wpisujemy *'cmd'*
3. Gdy otworzy nam się konsola wpisujemy *'app.bat'* i gra powinna się uruchomić.
4. Wpisujemy nick i wybieramy opcje Hostuj.
5. Powtarzamy kroki 2, 3 i 4, ale tym razem po wpisaniu nicku wybieramy opcje Gość.
6. Na ekranie Gościa powinna pokazać się informacja o serwerze stworzonym przez Hosta i przycisk *dołącz*. Po dołączeniu rozpoczynamy grę.

**6. Zamiast punktu 2 możemy w folderze manualnie znaleźć plik 'app.bat' i kliknąć na niego dwa razy.**

Ta metoda jest możliwa dzięki wygenerowaniu lokalnego środowiska JavyFX, przy użyciu Maven z pluginem Jlink. Dzięki niej nie musimy instalować JavyFX na swój komputer.

----

### Sposób używając Javy i JavyFX
1. Po pobraniu Javy pobieramy JavaFX wersje min. 21 z tego linku: https://gluonhq.com/products/javafx/ , wybierając poprawną wersję dla naszego systemu operacyjnego, oraz *TYP SDK*.
2. Wypakowujemy plik zip do wybranego folderu.
3. Wchodzimy do **javafx-sdk-21.0.2->lib** i kopiujemy ścieżkę do tego folderu.
4. W plikach projektu wchodzimy do **out->artifacts->Trivia_Game_jar**.
5. w miejscu gdzie podana jest ścieżka folderu u góry wpisujemy *'cmd'*
6. wpisujemy tą komende *'java --module-path "<ścieżka do skopiowanego wczesniej folderu lib>" --add-modules javafx.controls,javafx.fxml -jar Trivia_Game.jar'*
7. Gra powinna się uruchomić. Wpisujemy nick i wybieramy opcje Hostuj.
8. Powtarzamy kroki 5 i 6, ale po wpisaniu nicku wybieramy opcje Gość.
9. Na ekranie Gościa powinna pokazać się informacja o serwerze stworzonym przez Hosta i przycisk *dołącz*. Po dołączeniu rozpoczynamy grę.

W tej metodzie korzytamy z artefaktu projektu stworzonego przez Maven, ale bez użycia pluginu Jlink, więc środowisko do uruchomienia klas JavyFX musimy zapewnić aplikacji sami, 
co robimy poprzez pobranie JavyFX i wskazanie na nią w komendzie uruchamiającej grę.

---

##  Dodatkowe informacje o grze
Gra wykorzystuje bazę danych *sqlite* do przechowywania w niej statystyk dotyczących rozgrywki. Jeśli po skończonej grze uruchomimy ją jeszcze raz i wpiszemy nick który był już używany
wcześniej, skutkuje to aktualizacją statystyk istniejącego gracza, zamiast stworzeniem nowego. Gra korzysta z protokołu TCP i wielowątkowości, więc jeśli jeden z graczy zamknie ją przed skończeniem, to poskutkuje błędem i nie możnością kontynuowania rozgrywki dla obu graczy. Należy wtedy uruchomić grę od nowa. Mimo że gra odbywa się w formie 1 vs 1, możemy uruchomić więcej instancji w jednym momencie, pamiętając tylko o tym aby zawsze najpierw dołączać do gry graczem hostującym a potem dopiero gościem.
