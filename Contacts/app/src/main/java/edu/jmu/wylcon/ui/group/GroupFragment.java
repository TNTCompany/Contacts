package edu.jmu.wylcon.ui.group;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import edu.jmu.wylcon.R;
import edu.jmu.wylcon.ui.Operate;
import edu.jmu.wylcon.ui.personc.*;


public class GroupFragment extends Fragment {

    private boolean isFirstLoad = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View viewSec = inflater.inflate(R.layout.fragment_group, container,false);
        return viewSec;
        //return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ImageView hp = (ImageView) getView().findViewById(R.id.imageView11);

    }

    @Override
    public void onResume() {
        super.onResume();

        TextView gpText = (TextView) getView().findViewById(R.id.textViewGroup);
        Operate o = new Operate(getContext());

        String data = "";

        Set<String> allGPL = new TreeSet<>();

        Set<Integer>idL = o.getIdList();

        ArrayList<Person> allpL = new ArrayList<>();
        for(int i:idL){
            allpL.add(o.getPerson(i));
        }


        for(Person p:allpL){
            ArrayList<String> gpLi = p.getGroup().getGpL();
            for(String j:gpLi){
                allGPL.add(j);
            }
        }

        if(allGPL.size()==0){
            gpText.setText("当前暂无群组数据！");
            return;
        }

        else{
            data+="当前共有 "+allGPL.size()+" 个群组。\n\n";

            Map<String, PersonList> m = new TreeMap<>();

            for(String i:allGPL){

                Set<Person> personList_i = new TreeSet<>();

                for(Person p:allpL){
                    ArrayList<String>gpLtmp = p.getGroup().getGpL();

                    for(String gpName:gpLtmp){
                        if(i.equals(gpName)){
                            try{
                                personList_i.add(p);
                            }catch (Exception e){
                                e.printStackTrace();

                            }
                        }
                    }
                }

                PersonList personList = new PersonList(personList_i);
                m.put(i, personList);
            }

            for(String gpName_i : m.keySet()){
                data+="[组名] "+gpName_i+"\n";
                data+="[成员]\n";
                PersonList pLO = m.get(gpName_i);
                Set<Person> pL = pLO.getpL();

                for(Person i:pL){
                    data+="\tID: "+i.getId()+", \t姓名: "+i.getName()+";\n";
                }
                data+="\n";
            }

            gpText.setText(data);


        }


    }



}

class PersonList{
    private Set<Person> pL = new TreeSet<>();

    public PersonList(Set<Person> pL) {
        this.pL = pL;
    }

    public Set<Person> getpL() {
        return pL;
    }

    public void setpL(Set<Person> pL) {
        this.pL = pL;
    }

}
