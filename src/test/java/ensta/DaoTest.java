package ensta;

import com.ensta.librarymanager.dao.LivreDaoImpl;
import com.ensta.librarymanager.dao.MembreDaoImpl;
import com.ensta.librarymanager.dao.EmpruntDaoImpl;
import com.ensta.librarymanager.dao.DaoException;

import com.ensta.librarymanager.model.Abonnement;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.model.Emprunt;
import java.time.LocalDate;


import org.junit.Test;


public class DaoTest 
{
    
    @Test
    public void TestApp() throws DaoException
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
        LivreDaoImpl daoL = LivreDaoImpl.getInstance();
        int idL=daoL.create("pensees","pascal","123457");
        System.out.println(idL);
        Livre L=daoL.getById(idL);
        System.out.println(L);
        L.setTitre("pensees 2");
        daoL.update(L);
        System.out.println(daoL.getById(idL));

        System.out.println("test membre");
        MembreDaoImpl daoM = MembreDaoImpl.getInstance();
        int idM=daoM.create("jean","dupont","7 avenue du pont vittel","a@a.com","0987654321");
        System.out.println(idM);
        Membre M=daoM.getById(idM);
        System.out.println(M);
        M.setPrenom("jean claude");
        M.setAbonnement(Abonnement.VIP);
        daoM.update(M);
        System.out.println(daoM.getById(idM));

        System.out.println("test emprunt");
        EmpruntDaoImpl daoE = EmpruntDaoImpl.getInstance();
        //daoE.create(idL,idM,dateEmprunt);
       


    }
}
