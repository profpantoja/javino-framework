package javino.model;

import javino.exception.BaseConversionException;

/** Classe enumerável para o números na base 16. */
public enum  Base16Enum {

    /** Elemento 0 da base 16. */
    B16_0("0", 0),

    /** Elemento 1 da base 16. */
    B16_1("1", 1),

    /** Elemento 2 da base 16. */
    B16_2("2", 2),

    /** Elemento 3 da base 16. */
    B16_3("3", 3),

    /** Elemento 4 da base 16. */
    B16_4("4", 4),

    /** Elemento 5 da base 16. */
    B16_5("5", 5),

    /** Elemento 6 da base 16. */
    B16_6("6", 6),

    /** Elemento 7 da base 16. */
    B16_7("7", 7),

    /** Elemento 8 da base 16. */
    B16_8("8", 8),

    /** Elemento 9 da base 16. */
    B16_9("9", 9),

    /** Elemento A da base 16. */
    B16_A("A", 10),

    /** Elemento B da base 16. */
    B16_B("B", 11),

    /** Elemento C da base 16. */
    B16_C("C", 12),

    /** Elemento D da base 16. */
    B16_D("D", 13),

    /** Elemento E da base 16. */
    B16_E("E", 14),

    /** Elemento F da base 16. */
    B16_F("F", 15);

    /** Valor do elemento na base 64. */
    private String element;

    /** Valor do elemento da base 64 em inteiro. */
    private int intValue;

    /** Base name. */
    public static final String BASE_NAME = "Base16";

    /** Base value to convert. */
    public static final int BASE_VALUE = 16;

    /**
     * Construtor.
     *
     * @param element {@link #element}
     * @param intValue {@link #intValue}
     */
    Base16Enum(String element, int intValue) {
        this.element = element;
        this.intValue = intValue;
    }

    /**
     * Executa a recuperação do elemento na base 16 de acordo com um valor inteiro.
     *
     * @param intValue Valor inteiro.
     * @return {@code #Base16Enum} Valor convertido para a base 16 ou {@code #null} caso não exista elemento com o
     * valor inteiro recebido.
     */
    public static Base16Enum getByIntValue(int intValue) throws BaseConversionException {
        for (Base16Enum base16Enum : values()) {
            if (base16Enum.getIntValue() == intValue) {
                return base16Enum;
            }
        }
        throw new BaseConversionException(intValue, BASE_NAME);
    }

    /**
     * Executa a recuperação do elemento na base 16 de acordo com um valor String de um elemento.
     * @param element Valor String de um elemento.
     * @return {@code #Base16Enum} Elemento convertido para a base 16 ou {@code #null} caso não exista este elemento
     * String recebido.
     */
    public static Base16Enum getByElement(String element) throws BaseConversionException {
        for (Base16Enum base16Enum : values()) {
            if (base16Enum.getElement().equalsIgnoreCase(element)) {
                return base16Enum;
            }
        }
        throw new BaseConversionException(element, BASE_NAME);
    }

    /**
     * Executa a conversão de inteiro para hexadecimal.
     *
     * @param msgSize Valor a ser convertido.
     * @return {@code #String} Valor em Hexadecimal.
     */
    public static String getMsgSize(int msgSize) {
        String stringOne = Integer.toHexString(msgSize);
        if (msgSize < BASE_VALUE) {
            stringOne = B16_0.getElement() + stringOne;
        }
        return stringOne;
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
