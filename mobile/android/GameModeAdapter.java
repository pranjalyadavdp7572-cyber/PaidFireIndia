package com.pranjalfreefireindia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class GameModeAdapter extends BaseAdapter {
    
    private Context context;
    private List<AppManagerActivity.GameModePackage> gameModules;
    private LayoutInflater inflater;
    
    public GameModeAdapter(Context context, List<AppManagerActivity.GameModePackage> gameModules) {
        this.context = context;
        this.gameModules = gameModules;
        this.inflater = LayoutInflater.from(context);
    }
    
    @Override
    public int getCount() {
        return gameModules.size();
    }
    
    @Override
    public Object getItem(int position) {
        return gameModules.get(position);
    }
    
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_game_mode, parent, false);
            holder = new ViewHolder();
            holder.nameText = convertView.findViewById(R.id.module_name);
            holder.descriptionText = convertView.findViewById(R.id.module_description);
            holder.sizeText = convertView.findViewById(R.id.module_size);
            holder.progressBar = convertView.findViewById(R.id.download_progress);
            holder.actionButton1 = convertView.findViewById(R.id.action_button_1);
            holder.actionButton2 = convertView.findViewById(R.id.action_button_2);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        AppManagerActivity.GameModePackage module = gameModules.get(position);
        
        holder.nameText.setText(module.name);
        holder.descriptionText.setText(module.description);
        holder.sizeText.setText(module.sizeStr);
        
        if (module.isInstalled) {
            holder.progressBar.setVisibility(View.GONE);
            holder.actionButton1.setText("⚙️ Settings");
            holder.actionButton1.setOnClickListener(v -> 
                    Toast.makeText(context, "Settings for " + module.name, Toast.LENGTH_SHORT).show());
            holder.actionButton2.setText("❌ Uninstall");
            holder.actionButton2.setOnClickListener(v -> {
                module.isInstalled = false;
                notifyDataSetChanged();
                Toast.makeText(context, module.name + " uninstalled", Toast.LENGTH_SHORT).show();
            });
        } else {
            holder.progressBar.setVisibility(View.VISIBLE);
            holder.progressBar.setProgress(module.downloadProgress);
            holder.actionButton1.setText("⬇️ Install");
            holder.actionButton1.setOnClickListener(v -> {
                module.isInstalled = true;
                notifyDataSetChanged();
                Toast.makeText(context, module.name + " installed", Toast.LENGTH_SHORT).show();
            });
            holder.actionButton2.setText("ℹ️ Info");
            holder.actionButton2.setOnClickListener(v -> 
                    Toast.makeText(context, module.description, Toast.LENGTH_LONG).show());
        }
        
        return convertView;
    }
    
    private static class ViewHolder {
        TextView nameText, descriptionText, sizeText;
        ProgressBar progressBar;
        Button actionButton1, actionButton2;
    }
}
