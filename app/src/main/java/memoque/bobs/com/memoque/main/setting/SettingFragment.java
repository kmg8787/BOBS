package memoque.bobs.com.memoque.main.setting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import memoque.bobs.com.memoque.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment
{
	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
	{
		// Inflate the layout for this fragment
		View view = inflater.inflate( R.layout.fragment_setting, container, false );

//		Button searchButton = view.findViewById(  )

		return view;
	}
}
