import org.apache.commons.lang3.StringUtils;

import java.util.*;


public class GrammarHandler{

    char NULL_WORD_CHAR = '@';
    String NULL_WORD_STR = "@";
    //文法map 存储文法 key为非终结符
    //用list储存该非终结符的所有规则
    private Map<Character, Set<String>> grammarMap = new HashMap<Character, Set<String>>();
    //选择集 map
    private Map<Character, Map<Character,String>> selectMap = new HashMap<Character, Map<Character,String>>();
    //标志位  是否初始化
    //未初始化不得开始验证语句
    private boolean isInit = false;
    //启始非终结符
    private Character firstK = null;


    public void addGrammar(char k, String s) {
        if (!grammarMap.containsKey(k)) {
            grammarMap.put(k, new HashSet<String>());
        }
        grammarMap.get(k).add(s);
    }

    public boolean isMatch(String s) {
        Stack<Character> gStack = new Stack<Character>();
        Stack<Character> sStack = new Stack<Character>();
        //添加结尾标记
        gStack.push('#');
        sStack.push('#');
        //init gStack & sStack
        gStack.push(firstK);
        for (int i=s.length()-1;i>=0;i--) {
            sStack.push(s.charAt(i));
        }
        String s1 = null;
        //分析核心
        while (!(gStack.isEmpty()||sStack.isEmpty())) {
            while (!(gStack.isEmpty() || sStack.isEmpty())
                    && gStack.peek() == sStack.peek()) {
                gStack.pop();
                sStack.pop();
            }
            //通过表查询 获得对应文法规则
            if (gStack.isEmpty() || sStack.isEmpty()) {
                break;
            }
            s1 = null;
            if (selectMap.get(gStack.peek()) != null) {
                s1 = selectMap.get(gStack.pop()).get(sStack.peek());
            }
            if (s1 == null) {
                return false;
            }
            if (this.NULL_WORD_STR.equals(s1)) {
                continue;
            }
            for (int i=s1.length()-1;i>=0;i--) {
                gStack.push(s1.charAt(i));
            }

        }
        //分析成功
        if (sStack.isEmpty()&&gStack.isEmpty()){
            return true;
        }
        return false;
    }

    public void init(char firstK) {
        if (this.isInit) {
            return;
        }
        this.firstK = firstK;
        //计算SELECT集
        getSelectMap();
        this.isInit = true;
    }

    private void getSelectMap() {
        //遍历非终结符
        for (Character k : grammarMap.keySet()) {
            select(k);
        }
    }
    //求select
    private void select(char k) {
        if (selectMap.containsKey(k)) {
            return;
        }
        selectMap.put(k, new HashMap<Character,String>());
        //遍历该终结符的所有规则
        for (String s : grammarMap.get(k)) {
            //空
            if (StringUtils.isEmpty(s)) {
                follow(k);
            }else{
                //select 集为 s的first
                selectMap.get(k).put(s.charAt(0),s);
            }
        }
    }
    //求follow
    private void follow(char k) {
        char[] charArr = null;
        for (Character k1 : grammarMap.keySet()) {
            for (String s : grammarMap.get(k1)) {
                charArr = s.toCharArray();
                for (int i=0;i<charArr.length;i++) {
                    if (charArr[i] == k) {
                        if (i == charArr.length - 1) {
                            selectMap.get(k).put('#',this.NULL_WORD_STR);
                            //后继为空
                        }else{
                            select(charArr[i+1]);
                            for (Character c : selectMap.get(charArr[i + 1]).keySet()) {
                                selectMap.get(k).put(c,this.NULL_WORD_STR);
                            }
                        }

                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        //构造一个LL1文法分析器
        GrammarHandler handler = new GrammarHandler();
        //添加文法规则
        handler.addGrammar('S',"dABA");//S->dABA
        handler.addGrammar('A',"");//A->NULL
        handler.addGrammar('A',"a");//A->a
        handler.addGrammar('B',"bb");//B->bb
        //初始化分析器 并设置启始非终结符
        handler.init('S');
        //待验证数组
        String[] sArr = new String[]{"dbb","dab","dabba"};
        //得出结果
        for (String s : sArr) {
            boolean result = handler.isMatch(s);
            System.out.println(s+":"+result);
        }
    }
}
