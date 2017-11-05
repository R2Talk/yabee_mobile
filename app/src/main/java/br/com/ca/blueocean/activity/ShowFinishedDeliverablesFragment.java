package br.com.ca.blueocean.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

//import br.com.ca.yabee.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.ca.blueocean.adapter.DeliverablesAdapter;
import br.com.ca.blueocean.adapter.FinishedDeliverablesAdapter;
import br.com.ca.blueocean.database.DeliverableDAO;
import br.com.ca.blueocean.vo.DeliverableVo;
import br.com.ca.shareview.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * { ShowFinishedDeliverablesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowFinishedDeliverablesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowFinishedDeliverablesFragment extends Fragment {

    // the fragment initialization parameters
    private static final String ARG_INITIATIVE_ID = "INITIATIVE_ID"; //expected value to the activity initialization
    private static final String ARG_INITIATIVE_TITLE = "INITIATIVE_TITLE"; //expected value to the activity initialization


    //parameters
    private String mInitiativeId;
    private String mInitiativeTitle;

    //ListView Adapter
    FinishedDeliverablesAdapter adapter = null;
    ListView listView = null;

    //listener implemented in the Activity that contains this fragment, and has the callback method
    private ShowOpenedDeliverablesFragment.OnFragmentInteractionListener mListener;

    /**
     * Empty constructor
     *
     */
    public ShowFinishedDeliverablesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param initiativeId Parameter 1.
     * @param initiativeTitle Parameter 2.
     * @return A new instance of fragment ShowFinishedDeliverablesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowFinishedDeliverablesFragment newInstance(String initiativeId, String initiativeTitle) {
        ShowFinishedDeliverablesFragment fragment = new ShowFinishedDeliverablesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_INITIATIVE_ID, initiativeId);
        args.putString(ARG_INITIATIVE_TITLE, initiativeTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mInitiativeId = getArguments().getString(ARG_INITIATIVE_ID);
            mInitiativeTitle = getArguments().getString(ARG_INITIATIVE_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_finished_deliverables, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);


        //Initialize List View
        //
        // 1. pass context and data to the custom adapter
        adapter = new FinishedDeliverablesAdapter(getActivity().getApplicationContext(), getDeliverableArrayList(mInitiativeId));
        // 2. Get ListView from activity xml
        //BEWARE: Use getView(). It returns the root view for the fragment (the one returned by onCreateView() method). With this you can call findViewById().
        listView = (ListView) getView().findViewById(R.id.f_deliverables_listView);
        // 3. setListAdapter
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                /*
                Context context = getActivity().getApplicationContext();
                TextView deliverableIdTextView = (TextView) view.findViewById(R.id.deliverableIdTextView); //view list item is received as a parameter
                CharSequence text = "Selected delvirable is " + deliverableIdTextView.getText().toString();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                */

            }
        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    /**
     * onStart
     *
     * refresh the list of activities
     */
    @Override
    public void onStart() {
        super.onStart();
        refreshDeliverablesListView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * refreshInitiativesListView
     *
     */
    private void refreshDeliverablesListView(){
        //reload content
        adapter.clear();
        adapter.addAll((ArrayList<DeliverableVo>) getDeliverableArrayList(mInitiativeId));
        adapter.notifyDataSetChanged();
    }

    /**
     * getDeliverableArrayList
     *
     * Generate DeliverableVo ArrayList for use with ListView Adapter
     *
     * @param initiativeId
     * @return
     */
    private ArrayList<DeliverableVo> getDeliverableArrayList(String initiativeId){

        ArrayList<DeliverableVo> deliverableVoArrayList = new ArrayList<>();
        List<DeliverableVo> deliverableVoList;

        //BEWARE: Use getActivity to get the appliction context from a fragment
        Context context = getActivity().getApplicationContext();

        DeliverableDAO deliverableDAO = new DeliverableDAO(context);
        deliverableVoList = deliverableDAO.selectFinishedDeliverablesByInitiativeId(initiativeId);

        //access initiative list via Iterator
        Iterator iterator = deliverableVoList.iterator();
        while(iterator.hasNext()){
            DeliverableVo deliverableVo = (DeliverableVo) iterator.next();
            //add into ArrayList
            deliverableVoArrayList.add(deliverableVo);

            //TODO: Needs evaluation. Code for adding into data strucutre (code array list) for future context menu event reference.
            //deliverableCodeArrayList.add(deliverableVo.getCode());
        }

        return deliverableVoArrayList;
    }

}
