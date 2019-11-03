import java.util.ArrayList;
import java.util.List;

public class KeyList {
    private List<Integer> param1 = new ArrayList<>();
    private char param2;
    private List<Integer> result = new ArrayList<>();

    public KeyList(List<Integer> param1, char param2, List<Integer> result) {
        this.param1 = param1;
        this.param2 = param2;
        this.result = result;
    }

    public List<Integer> getParam1() {
        return param1;
    }

    public void setParam1(List<Integer> param1) {
        this.param1 = param1;
    }

    public char getParam2() {
        return param2;
    }

    public void setParam2(char param2) {
        this.param2 = param2;
    }

    public List<Integer> getResult() {
        return result;
    }

    public void setResult(List<Integer> result) {
        this.result = result;
    }
}
