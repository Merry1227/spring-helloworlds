package cmei.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.*;
import java.util.regex.Pattern;

/**
 * IPUtil: 提供IP地址处理相关工具
 *
 * A类地址：10.0.0.0～10.255.255.255
 * B类地址：172.16.0.0～172.31.255.255
 * C类地址：192.168.0.0～192.168.255.255
 *
 * @author meicanhua
 * @create 2018-05-07 下午6:01
 **/
public class IPUtil {

    private static final Logger logger = LoggerFactory.getLogger(IPUtil.class);

    private static final String LOCAL_IP = "0:0:0:0:0:0:0:1";

    // IP的正则
    private static Pattern pattern = Pattern.compile("(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\." + "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\."
                    + "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\." + "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})");

    public static final String DEFAULT_ALLOW_ALL_FLAG = "*";// 允许所有ip标志位
    public static final String DEFAULT_DENY_ALL_FLAG = "0"; // 禁止所有ip标志位

    /**
     * 获取用户真实ip地址
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if ( ip != null && ip.length() != 0 && ! "unknown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值， 第一个ip才是真实ip
            if( ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
            logger.info("{} from x-forwarded-for", ip);
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            logger.info("{} from Proxy-Client-IP", ip);
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            logger.info("{} from WL-Proxy-Client-IP", ip);
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            logger.info("{} from HTTP_CLIENT_IP", ip);
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            logger.info("{} from HTTP_X_FORWARDED_FOR", ip);
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
            logger.info("{} from X-Real-IP", ip);
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            logger.info("{} from remoteAddr", ip);
            if (LOCAL_IP.equals(ip)) {
                ip = getMyIpV4();
                logger.info("{} from myIpAddr", ip);
            }

        }
        return ip;
    }


    public static boolean isPermited(String ip, Set<String> allowIpList) {
        if (allowIpList.isEmpty() || allowIpList.contains(ip)) {
            return true;
        }

        for (String allow : allowIpList) {
            if (allow.indexOf("-") > -1) {// 处理 类似 192.168.0.0-192.168.2.1
                String[] tempAllow = allow.split("-");
                String[] from = tempAllow[0].split("\\.");
                String[] end = tempAllow[1].split("\\.");
                String[] tag = ip.split("\\.");
                boolean check = true;
                for (int i = 0; i < 4; i++) {// 对IP从左到右进行逐段匹配
                    int s = Integer.valueOf(from[i]);
                    int t = Integer.valueOf(tag[i]);
                    int e = Integer.valueOf(end[i]);
                    if (!(s <= t && t <= e)) {
                        check = false;
                        break;
                    }
                }
                if (check)
                    return true;
            } else if (allow.contains("/")) {// 处理 网段 xxx.xxx.xxx./24
                int splitIndex = allow.indexOf("/");
                // 取出子网段
                String ipSegment = allow.substring(0, splitIndex); // 192.168.3.0
                // 子网数
                String netmask = allow.substring(splitIndex + 1);// 24
                // ip 转二进制
                long ipLong = ipToLong(ip);
                //子网二进制
                long maskLong=(2L<<32 -1) -(2L << Integer.valueOf(32-Integer.valueOf(netmask))-1);
                // ip与和子网相与 得到 网络地址
                String calcSegment = longToIP(ipLong & maskLong);
                // 如果计算得出网络地址和库中网络地址相同 则合法
                if(ipSegment.equals(calcSegment))return true;
            }
        }
            return false;
    }

    /**
     * 根据设置的ip, 获取ip List
     * @param allowIps
     * @return
     */
    public static Set<String> getAllowIpList(String allowIps) {
        String[] splitRex = allowIps.split(";");// 拆分出白名单正则
        Set<String> ipList = new HashSet<>(splitRex.length);
        for (String allow : splitRex) {
            if (allow.contains("*")) {// 处理通配符 *
                String[] ips = allow.split("\\.");
                String[] from = new String[] { "0", "0", "0", "0" };
                String[] end = new String[] { "255", "255", "255", "255" };
                List<String> tem = new ArrayList<String>();
                for (int i = 0; i < ips.length; i++)
                    if (ips[i].indexOf("*") > -1) {
                        tem = completeRange(ips[i]);
                        from[i] = null;
                        end[i] = null;
                    } else {
                        from[i] = ips[i];
                        end[i] = ips[i];
                    }

                StringBuilder fromIP = new StringBuilder();
                StringBuilder endIP = new StringBuilder();
                for (int i = 0; i < 4; i++)
                    if (from[i] != null) {
                        fromIP.append(from[i]).append(".");
                        endIP.append(end[i]).append(".");
                    } else {
                        fromIP.append("[*].");
                        endIP.append("[*].");
                    }
                fromIP.deleteCharAt(fromIP.length() - 1);
                endIP.deleteCharAt(endIP.length() - 1);

                for (String s : tem) {
                    String ip = fromIP.toString().replace("[*]", s.split(";")[0]) + "-"
                            + endIP.toString().replace("[*]", s.split(";")[1]);
                    if (validatePattern(ip)) {
                        ipList.add(ip);
                    }
                }
            } else if (allow.contains("/")) {// 处理 网段 xxx.xxx.xxx./24
                ipList.add(allow);
            } else {// 处理单个 ip 或者 范围
                if (validatePattern(allow)) {
                    ipList.add(allow);
                }
            }

        }

        return ipList;
    }


    /**
     * 格式验证: 设置时候用上
     * @param ip
     * @return
     */
    public static boolean validatePattern(String ip) {
        String[] temp = ip.split("-");
        for(String s : temp) {
            if(!pattern.matcher(s).matches()) {
                return false;
            }
        }
        return true;
    }


    private static long ipToLong(String strIP) {
        long[] ip = new long[4];
        // 先找到IP地址字符串中.的位置
        int position1 = strIP.indexOf(".");
        int position2 = strIP.indexOf(".", position1 + 1);
        int position3 = strIP.indexOf(".", position2 + 1);
        // 将每个.之间的字符串转换成整型
        ip[0] = Long.parseLong(strIP.substring(0, position1));
        ip[1] = Long.parseLong(strIP.substring(position1 + 1, position2));
        ip[2] = Long.parseLong(strIP.substring(position2 + 1, position3));
        ip[3] = Long.parseLong(strIP.substring(position3 + 1));
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    private static String longToIP(long longIP) {
        StringBuilder sb = new StringBuilder("");
        // 直接右移24位
        sb.append(String.valueOf(longIP >>> 24));
        sb.append(".");
        // 将高8位置0，然后右移16位
        sb.append(String.valueOf((longIP & 0x00FFFFFF) >>> 16));
        sb.append(".");
        sb.append(String.valueOf((longIP & 0x0000FFFF) >>> 8));
        sb.append(".");
        sb.append(String.valueOf(longIP & 0x000000FF));
        return sb.toString();
    }

    /**
     * 限定范围解析
     *
     * @param arg
     * @return 返回限定后的IP范围，格式为List[10;19, 100;199]
     */
    private static List<String> completeRange(String arg) {
        List<String> com = new ArrayList<String>();
        int len = arg.length();
        if (len == 1) {
            com.add("0;255");
        } else if (len == 2) {
            String s1 = complete(arg, 1);
            if (s1 != null)
                com.add(s1);
            String s2 = complete(arg, 2);
            if (s2 != null)
                com.add(s2);
        } else {
            String s1 = complete(arg, 1);
            if (s1 != null)
                com.add(s1);
        }
        return com;
    }

    /**
     * 通配符解析
     * @param arg
     * @param length
     * @return
     */
    private static String complete(String arg, int length) {
        String from = "";
        String end = "";
        if (length == 1) {
            from = arg.replace("*", "0");
            end = arg.replace("*", "9");
        } else {
            from = arg.replace("*", "00");
            end = arg.replace("*", "99");
        }
        if (Integer.valueOf(from) > 255)
            return null;
        if (Integer.valueOf(end) > 255)
            end = "255";
        return from + ";" + end;
    }

    /**
     * 获取本机ip
     * @return
     */
    public static String getMyIpV4() {
        String localIp = null;
        String netIp = null;
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            boolean finded = false;// 是否找到外网IP
            while (netInterfaces.hasMoreElements() && !finded) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> address = ni.getInetAddresses();
                while (address.hasMoreElements()) {
                    ip = address.nextElement();

                    if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
                        netIp = ip.getHostAddress();
                        finded = true;
                        break;
                    } else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
                            && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
                        localIp = ip.getHostAddress();
                    }
                }
            }

            if (netIp != null && !"".equals(netIp)) {
                return netIp;
            }

        }catch (Exception e) {
            logger.error("getMyIpV4",e);
        }
        return localIp;
    }


}