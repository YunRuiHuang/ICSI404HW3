public class Computer {

    private Boolean haltedFlag;
    private ALU alu;
    private Memory memory;
    private LongWord[] register;
    private LongWord PC;
    private LongWord IR;
    private LongWord OP1;
    private LongWord OP2;

    private LongWord OPCode;
    private LongWord storeAddress;
    private LongWord result;

    public Computer(){
        this.haltedFlag = false;
        this.alu = new ALU();
        this.memory = new Memory();
        this.register = new LongWord[16];
        for (int i = 16; i > 0; i--) {
            this.register[i] = new LongWord();
        }
        this.PC = new LongWord();
        this.IR = new LongWord();
        this.OP1 = new LongWord();
        this.OP2 = new LongWord();
        this.result = new LongWord();

    }

    public void run(){
        while(!this.haltedFlag){
            fetch();
            decode();
            execute();
            store();
        }
    }

    private void fetch(){
        this.IR = this.memory.read(this.PC,2);
        this.PC.set((this.PC.getSigned() + 2));
    }

    private void decode(){
        LongWord delete = new LongWord();
        delete.set(7);
        this.OPCode = this.IR.shiftRightLogical(1).and(delete);
        delete.set(15);
        this.storeAddress = this.IR.shiftRightLogical(4).and(delete);
    }

    private void execute(){
        this.result = this.alu.operate(this.OPCode.getSigned(),this.OP1,this.OP2);
    }

    private void store(){
        this.memory.write(this.storeAddress,this.result,2);
    }



}
