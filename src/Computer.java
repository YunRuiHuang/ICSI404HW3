/**
 * @author Yunrui Huang
 * 12/13/2021
 */
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
    private LongWord branch;

    /**
     * the constructor of Computer class
     *
     */
    public Computer(){
        this.haltedFlag = false;
        this.alu = new ALU();
        this.memory = new Memory();
        this.register = new LongWord[16];
        for (int i = 15; i > -1; i--) {
            this.register[i] = new LongWord();
        }
        this.PC = new LongWord();
        this.IR = new LongWord();
        this.OP1 = new LongWord();
        this.OP2 = new LongWord();
        this.OPCode = new LongWord();
        this.storeAddress = new LongWord();
        this.result = new LongWord();
        this.branch = new LongWord();
    }

    /**
     * the run method of Computer class
     * Set the PC start at 0
     * use the preload method to load data into memory(check ReadMe Doc.)
     * run fetch > decode > execute > store > ...
     * stop when haltedFlag is true
     */
    public void run(){
        this.PC.set(0);
        this.memory.preload();
        //System.out.println(memory.toString());
        while(!this.haltedFlag){
            //System.out.println("here");
            fetch();
            decode();
            execute();
            store();
        }
    }

    /**
     * the Fetch method use to load the IR from memory and add a step to PC
     */
    private void fetch(){
        this.IR = this.memory.read(this.PC,2);
        System.out.println("IR CODE > " + this.IR.toString());
        this.PC.set((this.PC.getSigned() + 2));
    }

    /**
     * The decode method use to decode the IR which read from memory
     * After decode everything will hold by OP1, OP2, OPCode, storeAddress, result, and branch
     */
    private void decode(){
        LongWord delete = new LongWord();
        delete.set(0);
        this.result.copy(delete);
        this.OPCode.copy(delete);
        this.storeAddress.copy(delete);
        //this.OP1.copy(delete);
        //this.OP2.copy(delete);

        if(this.IR.getBit(15)){
            delete.set(15);
            this.OPCode = this.IR.shiftRightLogical(12).and(delete);
            this.storeAddress = this.IR.shiftRightLogical(8).and(delete);
            this.OP1 = this.IR.shiftRightLogical(4).and(delete);
            this.OP2 = this.IR.and(delete);

        }else if(!this.IR.getBit(15)){//0***

            if((!this.IR.getBit(14)) && (!this.IR.getBit(13)) && (!this.IR.getBit(12))){//0000
                this.haltedFlag = true;

            }else if((!this.IR.getBit(14)) && (!this.IR.getBit(13)) && (this.IR.getBit(12))){//0001
                delete.set(15);
                this.OPCode = this.IR.shiftRightLogical(12).and(delete);
                this.result = this.IR.and(delete);

            }else if((!this.IR.getBit(14)) && (this.IR.getBit(13)) && (!this.IR.getBit(12))){//0010
                delete.set(15);
                this.OPCode = this.IR.shiftRightLogical(12).and(delete);
                this.storeAddress = this.IR.shiftRightLogical(8).and(delete);
                delete.set(255);
                this.OP1 = this.IR.and(delete);
                if(this.OP1.getBit(7)){
                    for (int i = 0; i < 24; i++) {
                        this.OP1.setBit(i+8);
                    }
                }

            }else if((!this.IR.getBit(14)) && (this.IR.getBit(13)) && (this.IR.getBit(12))){//0011
                delete.set(15);
                this.OPCode = this.IR.shiftRightLogical(12).and(delete);
                delete.set(4095);
                this.result = this.IR.shiftLeftLogical(1).and(delete);

            }else if((this.IR.getBit(14)) && (!this.IR.getBit(13)) && (!this.IR.getBit(12))){//0100
                delete.set(15);
                this.OPCode = this.IR.shiftRightLogical(12).and(delete);
                this.OP1 = this.IR.shiftRightLogical(4).and(delete);
                this.OP2 = this.IR.and(delete);

            }else if((this.IR.getBit(14)) && (!this.IR.getBit(13)) && (this.IR.getBit(12))){//0101
                delete.set(15);
                this.OPCode = this.IR.shiftRightLogical(12).and(delete);
                delete.set(3);
                this.result = this.IR.shiftRightLogical(10).and(delete);
                delete.set(1023);
                this.storeAddress = this.IR.shiftLeftLogical(1).and(delete);
            }
        }
    }

    /**
     * The execute method use to execute the operate which new hold by OPCode
     */
    private void execute(){
        if(this.OPCode.getBit(3)){
            this.OP1.copy(this.register[this.OP1.getSigned()]);
            this.OP2.copy(this.register[this.OP2.getSigned()]);
            this.OPCode.clearBit(3);
            this.result.copy(this.alu.operate(this.OPCode.getSigned(), this.OP1,this.OP2));
            this.OPCode.setBit(3);

        }else if(!this.OPCode.getBit(3)){



            if((!this.OPCode.getBit(2)) && (!this.OPCode.getBit(1)) && (!this.OPCode.getBit(0))) {//0000
                //nothing

            }else if((!this.OPCode.getBit(2)) && (!this.OPCode.getBit(1)) && (this.OPCode.getBit(0))){//0001

                //System.out.println(this.OPCode.toString());
                if(!this.result.getBit(0)){
                    System.out.println("print all register :");
                    for (int i = 0; i < 16; i++) {
                        System.out.println(i + " > " + this.register[i].toString());
                    }
                }else if(this.result.getBit(0)){
                    System.out.println(this.memory.toString());
                }



            }else if((!this.OPCode.getBit(2)) && (this.OPCode.getBit(1)) && (!this.OPCode.getBit(0))){//0010
                //this.register[this.storeAddress.getSigned()].copy(this.OP1);//move to store

            }else if((!this.OPCode.getBit(2)) && (this.OPCode.getBit(1)) && (this.OPCode.getBit(0))){//0011
                this.PC.copy(this.result);

            }else if((this.OPCode.getBit(2)) && (!this.OPCode.getBit(1)) && (!this.OPCode.getBit(0))) {//0100
                this.OP1.copy(this.register[this.OP1.getSigned()]);
                this.OP2.copy(this.register[this.OP2.getSigned()]);
                this.alu.operate(4,this.OP1,this.OP2);

            }else if((this.OPCode.getBit(2)) && (!this.OPCode.getBit(1)) && (this.OPCode.getBit(0))){//0101
                if((this.result.getSigned() == 0) && !((this.alu.getZF() == 1) && (this.alu.getNF() == 0))){
                    this.PC.copy(this.storeAddress);
                }else if((this.result.getSigned() == 1) && (this.alu.getZF() == 0) && (this.alu.getNF() == 1)){
                    this.PC.copy(this.storeAddress);
                }else if((this.result.getSigned() == 2) && (this.alu.getZF() == 1) && (this.alu.getNF() == 0)){
                    this.PC.copy(this.storeAddress);
                }else if((this.result.getSigned() == 3) && (((this.alu.getZF() == 1) && (this.alu.getNF() == 0)) || ((this.alu.getZF() == 0) && (this.alu.getNF() == 1)))){
                    this.PC.copy(this.storeAddress);
                }

            }
        }
    }

    /**
     * The store method use to store the data into register
     * Only Arithmetic and Logic instructions would store data
     */
    private void store(){
        if(this.OPCode.getBit(3)){
            this.register[this.storeAddress.getSigned()].copy(this.result);
        }else if(!this.OPCode.getBit(3)){

            if((!this.OPCode.getBit(2)) && (!this.OPCode.getBit(1)) && (!this.OPCode.getBit(0))) {//0000
                //nothing

            }else if((!this.OPCode.getBit(2)) && (!this.OPCode.getBit(1)) && (this.OPCode.getBit(0))){//0001
                //nothing store

            }else if((!this.OPCode.getBit(2)) && (this.OPCode.getBit(1)) && (!this.OPCode.getBit(0))){//0010
                this.register[this.storeAddress.getSigned()].copy(this.OP1);

            }else if((!this.OPCode.getBit(2)) && (this.OPCode.getBit(1)) && (this.OPCode.getBit(0))){//0011
                //nothing

            }else if((this.OPCode.getBit(2)) && (!this.OPCode.getBit(1)) && (!this.OPCode.getBit(0))) {//0100
                //nothing
                System.out.println("NF:" + this.alu.getNF() + "ZF:" + this.alu.getZF());

            }else if((this.OPCode.getBit(2)) && (!this.OPCode.getBit(1)) && (this.OPCode.getBit(0))){//0101
                //nothing

            }
        }
    }




}
