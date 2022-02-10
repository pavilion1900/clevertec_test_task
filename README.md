# О проекте

## Описание.

Приложение принимает набор параметров в формате itemId-quantity (itemId - идентификатор товара, 
quantity - его количество. Например: 3-8 2-5 5-4 card-1234.
Дальше формирует и выводит в консоль чек содержащий в себе наименование товара с id=3 в количестве 8 шт, 
то же самое с id=2 в количестве 5 штук, id=5 - 4 шт и т. д. 
Card-1234 означает, что была предъявлена скидочная карта с номером 1234. 
Сформированный чек, содержит в себе список товаров и их количество с ценой, 
а также рассчитанную сумму с учетом скидки по предъявленной карте (если она есть).

### Предусмотрены следующие возможности:

1. Наличие акционных товаров. 
Если их в чеке больше пяти, то применяется скидка 10% по этой позиции. 
Данная информацию отражается в чеке как текст **"sale"** между названием товара и его ценой.
2. Организовано чтение исходных данных (товаров и скидочных карт) из файлов.

Данные по товарам находятся в файле **Items.txt** в слеующем виде:

    1 milk 10.12 false
    2 bread 20.58 false
    3 sugar 50.36 true
    4 tomato 12.59 false
    5 banana 17.96 true 

где 1 - id товара, milk - его название, 10.12 - цена, false - является ли товар акционным.

Данные по скидочным картам находятся в файле **Cards.txt** в слеующем виде:

    1234 5
    1111 1
    2222 2
    3333 3

где 1234 - номер скидочной карты, 5 - размер скидки в % по данной карте.

3. Реализована обработка исключений.

4. Функционал покрыт тестами.

5. Реализован вывод чека в текстовый файл Check.txt.

При данном наборе параметров 

    3-8 2-5 5-4 card-1234
сформированный чек имеет следующий вид:

              CASH RECEIPT
            SUPERMARKET 123
      12, MILKYWAY Galaxy / Earth
           Tel: 123-456-7890
    CASHIER: №1520          DATE: 10/02/2022
                            TIME: 13:54:26
    -----------------------------------------
    QTY DESCRIPTION            PRICE   TOTAL
    8   sugar           sale   45.32  362.56
    5   bread                  19.55   97.75
    4   banana                 17.06   68.24
    =========================================
    TAXABLE TOT.                      528.55

Скидки по акионным товарам и по скидочным картам не суммируются.

Покупатель предъявил скидочную карту 1234 с 5% скидкой.

В данном чеке первый товар "sugar" является акционным и его количество > 5,
соответствено применяется скидка 10% по этой позиции и
данная информация отражается в чеке как текст "sale" между названием товара и его ценой.

Следующий товар "bread" не является акционным и к цене применяется скидка
только по карте (если она есть).

Следующий товар "banana" является акционным, но его количество <= 5
соотвественно к цене применяется скидка только по карте (если она есть).

## Инструкция по запуску приложения:
1. Необходимо запустить **class CheckRunner**
2. Появится меню
    

    Menu:
    0. Make order fixed settings
    1. Make order
    2. Find all items
    3. Find all cards
    4. Exit program
    Select 
4. Далее выбрать 0 и ввести набор параметров 


    3-8 2-5 5-4 card-1234
После сформируется чек в косоле.

Пункт меню 0. Make order fixed settings 
может сформировать чек только из пяти товаров и четырех скидочных карт.

Пункт меню 1. Make order
может сформировать чек из любово количества товаров и скидочных карт.
Предварительно необходимо все данные по товарам и картам 
добавить в файлы Items.txt и Cards.txt.

Пункты меню 2. Find all items и 3. Find all cards
показывают в консоль все данные по товарам и картам
из файлов Items.txt и Cards.txt.

Пункт меню 4. Exit program выход из программы.