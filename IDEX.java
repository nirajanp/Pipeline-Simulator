public class IDEX {


    // Control Signals
    private short RegDst;
    private short ALUSrc;
    private short ALUOp;
    private short MemRead;
    private short MemWrite;
    private short Branch;
    private short MemToReg;
    private short RegWrite;

    //Required for calculation
    private int ReadData1;
    private int ReadData2;
    private int SEOffset;
    private int Wreg_20_16;
    private int Wreg_15_11;
    private int function;

    public IDEX() {
        this.RegDst = 0;
        this.RegDst = 0;
        this.ALUSrc = 0;
        this.ALUOp = 0;
        this.MemRead = 0;
        this.MemWrite = 0;
        this.Branch = 0;
        this.MemToReg = 0;
        this.RegWrite = 0;

        //Required for calculation
        this.ReadData1 = 0;
        this.ReadData2 = 0;
        this.SEOffset = 0;
        this.Wreg_20_16 = 0;
        this.Wreg_15_11 = 0;
        this.function = 0;
    }


    public int getFunction() {
        return function;
    }

    public IDEX setFunction(int function) {
        this.function = function;
        return this;
    }

    public int getReadData1() {
        return ReadData1;
    }

    public IDEX setReadData1(int readData1) {
        ReadData1 = readData1;
        return this;
    }

    public int getReadData2() {
        return ReadData2;
    }

    public IDEX setReadData2(int readData2) {
        ReadData2 = readData2;
        return this;
    }

    public int getSEOffset() {
        return SEOffset;
    }

    public IDEX setSEOffset(int SEOffset) {
        this.SEOffset = SEOffset;
        return this;
    }

    public int getWreg_20_16() {
        return Wreg_20_16;
    }

    public IDEX setWreg_20_16(int wreg_20_16) {
        Wreg_20_16 = wreg_20_16;
        return this;
    }

    public int getWreg_15_11() {
        return Wreg_15_11;
    }

    public IDEX setWreg_15_11(int wreg_15_11) {
        Wreg_15_11 = wreg_15_11;
        return this;
    }

    //+++++++CONTROL SIGNAL+++++++++++++++++++++++//
    public short getRegDst() {
        return RegDst;
    }

    public IDEX setRegDst(short regDst) {
        this.RegDst = regDst;
        return this;
    }

    public short getALUSrc() {
        return ALUSrc;
    }

    public IDEX setALUSrc(short ALUSrc) {
        this.ALUSrc = ALUSrc;
        return this;
    }

    public short getALUOp() {
        return ALUOp;
    }

    public IDEX setALUOp(short ALUOp) {
        this.ALUOp = ALUOp;
        return this;
    }

    public short getMemRead() {
        return MemRead;
    }

    public IDEX setMemRead(short memRead) {
        MemRead = memRead;
        return this;
    }

    public short getMemWrite() {
        return MemWrite;
    }

    public IDEX setMemWrite(short memWrite) {
        MemWrite = memWrite;
        return this;
    }

    public short getBranch() {
        return Branch;
    }

    public IDEX setBranch(short branch) {
        Branch = branch;
        return this;
    }

    public short getMemToReg() {
        return MemToReg;
    }

    public IDEX setMemToReg(short memToReg) {
        MemToReg = memToReg;
        return this;
    }

    public short getRegWrite() {
        return RegWrite;
    }

    public IDEX setRegWrite(short regWrite) {
        RegWrite = regWrite;
        return this;
    }
}
