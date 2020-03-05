package com.weitongming.util;


import org.apache.commons.lang3.ArrayUtils;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 与byte[]有关的帮助方法
 *
 *
 */
public class ByteUtil {
    /**
     * 从byte[]里得到int
     *
     * @param array
     * @param index
     * @return
     */
    public static int readInt(byte[] array, int index) {
        byte[] temp = ArrayUtils.subarray(array, index, index + 4);
        // ArrayUtils.reverse(temp);
        int result = new BigInteger(temp).intValue();

        return result;
    }

    /**
     * 从byte[]里得到short
     *
     * @param array
     * @param index
     * @return
     */
    public static short readShort(byte[] array, int index) {
        byte[] temp = ArrayUtils.subarray(array, index, index + 2);
        // ArrayUtils.reverse(temp);
        short result = new BigInteger(temp).shortValue();

        return result;
    }

    /**
     * 从byte[]里得到float
     *
     * @param array
     * @param index
     * @return
     */
    public static float readFloat(byte[] array, int index) {
        int i = readInt(array, index);
        return Float.intBitsToFloat(i);
    }


    /**
     * 将多个byte[]组合成一个byte[]
     *
     * @param array
     * @return
     */
    public static byte[] addAll(byte[][] array) {
        byte[] result = new byte[0];

        for (int i = 0; i < array.length; i++) {
            result = ArrayUtils.addAll(result, array[i]);
        }
        return result;
    }


    public static int getUnsignedByte(byte data) { // 将data字节型数据转换为0~255 (0xFF
        // 即BYTE)。
        return data & 0x0FF;
    }
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
    //short范围是-32768~32767
    public static int getUnsignedByte(short data) { // 将data字节型数据转换为0~65535
        // (0xFFFF 即
        // WORD)。
        return data & 0x0FFFF;
    }

    public static long getUnsignedInt(int data) { // 将int数据转换为0~4294967295
        // (0xFFFFFFFF即DWORD)。
        return data & 0x0FFFFFFFFL;
    }

    public static String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }



    public static String byteToBit(byte b) {
        return "" + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1) + (byte) ((b >> 5) & 0x1)
                + (byte) ((b >> 4) & 0x1) + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1) + (byte) ((b >> 1) & 0x1)
                + (byte) ((b) & 0x1);
    }

    public static byte bitToByte(String byteStr) {
        int re, len;
        if (null == byteStr) {
            return 0;
        }
        len = byteStr.length();
        if (len != 4 && len != 8) {
            return 0;
        }
        if (len == 8) {// 8 bit处理
            if (byteStr.charAt(0) == '0') {// 正数
                re = Integer.parseInt(byteStr, 2);
            } else {// 负数
                re = Integer.parseInt(byteStr, 2) - 256;
            }
        } else {// 4 bit处理
            re = Integer.parseInt(byteStr, 2);
        }
        return (byte) re;
    }

    /**
     * 0xff是00000000 00000000 00000000 11111111，是一个整数,&0xff?
     * -28对应数组是[-1,-28]
     * (short)-28原码是10000000 00011100，补码1111111 11100100
     * @param n
     * @return
     */
    public static byte[] shortToBytes(int n) {
        byte[] b = new byte[2];
        b[1] = (byte) (n & 0xff);//取byte为11100100,就对应反码11100011,原码10011100 对应-28
        b[0] = (byte) ((n >> 8) & 0xff);//有符号右移8位后是11111111 11111111
        //与&oxff与后是(24个0)+11111111,截取byte取11111111,(-1原码10000001 补码11111111)
        return b;
    }
    public static byte shortToByte(short value) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putShort(value);
        byte[] bytes = new byte[1];
        System.arraycopy(buffer.array(), 0, bytes, 0, bytes.length);
        return bytes[0];
    }

    //补码:正数和原码相同，负数是原码取反码再加1
    //计算机存的补码,程序显示的原码,int转byte只取低8位,
    //如num=-5,对应补码是111111111 11111111 11111111 11111011,
    //正好对应数组[-1,-1,-1,-5],-1原码10000001 补码11111111
    //这个方法就是把11111111等4个byte分别取出来
    //位移运算符操作的是补码
    //>>>运算符:负数的话右移动后前面补0,>>运算符:负数的话右移后前面补1,>>>和>>对正数一样都是前面补0
    //<<表示左移，不分正负数，低位补0；

    public static byte[] intToBytes(long num) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (num >>> (24 - i * 8));
            //System.out.println(b[i]);
        }
        return b;
    }

    public static String hexString2binaryString(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0) {
            return null;
        }
        StringBuilder bString = new StringBuilder();
        String tmp;
        for (int i = 0; i < hexString.length(); i++) {
            //HexString 1 toBinaryString 1,补零00001,取后4位0001
            tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
            bString.append(tmp.substring(tmp.length() - 4));

        }
        return bString.toString();
    }
    /**
     * int bit = 0;//存放一个4位二进制数字
     //这个for循环i += 4，意思是每次跳过4个字符
     //即 每次循环处理的是4位，（1位十六进制占 4 位二进制，eg：0xF = 1111）
     //其实就是每次取4位，转为十六进制的1位
     //因为字符串没有高低位之分，索引转为
     for (int i = 0; i < binaryString.length(); i += 4) {
     //假设本次循环拿到的二进制数字是：1001
     //取第一个字符："1"
     String str4 = binaryString.substring(i, i + 1);
     //将这个1转为int得到0001，左移3位，将这个1放在最高位 ：1000(1xxx)
     int bit4 = Integer.parseInt(str4) << 3;
     //取第二个字符："0"，左移2位，得到：0000(x0xx)
     int bit3 = Integer.parseInt(binaryString.substring(i + 1, i + 2)) << 2;
     //取第三个字符："0"，左移1位，得到：0000(xx0x)
     int bit2 = Integer.parseInt(binaryString.substring(i + 2, i + 3)) << 1;
     //取第三个字符："1"，不用移动，得到：0001(xxx1)
     int bit1 = Integer.parseInt(binaryString.substring(i + 3, i + 4));

     //1001 = 1000 + 0000 + 0000 + 0001
     bit = bit4 + bit3 + bit2 + bit1;

     hexString += Integer.toHexString(bit);//将二进制转为十六进制
     }
     */
    public static String binaryString2hexString(String bString) {
        if (bString == null || bString.equals("") || bString.length() % 8 != 0)
            return null;
        StringBuffer tmp = new StringBuffer();
        int iTmp = 0;
        for (int i = 0; i < bString.length(); i += 4) {
            iTmp = 0;
            for (int j = 0; j < 4; j++) {
                iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
            }
            tmp.append(Integer.toHexString(iTmp));
        }
        return tmp.toString();
    }
    public static byte[] byte2BigEndian(short value) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putShort(value);
        byte[] bytes = new byte[1];
        System.arraycopy(buffer.array(), 0, bytes, 0, bytes.length);
        return bytes;
    }
    public static String bytesToBit(byte[] bytes){
        StringBuilder sb = new StringBuilder();

        for(int i=bytes.length-1;i>=0;i--){
            System.out.println(byteToBit(bytes[i]));
            sb.append(byteToBit(bytes[i]));
        }
        return sb.toString();
    }
    /**
     * 从byte[]里得到String
     *
     * @param array
     * @param index
     * @param length
     * @return
     */
    public static String readString(byte[] array, int index, int length) throws Exception {
        byte[] temp = ArrayUtils.subarray(array, index, index + length);
        String result = new String(temp, "GB18030").replaceFirst("\0.*", "");

        return result;
    }
    /*    @Test
    public void test123(){
        byte[] b = byte2BigEndian((short)255);
    }
    @Test
    public void test1() {
        byte[] b1 =new byte[]{-15,3};
        //b1 = new byte[] {-1,-1,-1,-5};
        System.out.println(ByteUtil.readShort(b1, 0));
        System.out.println(ByteUtil.getUnsignedByte(ByteUtil.readShort(b1, 0)));
    }
    @Test
    public void test11() {
        byte aa = ByteUtil.shortToByte((short)254);
        byte[] a1 = ByteUtil.shortToBytes((short)32767);
        byte[] b1 =new byte[]{-15,3};
        //b1 = new byte[] {-1,-1,-1,-5};
        System.out.println(ByteUtil.readShort(b1, 0));
        System.out.println(ByteUtil.getUnsignedByte(ByteUtil.readShort(a1, 0)));
    }
    @Test
    public void test2() {
       byte[] bb = intToBytes(4294967294l);
        byte[] b1 = new byte[] {-1,-1,-1,-5};
        //b1 = new byte[] {-1,-1,-1,-5};
        System.out.println(ByteUtil.readInt(b1, 0));
        System.out.println(ByteUtil.getUnsignedInt(ByteUtil.readInt(b1, 0)));
        byte[] cc = ByteUtil.shortToBytes(0xFFFF);
    }
   *//* @Test
    public void test3() {
        ByteUtil.bytesToHexString(bArray);
        byte[] a1 = ByteUtil.shortToBytes2(65525);
        int n1 = ByteUtil.getUnsignedByte(ByteUtil.readShort(a1,0));
        System.out.println(n1);
        //byte[] b2 = EndianUtils..short2LittleEndian(65535);
    }*//*
    @Test
    public void test4() {
        String test = ByteUtil.bytesToHexString(ByteUtil.intToBytes(32767));
        System.out.println(test.substring(4));
    }
    @Test
    public void test5() {
        String a1 = Integer.toBinaryString(3842);
        String a = ByteUtil.binaryString2hexString("0000111100000010");
        System.out.println(String.format("%014d", Integer.parseInt(Integer.toBinaryString(3842))));
    }
    @Test
    public void test6() {
        byte[] bb = new byte[]{50,100};
        String a = ByteUtil.bytesToBit(bb);
        System.out.println(a);
    }
    @Test
    public void test7() {
        
        System.out.println(byteToBit((byte)50));
    }
*/
    public static void main(String[] args) throws Exception {
        byte[] bt = new byte[1];
        bt[0] = (byte)1;
        String aaaa=HexUtils.bytes2Hex(bt);
        byte[] ss =shortToBytes(-128);
    	int abcd= 123456;
    	byte[] acc = ByteUtil.intToBytes(abcd);
        int ddd= ByteUtil.readInt(acc,0);
    	byte kk = 0x22;
    	int abcde =123456;
    	byte[] acc2 = ByteUtil.intToBytes(abcde);
    	short xxx=1;
    	List list = Arrays.asList(xxx);
    	boolean ggg = list.get(0) instanceof Short;
    	Calendar cccc = Calendar.getInstance();
    	cccc.setTime(new Date());
    	int m = cccc.get(Calendar.HOUR_OF_DAY);
    	byte a123 = ByteUtil.bitToByte("11111111");
    	//32769的二进制是10000000 00000001,
        byte[] a5 = ByteUtil.intToBytes(65535);//-128,1
    long   pppp= ByteUtil.getUnsignedInt(32767);
     /*   byte[] a6 = new byte[4];
        for(int i=0;i<4;i++){
            a6[i] = (byte)15;
        }
        int srss = new BigInteger(a6).intValue();*/
        int ttt = ByteUtil.readInt(a5,0);
        byte[] a4 = ByteUtil.intToBytes(-5);
        int a3 = 1073742824>>>8;
        //00000000000000000000001111101000
        byte a2 = (byte)(1073742824>>>8);
        //原码10000000 00000000 00000000 0011000
        //反码01111111 11111111 11111111 1100111
        //补码0
        //System.out.println(Integer.toBinaryString(-24));//11111111111111111111111111101000
        byte a = (byte)((8 >> 24) & 0xFF);
        //1000的二进制是00000000 00000000 00000011 11101000
        //
        byte[] a1 = ByteUtil.intToBytes(1000);//[0,0,3,-24]
        System.out.println(Integer.toBinaryString(7));
        System.out.println(Integer.parseInt("111"));
        String msgattr = "00"+String.format("%014d", Integer.parseInt(Integer.toBinaryString(7)));
        System.out.println(msgattr);
        // System.exit(0);
        //int i = 0x0F03;
        byte[] b1 =new byte[]{15,3};
        //15对应00001111,3对应00000011，连起来就是3843
        System.out.println(ByteUtil.readShort(b1, 0));
       /* String test = ByteUtil.bytesToHexString(ByteUtil.shortToBytes((short) 32767));
        test = ByteUtil.bytesToHexString(ByteUtil.shortToBytes((short)1));
        System.out.println(test);
        byte[] b = ByteUtil.hexString2Bytes("01");
        int i = ByteUtil.getUnsignedByte(ByteUtil.readShort(b, 0));
        System.out.println(111);

        byte[] body = new byte[0];
        body = ArrayUtils.addAll(body, ByteUtil.toByteArray("GgSiLEtTNDS2", 12));
        System.out.println(ByteUtil.bytesToHexString(body));

        System.out.println(ByteUtil.readString(body, 0, 12));*/
        ByteUtil.byteToBit((byte)100);
        String bit = "10000000";
        byte c = ByteUtil.bitToByte(bit);
       /* //byte  b = 256;
        ByteUtil.byteToBit(b);*/
        // System.out.println(c);
        short data =123;
        int b = data&0x0FFFF;
        System.out.println(b);
        System.out.println(ByteUtil.hexString2binaryString("04271544"));
/*
        byte[] head = new byte[0];
        head = ArrayUtils.addAll(head, ByteUtil.toByteArray(Constants.PLATFORM_SERIAL, 5));
        System.out.println(ByteUtil.bytesToHexString(head));
        System.out.println(ByteUtil.readString(head, 0, 5));

        String t = "8100";
        System.out.println(ByteUtil.hexString2Bytes(t));

        System.out.println(ByteUtil.hexString2binaryString("0104"));*/

    }
}
