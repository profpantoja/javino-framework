package javino.utils;

/** Classe de utilidade de conversão de dados. */
public class ConversionUtils {

    /**
     * Executa a conversão de um vetor de caracteres para uma String.
     *
     * @param in     Vetor de caracteres.
     * @param sizein Tamanho do vetor de caracteres.
     * @return {@code #String} Vetor convertido para String.
     */
    public static String charToString(char in[], int sizein) {
        int newsize = sizein - 6;
        char[] output = new char[newsize];
        int cont = 0;
        for (int i = 6; i < sizein; i++) {
            output[cont] = in[i];
            cont++;
        }
        return String.valueOf(output);
    }

    /**
     * Executa a conversão de inteiro para hexadecimal.
     *
     * @param amount Valor a ser convertido.
     * @return {@code #String} Valor em Hexadecimal.
     */
    public static String intToHex(int amount) {
        String stringOne = Integer.toHexString(amount);
        if (amount < 16) {
            stringOne = "0" + stringOne;
        }
        return stringOne;
    }

    /**
     * Executa a conversão de um elemento em hexadecimal para inteiro.
     *
     * @param v Elemento em hexadecimal.
     * @return {@code #Integer} Valor convertido para inteiro.
     */
    public static int hexToInt(char v) {
        int vI = 0;
        switch (v) {
            case '1':
                vI = 1;
                break;
            case '2':
                vI = 2;
                break;
            case '3':
                vI = 3;
                break;
            case '4':
                vI = 4;
                break;
            case '5':
                vI = 5;
                break;
            case '6':
                vI = 6;
                break;
            case '7':
                vI = 7;
                break;
            case '8':
                vI = 8;
                break;
            case '9':
                vI = 9;
                break;
            case 'a':
                vI = 10;
                break;
            case 'b':
                vI = 11;
                break;
            case 'c':
                vI = 12;
                break;
            case 'd':
                vI = 13;
                break;
            case 'e':
                vI = 14;
                break;
            case 'f':
                vI = 15;
                break;
        }
        return vI;
    }

    /**
     * Executa a conversão de um inteiro para base 64.
     *
     * @param amount Valor inteiro a ser convertido.
     * @return {@code #String} Valor convertido para base 64.
     */
    public static String intToB64(int amount) {
        String vI = "A";
        switch (amount) {
            case 0:
                vI = "A";
                break;
            case 1:
                vI = "B";
                break;
            case 2:
                vI = "C";
                break;
            case 3:
                vI = "D";
                break;
            case 4:
                vI = "E";
                break;
            case 5:
                vI = "F";
                break;
            case 6:
                vI = "G";
                break;
            case 7:
                vI = "H";
                break;
            case 8:
                vI = "I";
                break;
            case 9:
                vI = "J";
                break;
            case 10:
                vI = "K";
                break;
            case 11:
                vI = "L";
                break;
            case 12:
                vI = "M";
                break;
            case 13:
                vI = "N";
                break;
            case 14:
                vI = "O";
                break;
            case 15:
                vI = "P";
                break;
            case 16:
                vI = "Q";
                break;
            case 17:
                vI = "R";
                break;
            case 18:
                vI = "S";
                break;
            case 19:
                vI = "T";
                break;
            case 20:
                vI = "U";
                break;
            case 21:
                vI = "V";
                break;
            case 22:
                vI = "W";
                break;
            case 23:
                vI = "X";
                break;
            case 24:
                vI = "Y";
                break;
            case 25:
                vI = "Z";
                break;
            case 26:
                vI = "a";
                break;
            case 27:
                vI = "b";
                break;
            case 28:
                vI = "c";
                break;
            case 29:
                vI = "d";
                break;
            case 30:
                vI = "e";
                break;
            case 31:
                vI = "f";
                break;
            case 32:
                vI = "g";
                break;
            case 33:
                vI = "h";
                break;
            case 34:
                vI = "i";
                break;
            case 35:
                vI = "j";
                break;
            case 36:
                vI = "k";
                break;
            case 37:
                vI = "l";
                break;
            case 38:
                vI = "m";
                break;
            case 39:
                vI = "n";
                break;
            case 40:
                vI = "o";
                break;
            case 41:
                vI = "p";
                break;
            case 42:
                vI = "q";
                break;
            case 43:
                vI = "r";
                break;
            case 44:
                vI = "s";
                break;
            case 45:
                vI = "t";
                break;
            case 46:
                vI = "u";
                break;
            case 47:
                vI = "v";
                break;
            case 48:
                vI = "w";
                break;
            case 49:
                vI = "x";
                break;
            case 50:
                vI = "y";
                break;
            case 51:
                vI = "z";
                break;
            case 52:
                vI = "0";
                break;
            case 53:
                vI = "1";
                break;
            case 54:
                vI = "2";
                break;
            case 55:
                vI = "3";
                break;
            case 56:
                vI = "4";
                break;
            case 57:
                vI = "5";
                break;
            case 58:
                vI = "6";
                break;
            case 59:
                vI = "7";
                break;
            case 60:
                vI = "8";
                break;
            case 61:
                vI = "9";
                break;
            case 62:
                vI = "+";
                break;
            case 63:
                vI = "/";
                break;
        }
        return vI;
    }

    /**
     * Executa a conversão de um elemento na base 64 para inteiro.
     *
     * @param character Elemento na base 64.
     * @return {@code #Integer} Valor convertido para inteiro.
     */
    public static int b64ToInt(char character) {
        int vI = 0;
        switch (character) {
            case 'A':
                vI = 0;
                break;
            case 'B':
                vI = 1;
                break;
            case 'C':
                vI = 2;
                break;
            case 'D':
                vI = 3;
                break;
            case 'E':
                vI = 4;
                break;
            case 'F':
                vI = 5;
                break;
            case 'G':
                vI = 6;
                break;
            case 'H':
                vI = 7;
                break;
            case 'I':
                vI = 8;
                break;
            case 'J':
                vI = 9;
                break;
            case 'K':
                vI = 10;
                break;
            case 'L':
                vI = 11;
                break;
            case 'M':
                vI = 12;
                break;
            case 'N':
                vI = 13;
                break;
            case 'O':
                vI = 14;
                break;
            case 'P':
                vI = 15;
                break;
            case 'Q':
                vI = 16;
                break;
            case 'R':
                vI = 17;
                break;
            case 'S':
                vI = 18;
                break;
            case 'T':
                vI = 19;
                break;
            case 'U':
                vI = 20;
                break;
            case 'V':
                vI = 21;
                break;
            case 'W':
                vI = 22;
                break;
            case 'X':
                vI = 23;
                break;
            case 'Y':
                vI = 24;
                break;
            case 'Z':
                vI = 25;
                break;
            case 'a':
                vI = 26;
                break;
            case 'b':
                vI = 27;
                break;
            case 'c':
                vI = 28;
                break;
            case 'd':
                vI = 29;
                break;
            case 'e':
                vI = 30;
                break;
            case 'f':
                vI = 31;
                break;
            case 'g':
                vI = 32;
                break;
            case 'h':
                vI = 33;
                break;
            case 'i':
                vI = 34;
                break;
            case 'j':
                vI = 35;
                break;
            case 'k':
                vI = 36;
                break;
            case 'l':
                vI = 37;
                break;
            case 'm':
                vI = 38;
                break;
            case 'n':
                vI = 39;
                break;
            case 'o':
                vI = 40;
                break;
            case 'p':
                vI = 41;
                break;
            case 'q':
                vI = 42;
                break;
            case 'r':
                vI = 43;
                break;
            case 's':
                vI = 44;
                break;
            case 't':
                vI = 45;
                break;
            case 'u':
                vI = 46;
                break;
            case 'v':
                vI = 47;
                break;
            case 'w':
                vI = 48;
                break;
            case 'x':
                vI = 49;
                break;
            case 'y':
                vI = 50;
                break;
            case 'z':
                vI = 51;
                break;
            case '0':
                vI = 52;
                break;
            case '1':
                vI = 53;
                break;
            case '2':
                vI = 54;
                break;
            case '3':
                vI = 55;
                break;
            case '4':
                vI = 56;
                break;
            case '5':
                vI = 57;
                break;
            case '6':
                vI = 58;
                break;
            case '7':
                vI = 59;
                break;
            case '8':
                vI = 60;
                break;
            case '9':
                vI = 61;
                break;
            case '+':
                vI = 62;
                break;
            case '/':
                vI = 63;
                break;
        }
        return vI;
    }

    /**
     * Executa a recuperação do tamanho da mensagem na base 64 levando em consideração que cada caractere ocupa 1
     * byte, ou seja 8 bits de memória.
     *
     * @param msgSize Tamanho da mensagem em inteiro considerando a quantidade de caracteres.
     * @return {@code #String} Tamanhoo da mensagem na base 64.
     */
    public static String getMsgSizeInB64(int msgSize) {
        int msgInBits = msgSize * 8;
        if (msgInBits <= 4096) {
            int rest = msgInBits % 64;
            int numerator = msgInBits / 64;
            return intToB64(numerator) + intToB64(rest);
        }
        return "AA";
    }

    /**
     * Executa a preparação da mensagem para o envio, ou seja, dediciona o preambulo, recupera o tamanho da mensagem
     * e concatena com o próprio conteúdo da mensagem.
     *
     * @param msg Conteúdo da mensagem.
     * @return {@code #String} Mensagem preparada para o envio com preambulo+tamanho+conteúdo.
     */
    public static String prepareToSend(String msg) {
        msg = "fffe" + intToHex(msg.length()) + msg;
        return msg;
    }

}
