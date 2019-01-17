import java.util.Scanner;

public class ProgressiveScramble {

    public static void /*main (String[] args)*/ scramble(){
        Scanner scan = new Scanner(System.in);

        int lines = scan.nextInt();
        scan.nextLine();

        //encrypts or decrypts each input line
        for (int i = 0; i < lines; i ++){
            String input = scan.nextLine();
            if (input.charAt(0) == 'e'){
                System.out.println(encrypt(input.substring(2)));
            }
            else {System.out.println(decrypt(input.substring(2)));}
        }
    }

    public static String encrypt(String input){
        int inputSize = input.length();
        int[] values = new int[inputSize];
        //converts to encrypting algorithm's numerical values for chars
        for (int i = 0; i < inputSize; i ++){
            char current = input.charAt(i);
            if (current == ' '){
                values[i] = 0;
            }
            else {values[i] = current - 96;}
        }

        int[] encryptedVals = new int[inputSize];
        encryptedVals[0] = values[0];
        String encryption = "";
        if (encryptedVals[0] == 0){
            encryption += ' ';
        }
        else {encryption += Character.toString((char)(encryptedVals[0] + 96));}

        //calculating encryption algorithm's encrypted value, building encrypted string
        for (int val = 1; val < inputSize; val ++){
            encryptedVals[val] = (values[val]+ encryptedVals[val-1])%27;

            if (encryptedVals[val] == 0){
                encryption += ' ';
            }
            else {encryption += Character.toString((char)(encryptedVals[val] + 96));}
        }
        return encryption;
    }

    public static String decrypt (String input){
        int inputSize = input.length();
        int[] modulos = new int[inputSize];
        for (int charInd = 0; charInd < inputSize; charInd ++){
            if (input.charAt(charInd) == ' '){
                modulos[charInd] = 0;
            }
            else {modulos[charInd] = input.charAt(charInd) - 96;}
        }

        int[] decryptedVals = new int[inputSize];
        decryptedVals[0] = modulos[0];
        int[] sums = new int[inputSize];
        sums[0] = modulos[0];
        String decryption = "";
        if (decryptedVals[0] == 0){
            decryption += ' ';
        }
        else {decryption += Character.toString((char)(decryptedVals[0] + 96));}

        for (int val = 1; val < inputSize; val ++){
            sums[val] = ((((modulos[val] - sums[val - 1])%27) + 27) % 27) + sums[val - 1];
            decryptedVals[val] = sums[val] - sums[val-1];

            if (decryptedVals[val] == 0){
                decryption += ' ';
            }
            else {decryption += Character.toString((char)(decryptedVals[val] + 96));}
        }
        return decryption;
    }
}