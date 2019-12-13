/**
 * Created by Mr.Zero on 2016/11/3.
 *
 * the code field for token:
 * 0 --> S (non-terminal symbols)
 * 1 --> C (non-terminal symbols)
 * 0 --> if
 * 1 --> else
 * 2 --> id
 * 3 --> +
 * 4 --> ;
 * 5 --> >
 * 6 --> &&
 * 7 --> $ (the end of input)
 *
 */
public class Token {
    private int code;

    private String value;

    public Token(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
