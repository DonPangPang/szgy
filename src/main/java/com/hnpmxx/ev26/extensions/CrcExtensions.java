package com.hnpmxx.ev26.extensions;

import java.util.Arrays;

@SuppressWarnings("DuplicatedCode")
public class CrcExtensions {
    private static final int[] Crctab16 = {
            0X0000, 0X1189, 0X2312, 0X329B, 0X4624, 0X57AD, 0X6536, 0X74BF,
            0X8C48, 0X9DC1, 0XAF5A, 0XBED3, 0XCA6C, 0XDBE5, 0XE97E, 0XF8F7,
            0X1081, 0X0108, 0X3393, 0X221A, 0X56A5, 0X472C, 0X75B7, 0X643E,
            0X9CC9, 0X8D40, 0XBFDB, 0XAE52, 0XDAED, 0XCB64, 0XF9FF, 0XE876,
            0X2102, 0X308B, 0X0210, 0X1399, 0X6726, 0X76AF, 0X4434, 0X55BD,
            0XAD4A, 0XBCC3, 0X8E58, 0X9FD1, 0XEB6E, 0XFAE7, 0XC87C, 0XD9F5,
            0X3183, 0X200A, 0X1291, 0X0318, 0X77A7, 0X662E, 0X54B5, 0X453C,
            0XBDCB, 0XAC42, 0X9ED9, 0X8F50, 0XFBEF, 0XEA66, 0XD8FD, 0XC974,
            0X4204, 0X538D, 0X6116, 0X709F, 0X0420, 0X15A9, 0X2732, 0X36BB,
            0XCE4C, 0XDFC5, 0XED5E, 0XFCD7, 0X8868, 0X99E1, 0XAB7A, 0XBAF3,
            0X5285, 0X430C, 0X7197, 0X601E, 0X14A1, 0X0528, 0X37B3, 0X263A,
            0XDECD, 0XCF44, 0XFDDF, 0XEC56, 0X98E9, 0X8960, 0XBBFB, 0XAA72,
            0X6306, 0X728F, 0X4014, 0X519D, 0X2522, 0X34AB, 0X0630, 0X17B9,
            0XEF4E, 0XFEC7, 0XCC5C, 0XDDD5, 0XA96A, 0XB8E3, 0X8A78, 0X9BF1,
            0X7387, 0X620E, 0X5095, 0X411C, 0X35A3, 0X242A, 0X16B1, 0X0738,
            0XFFCF, 0XEE46, 0XDCDD, 0XCD54, 0XB9EB, 0XA862, 0X9AF9, 0X8B70,
            0X8408, 0X9581, 0XA71A, 0XB693, 0XC22C, 0XD3A5, 0XE13E, 0XF0B7,
            0X0840, 0X19C9, 0X2B52, 0X3ADB, 0X4E64, 0X5FED, 0X6D76, 0X7CFF,
            0X9489, 0X8500, 0XB79B, 0XA612, 0XD2AD, 0XC324, 0XF1BF, 0XE036,
            0X18C1, 0X0948, 0X3BD3, 0X2A5A, 0X5EE5, 0X4F6C, 0X7DF7, 0X6C7E,
            0XA50A, 0XB483, 0X8618, 0X9791, 0XE32E, 0XF2A7, 0XC03C, 0XD1B5,
            0X2942, 0X38CB, 0X0A50, 0X1BD9, 0X6F66, 0X7EEF, 0X4C74, 0X5DFD,
            0XB58B, 0XA402, 0X9699, 0X8710, 0XF3AF, 0XE226, 0XD0BD, 0XC134,
            0X39C3, 0X284A, 0X1AD1, 0X0B58, 0X7FE7, 0X6E6E, 0X5CF5, 0X4D7C,
            0XC60C, 0XD785, 0XE51E, 0XF497, 0X8028, 0X91A1, 0XA33A, 0XB2B3,
            0X4A44, 0X5BCD, 0X6956, 0X78DF, 0X0C60, 0X1DE9, 0X2F72, 0X3EFB,
            0XD68D, 0XC704, 0XF59F, 0XE416, 0X90A9, 0X8120, 0XB3BB, 0XA232,
            0X5AC5, 0X4B4C, 0X79D7, 0X685E, 0X1CE1, 0X0D68, 0X3FF3, 0X2E7A,
            0XE70E, 0XF687, 0XC41C, 0XD595, 0XA12A, 0XB0A3, 0X8238, 0X93B1,
            0X6B46, 0X7ACF, 0X4854, 0X59DD, 0X2D62, 0X3CEB, 0X0E70, 0X1FF9,
            0XF78F, 0XE606, 0XD49D, 0XC514, 0XB1AB, 0XA022, 0X92B9, 0X8330,
            0X7BC7, 0X6A4E, 0X58D5, 0X495C, 0X3DE3, 0X2C6A, 0X1EF1, 0X0F78,
    };

    /// <summary>
    /// 包长度 - 信息序列号的值
    /// </summary>
    /// <param name="buffer"></param>
    /// <returns></returns>
    public static short GetCrc16(byte[] buffer) {
//        int length = buffer.length - 6;

        int length = buffer.length - 6;
        buffer = BufferExtensions.Slice(buffer, 2, buffer.length - 4);
        int fcs = 0x0000ffff;
        int index = 0;
        while (length > 0) {
            fcs = ((fcs >> 8) ^ Crctab16[(fcs ^ buffer[index]) & 0xff]);
            length--;
            index++;
        }

        return (short) ~fcs;
    }

    /// <summary>
    /// 包长度 - 信息序列号的值
    /// </summary>
    /// <param name="buffer"></param>
    /// <returns></returns>
    public static int GetCrc16tt(byte[] buffer) {
//        int length = buffer.length - 6;

        int length = buffer.length - 6;
        buffer = BufferExtensions.Slice(buffer, 2, buffer.length - 4);
        int fcs = 0x0000ffff;
        int index = 0;
        while (length > 0) {
            fcs = ((fcs >> 8) ^ Crctab16[(fcs ^ buffer[index]) & 0xff]);
            length--;
            index++;
        }

        return ~fcs;
    }

    /// <summary>
    /// 包长度 - 信息序列号的值
    /// </summary>
    /// <param name="buffer"></param>
    /// <returns></returns>
    public static short GetCrc16ForPackage(byte[] buffer) {
        int length = buffer.length - 2;
        buffer = BufferExtensions.Slice(buffer, 2, length);
        int fcs = 0x0000ffff;
        int index = 0;
        while (length > 0) {
            fcs = ((fcs >> 8) ^ Crctab16[(fcs ^ buffer[index]) & 0xff]);
            length--;
            index++;
        }

        return (short) ~fcs;
    }

    /// <summary>
    /// 包长度 - 信息序列号的值
    /// </summary>
    /// <param name="buffer"></param>
    /// <returns></returns>
    public static short GetCrc16ForHeader(byte[] buffer) {
        int length = buffer.length;
        buffer = Arrays.copyOfRange(buffer, 0, length);
        short fcs = (short) 0xffff;
        int index = 0;
        while (length > 0) {
            fcs = (short) ((fcs >> 8) ^ Crctab16[(fcs ^ buffer[index]) & 0xff]);
            length--;
            index++;
        }

        return (short) ~fcs;
    }

//    /// <summary>
//    /// 包长度 - 信息序列号的值
//    /// </summary>
//    /// <param name="buffer"></param>
//    /// <returns></returns>
//    public static short GetCrc16(byte[] buffer)
//    {
//        //int length = buffer.length - 6;
//        int length = buffer.length - 4;
//        buffer = Arrays.copyOfRange(buffer, 0, length);
//        short fcs = (short) 0xffff;
//        int index = 0;
//        while (length > 0)
//        {
//            fcs = (short)((fcs >> 8) ^ Crctab16[(fcs ^ buffer[index]) & 0xff]);
//            length--;
//            index++;
//        }
//
//        return (short)~fcs;
//    }

    /// <summary>
    /// 包长度 - 信息序列号的值
    /// </summary>
    /// <param name="buffer"></param>
    /// <returns></returns>
    public static short GetCrc16(byte[] buffer, boolean isNeedStartEnd) {
        int length = isNeedStartEnd ? buffer.length - 6 : buffer.length - 2;
//        buffer = isNeedStartEnd ? Arrays.copyOfRange(buffer, 2, buffer.length) : Arrays.copyOfRange(buffer, 0, length);
        buffer = isNeedStartEnd ? BufferExtensions.Slice(buffer, 2, length) : BufferExtensions.Slice(buffer, 0, length);
        int fcs = 0x0000ffff;
        int index = 0;
        while (length > 0) {
            fcs = ((fcs >> 8) ^ Crctab16[(fcs ^ buffer[index]) & 0xff]);
            length--;
            index++;
        }

        return (short) ~fcs;
    }

    public static boolean AuthCrc(byte[] buffer, short crc) {
        return CrcExtensions.GetCrc16(buffer) == crc;
    }

    public static boolean AuthCrc(byte[] buffer, short crc, boolean isNeedStartEnd) {
        short value = CrcExtensions.GetCrc16(buffer, isNeedStartEnd);
        byte a = (byte) (value << 8 >> 8);
        byte b = (byte) (value >> 8);
        return CrcExtensions.GetCrc16(buffer, isNeedStartEnd) == crc;
    }

    public static short toCrc(byte[] buffer) {
        return (short) ((buffer[0] << 8) + buffer[1]);
    }

    public static void main(String[] args) {
//        byte[] hex = HexExtensions.toHexBytes("78 78 11 01 07 52 53 36 78 90 02 42 70 00 32 01 00 05 12 79 0D 0A");
//        int crc1 = CrcExtensions.toCrc(BufferExtensions.Slice(hex, hex.length - 4, 2));
//        short crc = CrcExtensions.toCrc(BufferExtensions.Slice(hex, hex.length - 4, 2));
//        short r_crc = CrcExtensions.GetCrc16(hex);
//
//        short value = (short) 65536;
//
//        System.out.println(value);
        //7878110108525336789002427000320100058D9D0D0A
        byte[] hex = HexExtensions.toHexBytes("7878110108525336789002427000320100058D9D0D0A");
        int crc1 = CrcExtensions.toCrc(BufferExtensions.Slice(hex, hex.length - 4, 2));
        short crc = CrcExtensions.toCrc(BufferExtensions.Slice(hex, hex.length - 4, 2));
        short r_crc = CrcExtensions.GetCrc16(hex);
        short crc2 = CrcExtensions.GetCrc16ForPackage(hex);


        short value = (short) 65536;

        System.out.println(value);
    }
}
