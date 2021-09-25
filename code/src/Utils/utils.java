package Utils;

public class utils {
    private static final int UINT_MAX = 65535;
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

    public static boolean checkDOUBLERange(String buffer){
        Double aux = Double.parseDouble(buffer);
        if (aux > 0) {
            if (aux < POSITIVE_DOUBLE_MIN || aux > POSITIVE_DOUBLE_MAX) {
                return false;
            } else {
                return true;
            }
        }else{
            if (aux < NEGATIVE_DOUBLE_MIN || aux > NEGATIVE_DOUBLE_MAX) {
                return false;
            } else {
                return true;
            }
        }
    }
}
