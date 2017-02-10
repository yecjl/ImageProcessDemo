package danke.mylibrary.utils;

import java.io.UnsupportedEncodingException;

/**
 * 功能：字符串处理工具类
 * Created by danke on 2017/2/8.
 */

public class StringUtil {
    /**
     * 判断一个字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isNull(String str) {
        if (str == null && "".equals(str)) {
            return true;
        }

        return false;
    }

    /**
     * 讲一个byte数组转化为String类型数据
     * 编码集：gb2312
     *
     * @param data
     * @return
     */
    public static String convertString(byte[] data) {
        String result = null;
        try {
            result = new String(data, "gb2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            result = "";
        }

        return result;
    }
}
