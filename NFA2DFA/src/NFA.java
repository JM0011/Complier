import java.util.ArrayList;
import java.util.List;
/**
 * @author JM0011
 */
public class NFA {
    private char[] chars;
    private Integer s0 ;
    private List<Integer> F = new ArrayList<>() ;
    private List<Key> move = new ArrayList<>();

    public char[] getChars() {
        return chars;
    }

    public Integer getS0() {
        return s0;
    }

    public List<Integer> getF() {
        return F;
    }

    public NFA(){
        s0 = 0;
        F.add(10);
        chars = new char[]{'a','b'};
        move.clear();
        //转换矩阵
        Key key = new Key(0,'*',1);
        move.add(key);
        key = new Key(0,'*',7);
        move.add(key);
        key = new Key(1,'*',2);
        move.add(key);
        key = new Key(1,'*',4);
        move.add(key);
        key = new Key(2,'a',3);
        move.add(key);
        key = new Key(3,'*',6);
        move.add(key);
        key = new Key(4,'b',5);
        move.add(key);
        key = new Key(5,'*',6);
        move.add(key);
        key = new Key(6,'*',1);
        move.add(key);
        key = new Key(6,'*',7);
        move.add(key);
        key = new Key(7,'a',8);
        move.add(key);
        key = new Key(8,'b',9);
        move.add(key);
        key = new Key(9,'b',10);
        move.add(key);

    }

    //转移函数计算转移结果
    public List<Integer> moveResult(Integer i ,char ch) {
        List<Integer> result = new ArrayList();
        for (Key key : move) {
            if (key.getParam1() == i && key.getParam2() == ch){
                result.add(key.getResult());
            }
        }
        if (result.size()!=0){
            return result;
        }
        return null;
    }

    //Smove函数
    public List<Integer> smoveResult(List<Integer> list,char ch){
        List<Integer> result = new ArrayList<>();
        for (Integer i : list) {
            List<Integer> sub = moveResult(i,ch);
            if(sub!=null){
                for (Integer a : sub) {
                    if (!result.contains(a)){
                        result.add(a);
                    }
                }
            }
        }
        if (result.size()!=0){
            return result;
        }
        return null;
    }

    //是否在终态集中
    public Boolean isInF(Integer i){
        for (Integer integer : F) {
            if (i == integer){
                return true;
            }
        }
        return false;
    }

    //打印转移函数
    public void print(){
        for (Key key : move) {
            System.out.println(key.getParam1()+ " , " + key.getParam2() + " = " + key.getResult());
        }
    }

    //转换成DFA


}
