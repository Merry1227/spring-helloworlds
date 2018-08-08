package cmei.utils;

import com.xiaojukeji.ddti.common.beans.DidiLogger;
import com.xiaojukeji.ddti.dataservice.common.DataServiceConstants;

import java.text.NumberFormat;


/**
 * MathUtil
 *
 * @author meicanhua
 * @create 2017-05-19 上午10:52
 **/
public class MathUtil {

    private static final int DEFAULT_FRACTION = 1;

    public static float calculateRate(int numerator, int denominator) {
        if (denominator == 0) {
            logger.error("calculateRate", "calculateRate's denominator is ZERO, use 0.0 instead, numerator:"+numerator);
            //TODO, add dingding 通知
            return 0.0f;
        }
        float rate = (float) numerator / denominator;
        return rate;
    }

    public static String calculateCmp(String thisData, String lastData) {
        String cmpData = DataServiceConstants.NO_DATA;
        boolean containPercent = thisData.endsWith("%");
        try {
            if(containPercent) {
                thisData = thisData.substring(0, thisData.indexOf("%"));
                lastData = lastData.substring(0, lastData.indexOf("%"));
            }
            Float var1 = Float.parseFloat(thisData);
            Float var2 = Float.parseFloat(lastData);
            Float cmp = var1.floatValue() - var2.floatValue();
            cmpData = NumberFormat.getInstance().format(cmp);
            if (containPercent) {
                cmpData = cmpData.concat("%");
            }
        } catch (Exception e) {
            logger.warn("calculateCmp", e.getMessage());
        }

        return cmpData;
    }



    /**
     * 转化成%输出，保留2位小数
     * @param number
     * @return
     */
    public static String convertPercent(Number number) {
        return convertPercent(number, DEFAULT_FRACTION);
    }

    public static String convertPercentWithoutTail(Number number) {
        float res = number.floatValue()*100;
        return String.format("%.1f", res);
    }


    /**
     * 转化成%输出，保留指定位小数
     * @param number
     * @return
     */
    public static String convertPercent(Number number, int fractionNum) {
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMaximumFractionDigits(fractionNum);
        return numberFormat.format(number.floatValue());
    }

    public static String concatDescForCmp(Number number, boolean percent) {
        String ret;
        if (number.floatValue() == 0) {
            return "无变化";
        }
        if (!percent) {
            if (number.intValue() > 0) {
                ret = "增加了" + number.intValue();
            } else {
                ret = "减少了" + Math.abs(number.intValue());
            }
        } else {
            if (number.floatValue() > 0) {
                ret = "提高了" + MathUtil.convertPercent(number);
            } else {
                ret = "降低了" + MathUtil.convertPercent(Math.abs(number.floatValue()));
            }
        }
        return ret;
    }
    public static float calculateFloatRate(float numerator, float denominator) {
        if (denominator == 0) {
            logger.error("calculateRate", "calculateRate's denominator is ZERO, use 0.0 instead, numerator:"+numerator);
            //TODO, add dingding 通知
            return 0.0f;
        }
        float rate = (float) numerator / denominator;
        return rate;
    }


    public static String concatDescForCmp2(Number number, boolean percent) {
        String ret;
        if (number.floatValue() == 0) {
            return "---";
        }
        if (!percent) {
            if (number.intValue() > 0) {
                ret = "+" + number.intValue();
            } else {
                ret = "-" + Math.abs(number.intValue());
            }
        } else {
            if (number.floatValue() > 0) {
                ret = "+" + MathUtil.convertPercent(number);
            } else {
                ret = "-" + MathUtil.convertPercent(Math.abs(number.floatValue()));
            }
        }
        return ret;
    }

}