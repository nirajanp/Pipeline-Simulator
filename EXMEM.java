public class EXMEM {

    // Control Signals
    private short MemRead;
    private short MemWrite;
    private short MemToReg;
    private short RegWrite;


    private short ALUResult;
    private short SwValue ;
    private short WriteRegNum;


     public EXMEM() {

        this.MemRead = 0;
        this.MemWrite = 0;
        this.MemToReg = 0;
        this.RegWrite = 0;

       this.ALUResult = 0;
       this.SwValue = 0;
       this.WriteRegNum = 0;

    }

    //++++++++++CONTROL SIGNALS++++++++++++++++++++//
    public short getMemRead() {
        return MemRead;
    }
    public EXMEM setMemRead(short memRead) {
        MemRead = memRead;
        return this;
    }
    public short getMemWrite() {
        return MemWrite;
    }
    public EXMEM setMemWrite(short memWrite) {
        MemWrite = memWrite;
        return this;
    }

    public short getMemToReg() {
        return MemToReg;
    }
    public EXMEM setMemToReg(short memToReg) {
        MemToReg = memToReg;
        return this;
    }
    public short getRegWrite() {
        return RegWrite;
    }
    public EXMEM setRegWrite(short regWrite) {
        RegWrite = regWrite;
        return this;
    }

    //++++++++++Required For Calc+++++++++++++++++//
    public short getALUResult() {
        return ALUResult;
    }
    public EXMEM setALUResult(short ALUResult) {
        this.ALUResult = ALUResult;
        return this;
    }
    public short getSwValue() {
        return SwValue;
    }
    public EXMEM setSwValue(short swValue) {
        SwValue = swValue;
        return this;
    }
    public short getWriteRegNum() {
        return WriteRegNum;
    }
    public EXMEM setWriteRegNum(short writeRegNum) {
        WriteRegNum = writeRegNum;
        return this;
    }
}
