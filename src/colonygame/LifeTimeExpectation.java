/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colonygame;

import colonygame.game.Person;
import colonygame.resources.Settings;
import java.util.Random;

/**
 *
 * @author WilCecil
 */
public class LifeTimeExpectation {

    public static int sampleSize = 10000;

    public static void main(String[] args) {
        double age = 0;
        
        int min = Integer.MAX_VALUE;
        int max = 0;
        Random r = new Random(1000);
        
        double chance = 1.0/15000;
        boolean alive;
        int life;
        
        for (int i = 0; i < sampleSize; i++) {
            alive = true;
            life = 0;
            while(alive){
                life++;
                if(r.nextDouble()<chance){
                    age+=life;
                    alive=false;
                    
                    if(min>life){
                        min = life;
                    }
                    if(max<life){
                        max = life;
                    }
                }
            }
        }
        
        System.out.println("Avg:"+age/sampleSize);
        System.out.println("Min:"+min);
        System.out.println("Max:"+max);
        
    }

    /**
     * does a simulation on a sample of people to get expected life from
     * settings
     *
     * @param args
     */
    public static void main2(String[] args) {
        double age = 0;
        double births = 0;
        double teenPregnancy = 0;
        int women = 0;
        int[] deaths = new int[7];
        int oldest = 0;
        String[] deathsDest = new String[]{"Infant", "Child", "Teen", "Adult", "Elder", "Ancient", "Childbirth"};
        boolean addMedical = false;
        boolean addSick = false;
        double roll;

        Random r = new Random(1000);

        //setup array, while java usually does this, lets just be safe
        for (int i = 0; i < deaths.length; i++) {
            deaths[i] = 0;
        }

        for (int i = 0; i < sampleSize; i++) {
            boolean alive = true;

            int pregnant = 0;

            boolean fertile = false;

            boolean female = r.nextBoolean();
            boolean teen = false;

            if (female) {
                women++;
            }

            for (int tAge = 1; alive; tAge++) {
                double rate = 0;
                int block = 0;

                roll = r.nextDouble();

                //catch eternal life
                if (tAge > 100000) {
                    alive = false;
                    age += tAge;
                    deaths[5]++;
                    break;
                }

                if (tAge > Person.MIN_ELDER) {
                    fertile = false;
                    teen = false;
                    rate = Settings.DEFAULT_MORTALITY_ELDER;
                    block = 4;
                } else if (tAge > Person.MIN_ADULT) {
                    fertile = true;
                    teen = false;
                    rate = Settings.DEFAULT_MORTALITY_ADULT;
                    block = 3;
                } else if (tAge > Person.MIN_TEEN) {
                    fertile = true;
                    teen = true;
                    rate = Settings.DEFAULT_MORTALITY_TEEN;
                    block = 2;
                } else if (tAge > Person.MIN_CHILD) {
                    fertile = false;
                    teen = false;
                    rate = Settings.DEFAULT_MORTALITY_CHILD;
                    block = 1;
                } else if (tAge > Person.MIN_BABY) {
                    fertile = false;
                    teen = false;
                    rate = Settings.DEFAULT_MORTALITY_INFANT;
                    block = 0;
                } else {
                    //wtf, guy.
                    rate = 0;
                }

                //check if we're giving birth
                if (tAge == pregnant) {
                    births++;

                    block = 6;

                    //roll for pregnancy death
                    if (addMedical) {
                        rate = Settings.DEFAULT_MORTALITY_PREGNANT_MEDICAL;
                    } else {
                        rate = Settings.DEFAULT_MORTALITY_PREGNANT;
                    }
                }


                if (addMedical) {
                    rate *= Settings.DEFAULT_MEDICAL_MODIFIER;
                }
                if (addSick) {
                    rate *= Settings.DEFAULT_SICK_MODIFIER;
                }



                if (roll < rate) {
                    alive = false;
                    age += tAge;
                    deaths[block]++;
                }


                if (female && alive && fertile && pregnant < tAge) {
                    //role for pregnany
                    if (teen) {
                        rate = Settings.DEFAULT_PREGNANCY_EVENT_TEEN;
                    } else {

                        rate = Settings.DEFAULT_PREGNANCY_EVENT_ADULT;
                    }

                    if (r.nextDouble() < rate) {
                        pregnant = tAge + Settings.DEFAULT_PREGNANCY_LENGTH;

                        if (teen) {
                            teenPregnancy++;
                        }
                    }
                }



                //update oldest
                if (oldest < tAge) {
                    oldest = tAge;
                }
            }
        }//main simulation block

        System.out.println(
                "Mortality Calculator for sample size = " + sampleSize);
        System.out.println(
                "Added Medical " + addMedical);
        System.out.println(
                "Added Sick " + addSick);
        System.out.println();

        System.out.println(
                "Average Age " + age / sampleSize);
        System.out.println();

        System.out.println(
                "Average Births " + births / women + ", total " + births);
        System.out.println();

        System.out.println(
                "Average Teen Births " + teenPregnancy / women + ", total " + teenPregnancy);
        System.out.println();

        System.out.println(
                "Women " + women);
        System.out.println();

        System.out.println(
                "Oldest " + oldest);
        System.out.println();

        System.out.println(
                "Death By Group");

        for (int i = 0;
                i < deaths.length;
                i++) {
            System.out.println("Block " + deathsDest[i] + " = " + deaths[i]);
        }
    }
}
