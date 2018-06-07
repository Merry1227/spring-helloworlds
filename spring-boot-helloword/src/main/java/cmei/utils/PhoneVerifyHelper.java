package cmei.utils;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PhoneVerifyHelper {

    private static final Logger logger = LoggerFactory.getLogger(PhoneVerifyHelper.class);

    /**
     * 座机电话格式验证
     **/
    private static final String PHONE_CALL_PATTERN = "^(\\(\\d{3,4}\\)|\\d{3,4}-)?\\d{7,8}(-\\d{1,4})?$";
    /**
     * 中国电信号码格式验证 手机段： 133,153,180,181,189,177,170,173
     **//*
    private static final String CHINA_TELECOM_PATTERN = "(^1(33|53|7[037]|8[019])\\d{8}$)";
    *//**
     * 中国联通号码格式验证 手机段：130,131,132,155,156,185,186,145,176,170,171,175
     **//*
    private static final String CHINA_UNICOM_PATTERN = "(^1(3[0-2]|4[5]|5[56]|7[0156]|8[56])\\d{8}$)";
    *//**
     * 中国移动号码格式验证
     * 手机段：134,135,136,137,138,139,150,151,152,157,158,159,182,183,184
     * ,187,188,147,178,1705
     **//*
    private static final String CHINA_MOBILE_PATTERN = "(^1(3[4-9]|4[7]|5[0-27-9]|7[028]|8[2-478])\\d{8}$)";*/

    /**
     * 仅手机号格式校验
     **/
    private static final String PHONE_PATTERN = "(^(13[0-9]|15[0-35-9]|17[0-8]|18[0-9]|14[5-9]|166|19[89])[0-9]{8}$)";

    private static final String CHINA_CODE = "86";

    private static Map<String, String> patternMatchMap = new HashMap<>();

    static {
        patternMatchMap.put(CHINA_CODE, PHONE_PATTERN);
    }

    /**
     * 校验多个手机号码，返回正确匹配的号码列表
     * @param phones
     * @return
     */
    public static List<String> verifyPhone(String countryCode, List<String> phones) {
        List<String> list = phones.stream().filter(phone -> isPhone(countryCode, phone)).collect(Collectors.toList());
        return list;
    }

    /**
     * 校验单个手机号码，返回true／false
     * true : 号码匹配正常
     * false : 号码不匹配
     * @param phone
     * @return
     **/
    public static boolean isPhone(String countryCode, String phone) {
        //暂时只支持 "86"国家码，如果非86，直接返回true
        if (!StringUtils.equalsIgnoreCase(CHINA_CODE, countryCode.trim())) {
            logger.info("PhoneVerifyHelper isPhone", "countryCode:{} is not 86 for phone:{}", countryCode, phone);
            return true;
        }
        String pattren = patternMatchMap.get(countryCode.trim());
        if (StringUtils.isBlank(pattren)) {
            return false;
        }
        return match(PHONE_PATTERN, phone.trim());
    }

    /**
     * 验证电话号码的格式
     *
     * @param str 校验电话字符串
     * @return 返回true, 否则为false
     */
    public static boolean isPhoneCallNum(String str) {
        return match(PHONE_CALL_PATTERN, str.trim());
    }

    /*
      匹配函数
      @param regex
      @param input
      @return
     */
    private static boolean match(String regex, String input) {
        return Pattern.matches(regex, input);
    }

}
