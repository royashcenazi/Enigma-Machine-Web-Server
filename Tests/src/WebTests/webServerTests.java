package WebTests;

import EnigmaCracking.DM;
import EnigmaCracking.Tasks.TaskLevels;
import LogicManager.Integrator;
import Machine.MachineProxy;
import XmlParsing.DictionaryXmlParser;
import XmlParsing.MachineXmlParser;
import enigmaAgent.EnigmaWebAgent;
import javafx.util.Pair;
import org.junit.BeforeClass;
import org.junit.Test;
import sun.awt.Mutex;

import javax.xml.bind.JAXBException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class webServerTests {
    private static MachineProxy mp;
    private Integrator integrator = new Integrator();

    @BeforeClass
    public static void init() {
        mp = null;
        try {
            mp = MachineXmlParser.parseXmlToMachineProxy("ex2-basic.xml");
            mp.setChosenRotors(new Pair<>(2, 3), new Pair<>(1, 3)); //left to right
            mp.setChosenReflector("I");
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }


//    @Test
//    public void testAgent()
//    {
//        try {
//            ServerSocket serverSocket = new ServerSocket(9090, 0, InetAddress.getLoopbackAddress());
//            //serverSocket.bind(socketAddress);
//            Socket socket = serverSocket.accept();
//            System.out.println("server connected");
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
//            objectOutputStream.writeObject(mp);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    @Test
    public void testDMWeb() {
        integrator.loadMachineFromXml("ex3-basic.xml");
        mp.setChosenRotors(new Pair<>(5, 1), new Pair<>(2, 1), new Pair(3, 1)); //left to right
        mp.setChosenReflector("I");
        DM dm = new DM(mp, DictionaryXmlParser.getDictionaryXmlParser().getDictionary("ex2-basic.xml"), "?KUUCYVAQS", 2, 20, TaskLevels.levelEasy, new Mutex(), 9090);
        dm.run();
    }

    @Test
    public void testDMmedium() {
        integrator.loadMachineFromXml("ex3-basic.xml");
        mp.setChosenRotors(new Pair<>(2, 1), new Pair<>(3, 1), new Pair(5, 1)); //left to right
        // mp.setChosenReflector("II");
        String test = mp.encryptCodeToString("encapsulation".toUpperCase());
        DM dm = new DM(mp, DictionaryXmlParser.getDictionaryXmlParser().getDictionary("ex3-basic.xml"), test, 2, 20, TaskLevels.levelMedium, new Mutex(), 9090);
        dm.run();
    }

}