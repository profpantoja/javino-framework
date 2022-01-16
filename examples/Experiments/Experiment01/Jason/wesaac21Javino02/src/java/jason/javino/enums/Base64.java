package jason.javino.enums;

import jason.javino.constants.JavinoConstants;
import jason.javino.exception.BaseConversionException;

/** Classe enumerável para o números na base 64. */
public enum Base64 {

    /** Elemento A da base 64. */
    B64_A("A", 0),

    /** Elemento B da base 64. */
    B64_B("B", 1),

    /** Elemento C da base 64. */
    B64_C("C", 2),

    /** Elemento D da base 64. */
    B64_D("D", 3),

    /** Elemento E da base 64. */
    B64_E("E", 4),

    /** Elemento F da base 64. */
    B64_F("F", 5),

    /** Elemento G da base 64. */
    B64_G("G", 6),

    /** Elemento H da base 64. */
    B64_H("H", 7),

    /** Elemento I da base 64. */
    B64_I("I", 8),

    /** Elemento J da base 64. */
    B64_J("J", 9),

    /** Elemento K da base 64. */
    B64_K("K", 10),

    /** Elemento L da base 64. */
    B64_L("L", 11),

    /** Elemento M da base 64. */
    B64_M("M", 12),

    /** Elemento N da base 64. */
    B64_N("N", 13),

    /** Elemento O da base 64. */
    B64_O("O", 14),

    /** Elemento P da base 64. */
    B64_P("P", 15),

    /** Elemento Q da base 64. */
    B64_Q("Q", 16),

    /** Elemento R da base 64. */
    B64_R("R", 17),

    /** Elemento S da base 64. */
    B64_S("S", 18),

    /** Elemento T da base 64. */
    B64_T("T", 19),

    /** Elemento U da base 64. */
    B64_U("U", 20),

    /** Elemento V da base 64. */
    B64_V("V", 21),

    /** Elemento W da base 64. */
    B64_W("W", 22),

    /** Elemento X da base 64. */
    B64_X("X", 23),

    /** Elemento Y da base 64. */
    B64_Y("Y", 24),

    /** Elemento Z da base 64. */
    B64_Z("Z", 25),

    /** Elemento a da base 64. */
    B64_a("a", 26),

    /** Elemento b da base 64. */
    B64_b("b", 27),

    /** Elemento c da base 64. */
    B64_c("c", 28),

    /** Elemento d da base 64. */
    B64_d("d", 29),

    /** Elemento e da base 64. */
    B64_e("e", 30),

    /** Elemento f da base 64. */
    B64_f("f", 31),

    /** Elemento g da base 64. */
    B64_g("g", 32),

    /** Elemento h da base 64. */
    B64_h("h", 33),

    /** Elemento i da base 64. */
    B64_i("i", 34),

    /** Elemento j da base 64. */
    B64_j("j", 35),

    /** Elemento k da base 64. */
    B64_k("k", 36),

    /** Elemento l da base 64. */
    B64_l("l", 37),

    /** Elemento m da base 64. */
    B64_m("m", 38),

    /** Elemento n da base 64. */
    B64_n("n", 39),

    /** Elemento o da base 64. */
    B64_o("o", 40),

    /** Elemento p da base 64. */
    B64_p("p", 41),

    /** Elemento q da base 64. */
    B64_q("q", 42),

    /** Elemento r da base 64. */
    B64_r("r", 43),

    /** Elemento s da base 64. */
    B64_s("s", 44),

    /** Elemento t da base 64. */
    B64_t("t", 45),

    /** Elemento u da base 64. */
    B64_u("u", 46),

    /** Elemento v da base 64. */
    B64_v("v", 47),

    /** Elemento w da base 64. */
    B64_w("w", 48),

    /** Elemento x da base 64. */
    B64_x("x", 49),

    /** Elemento y da base 64. */
    B64_y("y", 50),

    /** Elemento z da base 64. */
    B64_z("z", 51),

    /** Elemento 0 da base 64. */
    B64_0("0", 52),

    /** Elemento 1 da base 64. */
    B64_1("1", 53),

    /** Elemento 2 da base 64. */
    B64_2("2", 54),

    /** Elemento 3 da base 64. */
    B64_3("3", 55),

    /** Elemento 4 da base 64. */
    B64_4("4", 56),

    /** Elemento 5 da base 64. */
    B64_5("5", 57),

    /** Elemento 6 da base 64. */
    B64_6("6", 58),

    /** Elemento 7 da base 64. */
    B64_7("7", 59),

    /** Elemento 8 da base 64. */
    B64_8("8", 60),

    /** Elemento 9 da base 64. */
    B64_9("9", 61),

    /** Elemento + da base 64. */
    B64_Plus_Signal("+", 62),

    /** Elemento / da base 64. */
    B64_Back_Slash("/", 63);

    /** Valor do elemento na base 64. */
    private String element;

    /** Valor do elemento da base 64 em inteiro. */
    private int intValue;

    /** Base name. */
    public static final String BASE_NAME = "Base64";

    /** Base value to convert. */
    public static final int BASE_VALUE = 64;

    /**
     * Construtor.
     *
     * @param element  {@link #element}
     * @param intValue {@link #intValue}
     */
    Base64(String element, int intValue) {
        this.element = element;
        this.intValue = intValue;
    }

    /**
     * Executa a recuperação do elemento na base 64 de acordo com um valor inteiro.
     *
     * @param intValue Valor inteiro.
     * @return {@code #Base64Enum} Valor convertido para a base 64 ou {@code #null} caso não exista elemento com o
     * valor inteiro recebido.
     */
    public static Base64 getByIntValue(int intValue) throws BaseConversionException {
        for (Base64 base64Enum : values()) {
            if (base64Enum.getIntValue() == intValue) {
                return base64Enum;
            }
        }
        throw new BaseConversionException(intValue, BASE_NAME);
    }

    /**
     * Executa a recuperação do elemento na base 64 de acordo com um valor String de um elemento.
     *
     * @param element Valor String de um elemento.
     * @return {@code #Base64Enum} Elemento convertido para a base 64 ou {@code #null} caso não exista este elemento
     * String recebido.
     */
    public static Base64 getByElement(String element) throws BaseConversionException {
        for (Base64 base64Enum : values()) {
            if (base64Enum.getElement().equals(element)) {
                return base64Enum;
            }
        }
        throw new BaseConversionException(element, BASE_NAME);
    }

    /**
     * Executa a recuperação do tamanho da mensagem na base 64 levando em consideração que cada caractere ocupa 1
     * byte, ou seja 8 bits de memória.
     *
     * @param msgSize Tamanho da mensagem em inteiro considerando a quantidade de caracteres.
     * @return {@code #String} Tamanhoo da mensagem na base 64.
     */
    public static String getMsgSize(int msgSize) throws BaseConversionException {
        int msgInBits = msgSize * Byte.SIZE;
        if (msgInBits <= JavinoConstants.MESSAGE_MAX_SIZE_IN_BIT) {
            int rest = msgInBits % Base64.BASE_VALUE;
            int numerator = msgInBits / Base64.BASE_VALUE;
            return Base64.getByIntValue(numerator).getElement() + Base64.getByIntValue(rest).getElement();
        }
        throw new BaseConversionException(msgInBits, Base64.BASE_NAME);
    }
    
    
    public static int getMsgSize(char v1, char v2) {
    	
    	
    	return (B64toInt(v1)*64)+B64toInt(v2)/Byte.SIZE;
    	
    }
    
    private static int B64toInt(char valueIN) {
        int out = 0;
        switch (valueIN) {
        case 'A': out=0; break;
        case 'B': out=1; break;
        case 'C': out=2; break;
        case 'D': out=3; break;
        case 'E': out=4; break;
        case 'F': out=5; break;
        case 'G': out=6; break;
        case 'H': out=7; break;
        case 'I': out=8; break;
        case 'J': out=9; break;
        case 'K': out=10; break;
        case 'L': out=11; break;
        case 'M': out=12; break;
        case 'N': out=13; break;
        case 'O': out=14; break;
        case 'P': out=15; break;
        case 'Q': out=16; break;
        case 'R': out=17; break;
        case 'S': out=18; break;
        case 'T': out=19; break;
        case 'U': out=20; break;
        case 'V': out=21; break;
        case 'W': out=22; break;
        case 'X': out=23; break;
        case 'Y': out=24; break;
        case 'Z': out=25; break;
        case 'a': out=26; break;
        case 'b': out=27; break;
        case 'c': out=28; break;
        case 'd': out=29; break;
        case 'e': out=30; break;
        case 'f': out=31; break;
        case 'g': out=32; break;
        case 'h': out=33; break;
        case 'i': out=34; break;
        case 'j': out=35; break;
        case 'k': out=36; break;
        case 'l': out=37; break;
        case 'm': out=38; break;
        case 'n': out=39; break;
        case 'o': out=40; break;
        case 'p': out=41; break;
        case 'q': out=42; break;
        case 'r': out=43; break;
        case 's': out=44; break;
        case 't': out=45; break;
        case 'u': out=46; break;
        case 'v': out=47; break;
        case 'w': out=48; break;
        case 'x': out=49; break;
        case 'y': out=50; break;
        case 'z': out=51; break;
        case '0': out=52; break;
        case '1': out=53; break;
        case '2': out=54; break;
        case '3': out=55; break;
        case '4': out=56; break;
        case '5': out=57; break;
        case '6': out=58; break;
        case '7': out=59; break;
        case '8': out=60; break;
        case '9': out=61; break;
        case '+': out=62; break;
        case '/': out=63; break;
        }
        return out;
   }
    

    /**
     * @return {@link #element}
     */
    public String getElement() {
        return this.element;
    }

    /**
     * @param element {@link #element}
     */
    public void setElement(String element) {
        this.element = element;
    }

    /**
     * @return {@link #intValue}
     */
    public int getIntValue() {
        return this.intValue;
    }

    /**
     * @param intValue {@link #intValue}
     */
    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    @Override
    public String toString() {
        return this.element;
    }
    

}
