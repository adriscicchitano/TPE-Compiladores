package Utils;

import IntermediateCode.Terceto;
import StateTransitionMatrix.StructureUtilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class utils {
    public static final int UINT_MAX = 65535;
    public static final int UINT_MIN = 0;
    private static final double POSITIVE_DOUBLE_MIN = 2.2250738585072014E-308;
    private static final double POSITIVE_DOUBLE_MAX = 1.7976931348623157E+308;
    private static final double NEGATIVE_DOUBLE_MIN = -1.7976931348623157E+308;
    private static final double NEGATIVE_DOUBLE_MAX = -2.2250738585072014E-308;

    public static boolean checkUINTRange(String buffer) {
        Integer aux = Integer.parseInt(buffer);
        if (aux > UINT_MAX) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkDOUBLERange(String buffer) {
        Double aux = Double.parseDouble(buffer);
        if (aux == 0.0)
            return true;
        if (aux > 0) {
            if (aux < POSITIVE_DOUBLE_MIN || aux > POSITIVE_DOUBLE_MAX) {
                return false;
            } else {
                return true;
            }
        } else {
            if (aux < NEGATIVE_DOUBLE_MIN || aux > NEGATIVE_DOUBLE_MAX) {
                return false;
            } else {
                return true;
            }
        }
    }

    public static boolean isNumber(String s){
        return (s.charAt(0) == '-') ? Character.isDigit(s.charAt(1)) || s.charAt(1) == '.' : Character.isDigit(s.charAt(0)) || s.charAt(0) == '.';
    }

    public static String formattedList(List<String> list){
        String result = "";
        for(String t :  list){
            result += t + '\n';
        }
        return result;
    }

    public static void exportResults(StructureUtilities su, List<Terceto> tercetos) throws IOException {
        BufferedWriter writer;
        writer = new BufferedWriter(new FileWriter("errors.txt"));
        writer.write(su.showErrors());
        writer.close();
        writer = new BufferedWriter(new FileWriter("warnings.txt"));
        writer.write(su.showWarnings());
        writer.close();
        writer = new BufferedWriter(new FileWriter("symbols_table.txt"));
        writer.write(su.showSymbolsTable());
        writer.close();
        writer = new BufferedWriter(new FileWriter("tercetos.txt"));
        String tercetosStr = "";
        for(Terceto t : tercetos)
            tercetosStr += t.toString() + "\n";
        writer.write(tercetosStr);
        writer.close();
    }

    public static void exportErrors(StructureUtilities su) throws IOException {
        BufferedWriter writer;
        writer = new BufferedWriter(new FileWriter("errors.txt"));
        writer.write(su.showErrors());
        writer.close();
    }
}
