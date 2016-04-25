package br.com.ca.asap.synchronize;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.ca.asap.database.DatabaseOpenHelper;
import br.com.ca.asap.database.DeliverableDAO;
import br.com.ca.asap.database.InitiativeDAO;
import br.com.ca.asap.hiveservices.HiveGetDeliverablesByInitiative;
import br.com.ca.asap.hiveservices.HiveGetInitiatives;
import br.com.ca.asap.vo.AdminVo;
import br.com.ca.asap.vo.DeliverableVo;
import br.com.ca.asap.vo.InitiativeVo;

/**
 * Synchronizer
 *
 * @author Rodrigo Carvalho
 */
public class Synchronizer {

    Context context = null;

    //
    //Constructor
    //
    public Synchronizer(Context context){
        this.context = context;
    }

    /**
     * deleteAndFetchInitiatives
     *
     */
    public void deleteAndFetchInitiatives(){

        List<InitiativeVo> initiativeVoList = null;
        List<DeliverableVo> deliverableVoList = null;

        Iterator initiativeIterator = null;
        Iterator deliverableIterator = null;

        SQLiteDatabase db;
        //instantiate DatabaseOpenHelper
        DatabaseOpenHelper databaseOpenHelper = DatabaseOpenHelper.getInstance(context);

        //delete all from initiative table before repopulating
        db = databaseOpenHelper.getWritableDatabase();
        databaseOpenHelper.deleteTables();

        //Synchronize from server database (in demo version create with local values)

        //create Deliverable DAO
        DeliverableDAO deliverableDAO = new DeliverableDAO(context);


        //
        //FETCH INITIATIVES
        //

        //fetch from hive cloud server
        HiveGetInitiatives hiveGetInitiatives = new HiveGetInitiatives(context);
        initiativeVoList = hiveGetInitiatives.getInitiatives();

        //
        //INSERT INITIATIVES
        //

        //insert list of initiatives
        //access initiative list via Iterator
        //create Initiative DAO
        InitiativeDAO initiativeDAO = new InitiativeDAO(context);
        initiativeIterator = initiativeVoList.iterator();
        while(initiativeIterator.hasNext()){
            InitiativeVo initiativeVo = (InitiativeVo) initiativeIterator.next();
            //...insert into initiative table
            initiativeDAO.insertInitiative(initiativeVo);
        }

        //
        //FOR EACH INITIATIVE
        //
        initiativeIterator = initiativeVoList.iterator();
        while(initiativeIterator.hasNext()){
            InitiativeVo initiativeVo = (InitiativeVo) initiativeIterator.next();

            //
            //FETCH INITIATIVE DELIVERABLES
            //
            HiveGetDeliverablesByInitiative hiveGetDeliverablesByInitiative = new HiveGetDeliverablesByInitiative(context);
            deliverableVoList = hiveGetDeliverablesByInitiative.getDeliverablesByInitiative(initiativeVo);

            //
            //insert list of deliverable vo
            //access deliverable list via Iterator
            deliverableIterator = deliverableVoList.iterator();
            while(deliverableIterator.hasNext()){
                DeliverableVo deliverableVo = (DeliverableVo) deliverableIterator.next();
                //...insert into deliverable table
                deliverableDAO.insertDeliverableVo(deliverableVo);
            }

        }

    }
}
