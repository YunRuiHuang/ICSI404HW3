/**
 * @author Yunrui Huang
 * 12/13/2021
 */
public class MemoryTester {

    public MemoryTester(){
        Memory memory = new Memory();
        LongWord address = new LongWord();
        LongWord word = new LongWord();
        LongWord output = new LongWord();

        //testing input 128 and read that
        address.set(0);
        word.set(128);
        memory.write(address,word,1);
        System.out.println("word is >" + word);
        output = memory.read(address,1);
        System.out.println("output is >" + output);
        //System.out.println(memory.toString());

        //testing numByte as 5
        System.out.println("testing if the Byte num more than 4");
        word.set(1000);
        address.set(5);
        memory.write(address,word,5);
        System.out.println("word is >" + word);
        output = memory.read(address,5);
        System.out.println("output is >" + output);

        //testing a longer integer
        word.set(131071);
        address.set(5);
        memory.write(address,word,3);
        System.out.println("word is >" + word);
        output = memory.read(address,3);
        System.out.println("output is >" + output);


    }

}
