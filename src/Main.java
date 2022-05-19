import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Введите выражение:");
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        String answer = calc(str);
        System.out.println(answer);
    }


    public static String calc(String input) throws Exception {

        String[] stringsFirst = input.trim().split(" ");
        String[] stringsFinal = getStringsFinal(stringsFirst);
        String aString = stringsFinal[0];
        String sign = stringsFinal[1];
        String bString = stringsFinal[2];

        int a = 0;
        int b = 0;
        boolean singFound = false;
        boolean romanA = false;
        boolean romanB = false;
        boolean arabicA = false;
        boolean arabicB = false;

        for (Operation operation : Operation.values()) {
            if (Objects.equals(operation.getName(), sign)) {
                singFound = true;
            }
        }
        if (!singFound) {
            throw new Exception("//т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }


        for (Roman roman : Roman.values()) {
            if (Objects.equals(roman.getName(), aString)) {
                romanA = true;
                a = roman.getArabic();
            }
            if (Objects.equals(roman.getName(), bString)) {
                romanB = true;
                b = roman.getArabic();
            }
        }
        for (Arabic arabic : Arabic.values()) {
            if (Objects.equals(arabic.getName(), aString)) {
                arabicA = true;
                a = arabic.getArabic();
            }
            if (Objects.equals(arabic.getName(), bString)) {
                arabicB = true;
                b = arabic.getArabic();
            }
        }


        if ((arabicA && romanB) || (arabicB && romanA)){
            throw new Exception("//т.к. используются одновременно разные системы счисления");
        } else if (!arabicA && arabicB || !romanA && romanB || arabicA && !arabicB || romanA && !romanB){
            throw new Exception("//т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
        String answerString = "";
        if (Objects.equals(sign, "+")){     //Сложение
            operationFunction op = operationFunction.PLUS;
            int answer = op.action(a, b);
            if (arabicA && arabicB) {
                answerString = String.valueOf(answer);
            } else {
                answerString = getStringAnswer(answer);
            }
        }
        if (Objects.equals(sign, "-")){     //Вычитание
            operationFunction op = operationFunction.MINUS;
            int answer = op.action(a, b);
            if (arabicA && arabicB) {
                answerString = String.valueOf(answer);
            } else if(a > b){
                answerString = getStringAnswer(answer);
            } else {
                throw new Exception("//т.к. в римской системе нет отрицательных чисел");
            }
        }
        if (Objects.equals(sign, "*")){
            operationFunction op = operationFunction.MULTIPLICATION;
            int answer = op.action(a, b);
            if (arabicA && arabicB) {
                answerString = String.valueOf(answer);
            } else {
                answerString = getStringAnswer(answer);
            }
        }
        if (Objects.equals(sign, "/")) {
            operationFunction op = operationFunction.DIVIDED;
            if ((a / b) >= 1) {
                int answer = op.action(a, b);
                if (arabicA && arabicB) {
                    answerString = String.valueOf(answer);
                } else {
                    answerString = getStringAnswer(answer);
                }
            } else {
                throw new Exception("//т.к. результат меньше единицы");
            }
        }
        return answerString;

    }
    static String getStringAnswer(int a) {
        String answerString = " ";
        for (ArabicFromRoman i : ArabicFromRoman.values()) {
            if( a == i.getArabic() ) {
                answerString = i.getName();
            }
        }
        return answerString;
    }


    static String[] getStringsFinal(String[] stringsFirst) throws Exception {
        String[] stringsFinal = new String[stringsFirst.length - quantitySpace(stringsFirst)];
        for (int i = 0, j = 0; i < stringsFinal.length; i++) {
            if (stringsFirst[i].trim().length() > 0) {
                stringsFinal[j] = stringsFirst[i];
                j++;
            }
        }
        if (stringsFinal.length > 3) {
            throw new Exception("//т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        } else if (stringsFinal.length < 3){
            throw new Exception("//т.к. строка не является математической операцией");
        }
        return stringsFinal;
    }

    static int quantitySpace(String[] a) {
        int j = 0;
        for (String s : a) {
            if (s.trim().length() == 0) {
                j++;
            }
        }
        return j;
    }
}

enum Arabic {

    one(1, "1"),
    two(2, "2"),
    three(3, "3"),
    four(4, "4"),
    five(5, "5"),
    six(6, "6"),
    seven(7, "7"),
    eight(8, "8"),
    nine(9, "9"),
    ten(10, "10");


    private final int arabic;
    private String name;

    Arabic(int arabic, String name) {
        this.arabic = arabic;
        this.name = name;
    }

    public int getArabic() {
        return arabic;
    }

    public String getName() {
        return name;
    }
}

enum Roman {
    I(1, "I"),
    II(2, "II"),
    III(3, "III"),
    IV(4, "IV"),
    V(5, "V"),
    VI(6, "VI"),
    VII(7, "VII"),
    VIII(8, "VIII"),
    IX(9, "IX"),
    X(10, "X");


    private final int arabic;
    private String name;

    Roman(int arabic, String name) {
        this.arabic = arabic;
        this.name = name;
    }

    public int getArabic() {
        return arabic;
    }

    public String getName() {
        return name;
    }
}



enum Operation{
    PLUS("+"),
    MINUS("-"),
    MULTIPLICATION("*"),
    DIVIDED("/");

    private String name;

    Operation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

enum operationFunction {
    PLUS{
        public int action (int a, int b){return a + b;}
    },
    MINUS{
        public int action (int a, int b){return a - b;}
    },
    MULTIPLICATION{
        public int action (int a, int b){return a * b;}
    },
    DIVIDED{
        public int action (int a, int b){return a / b;}
    };
    public abstract int action(int x, int y);

}

class Exception extends java.lang.Exception {
    public Exception(String description){
        super(description);

    }
}

enum ArabicFromRoman{

    I(1, "I"),
    II(2, "II"),
    III(3, "III"),
    IV(4, "IV"),
    V(5, "V"),
    VI(6, "VI"),
    VII(7, "VII"),
    VIII(8, "VIII"),
    IX(9, "IX"),
    X(10, "X"),
    XI(11, "XI"),
    XII(12, "XII"),
    XIII(13, "XIII"),
    XIV(14, "XIV"),
    XV(15, "XV"),
    XVI(16, "XVI"),
    XVII(17, "XVII"),
    XVIII(18, "XVIII"),
    XIX(19, "XIX"),
    XX(20, "XX"),
    XXI(21, "XXI"),
    XXIV(24, "XXIV"),
    XXV(25, "XXV"),
    XXVII(27, "XXVII"),
    XXVIII(28, "XXVIII"),
    XXX(30, "XXX"),
    XXXII(32, "XXXII"),
    XXXV(35, "XXXV"),
    XXXVI(36, "XXXVI"),
    XL(40, "XL"),
    XLII(42, "XLII"),
    XLV(45, "XLV"),
    XLVIII(48, "XLVIII"),
    XLIX(49, "XLIX"),
    L(50, "L"),
    LIV(54, "LIV"),
    LVI(56, "LVI"),
    LX(60, "LX"),
    LXIII(63, "LXIII"),
    LXIV(64, "LXIV"),
    LXX(70, "LXX"),
    LXXVI(72, "LVI"),
    LXXX(80, "LXXX"),
    LXXXI(81, "LXXXI"),
    XC(90, "XC"),
    C(100, "C");


    final int arabic;
    private String name;

    ArabicFromRoman(int arabic, String name) {
        this.arabic = arabic;
        this.name = name;
    }

    public int getArabic() {
        return arabic;
    }

    public String getName() {
        return name;
    }
}






