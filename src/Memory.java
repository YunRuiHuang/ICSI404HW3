/**
 * @author Yunrui Huang
 * 12/13/2021
 */
public class Memory {

    private final int memorySize = 256;
    private byte[] memory;

    /**
     * the constructor of Memory class
     */
    public Memory(){
        this.memory = new byte[memorySize];
        for (int i = 0; i < memorySize; i++) {
            this.memory[i] = 0;
        }
    }

    /**
     * the Preload method use for testing the Computer class
     * About this method I write the ReadMe
     * Please check the ReadMe
     */
    public void preload(){
        this.memory[8]=16;
        LongWord longWord = new LongWord();
        LongWord address = new LongWord();
        address.set(0);
        longWord.set(8703);
        write(address,longWord,2);
        address.set(2);
        longWord.set(8202);
        write(address,longWord,2);
        address.set(4);
        longWord.set(8710);
        write(address,longWord,2);
        address.set(6);
        longWord.set(45840);
        write(address,longWord,2);
        address.set(10);
        longWord.set(45843);
        write(address,longWord,2);
        address.set(12);
        longWord.set(4096);
        write(address,longWord,2);
        address.set(14);
        longWord.set(16434);
        write(address,longWord,2);
        address.set(16);
        longWord.set(20485);
        write(address,longWord,2);
        address.set(18);
        longWord.set(4096);
        write(address,longWord,2);
        address.set(20);
        longWord.set(12303);
        write(address,longWord,2);
        address.set(30);
        longWord.set(50225);
        write(address,longWord,2);
        address.set(32);
        longWord.set(4096);
        write(address,longWord,2);
    }

    /**
     * The read method use to read the data from the memory
     * @param address
     * A LongWord use to point the address, which unsign number should less than 255
     * @param numBytes
     * A Integer to define how many byte need to read, should less or equal to 4
     * @return
     * A LonwWord to hold the data from memory, the first Byte would put at the most left place
     */
    public LongWord read(LongWord address, int numBytes){
        int local = address.getSigned();
        LongWord longWord = new LongWord();
        if((local < 0) || (local > 255) || (numBytes < 0) || (numBytes > 4)){
            System.out.println("Memory read error");
            return new LongWord();
        }


        for (int i = 0; i < numBytes; i++) {
            LongWord holder = new LongWord();
            holder.set(this.memory[local+i]);
            LongWord notNegitive = new LongWord();
            notNegitive.set(255);
            holder = holder.and(notNegitive);
            //System.out.println(holder);
            longWord = longWord.shiftLeftLogical(8);
            longWord = longWord.or(holder);

        }


        return longWord;
    }

    /**
     * The write method use to write the data into memory
     * @param address
     * A LongWord use to point the address, which unsign number should less than 255
     * @param word
     * A LongWord which hold the data read to write into memory
     * @param numBytes
     * A Integer to define how many byte need to read, should less or equal to 4
     */
    public void write(LongWord address, LongWord word, int numBytes){
        int local = address.getSigned();
        LongWord longWord = new LongWord();
        longWord.set(255);

        if((local < 0) || (local > 255) || (numBytes < 0) || (numBytes > 4)){
            System.out.println("Memory write error");
            return;
        }


        for (int i = numBytes-1; i > -1; i--) {
            this.memory[local+i] = (byte)word.shiftRightLogical((numBytes-1-i)*8).and(longWord).getUnsigned();
            //System.out.print(this.memory[local+i]);
            //System.out.println("  "+ (local + i));
        }


    }

    /**
     * OverWrite the toString method
     * @return
     * return all data in the memory as string
     */
    public String toString(){
        String string = "";

        for (int i = 0; i < 256; i++) {
            string = string + this.memory[i] + " ";
            if((i+1)%4 == 0){
                string = string +"\n";
            }
        }

        return string;
    }

}
