import java.util.ArrayList;
import java.util.List;
/**
 * @author JM0011
 */
public class DFA {
    private List<Key> move = new ArrayList<>();
    private Integer so;
    private List<Integer> F = new ArrayList<>();

    public DFA(List<Key> move, Integer so, List<Integer> f) {
        this.move = move;
        this.so = so;
        F = f;
    }

    //转移函数计算转移结果
    public Integer moveResult(Integer i ,char ch) {
        for (Key key : move) {
            if (key.getParam1() == i && key.getParam2() == ch){
                return key.getResult();
            }
        }
        return -1;
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

    //DFA计算函数
    public Boolean judge(String str){
        char [] chars = str.toCharArray();
        Integer s = so;
        for (char ch : chars) {
            s = moveResult(s,ch);
            if (s == -1){
                return false;
            }
        }
        if (isInF(s)){
            return true;
        }
        return false;
    }


}
