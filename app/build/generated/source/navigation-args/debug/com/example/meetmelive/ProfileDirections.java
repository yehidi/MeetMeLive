package com.example.meetmelive;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class ProfileDirections {
  private ProfileDirections() {
  }

  @NonNull
  public static NavDirections actionProfileToEditProfileFragment() {
    return new ActionOnlyNavDirections(R.id.action_Profile_to_editProfileFragment);
  }
}
