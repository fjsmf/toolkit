/*
This file is part of the project TraceroutePing, which is an Android library
implementing Traceroute with ping under GPL license v3.
Copyright (C) 2013  Olivier Goutay

TraceroutePing is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

TraceroutePing is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with TraceroutePing.  If not, see <http://www.gnu.org/licenses/>.
 */

package ss.com.toolkit.ui.wechat;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import ss.com.toolkit.R;

public class WechatListActivity extends Activity {
	@BindView(R.id.chat_list)
	RecyclerView chat_list;

	RecyclerView.Adapter adapter;
//	List<String>

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trace);
		initView();
	}

	private void initView() {
		chat_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		chat_list.setAdapter(adapter = new RecyclerView.Adapter() {
			@NonNull
			@Override
			public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
				View view = LayoutInflater.from(WechatListActivity.this).inflate(R.layout.main_item, null);
				return new MyViewHolder(view);
			}

			@Override
			public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
				/*((MyViewHolder)viewHolder).txt.setText(list[i].name);
				viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick(View v) {
						startActivity(new Intent(WechatListActivity.this, list[i].clazz));
					}
				});*/
			}

			@Override
			public int getItemCount() {
				return 0;//list.length;
			}
		});

	}
	class MyViewHolder extends RecyclerView.ViewHolder {
		TextView txt;
		public MyViewHolder(@NonNull View itemView) {
			super(itemView);
			txt = itemView.findViewById(R.id.txt);
		}
	}

	class Picture {
		String name;
		String path;
	}

}
