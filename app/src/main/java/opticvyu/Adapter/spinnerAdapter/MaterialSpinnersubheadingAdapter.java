package opticvyu.Adapter.spinnerAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.optic.interior.R;

import java.util.List;

import opticvyu.Model.Material.Heading;
import opticvyu.Model.Material.Sub_heading;


public class MaterialSpinnersubheadingAdapter extends ArrayAdapter<List<Heading>> {
    private final LayoutInflater layoutInflater;
    private final List<Sub_heading> mSub_headings;

    public MaterialSpinnersubheadingAdapter(Context context, List<Sub_heading> mSub_headings) {
        super(context, android.R.layout.simple_list_item_1);
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mSub_headings = mSub_headings;
    }

    @Override
    public int getCount() {
        return mSub_headings == null ? 0 : mSub_headings.size();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup viewgroup) {
        view = layoutInflater.inflate(R.layout.text, viewgroup, false);
        TextView tv_title = view.findViewById(R.id.text);

        tv_title.setText(mSub_headings.get(position).getSubheading());
        return view;
    }
}
