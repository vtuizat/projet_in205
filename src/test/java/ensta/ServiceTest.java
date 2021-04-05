package ensta;

import com.ensta.librarymanager.service.MembreServiceImpl;
import com.ensta.librarymanager.service.LivreServiceImpl;
import com.ensta.librarymanager.service.EmpruntServiceImpl;


import com.ensta.librarymanager.model.Abonnement;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.service.ServiceException;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.model.Emprunt;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import org.junit.Test;


public class ServiceTest 
{
    
    @Test
    public void TestApp() throws ServiceException
    {
        Membre testMembre =new Membre(0001,"dupont","jean","7 avenue du pont vittel","jd@a.com","0987654321",Abonnement.PREMIUM);
        testMembre.setPrenom("jean claude");

        Livre livre1 = new Livre(0001,"Mody Dick","melville","123456");
        Livre livre2 = new Livre(0002,"pensees","pascal","123457");
        livre2.setTitre("brideshead revisited");
        livre2.setAuteur("waugh");

        LocalDate dateEmprunt = LocalDate.of(2021, 3, 8);
        LocalDate dateRetour = LocalDate.of(2020, 3, 19);
        Emprunt testemprunt =new Emprunt(0001, testMembre,livre2, dateEmprunt, dateRetour);

        System.out.println("test livre");
        LivreServiceImpl serviceL = LivreServiceImpl.getInstance();
        int idL=serviceL.create("pensees","pascal","123457");
        System.out.println(idL);
        Livre L=serviceL.getById(idL);
        System.out.println(L);
        L.setTitre("pensees 2");
        serviceL.update(L);
        System.out.println(serviceL.getById(idL));

        System.out.println("test membre");
        MembreServiceImpl serviceM = MembreServiceImpl.getInstance();
        int idM=serviceM.create("dupont","jean","7 avenue du pont vittel","jd@a.com","0987654321");
        System.out.println(idM);
        Membre M=serviceM.getById(idM);
        System.out.println(M);
        M.setPrenom("pascal");
        serviceM.update(M);
        System.out.println(serviceM.getById(idM));

        List<Membre> A=new ArrayList<Membre>() ;
        A=serviceM.getListMembreEmpruntPossible();
        for (int k=0;k<A.size();k++){
            System.out.println(A.get(k));
        }
        

        System.out.println("test emprunt");
       


    }
}
