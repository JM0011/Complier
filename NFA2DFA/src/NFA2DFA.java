import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
/**
 * @author JM0011
 */
public class NFA2DFA {
    private NFA nfa;
    private char[] chars;
    private List<KeyList> Smove = new ArrayList<>();
    private List<Integer> s0;
    private List<Integer> F = new ArrayList<>();
    private List<Dstate> dstates = new ArrayList<>();

    public NFA2DFA(NFA nfa) {
        this.nfa = nfa;
        this.chars = nfa.getChars();
        this.F = nfa.getF();
    }


    //求空_闭包
    public List<Integer> getClosure(List<Integer> in) {
        List<Integer> results = new ArrayList<>();
        for (Integer i : in) {
            results.add(i);
        }
        Stack stack = new Stack();
        for (Integer i : in) {
            stack.push(i);
        }
        while (!stack.empty()){
            Integer i = (Integer) stack.pop();
            List<Integer> lists = nfa.moveResult(i,'*');
            if (lists!=null){
                for (Integer list : lists) {
                    if (!results.contains(list)){
                        results.add(list);
                    }
                    if (!in.contains(list)){
                        stack.push(list);
                    }
                }
            }
        }
        if (results.size()!=0){
            return results;
        }
        return null;
    }

    //确定化
    public void makeConfirm() {
        List<Integer> s0s = new ArrayList();
        s0s.add(nfa.getS0());
        this.s0 = getClosure(s0s);
        Dstate dstate = new Dstate(s0,0);
        dstates.clear();
        dstates.add(dstate);
        while (getUnMark()!=null){
            dstate = getUnMark();
            dstate.setMark(1);
            for (char aChar : chars) {
                if (nfa.smoveResult(dstate.getT(),aChar)!=null){
                    List<Integer> list = getClosure(nfa.smoveResult(dstate.getT(),aChar));
                    if (isMark(list)==-1){
                        Dstate dstate1 = new Dstate(list,0);
                        dstates.add(dstate1);
                    }
                    KeyList keyList = new KeyList(dstate.getT(),aChar,list);
                    Smove.add(keyList);
                }
            }
        }

    }

    //转换
    public DFA changetoDFA() {
        makeConfirm();
        Integer s = getMarkNum(this.s0);
        List<Integer> f = new ArrayList<>();
        List<Key> smove = new ArrayList<>();
        int i = 0;
        for (Dstate dstate : dstates) {
            if (isEnd(dstate.getT())){
                f.add(i);
            }
            dstate.setMark(i);
            i++;
        }
        for (KeyList keyList : Smove) {
            Integer param1 = getMarkNum(keyList.getParam1());
            char parma2 = keyList.getParam2();
            Integer param3 = getMarkNum(keyList.getResult());
            Key key = new Key(param1,parma2,param3);
            smove.add(key);
        }
        DFA dfa = new DFA(smove,s,f);
        return dfa;
    }

    //获取状态集中的标志
    public Integer getMarkNum(List<Integer> list){
        for (Dstate dstate : dstates) {
            if(equalList(dstate.getT(),list)){
                return dstate.getMark();
            }
        }
        System.out.println("error --- 未在Dstates中找到状态集");
        return null;
    }

    //获取第一个未标记的状态集(1为标记，0为未标记),返回null 说明全部都标记了
    public Dstate getUnMark(){
        for (Dstate dstate : dstates) {
            if (dstate.getMark()==0){
                return dstate;
            }
        }
        return null;
    }


    //判断状态集是否已标记(1为标记，0为未标记,-1为未注册)
    public Integer isMark(List<Integer> list){
        for (Dstate dstate : dstates) {
            if(equalList(dstate.getT(),list)){
                return dstate.getMark();
            }
        }
        return -1;
    }

    //判断是否是终态集
    public Boolean isEnd(List<Integer> list){
        for (Integer i : F) {
            if(!list.contains(i)){
                return false;
            }
        }
        return true;
    }

    //判断两个List是否相等
    public Boolean equalList(List<Integer> list1, List<Integer> list2) {
        if (list1.size() == list2.size()) {
            if (list1.containsAll(list2)) {
                return true;
            }
        }
        return false;
    }
}
