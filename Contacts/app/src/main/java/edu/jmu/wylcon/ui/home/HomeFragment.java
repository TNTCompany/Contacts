package edu.jmu.wylcon.ui.home;

import android.app.AlertDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.jmu.wylcon.databinding.FragmentHomeBinding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.TreeSet;

import edu.jmu.wylcon.R;
import edu.jmu.wylcon.ui.personc.Address;
import edu.jmu.wylcon.ui.personc.GroupOf;
import edu.jmu.wylcon.ui.personc.Person;
import edu.jmu.wylcon.ui.personc.PhoneNumber;
import edu.jmu.wylcon.ui.Operate;

public class HomeFragment extends Fragment {

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
        View viewSec = inflater.inflate(R.layout.fragment_home, container,false);
        return viewSec;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //todo

        Button newc = (Button) view.findViewById(R.id.button);
        newc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.navigation_newcon);
            }
        });


        Button xiangqing = (Button) view.findViewById(R.id.button3);
        xiangqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //String alllist = "";

                Operate o = new Operate(getContext());

                String data = "";

                Set<Integer> idList= o.getIdList();

                for(int i:idList){
                    data+=o.personToString(o.getPerson(i));
                }


                new AlertDialog.Builder(getContext())
                        .setTitle("联系人列表")
                        .setMessage(data)
                        .setPositiveButton("确定", null).show();
            }
        });


        Button editB = (Button) view.findViewById(R.id.button2);
        editB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.navigation_editcon);
            }
        });

        Button deleteB = (Button) view.findViewById(R.id.button9);
        deleteB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.navigation_deletecon);
            }
        });


        Button hebing = (Button) view.findViewById(R.id.buttonMerge);
        hebing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.navigation_mergecon);

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        Set<Integer> idL= new Operate(getContext()).getIdList();

        TextView num = (TextView) getView().findViewById(R.id.textViewContactNumber);
        num.setText("当前共有 " + idL.size() +" 名联系人");

        //updateUI();

    }

    private void updateUI(){
        new AlertDialog.Builder(getContext())
                .setTitle("title")
                .setMessage("\nhome update\n\n")
                .setPositiveButton("确定", null).show();
    }


}
