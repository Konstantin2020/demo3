package com.company;

import java.io.*;
import java.util.*;
import static java.lang.System.arraycopy;

public class Main {
    public static String[] inputNameFiles;
    private static Integer[] IntArray0;
    private static Integer[] IntArray1;
    private static Integer[] IntArrayForAll;
    private static String[] StringArray0;
    private static String[] StringArray1;
    private static String[] StringArrayForAll;
    private static Boolean SortedFiles = true;
    private static ArrayList<Integer> IntArrayList0 = new ArrayList<>();
    private static ArrayList<Integer> IntArrayList1 = new ArrayList<>();
    private static ArrayList<String> StringArrayList0 = new ArrayList<>();
    private static ArrayList<String> StringArrayList1 = new ArrayList<>();
    private static String OutputFileName;
    private static Boolean VerifyKeyI = false;
    private static Boolean IntVerifyKeyOutPutFileAsc = false;
    private static Boolean IntVerifyKeyOutPutFileDesc = false;
    private static Boolean VerifyKeyS = false;
    private static Boolean StrVerifyKeyOutPutFileAsc = false;
    private static Boolean StrVerifyKeyOutPutFileDesc = false;

    public static void main(String[] args) {
//проверяем наличие ключей, обязательного -i и необязательного -a
        if ((args[0].equals("-a") & args[1].equals("-i")) | (args[0].equals("-i") & args[1].equals("-a"))) {
            OutputFileName = args[2];
            System.out.println("Ascending integer.");
            inputNameFiles = new String[args.length - 3];
            for (int i = 3, k = 0; i < args.length; i++, k++) {//формирование списка файлов если два ключа
                inputNameFiles[k] = args[i];
                System.out.println("Resource file " + inputNameFiles[k]);
            }
            VerifyKeyI = true;
            IntVerifyKeyOutPutFileAsc = true;
        } else
//проверяем наличие одного обязательного -i ключа
            if (args[0].equals("-i") & (!args[0].equals("-d"))) {
                OutputFileName = args[1];
                System.out.println("Default ascending integer.");
                inputNameFiles = new String[args.length - 2];
                for (int i = 2, k = 0; i < args.length; i++, k++) {//формирование списка файлов если только обязательный ключ
                    inputNameFiles[k] = args[i];
                    System.out.println("Resource file " + inputNameFiles[k]);
                }
                VerifyKeyI = true;
                IntVerifyKeyOutPutFileAsc = true;
            }
//проверяем наличие ключей -i и -d (по убыванию)
        if ((args[0].equals("-d") & args[1].equals("-i")) | (args[0].equals("-i") & args[1].equals("-d"))) {
            OutputFileName = args[2];
            System.out.println("Descending integer.");
            inputNameFiles = new String[args.length - 3];
            for (int i = 3, k = 0; i < args.length; i++, k++) {//формирование списка файлов если два ключа
                inputNameFiles[k] = args[i];
                System.out.println("Resource file " + inputNameFiles[k]);
            }
            VerifyKeyI = true;
            IntVerifyKeyOutPutFileDesc = true;
        }

        if (VerifyKeyI == true) {
            for (int z = 0; z < inputNameFiles.length; z++) {
                if (z == 0) { //создание массива-основы из первого файла
                    try (Scanner scan = new Scanner(new FileReader(inputNameFiles[z]))) {
                        while (scan.hasNext()) {
                            try {
                                String regex = "[0-9]+"; //проверка содержатся ли только числа в первом обрабатываемом файле
                                String a = scan.next();
                                if (!a.matches(regex)) {
                                    System.out.println("Invalid data type in file: "+inputNameFiles[z]+". End of the program.");
                                    return;
                                }//завершение программы если не тот тип данных
                                int n = Integer.parseInt(a);
                                IntArrayList0.add(n);
                            } catch (NumberFormatException nfe) {
                            }
                        }
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                    IntArray0 = new Integer[IntArrayList0.size()];
                    IntArray0 = IntArrayList0.toArray(new Integer[0]);
                    for (int x = 1; x < IntArray0.length; x++) { //проверка отсортировано ли содержимое первого файла
                        Integer a = IntArray0[x - 1];
                        Integer b = IntArray0[x];
                        if (a > b) {
                            SortedFiles = false;
                        }
                        if (SortedFiles == false) {//если не отсортирован, то вывод названия файла и его сортировка
                            System.out.println("Unsorted file " + inputNameFiles[z]);
                            IntArray0 = intSortArray(IntArray0);
                            SortedFiles = true;
                            System.out.println("File " + inputNameFiles[z] + " sorted");
                        }
                    }
                } else {
                    IntArrayList1 = new ArrayList<>();
                    IntArray1 = null;
                    try (Scanner scan = new Scanner(new FileReader(inputNameFiles[z]))) {
                        while (scan.hasNext()) {
                            try {
                                String regex = "[0-9]+";//проверка содержатся ли только числа в остальных обрабатываемых файлах
                                String a = scan.next();
                                if (!a.matches(regex)) {
                                    System.out.println("Invalid data type in file: "+inputNameFiles[z]+". End of the program.");
                                    return;
                                }//завершение программы если не тот тип данных
                                int n = Integer.parseInt(a);
                                IntArrayList1.add(n);
                            } catch (NumberFormatException nfe) {
                            }
                        }

                    } catch (IOException ex) {

                        System.out.println(ex.getMessage());
                    }
                    IntArray1 = new Integer[IntArrayList1.size()];
                    IntArray1 = IntArrayList1.toArray(new Integer[0]);
                    for (int x = 1; x < IntArray1.length; x++) { //проверка отсортировано ли содержимое остальных файла
                        Integer a = IntArray1[x - 1];
                        Integer b = IntArray1[x];
                        SortedFiles = true;
                        if (a > b) {
                            SortedFiles = false;
                        }
                    }
                    if (SortedFiles == false) {
                        System.out.println("Unsorted file " + inputNameFiles[z]);
                        IntArray1 = intSortArray(IntArray1);
                        SortedFiles = true;
                        System.out.println("File " + inputNameFiles[z] + " sorted");
                    }

                    IntArrayForAll = integerMergeSort(IntArray0, IntArray1);//сортировка
                    IntArray0 = IntArrayForAll;
                }

            }
        }
        if (IntVerifyKeyOutPutFileAsc == true) {
            System.out.println("Final file " + OutputFileName);
            File fileOutIA = new File(OutputFileName);
            try (FileWriter fw0 = new FileWriter(fileOutIA)) {
                for (int g = 0; g < IntArray0.length; g++) {
                    fw0.write(IntArray0[g].toString());
                    fw0.write(System.getProperty("line.separator"));
                    fw0.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (IntVerifyKeyOutPutFileDesc == true) {
            //сортировка по убыванию при формировании итогового файла
            System.out.println("Final file " + OutputFileName);
            File fileOutID = new File(OutputFileName);
            try (FileWriter fw0 = new FileWriter(fileOutID)) {
                Integer[] RvrIntArray;
                RvrIntArray = intReverseArray(IntArray0);
                for (int g = 0; g < RvrIntArray.length; g++) {
                    fw0.write(RvrIntArray[g].toString());
                    fw0.write(System.getProperty("line.separator"));
                    fw0.flush();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


//проверяем наличие ключей, обязательного -s и необязательного -a
        if ((args[0].equals("-a") & args[1].equals("-s")) | (args[0].equals("-s") & args[1].equals("-a"))) {
            OutputFileName = args[2];
            System.out.println("Ascending String");
            inputNameFiles = new String[args.length - 3];
            for (int i = 3, k = 0; i < args.length; i++, k++) {//формирование списка файлов если два ключа
                inputNameFiles[k] = args[i];
                System.out.println("Resource file " + inputNameFiles[k]);
            }
            VerifyKeyS = true;
            StrVerifyKeyOutPutFileAsc = true;
        } else
//проверяем наличие одного обязательного -s ключа
            if (args[0].equals("-s") & (!args[0].equals("-d"))) {
                OutputFileName = args[1];
                System.out.println("Default ascending String.");
                inputNameFiles = new String[args.length - 2];
                for (int i = 2, k = 0; i < args.length; i++, k++) {//формирование списка файлов если только обязательный ключ
                    inputNameFiles[k] = args[i];
                    System.out.println("Resource file " + inputNameFiles[k]);
                }
                VerifyKeyS = true;
                StrVerifyKeyOutPutFileAsc = true;
            }
        //проверяем наличие ключей -s и -d (по убыванию)
        if ((args[0].equals("-d") & args[1].equals("-s")) | (args[0].equals("-s") & args[1].equals("-d"))) {
            OutputFileName = args[2];
            System.out.println("Descending String");
            inputNameFiles = new String[args.length - 3];
            for (int i = 3, k = 0; i < args.length; i++, k++) {//формирование списка файлов
                inputNameFiles[k] = args[i];
                System.out.println("Resource file " + inputNameFiles[k]);
                VerifyKeyS = true;
                StrVerifyKeyOutPutFileDesc = true;
            }
        }


        if (VerifyKeyS == true) {
            for (int z = 0; z < inputNameFiles.length; z++) {
                if (z == 0) {
                    try (Scanner scan = new Scanner(new FileReader(inputNameFiles[z]))) {
                        while (scan.hasNext()) {
                            try {
                                String n = scan.next();
                                StringArrayList0.add(n);//создание массива-основы из первого файла
                            } catch (NumberFormatException nfe) {
                            }
                        }
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                    StringArray0 = new String[StringArrayList0.size()];
                    StringArray0 = StringArrayList0.toArray(new String[0]);
                    for (int x = 1; x < StringArray0.length; x++) {//проверка отсортировано ли содержимое первого файла
                        String a = StringArray0[x - 1];
                        String b = StringArray0[x];
                        if (a.compareTo(b) > 0) {
                            SortedFiles = false;
                        }
                    }
                    if (SortedFiles == false) {//если не отсортирован, то вывод названия файла и его сортировка
                            System.out.println("Unsorted file " + inputNameFiles[z]);
                            StringArray0 = stringSortArray(StringArray0);
                            SortedFiles = true;
                            System.out.println("File " + inputNameFiles[z] + " sorted");
                }
                } else {
                    StringArrayList1 = new ArrayList<>();
                    StringArray1 = null;
                    try (Scanner scan = new Scanner(new FileReader(inputNameFiles[z]))) {
                        while (scan.hasNext()) {
                            try {
                                String n = scan.next();
                                StringArrayList1.add(n);
                            } catch (NumberFormatException nfe) {
                            }
                        }
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                    StringArray1 = new String[StringArrayList1.size()];
                    StringArray1 = StringArrayList1.toArray(new String[0]);
                    for (int x = 1; x < StringArray1.length; x++) {//проверка отсортировано ли содержимое остальных файлов
                        String a = StringArray1[x - 1];
                        String b = StringArray1[x];
                        if (a.compareTo(b) > 0) {
                            SortedFiles = false;

                        }
                    }
                    if (SortedFiles == false) {//если не отсортирован, то вывод названия файла и его сортировка
                        System.out.println("Unsorted file " + inputNameFiles[z]);
                        StringArray1 = stringSortArray(StringArray1);
                        SortedFiles = true;
                        System.out.println("File " + inputNameFiles[z] + " sorted");}
                    StringArrayForAll = stringMergeSort(StringArray0, StringArray1);//сортировка
                    StringArray0 = StringArrayForAll;
                }

            }
        }
        //формирование итогового файла со строковыми данными по возрастанию
        if (StrVerifyKeyOutPutFileAsc == true) {
            System.out.println("Final file " + OutputFileName);
            File fileOutSA = new File(OutputFileName);
            try (FileWriter fw0 = new FileWriter(fileOutSA)) {
                for (int g = 0; g < StringArray0.length; g++) {
                    fw0.write(StringArray0[g]);
                    fw0.write(System.getProperty("line.separator"));
                    fw0.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        //сортировка по убыванию при формировании итогового файла со строковыми данными
        if (StrVerifyKeyOutPutFileDesc == true) {
            System.out.println("Final file " + OutputFileName);
            File fileOutSD = new File(OutputFileName);
            try (FileWriter fw0 = new FileWriter(fileOutSD)) {
                String[] RvrStringArray;
                RvrStringArray = StringReverseArray(StringArray0);
                for (int g = 0; g < RvrStringArray.length; g++) {
                    fw0.write(RvrStringArray[g]);
                    fw0.write(System.getProperty("line.separator"));
                    fw0.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

        //метод сортировки слиянием одного числового неотсортированного массива
        public static Integer[] intSortArray (Integer[] ns0){
            if (ns0 == null) {
                return null;
            }
            if (ns0.length < 2) {
                return ns0;
            }
            Integer[] ns1 = new Integer[ns0.length / 2];
            System.arraycopy(ns0, 0, ns1, 0, ns0.length / 2);
            Integer[] ns2 = new Integer[ns0.length - ns0.length / 2];
            System.arraycopy(ns0, ns0.length / 2, ns2, 0, ns0.length - ns0.length / 2);
            ns1 = intSortArray(ns1);
            ns2 = intSortArray(ns2);
            return integerMergeSort(ns1, ns2);
        }

    //метод сортировки слиянием одного строкового неотсортированного массива
    public static String[] stringSortArray (String[] ns0){
        if (ns0 == null) {
            return null;
        }
        if (ns0.length < 2) {
            return ns0;
        }
        String[] ns1 = new String[ns0.length / 2];
        System.arraycopy(ns0, 0, ns1, 0, ns0.length / 2);
        String[] ns2 = new String[ns0.length - ns0.length / 2];
        System.arraycopy(ns0, ns0.length / 2, ns2, 0, ns0.length - ns0.length / 2);
        ns1 = stringSortArray(ns1);
        ns2 = stringSortArray(ns2);
        return stringMergeSort(ns1, ns2);
    }

        //метод сортировки слиянием двух массивов для числовых данных
        static Integer[] integerMergeSort (Integer[]a, Integer[]b){
            IntArrayForAll = new Integer[a.length + b.length];
            int i = 0, j = 0, k = 0;
            while (i < a.length && j < b.length) {
                IntArrayForAll[k++] = a[i] < b[j] ? a[i++] : b[j++];
            }
            if (i < a.length) {
                arraycopy(a, i, IntArrayForAll, k, a.length - i);
            } else if (j < b.length) {
                arraycopy(b, j, IntArrayForAll, k, b.length - j);
            }
            return IntArrayForAll;
        }
        //метод сортировки слиянием двух массивов для строковых данных
        static String[] stringMergeSort (String[]a, String[]b){
            StringArrayForAll = new String[a.length + b.length];
            int i = 0, j = 0, k = 0;
            while (i < a.length && j < b.length) {
                StringArrayForAll[k++] = (a[i].compareTo(b[j]) < 0) ? a[i++] : b[j++];
            }
            if (i < a.length) {
                arraycopy(a, i, StringArrayForAll, k, a.length - i);
            } else if (j < b.length) {
                arraycopy(b, j, StringArrayForAll, k, b.length - j);
            }
            return StringArrayForAll;
        }

        //метод для сортировки числовых данных по убыванию
        static Integer[] intReverseArray (Integer[]a){
            Integer[] b = new Integer[a.length];
            for (int k = 0, t = a.length - 1; k < a.length; k++, t--) {
                b[t] = a[k];
            }
            return b;
        }

        //метод для сортировки строковых данных по убыванию
        static String[] StringReverseArray (String[]a){
            String[] b = new String[a.length];
            for (int k = 0, t = a.length - 1; k < a.length; k++, t--) {
                b[t] = a[k];
            }
            return b;
        }
}
