package edu.jmu.wylcon.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import edu.jmu.wylcon.MainActivity;
import edu.jmu.wylcon.R;
import edu.jmu.wylcon.ui.personc.*;

public class Operate {
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public Operate(Context context) {
        this.context = context;
    }



    public Person getPerson(int id){

        //说明： Must guarantee that the id is available. (Not a deleted ID!!)
        //该函数不会主动判断id是否为已删除的。

        String name = fileR(id+"", "name.txt");

        String phoneSource = fileR(id+"", "phone.txt");
        String[] phoneArr = phoneSource.split("\n");
        ArrayList<String> phL = new ArrayList<>();
        for(String i:phoneArr){
            phL.add(i);
        }
        PhoneNumber phoneNumber = new PhoneNumber(phL);

        String email = fileR(id+"", "email.txt");

        String addString = fileR(id+"", "address.txt");
        String[] addArr = addString.split("\n");
        Address address = new Address("", "", "", "");
        try{
            address = new Address(addArr[0], addArr[1], addArr[2], addArr[3]);
        }catch (Exception e){
            e.printStackTrace();
        }

        String gpString = fileR(id+"", "group.txt");
        String[] gpArr = gpString.split("\n");
        ArrayList<String> gpL = new ArrayList<>();
        for(String i:gpArr){
            gpL.add(i);
        }
        GroupOf gp = new GroupOf(gpL);

        String exp = fileR(id+"", "explanation.txt");

        Person tmp = new Person(id, name, phoneNumber, email, address, gp, exp);
        return tmp;
    }

    public String personToString(Person person){

        String alllist="\n";

        alllist += "ID: "+person.getId() +"\n";
        alllist += "姓名: "+person.getName()+"\n";

        ArrayList<String> phoneL = person.getPhoneNumber().getPhL();
        String phones ="";
        for(String ph:phoneL){
            phones+=ph+"\n";
        }
        alllist+="电话列表:\n"+phones+"\n";

        alllist+="邮箱: "+person.getEmail()+"\n";

        alllist+="----------------\n[地址]\n"
                + "邮编: "+person.getAddress().getStamp() +"\n"
                + "省份: "+person.getAddress().getProvince()+"\n"
                + "城市: "+person.getAddress().getCity()+"\n"
                + "住址: "+person.getAddress().getAddress()+"\n----------------\n";

        ArrayList<String> gpL = person.getGroup().getGpL();
        String groups ="";
        for(String gp:gpL){
            groups+=gp+"\n";
        }
        alllist+="隶属群组:\n"+groups+"\n";

        alllist+="备注: "+person.getExplanation()+"\n";

        alllist+="============================\n";

        return alllist;

    }




    public Set<Integer> getIdList(){
        String idLStr = fileR("", "idlist.txt");
        String[] idLArr = idLStr.split(" ");
        Set<Integer> idL = new TreeSet<>();
        for(String i:idLArr){
            try{
                idL.add(Integer.parseInt(i));
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return idL;
    }


    public void setIdList(Set<Integer> idList){
        String data="";
        for(int i:idList){
            data+=i+" ";
        }
        fileW("","idlist.txt", data);
    }


    public void saveContact(Person p){

//        fileW("folder","ioioi.txt", "1");

        int id = p.getId();

        Set<Integer> idL = getIdList();
        idL.add(id);
        setIdList(idL);

        String folder = String.valueOf(id);

        String name = p.getName();
        fileW(folder, "name.txt", name);

        PhoneNumber pN = p.getPhoneNumber();
        ArrayList<String>phL = pN.getPhL();
        String phData="";
        for(String phone:phL){
            phData+=(phone.trim()+"\n");
        }
        fileW(folder, "phone.txt", phData);


        String email = p.getEmail();
        fileW(folder, "email.txt", email);

        Address add = p.getAddress();
        String addData = add.getStamp() +"\n"
                +add.getProvince()+"\n"
                +add.getCity()+"\n"
                +add.getAddress();
        fileW(folder, "address.txt", addData);


        GroupOf gp = p.getGroup();
        ArrayList<String>gpL = gp.getGpL();
        String gpData = "";
        for(String g:gpL){
            gpData+=(g.toString().trim()+"\n");
        }
        fileW(folder, "group.txt", gpData);

        String beizhu = p.getExplanation();
        fileW(folder, "explanation.txt", beizhu);



//        new AlertDialog.Builder(getContext())
//                .setMessage(getContext().getFilesDir().getAbsolutePath().toString())
//                .setPositiveButton("确定", null).show();


    }

    @SuppressLint("SdCardPath")
    public void fileW(String folderName, String fileName, String data){
        try{
            //FileOutputStream fout = getContext().openFileOutput(fileName+".txt", Context.MODE_PRIVATE);
            //FileOutputStream fout = getActivity().openFileOutput(getContext().getFilesDir().getAbsolutePath().toString()+"/"+fileName+".txt", Context.MODE_PRIVATE);
            PackageManager manager = context.getApplicationContext().getPackageManager();
            String packageNameStr = "";
            try {
                PackageInfo info = manager.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
                packageNameStr = info.packageName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            if(!folderName.equals("")){
                File mkdir = new File("/data/data/" +packageNameStr+ "/files/"+folderName);

                if(!mkdir.exists()){
                    mkdir.mkdirs();
                }

                mkdir=null;
            }else{
                File mkdir = new File("/data/data/" +packageNameStr+ "/files");

                if(!mkdir.exists()){
                    mkdir.mkdirs();
                }

                mkdir=null;
            }

            File file = null;

            if(folderName.equals("")){
                file = new File("/data/data/" +packageNameStr+ "/files/"+fileName);
            }else{
                file = new File("/data/data/" +packageNameStr+ "/files/"+folderName+"/"+fileName);
            }

//            if(!file.exists()){
//                file.mkdirs();
//            }

            FileOutputStream fout = new FileOutputStream(file);
            byte[] bytes = data.getBytes();
            fout.write(bytes);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // getContext().getFilesDir().getAbsolutePath().toString()+"/"+fileName+".txt"


    @SuppressLint("SdCardPath")
    public String fileR(String folderName, String fileName){
        String res="";
        try{
            //FileInputStream fin = getActivity().openFileInput(getContext().getFilesDir().getAbsolutePath().toString()+"/"+fileName+".txt");
            //FileInputStream fin = getActivity().openFileInput(fileName + ".txt");

            PackageManager manager = context.getApplicationContext().getPackageManager();
            String packageNameStr = "";
            try {
                PackageInfo info = manager.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
                packageNameStr = info.packageName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            File file = null;
            if(folderName.equals("")){
                file = new File("/data/data/" +packageNameStr+ "/files/"+fileName);
            }else{
                file = new File("/data/data/" +packageNameStr+ "/files/"+folderName +"/" +fileName);
            }
            FileInputStream fin = new FileInputStream(file);

            int length = fin.available();
            byte [] buffer = new byte[length];
            fin.read(buffer);
            //res = EncodingUtils.getString(buffer, "UTF-8");
            res = new String(buffer, "UTF-8");
            fin.close();
        }
        catch(Exception e){
            e.printStackTrace();
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            String str = sw.toString();
            //System.out.println("==========");

            //System.out.println(str);
            //Log.d(Environment.getDataDirectory().toString());
        /*new AlertDialog.Builder(getContext())
                .setMessage(str)
                .setPositiveButton("确定", null).show();*/
        }
        return res;
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
