package cmei.tests;



/**
 * Created by canhuamei on 12/23/16.
 *
 * 现在要为用户分发长度为7位数字的账号。其中如果账号包含连续3个4，则账号不可用 （如0044444， 4441238）。请求出可用账号数量为多少。

 *由于需求不固定，现在账号长度变为8位数字，那么可用账号数量又为多少？

 */
public class AccountNumber {


    /***
     * 限定在0-9数字上
     * Solution 1: 10(7)-10*(7-3)-9*10(7-3-1)-10*9*10(7-3-2)-10*10*9*10*(7-3-3)-。。。
     * Solution 2: 存在连续3个4的个数
     * f(3)=1
     * f(4)=f(3)*9+10*(4-3)
     * f(5)=f(4)*9+10*(5-1-2)
     * @param len
     * @param forbitChar
     * @param seqNum
     * @return
     */
    public static int getAvailableAcount(int len){
         double total=Math.pow(10,len);
         double sameSequence=getSubCount(len);
         return (new Double(total-sameSequence)).intValue();
    }

    public static double getSubCount(int len){
        if (len<3){
            return 0;
        }
        if (len==3){
            return 1;
        }
        else{
            return getSubCount(len-1)*9+Math.pow(10,len-3);
        }
    }

    public static void main(String[] args){
        int res=getAvailableAcount(4);
        System.out.println(res);
        System.out.println(getAvailableAcount(8));
    }

}
