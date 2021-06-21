/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

/**
 *
 * @author User
 */
public class CaeserCrypto {
    
    String text;

    public CaeserCrypto() {
    }
    
    public CaeserCrypto(String text) {
        this.text = text;
    }
    
    public StringBuffer Encrypt(String text, int key){
        // if the input is greater than 26, 32 for exemple, it is considered as 32 - 26 = 6 shifts 
        if (key >26){
            key = key -26;
        }
        //clean the text from spaces and transform it to uppercase
        text = text.trim().toUpperCase();
        text = text.replaceAll(" ", "");
        
        StringBuffer result = new StringBuffer();
        
        // from the formula Ek(M) = (x + k) mod 26
        for(int i = 0; i< text.length(); i++){
            char ch = (char) (((int) text.charAt(i) + key - 65 ) %26 + 65); 
            result.append(ch);
        }
        return result;
    }
    
    public StringBuffer Decrypt(String text, int key){
        // if the input is greater than 26, 32 for exemple, it is considered as 32 - 26 = 6 shifts 
        if (key >26){
            key = key -26;
        }
        
        //clean the text from spaces and transform it to uppercase
        text = text.trim().toUpperCase();
        text = text.replaceAll(" ", "");

        StringBuffer result = new StringBuffer();
        
        // from the formula Ek(M) = (x - k) mod 26
        for(int i = 0; i< text.length(); i++){
            char ch = (char) (((int) text.charAt(i) - key - 90 ) %26 + 90); 
            result.append(ch);
        }
        return result;
    }
    
}
