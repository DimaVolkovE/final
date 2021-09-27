

import java.io.IOException;
import java.util.Scanner;


public class Prog1 {

    // Переменные хранят строковое выражение операнда1 (s1), операнда2 (s2) и знака операции (s3)
    static String s1="",s2="",s3="";

    // Переменная s. В ней будет храниться и проверяться операнд при переводе из римской в арабскую систему для вычислений
    static String s="";
    static boolean kl_arab, kl_rim;  /* переменные принимают значение TRUE, если система счисления арабская или римская соотв-но
                                     по умолчанию эти переменные в значние FALSE*/
    static int rez; // Результат арифметической операции




    public static void main(String[] args)  throws IOException, NumberFormatException {

        System.out.println("Введите арифметическое выражение типа (А) <арифметическое действие> (В) ");
        System.out.println("где (А) и (В) - целые арабские или римские цифры  от 1 до 10 ( или X) включительно, ");
        System.out.println("а <арифметическое действие> - это операции сложения, вычитания, деления или умножения");
        Scanner inp = new Scanner(System.in);
        String str;
        str = inp.nextLine();  // наша введенная строка

        str = str.replaceAll("\\s+", "");       // удаляем все лишние пробелы, если есть
        str = str.toUpperCase();                // приводим к верхнему регистру (если римские цифры)

        String simb1=str.substring(0,1);        //Рассматриваем первый символ, в римской он не должен быть отрицательным

        // начало проверок
        if ((str.isEmpty())||(str.length()<=2)) {
            System.out.println("Строка пуста или нехватает данных");
            //break;
        }
        else if ((((simb1.equals("/"))|| (simb1.equals("*")))||(simb1.equals("+")))||(simb1.equals("-")))
        {
            System.out.println("1 символ не должен быть знаком операции!");
            return;
        }

        else if (proverka_arab(str)) {

            System.out.println("Арабская система счисления");
            kl_arab=true;
            //выявляем операнды
            Operandi(str);
            Resultat(); // теперь проводим вычисления
        }
        else
        if (proverka_rim(str)){
            kl_rim=true;
            System.out.println("Римская система счисления");
            Operandi(str); //метод формирует операнды в строковом виде
            Resultat(); // теперь проводим вычисления
        }
        else
            System.out.println("Неверно введенные данные. Обратите внимание на требование");
        if (inp != null) {
            inp.close();
        }
    }


    public static boolean proverka_arab(String str) {
        boolean kl_arab=false;
        int kl = 0;  // ПЕРЕМЕННАЯ РАВНА 1, ЕСЛИ ХОТЬ РАЗ ВСТРЕЧАЕТСЯ ЗАПРЕЩЕННЫЙ СИМВОЛ
        for (int i = 0; i < str.length() ; i++) {

            char num = str.charAt(i);
            switch (num) {

                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case '0':
                case '+':
                case '-':
                case '/':
                case '*': {
                    kl_arab=true;
                    continue;
                }
                default: {
                    kl=1;
                    break;
                }
            }
        }
        if (kl==1) kl_arab=false;
        return kl_arab;
    }



    // вычисление результата
    public static void rez_arab(String s1,String s2,String s3) throws IOException, NumberFormatException {
        int op1 = Integer.parseInt(s1);
        int op2 = Integer.parseInt(s2);
        try
        {
            if ((((op1<1) || (op1>10)) || (op2<1)) || (op2>10)) {
                System.out.println("ОШИБКА ВВОДА: Вводимые операнды должны быть в интервале от 1 до 10");
            }
            else
            {

                switch (s3) {

                    case "+": {
                        rez = op1 + op2;
                        break;
                    }
                    case "-": {
                        rez = op1 - op2;
                        break;
                    }
                    case "*": {
                        rez = op1 * op2;
                        break;
                    }
                    case "/": {
                        // откидываем дробную часть
                        rez = Math.round(op1 / op2);
                        break;
                    }
                }
            }
        }

        catch (ArithmeticException e)
        {
            System.out.println("Ошибка! Деление на 0!");
            return;
        }
        catch (NumberFormatException e)
        {
            System.out.println("Ошибка ввода данных, читай условия!!!");
            return;
        }
    }

    public static void Resultat() throws IOException, NumberFormatException {
        try {
            if (isKl_arab()) { // вычисления для арабских чисел
                rez_arab(s1, s2, s3);
                System.out.println("Результат операции: " + rez);


            }
            if (isKl_rim()) { // вычисления для римских чисел

                //1 шаг перевести римские в арабские
                s = s1;
                if (rim_to_arab())
                    s1 = s;

                s = s2;
                if (rim_to_arab())
                    s2 = s;


                // 2 шаг вычисления
                rez_arab(s1, s2, s3);

                if (rez<=0)
                {
                    System.out.println("ОШИБКА !!! Для римской системы счисления результат должен быть строго положительный: ");
                }
                else {
                    // 3 шаг Перевод из арабской в римскую
                    arab_rim(rez);
                    System.out.println("Результат операции: " + arab_rim(rez));
                }
            }


        } catch (NumberFormatException e) {
            System.out.println("Ошибка ввода операндов!!!");
        }
    }

    public static String arab_rim(int in) {
        StringBuffer a = new StringBuffer("");
        a.append(basic(in));
        return a.toString();
    }

    private static String basic(int in) {
        String[] a = {
                "",
                "I",
                "II",
                "III",
                "IV",
                "V",
                "VI",
                "VII",
                "VIII",
                "IX",
                "X",
                "XI",
                "XII",
                "XIII",
                "XIV",
                "XV",
                "XVI",
                "XVII",
                "XVIII",
                "XIX",
                "XX",

        };
        return a[in];
    }


    public static boolean isKl_rim() {
        return kl_rim;
    }

    public static boolean isKl_arab() {
        return kl_arab;
    }

    public static boolean rim_to_arab() throws IOException, NumberFormatException {
        switch (s) {
            case "I": {
                s = "1";
                return true;
            }
            case "II": {
                s = "2";
                return true;
            }
            case "III": {
                s = "3";
                return true;
            }
            case "IV": {
                s = "4";
                return true;
            }
            case "V": {
                s = "5";
                return true;
            }
            case "VI": {
                s = "6";
                return true;
            }
            case "VII": {
                s = "7";
                return true;
            }
            case "VIII": {
                s = "8";
                return true;
            }
            case "IX": {
                s = "9";
                return true;
            }
            case "X": {
                s = "10";
                return true;
            }
            default:
                return false;
        }

    }



    public static void Operandi(String str)
    {
        char num;    // переменная, в которой хранится текущий символ строки
        int init1=0; //флаг принимает значение 1 когда сформирован операнд1
        int init2=0; //флаг принимает значение 1 когда сформирован операнд2
        int init3=0;//флаг принимает значение 1 когда сформирован знак операции

        for (int i = 0; i < str.length() ; i++)
        {

            num = str.charAt(i);
            //пока не сформирован 1 операнд
            if (init1 == 0)
            {
                if (((i == 0) && (num == '-')) && (kl_rim))
                {
                    System.out.println("Отрицательные числа не могут быть в римской системе  ");
                    break;
                }
                else
                if ((i == 0) && (num == '-'))
                {
                    s1=s1+num;
                    continue;
                }
                else if  ((((((num != '-')||(i!=0))&&(num != '-'))&&(num != '+'))&&(num != '*'))&&(num != '/'))                {
                    s1 = s1 + num;
                }
                else {
                    //закончили формировать операнд1, флаг устанавливаем в "1"
                    init1 = 1;
                    // System.out.println("1 операнд: " + s1);
                }
            }

            // формируем знак операции
            if ((init3 == 0) && (init1 == 1)) {
                s3 = s3 + num;
                //определились со знаком операции, флаг устанавливаем в "1"
                init3=1;

                continue;
            }

            if ((init2 == 0) && (init1 == 1)&& (init3==1))
            {
                // после знака операции выделяем второй операнд
                s2 = s2 + num;
            }
        }
    }



    public static boolean proverka_rim(String str) {
        //boolean kl_rim=false;
        int kl = 0;  // ПЕРЕМЕННАЯ РАВНА 1, ЕСЛИ ХОТЬ РАЗ ВСТРЕЧАЕТСЯ ЗАПРЕЩЕННЫЙ СИМВОЛ
        for (int i = 0; i < str.length() ; i++) {

            char num = str.charAt(i);
            switch (num) {
                case '+':
                case '-':
                case '*':
                case '/':
                case 'I':
                case 'V':
                case 'X': {
                    kl_rim=true;
                    continue;
                }
                default: {
                    kl=1;
                    break;
                }
            }
        }
        if (kl==1) kl_rim=false;
        return kl_rim;
    }

}