package com.minhaagenciadeviagens.agenciaviagens.Commom;

public class RegCons {

    public static String VAZIO = "^(?!\\s*$).+";
    public static String EMAIL = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$";
    public static String CHARS2 = "^[a-zA-Z](\\s?[a-zA-Z]){1,1}$";
    public static String CEP = "^\\d{5}[-]\\d{3}$";
}
