/**
 * @author JM0011
 */
public class Main {
    public static void main(String[] args) {
        NFA nfa = new NFA();
        NFA2DFA nfa2DFA = new NFA2DFA(nfa);
        DFA dfa = nfa2DFA.changetoDFA();
        String str = "abb";
        if(dfa.judge(str)){
            System.out.println(str + "： 接受");
        }else {
            System.out.println(str + ":  不接受");
        }
    }
}
