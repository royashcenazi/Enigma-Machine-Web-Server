package Machine;

import Parts.Reflector;
import Parts.Rotor;
import Utilities.RomanInterpeter;
import Utilities.LanguageInterpeter;
import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MachineProxy implements Serializable {
    private EnigmaMachine machine;

    public LanguageInterpeter getLanguageInterpeter() {
        return languageInterpeter;
    }

    private LanguageInterpeter languageInterpeter;
    private int appliedRotors;
    private List<String> machineSpecifications;
    private int inputMsgNum;
    private boolean machineIsSet;
    private MachineStatistics machineStatistics;
    private String machineCode;
    private Pair<Integer, Integer>[] currentRotorAndLocations;

    public Pair<Integer, Integer>[] getCurrentRotorAndLocations() {
        return currentRotorAndLocations;
    }


    public MachineProxy(EnigmaMachine machine, LanguageInterpeter languageInterpeter, int rotorsCount)
    {
        this.machine = machine;
        this.languageInterpeter = languageInterpeter;
        this.appliedRotors = rotorsCount;
        this.inputMsgNum = 0;
        this.machineIsSet = false;
    }

    public List<Character> encryptCode(String codeToEncrypt)
    {
        long start = System.currentTimeMillis();
        List<Character> encrypted =  languageInterpeter.numberToLetters(machine.encryptCode(languageInterpeter.lettersToNumbers(codeToEncrypt.toCharArray())));
        long end = System.currentTimeMillis();
        updateStatistics(codeToEncrypt, encrypted.toString(), end - start);
        inputMsgNum++;
        return encrypted;
    }

    public String encryptCodeToString(String codeToEncrypt){
        List<Character> encryptedArr = encryptCode(codeToEncrypt);
        StringBuilder sb = new StringBuilder();
        for (Character character : encryptedArr) {
            sb.append(character);
        }
        return sb.toString();
    }

    private void updateStatistics(String codeToEncrypt, String encrypted, long timeToProcess) {
        if(machineStatistics == null){
            machineStatistics = new MachineStatistics(getCurrentCode());
        }
        this.machineStatistics.addOrigStringDestStringAndTime(getCurrentCode(),codeToEncrypt, encrypted, timeToProcess);
    }

    public void setMachineToInitialState()
    {
        this.machine.setToInitialState();
    }

    public void setChosenRotors(Pair<Integer, Integer>... rotorsAndNotch)
    {
        currentRotorAndLocations = copyRotorsAndLocations(rotorsAndNotch);
        machine.setChosenRotors(rotorsAndNotch);
    }

    public void setChosenReflector(String reflectorNum)
    {
        machine.setChosenReflector(RomanInterpeter.romanToNum(reflectorNum));
    }

    public List<String> getMachineSpecifications(){
        this.machineSpecifications = new ArrayList<>();
        this.machineSpecifications.add("possible amount of wheels: " + getAppliedRotors());
        this.machineSpecifications.add("amount of wheels: " + machine.getRotorsSize());
        for(Rotor rotor : machine.getRotors()){
           this.machineSpecifications.add(rotor.toString());
        }
        this.machineSpecifications.add("amount of reflectors: " + machine.getReflectorsSize());
        this.machineSpecifications.add("number of messages that was processed so far: " + this.inputMsgNum);

        if(machineIsSet)
            this.machineSpecifications.add(getCurrentCode());

        return machineSpecifications;
    }

    public String getCurrentCode() {
        StringBuilder sb = new StringBuilder();
        List<Integer> rotorNotchesNum = new ArrayList<>();
        List<Integer> rotorIds = new ArrayList<>();
        List<Character> rotorNotchesChar = new ArrayList<>();
        for(Rotor rotor : machine.getChosenRotors()){
            rotorNotchesNum.add(rotor.getInitialPosition());
            rotorIds.add(rotor.getId());
        }
        rotorNotchesChar = languageInterpeter.numberToLetters(rotorNotchesNum);
        sb.append("<");
        for(int i = 0; i < rotorIds.size(); i++){
            if(i == rotorIds.size() -1)
                sb.append(rotorIds.get(i));
            else
                sb.append(rotorIds.get(i) + ",");
        }
        sb.append("><");
        for(int i = 0; i < rotorNotchesChar.size(); i++){
            if(i == rotorNotchesChar.size() -1)
                sb.append(rotorNotchesChar.get(i));
            else
                sb.append(rotorNotchesChar.get(i) + ",");
        }
        sb.append("><");
        sb.append(RomanInterpeter.numToRoman(machine.getChosenReflector()));
        sb.append(">");
        return sb.toString();

    }

    public void isMachineSet(boolean isSet){
        this.machineIsSet = isSet;
        if(machineIsSet) {
            if (machineStatistics == null)
            {
                machineStatistics = new MachineStatistics(getCurrentCode());
            }
            else
            {
                machineStatistics.setNewInitialCode(getCurrentCode());
            }
        }
    }

    public int getAppliedRotors(){
        return appliedRotors;
    }

    public char[] getLanguage(){
        return this.languageInterpeter.getLanguage();
    }

    public String getStatistics() {
        return machineStatistics.getStatistics();
    }

    public int getNumRotors() {
        return machine.getNumRotors();
    }

    public int getNumReflectors() {
        return machine.getNumReflectors();
    }

    private Pair<Integer,Integer>[] copyRotorsAndLocations(Pair<Integer, Integer>[] toCopy) {
        Pair<Integer, Integer>[] clone = new Pair[toCopy.length];
        int index = 0;
        for (Pair<Integer, Integer> copyPair : toCopy) {
            clone[index] = new Pair(copyPair.getKey(), copyPair.getValue());
            index++;
        }
        return clone;
    }

    public List<Reflector> getReflectors() {
        return machine.getReflectors();
    }

    @Override
    public MachineProxy clone() throws CloneNotSupportedException {
        MachineProxy clone = new MachineProxy(machine.clone(), languageInterpeter, appliedRotors);
        Pair<Integer, Integer>[] copyRotorLoc = copyRotorsAndLocations(currentRotorAndLocations);
        clone.currentRotorAndLocations = copyRotorLoc;
        return clone;
    }

    public List<Integer> getRotorIds() {
        return machine.getRotorIds();
    }
}
