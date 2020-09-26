import com.learning.Main;
import com.learning.methods.MainCommand;
import org.junit.jupiter.api.*;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainTest {

    final PrintStream originalOut = System.out;
    final PrintStream originalErr = System.err;
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    final ByteArrayOutputStream err = new ByteArrayOutputStream();

    @BeforeAll
    public static void init(){
        Main.initRetrofit("http://localhost:8080");
    }

    public void setUpStreams() {
        out.reset();
        err.reset();
        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));
    }

    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    @Order(1)
    public void createProfile(){
        String[] args = "--createProfile test".split(" ");
        new CommandLine(new MainCommand()).execute(args);
        assertTrue((showProfile()).contains("Profile(profileId=null, name=test, houses=[])"));
    }

    @Test
    @Order(2)
    public void createHouse(){
        String[] args = "--createHouse house1".split(" ");
        new CommandLine(new MainCommand()).execute(args);
        assertTrue((showProfile()).contains("houseId=null, profileId=null, name=house1, devices=[], buttons=[], buttonToDevices=[]"));
    }

    @Test
    @Order(3)
    public void setHouse(){
        setUpStreams();
        String[] args = "--setHouse house1".split(" ");
        new CommandLine(new MainCommand()).execute(args);
        assertTrue(!out.toString().contains("Error"));
        restoreStreams();
    }

    @Test
    @Order(4)
    public void createButton(){
        String[] args = "--createButton button1,set,16".split(" ");
        new CommandLine(new MainCommand()).execute(args);
        assertTrue((showProfile()).contains("name=button1, buttonType=1, value=16, houseId=null"));
    }

    @Test
    @Order(5)
    public void createDevice(){
        String[] args = "--createDevice device1".split(" ");
        new CommandLine(new MainCommand()).execute(args);
        assertTrue((showProfile()).contains("name=device1, value=0, prevValue=0, houseId=null"));
    }

    @Test
    @Order(6)
    public void mapButtonToDevice(){
        String[] args = "--mapButtonToDevice button1,device1".split(" ");
        new CommandLine(new MainCommand()).execute(args);
        assertTrue((showProfile()).contains("buttonToDevices=[ButtonToDevice(buttonToDevicePK=ButtonToDevicePK(buttonId="));
    }

    @Test
    @Order(7)
    public void callButtonDevice(){
        assertTrue((showProfile()).contains("name=device1, value=0, prevValue=0, houseId=null"));
        String[] args = "--callButtonDevice on,device1".split(" ");
        new CommandLine(new MainCommand()).execute(args);
        assertTrue((showProfile()).contains("name=device1, value=1, prevValue=0, houseId=null"));
        args = "--callButtonDevice off,device1".split(" ");
        new CommandLine(new MainCommand()).execute(args);
        assertTrue((showProfile()).contains("name=device1, value=0, prevValue=1, houseId=null"));
        args = "--callButtonDevice undo,device1".split(" ");
        new CommandLine(new MainCommand()).execute(args);
        assertTrue((showProfile()).contains("name=device1, value=1, prevValue=0, houseId=null"));
    }

    @Test
    @Order(8)
    public void callButtonDeviceMapped(){
        String[] args = "--callButton button1".split(" ");
        new CommandLine(new MainCommand()).execute(args);
        assertTrue((showProfile()).contains("name=device1, value=16, prevValue=1, houseId=null"));
    }

    @Test
    @Order(9)
    public void saveProfile(){
        assumeTrue(checkLiveStatusDatabase());
        String[] args = "--saveProfile".split(" ");
        new CommandLine(new MainCommand()).execute(args);
        assertTrue((showProfile()).contains("Error"));
    }

    @Test
    @Order(10)
    public void setProfile(){
        assumeTrue(checkLiveStatusDatabase());
        String[] args = "--setProfile test".split(" ");
        new CommandLine(new MainCommand()).execute(args);
        assertFalse((showProfile()).contains("name=device1, value=16, prevValue=1, houseId=null"));
        assertTrue((showProfile()).contains("name=device1, value=16, prevValue=1, houseId="));
    }

    @Test
    @Order(11)
    public void undoState(){
        assumeTrue(checkLiveStatusDatabase());
        String[] args = "--undoState".split(" ");
        new CommandLine(new MainCommand()).execute(args);
        assertTrue((showProfile()).contains("Error"));
    }

    @Test
    @Order(12)
    public void deleteProfile(){
        assumeTrue(checkLiveStatusDatabase());
        String[] args = "--deleteProfile test".split(" ");
        new CommandLine(new MainCommand()).execute(args);
        args = "--setProfile test".split(" ");
        new CommandLine(new MainCommand()).execute(args);
        System.out.println(showProfile());
        assertTrue((showProfile()).contains("Error"));
    }

    private String showProfile(){
        setUpStreams();
        String[] args = "--showCurrentProfile".split(" ");
        new CommandLine(new MainCommand()).execute(args);
        String res = out.toString();
        restoreStreams();
        return res;
    }

    private boolean checkLiveStatusDatabase(){
        return true;
    }

    private static String uuidMatcher = "[a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8}";
}
