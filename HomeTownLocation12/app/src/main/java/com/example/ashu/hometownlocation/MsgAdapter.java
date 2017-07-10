package com.example.ashu.hometownlocation;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * Created by ashu on 16/4/17.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.MsgListHolder> {

    private ArrayList<Chat> chatList;

    class MsgListHolder extends RecyclerView.ViewHolder {
        TextView msgText;

        MsgListHolder(View view) {
            super(view);
            msgText = (TextView) view.findViewById(R.id.msgText);

        }


    }
    MsgAdapter(ArrayList<Chat> chatList )
    {
        this.chatList = chatList;
    }

    @Override
    public MsgAdapter.MsgListHolder onCreateViewHolder (ViewGroup vg, int viewType )
    {
        LayoutInflater layoutInflater = LayoutInflater.from(vg.getContext());
        View view = layoutInflater.inflate(R.layout.chat_recycle_layout, vg, false);
        return new MsgAdapter.MsgListHolder(view);
    }

    @Override
    public void onBindViewHolder(MsgAdapter.MsgListHolder holder, int position)
    {
        Chat chat = chatList.get(position);
        String uemail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Log.i("user email", uemail);
        Log.i("sender email", chat.senderUid);
        Log.i("position", Integer.toString(position));
        if (TextUtils.equals(uemail, chat.senderUid))
        {
            holder.msgText.setGravity(Gravity.END);
        }

        Log.i("msg", chat.message);
        holder.msgText.setText(chat.message);
    }

    @Override
    public int getItemCount()
    {
        return chatList.size();
    }

}
