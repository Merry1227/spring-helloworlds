package cmei.tests;
/*
-找出定和数组
写一个函数做如下处理（数组和数是入参）:

给定一个数组 e.g. [1, 3, 2, 4] 和 一个指定数 e.g 5 ， 输出所有和为定数的数组， 注意原数组的每个元素只能只用一次。

=>  输出 {(1, 4), (2, 3)}

[1, 1, 3, 1, 2, 3]， 4 => { (1, 3), (1, 3) }
*/

import java.util.*;

public class FixedSumPair {
    private LinkedList<Integer> originalArray;//Linked Array will be better, because the visited will be deleted if sum is meet.

    private int sum;

    private List<SumPair> pairs = new ArrayList<SumPair>();

    public FixedSumPair(LinkedList<Integer> originalArray, int sum) {
        this.originalArray = originalArray;
        this.sum = sum;
    }

    //需要自己写吗？这里改变了 originalArray，可以用 arraycopy 一份。如果不希望改变的话。
    private void sortedArray() {
        Collections.sort(originalArray);
    }

    private int searchNumIndexByBinarySearch(int num2, List<Integer> newSortedArray,int startIndex,int endIndex) {

        if(startIndex>=endIndex){
            return -1;
        }

        int start = newSortedArray.get(startIndex);

        int end = newSortedArray.get(endIndex- 1);

        int middleIndex=(endIndex+startIndex)/2;

        int middle=newSortedArray.get(middleIndex);

        if (num2 < start || num2 > end) {
            return -1;
        }else if(num2==middle){
            return middleIndex;
        }else if (num2<middle){
            return searchNumIndexByBinarySearch(num2,newSortedArray,startIndex,middleIndex);
        }else if (num2>middle){
            return searchNumIndexByBinarySearch(num2,newSortedArray,middleIndex+1,endIndex);
        }
        return -1;
    }


    public void caculatePairs() {
        sortedArray();

        while(!originalArray.isEmpty()){
            int num1=originalArray.get(0);
            int expectedNum2=this.sum-num1;
            int num2Index=this.searchNumIndexByBinarySearch(expectedNum2,originalArray,1,originalArray.size());
            if (num2Index!=-1){
                int num2=originalArray.remove(num2Index);
                SumPair p= new SumPair();
                p.num1=num1;
                p.num2=num2;
                pairs.add(p);
                originalArray.remove(0);
            }else{
                originalArray.remove(0);
            }
           // System.out.println(" size:"+originalArray.size()+" num2:"+num2Index);
        }
    }

    public String printPairs(){
        StringBuffer sb=new StringBuffer("{");
        for (SumPair sumPair:pairs){
            sb.append(sumPair+",");
        }
        sb.replace(sb.length()-1,sb.length(),"}");
        return sb.toString();
    }

    public static void main(String[] args){
        //List<Integer> array= Arrays.asList(1,3,2,4);
        LinkedList<Integer> array=new LinkedList<Integer>();
        array.add(1);
        array.add(3);
        array.add(2);
        array.add(4);
        int sum=5;
        FixedSumPair fixedSumPair=new FixedSumPair(array,sum);
        fixedSumPair.caculatePairs();
        System.out.println("case1:"+fixedSumPair.printPairs());

        //case2:
        LinkedList<Integer> array2=new LinkedList<Integer>();
        array.add(1);
        array.add(1);
        array.add(3);
        array.add(1);
        array.add(2);
        array.add(3);
        FixedSumPair fixedSumPair2=new FixedSumPair(array,4);
        fixedSumPair2.caculatePairs();
        System.out.println("case1:"+fixedSumPair2.printPairs());
    }
}

class SumPair{
    public int num1;//for saving time, define to public,no geter/setters.
    public int num2;

    public String toString(){
        return "("+num1+","+num2+")";
    }
}


