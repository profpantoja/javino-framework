package javino.model;

/** Classe enumerável para o números na base 64. */
public enum Base64Enum {

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

    /**
     * Construtor.
     *
     * @param element  {@link #element}
     * @param intValue {@link #intValue}
     */
    Base64Enum(String element, int intValue) {
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
    public static Base64Enum getByIntValue(int intValue) {
        for (Base64Enum base64Enum : values()) {
            if (base64Enum.getIntValue() == intValue) {
                return base64Enum;
            }
        }
        return null;
    }

    /**
     * Executa a recuperação do elemento na base 64 de acordo com um valor String de um elemento.
     * @param element Valor String de um elemento.
     * @return {@code #Base64Enum} Elemento convertido para a base 64 ou {@code #null} caso não exista este elemento
     * String recebido.
     */
    public static Base64Enum getByElement(String element) {
        for (Base64Enum base64Enum : values()) {
            if (base64Enum.getElement().equals(element)) {
                return base64Enum;
            }
        }
        return null;
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
