#ifndef SECURINO_H
#define SECURINO_H
#include "Arduino.h"

class Securino
{
    public:
        void show(String a, byte vet[]);
        void show(String a, byte vet[], int sizeVet);
        void show(String a, String b, byte vet[]);
        byte * encript(String operacao, byte in[], byte k[]);
        byte * decript(String operacao, byte in[], byte k[]);
        byte * getVIRandom();
    private:
        void setKey(byte x[16]);
        void setData(byte x[16]);
        byte * getData();
        byte * getDataCBC(byte randKey[]);
        void encAES128(byte plainText[][4], byte chave[][4]);
        void decAES128(byte plainText[][4], byte chave[][4]);
        byte * AddRoundKey(byte state[][4], byte roundkey[][4]);
        byte tableS(byte linha, byte coluna);
        byte tableInvS(byte linha, byte coluna);
        byte subX(byte valor);
        byte invSub(byte valor);
        void subBytes(byte state[][4]);
        void InvSubBytes(byte state[][4]);
        void SubBytesKey(byte chave[]);
        void ShiftRows(byte state[][4]);
        void invShiftRows(byte state [][4]);
        byte tableE(byte linha, byte coluna);
        byte tableL(byte linha, byte coluna);
        byte L(byte valor);
        byte E(byte valor);
        byte calculaLE(byte valor1, byte valor2);
        void MixColumns(byte matrizEntrada[][4]);
        void MixColumnsInversa(byte matrizResultante[][4]);
        void KeySchedule(byte chave[][4], int round);
        void InvKeySchedule(byte chave[][4], int rodada);
        void geraVetorInicializacao(byte ivRandom[]);
        void split(byte state[], byte iv[], byte stateIV[]);
};

#endif