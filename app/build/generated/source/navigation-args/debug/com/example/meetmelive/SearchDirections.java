package com.example.meetmelive;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class SearchDirections {
  private SearchDirections() {
  }

  @NonNull
  public static NavDirections actionSearchToNearby() {
    return new ActionOnlyNavDirections(R.id.action_search_to_Nearby);
  }
}
