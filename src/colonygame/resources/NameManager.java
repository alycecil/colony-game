/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame.resources;

import colonygame.game.Person;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author WilCecil
 */
public class NameManager {

    static NameManager readNames(File surnames, File male, File female) {

        ArrayList<String> surs;
        ArrayList<String> f;
        ArrayList<String> m;
        
        surs = new ArrayList<>();
        
        
        m = new ArrayList<>();
        
        
        f = new ArrayList<>();



        //parse the files adding each line of the file as a new name entry

        Scanner scanner;

        try {
            //read surnames
            scanner = new Scanner(surnames);

            while (scanner.hasNextLine()) {
                surs.add(scanner.nextLine());
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(NameManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //read female
            scanner = new Scanner(female);

            while (scanner.hasNextLine()) {
                f.add(scanner.nextLine());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NameManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //read male
            scanner = new Scanner(male);

            while (scanner.hasNextLine()) {
                m.add(scanner.nextLine());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NameManager.class.getName()).log(Level.SEVERE, null, ex);
        }


        //ensure none are empty
        if (surs.isEmpty()) {
            surs.add("Colonist");
        }
        if (m.isEmpty()) {
            m.add("Male");
        }
        if (f.isEmpty()) {
            f.add("Female");
        }

        return new NameManager(surs, f, m);


    }
    ArrayList<String> surnames;
    ArrayList<String> femaleNames;
    ArrayList<String> maleNames;

    public NameManager(ArrayList<String> surnames, ArrayList<String> femaleNames, ArrayList<String> maleNames) {
        this.surnames = surnames;
        this.femaleNames = femaleNames;
        this.maleNames = maleNames;
    }
    
    public String getSurname(Random r){
        return surnames.get(r.nextInt(surnames.size()));
    }
    
    public String getFemaleName(Random r){
        return femaleNames.get(r.nextInt(femaleNames.size()));
    }
    
    public String getMaleName(Random r){
        return maleNames.get(r.nextInt(maleNames.size()));
    }

    public String getFirstName(Random r, boolean gender) {
        if(gender==Person.MALE){
            return getMaleName(r);
        }
        
        return getFemaleName(r);
    }
}
