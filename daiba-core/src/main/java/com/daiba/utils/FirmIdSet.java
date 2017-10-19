package com.daiba.utils;

import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * 生成唯一流水号firmID
 * Created by tangzuopeng on 2016/10/31.
 */
public class FirmIdSet {

    public static String[] chars = new String[] {  "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9" };

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

    //对外接口，得到firmID
    public static String getFirmId(String type){
        StringBuffer firmId = new StringBuffer();
        firmId.append(type)
                .append(format.format(System.currentTimeMillis()))
                .append(generateShortUuid(8));
//        System.out.println(firmId.toString());
        return firmId.toString();
    }

    public static String generateShortUuid(int length) {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < length; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 10]);
        }
        return shortBuffer.toString();
    }

}
