import java.util.*;


/**
 * @author JM0011
 */
public class DFA {

    private List<Key> move = new ArrayList<>();
    private Integer so;
    private List<Integer> F = new ArrayList<>();

    public DFA(){
        this.so = 0;
        this.F.add(3);
        move.clear();
        //转换矩阵行
        char[] row = {'a','b'};
        //转换矩阵列
        Integer[] col = {0,1,2,3};
        //转换矩阵
        Integer [] [] table = {
                {1,0},
                {1,2},
                {1,3},
                {1,0}
        };
        for (int i = 0; i < table.length; i++) {
            for (int j = 0;j < table[i].length ;j++ ) {
                Key key = new Key(col[i],row[j],table[i][j]);
                move.add(key);
            }
        }

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
