package com.example.meetmelive;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.meetmelive.chat.ConnectionsFragment;

public class UsersFragment extends ConnectionsFragment {
   // Button message,unmatch;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        message.setVisibility(View.INVISIBLE);
//        unmatch.setVisibility(View.INVISIBLE);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}

//public class UsersFragment extends Fragment {
//    RecyclerView recyclerView;
//    AdapterUsers adapterUsers;
//    //List<User> userList;
//
//    //new
//    ArrayList<DataModel> dataModelArrayList;
//    private ConnectionsAdapter adapter;
//    private CollectionReference FriendsReference;
//
//
//    public UsersFragment() {
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_users, container, false);
//        recyclerView = view.findViewById(R.id.users_recyclerView);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        //userList=new ArrayList<>();
//        //new
//        dataModelArrayList = new ArrayList<>();
//        //new
//        gatAllUser();
//
//        return view;
//    }
//
//    private void gatAllUser() {

//      //get current user
//        FirebaseUser fUser= FirebaseAuth.getInstance().getCurrentUser();
//        //get path of database named "Users"containing users info
//        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users");
//        //get all data from path
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                userList.clear();
//                for(DataSnapshot ds: dataSnapshot.getChildren()){
//                    User modelUser= ds.getValue(User.class);
//
//                    //get all users except currrently signed in user
//                    if(!modelUser.getUserId().equals(fUser.getUid())){
//                        userList.add(modelUser);
//                    }
//
//                    //adapter
//                    adapterUsers=new AdapterUsers(getActivity(),userList);
//                    recyclerView.setAdapter(adapterUsers);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//            }
//        });
//    }
//}