package com.hynet.mergepay.utils.constant;

public final class Constant {

    private Constant() {}

    public final static class View {
        public static final int DRAWABLE_TOP = 0x1001;
        public static final int DRAWABLE_LEFT = 0x1002;
        public static final int DRAWABLE_RIGHT = 0x1003;
        public static final int DRAWABLE_BOTTOM = 0x1004;
        public static final long CLICK_PERIOD = -500;
        public static final int COLOR_DEFAULT = 0x9999;
        public static final int SIZE_DEFAULT = 0x9999;
        public static final int RESOURCE_DEFAULT = 0x9999;
    }

    public final static class Device {
        public final static String DEVICE_NAME = "phoneName";
        public final static String DEVICE_VERSION = "systemVersion";
        public final static String DEVICE_VERSION_NAME = "systemName";
        public final static String DEVICE_TYPE = "phoneModel";
        public final static String DEVICE_ID = "phoneID";
        public final static String SUBSCRIBER_ID = "carrierName";
        public final static String DEVICE_IP = "ipAddress";
        public final static String DEVICE_CORE = "countCores";
    }

    public static class HttpTask {
        public static final int REQUEST_TIME_OUT_PERIOD = 30000;
        public static final String DEFAULT_TASK_KEY = "default_key";
        public static final String TIME_OUT = "timeout";
    }

    public static class Extra {
        public static final int DEFAULT_VALUE = 0x9999;
    }

    public final static class Data {
        public final static String KEY = "39EB339F80B715384793F7EF";
        public final static String FROMAT = "ToHex16";
        public static final String ALGORITHM_CBC = "DES/ECB/NoPadding";// "DES/CBC/NoPadding";
        public static final String ALGORITHM_ECB = "DES/ECB/NoPadding";
        public static final String ALGORITHM0 = "DES";
        public static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        public static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        public static final byte[] TABLE = {
                (byte) 'A', (byte) 'B', (byte) 'C', (byte) 'D', (byte) 'E',
                (byte) 'F', (byte) 'G', (byte) 'H', (byte) 'I', (byte) 'J',
                (byte) 'K', (byte) 'L', (byte) 'M', (byte) 'N', (byte) 'O',
                (byte) 'P', (byte) 'Q', (byte) 'R', (byte) 'S', (byte) 'T',
                (byte) 'U', (byte) 'V', (byte) 'W', (byte) 'X', (byte) 'Y',
                (byte) 'Z', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd',
                (byte) 'e', (byte) 'f', (byte) 'g', (byte) 'h', (byte) 'i',
                (byte) 'j', (byte) 'k', (byte) 'l', (byte) 'm', (byte) 'n',
                (byte) 'o', (byte) 'p', (byte) 'q', (byte) 'r', (byte) 's',
                (byte) 't', (byte) 'u', (byte) 'v', (byte) 'w', (byte) 'x',
                (byte) 'y', (byte) 'z', (byte) '0', (byte) '1', (byte) '2',
                (byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7',
                (byte) '8', (byte) '9', (byte) '+', (byte) '/'
        };
    }
}
