package edu.jmu.wylcon.ui.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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

public class NewContact extends Fragment {

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
        View viewSec = inflater.inflate(R.layout.contact_new, container,false);
        return viewSec;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //todo





        /* 返回按钮 */
        Button back = (Button) view.findViewById(R.id.button7);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(NewContact.this).navigateUp();
            }
        });

        EditText nm = (EditText) getView().findViewById(R.id.ed1);
        EditText ph = (EditText) getView().findViewById(R.id.ed);
        EditText email = (EditText) getView().findViewById(R.id.ed8);
        EditText stamp = (EditText) getView().findViewById(R.id.ed2);
        EditText prov = (EditText) getView().findViewById(R.id.ed3);
        EditText city = (EditText) getView().findViewById(R.id.ed4);
        EditText addr = (EditText) getView().findViewById(R.id.ed5);
        EditText group = (EditText) getView().findViewById(R.id.ed6);
        EditText expl = (EditText) getView().findViewById(R.id.ed7);

        nm.setText("");
        ph.setText("");
        email.setText("");
        stamp.setText("");
        prov.setText("");
        city.setText("");
        addr.setText("");
        group.setText("");
        expl.setText("");


        TextView idV = (TextView) getView().findViewById(R.id.textView5);

        int idNow=1;
        Set<Integer> idL = new Operate(getContext()).getIdList();
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



        String idf = String.format("%03d", idNow);
        idV.setText(idf);



        Button newcB = (Button) view.findViewById(R.id.button4);
        newcB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //EditText nm = (EditText) getView().findViewById(R.id.ed1);

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


                Person p = new Person(Integer.parseInt(idV.getText().toString()), nm.getText().toString(), phLo, email.getText().toString(), addrLt, gLt, expl.getText().toString());


//                new AlertDialog.Builder(getContext())
//                        .setTitle("新建联系人")
//                        .setMessage("\n联系人"+nm.getText().toString()+"创建成功！\n\n")
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                NavHostFragment.findNavController(NewContact.this)
//                                        .navigate(R.id.navigation_home);
//                            }
//                        }).show();



                new Operate(getContext()).saveContact(p);


                new AlertDialog.Builder(getContext())
                        .setTitle("新建联系人")
                        .setMessage("\n联系人 "+nm.getText().toString()+" 创建成功！\n\n")
                        .setPositiveButton("确定", null).show();




//                new AlertDialog.Builder(getContext())
//                        .setTitle("新建联系人")
//                        .setMessage(p.toString())
//                        .setPositiveButton("确定", null).show();

                //NavHostFragment.findNavController(NewContact.this).navigate(R.id.action_navigation_newcon_home);
                NavHostFragment.findNavController(NewContact.this).navigateUp();

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

//    public int chkId(){
//        PackageManager manager = getContext().getPackageManager();
//        String packageNameStr = "";
//        try {
//            PackageInfo info = manager.getPackageInfo(getContext().getPackageName(), 0);
//            packageNameStr = info.packageName;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        for(int i=1; i<1000; i++){
//            File ckDir = new File("/data/data/" +packageNameStr+ "/files/"+i);
//            if(!ckDir.exists()){
//                return i;
//            }
//            ckDir=null;
//        }
//
//        return 0;
//    }




}
