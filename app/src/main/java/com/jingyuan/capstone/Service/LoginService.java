package com.jingyuan.capstone.Service;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginService {
    private final FirebaseFirestore db;

    public LoginService() {
        this.db = FirebaseFirestore.getInstance();
    }

    public boolean checkLogin(String username, String password) throws NullPointerException{
        Task<QuerySnapshot> task = db.collection("User").whereEqualTo("username", username).limit(1).get();
        if (task.isSuccessful()) {
            DocumentSnapshot docSnap = task.getResult().getDocuments().get(0);
            if (docSnap.exists()) {
                String truePassword = (String) docSnap.get("password");
                assert truePassword != null;
                return (truePassword.equals(password));
            }
        }
        return false;
    }
}
