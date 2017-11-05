package br.com.ca.blueocean.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.ca.blueocean.adapter.DeliverablesAdapter;
import br.com.ca.blueocean.database.DeliverableDAO;
import br.com.ca.blueocean.users.UserManager;
import br.com.ca.blueocean.vo.DeliverableVo;
import br.com.ca.blueocean.vo.UserVo;
import br.com.ca.shareview.R;

/**
 * ShowOpenedDeliverablesFragment
 *
 * Show list of deliverables associated with an identified Initiative.
 *
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowOpenedDeliverablesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 *
 * Use the {@link ShowOpenedDeliverablesFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author Rodrigo Carvalho
 */
public class ShowOpenedDeliverablesFragment extends Fragment {

    // the fragment initialization parameters
    private static final String ARG_INITIATIVE_ID = "INITIATIVE_ID"; //expected value to the activity initialization
    private static final String ARG_INITIATIVE_TITLE = "INITIATIVE_TITLE"; //expected value to the activity initialization

    //used for identification of StartActivityForResults request
    public final int SHOW_DELIVERABLE_DETAILS_FOR_ACTION_INTENT_CALL = 1;

    //parameters
    private String mInitiativeId;
    private String mInitiativeTitle;

    //listener implemented in the Activity that contains this fragment, and has the callback method
    private ShowOpenedDeliverablesFragment.OnFragmentInteractionListener mListener;

    //ListView Adapter
    DeliverablesAdapter adapter = null;
    ListView listView = null;

    /**
     * Empty constructor
     *
     */
    public ShowOpenedDeliverablesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param initiativeId Parameter 1.
     * @return A new instance of fragment ShowOpenedDeliverablesFragment.
     */
    public static ShowOpenedDeliverablesFragment newInstance(String initiativeId, String initiativeTitle) {
        ShowOpenedDeliverablesFragment fragment = new ShowOpenedDeliverablesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_INITIATIVE_ID, initiativeId);
        args.putString(ARG_INITIATIVE_TITLE, initiativeTitle);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Fragment onCreate
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        //read parameters
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
        return inflater.inflate(R.layout.fragment_show_opened_deliverables, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);


        //Initialize List View
        //
        // 1. pass context and data to the custom adapter
        adapter = new DeliverablesAdapter(getActivity().getApplicationContext(), getDeliverableArrayList(mInitiativeId));
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


                //Intent
                Intent intent = new Intent(getActivity(), DeliverableDetailsActivity.class);

                //Intent Parameter
                TextView deliverableId = (TextView) view.findViewById(R.id.deliverableIdTextView); //view list item is received as a parameter

                Bundle extras = new Bundle();
                extras.putString(DeliverableDetailsActivity.EXTRA_DELIVERABLE_ID, deliverableId.getText().toString());
                intent.putExtras(extras);

                //Start Intent
                startActivityForResult(intent, SHOW_DELIVERABLE_DETAILS_FOR_ACTION_INTENT_CALL);

            }
        });



    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*
        if (context instanceof ShowOpenedDeliverablesFragment.OnFragmentInteractionListener) {
            mListener = (ShowOpenedDeliverablesFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        */
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
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String param);
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
        deliverableVoList = deliverableDAO.selectDeliverablesByInitiativeId(initiativeId);

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
