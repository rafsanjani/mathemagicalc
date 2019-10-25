package com.foreverrafs.numericals.fragments.sys_of_equations;

import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.foreverrafs.numericals.activities.MainActivity;

abstract class FragmentSystemOfEquationsBase extends Fragment {
    protected void goToMainmenu(Button button) {
        if (getActivity() != null)
            ((MainActivity) getActivity()).goToMainMenu(button);
    }
}
