# Dokumentacja.md
## PROZE21L_PROJEKT_PANG

#### Zespół:
| Indeks   | Nazwisko |   Imię   |
| -------- | -------- | -------- |
| 304160   | Górski   | Damian   |
| 304194   | Ogrodnik | Michał   |

## Cel gry

Celem gry jest unikanie odbijających się piłek.

## Szczegółowe zasady gry

- Na planszy znajduje się bohater oraz piłki.
- Piłki poruszają się w pionie i poziomie z różnymi prędkościami, z zachowaniem zasad fizycznych, jednak bez uwzględniania strat energii.
- Gracz ma sterować bohaterem tak, aby nie trafiła go żadna piłka.
- Gracz może poruszać się w lewo, w prawo, oraz podskakiwać, a także strzelać do piłek.
- Piłki mają trzy rodzaje("mała", "duża", "mega")
- Aby rozbić małą piłkę gracz musi w nią trafić 1 raz.
- Aby rozbić dużą piłkę gracz musi w nią trafić 3 razy.
- Aby rozbić mega piłkę gracz musi w nią trafić 7 razy.

+ Bohater:
    + na start 10 punktów życia
    + możliwość strzelania
    + możliwość poruszania się
+ Przeciwnicy 

| Rodzaj    | Mała piłka | Duża piłka | Mega piłka |
| --------- | ---------- | ---------- | ---------- |
| życie     | 1          | 3          | 7          |
| obrażenia | 1          | 1.5        | 3          |

## Szkice interfejsu graficznego
![](./szkice/menu.png)
![](./szkice/rejestracja.png)
![](./szkice/w_trakcie_gry.png)
![](./szkice/pause.png)
![](./szkice/gratulacje.png)
![](./szkice/ranking.png)

## Funkcjonalność aplikacji

- menu startowe
- wybór planszy
- możliwość pauzy w trakcie rozgrywki
- zapisywanie wyników


## Zasady punktacji

+ Za zniszczenie małej piłki gracz otrzymuje 1 pkt
+ Za zniszczenie dużej piłki gracz otrzymuje 5 pkt
+ Za zniszczenie mega piłki gracz otrzymuje 10 punktów
+ Za przejście poziomu gracz otrzymuje dodatkową liczbę punktów, która wynika ze wzoru:

\\[W=ceil(10*[(0,028*k^6-0,4*k^2+2,3*k-2,55)+0,4*(p-2,5)+0,8*(s-3)]+29)\\]

,gdzie:
k - poziom trudności (łatwy -> k=1, trudny -> k=3)
p - pozostałe hp (1-10)
s - numer poziomu(1-3)


## Elementy Dodatkowe
- wyświetlenie statystyk(ilość zniszczeń danego rodzaju piłki)
- wybór poziomu trudności danej planszy (łatwy, trudny)
    - inne prędkości i ilość piłek dla danego poziomu trudności


