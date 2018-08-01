package com.trafficticketbuddy.lawyer.fragement;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import com.trafficticketbuddy.lawyer.BaseActivity;

public class BaseFragment extends Fragment implements View.OnClickListener {
    public BaseActivity baseActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        baseActivity = (BaseActivity) context;
    }

    @Override
    public void onClick(View v) {

    }
    public void displayView(Fragment fragment) {
        if(fragment!=null){
            ((BaseFragment) getParentFragment()).displayView(fragment);
          /*  FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);*/
        }

    }

    public void goBack() {
        ((BaseActivity) getActivity()).onBackPressed();
    }


}
