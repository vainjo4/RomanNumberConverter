import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * This is a class for converting numbers from Roman to Arabic format.
 * The class is not static and has a nested enum for better defining the 
 * numerals. The numerals have been assigned ranks and values.
 * 
 * Roman numerals are
 * I = 1
 * V = 5
 * X = 10
 * L = 50
 * C = 100
 * D = 500
 * M = 1000
 * 
 * The Roman numeric system works as follows:
 * 
 * 1. 	The value of each numeral is added to the total, with the exception 
 * 		that a numeral written before a bigger one is subtracted from 
 * 		it (e.g. IX = 9).
 * 
 * 2. 	Apart from the above, numerals are written in descending 
 * 		order by size (e.g. MDCV).
 * 
 * 3. 	There can't be two subtractable numerals in a row (e.g. IIX is 
 * 		not allowed).
 * 
 * @author	Johannes Vainio
 * @date	15.3.2013
 */
@SuppressWarnings("unused")
public class RomanNumberConverter {

	/**
	 * The sum that is accumulated by the convertNumber method.
	 * This is a class attribute to facilitate helper method use.
	 */
	private int sum;

	/**
	 * Constructor. Does nothing else.
	 */
	public RomanNumberConverter() {
	}

	/**
	 * This is the method for running the converter.
	 * @param input		the Roman number as a String
	 * @return			the converted number
	 */
	public int runConverter(String input) {

		//reset sum
		this.sum = 0;

		//save the length of the input String
		int inputlength = input.length();

		//make equally long arrays for chars and RomanNumerals
		char[] chararray = input.toCharArray();		
		RomanNumeral[] romanarray = new RomanNumeral[inputlength];

		//a loop for converting chararray values to RomanNumerals 
		//and saving them in romanarray
		int i = 0;
		while(i < inputlength) {

			romanarray[i] = this.convertCharToRomanNumeral(chararray[i]);

			//nullcheck in case of invalid inputs
			if (romanarray[i] == null) {
				this.invalidInput(-1);
				return -1;
			}
			i++;
		}

		//a call to the main loop
		this.mainLoop(inputlength, chararray, romanarray);

		//after the loop finishes, returns the accumulated sum.
		return this.sum;
	}


	/**
	 * The main loop, which analyses the numerals and manipulates the sum. 
	 * Used from runConverter.
	 * @param inputlength	length of the input
	 * @param chararray		input as array of chars
	 * @param romanarray	input as array of RomanNumerals
	 */
	private void mainLoop(int inputlength, char[] chararray, 
			RomanNumeral[] romanarray) {

		//a loop for adding numerals' values to the sum.
		int i = 0;
		while(i < inputlength) {



			//check if this is the last numeral. Last one 
			//is always added to the sum, since it is not possible 
			//for the next one to be bigger in rank.
			//the check is here to keep further checks within the array.
			if (i == inputlength-1) {

				this.addNumeralToSum(romanarray[i]);
			}

			//if the next numeral is one or two ranks bigger:
			else if (romanarray[i].getRank() + 1
					== romanarray[i+1].getRank()
					|| romanarray[i].getRank() + 2
					== romanarray[i+1].getRank()) {

				//=> this numeral is subtracted from the sum.
				this.subtractNumeralFromSum(romanarray[i]);
			}

			//if the next numeral is of a lower or same rank: 
			else if (romanarray[i].getRank()
					>= romanarray[i+1].getRank()) {

				int j = i+2;
				while(j < inputlength-1) {

					//Additional check to see if there is a higher-
					//ranking numeral further to the right. That is not 
					//allowed in the Roman system.
					if (romanarray[i].getRank()
							< romanarray[j].getRank()) {

						this.invalidInput(-2);
					}
					j++;
				}

				//=> this numeral is added to the sum.
				this.addNumeralToSum(romanarray[i]);
			}

			//the only case left is that of the next numeral being
			//more than two ranks bigger. That is not allowed in the 
			//Roman system.
			else {
				this.invalidInput(-3);
			}

			//move on to the next numeral
			i++;
		}
	}



	/**
	 * Helper method. Prints "Input not valid" with error source identifier.
	 */
	public void invalidInput(int identifier) {
		System.out.println("Input not valid " +identifier);
	}
	/**
	 * Helper method. Adds to the sum.
	 * @param num	the numeral which value is to be added
	 */
	public void addNumeralToSum(RomanNumeral num) {
		this.sum = this.sum + num.getValue();
	}
	/**
	 * Helper method. Subtracts from the sum.
	 * @param num	the numeral which value is to be subtracted
	 */
	public void subtractNumeralFromSum(RomanNumeral num) {
		this.sum = this.sum - num.getValue();
	}




	/**
	 * Converts chars to RomanNumerals.
	 * @param c	the character to convert
	 * @return	corresponding RomanNumeral or null if invalid
	 */
	public RomanNumeral convertCharToRomanNumeral(char c) {
		RomanNumeral num;

		switch(c){
		case 'I' : {num = RomanNumeral.I; break;}
		case 'V' : {num = RomanNumeral.V; break;}
		case 'X' : {num = RomanNumeral.X; break;}
		case 'L' : {num = RomanNumeral.L; break;}
		case 'C' : {num = RomanNumeral.C; break;}
		case 'D' : {num = RomanNumeral.D; break;}
		case 'M' : {num = RomanNumeral.M; break;}
		default : num = null;}

		return num;
	}

	/**
	 * An enumeration to help define the Roman numerals 
	 * and helper methods.
	 * @author Johannes
	 */
	public enum RomanNumeral {
		I,V,X,L,C,D,M;

		/**
		 * Method for getting a numeral's rank.
		 * Bigger value -> bigger rank. Ranks are from 1 to 7.
		 * @return	the rank of the numeral
		 */
		public int getRank() {

			int rank;

			switch(this){
			case I : {rank = 1; break;}
			case V : {rank = 2; break;}
			case X : {rank = 3; break;}
			case L : {rank = 4; break;}
			case C : {rank = 5; break;}
			case D : {rank = 6; break;}
			case M : {rank = 7; break;}
			default : rank = -1;
			}

			return rank;
		}

		/**
		 * Method for getting a numeral's number value.
		 * @return the value of the numeral
		 */
		public int getValue() {

			int value;

			switch(this){
			case I : {value = 1; break;}
			case V : {value = 5; break;}
			case X : {value = 10; break;}
			case L : {value = 50; break;}
			case C : {value = 100; break;}
			case D : {value = 500; break;}
			case M : {value = 1000; break;}
			default : value = -1;
			}

			return value;
		}

	}

	public static void main(String[] args) {

		RomanNumberConverter con = new RomanNumberConverter();


		/*
		 //A reader for testing

		BufferedReader reader
		= new BufferedReader(
				new InputStreamReader(System.in));

		while(true) {
			try {
				System.out.println(con.runConverter(reader.readLine()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/


		// a block of 20 tests
		
		System.out.println(con.runConverter("LIX"));	
		System.out.println(con.runConverter("MDCLXII"));
		System.out.println(con.runConverter("DCCV"));
		System.out.println(con.runConverter("MDCCIX"));
		System.out.println(con.runConverter("MDCCLXXVI"));
		System.out.println(con.runConverter("DCXXII"));
		System.out.println(con.runConverter("DCCXCIV"));
		System.out.println(con.runConverter("CCXXXIV"));
		System.out.println(con.runConverter("CMLIII"));
		System.out.println(con.runConverter("CMVI"));
		System.out.println(con.runConverter("CCCXLIV"));
		System.out.println(con.runConverter("MCCCLXIII"));
		System.out.println(con.runConverter("CDXXV"));
		System.out.println(con.runConverter("DCCLXIII"));
		System.out.println(con.runConverter("MCCXCIII"));
		System.out.println(con.runConverter("CDLXIII"));
		System.out.println(con.runConverter("MLXVIII"));
		System.out.println(con.runConverter("MDCCLX"));
		System.out.println(con.runConverter("MCMXLV"));
		System.out.println(con.runConverter("MCXCIX"));
		
		//bad input test
		System.out.println(con.runConverter("acsfga"));

		/*
		 Correct answers to above tests
		59
		1662
		705
		1709
		1776
		622
		794
		234
		953
		906
		344
		1363
		425
		763
		1293
		463
		1068
		1760
		1945
		1199
		
		courtesy of
		http://www.random.org/
		http://www.onlineconversion.com/roman_numerals_advanced.htm
		 */
	}
}
