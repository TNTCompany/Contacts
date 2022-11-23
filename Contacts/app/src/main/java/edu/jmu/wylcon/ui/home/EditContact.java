package edu.jmu.wylcon.ui.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;

import android.content.Context;
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

public class EditContact extends Fragment {

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
        View viewSec = inflater.inflate(R.layout.contact_edit, container,false);
        return viewSec;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //todo





        /* 返回按钮 */
        Button back = (Button) view.findViewById(R.id.ebutton7);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(EditContact.this).navigateUp();
            }
        });

        EditText nm = (EditText) getView().findViewById(R.id.eed1);
        EditText ph = (EditText) getView().findViewById(R.id.eed);
        EditText email = (EditText) getView().findViewById(R.id.eed8);
        EditText stamp = (EditText) getView().findViewById(R.id.eed2);
        EditText prov = (EditText) getView().findViewById(R.id.eed3);
        EditText city = (EditText) getView().findViewById(R.id.eed4);
        EditText addr = (EditText) getView().findViewById(R.id.eed5);
        EditText group = (EditText) getView().findViewById(R.id.eed6);
        EditText expl = (EditText) getView().findViewById(R.id.eed7);


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

        EditText searchID = (EditText)getView().findViewById(R.id.editID);



        Button searchButton = (Button) view.findViewById(R.id.button8);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sid = searchID.getText().toString();
                if(sid.equals("")||sid.contains(" ")||sid.contains(",")){
                    new AlertDialog.Builder(getContext())
                            .setTitle("错误")
                            .setMessage("\n输入的 ID 有误！\n请重新输入！\n")
                            .setPositiveButton("确定", null).show();
                    return;
                }

                try{
                    if(Integer.parseInt(sid)<=0){
                        new AlertDialog.Builder(getContext())
                                .setTitle("错误")
                                .setMessage("\n输入的 ID 有误！\n请重新输入！\n")
                                .setPositiveButton("确定", null).show();
                        return;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                Set<Integer> idL = o.getIdList();
                int tid = -1;
                for(int i:idL){
                    try{
                        if(i==Integer.parseInt(sid)){
                            tid=i;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                if(tid==-1){
                    new AlertDialog.Builder(getContext())
                            .setTitle("错误")
                            .setMessage("\n输入的 ID 不存在或有误！\n请重新输入！\n")
                            .setPositiveButton("确定", null).show();
                    return;
                }

                searchID.setFocusable(false);
                //searchID.setActivated(false);
                searchID.setClickable(false);
                searchID.setCursorVisible(false);
                searchButton.setEnabled(false);

                Operate o = new Operate(getContext());

                Person p = o.getPerson(tid);

                nm.setText(p.getName());

                ArrayList<String>phL = p.getPhoneNumber().getPhL();
                String phS = "";
                int l = phL.size();
                for(int i=0; i<l-1; i++){
                    phS+=phL.get(i)+", ";
                }
                if(l>0)phS+=phL.get(l-1);
                ph.setText(phS);


                email.setText(p.getEmail());
                stamp.setText(p.getAddress().getStamp());
                prov.setText(p.getAddress().getProvince());
                city.setText(p.getAddress().getCity());
                addr.setText(p.getAddress().getAddress());

                ArrayList<String>gpL = p.getGroup().getGpL();
                String gpS = "";
                l = gpL.size();
                for(int i=0; i<l-1; i++){
                    gpS+=gpL.get(i)+", ";
                }
                if(l>0)gpS+=gpL.get(l-1);

                group.setText(gpS);
                expl.setText(p.getExplanation());
            }
        });

        //idV.setText(idf);




        Button newcB = (Button) view.findViewById(R.id.ebutton4);
        newcB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //EditText nm = (EditText) getView().findViewById(R.id.ed1);

                Button searchButton = (Button) getView().findViewById(R.id.button8);
                if(searchButton.isEnabled()==true){
                    new AlertDialog.Builder(getContext())
                            .setTitle("错误")
                            .setMessage("\n请先输入联系人 ID！\n")
                            .setPositiveButton("确定", null).show();
                    return;
                }

                int nid=-1;
                try{
                    nid = Integer.parseInt(searchID.getText().toString());
                }catch (Exception e){
                    nid=-1;
                    e.printStackTrace();
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
                PhoneNumber phLo = new PhoneNumber(phLt) ;


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


//                EditText expl = (EditText) getView().findViewById(R.id.ed7);


                if(nid!=-1){
                    Person p = new Person(nid, nm.getText().toString(), phLo, email.getText().toString(), addrLt, gLt, expl.getText().toString());
                    o.saveContact(p);
                    new AlertDialog.Builder(getContext())
                            .setTitle("编辑联系人")
                            .setMessage("\n联系人 "+nm.getText().toString()+" 已完成编辑！\n\n")
                            .setPositiveButton("确定", null).show();
                }else{
                    new AlertDialog.Builder(getContext())
                            .setTitle("error")
                            .setMessage("\n联系人修改失败!!\n\n")
                            .setPositiveButton("确定", null).show();

                }









//                new AlertDialog.Builder(getContext())
//                        .setTitle("新建联系人")
//                        .setMessage(p.toString())
//                        .setPositiveButton("确定", null).show();

                //NavHostFragment.findNavController(NewContact.this).navigate(R.id.action_navigation_newcon_home);
                NavHostFragment.findNavController(EditContact.this).navigateUp();

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
