import java.util.List;

/**
 * 标记注册
 * @author JM0011
 */
public class Dstate {
    private List<Integer> T;
    private Integer mark = null;

    public Dstate(List<Integer> t, Integer mark) {
        T = t;
        this.mark = mark;
    }

    public List<Integer> getT() {
        return T;
    }

    public void setT(List<Integer> t) {
        T = t;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public boolean Isequals(List<Integer> list) {
        if(list.size()==this.T.size()){
            if (list.containsAll(T)){
                return true;
            }
        }
        return false;
    }
}
