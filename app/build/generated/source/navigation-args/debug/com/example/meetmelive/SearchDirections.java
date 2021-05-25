package com.example.meetmelive;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class SearchDirections {
  private SearchDirections() {
  }

  @NonNull
  public static NavDirections actionSearchFragmentNewToNearby() {
    return new ActionOnlyNavDirections(R.id.action_searchFragment_new_to_Nearby);
  }
}
