/**
 * The program simulates pipeline. There are total 5 classes.
 * Classes present are PipelineSim, IFID, IDEX, EXMEM, and MEMWB 
 * which are used to copy the data.
 *
 * Author: Nirajan Pandey
 * Instructio: Dave Hendrickson
 * Teaching Assistant: Karen Susan Palmer
 * Due Date: April 23rd, 2020
 * 
 */

public class PipelineSim {

    public static void main(String[] args) {

        IFID ifidW = new IFID();
        IFID ifidR = new IFID();

        IDEX idexW = new IDEX();
        IDEX idexR = new IDEX();

        EXMEM exmemW = new EXMEM();
        EXMEM exmemR = new EXMEM();

        MEMWB memwbW = new MEMWB();
        MEMWB memwbR = new MEMWB();


        // MAIN MEMORY
        int[] Main_Mem = new int[0x400];
        // int j = 0x0;

        int j = 0;

        for (int i = 0; i < Main_Mem.length; i++) {
            if (i < Main_Mem.length) {
                Main_Mem[i] = j;
                j = j + 1;

                if (j == 256) {
                    j = 0;
                }
            }
        }


        // REGISTER
        int[] Regs = new int[0x20];
        Regs[0x0] = 0x0;
        for (int i = 0x1; i < Regs.length; i++) {
            Regs[i] = (i + 0x100);
        }

        int instruction[] = new int[]{0xA1020000, 0x810AFFFC, 0x00831820, 0x01263820,
                0x01224820, 0x81180000, 0x81510010, 0x00624022,
                0x00000000, 0x00000000, 0x00000000, 0x00000000};


        for (int i = 0; i < instruction.length; i++) {
            System.out.print("\n_______________________________________________________________________________\n");
            System.out.println("clock cycle " + (i + 1));
            IF_stage(instruction[i], ifidW);
            ID_stage(ifidR, idexW, Regs);
            EX_stage(idexR, exmemW, Regs);
            MEM_stage(exmemR, memwbW, Regs, Main_Mem);
            WB_stage(memwbR, Regs, Main_Mem);
            Print_out_everything(Regs, ifidW, ifidR, idexW, idexR, exmemW, exmemR, memwbW, memwbR);
            Copy_write_to_read(ifidW, ifidR, idexW, idexR, exmemW, exmemR, memwbW, memwbR);
        }

    }

    public static void IF_stage(int instruct, IFID ifidw) {
        ifidw.setInst(instruct);
    }


    public static void ID_stage(IFID ifidr, IDEX idexw, int Regs[]) {

        // variable for R-Format.
        int opcode = ((ifidr.getInstr() & 0xFC000000) >>> 26);
        int src1 = ((ifidr.getInstr() & 0x03E00000) >>> 21);
        int src2 = ((ifidr.getInstr() & 0x001F0000) >>> 16);
        int dest = ((ifidr.getInstr() & 0x0000F800) >>> 11);
        int func = (ifidr.getInstr() & 0x0000003F);

        idexw.setFunction(ifidr.getInstr() & 0x0000003F);


        //Setting up registers
        if (ifidr.getInstr() != 0) {
            if (opcode == 0x0 && (func == 0x20 || func == 0x22)) {
                idexw.setReadData1(Regs[src1]);
                idexw.setReadData2(Regs[src2]);
                idexw.setSEOffset(0);
                idexw.setWreg_15_11(dest);
                idexw.setWreg_20_16(src2);
            } else if ( opcode == 0x20 ) {
                idexw.setReadData1(Regs[src1]);
                idexw.setReadData2(0);
                idexw.setSEOffset((byte) ifidr.getInstr() & (byte) 0x000000FF);
                idexw.setWreg_15_11(dest);
                idexw.setWreg_20_16(src2);
            } else if ( opcode == 0x28) {
                idexw.setReadData1(Regs[src1]);
                idexw.setReadData2(Regs[src2]);
                idexw.setSEOffset((byte) ifidr.getInstr() & (byte) 0x000000FF);
                idexw.setWreg_15_11(dest);
                idexw.setWreg_20_16(src2);
            }
        }  //Setting Control Units
            if (opcode == 0x28) {                //sw
                idexw.setRegDst((short) 0);
                idexw.setALUSrc((short) 1);
                idexw.setALUOp((short) 0);
                idexw.setMemRead((short) 0);
                idexw.setMemWrite((short) 1);
                idexw.setBranch((short) 0);
                idexw.setMemToReg((short) 0);
                idexw.setRegWrite((short) 0);

            } else if (opcode == 0x20) {          //lw
                idexw.setRegDst((short) 0);
                idexw.setALUSrc((short) 1);
                idexw.setALUOp((short) 0);
                idexw.setMemRead((short) 1);
                idexw.setMemWrite((short) 0);
                idexw.setBranch((short) 0);
                idexw.setMemToReg((short) 1);
                idexw.setRegWrite((short) 1);

            } else if (opcode == 0x0 && func == 0x20) {         //R-format
                idexw.setRegDst((short) 1);
                idexw.setALUSrc((short) 0);
                idexw.setALUOp((short) 10);
                idexw.setMemRead((short) 0);
                idexw.setMemWrite((short) 0);
                idexw.setBranch((short) 0);
                idexw.setMemToReg((short) 0);
                idexw.setRegWrite((short) 1);
            } else if (opcode == 0x0 && func == 0x22) {
                idexw.setRegDst((short) 1);
                idexw.setALUSrc((short) 0);
                idexw.setALUOp((short) 10);
                idexw.setMemRead((short) 0);
                idexw.setMemWrite((short) 0);
                idexw.setBranch((short) 0);
                idexw.setMemToReg((short) 0);
                idexw.setRegWrite((short) 1);
            } else {
                idexw.setRegDst((short) 0);
                idexw.setALUSrc((short) 0);
                idexw.setALUOp((short) 0);
                idexw.setMemRead((short) 0);
                idexw.setMemWrite((short) 0);
                idexw.setBranch((short) 0);
                idexw.setMemToReg((short) 0);
                idexw.setRegWrite((short) 0);
            }



    }


    public static void EX_stage(IDEX idexr, EXMEM exmemw, int Regs[]) {


         if (idexr.getMemWrite() == 1 || idexr.getMemRead() == 1 || idexr.getRegWrite() == 1 || idexr.getMemToReg() == 1) {

            exmemw.setMemWrite(idexr.getMemWrite());
            exmemw.setRegWrite(idexr.getRegWrite());
            exmemw.setMemToReg(idexr.getMemToReg());
            exmemw.setMemRead(idexr.getMemRead());


            // Setting value for Multiplexer.
            if (idexr.getALUSrc() == 0) {
                idexr.getReadData2();

            } else if (idexr.getALUSrc() == 1) {
                idexr.getSEOffset();
            }


            // Setting up ALU to compute.
            if (idexr.getALUOp() == 10 && idexr.getFunction() == 0x20) {    // I have doubt in the line. This will most probably not work
                exmemw.setALUResult((short) (idexr.getReadData1() + idexr.getReadData2()));
                System.out.println(String.format("ALU Result from add %02X\t", exmemw.getALUResult()));
            }
            else if
            (idexr.getALUOp() == 10 && idexr.getFunction() == 0x22) {
                exmemw.setALUResult((short) (idexr.getReadData1() - idexr.getReadData2()));
                System.out.println(String.format("ALU Result from sub %02X\t", exmemw.getALUResult()));
            }

            else if
            (idexr.getALUOp() == 0 && idexr.getMemWrite() == 1) {
                exmemw.setALUResult((short) (idexr.getReadData1() + idexr.getSEOffset()));
                exmemw.setSwValue((short) idexr.getReadData2());
                System.out.println(String.format("ALU Result from SEO sw %02X\t", exmemw.getALUResult()));
            }
            else if (idexr.getALUOp() == 0)
                {
                exmemw.setALUResult((short) (idexr.getReadData1() + idexr.getSEOffset()));
                 System.out.println(String.format("ALU Result from SEO lw %02X\t", exmemw.getALUResult()));
            }



            // Setting up second multiplexer
            if (idexr.getRegDst() == 0) {
                exmemw.setWriteRegNum((short) idexr.getWreg_20_16());

                 System.out.println(String.format("Reg No _20_16##### %02d\t", idexr.getWreg_20_16()));
                // mux_2 = 0;
            } else if (idexr.getRegDst() == 1) {
                exmemw.setWriteRegNum((short) idexr.getWreg_15_11());
                 System.out.println(String.format("Reg NoE#####_15_11 %02d\t", idexr.getWreg_15_11()));
                // mux_2 = 1;
            }

        }


    }



    public static void MEM_stage(EXMEM exmemr, MEMWB memwbw, int Regs[], int Main_Mem[]) {
            memwbw.setMemToReg(exmemr.getMemToReg());
            memwbw.setRegWrite(exmemr.getRegWrite());
            memwbw.setMemWrite(exmemr.getMemWrite());
            memwbw.setALUResult(exmemr.getALUResult());

            // If MemWrite == 1, then the value in the memory needs to be updated.
            // This happens when the instruction is sb.
            if (exmemr.getMemWrite() == 1) {
                int address = exmemr.getALUResult();
                int write_data = exmemr.getSwValue();
                Main_Mem[address] = write_data;
                memwbw.setALUResult((short) 0);
                memwbw.setLWDataValue((short) 0);
                memwbw.setLWDataValue((short) 0);
            }
             // If MemRead == 1, then the value in Memory in particular
            // address needs to be set to LWDataValue, so it could be written in
            // register. This happens when instruction is lb.
            else {
                if (exmemr.getMemRead() == 1 && exmemr.getMemToReg() == 1) {
                    int aluVal = Main_Mem[exmemr.getALUResult()];
                    memwbw.setLWDataValue((short) aluVal);
                    memwbw.setWriteRegNum(exmemr.getWriteRegNum());

                } else {
                    if (exmemr.getRegWrite() == 1) {
                        memwbw.setALUResult(exmemr.getALUResult());
                        memwbw.setWriteRegNum(exmemr.getWriteRegNum());
                    }
                }
            }



    }

    public static void WB_stage(MEMWB memwbr, int Regs[], int Main_Mem[]) {

        int write = memwbr.getWriteRegNum();

        if (memwbr.getRegWrite() == 1 && memwbr.getMemToReg() == 1) {
            memwbr.getLWDataValue();
                Regs[write] = memwbr.getLWDataValue();
        } else {
            if (memwbr.getRegWrite() == 1) {
                 memwbr.getALUResult();
                Regs[write] = memwbr.getALUResult();
            }
        }
    }

    public static void Print_out_everything(int Regs[], IFID ifidw, IFID ifidr, IDEX idexw, IDEX idexr, EXMEM exmemw, EXMEM exmemr, MEMWB memwbw, MEMWB memwbr) {

        System.out.println();
        System.out.print("");
        System.out.println("IF/ID WRITE");
        System.out.println("___________");
        System.out.println(String.format("Inst = 0x%08X", ifidw.getInstr()));

        System.out.print("");
        System.out.println("IF/ID Read");
        System.out.println("___________");
        System.out.println(String.format("Inst = 0x%08X", ifidr.getInstr()));
        System.out.println();


        System.out.println("IDEX WRITE");
        System.out.println("___________");
        System.out.println("CONTROL:");
        System.out.print(String.format("RegDst: %02d \t", idexw.getRegDst()));
        System.out.print(String.format("ALUSrc: %02d \t\t", idexw.getALUSrc()));
        System.out.print(String.format("MemRead: %02d \t\t", idexw.getMemRead()));
        System.out.print(String.format("MemWrite: %02d \n", idexw.getMemWrite()));
        //  System.out.print(String.format("Branch: %02d \t", idexw.getBranch() ));
        System.out.print(String.format("MemToReg: %02d \t", idexw.getMemToReg()));
        System.out.print(String.format("RegWrite: %02d \t\t", idexw.getRegWrite()));
        System.out.println(String.format("ALUOp:%02d\t\t", idexw.getALUOp()));
        System.out.print("REGISTER VALUES");
        System.out.print(String.format("\nReadData1: %02x \t", idexw.getReadData1()));
        System.out.println(String.format("ReadData2: %02X", idexw.getReadData2()));
        /** OTHER VALUES */
        System.out.print(String.format("SEOffset:000000%2d\t", idexw.getSEOffset()));
        System.out.print(String.format("WriteReg_20_16:%2d \t\t", idexw.getWreg_20_16()));
        System.out.print(String.format("WriteReg_15_11:%2d\t", idexw.getWreg_15_11()));


        System.out.println();
        System.out.println("IDEX Read");
        System.out.println("___________");
        System.out.println("CONTROL:");
        System.out.print(String.format("RegDst=%02d \t", idexr.getRegDst()));
        System.out.print(String.format("ALUSrc=%02d \t\t", idexr.getALUSrc()));
        System.out.print(String.format("MemRead=%02d \t\t", idexr.getMemRead()));
        System.out.print(String.format("MemWrite=%02d \n", idexr.getMemWrite()));
        //  System.out.print(String.format("Branch=%02d \t", idexr.getBranch() ));
        System.out.print(String.format("MemToReg=%02d \t", idexr.getMemToReg()));
        System.out.print(String.format("RegWrite=%02d \t\t", idexr.getRegWrite()));
        System.out.println(String.format("ALUOp=%02d\t\t", idexr.getALUOp()));

        System.out.print("REGISTER VALUES");
        System.out.print(String.format("\nReadData1=%02X \t", idexr.getReadData1()));
        System.out.println(String.format("ReadData2=%02X", idexr.getReadData2()));
        /** OTHER VALUES */
        System.out.print(String.format("SEOffset=000000%2d\t", idexr.getSEOffset()));
        System.out.print(String.format("WriteReg_20_16=%2d \t\t", idexr.getWreg_20_16()));
        System.out.print(String.format("WriteReg_15_11=%2d \t", idexr.getWreg_15_11()));



        // EXMEM
        System.out.println();
        System.out.println("EXMEM Write");
        System.out.println("___________");
        System.out.println("CONTROL");
        System.out.print(String.format("MemRead=%02d\t\t", exmemw.getMemRead()));
        System.out.print(String.format("MemWrite=%02d\t", exmemw.getMemWrite()));
        //  System.out.print(String.format("Branch=%02d\t",  exmemw.getBranch()));
        System.out.print(String.format("MemToReg=%02d\t", exmemw.getMemToReg()));
        System.out.print(String.format("RegWrite=%02d\n", exmemw.getRegWrite()));

        System.out.print(String.format("ALUResult=%02X\t", exmemw.getALUResult()));
        /** OTHER VALUES */
        System.out.print(String.format("SWValue=%02X\t", exmemw.getSwValue()));
        System.out.println(String.format("WriteRegNum=%02d\t", exmemw.getWriteRegNum()));


        System.out.println();
        System.out.println("\nEXMEM Read");
        System.out.println("___________");
        System.out.println("CONTROL");
        System.out.print(String.format("MemRead=%02d\t\t", exmemr.getMemRead()));
        System.out.print(String.format("MemWrite=%02d\t", exmemr.getMemWrite()));
        //  System.out.print(String.format("Branch=%02d\t",  exmemr.getBranch()));
        System.out.print(String.format("MemToReg=%02d\t", exmemr.getMemToReg()));
        System.out.print(String.format("RegWrite=%02d\n", exmemr.getRegWrite()));

        System.out.print(String.format("ALUResult=%02X\t", exmemr.getALUResult()));
        /** OTHER VALUES */
        System.out.print(String.format("SWValue=%02X\t", exmemr.getSwValue()));
        System.out.println(String.format("WriteRegNum=%02d\t", exmemr.getWriteRegNum()));


        System.out.println("\nMEM/WB Write");
        System.out.println("___________");
        System.out.println("CONTROL");
        System.out.print(String.format("MemToReg=%02d\t\t\t", memwbw.getMemToReg()));
        System.out.println(String.format("RegWrite=%02d\t\t", memwbw.getRegWrite()));

        System.out.print(String.format("LWDataValue=%02X\t\t", memwbw.getLWDataValue()));
        System.out.print(String.format("ALUResult=%02X\t", memwbw.getALUResult()));
        System.out.println(String.format("WriteRegNum=%02d\t", memwbw.getWriteRegNum()));


        System.out.println("\nMEM/WB Read");
        System.out.println("___________");
        System.out.println("CONTROL");
        System.out.print(String.format("MemToReg=%02d\t\t\t", memwbr.getMemToReg()));
        System.out.println(String.format("RegWrite=%02d\t\t", memwbr.getRegWrite()));

        System.out.print(String.format("LWDataValue=%02X\t\t", memwbr.getLWDataValue()));
        System.out.print(String.format("ALUResult=%02X\t", memwbr.getALUResult()));
        System.out.println(String.format("WriteRegNim=%02d\t", memwbr.getWriteRegNum()));


        // REGISTER
        System.out.println();
        Regs[0] = 0;
        int j = 0;
        System.out.print("Reg[0]=");
        System.out.println(String.format("%02X\t", Regs[0]));
        for (int i = 1; i < Regs.length; i++) {
            //System.out.print(Regs[i] + " ");

                System.out.print("Reg[" + i + "]= ");
                System.out.println(String.format("%03X\t", Regs[i]));

        }
    }

    public static void Copy_write_to_read(IFID ifidw, IFID ifidr, IDEX idexw, IDEX idexr, EXMEM exmemw, EXMEM exmemr, MEMWB memwbw, MEMWB memwbr) {

        ifidr.setInst(ifidw.getInstr());


        idexr.setWreg_20_16(idexw.getWreg_20_16());
        idexr.setWreg_15_11(idexw.getWreg_15_11());
        idexr.setSEOffset(idexw.getSEOffset());
        idexr.setRegDst(idexw.getRegDst());
        idexr.setReadData1(idexw.getReadData1());
        idexr.setReadData2(idexw.getReadData2());
        idexr.setRegWrite(idexw.getRegWrite());
        idexr.setMemWrite(idexw.getMemWrite());
        idexr.setMemToReg(idexw.getMemToReg());
        idexr.setALUOp(idexw.getALUOp());
        idexr.setALUSrc(idexw.getALUSrc());
        idexr.setBranch(idexw.getBranch());
        idexr.setMemRead(idexw.getMemRead());
        idexr.setFunction(idexw.getFunction());


        exmemr.setWriteRegNum(exmemw.getWriteRegNum());
        exmemr.setSwValue(exmemw.getSwValue());
        exmemr.setALUResult(exmemw.getALUResult());

        exmemr.setMemRead(exmemw.getMemRead());
        exmemr.setMemToReg(exmemw.getMemToReg());
        exmemr.setMemWrite(exmemw.getMemWrite());

        exmemr.setRegWrite(exmemw.getRegWrite());


        memwbr.setLWDataValue(exmemw.getSwValue());
        memwbr.setWriteRegNum(exmemw.getWriteRegNum());
        memwbr.setALUResult(exmemw.getALUResult());
        memwbr.setMemToReg(exmemw.getMemToReg());
        memwbr.setRegWrite(exmemw.getRegWrite());


    }


}