public class Memory {

    private final int memorySize = 256;
    private byte[] memory;

    public Memory(){
        this.memory = new byte[memorySize];
        for (int i = 0; i < memorySize; i++) {
            this.memory[i] = 0;
        }
    }

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
