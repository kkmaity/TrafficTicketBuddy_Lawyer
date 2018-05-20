package com.trafficticketbuddy.lawyer.interfaces;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by User on 19-05-2018.
 */

public interface GoogleLoginCompleted {
    public void onGoogleCompleted(GoogleSignInAccount account);
}
