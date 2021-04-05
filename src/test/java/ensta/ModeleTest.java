package ensta;

import com.ensta.librarymanager.model.Abonnement;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.model.Emprunt;
import java.time.LocalDate;


import org.junit.Test;


public class ModeleTest 
{
    
    @Test
    public void TestApp()
    {
        Membre testMembre =new Membre(0001,"dupont","jean","7 avenue du pont vittel","jd@a.com","0987654321",Abonnement.PREMIUM);
        System.out.println(testMembre);
        testMembre.setPrenom("jean claude");
        System.out.println(testMembre);

        Livre livre1 = new Livre(0001,"Mody Dick","melville","123456");
        Livre livre2 = new Livre(0002,"pensees","pascal","123457");
        System.out.println(livre1);
        System.out.println(livre2);
        livre2.setTitre("brideshead revisited");
        livre2.setAuteur("waugh");
        System.out.println(livre2);

        LocalDate dateEmprunt = LocalDate.of(2021, 3, 8);
        LocalDate dateRetour = LocalDate.of(2020, 3, 19);
        Emprunt testemprunt =new Emprunt(0001, testMembre,livre2, dateEmprunt, dateRetour);
        System.out.println(testemprunt);



    }
}
