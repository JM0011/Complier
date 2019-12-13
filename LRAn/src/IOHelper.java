import java.io.*;

/**
 * Created by Mr.Zero on 2016/11/3.
 */
public class IOHelper {
    private BufferedReader reader = null;
    private BufferedWriter writer = null;

    public IOHelper() {
        try {
            reader = new BufferedReader(new FileReader(new File("input.txt")));
            writer = new BufferedWriter(new FileWriter(new File("output.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Token readToken() throws IOException {
        String tokenStr = reader.readLine();
        if (tokenStr.trim() == null){
            return null;
        }
        String [] strArr = tokenStr.split(" ");
        return new Token(Integer.parseInt(strArr[0]), strArr[1]);
    }

    public void write(String generation) {
        String str = generation + "\n";
        try {
            writer.write(str, 0, str.length());
            System.out.print(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeIO(){
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (writer!=null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
