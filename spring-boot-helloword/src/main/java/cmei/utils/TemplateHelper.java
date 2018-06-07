package cmei.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TemplateHelper {

    public static final String ESCAPE_DOLLAR_SIGN = "$";

    public static final String ESCAPE_NUMBER_SIGN = "#";

//    @Autowired
//    VelocityEngine velocityEngine;
//
//
//    /**
//     * 模板替换:适合小文本
//     * @param templateName 模板名
//     * @param parameters 所需参数，传null不替换
//     * @return
//     */
//    public String buildTemplate(String templateName, Map<String, Object> parameters) {
//        Map<String, Object> parameterMap = parameters;
//        if (parameters == null) {
//            parameterMap = new HashMap();
//        }
//        String content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, "UTF-8", parameterMap);
//        return content;
//    }



    /**
     * 替换含有${}的参数
     * @param parameters
     * @param template
     * @return
     */
    public static String buildTemplate(String template, Map<String, Object> parameters) {

        return replaceMatchedPattern(template, parameters, ESCAPE_DOLLAR_SIGN);
    }

    /**
     * 替换含有#{}的参数
     * @param parameters
     * @param template
     * @return
     */
    public static String buildTemplateWithNumSign(String template, Map<String, Object> parameters) {

        return replaceMatchedPattern(template, parameters, ESCAPE_NUMBER_SIGN);
    }


    private static String replaceMatchedPattern(String template, Map<String, Object> parameters, String escapeString){

        if (template == null) return null;

        if(parameters == null || parameters.isEmpty()) return template;


        String patternString = "\\"+ escapeString + "\\{(" + StringUtils.join(parameters.keySet(), "|") + ")\\}";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(template);

        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, String.valueOf(parameters.get(matcher.group(1))));
        }
        matcher.appendTail(sb);

        return sb.toString();

    }


}
