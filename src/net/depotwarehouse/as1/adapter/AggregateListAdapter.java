package net.depotwarehouse.as1.adapter;

import java.util.ArrayList;

import net.depotwarehouse.as1.R;
import net.depotwarehouse.as1.model.TimeAggregateCount;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
/**
 * If you're wondering what the actual utility of this class is, it's pretty trivial. Functionally, it only allows me
 * to call the four-parameter ArrayAdapter constructor with only two of my own parameters. These adapters exist for
 * extended functionality down the line, however that functionality was not needed for this particular assignment.
 * @author tpavlek
 *
 */
public class AggregateListAdapter extends ArrayAdapter<TimeAggregateCount> {
	
	public AggregateListAdapter(Context context, ArrayList<TimeAggregateCount> list) {
		super(context, R.layout.listview_layout_item, R.id.item_name, list);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return super.getView(position, convertView, parent);	
	}

}
