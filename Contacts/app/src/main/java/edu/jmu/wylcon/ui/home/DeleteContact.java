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

public class DeleteContact extends Fragment {

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
        View viewSec = inflater.inflate(R.layout.contact_delete, container,false);
        return viewSec;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //todo





        /* 返回按钮 */
        Button back = (Button) view.findViewById(R.id.dbutton7);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(DeleteContact.this).navigateUp();
            }
        });

        EditText nm = (EditText) getView().findViewById(R.id.ded1);
        EditText ph = (EditText) getView().findViewById(R.id.ded);
        EditText email = (EditText) getView().findViewById(R.id.ded8);
        EditText stamp = (EditText) getView().findViewById(R.id.ded2);
        EditText prov = (EditText) getView().findViewById(R.id.ded3);
        EditText city = (EditText) getView().findViewById(R.id.ded4);
        EditText addr = (EditText) getView().findViewById(R.id.ded5);
        EditText group = (EditText) getView().findViewById(R.id.ded6);
        EditText expl = (EditText) getView().findViewById(R.id.ded7);


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

        EditText deleteID = (EditText)getView().findViewById(R.id.deleteID);



        Button deleteButton = (Button) view.findViewById(R.id.dbutton8);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sid = deleteID.getText().toString();
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

                deleteID.setFocusable(false);
                //searchID.setActivated(false);
                deleteID.setClickable(false);
                deleteID.setCursorVisible(false);
                deleteButton.setEnabled(false);

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


                nm.setFocusable(false);
                nm.setClickable(false);
                nm.setCursorVisible(false);

                ph.setFocusable(false);
                ph.setClickable(false);
                ph.setCursorVisible(false);

                email.setFocusable(false);
                email.setClickable(false);
                email.setCursorVisible(false);

                stamp.setFocusable(false);
                stamp.setClickable(false);
                stamp.setCursorVisible(false);

                prov.setFocusable(false);
                prov.setClickable(false);
                prov.setCursorVisible(false);

                city.setFocusable(false);
                city.setClickable(false);
                city.setCursorVisible(false);

                addr.setFocusable(false);
                addr.setClickable(false);
                addr.setCursorVisible(false);

                group.setFocusable(false);
                group.setClickable(false);
                group.setCursorVisible(false);

                expl.setFocusable(false);
                expl.setClickable(false);
                expl.setCursorVisible(false);
                expl.setFocusable(false);
                expl.setClickable(false);
                expl.setCursorVisible(false);



            }
        });

        //idV.setText(idf);




        Button newcB = (Button) view.findViewById(R.id.dbutton4);
        newcB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //EditText nm = (EditText) getView().findViewById(R.id.ed1);

                Button searchButton = (Button) getView().findViewById(R.id.dbutton8);
                if(searchButton.isEnabled()==true){
                    new AlertDialog.Builder(getContext())
                            .setTitle("错误")
                            .setMessage("\n请先输入联系人 ID！\n")
                            .setPositiveButton("确定", null).show();
                    return;
                }

                int nid=-1;
                try{
                    nid = Integer.parseInt(deleteID.getText().toString());
                }catch (Exception e){
                    nid=-1;
                    e.printStackTrace();
                }

                if(nid==-1){
                    new AlertDialog.Builder(getContext())
                            .setTitle("error")
                            .setMessage("\n联系人删除失败!!\n\n")
                            .setPositiveButton("确定", null).show();
                    return;
                }

                new AlertDialog.Builder(getContext())
                        .setTitle("确认")
                        .setMessage("\n是否确定要删除联系人 "+nm.getText().toString()+" ?\n")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                int nid=-1;
                                try{
                                    nid = Integer.parseInt(deleteID.getText().toString());
                                }catch (Exception e){
                                    nid=-1;
                                    e.printStackTrace();
                                }

                                if(nid==-1)return;

                                Operate o = new Operate(getContext());
                                Set<Integer> idL = o.getIdList();

                                idL.remove(nid);

                                o.setIdList(idL);

                                new AlertDialog.Builder(getContext())
                                        .setTitle("删除联系人")
                                        .setMessage("\n联系人 "+nm.getText().toString()+" 已删除！\n\n")
                                        .setPositiveButton("确定", null).show();

                                NavHostFragment.findNavController(DeleteContact.this).navigateUp();

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
