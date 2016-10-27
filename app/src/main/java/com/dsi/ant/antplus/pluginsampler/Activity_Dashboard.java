/*
This software is subject to the license described in the License.txt file
included with this software distribution. You may not use this file except in compliance
with this license.

Copyright (c) Dynastream Innovations Inc. 2013
All rights reserved.
*/

package com.dsi.ant.antplus.pluginsampler;

import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.dsi.ant.plugins.antplus.pccbase.AntPluginPcc;
import com.dsi.ant.plugins.pluginlib.version.PluginLibVersionInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dashboard 'menu' of available sampler activities
 */
public class Activity_Dashboard extends FragmentActivity
{
    protected ListAdapter mAdapter;
    protected ListView mList;

    //Initialize the list
    @SuppressWarnings("serial") //Suppress warnings about hash maps not having custom UIDs
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            Log.i("ANT+ Plugin Sampler", "Version: " + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (NameNotFoundException e)
        {
            Log.i("ANT+ Plugin Sampler", "Version: " + e.toString());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        List<Map<String,String>> menuItems = new ArrayList<Map<String,String>>();
        menuItems.add(new HashMap<String,String>(){{put("title","Bike Speed and Distance Display");put("desc","Receive from Bike Speed sensors");}});

        SimpleAdapter adapter = new SimpleAdapter(this, menuItems, android.R.layout.simple_list_item_2, new String[]{"title","desc"}, new int[]{android.R.id.text1,android.R.id.text2});
        setListAdapter(adapter);

        try
        {
            ((TextView)findViewById(R.id.textView_PluginSamplerVersion)).setText("Sampler Version: " + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (NameNotFoundException e)
        {
            ((TextView)findViewById(R.id.textView_PluginSamplerVersion)).setText("Sampler Version: ERR");
        }
        ((TextView)findViewById(R.id.textView_PluginLibVersion)).setText("Built w/ PluginLib: " + PluginLibVersionInfo.PLUGINLIB_VERSION_STRING);
        ((TextView)findViewById(R.id.textView_PluginsPkgVersion)).setText("Installed Plugin Version: " + AntPluginPcc.getInstalledPluginsVersionString(this));
    }

    //Launch the appropriate activity/action when a selection is made
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        int j=0;
        if(position == j++)
        {
            Intent i = new Intent(this, Activity_BikeSpeedDistanceSampler.class);
            startActivity(i);
        }
        else
        {
            Toast.makeText(this, "This menu item is not implemented", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Sets the list display to the give adapter
     * @param adapter Adapter to set list display to
     */
    public void setListAdapter(ListAdapter adapter)
    {
        synchronized (this)
        {
            if (mList != null)
                return;
            mAdapter = adapter;
            mList = (ListView)findViewById(android.R.id.list);
            mList.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?>  parent, View v, int position, long id)
                {
                    onListItemClick((ListView)parent, v, position, id);
                }
            });
            mList.setAdapter(adapter);
        }
    }
}
