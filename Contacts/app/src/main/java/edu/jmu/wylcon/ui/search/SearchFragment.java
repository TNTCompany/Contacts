package edu.jmu.wylcon.ui.search;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import edu.jmu.wylcon.R;
import edu.jmu.wylcon.ui.Operate;
import edu.jmu.wylcon.ui.personc.Person;

public class SearchFragment extends Fragment {

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
        View viewSec = inflater.inflate(R.layout.fragment_search, container,false);
        return viewSec;
        //return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText S = (EditText)view.findViewById(R.id.editSearchInput);

        Button sBName = (Button) view.findViewById(R.id.buttonSearchFragment1);
        Button sBPhone = (Button) view.findViewById(R.id.buttonSearchFragment2);
        Button sBID = (Button) view.findViewById(R.id.buttonSearchFragment3);
        Button sBAll = (Button) view.findViewById(R.id.buttonSearchFragment4);

        Operate o = new Operate(getContext());
        Set<Integer>idL = o.getIdList();

        sBName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = S.getText().toString();
                s=s.trim();
                if(s.equals("")){
                    new AlertDialog.Builder(getContext())
                            .setTitle("错误")
                            .setMessage("\n输入为空！\n")
                            .setPositiveButton("确定", null).show();
                    return;
                }
                String data="";
                for(int i:idL){
                    if(o.getPerson(i).getName().contains(s)){
                        data+=o.personToString(o.getPerson(i));
                    }
                }
                if(data.equals("")){
                    new AlertDialog.Builder(getContext())
                            .setTitle("搜索完成")
                            .setMessage("\n未找到符合条件的联系人。\n")
                            .setPositiveButton("确定", null).show();
                    return;
                }

                new AlertDialog.Builder(getContext())
                        .setTitle("搜索完成")
                        .setMessage("\n"+data+"\n")
                        .setPositiveButton("确定", null).show();
                return;
            }
        });

        sBPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = S.getText().toString();
                s=s.trim();
                if(s.equals("")){
                    new AlertDialog.Builder(getContext())
                            .setTitle("错误")
                            .setMessage("\n输入为空！\n")
                            .setPositiveButton("确定", null).show();
                    return;
                }
                if(s.contains(",")){
                    new AlertDialog.Builder(getContext())
                            .setTitle("错误")
                            .setMessage("\n只能搜索一个电话!\n")
                            .setPositiveButton("确定", null).show();
                    return;
                }

                Set<Person> pL= new TreeSet<>();
                for(int i:idL){
                    Person p = o.getPerson(i);
                    ArrayList<String>phL = p.getPhoneNumber().getPhL();
                    for(String j:phL){
                        if(j.contains(s)){
                            pL.add(p);
                        }
                    }

                }

                if(pL.size()==0){
                    new AlertDialog.Builder(getContext())
                            .setTitle("搜索完成")
                            .setMessage("\n未找到符合条件的联系人。\n")
                            .setPositiveButton("确定", null).show();
                    return;
                }

                String data="";
                for(Person p:pL){
                    data+=o.personToString(p);
                }

                new AlertDialog.Builder(getContext())
                        .setTitle("搜索完成")
                        .setMessage("\n"+data+"\n")
                        .setPositiveButton("确定", null).show();
                return;

            }
        });


        sBID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = S.getText().toString();
                s=s.trim();
                if(s.equals("")){
                    new AlertDialog.Builder(getContext())
                            .setTitle("错误")
                            .setMessage("\n输入为空！\n")
                            .setPositiveButton("确定", null).show();
                    return;
                }
                int x=-1;
                try{
                    x=Integer.parseInt(s);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(x==-1){
                    new AlertDialog.Builder(getContext())
                            .setTitle("错误")
                            .setMessage("\nID 输入错误！\n")
                            .setPositiveButton("确定", null).show();
                    return;
                }

                for(int i:idL){
                    if(i==x){
                        String data = o.personToString(o.getPerson(i));
                        new AlertDialog.Builder(getContext())
                                .setTitle("查找成功!")
                                .setMessage("\n"+data+"\n")
                                .setPositiveButton("确定", null).show();
                        return;
                    }
                }


                new AlertDialog.Builder(getContext())
                        .setTitle("搜索完成")
                        .setMessage("\n未找到符合条件的联系人。\n")
                        .setPositiveButton("确定", null).show();
                return;

            }
        });

        sBAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = S.getText().toString();
                s=s.trim();
                if(s.equals("")){
                    new AlertDialog.Builder(getContext())
                            .setTitle("错误")
                            .setMessage("\n输入为空！\n")
                            .setPositiveButton("确定", null).show();
                    return;
                }

                Set<Person> pL = new TreeSet<>();

                for(int i:idL){
                    String name = o.fileR(i+"", "name.txt");
                    String phone = o.fileR(i+"", "phone.txt");
                    String email = o.fileR(i+"", "email.txt");
                    String address = o.fileR(i+"", "address.txt");
                    String group = o.fileR(i+"", "group.txt");
                    String explanation = o.fileR(i+"", "explanation.txt");

                    if(name.contains(s)||phone.contains(s)||email.contains(s)||address.contains(s)||group.contains(s)||explanation.contains(s)){
                        pL.add(o.getPerson(i));
                    }
                }

                if(pL.size()==0){
                    new AlertDialog.Builder(getContext())
                            .setTitle("搜索完成")
                            .setMessage("\n未找到符合条件的联系人。\n")
                            .setPositiveButton("确定", null).show();
                    return;
                }

                String data="";
                for(Person p:pL){
                    data+=o.personToString(p);
                }

                new AlertDialog.Builder(getContext())
                        .setTitle("搜索完成")
                        .setMessage("\n"+data+"\n")
                        .setPositiveButton("确定", null).show();
                return;

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
                .setMessage("\nsearch update\n\n")
                .setPositiveButton("确定", null).show();
    }

}
