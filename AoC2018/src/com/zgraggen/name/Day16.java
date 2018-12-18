package com.zgraggen.name;

import java.util.ArrayList;

class Register{
	int r0;
	int r1;
	int r2;
	int r3;

	public Register(int r0, int r1, int r2, int r3) {
		this.r0 = r0;
		this.r1 = r1;
		this.r2 = r2;
		this.r3 = r3;
	}
	
	public Register(Register reg) {
		this.r0 = reg.r0;
		this.r1 = reg.r1;
		this.r2 = reg.r2;
		this.r3 = reg.r3;
	}

	public Register(String[] input) {
		this(
				Integer.parseInt(input[0]),
				Integer.parseInt(input[1]),
				Integer.parseInt(input[2]),
				Integer.parseInt(input[3])
				);
		
	}

	@Override
	public String toString() {
		return "["+r0+ ","+ r1 + ","+ r2 + ","+ r3 + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Register) {
			Register reg = (Register) obj;
			return reg.r0==r0 && reg.r1 == r1 && reg.r2 == r2 && reg.r3 == r3;
		}
		return false;
	}

	public int get(int pos) {
		if(pos==0) {
			return r0;
		}
		if(pos==1) {
			return r1;
		}
		if(pos==2) {
			return r2;
		}
		if(pos==3) {
			return r3;
		}
		System.out.println("ERROR");
		return -1;
	}

	public Register set(int pos, int value) {
		if(pos==0) {
			this.r0 = value;
		} else if(pos==1) {
			this.r1 = value;
		} else if(pos==2) {
			this.r2 = value;
		} else if(pos==3) {
			this.r3 = value;
		} else {
			System.out.println("ERROR");
		}
		return this;
	}
}

interface OpsLambda{
	public Register execute(int a, int b, int c, Register reg);
}

class Operations{
	ArrayList<OpsLambda> operations;
	
	public Operations(){
		operations = new ArrayList<>();
		
		//Addition:
		//addr (add register) stores into register C the result of adding register A and register B.
		operations.add((a,b,c,reg) -> new Register(reg).set(c,reg.get(a)+reg.get(b)));		
		//addi (add immediate) stores into register C the result of adding register A and value B.
		operations.add((a,b,c,reg) -> new Register(reg).set(c,reg.get(a)+b));
		
		//Multiplication:
		//mulr (multiply register) stores into register C the result of multiplying register A and register B.
		operations.add((a,b,c,reg) -> new Register(reg).set(c,reg.get(a)*reg.get(b)));		
		//muli (multiply immediate) stores into register C the result of multiplying register A and value B.
		operations.add((a,b,c,reg) -> new Register(reg).set(c,reg.get(a)*b));

		//Bitwise AND:
		//banr (bitwise AND register) stores into register C the result of the bitwise AND of register A and register B.
		operations.add((a,b,c,reg) -> new Register(reg).set(c,reg.get(a)&reg.get(b)));		
		//bani (bitwise AND immediate) stores into register C the result of the bitwise AND of register A and value B.
		operations.add((a,b,c,reg) -> new Register(reg).set(c,reg.get(a)&b));
		
		//Bitwise OR:
		//borr (bitwise OR register) stores into register C the result of the bitwise OR of register A and register B.
		operations.add((a,b,c,reg) -> new Register(reg).set(c,reg.get(a)|reg.get(b)));		
		//bori (bitwise OR immediate) stores into register C the result of the bitwise OR of register A and value B.
		operations.add((a,b,c,reg) -> new Register(reg).set(c,reg.get(a)|b));

		//Assignment:
		//setr (set register) copies the contents of register A into register C. (Input B is ignored.)
		operations.add((a,b,c,reg) -> new Register(reg).set(c,reg.get(a)));		
		//seti (set immediate) stores value A into register C. (Input B is ignored.)
		operations.add((a,b,c,reg) -> new Register(reg).set(c,a));		
		
		
		//Greater-than testing:
		//gtir (greater-than immediate/register) sets register C to 1 if value A is greater than register B. Otherwise, register C is set to 0.
		operations.add(
				(a,b,c,reg) -> {
					Register newReg  = new Register(reg);
					if(a>reg.get(b)) {
						reg.set(c, 1);
					} else {
						reg.set(c, 0);
					}
					return newReg;
				}
				);		
		//gtri (greater-than register/immediate) sets register C to 1 if register A is greater than value B. Otherwise, register C is set to 0.
		operations.add(
				(a,b,c,reg) -> {
					Register newReg  = new Register(reg);
					if(b<reg.get(a)) {
						reg.set(c, 1);
					} else {
						reg.set(c, 0);
					}
					return newReg;
				}
				);		
		//gtrr (greater-than register/register) sets register C to 1 if register A is greater than register B. Otherwise, register C is set to 0.
		operations.add(
				(a,b,c,reg) -> {
					Register newReg  = new Register(reg);
					if(reg.get(a)>reg.get(b)) {
						reg.set(c, 1);
					} else {
						reg.set(c, 0);
					}
					return newReg;
				}
				);		

		
		//Equality testing:
		//eqir (equal immediate/register) sets register C to 1 if value A is equal to register B. Otherwise, register C is set to 0.
		operations.add(
				(a,b,c,reg) -> {
						Register newReg  = new Register(reg);
						if(a==reg.get(b)) {
							reg.set(c, 1);
						} else {
							reg.set(c, 0);
						}
						return newReg;
					}
				);		
		//eqri (equal register/immediate) sets register C to 1 if register A is equal to value B. Otherwise, register C is set to 0.
		operations.add(
				(a,b,c,reg) -> {
						Register newReg  = new Register(reg);
						if(b==reg.get(a)) {
							reg.set(c, 1);
						} else {
							reg.set(c, 0);
						}
						return newReg;
					}
				);		
		//eqrr (equal register/register) sets register C to 1 if register A is equal to register B. Otherwise, register C is set to 0.
		operations.add(
				(a,b,c,reg) -> {
						Register newReg  = new Register(reg);
						if(reg.get(a)==reg.get(b)) {
							reg.set(c, 1);
						} else {
							reg.set(c, 0);
						}
						return newReg;
					}
				);		
	}

	public ArrayList<OpsLambda> getAll() {
		return operations;
	}
}


//658 too low
public class Day16 {	
	public final static String INPUT_FILE = "Day16_Input.txt";
//	public final static String INPUT_FILE = "Day16_Example.txt";
	
	public static void main(String[] args) {
		ArrayList<String> lines = FileHelper.readFile(INPUT_FILE);
		Operations operations = new Operations();
		
		part1(lines, operations);
	}

	private static void part1(ArrayList<String> lines, Operations operations) {

		int count=0;
		for(int i=0;i<lines.size();i++) {
			if(lines.get(i).isEmpty()) {
				if(lines.get(i+1).isEmpty()) {
					break;
				}
				continue;
			}
			String[] before = lines.get(i).substring(9, lines.get(i).length()-1).split(", ");
			i++;
			String[] opsInput = lines.get(i).split(" ");
			i++;
			String[] after = lines.get(i).substring(9, lines.get(i).length()-1).split(", ");

			int a = Integer.parseInt(opsInput[1]);
			int b = Integer.parseInt(opsInput[2]);
			int c = Integer.parseInt(opsInput[3]);
			Register input = new Register(before); 
			Register output = new Register(after);
			
			if(hasMoreThan3Matches(a, b, c, input, output, operations)) {
				count++;
			}
		}
		
		System.out.println(count);
	}

	public static boolean hasMoreThan3Matches(int a, int b, int c, Register input, Register output, Operations operations) {
		int match=0;
		for(OpsLambda op : operations.getAll()) {
			Register result = op.execute(a,b,c,input);
			if(result.equals(output)) {
				match++;
			}
		}
		return match>=3;
	}

}


