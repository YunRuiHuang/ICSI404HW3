public class MemoryTester {

    public MemoryTester(){
        Memory memory = new Memory();
        LongWord address = new LongWord();
        LongWord word = new LongWord();
        LongWord output = new LongWord();

        address.set(1);
        word.set(100);
        memory.write(address,word,1);
        System.out.println("word is >" + word);
        output = memory.read(address,1);
        System.out.println("output is >" + output);

        word.set(1000);
        address.set(5);
        memory.write(address,word,4);
        System.out.println("word is >" + word);
        output = memory.read(address,4);
        System.out.println("output is >" + output);

        word.set(256);
        address.set(5);
        memory.write(address,word,5);
        System.out.println("word is >" + word);
        output = memory.read(address,5);
        System.out.println("output is >" + output);
    }

}
