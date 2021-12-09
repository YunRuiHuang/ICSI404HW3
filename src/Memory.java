public class Memory {

    private final int memorySize = 256;
    private byte[] memory;

    public Memory(){
        this.memory = new byte[memorySize];
        for (int i = 0; i < memorySize; i++) {
            this.memory[i] = 0;
        }
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

}
