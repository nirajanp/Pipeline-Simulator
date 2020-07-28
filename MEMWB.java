public class MEMWB {

    // variables for control Signals
    private short MemToReg;
    private short RegWrite;
    private short MemWrite;

    private short LWDataValue;
    private short ALUResult;
    private short WriteRegNum;


    public short getMemWrite() {
        return MemWrite;
    }

    public MEMWB setMemWrite(short memWrite) {
        MemWrite = memWrite;
        return this;
    }

    public MEMWB() {


        this.MemToReg = 0;
        this.RegWrite = 0;
        this.MemWrite = 0;

        this.LWDataValue = 0;
       this.ALUResult = 0;
       this.WriteRegNum = 0;

    }


    //+++++++++++ CONTROL SIGNALS ++++++++++++++++//
    public short getMemToReg() {
        return MemToReg;
    }

    public MEMWB setMemToReg(short memToReg) {
        MemToReg = memToReg;
        return this;
    }

    public short getRegWrite() {
        return RegWrite;
    }

    public MEMWB setRegWrite(short regWrite) {
        RegWrite = regWrite;
        return this;
    }

    public short getLWDataValue() {
        return LWDataValue;
    }

    public MEMWB setLWDataValue(short LWDataValue) {
        this.LWDataValue = LWDataValue;
        return this;
    }

    public short getALUResult() {
        return ALUResult;
    }

    public MEMWB setALUResult(short ALUResult) {
        this.ALUResult = ALUResult;
        return this;
    }

    public short getWriteRegNum() {
        return WriteRegNum;
    }

    public MEMWB setWriteRegNum(short writeRegNum) {
        WriteRegNum = writeRegNum;
        return this;
    }
}
