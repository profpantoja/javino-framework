package javino.utils;

import javino.enums.Base16;

/** Classe de utilidade de conversão de dados. */
public class Conversion {

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
     * Executa a preparação da mensagem para o envio, ou seja, dediciona o preambulo, recupera o tamanho da mensagem
     * e concatena com o próprio conteúdo da mensagem.
     *
     * @param msg Conteúdo da mensagem.
     * @return {@code #String} Mensagem preparada para o envio com preambulo+tamanho+conteúdo.
     */
    public static String prepareToSend(String msg) {
        msg = "fffe" + Base16.getMsgSize(msg.length()) + msg;
        return msg;
    }

}
