package edu.jmu.wylcon.ui.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Path;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.navigation.fragment.NavHostFragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Set;

import edu.jmu.wylcon.R;
import edu.jmu.wylcon.ui.personc.*;
import edu.jmu.wylcon.ui.Operate;

public class MergeContact extends Fragment {

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
        View viewSec = inflater.inflate(R.layout.contact_merge, container,false);
        return viewSec;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //todo





        /* 返回按钮 */
        Button back = (Button) view.findViewById(R.id.mbutton7);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MergeContact.this).navigateUp();
            }
        });

        EditText nm = (EditText) getView().findViewById(R.id.med1);
        EditText ph = (EditText) getView().findViewById(R.id.med);
        EditText email = (EditText) getView().findViewById(R.id.med8);
        EditText stamp = (EditText) getView().findViewById(R.id.med2);
        EditText prov = (EditText) getView().findViewById(R.id.med3);
        EditText city = (EditText) getView().findViewById(R.id.med4);
        EditText addr = (EditText) getView().findViewById(R.id.med5);
        EditText group = (EditText) getView().findViewById(R.id.med6);
        EditText expl = (EditText) getView().findViewById(R.id.med7);


        nm.setText("");
        ph.setText("");
        email.setText("");
        stamp.setText("");
        prov.setText("");
        city.setText("");
        addr.setText("");
        group.setText("");
        expl.setText("");



        Operate o = new Operate(getContext());

        EditText mergeName = (EditText)getView().findViewById(R.id.mergeName);



        Button searchButton = (Button) view.findViewById(R.id.mbutton8);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String snm = mergeName.getText().toString();
                if(snm.equals("")){
                    new AlertDialog.Builder(getContext())
                            .setTitle("错误")
                            .setMessage("\n输入的姓名有误！\n请重新输入！\n")
                            .setPositiveButton("确定", null).show();
                    return;
                }

                Operate o = new Operate(getContext());



                Set<Integer> idL = o.getIdList();
                ArrayList<Integer>sameNameL = new ArrayList<>();
                String out = "[";

                for(int i:idL){
                    if(o.getPerson(i).getName().equals(snm)){
                        sameNameL.add(i);
                        out+=i+" ";
                    }
                }
                out+="]";

                if(sameNameL.size()<=1){
                    new AlertDialog.Builder(getContext())
                            .setTitle("错误")
                            .setMessage("\n该姓名的联系人未找到或仅有一个！\n请重新输入！\n")
                            .setPositiveButton("确定", null).show();
                    return;
                }


                mergeName.setFocusable(false);
                //searchID.setActivated(false);
                mergeName.setClickable(false);
                mergeName.setCursorVisible(false);
                searchButton.setEnabled(false);

                //Operate o = new Operate(getContext());


                new AlertDialog.Builder(getContext())
                        .setTitle("查找完毕")
                        .setMessage("\n找到 ID 为\n"+out+"\n的联系人姓名相同。\n")
                        .setPositiveButton("确定", null).show();



                int id0 = sameNameL.get(0);

                Person p0 = o.getPerson(id0);
                String name = p0.getName();
                nm.setText(name);


                String phS = "";
                ArrayList<String>phL0 = p0.getPhoneNumber().getPhL();
                int l = phL0.size();
                for(int i=0; i<l-1; i++){
                    phS+=phL0.get(i)+", ";
                }
                if(l>0)phS+=phL0.get(l-1);
                phL0=null;
               // ph.setText(phS);


                String emailS = p0.getEmail();
                String stampS = p0.getAddress().getStamp();
                String provS = p0.getAddress().getProvince() ;
                String cityS = p0.getAddress().getCity();
                String addS = p0.getAddress().getAddress();

                String groupS = "";
                ArrayList<String>gpL0 = p0.getGroup().getGpL();
                l = gpL0.size();
                for(int i=0; i<l-1; i++){
                    groupS+=gpL0.get(i)+", ";
                }
                if(l>0)groupS+=gpL0.get(l-1);
                gpL0=null;


                String expS = p0.getExplanation();


                for(int i=1; i<sameNameL.size(); i++){
                    int idi = sameNameL.get(i);
                    Person p = o.getPerson(idi);
                    name += ", "+p.getName();

                    ArrayList<String>phLi = p.getPhoneNumber().getPhL();
                    l = phLi.size();
                    if(l>0)phS+=", ";
                    for(int j=0; j<l-1; j++){
                        phS+=phLi.get(j)+", ";
                    }
                    if(l>0)phS+=phLi.get(l-1);

                    if(emailS.equals(""))emailS += p.getEmail().equals("")?"":p.getEmail();
                    else emailS += p.getEmail().equals("")?"":", "+p.getEmail();

                    if(stampS.equals(""))stampS += p.getAddress().getStamp().equals("")?"":p.getAddress().getStamp();
                    else stampS += p.getAddress().getStamp().equals("")?"":", "+p.getAddress().getStamp();

                    if(provS.equals(""))provS += p.getAddress().getProvince().equals("")?"":p.getAddress().getProvince();
                    else provS += p.getAddress().getProvince().equals("")?"":", "+p.getAddress().getProvince();

                    if(cityS.equals(""))cityS += p.getAddress().getCity().equals("")?"":p.getAddress().getCity();
                    else cityS += p.getAddress().getCity().equals("")?"":", "+p.getAddress().getCity();

                    if(addS.equals(""))addS += p.getAddress().getAddress().equals("")?"":p.getAddress().getAddress();
                    else addS += p.getAddress().getAddress().equals("")?"":", "+p.getAddress().getAddress();

                    ArrayList<String>gpLi = p.getGroup().getGpL();
                    l = gpLi.size();
                    if(l>0)groupS+=", ";
                    for(int j=0; j<l-1; j++){
                        groupS+=gpLi.get(j)+", ";
                    }
                    if(l>0)groupS+=gpLi.get(l-1);

                    if(expS.equals(""))expS+=p.getExplanation().equals("")?"":p.getExplanation();
                    else expS+=p.getExplanation().equals("")?"":", "+p.getExplanation();

                }



                ph.setText(phS);
                email.setText(emailS);
                stamp.setText(stampS);
                prov.setText(provS);
                city.setText(cityS);
                addr.setText(addS);
                group.setText(groupS);
                expl.setText(expS);
            }
        });

        //idV.setText(idf);




        Button newcB = (Button) view.findViewById(R.id.mbutton4);
        newcB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //EditText nm = (EditText) getView().findViewById(R.id.ed1);

                Button searchButton = (Button) getView().findViewById(R.id.mbutton8);
                if(searchButton.isEnabled()==true){
                    new AlertDialog.Builder(getContext())
                            .setTitle("错误")
                            .setMessage("\n请先搜索姓名！\n")
                            .setPositiveButton("确定", null).show();
                    return;
                }



                if(nm.getText().toString().equals("")){
                    new AlertDialog.Builder(getContext())
                            .setTitle("错误")
                            .setMessage("\n联系人姓名不能为空！\n")
                            .setPositiveButton("确定", null).show();
                    return;
                }


//                EditText ph = (EditText) getView().findViewById(R.id.ed);

                String[] phones = ph.getText().toString().split(",");
                ArrayList<String>phLt = new ArrayList<>();
                for(String phN:phones){
                    try{
                        phLt.add(phN);
                    }catch(NumberFormatException e){
                        e.printStackTrace();
                    }
                }
                PhoneNumber phLo = new PhoneNumber(phLt);


//                EditText email = (EditText) getView().findViewById(R.id.eed8);


//                EditText stamp = (EditText) getView().findViewById(R.id.ed2);
//                EditText prov = (EditText) getView().findViewById(R.id.ed3);
//                EditText city = (EditText) getView().findViewById(R.id.ed4);
//                EditText addr = (EditText) getView().findViewById(R.id.ed5);
                Address addrLt =  new Address(stamp.getText().toString(), prov.getText().toString(), city.getText().toString(), addr.getText().toString());



//                EditText group = (EditText) getView().findViewById(R.id.ed6);
                String[] groups = group.getText().toString().split(",");
                ArrayList<String>gL = new ArrayList<>();
                for(String g:groups){
                    try{
                        gL.add(g);
                    }catch(NumberFormatException e){
                        e.printStackTrace();
                    }
                }
                GroupOf gLt = new GroupOf(gL);



                new AlertDialog.Builder(getContext())
                        .setTitle("确认")
                        .setMessage("\n是否确定要合并联系人 "+nm.getText().toString()+" ?\n合并后，将建立新的联系人，并删除目前的重名联系人！\n")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int ii) {

                                Operate o = new Operate(getContext());
                                Set<Integer> idL = o.getIdList();
                                ArrayList<Integer>sameNameL = new ArrayList<>();

                                EditText nm = (EditText) getView().findViewById(R.id.med1);
                                EditText ph = (EditText) getView().findViewById(R.id.med);
                                EditText email = (EditText) getView().findViewById(R.id.med8);
                                EditText stamp = (EditText) getView().findViewById(R.id.med2);
                                EditText prov = (EditText) getView().findViewById(R.id.med3);
                                EditText city = (EditText) getView().findViewById(R.id.med4);
                                EditText addr = (EditText) getView().findViewById(R.id.med5);
                                EditText group = (EditText) getView().findViewById(R.id.med6);
                                EditText expl = (EditText) getView().findViewById(R.id.med7);

                                if(nm.getText().toString().equals("")){
                                    new AlertDialog.Builder(getContext())
                                            .setTitle("错误")
                                            .setMessage("\n联系人姓名不能为空！\n")
                                            .setPositiveButton("确定", null).show();
                                    return;
                                }


                                String snm = mergeName.getText().toString();

                                for(int i:idL){
                                    if(o.getPerson(i).getName().equals(snm)){
                                        sameNameL.add(i);
                                    }
                                }

                                for(int i:sameNameL)
                                    idL.remove(i);

                                o.setIdList(idL);
// -------------------------------------------------------------------------------------------------

                                String[] phones = ph.getText().toString().split(",");
                                ArrayList<String>phLt = new ArrayList<>();
                                for(String phN:phones){
                                    try{
                                        phLt.add(phN);
                                    }catch(NumberFormatException e){
                                        e.printStackTrace();
                                    }
                                }
                                PhoneNumber phLo = new PhoneNumber(phLt);

                                Address addrLt =  new Address(stamp.getText().toString(), prov.getText().toString(), city.getText().toString(), addr.getText().toString());

                                String[] groups = group.getText().toString().split(",");
                                ArrayList<String>gL = new ArrayList<>();
                                for(String g:groups){
                                    try{
                                        gL.add(g);
                                    }catch(NumberFormatException e){
                                        e.printStackTrace();
                                    }
                                }
                                GroupOf gLt = new GroupOf(gL);

                                int idNow=1;

                                if(idL.size()==0){
                                    idNow=1;
                                }else{
                                    for(int i=1; i<1000; i++){
                                        if(!idL.contains(i)){
                                            idNow=i;
                                            break;
                                        }
                                    }
                                }

                                Person p = new Person(idNow, nm.getText().toString(), phLo, email.getText().toString(), addrLt, gLt, expl.getText().toString());

                                new Operate(getContext()).saveContact(p);



                                new AlertDialog.Builder(getContext())
                                        .setTitle("合并联系人")
                                        .setMessage("\n联系人 "+nm.getText().toString()+" 已完成合并！\n新的 ID 是: "+idNow+"\n")
                                        .setPositiveButton("确定", null).show();

                                NavHostFragment.findNavController(MergeContact.this).navigateUp();

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        })
                        .show();



            }
        });



    }

    @Override
    public void onResume() {
        super.onResume();

        //updateUI();

    }

    private void updateUI(){
        new AlertDialog.Builder(getContext())
                .setTitle("title")
                .setMessage("new contact\n\n")
                .setPositiveButton("确定", null).show();
    }


}
