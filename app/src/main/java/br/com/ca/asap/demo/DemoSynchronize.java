package br.com.ca.asap.demo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.ca.asap.database.DatabaseOpenHelper;
import br.com.ca.asap.database.AdminDAO;
import br.com.ca.asap.database.DeliverableDAO;
import br.com.ca.asap.vo.AdminVo;
import br.com.ca.asap.vo.DeliverableVo;
import br.com.ca.asap.database.InitiativeDAO;
import br.com.ca.asap.vo.InitiativeVo;

/**
 * DemoSynchronize
 */
public class DemoSynchronize {

    private List<InitiativeVo> initiativeVoList = null;
    private List<DeliverableVo> deliverableVoList = null;
    private List<AdminVo> adminVoList = null;

    //Empty constructor
    public DemoSynchronize(){

    }

    //Rewrite SQLite database with current initiatives
    public void demoSynchronize(Context context){

        SQLiteDatabase db;

        //instantiate DatabaseOpenHelper
        DatabaseOpenHelper databaseOpenHelper = DatabaseOpenHelper.getInstance(context);

        //delete all from initiative table before repopulating
        db = databaseOpenHelper.getWritableDatabase();
        databaseOpenHelper.deleteCaTables();

        //Synchronize from server database (in demo version create with local values)

        //create Initiative DAO
        InitiativeDAO initiativeDAO = new InitiativeDAO(context);
        //create Work Item DAO
        DeliverableDAO deliverableDAO = new DeliverableDAO(context);
        AdminDAO adminDAO = new AdminDAO(context);

        //create array lists
        initiativeVoList = new ArrayList<>();
        deliverableVoList = new ArrayList<>();
        adminVoList = new ArrayList<>();

        //DEMO LOCAL CREATION
        //local creation of list of InitiativeVo. In the non demo version, list must be create from CA server http response
        //hard coded for demo version. In non demo version CA server must return array of initiatives for synchronization


        //
        //CREATE INITIATIVES LIST
        //

        initiativeVoList.add(new InitiativeVo("TEAMWORK","TEAMWORK","Team Initiative."));



        //insert list of initiatives
        //access initiative list via Iterator
        Iterator iterator = initiativeVoList.iterator();
        while(iterator.hasNext()){
            InitiativeVo initiativeVo = (InitiativeVo) iterator.next();
            //...insert into initiative table
            initiativeDAO.insertInitiative(initiativeVo);
        }

        //DEMO LOCAL CREATION
        //local creation of list of DeliverableVo. In the non demo version, list must be create from CA server http response
        //hard coded for demo version. In non demo version CA server must return array of work items for synchronization


        //
        //CREATE DELIVERABLES LIST
        //

        deliverableVoList.add(new DeliverableVo("001", "TEAMWORK", "DELIVERY 01", "DELIVERY 01 DESCRIPTION", "TODO ACTION", "Open", "2015-10-10", "RODRIGO CARVALHO DOS SANTOS", "5"));
        deliverableVoList.add(new DeliverableVo("002", "TEAMWORK", "DELIVERY 02", "DELIVERY 02 DESCRIPTION", "TODO ACTION", "Open", "2015-10-10", "RODRIGO CARVALHO DOS SANTOS", "5"));


        //insert list of work items
        //access initiative list via Iterator
        Iterator iterator2 = deliverableVoList.iterator();
        while(iterator2.hasNext()){
            DeliverableVo deliverableVo = (DeliverableVo) iterator2.next();
            Log.d("DemoSynchronize","Inserting into database : " + deliverableVo.getDeliverable_title());
            //...insert into initiative table
            deliverableDAO.insertWorkItem(deliverableVo);
        }

        //
        //CREATE ADMIN LIST
        //
        adminVoList.add(new AdminVo("1","RODRIGO CARVALHO DOS SANTOS"));


        //insert list of initiatives
        //access initiative list via Iterator
        Iterator iterator3 = adminVoList.iterator();
        while(iterator3.hasNext()){
            AdminVo adminVo = (AdminVo) iterator3.next();
            //...insert into Pmo table
            adminDAO.insertPmo(adminVo);
        }
        //END OF DEMO LOCAL CREATION
    }
}
