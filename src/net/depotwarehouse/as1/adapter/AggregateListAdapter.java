package net.depotwarehouse.as1.adapter;

import java.util.ArrayList;

import net.depotwarehouse.as1.R;
import net.depotwarehouse.as1.model.Clicker;
import net.depotwarehouse.as1.model.TimeAggregateCount;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AggregateListAdapter extends ArrayAdapter<TimeAggregateCount> {
	
	public AggregateListAdapter(Context context, ArrayList<TimeAggregateCount> list) {
		super(context, R.layout.listview_layout_item, R.id.item_name, list);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return super.getView(position, convertView, parent);	
	}

}
