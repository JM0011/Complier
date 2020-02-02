/**
 * @author JM0011
 */
public class Main {

    public static void main(String[] args) {
        String str = "abb";
        DFA dfa = new DFA();
        if(dfa.judge(str)){
            System.out.println(str + "： 接受");
        }else {
            System.out.println(str + ":  不接受");
        }
    }
}
