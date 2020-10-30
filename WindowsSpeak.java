import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WindowsSpeak {
    public static void main(String[] args) {
        WindowsSpeak speak = new WindowsSpeak();
        speak.speak("我操你妈！什么卵东西啊");
    }
    public void speak(String words){
        Runtime run =Runtime.getRuntime();
        try {
            Process p = run.exec("cmd /c  echo CreateObject(\"SAPI.SpVoice\").Speak \" "+ words+"\" > script.vbs && wscript script.vbs");
            InputStream ins= p.getInputStream();
            InputStream ers= p.getErrorStream();
            new Thread(new inputStreamThread(ins)).start();
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    class inputStreamThread implements Runnable{
        private InputStream ins = null;
        private BufferedReader bfr = null;
        public inputStreamThread(InputStream ins){
            this.ins = ins;
            this.bfr = new BufferedReader(new InputStreamReader(ins));
        }
        @Override
        public void run() {
            String line = null;
            byte[] b = new byte[100];
            int num = 0;
            try {
                while((num=ins.read(b))!=-1){
                    System.out.println(new String(b,"gb2312"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
