package cmei.tests;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by canhuamei on 12/23/16.
 *
 * e.g.
//  Input:
// [
// 　　'GCAG---A-GC--',
// 　　'G-AGCCCAAG-AA'  # This is the referece

//

// # positions: 0 12345678
// ]
//
// Output:
// {
// "Insertions": [ (1:'C') ],
// "Deletions":  [ (3:'CCC'), (7:'A') ]
// }

 */
public class GeneSequenceChecker {
    private String seq1;//="GCAG---A-G";
    private String seq2; //="G-AGCCCAAG";

    //插入用+，删除用-
    private List<Integer> diffPositionStart=new ArrayList<Integer>();

    private List<Integer> diffPositionEnd=new ArrayList<Integer>();

    private List<Integer> _preCount=new ArrayList<Integer>();

    //记录原始序列中-的开始位置_start和长度count，对删除中 start 如果在-之前，则调整输出位置为 start-count
    // private Map<Integer,Integer> originalSlashIndex=new HashMap<Integer,Integer>();
    //计算前方-的总数

    public GeneSequenceChecker(String seq1,String seg2){
        this.seq1=seq1;
        this.seq2=seg2;
    }

    public void convertSlashIndex(){

    }

    public void check(){
        int seq2_len=seq2.length();
        //int limit=(seq1.length()<=seq2_len)?seq1.length():seq2_len;
        int i=0;
        int j=0;
        boolean cleanLast=true;
        int _count=0;

        while(i<seq2_len){
            char c2=seq2.charAt(i);
            char c1=seq1.charAt(j);
            if(c2==c1){
                i++;
                j++;
                if(!cleanLast){
                    diffPositionEnd.add(j-1);
                }
                cleanLast=true;
            }else if((c1!='-') && (c2=='-')){
                if(cleanLast) {
                    diffPositionStart.add(j);
                    _preCount.add(_count);
                    cleanLast=false;
                }
                i++;
                j++;
                _count++;
            }else if((c1=='-') && (c2!='-')){
                if(cleanLast) {
                    diffPositionStart.add(-j);
                    _preCount.add(_count);
                    cleanLast=false;
                }
                i++;
                j++;
            }
        }
    }

    public void print(){
        Output output=new Output();
        ArrayList<Pair> insertions=new ArrayList<Pair>();
        ArrayList<Pair> deletions=new ArrayList<Pair>();
        for(int i=0;i<diffPositionStart.size();i++){
            int start=diffPositionStart.get(i);
            int end=diffPositionEnd.get(i);
            Pair pair=new Pair();
            if(start<0){ //delete
                pair.index=Math.abs(start)-_preCount.get(i);
                pair.chars=seq2.substring(Math.abs(start),end);
                deletions.add(pair);
            }else{
                pair.index=start-_preCount.get(i);
                pair.chars=seq1.substring(start,end);
                insertions.add(pair);
            }
            //log
            System.out.println(pair.index+","+pair.chars);
        }
        output.Insertions=insertions;
        output.Deletions=deletions;
        //call json lib to convert output to json string. TODO
        System.out.print(output.toString());
    }

    public static void main(String[] args){
        GeneSequenceChecker ch=new GeneSequenceChecker("GCAG---A-G","G-AGCCCAAG");
        ch.check();
        ch.print();
    }
}

//不写 get set
class Output{
  public ArrayList<Pair> Insertions;

  public ArrayList<Pair> Deletions;
}

class Pair{
    public int index;
    public String chars;
}
