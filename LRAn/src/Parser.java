import java.io.IOException;
import java.util.Stack;

/**
 * Created by Mr.Zero on 2016/11/3.
 */
public class Parser {

    private static final String [] generations = {
            "S' --> S",                 //0
            "S --> if C S else S",      //1
            "S --> id + id ;",          //2
            "C --> id > id",            //3
            "C --> C && C"              //4
    };

    /**
     * the length of each generation in String [] generations.
     * this array is used to determine how many tokens and states should be popped from stack when reduce a generation.
     */
    private static final int [] generationLength = {
            1,
            5,
            4,
            3,
            3
    };

    /**
     * the position of each non-terminal symbol in goto table
     */
    private static final String [] nonTerminalCode = {
            "S",
            "C"
    };

    private static final String [][] actionMap = {
          //  if   else   id    +     ;     >    &&     $
            {"S3", ""  , "S2", ""  , ""  , ""  , ""  , ""  },
            {""  , ""  , ""  , ""  , ""  , ""  , ""  , "R0"},
            {""  , ""  , ""  , "S4", ""  , ""  , ""  , ""  },
            {""  , ""  , "S8", ""  , ""  , ""  , ""  , ""  },
            {""  , ""  , "S5", ""  , ""  , ""  , ""  , ""  },
            {""  , ""  , ""  , ""  , "S6", ""  , ""  , ""  },
            {""  , ""  , ""  , ""  , ""  , ""  , ""  , "R2"},
            {"S13",""  ,"S14", ""  , ""  , ""  ,"S12", ""  },
            {""  , ""  , ""  , ""  , ""  , "S9", ""  , ""  },
            {""  , ""  ,"S10", ""  , ""  , ""  , ""  , ""  },
            {"R3", ""  , "R3", ""  , ""  , ""  , ""  , ""  },
            {""  ,"S18", ""  , ""  , ""  , ""  , ""  , ""  },
            {""  , ""  , "S8", ""  , ""  , ""  , ""  , ""  },
            {""  , ""  , "S8", ""  , ""  , ""  , ""  , ""  },
            {""  , ""  , ""  ,"S15", ""  , ""  , ""  , ""  },
            {""  , ""  ,"S16", ""  , ""  , ""  , ""  , ""  },
            {""  , ""  , ""  , ""  ,"S17", ""  , ""  , ""  },
            {""  , "R2", ""  , ""  , ""  , ""  , ""  , ""  },
            {"S3", ""  , "S2", ""  , ""  , ""  , ""  , ""  },
            {""  , ""  , ""  , ""  , ""  , ""  , ""  , "R1"},
            {"R4", ""  , "R4", ""  , ""  , ""  ,"S12", ""  },
            {"S13",""  ,"S14", ""  , ""  , ""  ,"S12", ""  },
            {""  ,"S23", ""  , ""  , ""  , ""  , ""  , ""  },
            {"S13",""  ,"S14", ""  , ""  , ""  , ""  , ""  },
            {""  , "R1", ""  , ""  , ""  , ""  , ""  , ""  }
    };

    private static final int[][] gotoMap = {
          //  S   C
            {  1, -1},
            { -1, -1},
            { -1, -1},
            { -1, 7 },
            { -1, -1},
            { -1, -1},
            { -1, -1},
            { 11, -1},
            { -1, -1},
            { -1, -1},
            { -1, -1},
            { -1, -1},
            { -1, 20},
            { -1, 21},
            { -1, -1},
            { -1, -1},
            { -1, -1},
            { -1, -1},
            { 19, -1},
            { -1, -1},
            { -1, -1},
            { 22, -1},
            { -1, -1},
            { 24, -1},
            { -1, -1},
    };

    private Stack<Token> tokenStack;

    private Stack<Integer> stateStack;

    private IOHelper ioHelper;

    public Parser() {
        this.tokenStack = new Stack<>();
        tokenStack.push(new Token(7, "$"));
        this.stateStack = new Stack<>();
        stateStack.push(0);
        this.ioHelper = new IOHelper();
    }

    public void parse() throws IOException {
        Token tokenCur = ioHelper.readToken();
        while(tokenCur!=null){
            String optStr = actionMap[stateStack.peek()][tokenCur.getCode()];
            if (optStr == null || optStr.isEmpty()) {
                ioHelper.write("Error! Can not parse input token: "+tokenCur.getValue());
                break;
            }
            if (optStr.charAt(0) == 'S') {      //shift
                int nextState = Integer.parseInt(optStr.substring(1));
                tokenStack.push(tokenCur);
                stateStack.push(nextState);
            }
            else if (optStr.charAt(0) == 'R') { //reduce
                int genIndex = Integer.parseInt(optStr.substring(1));
                if (genIndex == 0) {    //all tokens are parsed successfully!
                    break;
                }
                else {
                    ioHelper.write(generations[genIndex]);
                    for (int i=0; i<generationLength[genIndex]; i++) {
                        tokenStack.pop();
                        stateStack.pop();
                    }

                    String nonTerminal = generations[genIndex].substring(0,1);
                    int nonCode = -1;
                    for (int i=0; i<nonTerminalCode.length; i++){
                        if (nonTerminalCode[i].equals(nonTerminal)){
                            nonCode = i;
                            break;
                        }
                    }
                    if (nonCode<0){
                        ioHelper.write("Error! Non-terminal symbol: "+nonTerminal+" is not in nonTerminalCode list!");
                        break;
                    }
                    tokenStack.push(new Token(nonCode, nonTerminal));
                    int nextState = gotoMap[stateStack.peek()][nonCode];
                    stateStack.push(nextState);

                    continue;       //prevent ioHelper read next token, because current token has not been processed
                }
            }
            else {
                ioHelper.write("Error! Wrong parsing table caused by Invalid operation string: "+optStr);
                break;
            }
            tokenCur = ioHelper.readToken();
        }
    }

    public void closeIO(){
        ioHelper.closeIO();
    }

    public static void main(String [] args) {
        Parser parser = new Parser();
        try {
            parser.parse();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            parser.closeIO();
        }
    }
}
